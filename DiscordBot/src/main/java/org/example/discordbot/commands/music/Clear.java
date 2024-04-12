package org.example.discordbot.commands.music;

import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.example.discordbot.commands.BotCommand;
import org.example.discordbot.lavaplayer.GuildMusicManager;
import org.example.discordbot.lavaplayer.PlayerManager;
import org.example.discordbot.lavaplayer.TrackScheduler;

public class Clear implements BotCommand {
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
            return;
        }

        if (selfVoiceState.getChannel() != memberVoiceState.getChannel()) {
            event.getChannel().sendMessage("you are not in the same channel as me").queue();
            return;
        }
        GuildMusicManager guildMusicManager = PlayerManager.getInstance().getGuildMusicManager(event.getGuild());
        TrackScheduler trackScheduler = guildMusicManager.getTrackScheduler();

        if (trackScheduler.getQueue().isEmpty()) {
            event.getChannel().sendMessage("Nothing in queue").queue();
            return;
        }

        trackScheduler.getQueue().clear();
        trackScheduler.getPlayer().stopTrack();
        event.getChannel().sendMessage("Cleared queue").queue();

    }

    @Override
    public String getHelp() {
        return "Clears queue";
    }
}
