package com.tg.investbot.command;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * TODO javadoc
 *
 * @since 24.11.2023
 */
public interface UserCommand {
     void execute(long chatId, String message) throws TelegramApiException;
}
