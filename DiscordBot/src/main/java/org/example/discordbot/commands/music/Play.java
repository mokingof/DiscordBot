package org.example.discordbot.commands.music;

import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.example.discordbot.commands.BotCommand;
import org.example.discordbot.lavaplayer.PlayerManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Play implements BotCommand {
    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        Member member = event.getMember();
        GuildVoiceState memberVoiceState = member.getVoiceState();

        if (!memberVoiceState.inAudioChannel()) {
            event.getChannel().sendMessage("You need to be in a channel").queue();
            return;
        }
        Member self = event.getGuild().getSelfMember();
        GuildVoiceState selfVoiceState = self.getVoiceState();

        String url = event.getMessage().getContentRaw().substring("!play".length()).trim();

        Pattern pattern = Pattern.compile("https://");
        Matcher matcher = pattern.matcher(url);


        if (!selfVoiceState.inAudioChannel() && matcher.find()) {
            event.getGuild().getAudioManager().openAudioConnection(memberVoiceState.getChannel());
            PlayerManager playerManager = PlayerManager.getInstance();
            event.getChannel().sendMessage("Playing ").queue();
            playerManager.play(event.getGuild(), url);
        } else {
            if (!matcher.find()) {
                event.getChannel().sendMessage("Invalid link").queue();
            }else if (selfVoiceState.getChannel() != memberVoiceState.getChannel()) {
                event.getChannel().sendMessage("you need to be in the same voice channel").queue();

            }
        }

    }

    @Override
    public String getHelp() {
        return "play [url]";
    }


}