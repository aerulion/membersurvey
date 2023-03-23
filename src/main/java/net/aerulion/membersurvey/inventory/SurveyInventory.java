package net.aerulion.membersurvey.inventory;

import java.util.ArrayList;
import java.util.List;
import net.aerulion.erenos.core.utils.item.ItemBuilder;
import net.aerulion.erenos.core.utils.nbt.NbtUtils;
import net.aerulion.erenos.core.utils.string.StringUtils;
import net.aerulion.membersurvey.utils.Lang;
import net.aerulion.membersurvey.utils.Survey;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SurveyInventory {

  public static @NotNull Inventory create(final @NotNull Survey survey) {
    final @NotNull Inventory inventory = Bukkit.createInventory(null, 36,
        Lang.INVENTORY_NAME_SURVEY);
    final @NotNull ItemStack spacer = NbtUtils.setNBTString(
        ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE).withDisplayName(Component.empty())
            .asItemStack(), Lang.NBT_KEY_SURVEY_ID, survey.getId());
    for (int i = 0; i < inventory.getSize(); i++) {
      inventory.setItem(i, spacer);
    }
    final @NotNull List<Component> questionLore = new ArrayList<>();
    for (final String string : StringUtils.wrapString(survey.getQuestion(), 30)) {
      questionLore.add(LegacyComponentSerializer.legacySection().deserialize("§7" + string));
    }
    final @NotNull ItemStack question = ItemBuilder.of(Material.WRITABLE_BOOK)
        .withDisplayName(LegacyComponentSerializer.legacySection().deserialize("§e§lFrage:"))
        .withLore(questionLore).asItemStack();
    int optionSlot = 9;
    for (final @NotNull String option : survey.getOptions()) {
      final @NotNull List<Component> optionLore = new ArrayList<>();
      for (final String string : StringUtils.wrapString(option, 30)) {
        optionLore.add(LegacyComponentSerializer.legacySection().deserialize("§7" + string));
      }
      optionLore.add(Component.empty());
      optionLore.add(
          LegacyComponentSerializer.legacySection().deserialize("§e▶ Klicke zum Abstimmen"));
      inventory.setItem(optionSlot, NbtUtils.setNBTString(ItemBuilder.of(Material.PAPER)
              .withDisplayName(LegacyComponentSerializer.legacySection()
                  .deserialize("§e§lAntwort " + (optionSlot - 8))).withLore(optionLore).asItemStack(),
          Lang.NBT_KEY_SURVEY_OPTION, option));
      optionSlot++;
    }
    inventory.setItem(4, question);
    return inventory;
  }

}