package com.znn.demo.admin.controller.system;

import com.znn.demo.common.core.controller.BaseController;
import com.znn.demo.dao.bo.SysDictTypeBO;
import com.znn.demo.domain.entity.SysDictType;
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
@RequestMapping("/system/dict/type")
public class SysDictTypeController extends BaseController {

    @Autowired
    SysDictTypeBO sysDictTypeBO;


    @GetMapping("list")
    public TableDataInfo list(SysDictType sysDictType) {

        startPage();
        List<SysDictType> list = sysDictTypeBO.selectDictTypeList(sysDictType);
        return getDataTable(list);
    }
}
