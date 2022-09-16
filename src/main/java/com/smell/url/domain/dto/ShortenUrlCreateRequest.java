package com.smell.url.domain.dto;

import lombok.Getter;

/**
 *  shorten url 생성 Request
 * */
@Getter
public class ShortenUrlCreateRequest {
    public String url;
}
