package com.tg.investbot.helper;

import com.tg.investbot.registry.UserCommandName;

public class CommandHelper {
    public static UserCommandName getCommandName(String message) {
        var parameters = message.split(" ");
        return UserCommandName.byCode(parameters[0]).orElseThrow();
    }

    public static String getTicker(String message) {
        return message.split(" ")[1];
    }

    public static Long getCount(String message) {
        return Long.parseLong(message.split(" ")[2]);
    }
}
