package com.daishumovie.timer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

/**
 * @author zhuruisong on 2017/4/5
 * @since 1.0
 */
//开启定时任务
@EnableScheduling
@SpringBootApplication(scanBasePackages = {"com.daishumovie.timer","com.daishumovie.dao"})
@Slf4j
public class TimerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(TimerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("-------------------------------------------------start");
    }

    @Bean
    public RestTemplate getRestTemplat(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }
}
