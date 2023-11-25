package com.tg.investbot.bot;

import com.tg.investbot.config.BotConfig;
import com.tg.investbot.model.Buttons;
import com.tg.investbot.model.CurrencyModel;
import com.tg.investbot.service.CurrencyService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.text.ParseException;

/**
 * TODO javadoc
 *
 * @since 24.11.2023
 */
@Component
public class InvestBot extends TelegramLongPollingBot {
    private final BotConfig botConfig;

    public InvestBot(BotConfig botConfig) {
        this.botConfig = botConfig;
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        CurrencyModel currencyModel = new CurrencyModel();
        String currency = "";

        if(update.hasMessage() && update.getMessage().hasText()){
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (messageText) {
                case "/start":
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;
                default:
                    try {
                        currency = CurrencyService.getCurrencyRate(messageText, currencyModel);
                    } catch (IOException e) {
                        sendMessage(chatId, "We have not found such a currency." + "\n" +
                                "Enter the currency whose official exchange rate" + "\n" +
                                "you want to know in relation to BYN." + "\n" +
                                "For example: USD");
                    } catch (ParseException e) {
                        throw new RuntimeException("Unable to parse date");
                    }
                    sendMessage(chatId, currency);
            }
        }

    }

    private void startCommandReceived(Long chatId, String name) {
        String answer = "Hi, " + name + ", nice to meet you!" + "\n" +
                "Enter the currency whose official exchange rate" + "\n" +
                "you want to know in relation to BYN." + "\n" +
                "For example: USD";
        sendMessage(chatId, answer);
    }

    private void sendMessage(Long chatId, String textToSend){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);
        sendMessage.setReplyMarkup(Buttons.inlineMarkup());
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {

        }
    }
}