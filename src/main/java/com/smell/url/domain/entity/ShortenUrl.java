package com.smell.url.domain.entity;

import com.smell.url.domain.dto.ShortenUrlResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@SequenceGenerator(
        name = "SHORTEN_URL_ID_GENERATOR",
        sequenceName = "ENTITY_SEQUENCES",
        initialValue = 3844)
public class ShortenUrl {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SHORTEN_URL_ID_GENERATOR")
    @Column(unique = true)
    private Long id;

    private String shortId;
    private String url;
    private LocalDateTime createdAt;


    public void updateShortUrl(String shortId) {
        this.shortId = shortId;
    }

    public ShortenUrlResponse toShortenUrlResponse() {
        return new ShortenUrlResponse(shortId, url, createdAt);
    }
}
