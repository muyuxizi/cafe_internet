package com.csust.InternetCafe.business.service;

import com.csust.InternetCafe.common.entity.Permission;

import java.util.List;

/**
 * @Author: 小凯神
 * @create: 2019-05-17 16:42
 * @Description:
 */
public interface PermissionToGrid {

    public List<Permission> get ();

    public Permission getone(String name);

    public String add(Permission permission);

    public String update(Permission permission);

    public String del(int id);
}
