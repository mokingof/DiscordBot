package org.example.discordbot.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface BotCommand {

    void execute(MessageReceivedEvent event, String[] args);
    String getHelp();
}
