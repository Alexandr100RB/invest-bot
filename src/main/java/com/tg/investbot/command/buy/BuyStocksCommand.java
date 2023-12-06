package com.tg.investbot.command.buy;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.Config;
import com.crazzyghost.alphavantage.parameters.Interval;
import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;
import com.tg.investbot.bot.InvestBot;
import com.tg.investbot.command.UserCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    @Autowired
    public BuyStocksCommand(InvestBot investBot) {
        this.investBot = investBot;
        COMMAND_REGISTRY.put(PURCHASE, this);
    }

    @Override
    public void execute(long chatId, String message) {
        Config cfg = Config.builder()
                .key(System.getenv("stockToken"))
                .timeOut(10)
                .build();
        AlphaVantage.api().init(cfg);
        AlphaVantage.api()
                .timeSeries()
                .intraday()
                .forSymbol(getTicker(message))
                .interval(Interval.FIVE_MIN)
                .outputSize(OutputSize.FULL)
                .onSuccess(e-> handleSuccess((TimeSeriesResponse) e, chatId))
                .onFailure(e-> investBot.sendMessage(chatId, "Invalid ticker " + e))
                .fetch();
    }

    public void handleSuccess(TimeSeriesResponse response, long chatId) {
        var stockUnit = response.getStockUnits().stream().findFirst();
        stockUnit.ifPresent(
                unit -> investBot.sendMessage(chatId,
                        "Price: " + unit.getClose()
                                + "\nDate: " + unit.getDate()
                )
        );
    }
}
