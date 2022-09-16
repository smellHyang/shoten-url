package com.smell.url.repository;

import com.smell.url.domain.entity.ShortenUrl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortenUrlRepository extends JpaRepository<ShortenUrl, String> {
    Integer findByOriginUrl(String url);
    String updateOriginUrl(String url);
    String findOriginUrlByShortUrl(String url);
}
