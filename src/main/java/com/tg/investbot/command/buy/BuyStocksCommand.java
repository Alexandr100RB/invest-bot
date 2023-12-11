package com.tg.investbot.command.buy;

import com.tg.investbot.bot.InvestBot;
import com.tg.investbot.command.UserCommand;
import com.tg.investbot.helper.CommandHelper;
import com.tg.investbot.model.StockInfo;
import com.tg.investbot.repository.StocksInfoRepository;
import com.tg.investbot.service.TinkoffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.tg.investbot.helper.CommandHelper.getTicker;
import static com.tg.investbot.registry.Registry.COMMAND_REGISTRY;
import static com.tg.investbot.registry.UserCommandName.PURCHASE;


@Component
public class BuyStocksCommand implements UserCommand {
    private final InvestBot investBot;
    private final StocksInfoRepository stocksInfoRepository;
    private final TinkoffService tinkoffService;

    @Autowired
    public BuyStocksCommand(InvestBot investBot,
                            StocksInfoRepository stocksInfoRepository,
                            TinkoffService tinkoffService) {
        this.investBot = investBot;
        this.stocksInfoRepository = stocksInfoRepository;
        this.tinkoffService = tinkoffService;
        COMMAND_REGISTRY.put(PURCHASE, this);
    }

    @Override
    public void execute(long chatId, String message) {
        String ticker = getTicker(message);
        var priceT = tinkoffService.getPriceByTicker(ticker);
        if (priceT.isEmpty()) {
            investBot.sendMessage(chatId, "Не удалось получить стоимость акции.");
        }

        Integer quantity = CommandHelper.getCount(message);
        stocksInfoRepository.save(
                StockInfo.StocksInfoBuilder.aStocksInfo()
                        .withBuyPrice(Double.valueOf(priceT.get()))
                        .withChatId(chatId)
                        .withQuantity(quantity)
                        .withTicker(ticker)
                        .build()
        );
        investBot.sendMessage(chatId, "Мы купили " + quantity + " акций "
                + ticker + " по " + priceT.get());
    }
}
