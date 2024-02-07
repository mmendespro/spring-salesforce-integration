package br.com.vivo.sfclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import br.com.vivo.sfclient.annotations.EnableSfClientLibrary;

@EnableCaching
@EnableSfClientLibrary
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
