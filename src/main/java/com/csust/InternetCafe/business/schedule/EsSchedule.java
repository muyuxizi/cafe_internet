package com.csust.InternetCafe.business.schedule;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.csust.InternetCafe.common.commonconst.RedisOrSelect;
import com.csust.InternetCafe.common.entity.EsSurfInternetRecords;
import com.csust.InternetCafe.common.entity.SurfInternetRecords;
import com.csust.InternetCafe.common.entity.Users;
import com.csust.InternetCafe.common.mapper.EsSurfInternetRecordsRepository;
import com.csust.InternetCafe.common.mapper.EsSurfInternetRecordsService;
import com.csust.InternetCafe.common.service.SurfInternetRecordsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 小凯神
 * @create: 2019-05-10 11:36
 * @Description:
 */
@Service
public class EsSchedule {

    @Resource
    private SurfInternetRecordsService surfInternetRecordsService;

    @Resource
    private EsSurfInternetRecordsService esSurfInternetRecordsService;

    @Resource
    private RedisOrSelect redisOrSelect;

    private static Logger logger = LogManager.getLogger("HelloLog4j");

    @Transactional(rollbackFor = Exception.class)
    @Scheduled(cron = "0 0 0 * * ?")
    public void insertToEs() {
       logger.info("将上网记录存入ES");
        EntityWrapper<SurfInternetRecords> surfInternetRecordsEntityWrapper = new EntityWrapper<>();
        List<SurfInternetRecords> surfInternetRecordsList = new ArrayList<>();
        surfInternetRecordsList = surfInternetRecordsService.selectList(surfInternetRecordsEntityWrapper);
        for(SurfInternetRecords surfInternetRecords : surfInternetRecordsList){
            Users users = redisOrSelect.findUsers(surfInternetRecords.getUid());
            EsSurfInternetRecords esSurfInternetRecords = EsSurfInternetRecords.builder()
                    .cafeName(surfInternetRecords.getCafeName())
                    .consumptionAmount(surfInternetRecords.getConsumptionAmount())
                    .computerId(surfInternetRecords.getComputerId())
                    .uid(surfInternetRecords.getUid())
                    .id(surfInternetRecords.getId())
                    .startTime(surfInternetRecords.getStartTime())
                    .endTime(surfInternetRecords.getEndTime())
                    .username(users.getUsername())
                    .build();
            esSurfInternetRecordsService.save(esSurfInternetRecords);
        }
        try {
            EntityWrapper<SurfInternetRecords> entityWrapper = new EntityWrapper<>();
            entityWrapper.eq("cafe_name","wangyu");
            surfInternetRecordsService.delete(entityWrapper);
        }catch (Exception e){
            throw  e;
        }


    }
}
