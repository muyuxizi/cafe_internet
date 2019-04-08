package com.csust.InternetCafe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;



@SpringBootApplication()
public class CafeInternetApplication {

    public static void main(String[] args) throws Exception{
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(CafeInternetApplication.class, args);
    }

}



