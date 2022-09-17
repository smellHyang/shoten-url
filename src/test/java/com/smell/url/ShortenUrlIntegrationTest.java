package com.smell.url;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smell.url.domain.dto.ShortenUrlCreateRequest;
import com.smell.url.service.ShortenUrlService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@Transactional
public class ShortenUrlIntegrationTest {

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private ShortenUrlService shortenUrlService;

    @Test
    @DisplayName("OriginUrl to ShortenUrl")
    public void shortenUrl() throws Exception{

        //given
        ShortenUrlCreateRequest shortenUrlRequest = ShortenUrlCreateRequest.builder()
                .url("https://www.youtube.com/watch?v=ueksOltZqH0")
                .build();

        //when & then
        mockMvc.perform(post("/short-links")
                        .content(objectMapper.writeValueAsString(shortenUrlRequest))
                        .contentType("application/json")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("data").hasJsonPath())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString()
                ;
    }
}
