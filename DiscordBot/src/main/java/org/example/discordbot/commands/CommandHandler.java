package org.example.discordbot.commands;


import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.example.discordbot.commands.music.*;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler {
    private final Map<String, BotCommand> commands;

    public CommandHandler() {
        this.commands = new HashMap<>();
        // Register commands
        commands.put("ping", new PingCommand());
        commands.put("help", new HelpCommand(this));
        commands.put("bully", new BullyCommand());
        commands.put("play", new Play());
        commands.put("skip", new skip());
        commands.put("pause", new Pause());
        commands.put("resume", new Resume());
        commands.put("clear", new Clear());
        commands.put("nowPlaying", new NowPlaying());
        commands.put("queue", new Queue());
        commands.put("repeat", new Repeat());
         //Add more commands here as needed
    }

    public void handleCommand(MessageReceivedEvent event, String command, String[] args) {
        BotCommand cmd = commands.get(command);
        if (cmd != null) {
            cmd.execute(event, args);
        } else {
            event.getChannel().sendMessage("Incorrect command type **!help** for a list of Commands").queue();
        }

    }
    public Map<String, BotCommand> getCommands() {
        return commands;
    }
}
