package com.tg.investbot.command;

/**
 * TODO javadoc
 *
 * @since 24.11.2023
 */
public interface UserCommand <CommandDataT extends CommandData> {
     void execute(CommandDataT commandData);
}
