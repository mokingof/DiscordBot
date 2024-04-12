package org.example.discordbot.commands.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.example.discordbot.commands.BotCommand;
import org.example.discordbot.lavaplayer.GuildMusicManager;
import org.example.discordbot.lavaplayer.PlayerManager;
import org.example.discordbot.lavaplayer.TrackScheduler;

public class NowPlaying implements BotCommand {
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
       AudioTrackInfo info = guildMusicManager.getTrackScheduler().getPlayer().getPlayingTrack().getInfo();
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Currently Playing");
        embedBuilder.setDescription("**Name:** " + info.title);
        embedBuilder.appendDescription("\n**Author** " + info.author);
        embedBuilder.appendDescription("\n**URL** " + info.uri);
        event.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();



    }

    @Override
    public String getHelp() {
        return "Current Track";
    }
}
