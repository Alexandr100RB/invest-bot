package com.tg.investbot.command.purchase;

import com.tg.investbot.bot.InvestBot;
import com.tg.investbot.command.UserCommand;
import com.tg.investbot.model.Buttons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.tg.investbot.command.registry.Registry.registry;
import static com.tg.investbot.command.registry.UserCommandName.PURCHASE;

/**
 * TODO javadoc
 *
 * @author Alexey Chuyko (aachuyko@yoomoney.ru)
 * @since 24.11.2023
 */
@Component
public class PurchaseStocksCommand implements UserCommand<PurchaseStocksData> {
    private final InvestBot investBot;

    @Autowired
    public PurchaseStocksCommand(InvestBot investBot) {
        this.investBot = investBot;
        registry.put(PURCHASE, this);
    }

    @Override
    public void execute(PurchaseStocksData commandData) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);
        sendMessage.setReplyMarkup(Buttons.inlineMarkup());
        try {
            investBot.execute(sendMessage);
        } catch (TelegramApiException e) {

        }
        investBot.execute()
    }
}
