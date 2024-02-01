package com.tg.investbot.command.start;

import com.tg.investbot.bot.InvestBot;
import com.tg.investbot.command.UserCommand;
import com.tg.investbot.model.StockInfo;
import com.tg.investbot.registry.UserCommandName;
import com.tg.investbot.repository.StocksInfoRepository;
import com.tg.investbot.service.TinkoffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.tinkoff.piapi.contract.v1.*;
import ru.tinkoff.piapi.core.InvestApi;
import ru.tinkoff.piapi.core.SandboxService;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    private final TinkoffService tinkoffService;

    @Autowired
    public StartCommand(InvestBot investBot,
                        StocksInfoRepository stocksInfoRepository,
                        InvestApi investApi,
                        TinkoffService tinkoffService) {
        this.investBot = investBot;
        this.stocksInfoRepository = stocksInfoRepository;
        this.investApi = investApi;
        this.tinkoffService = tinkoffService;
        COMMAND_REGISTRY.put(UserCommandName.START, this);
    }


    @Override
    public void execute(long chatId, String message) {
        List<StockInfo> stocksList = stocksInfoRepository.findAll();
        if (stocksList.isEmpty()) {
            investBot.sendMessage(chatId, "Список пуст");
            return;
        }
//        for (StockInfo stock: stocksList) {
//            investBot.sendMessage(chatId, "Куплено " + stock.getQuantity()
//                    + " шт " + stock.getTicker()+ " по " + stock.getBuyPrice());
//        }
        Optional<Share> sber = tinkoffService.getShareByTicker("YNDX");
        var candles = investApi.getMarketDataService()
                .getCandlesSync(
                        sber.get().getFigi(),
                        Instant.now().minusSeconds(300),
                        Instant.now(),
                        CANDLE_INTERVAL_1_MIN);
        //investBot.sendMessage(159776036, candles.toString());
        SandboxService sandboxService = investApi.getSandboxService();
        //String acc = sandboxService.openAccountSync();
        String acc = "accNumber";
        //investBot.sendMessage(159776036, acc);
        //MoneyValue balance = sandboxService.payInSync(acc, MoneyValue.newBuilder().setUnits(20000).setCurrency("4217").build());
        //investBot.sendMessage(159776036, balance.toString());


//        sandboxService.postOrderSync(sber.get().getFigi(), 2,
//                Quotation.newBuilder().setUnits(3043).build(), OrderDirection.ORDER_DIRECTION_BUY,
//                acc, OrderType.ORDER_TYPE_LIMIT, UUID.randomUUID().toString());
        PortfolioResponse myShares = sandboxService.getPortfolioSync(acc);
        investBot.sendMessage(159776036, String.valueOf(myShares));

        //investBot.sendMessage(159776036, sandboxService.getOrdersSync(acc).toString());

        //sandboxService.getOperationsSync(acc, Instant.now().minusSeconds(6000), );
    }
}
