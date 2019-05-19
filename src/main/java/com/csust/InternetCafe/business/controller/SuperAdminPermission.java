package com.csust.InternetCafe.business.controller;

import com.csust.InternetCafe.business.service.PermissionToGrid;
import com.csust.InternetCafe.common.entity.Permission;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: 小凯神
 * @create: 2019-05-17 16:40
 * @Description:
 */
@RestController
public class SuperAdminPermission {

    private static Logger logger = LogManager.getLogger("HelloLog4j");

    @Resource
    private PermissionToGrid permissionToGrid;

    @RequestMapping(value = "/permission_list.action")
    @GetMapping
    public List<Permission> getall(){
        List<Permission> permissionList = new ArrayList<>();
        permissionList = permissionToGrid.get();
        logger.info(permissionList);
        return  permissionList;
    }

    @RequestMapping(value = "/permission_one.action")
    public List<Permission> getone(@RequestParam Map<String, Object> params){
        String name =(String) params.get("name");
        List<Permission> permissionList = new ArrayList<>();
        Permission permission = null;
        permission = permissionToGrid.getone(name);
        permissionList.add(permission);
        return permissionList;
    }

    @RequestMapping(value = "/permission_list/add")
    @PostMapping
    public String add(@RequestBody Permission permission){
        String result = permissionToGrid.add(permission);
        if("success".equals(result)){
            return "success";
        }
        return "error";
    }

    @RequestMapping(value = "/permission_list/update")
    @PostMapping
    public String update(@RequestBody Permission permission){
        String result = permissionToGrid.update(permission);
        if("success".equals(result)){
            return "success";
        }
        return "error";
    }

    @RequestMapping(value = "/permission_list/del")
    @PostMapping
    public String del(@RequestBody Permission permission){
        String result = permissionToGrid.del(permission.getId());
        if("success".equals(result)){
            return "success";
        }
        return "error";
    }
}
