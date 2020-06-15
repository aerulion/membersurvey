package net.aerulion.membersurvey.task;

import net.aerulion.membersurvey.Main;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SurveySoundTask extends BukkitRunnable {

    private int time;
    private final Player player;

    public SurveySoundTask(Player player) {
        this.time = 0;
        this.player = player;
        this.runTaskTimerAsynchronously(Main.plugin, 0L, 2L);
    }

    @Override
    public void run() {
        time++;
        switch (time) {
            case 1:
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 0.5F, 0.891F);
                break;
            case 2:
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 0.5F, 1.059F);
                break;
            case 3:
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 0.5F, 1.335F);
                break;
            default:
                this.cancel();
                break;
        }
    }
}
