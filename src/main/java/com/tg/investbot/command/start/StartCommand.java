package com.tg.investbot.command.start;

import com.tg.investbot.bot.InvestBot;
import com.tg.investbot.command.UserCommand;
import com.tg.investbot.command.registry.UserCommandName;
import com.tg.investbot.repository.StocksInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static com.tg.investbot.command.registry.Registry.COMMAND_REGISTRY;

/**
 * TODO javadoc
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
        investBot.sendMessage(chatId, stocksInfoRepository.findAll().toString());
    }
}
