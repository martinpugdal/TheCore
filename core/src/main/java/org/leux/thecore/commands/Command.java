package org.leux.thecore.commands;

import org.leux.TheCore;

public class Command {

    public Command() {
    }

    public static void init() {
        TheCore.getInstance().getLogger().info("Loading commands...");
        CoreCommand.init();
        DiscordCommand.init();
        BroadcastCommand.init();
    }

}
