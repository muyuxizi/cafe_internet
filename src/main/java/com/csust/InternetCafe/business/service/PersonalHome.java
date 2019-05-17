package com.csust.InternetCafe.business.service;

import com.csust.InternetCafe.business.vo.PersonalHomevo;

import java.util.List;

/**
 * @Author: 小凯神
 * @Date: 2019-04-15 17:18
 * @Description:
 */
public interface PersonalHome {

    public PersonalHomevo getinformation (String username);

    public List<PersonalHomevo> getcustomerslist();

    public String update(String username ,PersonalHomevo personalHomevo);
}
