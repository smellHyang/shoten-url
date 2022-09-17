package com.smell.url;

import com.smell.url.domain.entity.ShortenUrl;
import com.smell.url.repository.ShortenUrlRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class MySqlConnectionTest {

    @Autowired
    ShortenUrlRepository shortenUrlRepository;

    @Test
    public void save(){

        //Only OriginUrl Insert Test
        shortenUrlRepository.save(ShortenUrl.builder()
                .url("https://www.youtube.com/watch?v=ueksOltZqH0")
                .createdAt(LocalDateTime.now())
                .build());
    }

}