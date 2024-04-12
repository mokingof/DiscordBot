package org.example.discordbot.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PingCommand implements BotCommand {
    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        event.getChannel().sendMessage("Pong!").queue();
    }

    @Override
    public String getHelp() {
        return "Type !ping - Replies with 'Pong!'";
    }
}
