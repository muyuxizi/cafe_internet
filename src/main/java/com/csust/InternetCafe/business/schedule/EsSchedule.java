package com.csust.InternetCafe.business.schedule;

import com.csust.InternetCafe.common.entity.EsSurfInternetRecords;
import com.csust.InternetCafe.common.mapper.EsSurfInternetRecordsRepository;
import com.csust.InternetCafe.common.mapper.EsSurfInternetRecordsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 小凯神
 * @create: 2019-05-10 11:36
 * @Description:
 */
@Service
public class EsSchedule {



    @Resource
    private EsSurfInternetRecordsService esSurfInternetRecordsService;

    private static Logger logger = LogManager.getLogger("HelloLog4j");

    public void insertToEs(String username) {
        EsSurfInternetRecords esSurfInternetRecords = EsSurfInternetRecords.builder()
                .id(17)
                .username(username)
                .uid(14)
                .computerId(11)
                .consumptionAmount(10)
                .startTime(System.currentTimeMillis())
                .endTime(System.currentTimeMillis())
                .cafeName("changsha")
                .build();

        EsSurfInternetRecords esSurfInternetRecords1 = EsSurfInternetRecords.builder()
                .id(18)
                .uid(22)
                .username(username)
                .computerId(13)
                .consumptionAmount(20)
                .startTime(System.currentTimeMillis())
                .endTime(System.currentTimeMillis())
                .cafeName("wangyu")
                .build();
       esSurfInternetRecordsService.save(esSurfInternetRecords1);
        esSurfInternetRecordsService.save(esSurfInternetRecords);

    }
}
