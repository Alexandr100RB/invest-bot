package com.tg.investbot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Конфигурация бота
 *
 * @author Alexey Chuyko (aachuyko@yoomoney.ru)
 * @since 24.11.2023
 */
@Configuration
@PropertySource("classpath:/application.properties")
public class BotConfig {
    @Value("${bot.name}")
    private String botName;
    @Value("${bot.token}")
    private String token;

    public String getBotName() {
        return botName;
    }

    public String getToken() {
        return token;
    }
}