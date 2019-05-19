package com.csust.InternetCafe.business.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.csust.InternetCafe.business.service.PermissionToGrid;
import com.csust.InternetCafe.common.entity.Permission;
import com.csust.InternetCafe.common.service.PermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 小凯神
 * @create: 2019-05-17 16:44
 * @Description:
 */
@Service
public class PermissionToGridImpl implements PermissionToGrid {

    @Resource
    private PermissionService permissionService;

    @Override
    public List<Permission> get() {
        List<Permission> permissionList = new ArrayList<>();
        EntityWrapper<Permission> entityWrapper = new EntityWrapper<>();
        permissionList = permissionService.selectList(entityWrapper);
        return permissionList;
    }

    @Override
    public Permission getone(String name) {
        Permission permission = null;
        EntityWrapper<Permission> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("name" , name);
        permission = permissionService.selectOne(entityWrapper);
        return permission;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String add(Permission permission) {
        try {
            permissionService.insert(permission);
        }catch (Exception e){
            throw  e;
        }
        return "success";
    }

    @Override
    public String update(Permission permission) {
        try {
            permissionService.updateById(permission);
        }catch (Exception e){
            throw  e;
        }
        return "success";
    }

    @Override
    public String del(int id) {
        try {
            permissionService.deleteById(id);
        }catch (Exception e){
            throw  e;
        }
        return "success";
    }
}
