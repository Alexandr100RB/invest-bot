package com.tg.investbot.command.start;

import com.tg.investbot.bot.InvestBot;
import com.tg.investbot.command.UserCommand;
import com.tg.investbot.registry.UserCommandName;
import com.tg.investbot.model.StockInfo;
import com.tg.investbot.repository.StocksInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.tg.investbot.registry.Registry.COMMAND_REGISTRY;

/**
 * Команда старт {@link UserCommandName#START}
 *
 * @since 25.11.2023
 */
@Component
public class StartCommand implements UserCommand {
    private final InvestBot investBot;
    private final StocksInfoRepository stocksInfoRepository;

    @Autowired
    public StartCommand(InvestBot investBot,
                        StocksInfoRepository stocksInfoRepository) {
        this.investBot = investBot;
        this.stocksInfoRepository = stocksInfoRepository;
        COMMAND_REGISTRY.put(UserCommandName.START, this);
    }


    @Override
    public void execute(long chatId, String message) {
        List<StockInfo> stocksList = stocksInfoRepository.findAll();
        if (stocksList.isEmpty()) {
            investBot.sendMessage(chatId, "Список пуст");
            return;
        }
        for (StockInfo stock: stocksList) {
            investBot.sendMessage(chatId, "Куплено " + stock.getQuantity()
                    + " шт " + stock.getTicker()+ " по " + stock.getBuyPrice());
        }
    }
}
