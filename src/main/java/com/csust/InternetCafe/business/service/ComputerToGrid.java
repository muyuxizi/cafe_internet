package com.csust.InternetCafe.business.service;

import com.csust.InternetCafe.common.entity.Computers;

import java.util.List;

/**
 * @Author: 小凯神
 * @create: 2019-05-11 17:16
 * @Description:
 */
public interface ComputerToGrid {

    public List<Computers> get();

    public String add(String username ,Computers computers);

    public String update(String username ,Computers computers);

    public String del(String username ,int id);

    public Computers getone(int id);
}
