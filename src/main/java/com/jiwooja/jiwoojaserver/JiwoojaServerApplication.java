package com.jiwooja.jiwoojaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class JiwoojaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(JiwoojaServerApplication.class, args);
    }

    @Bean
    public RestTemplate template() {
        return new RestTemplate();
    }
}
