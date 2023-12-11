package com.tg.investbot.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Класс - результат группирующего запроса для вычисления средней цены
 */
public class StockPrice {
    private final String ticker;
    private final Long count;
    private final Double avgPrice;

    public StockPrice(String ticker, Long count, Double avgPrice) {
        this.ticker = ticker;
        this.count = count;
        this.avgPrice = avgPrice;
    }

    public String getTicker() {
        return ticker;
    }

    public Long getCount() {
        return count;
    }

    public BigDecimal getAvgPrice() {
        return BigDecimal.valueOf(avgPrice).setScale(2, RoundingMode.HALF_DOWN);
    }
}
