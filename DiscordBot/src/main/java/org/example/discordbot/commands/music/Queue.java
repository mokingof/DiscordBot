package org.example.discordbot.commands.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.example.discordbot.commands.BotCommand;
import org.example.discordbot.lavaplayer.GuildMusicManager;
import org.example.discordbot.lavaplayer.PlayerManager;

import java.util.ArrayList;
import java.util.List;

public class Queue implements BotCommand {
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

        if (!selfVoiceState.inAudioChannel()) {
            event.getChannel().sendMessage("I am not in an audio channel").queue();
        }

        if (selfVoiceState.getChannel() != memberVoiceState.getChannel()) {
            event.getChannel().sendMessage("you are not in the same channel as me").queue();
        }
        GuildMusicManager guildMusicManager = PlayerManager.getInstance().getGuildMusicManager(event.getGuild());
        List<AudioTrack> queue = new ArrayList<>(guildMusicManager.getTrackScheduler().getQueue());
        EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setTitle("Current Queue");
        if (queue.isEmpty()) {
            embedBuilder.setDescription("Queue empty.");
        }
        for (int i = 0; i < queue.size(); i++) {
            AudioTrackInfo info = queue.get(i).getInfo();
            embedBuilder.addField(i + 1 + ":", info.title, false);
        }

        event.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();

    }

    @Override
    public String getHelp() {
        return "Track in queue";
    }
}
