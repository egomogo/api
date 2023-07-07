package com.egomogo.api.global.adapter.webclient;

import com.egomogo.api.global.adapter.webclient.dto.KakaoMapResponse;
import com.egomogo.api.global.exception.impl.BadRequest;
import com.egomogo.api.global.util.ValidUtils;
import com.egomogo.api.global.adapter.webclient.dto.CoordinateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
public class KakaoWebClientComponent {

    private final WebClient webClient;

    public KakaoWebClientComponent(Environment env) {
        final String API_KEY = env.getProperty("kakao.api-key");
        final String KAKAO_AK_PREFIX = "KakaoAK ";
        final String KAKAO_AUTHORIZATION = KAKAO_AK_PREFIX + API_KEY;

        webClient = WebClient.builder()
                .baseUrl("https://dapi.kakao.com/v2")
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                    httpHeaders.set(HttpHeaders.AUTHORIZATION, KAKAO_AUTHORIZATION);
                })
                .build();
    }

    public CoordinateDto getCoordinateByAddress(String address) {
        log.info("START --- Kakao API called for get coordinate.");

        final String queryAddress = address.substring(0, address.indexOf(" ("));

        log.info("DURING --- Kakao API called for get coordinates.\naddress -> {}", queryAddress);
        KakaoMapResponse response = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/local/search/address")
                        .queryParam("query", queryAddress)
                        .build())
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        statusResponse -> statusResponse.bodyToMono(String.class).map(RuntimeException::new)
                )
                .onStatus(
                        HttpStatusCode::is5xxServerError,
                        statusResponse -> statusResponse.bodyToMono(String.class).map(RuntimeException::new)
                )
                .bodyToMono(KakaoMapResponse.class)
                .block();

        KakaoMapResponse.KakaoMapDocument kakaoMapDocument = validateResponseAndIfPresentGetDocument(response, queryAddress);

        log.info("END --- Kakao API called for get coordinate.");
        return new CoordinateDto(
                Double.parseDouble(kakaoMapDocument.getX()),
                Double.parseDouble(kakaoMapDocument.getY())
        );
    }

    private KakaoMapResponse.KakaoMapDocument validateResponseAndIfPresentGetDocument(KakaoMapResponse response, String address) {
        if (response == null || CollectionUtils.isEmpty(response.getDocuments())) {
            log.error("KAKAO 지도 API 호출에 대한 응답이 없습니다. 주소 -> {}", address);
            throw new BadRequest("KAKAO 지도 API 호출에 대한 응답이 없습니다.");
        }
        return response.getDocuments().stream()
                .filter(e -> ValidUtils.isSimilarBetweenText(address, e.getAddress_name(), 0.45))
                .findAny()
                .orElseThrow(() -> {
                    log.error("KAKAO 지도 API 응답 중 일치하는 매장 주소가 없습니다.\n요청 주소 -> {}", address);
                    return new BadRequest("KAKAO 지도 API 응답 중 해당 매장과의 주소가 일치하지 않습니다. 요청 주소 -> " + address);
                });
    }

}
