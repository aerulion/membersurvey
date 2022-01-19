package net.aerulion.membersurvey.listener;

import net.aerulion.membersurvey.Main;
import net.aerulion.membersurvey.task.SaveToFileTask;
import net.aerulion.membersurvey.utils.Lang;
import net.aerulion.membersurvey.utils.Survey;
import net.aerulion.nucleus.api.console.ConsoleUtils;
import net.aerulion.nucleus.api.nbt.NbtUtils;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

public class SurveyGUIListener implements Listener {

  @EventHandler
  public void onInventoryClick(final @NotNull InventoryClickEvent event) {
    if (event.getView().getTitle().equals(Lang.INVENTORY_NAME_SURVEY)) {
      event.setCancelled(true);
      if ((event.getRawSlot() >= 0
          && event.getRawSlot() <= event.getView().getTopInventory().getSize() - 1)) {
        if (event.getCurrentItem().getType() == Material.PAPER) {
          final Survey survey = Main.activeSurveys.get(
              NbtUtils.getNBTString(event.getInventory().getItem(0), Lang.NBT_KEY_SURVEY_ID));
          survey.addResult(event.getWhoClicked().getUniqueId().toString(),
              NbtUtils.getNBTString(event.getCurrentItem(), Lang.NBT_KEY_SURVEY_OPTION));
          new SaveToFileTask(survey.getId());
          event.getWhoClicked().closeInventory();
          final EconomyResponse economyResponse = Main.economy.depositPlayer(
              (Player) event.getWhoClicked(), survey.getReward());
          if (economyResponse.transactionSuccess()) {
            event.getWhoClicked().sendMessage(Lang.MESSAGE_SURVEY_COMPLETED_1 + survey.getReward()
                + Lang.MESSAGE_SURVEY_COMPLETED_2);
            ((Player) event.getWhoClicked()).playSound(event.getWhoClicked().getLocation(),
                Sound.ENTITY_PLAYER_LEVELUP, 0.5F, 1.2F);
          } else {
            event.getWhoClicked().sendMessage(Lang.ERROR_TRANSACTION_FAILED);
            ((Player) event.getWhoClicked()).playSound(event.getWhoClicked().getLocation(),
                Sound.ENTITY_VILLAGER_NO, 0.5F, 1.2F);
            ConsoleUtils.sendColoredConsoleMessage(
                Lang.CONSOLE_TRANSACTION_FAILED + survey.getReward() + " CT -> "
                    + event.getWhoClicked().getName());
          }
        }
      }
    }
  }
}