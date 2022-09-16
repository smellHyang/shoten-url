package com.smell.url.controller;

import com.smell.url.domain.dto.ShortenUrlCreateRequest;
import com.smell.url.domain.dto.ShortenUrlResponse;
import com.smell.url.service.ShortenUrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class UrlController {
    private final ShortenUrlService shortenUrlService;

    //URL을 입력하면 random한 ID를 가지는 Short Link를 생성해주는 API
    @PostMapping("/short-links")
    public ResponseEntity<ShortenUrlResponse> createShortenUrl(@RequestBody ShortenUrlCreateRequest shortenUrlCreateRequest){
        return ResponseEntity.ok(shortenUrlService.createShortenUrl(shortenUrlCreateRequest));
    }

    //Short Link 1개를 조회하는 API 개발
    @GetMapping("/short-links/{short_id}")
    public ResponseEntity<ShortenUrlResponse> redirect(@PathVariable(value = "short_id") String shortenUrl){
        return ResponseEntity.ok(shortenUrlService.getShortenUrl(shortenUrl));
    }
    
    //Short Link를 통해 접속했을떄 원래 입력했던 URL로 redirect 해주는 API
    @GetMapping("/r/{short_id}")
    public void redirect(@PathVariable(value = "short_id") String shortenUrl, HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.sendRedirect(shortenUrlService.getShortenUrl(shortenUrl).getUrl());
    }

}
