package net.aerulion.membersurvey.utils;

import net.minecraft.server.v1_15_R1.NBTTagCompound;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class NBT {
    public static ItemStack setNBTString(ItemStack item, String key, String value) {
        net.minecraft.server.v1_15_R1.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound localNBTTagCompound = nmsItemStack.getTag();
        if (localNBTTagCompound == null)
            localNBTTagCompound = new NBTTagCompound();
        localNBTTagCompound.setString(key, value);
        nmsItemStack.setTag(localNBTTagCompound);
        return CraftItemStack.asBukkitCopy(nmsItemStack);
    }

    public static String getNBTString(ItemStack item, String key) {
        net.minecraft.server.v1_15_R1.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound localNBTTagCompound = nmsItemStack.getTag();
        if ((localNBTTagCompound != null) && (localNBTTagCompound.hasKey(key)))
            return localNBTTagCompound.getString(key);
        return null;
    }
}