package com.tg.investbot.command.start;

import com.tg.investbot.bot.InvestBot;
import com.tg.investbot.command.UserCommand;
import com.tg.investbot.registry.UserCommandName;
import com.tg.investbot.model.StockInfo;
import com.tg.investbot.repository.StocksInfoRepository;
import org.hibernate.sql.results.graph.BiDirectionalFetch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.tinkoff.piapi.core.InvestApi;
import ru.tinkoff.piapi.core.MarketDataService;

import java.time.Instant;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.tg.investbot.registry.Registry.COMMAND_REGISTRY;
import static ru.tinkoff.piapi.contract.v1.CandleInterval.CANDLE_INTERVAL_1_MIN;

/**
 * Команда старт {@link UserCommandName#START}
 *
 * @since 25.11.2023
 */
@Component
public class StartCommand implements UserCommand {
    private final InvestBot investBot;
    private final StocksInfoRepository stocksInfoRepository;
    private final InvestApi investApi;

    @Autowired
    public StartCommand(InvestBot investBot,
                        StocksInfoRepository stocksInfoRepository,
                        InvestApi investApi) {
        this.investBot = investBot;
        this.stocksInfoRepository = stocksInfoRepository;
        this.investApi = investApi;
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
        var candles = investApi.getMarketDataService()
                .getCandles(
                        "BBG004730N88",
                        Instant.now(),
                        Instant.now().plusMillis(100000),
                        CANDLE_INTERVAL_1_MIN).join();
        investBot.sendMessage(159776036, candles.toString());
    }
}
