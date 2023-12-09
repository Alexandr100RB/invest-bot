package com.tg.investbot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.piapi.core.InvestApi;

/**
 * Конфигурация клиента tinkoff
 */
@Configuration
public class TinkoffConfiguration {
    @Bean
    public InvestApi investApi() {
        return  InvestApi.createReadonly(
                System.getenv("tinkoffToken")
        );
    }
}
