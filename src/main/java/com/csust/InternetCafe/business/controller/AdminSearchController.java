package com.csust.InternetCafe.business.controller;

import com.csust.InternetCafe.business.service.AdminSearch;
import com.csust.InternetCafe.business.vo.Appointmentoutvo;
import com.csust.InternetCafe.business.vo.TemporaryAppointmentvo;
import com.csust.InternetCafe.common.entity.Appointment;
import com.csust.InternetCafe.common.entity.SurfInternetRecords;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 小凯神
 * @create: 2019-05-09 10:38
 * @Description:
 */
@RestController
public class AdminSearchController {

    @Resource
    private AdminSearch adminSearch;

    private static Logger logger = LogManager.getLogger("HelloLog4j");

    @RequestMapping(value = "/temporary_appointment.action")
    @GetMapping
    public List<TemporaryAppointmentvo> temporaryAppointmentvos(HttpServletRequest request){
        List<TemporaryAppointmentvo> temporaryAppointmentvoList = new ArrayList<>();
        temporaryAppointmentvoList = adminSearch.temporaryAppointmetList();
        return temporaryAppointmentvoList;
    }

    @RequestMapping(value = "/surf_record.action")
    @GetMapping
    public List<SurfInternetRecords> surfInternetRecordvos(){
        List<SurfInternetRecords> surfInternetRecords = new ArrayList<>();
        return surfInternetRecords;
    }

    @RequestMapping(value = "/appointment.action")
    @GetMapping
    public List<Appointmentoutvo> appointmentvos(){
        List<Appointmentoutvo> appointmentoutvos = new ArrayList<>();
        appointmentoutvos = adminSearch.appointmentvooutlist();
        return appointmentoutvos;
    }
}
