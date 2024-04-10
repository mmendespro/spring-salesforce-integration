package br.com.demo.sfclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import br.com.demo.sfclient.annotations.EnableSfClientLibrary;

@EnableCaching
@EnableSfClientLibrary
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
