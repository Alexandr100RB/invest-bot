package com.tg.investbot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.Share;
import ru.tinkoff.piapi.core.InvestApi;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с tinkoff api
 */
@Service
public class TinkoffService {
    private static final Logger log = LoggerFactory.getLogger(TinkoffService.class);
    private final InvestApi investApi;
    private final List<Share> shareList;

    @Autowired
    public TinkoffService(InvestApi investApi) {
        this.investApi = investApi;
        this.shareList = investApi.getInstrumentsService().getTradableSharesSync();
    }

    public Optional<String> getPriceByTicker(String ticker) {
        log.info("Start tinkoff api process: ticker={}", ticker);
        var shareByTicker = shareList.stream()
                .filter(share -> ticker.equals(share.getTicker()))
                .findFirst();
        if (shareByTicker.isEmpty()) {
            log.info("Not tradable stock: stock={}", ticker);
            return Optional.empty();
        }
        log.info("Stock found: ticker={}, shareByTicker={}", ticker, shareByTicker.get());
        var figi = shareByTicker.get().getFigi();
        var lastPrices = investApi.getMarketDataService().getLastPrices(List.of(figi)).join();
        log.info("Last prices: lastPrices={}", lastPrices);
        if (lastPrices.isEmpty()) {
            return Optional.empty();
        }
        var lastPrice = lastPrices.get(lastPrices.size() - 1);
        String cents = String.valueOf(lastPrice.getPrice().getNano()).substring(0, 2);
        String finalPrice = lastPrice.getPrice().getUnits() + "." +  cents;
        return Optional.of(finalPrice);
    }

    public Optional<String> getCurrencyByTicker(String ticker) {
        log.info("Start tinkoff api process: ticker={}", ticker);
        var shareByTicker = shareList.stream()
                .filter(share -> ticker.equals(share.getTicker()))
                .findFirst();
        if (shareByTicker.isEmpty()) {
            log.info("Not tradable stock: stock={}", ticker);
            return Optional.empty();
        }

        return Optional.of(shareByTicker.get().getCurrency());
    }
}
