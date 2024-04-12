package org.example.discordbot;


import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.example.discordbot.commands.CommandHandler;
import org.example.discordbot.listeners.BotEventListener;
import javax.security.auth.login.LoginException;

public class Main {
    public static void main(String[] args) throws LoginException {
//        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);
//
//        builder.setStatus(OnlineStatus.ONLINE);
//        builder.setActivity(Activity.watching("The world Burn"));
//        builder.addEventListeners(new BotEventListener()).build();
      //  ShardManager shardManager = builder.build();

        String token = "MTIxODE3ODA4MTY3MjM5NjgyMA.GArU8Z.BOmHQZEZ55NRBYjmCQnhJg8-Mc4RtB2gHAe9ik";

      JDA jda =  JDABuilder.createDefault(token)
                .setStatus(OnlineStatus.ONLINE)
                .setActivity(Activity.watching("The World Burn"))
                .addEventListeners(new BotEventListener(new CommandHandler()))
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .build();


    }
}
