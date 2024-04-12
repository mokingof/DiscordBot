package org.example.discordbot.commands;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BullyCommand implements BotCommand {
    private final String[] insultTemplates = {
            "Hey %s, did it hurt when you fell from your mother's nest?",
            "I bet %s's family tree is a cactus. Because everyone on it is a prick.",
            "%s, if brains were dynamite, you wouldn't have enough to blow your nose.",
            "%s is the reason the gene pool needs a lifeguard.",
            "I'd like to see things from %s's point of view, but I can't seem to get my head that far up my ass.",
            "%s, your face makes onions cry.",
            "I've seen more coordination in a group of toddlers than in %s's basketball game.",
            "%s, your shooting accuracy is about as reliable as a broken compass.",
            "Watching %s play basketball is like watching a train wreck in slow motion.",
            "%s, even Shaq could shoot better free throws than you, and that's saying something.",
            "Hey %s, I've seen better basketball skills from a tree.",
            "%s, your basketball game is about as impressive as a layup from a five-year-old.",
            "%s, if you were any more inbred, you'd be a sandwich.",
            "I've seen more attractive faces on the back of a $5 bill than %s's.",
            "%s, were you born this stupid, or did you have to work at it?",
            "%s is the reason the aliens won't talk to us.",
            "I'd tell %s to go outside and play, but they'd probably bring down property values.",
            "%s, even if I agreed with you, we'd both be wrong.",
            "%s is proof that evolution can go in reverse.", "%s, you're the reason the middle finger was invented.",
            "I'd call %s a tool, but that would imply they're useful.",
            "Do the world a favor, %s, and don't reproduce.",
            "%s's face is the reason why birth control exists.",
            "%s, I bet your parents dressed you as a mistake for Halloween.",
            "%s's IQ is lower than their shoe size."
    };

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        User mentionedUser = event.getMessage().getMentions().getUsers().get(0);
        if (mentionedUser != null) {
            String insult = generateRandomInsult(mentionedUser);
            event.getChannel().sendMessage(insult).queue();
        } else {
            event.getChannel().sendMessage("You need to mention someone to bully!").queue();
        }
    }

    private String generateRandomInsult(User mentionedUser) {
        Random random = new Random();
        String insultTemplate = insultTemplates[random.nextInt(insultTemplates.length)];
        // Replace placeholders (%s) with the mentioned user's name
        return String.format(insultTemplate, mentionedUser.getAsMention(), mentionedUser.getAsMention());
    }

    @Override
    public String getHelp() {
        return "Type: !bully @[user] - Generates an insult directed at the mentioned user.";
    }
}
