package com.smell.url;

import com.smell.url.domain.dto.ShortenUrlCreateRequest;
import com.smell.url.domain.dto.ShortenUrlResponse;
import com.smell.url.domain.entity.ShortenUrl;
import com.smell.url.repository.ShortenUrlRepository;
import com.smell.url.service.ShortenUrlService;
import com.smell.url.util.Base62;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ShortenUrlServiceTest {
    @InjectMocks
    private ShortenUrlService shortenUrlService;
    @Mock
    private ShortenUrlRepository shortenUrlRepository;
    @Autowired
    private Base62 base62;

    /** Testing create */
    @Test
    public void createShortenUrl() {
        // given
        String givenUrl = "https://www.ab180.co/solutions/airbridge";
        ShortenUrlCreateRequest request = new ShortenUrlCreateRequest(givenUrl);
        given(shortenUrlRepository.save(any())).willReturn(ShortenUrl.builder()
                .id(1L)
                .url(givenUrl)
                .createdAt(LocalDateTime.now())
                .build());

        // when
        ShortenUrlResponse response = shortenUrlService.createShortenUrl(request);

        // then
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getUrl(), givenUrl);
        Assertions.assertNotNull(response.getShortId());
    }

    /** Testing getShortenUrl */
    @Test
    public void getShortenUrl() {
        // given
        String givenUrl =  "https://www.ab180.co/solutions/airbridge";
        given(shortenUrlRepository.findById(any())).willReturn(Optional.of(ShortenUrl.builder()
                .id(1L)
                .url(givenUrl)
                .createdAt(LocalDateTime.now())
                .shortId("abcdc")
                .build()));

        // when
        ShortenUrlResponse response = shortenUrlService.getShortenUrl("shortId");

        // then
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getUrl(), givenUrl);
        Assertions.assertNotNull(response.getShortId());
    }

    @Test
    public void testBase62() {
        Assertions.assertEquals(Base62.encoding(62L * 62L).length(), 3);
    }
}
