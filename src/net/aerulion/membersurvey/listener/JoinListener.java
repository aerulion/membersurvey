package net.aerulion.membersurvey.listener;

import net.aerulion.membersurvey.Main;
import net.aerulion.membersurvey.task.SurveyAlertTask;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Main.scheduledSurveyAlerts.put(event.getPlayer().getUniqueId().toString(), new SurveyAlertTask(event.getPlayer().getUniqueId()));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        String playerUUID = event.getPlayer().getUniqueId().toString();
        if (Main.scheduledSurveyAlerts.containsKey(playerUUID)) {
            Main.scheduledSurveyAlerts.get(playerUUID).cancel();
            Main.scheduledSurveyAlerts.remove(playerUUID);
        }
    }
}