package com.tg.investbot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.invest.openapi.OpenApi;
import ru.tinkoff.invest.openapi.okhttp.OkHttpOpenApi;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenApi openApi() {
        return new OkHttpOpenApi(System.getenv("ssoToken"), true);
    }
}
