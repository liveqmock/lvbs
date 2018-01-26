package com.daishumovie.api;

import org.springframework.beans.factory.support.DefaultBeanNameGenerator;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.web.client.RestTemplate;

import javax.servlet.MultipartConfigElement;

/**
 * boot入口
 *
 * @author zhuruisong on 2017/3/23
 * @since 1.0
 */
//@EnableCaching
@SpringBootApplication(
        scanBasePackages = {"com.daishumovie.api", "com.daishumovie.dao","com.daishumovie.third","com.daishumovie.search"}
)
@EnableAutoConfiguration(exclude={MongoAutoConfiguration.class})
public class ApiApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ApiApplication.class)
                //类名重复bean的处理
                .beanNameGenerator(new DefaultBeanNameGenerator())
                .run(args);
    }

    @Bean
    public RestTemplate getRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }
    
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("-1");
        factory.setMaxRequestSize("-1");
        return factory.createMultipartConfig();
    }

    @Bean("hashRedisTemplate")
    public RedisTemplate<String,Integer> getRedisTemplate(RedisTemplate redisTemplate) {

        GenericToStringSerializer<Integer> stringSerializer = new GenericToStringSerializer<>(Integer.TYPE);
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
        return redisTemplate;
    }


}
