package com.ggwp.memberservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
//@EnableFeignClients
//@EnableDiscoveryClient
public class MemberServiceApplication  {

    public static void main(String[] args) {
        SpringApplication.run(MemberServiceApplication.class, args);
    }

}