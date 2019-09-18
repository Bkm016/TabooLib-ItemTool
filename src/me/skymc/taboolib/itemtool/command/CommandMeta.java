package me.skymc.taboolib.itemtool.command;

import com.google.common.collect.Lists;
import io.izzel.taboolib.module.command.lite.CommandBuilder;
import io.izzel.taboolib.module.inject.TInject;
import io.izzel.taboolib.util.item.Items;
import io.izzel.taboolib.util.lite.Numbers;
import me.skymc.taboolib.itemtool.ItemTool;
import me.skymc.taboolib.itemtool.util.Message;
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
    private static CommandBuilder addEnchant = CommandBuilder.create("addEnchant", ItemTool.getInst())
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
                    Message.INSTANCE.send(sender, "&cCommand disabled on console.");
                } else if (Items.isNull(((Player) sender).getItemInHand())) {
                    Message.INSTANCE.send(sender, "&cInvalid item.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else if (args.length < 2) {
                    Message.INSTANCE.send(sender, "&cInvalid arguments.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else if (!args[0].equalsIgnoreCase("all") && Items.asEnchantment(args[0].toUpperCase()) == null) {
                    Message.INSTANCE.send(sender, "&cInvalid Enchantment type.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else if (NumberConversions.toInt(args[1]) <= 0) {
                    Message.INSTANCE.send(sender, "&cInvalid Enchantment level.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else {
                    Message.INSTANCE.send(sender, "Enchant §8+ &f" + args[0].toUpperCase() + ":" + args[1]);
                    Message.INSTANCE.getITEM_EDIT().play((Player) sender);
                    // Action
                    ItemMeta itemMeta = ((Player) sender).getItemInHand().getItemMeta();
                    if (args[0].equalsIgnoreCase("all")) {
                        Arrays.stream(Enchantment.values()).forEach(enchantment -> itemMeta.addEnchant(enchantment, NumberConversions.toInt(args[1]), true));
                    } else {
                        itemMeta.addEnchant(Items.asEnchantment(args[0].toUpperCase()), NumberConversions.toInt(args[1]), true);
                    }
                    ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                }
            });

    @TInject
    private static CommandBuilder removeEnchant = CommandBuilder.create("removeEnchant", ItemTool.getInst())
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
                    Message.INSTANCE.send(sender, "&cCommand disabled on console.");
                } else if (Items.isNull(((Player) sender).getItemInHand())) {
                    Message.INSTANCE.send(sender, "&cInvalid item.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else if (args.length == 0) {
                    Message.INSTANCE.send(sender, "&cInvalid arguments.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else if (!args[0].equalsIgnoreCase("all") && Items.asEnchantment(args[0].toUpperCase()) == null) {
                    Message.INSTANCE.send(sender, "&cInvalid Enchantment type.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else {
                    Message.INSTANCE.send(sender, "Enchant §8- &f" + args[0].toUpperCase());
                    Message.INSTANCE.getITEM_EDIT().play((Player) sender);
                    // Action
                    ItemMeta itemMeta = ((Player) sender).getItemInHand().getItemMeta();
                    if (args[0].equalsIgnoreCase("all")) {
                        Arrays.stream(Enchantment.values()).forEach(itemMeta::removeEnchant);
                    } else {
                        itemMeta.removeEnchant(Items.asEnchantment(args[0].toUpperCase()));
                    }
                    ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                }
            });

    @TInject
    private static CommandBuilder addFlag = CommandBuilder.create("addFlag", ItemTool.getInst())
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
                    Message.INSTANCE.send(sender, "&cCommand disabled on console.");
                } else if (Items.isNull(((Player) sender).getItemInHand())) {
                    Message.INSTANCE.send(sender, "&cInvalid item.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else if (args.length == 0) {
                    Message.INSTANCE.send(sender, "&cInvalid arguments.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else if (!args[0].equalsIgnoreCase("all") && Items.asItemFlag(args[0].toUpperCase()) == null) {
                    Message.INSTANCE.send(sender, "&cInvalid ItemFlag.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else {
                    Message.INSTANCE.send(sender, "Flag §8+ &f" + args[0].toUpperCase());
                    Message.INSTANCE.getITEM_EDIT().play((Player) sender);
                    // Action
                    ItemMeta itemMeta = ((Player) sender).getItemInHand().getItemMeta();
                    if (args[0].equalsIgnoreCase("all")) {
                        itemMeta.addItemFlags(ItemFlag.values());
                    } else {
                        itemMeta.addItemFlags(Items.asItemFlag(args[0].toUpperCase()));
                    }
                    ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                }
            });

    @TInject
    private static CommandBuilder removeFlag = CommandBuilder.create("removeFlag", ItemTool.getInst())
            .forceRegister()
            .permission("itemTool.use")
            .description("移除物品标签")
            .tab((sender, args) -> {
                if (args.length == 1) {
                    return Arrays.stream(ItemFlag.values()).filter(type -> type.name().toUpperCase().startsWith(args[0].toUpperCase())).map(Enum::name).collect(Collectors.toList());
                }
                return Lists.newArrayList();
            })
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.INSTANCE.send(sender, "&cCommand disabled on console.");
                } else if (Items.isNull(((Player) sender).getItemInHand())) {
                    Message.INSTANCE.send(sender, "&cInvalid item.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else if (args.length == 0) {
                    Message.INSTANCE.send(sender, "&cInvalid arguments.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else if (!args[0].equalsIgnoreCase("all") && Items.asItemFlag(args[0].toUpperCase()) == null) {
                    Message.INSTANCE.send(sender, "&cInvalid ItemFlag.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else {
                    Message.INSTANCE.send(sender, "Flag §8- &f" + args[0].toUpperCase());
                    Message.INSTANCE.getITEM_EDIT().play((Player) sender);
                    // Action
                    ItemMeta itemMeta = ((Player) sender).getItemInHand().getItemMeta();
                    if (args[0].equalsIgnoreCase("all")) {
                        itemMeta.removeItemFlags(ItemFlag.values());
                    } else {
                        itemMeta.removeItemFlags(Items.asItemFlag(args[0].toUpperCase()));
                    }
                    ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                }
            });

    @TInject
    private static CommandBuilder setUnbreakable = CommandBuilder.create("setUnbreakable", ItemTool.getInst())
            .forceRegister()
            .aliases("unbreakable")
            .permission("itemTool.use")
            .description("设置物品无法破坏")
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.INSTANCE.send(sender, "&cCommand disabled on console.");
                } else if (Items.isNull(((Player) sender).getItemInHand())) {
                    Message.INSTANCE.send(sender, "&cInvalid item.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else if (args.length == 0) {
                    Message.INSTANCE.send(sender, "&cInvalid arguments.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else {
                    Message.INSTANCE.send(sender, "Unbreakable §8-> &f" + String.valueOf(Numbers.getBoolean(args[0])).toUpperCase());
                    Message.INSTANCE.getITEM_EDIT().play((Player) sender);
                    // Action
                    ItemMeta itemMeta = ((Player) sender).getItemInHand().getItemMeta();
                    try {
                        itemMeta.setUnbreakable(Numbers.getBoolean(args[0]));
                    } catch (Throwable ignored) {
                        try {
                            itemMeta.spigot().setUnbreakable(Numbers.getBoolean(args[0]));
                        } catch (Throwable ignored2) {
                        }
                    }
                    ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                }
            });
}
