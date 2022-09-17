package com.smell.url.service;

import com.smell.url.util.Base62;
import com.smell.url.domain.dto.ShortenUrlCreateRequest;
import com.smell.url.domain.dto.ShortenUrlResponse;
import com.smell.url.domain.entity.ShortenUrl;
import com.smell.url.repository.ShortenUrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ShortenUrlService {
    private final ShortenUrlRepository shortenUrlRepository;
    private final Base62 base62Util;

    @Transactional
    public ShortenUrlResponse createShortenUrl(ShortenUrlCreateRequest request) {
        ShortenUrl shortenUrl = shortenUrlRepository.save(ShortenUrl.builder()
                .url(request.getUrl())
                .createdAt(LocalDateTime.now())
                .build()
        );
        String shortId = makeShortUrl(shortenUrl.getId());
        shortenUrl.updateShortUrl(shortId);
        return shortenUrl.toShortenUrlResponse();
    }

    @Transactional(readOnly = true)
    public ShortenUrlResponse getShortenUrl(String shortId) {
        Long decodedId = makeOriginUrl(shortId);
        ShortenUrl shortenUrl = shortenUrlRepository.findById(decodedId)
                .orElseThrow(() -> new RuntimeException("Can`t not found shortId"));
        return shortenUrl.toShortenUrlResponse();
    }

    private String makeShortUrl(Long index) {
        return base62Util.encoding(index);
    }

    private Long makeOriginUrl(String shortenUrl){
        Long id = base62Util.decoding(shortenUrl);
        return id;
    }
}
