package com.tg.investbot.command.buy;

import com.tg.investbot.bot.InvestBot;
import com.tg.investbot.command.UserCommand;
import com.tg.investbot.model.Buttons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.tg.investbot.command.registry.Registry.COMMAND_REGISTRY;
import static com.tg.investbot.command.registry.UserCommandName.PURCHASE;

/**
 * TODO javadoc
 *
 * @since 24.11.2023
 */
@Component
public class BuyStocksCommand implements UserCommand {
    private final InvestBot investBot;

    @Autowired
    public BuyStocksCommand(InvestBot investBot) {
        this.investBot = investBot;
        COMMAND_REGISTRY.put(PURCHASE, this);
    }

    @Override
    public void execute(long chatId, String message) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(message);
        sendMessage.setReplyMarkup(Buttons.inlineMarkup());
        try {
            investBot.execute(sendMessage);
        } catch (TelegramApiException e) {

        }
        investBot.execute(sendMessage);
    }
}
