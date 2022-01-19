package net.aerulion.membersurvey.inventory;

import java.util.ArrayList;
import java.util.List;
import net.aerulion.membersurvey.utils.Lang;
import net.aerulion.membersurvey.utils.Survey;
import net.aerulion.nucleus.api.item.ItemUtils;
import net.aerulion.nucleus.api.nbt.NbtUtils;
import net.aerulion.nucleus.api.string.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SurveyInventory {

  public static @NotNull Inventory create(final @NotNull Survey survey) {
    final @NotNull Inventory inventory = Bukkit.createInventory(null, 36, Lang.INVENTORY_NAME_SURVEY);
    final @NotNull ItemStack spacer = NbtUtils.setNBTString(
        ItemUtils.buildItemStack(Material.BLACK_STAINED_GLASS_PANE, "§0", null, false),
        Lang.NBT_KEY_SURVEY_ID, survey.getId());
    for (int i = 0; i < inventory.getSize(); i++) {
      inventory.setItem(i, spacer);
    }
    final @NotNull List<String> questionLore = new ArrayList<>();
    for (final String string : StringUtils.wrapString(survey.getQuestion(), 30)) {
      questionLore.add("§7" + string);
    }
    final @NotNull ItemStack question = ItemUtils.buildItemStack(Material.WRITABLE_BOOK, "§e§lFrage:",
        questionLore, false);
    int optionSlot = 9;
    for (final @NotNull String option : survey.getOptions()) {
      final @NotNull List<String> optionLore = new ArrayList<>();
      for (final String string : StringUtils.wrapString(option, 30)) {
        optionLore.add("§7" + string);
      }
      optionLore.add("");
      optionLore.add("§e\u25B6 Klicke zum Abstimmen");
      inventory.setItem(optionSlot, NbtUtils.setNBTString(
          ItemUtils.buildItemStack(Material.PAPER, "§e§lAntwort " + (optionSlot - 8), optionLore,
              false), Lang.NBT_KEY_SURVEY_OPTION, option));
      optionSlot++;
    }
    inventory.setItem(4, question);
    return inventory;
  }
}