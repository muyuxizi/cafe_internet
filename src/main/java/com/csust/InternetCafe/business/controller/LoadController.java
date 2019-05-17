package com.csust.InternetCafe.business.controller;


import com.csust.InternetCafe.business.schedule.EsSchedule;
import com.csust.InternetCafe.business.service.LoadAndRegister;
import com.csust.InternetCafe.business.vo.Registervo;
import com.csust.InternetCafe.common.commonconst.Const;
import com.csust.InternetCafe.common.commonconst.RedisOrSelect;
import com.csust.InternetCafe.common.config.RabbitmqConfig;
import com.csust.InternetCafe.common.entity.EsSurfInternetRecords;
import com.csust.InternetCafe.common.entity.Permission;
import com.csust.InternetCafe.common.entity.Users;
import com.csust.InternetCafe.common.mapper.EsSurfInternetRecordsRepository;
import com.csust.InternetCafe.common.pojo.MsgProducer;
import com.csust.InternetCafe.common.service.PermissionService;
import net.minidev.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Set;

/**
 * @Author: 小凯神
 * @Date: 2019-02-28 10:18
 * @Description:
 */

@Controller
public class LoadController {


    private ModelAndView modelAndView;

    @Resource
    private MsgProducer msgProducer;

    @Resource
    private LoadAndRegister loadAndRegister;

    @Resource
    private RedisOrSelect redisOrSelect;

    private static Logger logger = LogManager.getLogger("HelloLog4j");

    @Resource
    private EsSchedule esSchedule;

    @RequestMapping("/login.html")
    @PostMapping
    public String login() {
        esSchedule.insertToEs("用户1");
        msgProducer.sendMsg("登陆请求");
        logger.info("登陆请求已经发送");
        return "login";
    }

    @RequestMapping("/error.html")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        logger.info("密码错误？");
        return "login";
    }

    @RequestMapping("/login/timeout")
    @GetMapping
    public String timeOut(){
        return "login";
    }


    @RequestMapping("/find.html")
    @GetMapping
    public View tiaozhuan(ModelMap model, HttpServletRequest request){
        String path = request.getContextPath() ;
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
        Set<String> roles = AuthorityUtils.authorityListToSet(SecurityContextHolder.getContext()
                .getAuthentication().getAuthorities());
        logger.info(roles);
        if (roles.contains("read_index")) {
            return new RedirectView(basePath + "index");
        }else if(roles.contains("read_admin")) {
            return new RedirectView(basePath + "admin.html");
        }else {
            return new RedirectView(basePath + "superadmin.html");
        }
    }



   @RequestMapping(value = "/register.action")
    @PostMapping
    public String toRegister(){
        return "register";
    }




}
