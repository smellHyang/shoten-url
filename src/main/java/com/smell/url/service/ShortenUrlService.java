package com.smell.url.service;

import com.smell.url.domain.dto.ShortenUrlCreateRequest;
import com.smell.url.domain.dto.ShortenUrlResponse;
import com.smell.url.domain.entity.ShortenUrl;
import com.smell.url.repository.ShortenUrlRepository;
import com.smell.url.util.Base62;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ShortenUrlService {
    private final ShortenUrlRepository shortenUrlRepository;
    private final Base62 base62Util;

    @Transactional
    public ShortenUrlResponse createShortenUrl(ShortenUrlCreateRequest request) {
        if (!isValidUrl(request.url)) {
            throw new RuntimeException("invalid url");
        }

        ShortenUrl shortenUrl = shortenUrlRepository.save(ShortenUrl.builder()
                .url(request.getUrl())
                .createdAt(LocalDateTime.now())
                .build()
        );
        String shortId = makeShortUrl(shortenUrl.getId());
        shortenUrl.updateShortUrl(shortId);
        return shortenUrl.toShortenUrlResponse();
    }


    //@Cacheable(value = "shortenUrl", key = "#p0")
    @Transactional(readOnly = true)
    public ShortenUrlResponse getShortenUrl(String shortId) {
        Long decodedId = makeOriginUrl(shortId);
        ShortenUrl shortenUrl = shortenUrlRepository.findById(decodedId)
                .orElseThrow(() -> new RuntimeException("Can`t not found shortId"));
        return shortenUrl.toShortenUrlResponse();
    }

    public boolean isValidUrl(String url) {
        String pattern = "(https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|www\\.[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9]+\\.[^\\s]{2,}|www\\.[a-zA-Z0-9]+\\.[^\\s]{2,})";
        return Pattern.matches(pattern, url);
    }

    private String makeShortUrl(Long index) {
        return base62Util.encoding(index);
    }

    private Long makeOriginUrl(String shortenUrl){
        Long id = base62Util.decoding(shortenUrl);
        return id;
    }
}
