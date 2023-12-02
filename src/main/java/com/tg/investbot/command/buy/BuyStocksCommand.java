package com.tg.investbot.command.buy;

import com.tg.investbot.bot.InvestBot;
import com.tg.investbot.command.UserCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.tinkoff.invest.openapi.OpenApi;
import ru.tinkoff.invest.openapi.model.rest.MarketInstrument;
import ru.tinkoff.invest.openapi.model.rest.MarketInstrumentList;

import java.util.concurrent.CompletableFuture;

import static com.tg.investbot.helper.CommandHelper.getTicker;
import static com.tg.investbot.registry.Registry.COMMAND_REGISTRY;
import static com.tg.investbot.registry.UserCommandName.PURCHASE;

/**
 * TODO javadoc
 *
 * @since 24.11.2023
 */
@Component
public class BuyStocksCommand implements UserCommand {
    private static final Logger log = LoggerFactory.getLogger(BuyStocksCommand.class);
    private final InvestBot investBot;
    private final OpenApi openApi;

    @Autowired
    public BuyStocksCommand(InvestBot investBot, OpenApi openApi) {
        this.investBot = investBot;
        this.openApi = openApi;
        COMMAND_REGISTRY.put(PURCHASE, this);
    }

    public CompletableFuture<MarketInstrumentList> getMarketInstrumentTicker(String ticker) {
        log.info("Getting {} from Tinkoff", ticker);
        var context = openApi.getMarketContext();
        return context.searchMarketInstrumentsByTicker(ticker);
    }

    @Override
    public void execute(long chatId, String message) {
        log.info("context: token={}", openApi.getAuthToken());
        log.info("check context: token={}", getTicker(message));
        var stocksCF = getMarketInstrumentTicker(getTicker(message));
        log.info("Get stock info: ticker={}", getMarketInstrumentTicker(getTicker(message)));
        var stocks = stocksCF.join();
        log.info("Get stock info: ticker={}", stocks.getInstruments());
        for (MarketInstrument item : stocks.getInstruments()) {
            investBot.sendMessage(chatId, item.getName() + ": " + item.getCurrency().getValue());
        }
    }
}
