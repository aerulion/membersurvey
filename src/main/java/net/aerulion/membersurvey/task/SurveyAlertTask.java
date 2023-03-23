package net.aerulion.membersurvey.task;

import java.util.Random;
import java.util.UUID;
import net.aerulion.erenos.core.utils.chat.ChatUtils;
import net.aerulion.membersurvey.Main;
import net.aerulion.membersurvey.utils.Lang;
import net.aerulion.membersurvey.utils.Survey;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SurveyAlertTask extends BukkitRunnable {

  private final UUID playerUUID;

  public SurveyAlertTask(final UUID playerUUID) {
    this.playerUUID = playerUUID;
    final @NotNull Random random = new Random();
    this.runTaskLaterAsynchronously(Main.plugin, 6000L + random.nextInt(6000));
  }

  @Override
  public void run() {
    final @Nullable Player player = Bukkit.getPlayer(playerUUID);
    if (player != null) {
      if (player.hasPermission("membersurvey.member") || player.hasPermission(
          "membersurvey.admin")) {
        for (final @NotNull Survey survey : Main.activeSurveys.values()) {
          if (!survey.getResults().containsKey(player.getUniqueId().toString())) {
            ChatUtils.sendChatDividingLine(player, NamedTextColor.GRAY);
            ChatUtils.sendCenteredChatMessage(player, LegacyComponentSerializer.legacySection()
                .deserialize(Lang.MESSAGE_SURVEY_AVAILABLE_1));
            ChatUtils.sendCenteredChatMessage(player, LegacyComponentSerializer.legacySection()
                .deserialize(Lang.MESSAGE_SURVEY_AVAILABLE_2));
            ChatUtils.sendChatDividingLine(player, NamedTextColor.GRAY);
            new SurveySoundTask(player);
          }
        }
      }
    }
  }
}