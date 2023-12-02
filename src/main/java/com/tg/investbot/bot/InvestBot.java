package com.tg.investbot.bot;

import com.tg.investbot.command.registry.UserCommandName;
import com.tg.investbot.config.BotConfig;
import com.tg.investbot.model.Buttons;
import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.tg.investbot.command.registry.Registry.COMMAND_REGISTRY;

/**
 * TODO javadoc
 *
 * @since 24.11.2023
 */
@Component
public class InvestBot extends TelegramLongPollingBot {
    private static final Logger log = LoggerFactory.getLogger(InvestBot.class);
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
        if(update.hasMessage() && update.getMessage().hasText()){
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            var parameters = messageText.split(" ");
            try {
                UserCommandName commandName = UserCommandName.byCode(parameters[0]).orElseThrow();
                COMMAND_REGISTRY.get(commandName).execute(chatId, messageText);
            } catch (RuntimeException | TelegramApiException e) {
                log.error("Error while message sending: error=", e);
                sendMessage(chatId, "Что-то пошло не так, попробуйте ввести команду снова");
            }
        }
    }

    public void sendMessage(long chatId, String textMessage) {
        try {
            execute(SendMessage.builder()
                    .text(textMessage)
                    .chatId(chatId)
                    .replyMarkup(Buttons.replyMarkup())
                    .build()
            );
        } catch (TelegramApiException e) {
            log.error("Error while message sending: error=%s", e);
        }
    }
}