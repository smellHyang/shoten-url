package com.smell.url.domain.entity;

import com.smell.url.domain.dto.ShortenUrlResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@RequiredArgsConstructor
public class ShortenUrl {
    @Id
    @Column(unique = true)
    private String shortId;
    private String url;
    private LocalDateTime createdAt;

    public ShortenUrlResponse toShortenUrlResponse() {
        return new ShortenUrlResponse(shortId, url, createdAt);
    }
}
