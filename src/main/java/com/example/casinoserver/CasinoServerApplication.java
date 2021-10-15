package com.example.casinoserver;

import com.example.casinoserver.entities.GameLog;
import com.example.casinoserver.entities.User;
import com.example.casinoserver.repositories.GameLogRepository;
import com.example.casinoserver.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CasinoServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CasinoServerApplication.class, args);
    }


}
