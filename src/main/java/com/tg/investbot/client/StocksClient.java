package com.tg.investbot.client;

import com.tg.investbot.model.PriceInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "stocksClient", url = "https://api.twelvedata.com")
public interface StocksClient {
    @GetMapping(value = "/price?symbol={ticker}&apikey={apiKey}&source=docs", consumes = "application/json")
    PriceInfo getPrice(@PathVariable("ticker") String ticker, @PathVariable("apiKey") String apiKey);
}
