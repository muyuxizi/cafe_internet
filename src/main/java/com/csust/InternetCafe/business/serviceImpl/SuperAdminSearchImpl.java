package com.csust.InternetCafe.business.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.csust.InternetCafe.business.service.SuperAdminSearch;
import com.csust.InternetCafe.common.entity.AdminOperationRecords;
import com.csust.InternetCafe.common.entity.EverydayBill;
import com.csust.InternetCafe.common.entity.TemporaryAppointment;
import com.csust.InternetCafe.common.service.AdminOperarionRecordsService;
import com.csust.InternetCafe.common.service.EveryBillService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 小凯神
 * @create: 2019-05-17 14:00
 * @Description:
 */
@Service
public class SuperAdminSearchImpl implements SuperAdminSearch {


    @Resource
    private AdminOperarionRecordsService adminOperarionRecordsService;

    @Resource
    private EveryBillService everyBillService;

    @Override
    public List<AdminOperationRecords> getOperationRecords() {
        List<AdminOperationRecords> adminOperationRecordsList = new ArrayList<>();
        EntityWrapper<AdminOperationRecords> entityWrapper = new EntityWrapper<>();
        adminOperationRecordsList = adminOperarionRecordsService.selectList(entityWrapper);
        return adminOperationRecordsList;
    }

    @Override
    public List<EverydayBill> getBillList() {
        List<EverydayBill> everydayBillList = new ArrayList<>();
        EntityWrapper<EverydayBill> entityWrapper = new EntityWrapper<>();
        everydayBillList = everyBillService.selectList(entityWrapper);
        return everydayBillList;
    }
}
