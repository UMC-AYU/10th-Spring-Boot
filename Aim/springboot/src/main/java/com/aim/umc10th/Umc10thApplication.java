package com.aim.umc10th;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // 자동으로 시간 기록
@SpringBootApplication
public class Umc10thApplication {

    public static void main(String[] args) {
        SpringApplication.run(Umc10thApplication.class, args);
    }

}
