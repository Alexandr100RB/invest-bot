package com.tg.investbot.command.get;

import com.tg.investbot.bot.InvestBot;
import com.tg.investbot.client.StocksClient;
import com.tg.investbot.command.UserCommand;
import com.tg.investbot.command.buy.BuyStocksCommand;
import com.tg.investbot.helper.CommandHelper;
import com.tg.investbot.model.PriceInfo;
import com.tg.investbot.model.StocksInfo;
import com.tg.investbot.registry.Registry;
import com.tg.investbot.registry.UserCommandName;
import com.tg.investbot.repository.StocksInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

import static com.tg.investbot.helper.CommandHelper.getTicker;
import static com.tg.investbot.registry.Registry.COMMAND_REGISTRY;

@Component
public class GetStockCommand implements UserCommand {

    private final InvestBot investBot;
    private final StocksClient stocksClient;
    private final StocksInfoRepository stocksInfoRepository;

    @Autowired
    public GetStockCommand(InvestBot investBot,
                           StocksClient stocksClient,
                           StocksInfoRepository stocksInfoRepository) {
        this.investBot = investBot;
        this.stocksClient = stocksClient;
        this.stocksInfoRepository = stocksInfoRepository;
        COMMAND_REGISTRY.put(UserCommandName.GET, this);
    }

    @Override
    public void execute(long chatId, String message) {
        List<StocksInfo> stocksList = stocksInfoRepository.findStocksInfosByChatId(chatId);
        if (stocksList.isEmpty()) {
            investBot.sendMessage(chatId, "Список пуст");
            return;
        }
        for (StocksInfo stock: stocksList) {
            PriceInfo response = stocksClient.getPrice(
                    stock.getTicker(), System.getenv("twelvedataToken")
            );
            investBot.sendMessage(chatId, "Куплено " + stock.getQuantity()
                    + " шт " + stock.getTicker()+ " по " + stock.getBuyPrice() +
                    ", сейчас цена " + response.getPrice());
        }
    }
}
