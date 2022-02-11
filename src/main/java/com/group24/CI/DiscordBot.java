package com.group24.CI;

import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClient;
import discord4j.rest.entity.RestChannel;

/**
 * Class to send message to a discord channel.
 * Create a Bot using the Discord developer options.
 */
public class DiscordBot {

    private final String token = "OTQxMzQ0MzIzMDA4NjEwMzE1.YgUlNw.WHUP9ws1sUJMLB50blmFNlpZ_6A";
    private final String channelID = "941349258215432252";
    private final DiscordClient client;

    /**
     * Constructor that connects to discord bot
     */
    public DiscordBot() {
        this.client = DiscordClient.create(token);
    }

    /**
     * Send message to Discord channel.
     * @param message to send to discord channel.
     */
    public void sendMsg(String message) {
        RestChannel channelByID = client.getChannelById(Snowflake.of(channelID));
        channelByID.createMessage(message).block();
    }
}
