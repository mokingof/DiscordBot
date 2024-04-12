package org.example.discordbot.listeners;


import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.example.discordbot.commands.CommandHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;


public class BotEventListener extends ListenerAdapter {
    private final CommandHandler commandHandler;

    public BotEventListener(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;

    }
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return; // Ignore messages from other bots

        String message = event.getMessage().getContentRaw();
        if (message.startsWith("!")) {
            String[] args = message.substring(1).split("\\s+");
            commandHandler.handleCommand(event, args[0], args.length > 1 ? args : new String[0]);
        }
    }

}
