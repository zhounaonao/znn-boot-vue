package com.znn.demo.dao.bo.impl;

import com.znn.demo.dao.SysDictDataDAO;
import com.znn.demo.dao.bo.SysDictDataBO;
import com.znn.demo.domain.entity.SysDictData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 周闹闹
 * @version 1.0
 */
@Service
public class SysDictDataBOImpl implements SysDictDataBO {

    @Autowired
    SysDictDataDAO sysDictDataDAO;

    /**
     * 根据条件分页查询字典数据
     *
     * @param dictData 字典数据信息
     * @return 字典数据集合信息
     */
    @Override
    public List<SysDictData> selectDictDataList(SysDictData dictData) {
        return sysDictDataDAO.selectDictDataList(dictData);
    }
}
