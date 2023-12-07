package com.tg.investbot.helper;

import com.tg.investbot.registry.UserCommandName;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CommandHelper {
    public static UserCommandName getCommandName(String message) {
        var parameters = message.split(" ");
        return UserCommandName.byCode(parameters[0]).orElseThrow();
    }

    public static String getTicker(String message) {
        return message.split(" ")[1];
    }

    public static Integer getCount(String message) {
        return Integer.parseInt(message.split(" ")[2]);
    }
}
