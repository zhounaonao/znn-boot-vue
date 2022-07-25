package com.znn.demo.dao;

import com.znn.demo.domain.entity.SysDictData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 周闹闹
 * @version 1.0
 */
@Mapper
public interface SysDictDataDAO {

    /**
     * 根据条件分页查询字典数据
     *
     * @param dictData 字典数据信息
     * @return 字典数据集合信息
     */
    public List<SysDictData> selectDictDataList(SysDictData dictData);



}
