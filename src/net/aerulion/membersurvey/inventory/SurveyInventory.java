package net.aerulion.membersurvey.inventory;

import net.aerulion.membersurvey.utils.Lang;
import net.aerulion.membersurvey.utils.NBT;
import net.aerulion.membersurvey.utils.Survey;
import net.aerulion.membersurvey.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SurveyInventory {

    private static ItemStack buildSimpleItem(Material material, String displayName, List<String> loreList) {
        ItemStack SimpleItem = new ItemStack(material);
        ItemMeta mSimpleItem = SimpleItem.getItemMeta();
        mSimpleItem.setDisplayName(displayName);
        mSimpleItem.setLore(loreList);
        SimpleItem.setItemMeta(mSimpleItem);
        return SimpleItem;
    }

    public static Inventory create(Survey survey) {
        Inventory inventory = Bukkit.createInventory(null, 36, Lang.INVENTORY_NAME_SURVEY);
        ItemStack spacer = NBT.setNBTString(buildSimpleItem(Material.BLACK_STAINED_GLASS_PANE, "§0", null), Lang.NBT_KEY_SURVEY_ID, survey.getId());
        for (int i = 0; i < inventory.getSize(); i++)
            inventory.setItem(i, spacer);
        List<String> questionLore = new ArrayList<>();
        for (String string : TextUtils.WrapString(survey.getQuestion(), 30))
            questionLore.add("§7" + string);
        ItemStack question = buildSimpleItem(Material.WRITABLE_BOOK, "§e§lFrage:", questionLore);
        int optionSlot = 9;
        for (String option : survey.getOptions()) {
            List<String> optionLore = new ArrayList<>();
            for (String string : TextUtils.WrapString(option, 30))
                optionLore.add("§7" + string);
            optionLore.add("");
            optionLore.add("§e\u25B6 Klicke zum Abstimmen");
            inventory.setItem(optionSlot, NBT.setNBTString(buildSimpleItem(Material.PAPER, "§e§lAntwort " + (optionSlot - 8), optionLore), Lang.NBT_KEY_SURVEY_OPTION, option));
            optionSlot++;
        }
        inventory.setItem(4, question);
        return inventory;
    }
}