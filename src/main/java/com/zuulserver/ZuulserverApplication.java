package com.zuulserver;

import com.zuulserver.config.FinalEnvConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

import javax.annotation.PostConstruct;

/**
 * 启动类，网关
 *
 * @author f
 * @date 2018-05-14
 */
@SpringBootApplication
@EnableZuulProxy
public class ZuulserverApplication {

    @Value("${spring.application.name}")
    private String appName;

    @Value("${spring.profiles.active}")
    private String env;

    public static void main(String[] args) {
        SpringApplication.run(ZuulserverApplication.class, args);
    }

    /**
     * 初始化
     */
    @PostConstruct
    public void init() {
        FinalEnvConfig.setAppName(appName);
        FinalEnvConfig.setEnv(env);
    }
}
