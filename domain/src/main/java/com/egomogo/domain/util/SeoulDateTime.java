package com.egomogo.domain.util;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class SeoulDateTime {

    /**
     * 현재 시간을 Asia/Seoul 기준으로 반환해주는 유틸리티 메소드.
     * @return Asia/Seoul 기준 LocalDateTime 현재 시간 객체
     */
    public static LocalDateTime now() {
        return LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }

}
