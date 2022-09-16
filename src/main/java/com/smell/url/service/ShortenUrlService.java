package com.smell.url.service;

import com.smell.url.component.Base62;
import com.smell.url.domain.dto.ShortenUrlCreateRequest;
import com.smell.url.domain.dto.ShortenUrlResponse;
import com.smell.url.domain.entity.ShortenUrl;
import com.smell.url.repository.ShortenUrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShortenUrlService {
    private final ShortenUrlRepository shortenUrlRepository;
    @Autowired
    private Base62 base62Util;

    @Transactional
    public ShortenUrlResponse createShortenUrl(ShortenUrlCreateRequest request) {
        ShortenUrl shortenUrl = shortenUrlRepository.save(ShortenUrl.builder()
                .shortId(makeShortUrl(request.getUrl()))
                .url(request.getUrl())
                .createdAt(LocalDateTime.now())
                .build()
        );
        return shortenUrl.toShortenUrlResponse();
    }

    @Transactional(readOnly = true)
    public ShortenUrlResponse getShortenUrl(String shortId) {
        ShortenUrl shortenUrl = shortenUrlRepository.findById(shortId)
                .orElseThrow(() -> new RuntimeException("Can`t not found shortId"));
        return shortenUrl.toShortenUrlResponse();
    }

    private String makeShortUrl(String url) {
        // TODO Algorithm
        Integer id = shortenUrlRepository.findByOriginUrl(url);
        if(id == null) shortenUrlRepository.updateOriginUrl(url);

        return Base62.encoding(id);
    }



    private String makeOriginUrl(String url){
        Integer id = Base62.decoding(url);
        String originUrl = shortenUrlRepository.findOriginUrlByShortUrl(url);
        return originUrl;
    }


}
