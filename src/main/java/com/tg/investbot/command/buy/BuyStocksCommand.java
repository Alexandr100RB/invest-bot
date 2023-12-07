package com.tg.investbot.command.buy;

import com.tg.investbot.bot.InvestBot;
import com.tg.investbot.client.StocksClient;
import com.tg.investbot.command.UserCommand;
import com.tg.investbot.helper.CommandHelper;
import com.tg.investbot.model.PriceInfo;
import com.tg.investbot.model.StocksInfo;
import com.tg.investbot.repository.StocksInfoRepository;
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
    private final StocksInfoRepository stocksInfoRepository;



    @Autowired
    public BuyStocksCommand(InvestBot investBot,
                            StocksClient stocksClient,
                            StocksInfoRepository stocksInfoRepository) {
        this.investBot = investBot;
        this.stocksClient = stocksClient;
        this.stocksInfoRepository = stocksInfoRepository;
        COMMAND_REGISTRY.put(PURCHASE, this);
    }

    @Override
    public void execute(long chatId, String message) {
        String ticker = getTicker(message);
        PriceInfo response = stocksClient.getPrice(
                ticker, System.getenv("twelvedataToken")
        );
        log.info("response={}", response.toString());
        Integer quantity = CommandHelper.getCount(message);
        Double price = Double.parseDouble(response.getPrice());
        stocksInfoRepository.save(
                StocksInfo.StocksInfoBuilder.aStocksInfo()
                        .withBuyPrice(price)
                        .withChatId(chatId)
                        .withQuantity(quantity)
                        .withTicker(ticker)
                        .build()
        );
        investBot.sendMessage(chatId, "Мы купили " + quantity + " акций "
                + ticker + " по " + price);
    }
}
