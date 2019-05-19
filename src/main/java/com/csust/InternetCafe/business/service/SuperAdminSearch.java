package com.csust.InternetCafe.business.service;

import com.csust.InternetCafe.common.entity.AdminOperationRecords;
import com.csust.InternetCafe.common.entity.EverydayBill;

import java.util.List;

/**
 * @Author: 小凯神
 * @create: 2019-05-17 13:57
 * @Description:
 */
public interface SuperAdminSearch {
    public List<AdminOperationRecords> getOperationRecords();

    public List<EverydayBill> getBillList();
}
