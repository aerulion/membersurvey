package net.aerulion.membersurvey.task;

import java.util.Random;
import java.util.UUID;
import net.aerulion.membersurvey.Main;
import net.aerulion.membersurvey.utils.Lang;
import net.aerulion.membersurvey.utils.Survey;
import net.aerulion.nucleus.api.chat.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SurveyAlertTask extends BukkitRunnable {

  private final UUID playerUUID;

  public SurveyAlertTask(UUID playerUUID) {
    this.playerUUID = playerUUID;
    Random random = new Random();
    this.runTaskLaterAsynchronously(Main.plugin, 6000L + random.nextInt(6000));
  }

  @Override
  public void run() {
    Player player = Bukkit.getPlayer(playerUUID);
    if (player != null) {
      if (player.hasPermission("membersurvey.member") || player.hasPermission(
          "membersurvey.admin")) {
        for (Survey survey : Main.activeSurveys.values()) {
          if (!survey.getResults().containsKey(player.getUniqueId().toString())) {
            ChatUtils.sendChatDividingLine(player, "§7");
            ChatUtils.sendCenteredChatMessage(player, Lang.MESSAGE_SURVEY_AVAILABLE_1);
            ChatUtils.sendCenteredChatMessage(player, Lang.MESSAGE_SURVEY_AVAILABLE_2);
            ChatUtils.sendChatDividingLine(player, "§7");
            new SurveySoundTask(player);
          }
        }
      }
    }
  }
}