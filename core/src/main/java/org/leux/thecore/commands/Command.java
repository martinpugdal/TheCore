package org.leux.thecore.commands;

import org.leux.TheCore;

public class Command {

    public Command() {
    }

    public static void init() {
        TheCore.getInstance().getLogger().info("Loading commands...");
        BroadcastCommand.init();
        CoreCommand.init();
        DiscordCommand.init();
        SpawnCommand.init();
    }
}
