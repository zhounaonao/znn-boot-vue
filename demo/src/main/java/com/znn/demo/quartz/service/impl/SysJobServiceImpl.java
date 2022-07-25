package com.znn.demo.quartz.service.impl;

import com.znn.demo.common.constant.ScheduleConstants;
import com.znn.demo.common.exception.job.TaskException;
import com.znn.demo.quartz.dao.SysJobDAO;
import com.znn.demo.quartz.domain.SysJob;
import com.znn.demo.quartz.service.SysJobService;
import com.znn.demo.quartz.util.ScheduleUtils;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author 周闹闹
 * @version 1.0
 */
@Service
public class SysJobServiceImpl implements SysJobService {

    @Autowired
    SysJobDAO sysJobDAO;

    @Autowired
    private Scheduler scheduler;

    /**
     * 项目启动时，初始化定时器 主要是防止手动修改数据库导致未同步到定时任务处理（注：不能手动修改数据库ID和任务组名，否则会导致脏数据）
     */
    @PostConstruct
    public void init() throws SchedulerException, TaskException {
        scheduler.clear();
        List<SysJob> jobList = sysJobDAO.selectJobAll();
        for (SysJob job : jobList) {
            // 根据job的设定创建定时任务
            ScheduleUtils.createScheduleJob(scheduler, job);
        }
    }


    @Override
    public List<SysJob> selectJobList(SysJob job) {
        return sysJobDAO.selectJobList(job);
    }

    @Override
    public SysJob selectJobById(Long jobId) {
        return sysJobDAO.selectJobById(jobId);
    }

    /**
     * 暂停任务
     *
     * @param job 调度信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int pauseJob(SysJob job) throws SchedulerException {
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        job.setStatus(ScheduleConstants.Status.PAUSE.getValue());
        int rows = sysJobDAO.updateJob(job);
        if (rows > 0)
        {
            scheduler.pauseJob(ScheduleUtils.getJobKey(jobId, jobGroup));
        }
        return rows;
    }

    /**
     * 恢复任务
     *
     * @param job 调度信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int resumeJob(SysJob job) throws SchedulerException {
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        job.setStatus(ScheduleConstants.Status.NORMAL.getValue());
        int rows = sysJobDAO.updateJob(job);
        if (rows > 0)
        {
            scheduler.resumeJob(ScheduleUtils.getJobKey(jobId, jobGroup));
        }
        return rows;
    }

    @Override
    public int changeStatus(SysJob job) throws SchedulerException {
        int rows = 0;
        String status = job.getStatus();
        // NORMAL 正常,"0"
        if (ScheduleConstants.Status.NORMAL.getValue().equals(status)) {
            // 恢复暂停的任务
            rows = resumeJob(job);
        }
        else if (ScheduleConstants.Status.PAUSE.getValue().equals(status))
        {
            // 暂停任务
            rows = pauseJob(job);
        }
        return rows;
    }
}
