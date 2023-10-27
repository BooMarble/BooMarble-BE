package com.likelion.boomarble;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BooMarbleBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BooMarbleBeApplication.class, args);
    }

}
