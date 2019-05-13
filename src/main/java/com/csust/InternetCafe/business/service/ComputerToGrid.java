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

    public String add(Computers computers);

    public String update(Computers computers);

    public String del(int id);

    public Computers getone(int id);
}
