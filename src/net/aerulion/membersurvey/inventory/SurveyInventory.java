package net.aerulion.membersurvey.inventory;

import net.aerulion.membersurvey.utils.Lang;
import net.aerulion.membersurvey.utils.Survey;
import net.aerulion.nucleus.api.item.ItemUtils;
import net.aerulion.nucleus.api.nbt.NbtUtils;
import net.aerulion.nucleus.api.string.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SurveyInventory {
    public static Inventory create(Survey survey) {
        Inventory inventory = Bukkit.createInventory(null, 36, Lang.INVENTORY_NAME_SURVEY);
        ItemStack spacer = NbtUtils.setNBTString(ItemUtils.buildItemStack(Material.BLACK_STAINED_GLASS_PANE, "§0", null, false), Lang.NBT_KEY_SURVEY_ID, survey.getId());
        for (int i = 0; i < inventory.getSize(); i++)
            inventory.setItem(i, spacer);
        List<String> questionLore = new ArrayList<>();
        for (String string : StringUtils.wrapString(survey.getQuestion(), 30))
            questionLore.add("§7" + string);
        ItemStack question = ItemUtils.buildItemStack(Material.WRITABLE_BOOK, "§e§lFrage:", questionLore, false);
        int optionSlot = 9;
        for (String option : survey.getOptions()) {
            List<String> optionLore = new ArrayList<>();
            for (String string : StringUtils.wrapString(option, 30))
                optionLore.add("§7" + string);
            optionLore.add("");
            optionLore.add("§e\u25B6 Klicke zum Abstimmen");
            inventory.setItem(optionSlot, NbtUtils.setNBTString(ItemUtils.buildItemStack(Material.PAPER, "§e§lAntwort " + (optionSlot - 8), optionLore, false), Lang.NBT_KEY_SURVEY_OPTION, option));
            optionSlot++;
        }
        inventory.setItem(4, question);
        return inventory;
    }
}