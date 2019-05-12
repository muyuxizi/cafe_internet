package com.csust.InternetCafe.business.serviceImpl;

import com.csust.InternetCafe.business.service.ComputerToGrid;
import com.csust.InternetCafe.common.commonconst.RedisOrSelect;
import com.csust.InternetCafe.common.commonconst.UpdateRedis;
import com.csust.InternetCafe.common.entity.Computers;
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
    @Transactional(rollbackFor = Exception.class)
    public String add(Computers computers) {
        try {
            computersService.insert(computers);
            updateRedis.UpdateComputers();
            return "success";
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String update(Computers computers) {
       try {
           computersService.updateById(computers);
           updateRedis.UpdateComputers(computers.getComputerId() , computers);
           return "success";
       }catch (Exception e){
           throw e;
       }
    }
}
