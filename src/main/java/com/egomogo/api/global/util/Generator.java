package com.egomogo.api.global.util;

import java.util.UUID;

public class Generator {

    /**
     * UUID를 생성하여 반환하는 메소드.
     * @return 생성된 UUID 문자열
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
