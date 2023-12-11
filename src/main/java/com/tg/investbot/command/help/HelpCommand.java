package com.tg.investbot.command.help;

import com.tg.investbot.bot.InvestBot;
import com.tg.investbot.command.UserCommand;
import com.tg.investbot.command.start.StartCommand;
import com.tg.investbot.registry.UserCommandName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.tg.investbot.registry.Registry.COMMAND_REGISTRY;

/**
 * TODO javadoc
 *
 * @since 25.11.2023
 */
@Component
public class HelpCommand implements UserCommand {
    private final InvestBot investBot;

    @Autowired
    public HelpCommand(InvestBot investBot) {
        this.investBot = investBot;
        COMMAND_REGISTRY.put(UserCommandName.HELP, this);
    }


    @Override
    public void execute(long chatId, String message) {
        investBot.sendMessage(chatId, "Вы можете нажать кнопку /start, чтобы получить купленные " +
                "всеми акции или нажать /get, чтобы получить только свои акции. \n Для покупки акции введите " +
                "команду /buy, затем тикер и " +
                "количество акций, например /buy AAPL 12. \n Для продажи акции введите команду /sell, затем " +
                "тикер и количество акций, например /sell MSFT 21.");
    }
}
