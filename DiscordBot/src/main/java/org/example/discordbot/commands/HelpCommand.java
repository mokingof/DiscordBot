package org.example.discordbot.commands;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.entities.MessageEmbed;
import java.awt.*;
import java.util.Map;

public class HelpCommand implements BotCommand{
    private final CommandHandler commandHandler;

    public HelpCommand(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.GREEN);
        embedBuilder.setTitle("Available Commands:");

        Map<String, BotCommand> commands = commandHandler.getCommands();
        for (Map.Entry<String, BotCommand> entry : commands.entrySet()) {
            String commandName = entry.getKey();
            BotCommand command = entry.getValue();
            embedBuilder.addField(commandName.toUpperCase(), command.getHelp(), false);
        }
        User user = event.getAuthor();
        user.openPrivateChannel().queue(channel -> {
            channel.sendMessageEmbeds(embedBuilder.build()).queue();
        });
     //   event.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();

    }

    @Override
    public String getHelp() {
        return "Type !help - Displays all available commands";
    }
}
