package me.skymc.taboolib.itemtool.asm;

import io.izzel.taboolib.Version;
import io.izzel.taboolib.util.Reflection;
import io.izzel.taboolib.util.lite.Numbers;
import me.skymc.taboolib.itemtool.util.Message;
import net.minecraft.server.v1_12_R1.NBTTagLongArray;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.NumberConversions;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Author 坏黑
 * @Since 2018-10-14 16:49
 */
public class AsmHandlerImpl extends AsmHandler {

    private Field tagListField;
    private Field intArrayDataField;
    private Field byteArrayDataField;
    private Field longArrayDataField;

    public AsmHandlerImpl() {
        try {
            tagListField = Reflection.getField(NBTTagList.class, true, "list");
            intArrayDataField = Reflection.getField(NBTTagIntArray.class, true, "data");
            byteArrayDataField = Reflection.getField(NBTTagByteArray.class, true, "data");
            // v1.12+
            if (Version.isAfter(Version.v1_13)) {
                longArrayDataField = NBTTagLongArray.class.getDeclaredFields()[0];
                longArrayDataField.setAccessible(true);
            }
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
        ((NBTTagCompound) attribtueCompound).setInt("UUIDMost", Numbers.getRandom().nextInt(Integer.MAX_VALUE));
        ((NBTTagCompound) attribtueCompound).setInt("UUIDLeast", Numbers.getRandom().nextInt(Integer.MAX_VALUE));
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
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) instanceof NBTTagCompound && ((NBTTagCompound) list.get(i)).hasKey("AttributeName") && ((NBTTagCompound) list.get(i)).getString("AttributeName").equalsIgnoreCase(attribute)) {
                    list.remove(i);
                }
            }
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

    @Override
    public ItemStack clearNBT(ItemStack itemStack) {
        Object nmsItem = CraftItemStack.asNMSCopy(itemStack);
        ((net.minecraft.server.v1_8_R3.ItemStack) nmsItem).setTag(new NBTTagCompound());
        return CraftItemStack.asBukkitCopy(((net.minecraft.server.v1_8_R3.ItemStack) nmsItem));
    }

    @Override
    public void sendItemNBT(Player player, ItemStack itemStack) {
        Object nmsItem = CraftItemStack.asNMSCopy(itemStack);
        Object itemTag = ((net.minecraft.server.v1_8_R3.ItemStack) nmsItem).hasTag() ? ((net.minecraft.server.v1_8_R3.ItemStack) nmsItem).getTag() : new NBTTagCompound();
        sendItemNBT(player, "NBT &8->&f", itemTag, 0);
    }

    private void sendItemNBT(Player player, String key, Object nbtBase, int node) {
        if (nbtBase instanceof NBTTagCompound) {
            Set<String> keys = Version.isAfter(Version.v1_13) ? ((net.minecraft.server.v1_13_R1.NBTTagCompound) nbtBase).getKeys() : ((NBTTagCompound) nbtBase).c();
            if (keys.isEmpty()) {
                Message.send(player, getNodeSpace(node) + key + " {}");
            } else {
                Message.send(player, getNodeSpace(node) + key + (key.equals("-") ? " {" : ""));
                for (String subKey : keys) {
                    sendItemNBT(player, subKey + ":", ((NBTTagCompound) nbtBase).get(subKey), node + 1);
                }
                if (key.equals("-")) {
                    Message.send(player, getNodeSpace(node) + "}");
                }
            }
        } else if (nbtBase instanceof NBTTagList) {
            try {
                List tagList = (List) tagListField.get(nbtBase);
                if (tagList.isEmpty()) {
                    Message.send(player, getNodeSpace(node) + key + " []");
                } else {
                    Message.send(player, getNodeSpace(node) + key);
                    tagList.forEach(aTagList -> sendItemNBT(player, "-", (NBTBase) aTagList, node));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else if (nbtBase instanceof NBTTagIntArray) {
            try {
                int[] array = (int[]) intArrayDataField.get(nbtBase);
                if (array.length == 0) {
                    Message.send(player, getNodeSpace(node) + key + " []");
                } else {
                    Message.send(player, getNodeSpace(node) + key);
                    for (int var : array) {
                        Message.send(player, getNodeSpace(node) + "- &f" + var);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else if (nbtBase instanceof NBTTagByteArray) {
            try {
                byte[] array = (byte[]) byteArrayDataField.get(nbtBase);
                if (array.length == 0) {
                    Message.send(player, getNodeSpace(node) + key + " []");
                } else {
                    Message.send(player, getNodeSpace(node) + key);
                    for (byte var : array) {
                        Message.send(player, getNodeSpace(node) + "- &f" + var);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else if (nbtBase.getClass().getSimpleName().equals("NBTTagLongArray")) {
            try {
                long[] array = (long[]) longArrayDataField.get(nbtBase);
                if (array.length == 0) {
                    Message.send(player, getNodeSpace(node) + key + " []");
                } else {
                    Message.send(player, getNodeSpace(node) + key);
                    for (long var : array) {
                        Message.send(player, getNodeSpace(node) + "- &f" + var);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            Message.send(player, getNodeSpace(node) + key + " &f" + (nbtBase == null ? "" : nbtBase instanceof NBTTagString ? "&7\"&r" + nbtBase.toString().substring(1, nbtBase.toString().length() - 1) + "&7\"" : nbtBase.toString()));
        }
    }

    private String getNodeSpace(int node) {
        return IntStream.range(0, node).mapToObj(i -> "  ").collect(Collectors.joining());
    }
}
