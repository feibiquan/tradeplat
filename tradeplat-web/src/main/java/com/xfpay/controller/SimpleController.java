package com.xfpay.controller;

import com.xfpay.mapper.SimpleEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by fei on 2019/6/4.
 * use to test
 */

@Controller
@RequestMapping("/test")
public class SimpleController {



    @Autowired
    Environment environment;


    @Autowired
    SimpleEntityMapper mapper;

    @RequestMapping("/test")
    @ResponseBody
    public String hello(){
        String[] activeProfiles = environment.getActiveProfiles();
        for (String activeProfile : activeProfiles) {
            System.out.println("activeProfile:   "+activeProfile);

        }

        String property = environment.getProperty("catalina.home");
        System.out.println("property:   "+property);


        String WORKDIR = environment.getProperty("WORKDIR");
        System.out.println("WORKDIR:   "+WORKDIR);

        return "hahaha";
    }
}
