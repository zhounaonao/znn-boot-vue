package com.znn.demo.admin.controller.system;

import com.znn.demo.common.core.controller.BaseController;
import com.znn.demo.dao.bo.SysDictDataBO;
import com.znn.demo.domain.entity.SysDictData;
import com.znn.demo.domain.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 周闹闹
 * @version 1.0
 */
@RestController
@RequestMapping("/system/dict/data")
public class SysDictDataController extends BaseController {


    @Autowired
    SysDictDataBO sysDictDataBO;

    @GetMapping("/list")
    public TableDataInfo list(SysDictData sysDictData){

        startPage();
        List<SysDictData> list = sysDictDataBO.selectDictDataList(sysDictData);
        return getDataTable(list);
    }

}
