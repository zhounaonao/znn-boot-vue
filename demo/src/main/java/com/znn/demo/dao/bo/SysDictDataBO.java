package com.znn.demo.dao.bo;

import com.znn.demo.domain.entity.SysDictData;

import java.util.List;

/**
 * @author 周闹闹
 * @version 1.0
 */
public interface SysDictDataBO {

    /**
     * 根据条件分页查询字典数据
     *
     * @param dictData 字典数据信息
     * @return 字典数据集合信息
     */
    public List<SysDictData> selectDictDataList(SysDictData dictData);

}
