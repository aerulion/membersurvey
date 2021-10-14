package net.aerulion.membersurvey.task;

import net.aerulion.membersurvey.Main;
import net.aerulion.membersurvey.utils.Survey;
import net.aerulion.nucleus.api.chat.ChatUtils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SurveyListTask extends BukkitRunnable {

  private final Player player;

  public SurveyListTask(Player player) {
    this.player = player;
    this.runTaskAsynchronously(Main.plugin);
  }

  @Override
  public void run() {
    ChatUtils.sendChatDividingLine(player, "§7");
    ChatUtils.sendCenteredChatMessage(player, "§e§lAlle aktiven Umfragen:");
    player.sendMessage("");
    for (Survey survey : Main.activeSurveys.values()) {
      player.sendMessage(" §e§l\u25B6§7 " + survey.getQuestion());
      TextComponent buttonRow = new TextComponent("             ");
      TextComponent detailButton = new TextComponent("§a§l[Details]");
      detailButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
          new ComponentBuilder("§eKlicke für mehr Details").create()));
      detailButton.setClickEvent(
          new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/survey evaluate " + survey.getId()));
      buttonRow.addExtra(detailButton);
      buttonRow.addExtra(new TextComponent("§r                         "));
      TextComponent deleteButton = new TextComponent("§c§l[Löschen]");
      deleteButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
          new ComponentBuilder("§eKlicke um die Umfrage zu löschen").create()));
      deleteButton.setClickEvent(
          new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/survey delete " + survey.getId()));
      buttonRow.addExtra(deleteButton);
      player.spigot().sendMessage(buttonRow);
      player.sendMessage("");
    }
    ChatUtils.sendChatDividingLine(player, "§7");
  }
}
