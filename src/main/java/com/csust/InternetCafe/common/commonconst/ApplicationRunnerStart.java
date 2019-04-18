package com.csust.InternetCafe.common.commonconst;

import com.csust.InternetCafe.business.service.Initialization;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: 小凯神
 * @Date: 2019-04-18 15:57
 * @Description:
 */
@Component
public class ApplicationRunnerStart implements ApplicationRunner {

    private static Logger logger = LogManager.getLogger("HelloLog4j");

    @Resource
    private Initialization initialization;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initialization.LoadComputersToRedis();
        logger.info("测试启动项");
    }
}
