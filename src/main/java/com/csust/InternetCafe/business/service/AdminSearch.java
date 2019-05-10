package com.csust.InternetCafe.business.service;

import com.csust.InternetCafe.business.vo.Appointmentoutvo;
import com.csust.InternetCafe.business.vo.TemporaryAppointmentvo;

import java.util.List;

/**
 * @Author: 小凯神
 * @create: 2019-05-09 10:35
 * @Description:
 */
public interface AdminSearch {
    public List<TemporaryAppointmentvo> temporaryAppointmetList();

    public List<Appointmentoutvo> appointmentvooutlist();
}
