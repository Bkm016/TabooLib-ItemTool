package me.skymc.taboolib.itemtool.command;

import com.google.common.collect.Lists;
import me.skymc.taboolib.commands.builder.SimpleCommandBuilder;
import me.skymc.taboolib.common.inject.TInject;
import me.skymc.taboolib.inventory.ItemUtils;
import me.skymc.taboolib.itemtool.ItemTool;
import me.skymc.taboolib.itemtool.util.Message;
import me.skymc.taboolib.other.NumberUtils;
import org.bukkit.FireworkEffect;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.NumberConversions;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @Author 坏黑
 * @Since 2018-10-12 23:09
 */
public class CommandMeta {

    @TInject
    private static SimpleCommandBuilder addEnchant = SimpleCommandBuilder.create("addEnchant", ItemTool.getInst())
            .silence()
            .forceRegister()
            .aliases("enchant")
            .permission("itemTool.use")
            .description("添加物品附魔")
            .tab((sender, args) -> {
                if (args.length == 1) {
                    return Arrays.stream(Enchantment.values()).filter(type -> type.getName().toUpperCase().startsWith(args[0].toUpperCase())).map(Enchantment::getName).collect(Collectors.toList());
                }
                return Lists.newArrayList();
            })
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.send(sender, "&cCommand disabled on console.");
                } else if (ItemUtils.isNull(((Player) sender).getItemInHand())) {
                    Message.send(sender, "&cInvalid item.");
                    Message.NO.play((Player) sender);
                } else if (args.length < 2) {
                    Message.send(sender, "&cInvalid arguments.");
                    Message.NO.play((Player) sender);
                } else if (!args[0].equalsIgnoreCase("all") && ItemUtils.asEnchantment(args[0].toUpperCase()) == null) {
                    Message.send(sender, "&cInvalid Enchantment type.");
                    Message.NO.play((Player) sender);
                } else if (NumberConversions.toInt(args[1]) <= 0) {
                    Message.send(sender, "&cInvalid Enchantment level.");
                    Message.NO.play((Player) sender);
                } else {
                    Message.send(sender, "Enchant §8+ &f" + args[0].toUpperCase() + ":" + args[1]);
                    Message.ITEM_EDIT.play((Player) sender);
                    // Action
                    ItemMeta itemMeta = ((Player) sender).getItemInHand().getItemMeta();
                    if (args[0].equalsIgnoreCase("all")) {
                        Arrays.stream(Enchantment.values()).forEach(enchantment -> itemMeta.addEnchant(enchantment, NumberConversions.toInt(args[1]), true));
                    } else {
                        itemMeta.addEnchant(ItemUtils.asEnchantment(args[0].toUpperCase()), NumberConversions.toInt(args[1]), true);
                    }
                    ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                }
                return true;
            });

    @TInject
    private static SimpleCommandBuilder removeEnchant = SimpleCommandBuilder.create("removeEnchant", ItemTool.getInst())
            .silence()
            .forceRegister()
            .permission("itemTool.use")
            .description("移除物品附魔")
            .tab((sender, args) -> {
                if (args.length == 1) {
                    return Arrays.stream(Enchantment.values()).filter(type -> type.getName().toUpperCase().startsWith(args[0].toUpperCase())).map(Enchantment::getName).collect(Collectors.toList());
                }
                return Lists.newArrayList();
            })
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.send(sender, "&cCommand disabled on console.");
                } else if (ItemUtils.isNull(((Player) sender).getItemInHand())) {
                    Message.send(sender, "&cInvalid item.");
                    Message.NO.play((Player) sender);
                } else if (args.length == 0) {
                    Message.send(sender, "&cInvalid arguments.");
                    Message.NO.play((Player) sender);
                } else if (!args[0].equalsIgnoreCase("all") && ItemUtils.asEnchantment(args[0].toUpperCase()) == null) {
                    Message.send(sender, "&cInvalid Enchantment type.");
                    Message.NO.play((Player) sender);
                } else {
                    Message.send(sender, "Enchant §8- &f" + args[0].toUpperCase());
                    Message.ITEM_EDIT.play((Player) sender);
                    // Action
                    ItemMeta itemMeta = ((Player) sender).getItemInHand().getItemMeta();
                    if (args[0].equalsIgnoreCase("all")) {
                        Arrays.stream(Enchantment.values()).forEach(itemMeta::removeEnchant);
                    } else {
                        itemMeta.removeEnchant(ItemUtils.asEnchantment(args[0].toUpperCase()));
                    }
                    ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                }
                return true;
            });

    @TInject
    private static SimpleCommandBuilder addFlag = SimpleCommandBuilder.create("addFlag", ItemTool.getInst())
            .silence()
            .forceRegister()
            .permission("itemTool.use")
            .description("添加物品标签")
            .tab((sender, args) -> {
                if (args.length == 1) {
                    return Arrays.stream(ItemFlag.values()).filter(type -> type.name().toUpperCase().startsWith(args[0].toUpperCase())).map(Enum::name).collect(Collectors.toList());
                }
                return Lists.newArrayList();
            })
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.send(sender, "&cCommand disabled on console.");
                } else if (ItemUtils.isNull(((Player) sender).getItemInHand())) {
                    Message.send(sender, "&cInvalid item.");
                    Message.NO.play((Player) sender);
                } else if (args.length == 0) {
                    Message.send(sender, "&cInvalid arguments.");
                    Message.NO.play((Player) sender);
                } else if (!args[0].equalsIgnoreCase("all") && ItemUtils.asItemFlag(args[0].toUpperCase()) == null) {
                    Message.send(sender, "&cInvalid ItemFlag.");
                    Message.NO.play((Player) sender);
                } else {
                    Message.send(sender, "Flag §8+ &f" + args[0].toUpperCase());
                    Message.ITEM_EDIT.play((Player) sender);
                    // Action
                    ItemMeta itemMeta = ((Player) sender).getItemInHand().getItemMeta();
                    if (args[0].equalsIgnoreCase("all")) {
                        itemMeta.addItemFlags(ItemFlag.values());
                    } else {
                        itemMeta.addItemFlags(ItemUtils.asItemFlag(args[0].toUpperCase()));
                    }
                    ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                }
                return true;
            });

    @TInject
    private static SimpleCommandBuilder removeFlag = SimpleCommandBuilder.create("removeFlag", ItemTool.getInst())
            .silence()
            .forceRegister()
            .permission("itemTool.use")
            .description("移除物品标签")
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.send(sender, "&cCommand disabled on console.");
                } else if (ItemUtils.isNull(((Player) sender).getItemInHand())) {
                    Message.send(sender, "&cInvalid item.");
                    Message.NO.play((Player) sender);
                } else if (args.length == 0) {
                    Message.send(sender, "&cInvalid arguments.");
                    Message.NO.play((Player) sender);
                } else if (!args[0].equalsIgnoreCase("all") && ItemUtils.asItemFlag(args[0].toUpperCase()) == null) {
                    Message.send(sender, "&cInvalid ItemFlag.");
                    Message.NO.play((Player) sender);
                } else {
                    Message.send(sender, "Flag §8- &f" + args[0].toUpperCase());
                    Message.ITEM_EDIT.play((Player) sender);
                    // Action
                    ItemMeta itemMeta = ((Player) sender).getItemInHand().getItemMeta();
                    if (args[0].equalsIgnoreCase("all")) {
                        itemMeta.removeItemFlags(ItemFlag.values());
                    } else {
                        itemMeta.removeItemFlags(ItemUtils.asItemFlag(args[0].toUpperCase()));
                    }
                    ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                }
                return true;
            });

    @TInject
    private static SimpleCommandBuilder setUnbreakable = SimpleCommandBuilder.create("setUnbreakable", ItemTool.getInst())
            .silence()
            .forceRegister()
            .aliases("unbreakable")
            .permission("itemTool.use")
            .description("设置物品无法破坏")
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.send(sender, "&cCommand disabled on console.");
                } else if (ItemUtils.isNull(((Player) sender).getItemInHand())) {
                    Message.send(sender, "&cInvalid item.");
                    Message.NO.play((Player) sender);
                } else if (args.length == 0) {
                    Message.send(sender, "&cInvalid arguments.");
                    Message.NO.play((Player) sender);
                } else {
                    Message.send(sender, "Unbreakable §8-> &f" + String.valueOf(NumberUtils.getBooleanAbbreviation(args[0])).toUpperCase());
                    Message.ITEM_EDIT.play((Player) sender);
                    // Action
                    ItemMeta itemMeta = ((Player) sender).getItemInHand().getItemMeta();
                    try {
                        itemMeta.setUnbreakable(NumberUtils.getBooleanAbbreviation(args[0]));
                    } catch (Throwable ignored) {
                        try {
                            itemMeta.spigot().setUnbreakable(NumberUtils.getBooleanAbbreviation(args[0]));
                        } catch (Throwable ignored2) {
                        }
                    }
                    ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                }
                return true;
            });
}
