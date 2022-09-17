package com.smell.url.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
