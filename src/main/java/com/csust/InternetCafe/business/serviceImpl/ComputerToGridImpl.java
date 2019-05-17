package com.csust.InternetCafe.business.serviceImpl;

import com.csust.InternetCafe.business.service.ComputerToGrid;
import com.csust.InternetCafe.common.commonconst.RedisOrSelect;
import com.csust.InternetCafe.common.commonconst.UpdateRedis;
import com.csust.InternetCafe.common.entity.AdminOperationRecords;
import com.csust.InternetCafe.common.entity.Computers;
import com.csust.InternetCafe.common.entity.Users;
import com.csust.InternetCafe.common.service.AdminOperarionRecordsService;
import com.csust.InternetCafe.common.service.ComputersService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 小凯神
 * @create: 2019-05-11 17:17
 * @Description:
 */
@Service
public class ComputerToGridImpl implements ComputerToGrid {

    @Resource
    private RedisOrSelect redisOrSelect;

    @Resource
    private UpdateRedis  updateRedis;

    @Resource
    private ComputersService computersService;

    @Resource
    private AdminOperarionRecordsService adminOperarionRecordsService;

    @Override
    public List<Computers> get() {
        Map<String , Object> map = new HashMap<>();
        map = redisOrSelect.findComputers();
        List<Computers> computersList = new ArrayList<>();
        for (Object object : map.values()){
            Computers computers = (Computers)object;
            computersList.add(computers);
        };
        return computersList;
    }

    @Override
    public Computers getone(int id) {
        Computers computers = redisOrSelect.findComputers(id);
        return computers;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String add(String username,Computers computers) {
        Users users = redisOrSelect.findUsers(username);
        AdminOperationRecords adminOperationRecords = AdminOperationRecords.builder()
                .adminId(users.getUid())
                .operationDetails("增加机器")
                .operationReason("增加机器")
                .operationSurface("computers")
                .updateTime(System.currentTimeMillis())
                .id(0)
                .build();
        try {
            adminOperarionRecordsService.insert(adminOperationRecords);
            computersService.insert(computers);
            updateRedis.UpdateComputers();
            return "success";
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String update(String username ,Computers computers) {
        Users users = redisOrSelect.findUsers(username);
        AdminOperationRecords adminOperationRecords = AdminOperationRecords.builder()
                .adminId(users.getUid())
                .operationDetails("更新机器")
                .operationReason("更新机器")
                .operationSurface("computers")
                .updateTime(System.currentTimeMillis())
                .id(0)
                .build();
       try {
           adminOperarionRecordsService.insert(adminOperationRecords);
           computersService.updateById(computers);
           updateRedis.UpdateComputers(computers.getComputerId() , computers);
           return "success";
       }catch (Exception e){
           throw e;
       }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String del(String username ,int id) {
        Users users = redisOrSelect.findUsers(username);
        AdminOperationRecords adminOperationRecords = AdminOperationRecords.builder()
                .adminId(users.getUid())
                .operationDetails("删除机器")
                .operationReason("删除机器")
                .operationSurface("computers")
                .updateTime(System.currentTimeMillis())
                .id(0)
                .build();
        try {
            adminOperarionRecordsService.insert(adminOperationRecords);
           computersService.deleteById(id);
           updateRedis.UpdateComputers();
            return "success";
        }catch (Exception e){
            throw e;
        }
    }


}
