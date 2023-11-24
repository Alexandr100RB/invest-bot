package com.tg.investbot.command;

/**
 * TODO javadoc
 *
 * @author Alexey Chuyko (aachuyko@yoomoney.ru)
 * @since 24.11.2023
 */
public interface UserCommand <CommandDataT extends CommandData> {
     void execute(CommandDataT commandData);
}
