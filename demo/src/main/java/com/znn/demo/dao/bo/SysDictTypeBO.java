package com.znn.demo.dao.bo;

import com.znn.demo.domain.entity.SysDictType;

import java.util.List;

/**
 * @author 周闹闹
 * @version 1.0
 */
public interface SysDictTypeBO {

    /**
     * 根据条件分页查询字典类型
     *
     * @param dictType 字典类型信息
     * @return 字典类型集合信息
     */
    public List<SysDictType> selectDictTypeList(SysDictType dictType);

}
