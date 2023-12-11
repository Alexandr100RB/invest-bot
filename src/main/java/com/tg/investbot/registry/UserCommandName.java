package com.tg.investbot.registry;

import java.util.Arrays;
import java.util.Optional;

/**
 * TODO javadoc
 *
 * @since 24.11.2023
 */
public enum UserCommandName {
    START("/start"),
    GET("/get"),
    PURCHASE("/buy"),
    HELP("/help");

    private final String code;

    UserCommandName(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static Optional<UserCommandName> byCode(String code) {
        return Arrays.stream(values())
                .filter(value -> value.code.equals(code))
                .findFirst();
    }
}
