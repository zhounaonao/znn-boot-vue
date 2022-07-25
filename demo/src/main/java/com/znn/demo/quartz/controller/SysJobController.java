package com.znn.demo.quartz.controller;

import com.znn.demo.common.core.controller.BaseController;
import com.znn.demo.common.enums.BusinessType;
import com.znn.demo.domain.AjaxResult;
import com.znn.demo.domain.page.TableDataInfo;
import com.znn.demo.framework.aspectj.annotation.Log;
import com.znn.demo.quartz.domain.SysJob;
import com.znn.demo.quartz.service.SysJobService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 周闹闹
 * @version 1.0
 */
@RestController
@RequestMapping("/monitor/job")
public class SysJobController extends BaseController {

    @Autowired
    SysJobService sysJobService;

    @PreAuthorize("@ss.hasPermi('monitor:job:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysJob sysJob){

        startPage();
        List<SysJob> sysJobs = sysJobService.selectJobList(sysJob);
        return getDataTable(sysJobs);
    }


    /**
     * 定时任务状态修改
     */
    @PreAuthorize("@ss.hasPermi('monitor:job:changeStatus')")
    @Log(title = "定时任务", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody SysJob job) throws SchedulerException {

        SysJob newJob = sysJobService.selectJobById(job.getJobId());
        newJob.setStatus(job.getStatus());
        return toAjax(sysJobService.changeStatus(newJob));
    }

}
