package com.smell.url.domain.dto;

import lombok.*;

/**
 *  shorten url 생성 Request
 * */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShortenUrlCreateRequest {
    public String url;
}
