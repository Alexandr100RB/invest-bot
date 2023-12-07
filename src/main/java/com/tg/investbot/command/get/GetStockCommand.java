package com.tg.investbot.command.get;

import com.tg.investbot.command.UserCommand;
import com.tg.investbot.registry.Registry;
import com.tg.investbot.registry.UserCommandName;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.tg.investbot.registry.Registry.COMMAND_REGISTRY;

public class GetStockCommand implements UserCommand {
    public GetStockCommand() {
        COMMAND_REGISTRY.put(UserCommandName.GET, this);
    }

    @Override
    public void execute(long chatId, String message) throws TelegramApiException {

    }
}
