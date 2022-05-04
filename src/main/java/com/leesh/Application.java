package com.leesh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args){
        //스프링부트 내장was 실행
        SpringApplication.run(Application.class, args);
    }
}
