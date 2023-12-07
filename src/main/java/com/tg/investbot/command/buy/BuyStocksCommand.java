package com.tg.investbot.command.buy;

import com.tg.investbot.bot.InvestBot;
import com.tg.investbot.client.StocksClient;
import com.tg.investbot.command.UserCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.tg.investbot.helper.CommandHelper.getTicker;
import static com.tg.investbot.registry.Registry.COMMAND_REGISTRY;
import static com.tg.investbot.registry.UserCommandName.PURCHASE;


@Component
public class BuyStocksCommand implements UserCommand {
    private static final Logger log = LoggerFactory.getLogger(BuyStocksCommand.class);
    private final InvestBot investBot;
    private final StocksClient stocksClient;

    @Autowired
    public BuyStocksCommand(InvestBot investBot,
                            StocksClient stocksClient) {
        this.investBot = investBot;
        this.stocksClient = stocksClient;
        COMMAND_REGISTRY.put(PURCHASE, this);
    }

    @Override
    public void execute(long chatId, String message) {
        var response = stocksClient.getPrice(
                getTicker(message), System.getenv("twelvedataToken")
        );
        var price = response;
        log.info("response={}", price.toString());
        investBot.sendMessage(chatId, price.getPrice());
    }
}
