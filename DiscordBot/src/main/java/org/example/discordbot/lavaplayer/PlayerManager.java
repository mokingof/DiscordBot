package org.example.discordbot.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.*;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.track.playback.NonAllocatingAudioFrameBuffer;

public class PlayerManager {
    private static PlayerManager INSTANCE;
    private Map<Long, GuildMusicManager> guildMusicManagerManagers = new HashMap<>();
    private AudioPlayerManager audioPlayerManager = new DefaultAudioPlayerManager();

    private PlayerManager() {
        AudioSourceManagers.registerRemoteSources(this.audioPlayerManager);
        AudioSourceManagers.registerLocalSource(this.audioPlayerManager);
    }

    public static PlayerManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlayerManager();
        }
        return INSTANCE;
    }

    public GuildMusicManager getGuildMusicManager(Guild guild) {
        return this.guildMusicManagerManagers.computeIfAbsent(guild.getIdLong(), (guildId) -> {
            GuildMusicManager musicManager = new GuildMusicManager(this.audioPlayerManager);

            guild.getAudioManager().setSendingHandler(musicManager.getAudioForwarder());

            return musicManager;
        }) ;
    }

    public void play(Guild guild, String input) {
        String trackUrl;
        // Check if the input is a URL or a search term
        if (input.startsWith("http://") || input.startsWith("https://")) {
            trackUrl = input;
        } else {
            // Prefix the search term to use YouTube search
            trackUrl = "ytsearch:" + input;
        }
        GuildMusicManager guildMusicManager = getGuildMusicManager(guild);
        this.audioPlayerManager.loadItemOrdered(guildMusicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                guildMusicManager.getTrackScheduler().queue(track);
            }

            public void playlistLoaded(AudioPlaylist playlist) {
                // If it's a search result, play the first track
                AudioTrack firstTrack = playlist.getSelectedTrack() != null ? playlist.getSelectedTrack() : playlist.getTracks().get(0);
                guildMusicManager.getTrackScheduler().queue(firstTrack);
            }

            @Override
            public void noMatches() {
                System.out.println("No Matches found for input" + trackUrl);
            }

            @Override
            public void loadFailed(FriendlyException exception) {

            }
        });

    }

}


