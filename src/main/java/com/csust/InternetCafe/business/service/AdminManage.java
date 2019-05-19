package com.csust.InternetCafe.business.service;

import com.csust.InternetCafe.common.entity.Admin;

import java.util.List;

/**
 * @Author: 小凯神
 * @create: 2019-05-18 19:26
 * @Description:
 */
public interface AdminManage {
    public List<Admin> get ();

    public Admin getone(String name);

    public String add(Admin admin);

    public String update(Admin admin);

    public String del(int uid , int id);
}
