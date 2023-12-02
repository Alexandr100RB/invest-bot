package com.tg.investbot.model;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
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
    private static final KeyboardButton MY_STOCKS_BUTTON = new KeyboardButton("/my_stocks");
    private static final KeyboardButton BUY_BUTTON = new KeyboardButton("/buy SBER");
    private static final KeyboardButton SELL_BUTTON = new KeyboardButton("/sell");

    public static ReplyKeyboardMarkup replyMarkup() {
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(START_BUTTON);
        keyboardFirstRow.add(MY_STOCKS_BUTTON);
        keyboard.add(keyboardFirstRow);

        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(BUY_BUTTON);
        keyboardSecondRow.add(SELL_BUTTON);
        keyboard.add(keyboardSecondRow);

        return ReplyKeyboardMarkup.builder()
                .keyboard(keyboard)
                .selective(true)
                .resizeKeyboard(true)
                .oneTimeKeyboard(false)
                .build();
    }
}