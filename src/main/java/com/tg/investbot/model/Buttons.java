package com.tg.investbot.model;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO javadoc
 *
 * @since 24.11.2023
 */
public class Buttons {
    private static final KeyboardButton START_BUTTON = new KeyboardButton("/start");

    public static ReplyKeyboardMarkup inlineMarkup() {
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(START_BUTTON);
        keyboard.add(keyboardFirstRow);

        ReplyKeyboardMarkup markupInline = new ReplyKeyboardMarkup();
        markupInline.setKeyboard(keyboard);

        return markupInline;
    }
}