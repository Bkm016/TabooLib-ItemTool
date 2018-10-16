package me.skymc.taboolib.itemtool.asm;

import me.skymc.taboolib.other.NumberUtils;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.NumberConversions;

import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

/**
 * @Author 坏黑
 * @Since 2018-10-14 16:49
 */
public class AsmHandlerImpl extends AsmHandler {

    private Field tagListField;

    public AsmHandlerImpl() {
        try {
            tagListField = NBTTagList.class.getDeclaredField("list");
            tagListField.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ItemStack addAttribute(ItemStack itemStack, String attribtue, String value, String equipment) {
        Object nmsItem = CraftItemStack.asNMSCopy(itemStack);
        Object itemTag = ((net.minecraft.server.v1_8_R3.ItemStack) nmsItem).hasTag() ? ((net.minecraft.server.v1_8_R3.ItemStack) nmsItem).getTag() : new NBTTagCompound();
        Object attribtueCompound = new NBTTagCompound();
        Object attributeModifiers = ((NBTTagCompound) itemTag).hasKey("AttributeModifiers") ? ((NBTTagCompound) itemTag).getList("AttributeModifiers", 10) : new NBTTagList();
        ((NBTTagCompound) attribtueCompound).setString("Name", attribtue);
        ((NBTTagCompound) attribtueCompound).setString("AttributeName", attribtue);
        ((NBTTagCompound) attribtueCompound).setInt("UUIDMost", NumberUtils.getRandom().nextInt(Integer.MAX_VALUE));
        ((NBTTagCompound) attribtueCompound).setInt("UUIDLeast", NumberUtils.getRandom().nextInt(Integer.MAX_VALUE));
        if (value.endsWith("%")) {
            ((NBTTagCompound) attribtueCompound).setInt("Operation", 1);
            ((NBTTagCompound) attribtueCompound).setDouble("Amount", NumberConversions.toDouble(value.substring(0, value.length() - 1)) / 100.0D);
        } else {
            ((NBTTagCompound) attribtueCompound).setInt("Operation", 0);
            ((NBTTagCompound) attribtueCompound).setDouble("Amount", NumberConversions.toDouble(value));
        }
        if (!equipment.equalsIgnoreCase("all")) {
            ((NBTTagCompound) attribtueCompound).setString("Slot", equipment);
        }
        ((NBTTagList) attributeModifiers).add(((NBTTagCompound) attribtueCompound));
        ((NBTTagCompound) itemTag).set("AttributeModifiers", ((NBTTagList) attributeModifiers));
        ((net.minecraft.server.v1_8_R3.ItemStack) nmsItem).setTag(((NBTTagCompound) itemTag));
        return CraftItemStack.asBukkitCopy(((net.minecraft.server.v1_8_R3.ItemStack) nmsItem));
    }

    @Override
    public ItemStack removeAttribtue(ItemStack itemStack, String attribute) {
        Object nmsItem = CraftItemStack.asNMSCopy(itemStack);
        Object itemTag = ((net.minecraft.server.v1_8_R3.ItemStack) nmsItem).hasTag() ? ((net.minecraft.server.v1_8_R3.ItemStack) nmsItem).getTag() : new NBTTagCompound();
        Object attribtueCompound = new NBTTagCompound();
        Object attributeModifiers = ((NBTTagCompound) itemTag).hasKey("AttributeModifiers") ? ((NBTTagCompound) itemTag).getList("AttributeModifiers", 10) : new NBTTagList();
        try {
            List list = new CopyOnWriteArrayList((List) tagListField.get(attributeModifiers));
            IntStream.range(0, list.size()).filter(i -> list.get(i) instanceof NBTTagCompound && ((NBTTagCompound) list.get(i)).hasKey("AttributeName") && ((NBTTagCompound) list.get(i)).getString("AttributeName").equalsIgnoreCase(attribute)).forEach(list::remove);
            tagListField.set(attributeModifiers, list);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        ((NBTTagCompound) itemTag).set("AttributeModifiers", ((NBTTagList) attributeModifiers));
        ((net.minecraft.server.v1_8_R3.ItemStack) nmsItem).setTag(((NBTTagCompound) itemTag));
        return CraftItemStack.asBukkitCopy(((net.minecraft.server.v1_8_R3.ItemStack) nmsItem));
    }

    @Override
    public ItemStack setItemTag(ItemStack itemStack, String key, String value) {
        Object nmsItem = CraftItemStack.asNMSCopy(itemStack);
        Object itemTag = ((net.minecraft.server.v1_8_R3.ItemStack) nmsItem).hasTag() ? ((net.minecraft.server.v1_8_R3.ItemStack) nmsItem).getTag() : new NBTTagCompound();
        ((NBTTagCompound) itemTag).setString(key, value);
        ((net.minecraft.server.v1_8_R3.ItemStack) nmsItem).setTag(((NBTTagCompound) itemTag));
        return CraftItemStack.asBukkitCopy(((net.minecraft.server.v1_8_R3.ItemStack) nmsItem));
    }

    @Override
    public String getItemTag(ItemStack itemStack, String key) {
        Object nmsItem = CraftItemStack.asNMSCopy(itemStack);
        Object itemTag = ((net.minecraft.server.v1_8_R3.ItemStack) nmsItem).hasTag() ? ((net.minecraft.server.v1_8_R3.ItemStack) nmsItem).getTag() : new NBTTagCompound();
        return ((NBTTagCompound) itemTag).getString(key);
    }
}
