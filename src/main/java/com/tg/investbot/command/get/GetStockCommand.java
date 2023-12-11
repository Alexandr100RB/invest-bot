package com.tg.investbot.command.get;

import com.tg.investbot.bot.InvestBot;
import com.tg.investbot.command.UserCommand;
import com.tg.investbot.model.StockPrice;
import com.tg.investbot.model.StockInfo;
import com.tg.investbot.registry.UserCommandName;
import com.tg.investbot.repository.StocksInfoRepository;
import com.tg.investbot.service.TinkoffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.tg.investbot.registry.Registry.COMMAND_REGISTRY;

@Component
public class GetStockCommand implements UserCommand {

    private final InvestBot investBot;
    private final StocksInfoRepository stocksInfoRepository;
    private final TinkoffService tinkoffService;

    @Autowired
    public GetStockCommand(InvestBot investBot,
                           StocksInfoRepository stocksInfoRepository,
                           TinkoffService tinkoffService) {
        this.investBot = investBot;
        this.tinkoffService = tinkoffService;
        this.stocksInfoRepository = stocksInfoRepository;
        COMMAND_REGISTRY.put(UserCommandName.GET, this);
    }

    @Override
    public void execute(long chatId, String message) {
        List<StockInfo> stocksList = stocksInfoRepository.findStocksInfosByChatId(chatId);
        if (stocksList.isEmpty()) {
            investBot.sendMessage(chatId, "Список пуст");
            return;
        }
        var stockPrices = stocksInfoRepository.countAverageSumByTicker(chatId);
        StringBuilder responseMessage = new StringBuilder();
        for (StockPrice stockPrice: stockPrices) {
            var price = tinkoffService.getPriceByTicker(stockPrice.getTicker());
            if (price.isEmpty()) {
                return;
            }
            responseMessage.append("Куплено " + stockPrice.getCount()
                    + " шт " + stockPrice.getTicker()
                    + " по средней цене " + stockPrice.getAvgPrice() +
                    ", сейчас цена " + price.get() + ".\n");
        }

        investBot.sendMessage(chatId, responseMessage.toString());
    }
}
