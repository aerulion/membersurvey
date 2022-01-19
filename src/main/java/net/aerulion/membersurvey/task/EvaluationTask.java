package net.aerulion.membersurvey.task;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;
import net.aerulion.membersurvey.Main;
import net.aerulion.membersurvey.utils.Survey;
import net.aerulion.nucleus.api.chat.ChatUtils;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class EvaluationTask extends BukkitRunnable {

  private final Player player;
  private final Survey survey;
  private final String[] chatColors = {"§2", "§3", "§4", "§5", "§6", "§7", "§9", "§a", "§b", "§c",
      "§d", "§f"};

  public EvaluationTask(final Player player, final Survey survey) {
    this.player = player;
    this.survey = survey;
    this.runTaskAsynchronously(Main.plugin);
  }

  @Override
  public void run() {
    final @NotNull TextComponent progressBar = new TextComponent();
    final @NotNull HashMap<String, Integer> results = new HashMap<>();
    final @NotNull HashMap<String, String> colorCoding = new HashMap<>();
    for (final String player : survey.getResults().keySet()) {
      if (results.containsKey(survey.getResults().get(player))) {
        results.put(survey.getResults().get(player),
            results.get(survey.getResults().get(player)) + 1);
      } else {
        results.put(survey.getResults().get(player), 1);
      }
    }
    for (final String option : survey.getOptions()) {
      if (!results.containsKey(option)) {
        results.put(option, 0);
      }
    }
    final @NotNull Random random = new Random();
    for (final String option : results.keySet()) {
      String color = chatColors[random.nextInt(chatColors.length)];
      if (colorCoding.size() < chatColors.length) {
        while (colorCoding.containsValue(color)) {
          color = chatColors[random.nextInt(chatColors.length)];
        }
      }
      colorCoding.put(option, color);
    }
    final @NotNull DecimalFormat df = new DecimalFormat("#.##");
    for (final Entry<String, Integer> entry : results.entrySet()) {
      final String option = entry.getKey();
      final @NotNull StringBuilder sb = new StringBuilder(colorCoding.get(option));
      for (int i = 1;
          i < (((double) entry.getValue() / survey.getResults().size()) * 160D); i++) {
        sb.append("|");
      }
      final @NotNull TextComponent subBar = new TextComponent(sb.toString());
      subBar.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(
          "§e" + df.format(
              ((double) entry.getValue() / survey.getResults().size()) * 100D) + "%§7 ["
              + entry.getValue() + "/" + survey.getResults().size() + "]§e "
              + option).create()));
      progressBar.addExtra(subBar);
    }
    ChatUtils.sendChatDividingLine(player, "§7");
    ChatUtils.sendCenteredChatMessage(player, "§e§lUmfrageergebnisse:");
    player.sendMessage("");
    player.sendMessage("§e§lFrage: §7" + survey.getQuestion());
    player.sendMessage("");
    ChatUtils.sendCenteredChatMessage(player,
        "§e§lBeteiligung:§7 " + survey.getResults().size() + " Spieler");
    player.sendMessage("");
    ChatUtils.sendCenteredChatMessage(player, "§e§lVerteilung:");
    player.sendMessage("");
    player.spigot().sendMessage(progressBar);
    player.sendMessage("");
    ChatUtils.sendCenteredChatMessage(player, "§e§lDetails:");
    player.sendMessage("");
    for (final Entry<String, Integer> entry : results.entrySet()) {
      final String option = entry.getKey();
      player.sendMessage("  " + colorCoding.get(option) + "§l\u25B6 " + option);
      player.sendMessage("     §eAnzahl an Stimmen: §7" + entry.getValue());
      player.sendMessage("     §eProzentualer Anteil: §7" + df.format(
          ((double) entry.getValue() / survey.getResults().size()) * 100D) + "%");
      player.sendMessage("");
    }
    ChatUtils.sendChatDividingLine(player, "§7");
  }
}