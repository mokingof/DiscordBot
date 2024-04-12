package org.example.discordbot.commands.music;

import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.example.discordbot.commands.BotCommand;
import org.example.discordbot.lavaplayer.GuildMusicManager;
import org.example.discordbot.lavaplayer.PlayerManager;

public class Repeat implements BotCommand {


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
            event.getGuild().getAudioManager().openAudioConnection(memberVoiceState.getChannel());
        } else {
            if (selfVoiceState.getChannel() != memberVoiceState.getChannel()) {
                event.getChannel().sendMessage("you need to be in the same voice channel").queue();
            }
        }

        String message = event.getMessage().getContentRaw();
        String url = message.substring("!play".length()).trim();


        GuildMusicManager guildMusicManager = PlayerManager.getInstance().getGuildMusicManager(event.getGuild());
        boolean isRepeat = !guildMusicManager.getTrackScheduler().isRepeat();
        guildMusicManager.getTrackScheduler().setRepeat(isRepeat);
        event.getChannel().sendMessage("Repeat is now " + isRepeat).queue();
    }

    @Override
    public String getHelp() {
        return "Repeat";
    }
}