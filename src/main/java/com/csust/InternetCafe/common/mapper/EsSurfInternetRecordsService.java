package com.csust.InternetCafe.common.mapper;

import com.csust.InternetCafe.common.entity.EsSurfInternetRecords;

import java.util.List;

/**
 * @Author: 小凯神
 * @create: 2019-05-10 12:29
 * @Description:
 */
public interface EsSurfInternetRecordsService {
    public List<EsSurfInternetRecords> getByName(String cafeName);

    public void save(EsSurfInternetRecords esSurfInternetRecords);
}
