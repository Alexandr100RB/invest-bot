package com.tg.investbot.command.start;

import com.tg.investbot.bot.InvestBot;
import com.tg.investbot.command.UserCommand;
import com.tg.investbot.command.registry.UserCommandName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.tg.investbot.command.registry.Registry.COMMAND_REGISTRY;

/**
 * TODO javadoc
 *
 * @since 25.11.2023
 */
@Component
public class StartCommand implements UserCommand {
    private final InvestBot investBot;

    @Autowired
    public StartCommand(InvestBot investBot) {
        this.investBot = investBot;
        COMMAND_REGISTRY.put(UserCommandName.START, this);
    }

    @Override
    public void execute(long chatId, String message) {
        investBot.sendMessage(chatId, "TEST WORKED");
    }
}
