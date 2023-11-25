package com.tg.investbot.command.registry;

import com.tg.investbot.command.UserCommand;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO javadoc
 *
 * @since 24.11.2023
 */
public class Registry {
    public static final Map<UserCommandName, UserCommand> registry;
    static {
        registry = new HashMap<>();
    }
}
