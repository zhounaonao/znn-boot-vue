package com.znn.demo.dao.bo.impl;

import com.znn.demo.dao.SysDictDataDAO;
import com.znn.demo.dao.SysDictTypeDAO;
import com.znn.demo.dao.bo.SysDictTypeBO;
import com.znn.demo.domain.entity.SysDictType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 周闹闹
 * @version 1.0
 */
@Service
public class SysDictTypeBOImpl implements SysDictTypeBO {

    @Autowired
    SysDictTypeDAO sysDictTypeDAO;

    @Override
    public List<SysDictType> selectDictTypeList(SysDictType dictType) {
        return sysDictTypeDAO.selectDictTypeList(dictType);
    }
}
