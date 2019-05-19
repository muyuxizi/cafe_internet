package com.csust.InternetCafe.business.controller;

import com.csust.InternetCafe.business.service.SuperAdminSearch;
import com.csust.InternetCafe.common.entity.AdminOperationRecords;
import com.csust.InternetCafe.common.entity.EverydayBill;
import com.csust.InternetCafe.common.entity.SurfInternetRecords;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 小凯神
 * @create: 2019-05-17 13:56
 * @Description:
 */
@RestController
public class SuperAdminSearchController {

    @Resource
    private SuperAdminSearch superAdminSearch;

    @RequestMapping(value = "/operation_records.action")
    @GetMapping
    public List<AdminOperationRecords> getrecords(){
        List<AdminOperationRecords> surfInternetRecordsList = new ArrayList<>();
        surfInternetRecordsList = superAdminSearch.getOperationRecords();
        return surfInternetRecordsList;
    }

    @RequestMapping(value = "/everyday_bill.action")
    @GetMapping
    public List<EverydayBill> everydayBillList(){
        List<EverydayBill> everydayBillList = new ArrayList<>();
        everydayBillList = superAdminSearch.getBillList();
        return everydayBillList;
    }
}
