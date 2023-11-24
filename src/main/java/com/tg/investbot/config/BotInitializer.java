package com.tg.investbot.config;

import com.tg.investbot.bot.InvestBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * TODO javadoc
 *
 * @author Alexey Chuyko (aachuyko@yoomoney.ru)
 * @since 24.11.2023
 */
@Component
public class BotInitializer {
    private final InvestBot investBot;
    @Autowired
    public BotInitializer(InvestBot investBot) {
        this.investBot = investBot;
    }

    @EventListener({ContextRefreshedEvent.class})
    public void init()throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try{
            telegramBotsApi.registerBot(investBot);
        } catch (TelegramApiException ignored) {

        }
    }
}