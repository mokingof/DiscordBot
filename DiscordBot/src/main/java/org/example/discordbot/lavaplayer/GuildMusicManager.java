package org.example.discordbot.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import net.dv8tion.jda.api.audio.AudioSendHandler;

public class GuildMusicManager {

    private TrackScheduler trackScheduler;
    private AudioForwarder audioForwarder;

    public GuildMusicManager(AudioPlayerManager manager) {
        AudioPlayer player = manager.createPlayer();
        this.trackScheduler = new TrackScheduler(player);
        player.addListener(this.trackScheduler);
        this.audioForwarder = new AudioForwarder(player);
    }

    public TrackScheduler getTrackScheduler() {
        return this.trackScheduler;
    }

    public AudioForwarder getAudioForwarder() {
        return this.audioForwarder;
    }
}
