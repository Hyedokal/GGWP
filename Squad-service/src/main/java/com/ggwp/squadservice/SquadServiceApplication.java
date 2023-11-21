package com.ggwp.squadservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SquadServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SquadServiceApplication.class, args);
    }

}
