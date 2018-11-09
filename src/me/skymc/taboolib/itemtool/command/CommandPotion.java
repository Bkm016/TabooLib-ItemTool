package me.skymc.taboolib.itemtool.command;

import me.skymc.taboolib.commands.builder.SimpleCommandBuilder;
import me.skymc.taboolib.common.inject.TInject;
import me.skymc.taboolib.inventory.ItemUtils;
import me.skymc.taboolib.itemtool.ItemTool;
import me.skymc.taboolib.itemtool.util.Message;
import me.skymc.taboolib.itemtool.util.Util;
import me.skymc.taboolib.other.NumberUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;
import org.bukkit.util.NumberConversions;

/**
 * @Author 坏黑
 * @Since 2018-10-13 23:52
 */
public class CommandPotion {

    @TInject
    private static SimpleCommandBuilder addPotion = SimpleCommandBuilder.create("addPotion", ItemTool.getInst())
            .silence()
            .forceRegister()
            .permission("itemTool.use")
            .description("添加药水效果")
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.send(sender, "&cCommand disabled on console.");
                } else if (ItemUtils.isNull(((Player) sender).getItemInHand()) || !(((Player) sender).getItemInHand().getItemMeta() instanceof PotionMeta)) {
                    Message.send(sender, "&cInvalid item.");
                    Message.NO.play((Player) sender);
                } else if (args.length < 3) {
                    Message.send(sender, "&cInvalid arguments.");
                    Message.NO.play((Player) sender);
                } else if (ItemUtils.asPotionEffectType(args[0].toUpperCase()) == null) {
                    Message.send(sender, "&cInvalid PotionEffect type.");
                    Message.NO.play((Player) sender);
                } else if (NumberConversions.toInt(args[1]) <= 0) {
                    Message.send(sender, "&cInvalid PotionEffect duration.");
                    Message.NO.play((Player) sender);
                } else if (NumberConversions.toInt(args[2]) <= 0) {
                    Message.send(sender, "&cInvalid PotionEffect amplifier.");
                    Message.NO.play((Player) sender);
                } else {
                    Message.send(sender, "PotionEffect §8+ &f" + args[0].toUpperCase() + ":" + args[1] + ":" + args[2]);
                    Message.ITEM_EDIT.play((Player) sender);
                    // Action
                    PotionMeta itemMeta = (PotionMeta) ((Player) sender).getItemInHand().getItemMeta();
                    itemMeta.addCustomEffect(new PotionEffect(ItemUtils.asPotionEffectType(args[0].toUpperCase()), NumberConversions.toInt(args[1]), NumberConversions.toInt(args[2])), true);
                    ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                }
                return true;
            });

    @TInject
    private static SimpleCommandBuilder removePotion = SimpleCommandBuilder.create("removePotion", ItemTool.getInst())
            .silence()
            .forceRegister()
            .permission("itemTool.use")
            .description("移除物品效果")
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.send(sender, "&cCommand disabled on console.");
                } else if (ItemUtils.isNull(((Player) sender).getItemInHand()) || !(((Player) sender).getItemInHand().getItemMeta() instanceof PotionMeta)) {
                    Message.send(sender, "&cInvalid item.");
                    Message.NO.play((Player) sender);
                } else if (args.length == 0) {
                    Message.send(sender, "&cInvalid arguments.");
                    Message.NO.play((Player) sender);
                } else if (!args[0].equalsIgnoreCase("all") && ItemUtils.asPotionEffectType(args[0].toUpperCase()) == null) {
                    Message.send(sender, "&cInvalid PotionEffect type.");
                    Message.NO.play((Player) sender);
                } else {
                    Message.send(sender, "PotionEffect §8- &f" + args[0].toUpperCase());
                    Message.ITEM_EDIT.play((Player) sender);
                    // Action
                    PotionMeta itemMeta = (PotionMeta) ((Player) sender).getItemInHand().getItemMeta();
                    if (args[0].equalsIgnoreCase("all")) {
                        itemMeta.clearCustomEffects();
                    } else {
                        itemMeta.removeCustomEffect(ItemUtils.asPotionEffectType(args[0].toUpperCase()));
                    }
                    ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                }
                return true;
            });

    @TInject
    private static SimpleCommandBuilder setPotionColor = SimpleCommandBuilder.create("setPotionColor", ItemTool.getInst())
            .silence()
            .forceRegister()
            .permission("itemTool.use")
            .description("设置药水颜色")
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.send(sender, "&cCommand disabled on console.");
                } else if (ItemUtils.isNull(((Player) sender).getItemInHand()) || !(((Player) sender).getItemInHand().getItemMeta() instanceof PotionMeta)) {
                    Message.send(sender, "&cInvalid item.");
                    Message.NO.play((Player) sender);
                } else if (args.length == 0) {
                    Message.send(sender, "&cInvalid arguments.");
                    Message.NO.play((Player) sender);
                } else if (Util.asColor(args[0]) == null) {
                    Message.send(sender, "&cInvalid PotionEffect color.");
                    Message.NO.play((Player) sender);
                } else {
                    Message.send(sender, "PotionColor §8-> &f" + args[0].toUpperCase());
                    Message.ITEM_EDIT.play((Player) sender);
                    // Action
                    PotionMeta itemMeta = (PotionMeta) ((Player) sender).getItemInHand().getItemMeta();
                    try {
                        itemMeta.setColor(ItemUtils.asColor(args[0]));
                    } catch (Throwable ignored) {
                    }
                    ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                }
                return true;
            });

    @TInject
    private static SimpleCommandBuilder setBasePotion = SimpleCommandBuilder.create("setBasePotion", ItemTool.getInst())
            .silence()
            .forceRegister()
            .aliases("setMainPotion")
            .permission("itemTool.use")
            .description("设置基础药水效果")
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.send(sender, "&cCommand disabled on console.");
                } else if (ItemUtils.isNull(((Player) sender).getItemInHand()) || !(((Player) sender).getItemInHand().getItemMeta() instanceof PotionMeta)) {
                    Message.send(sender, "&cInvalid item.");
                    Message.NO.play((Player) sender);
                } else if (args.length < 3) {
                    Message.send(sender, "&cInvalid arguments.");
                    Message.NO.play((Player) sender);
                } else if (PotionType.getByEffect(ItemUtils.asPotionEffectType(args[0].toUpperCase())) == null) {
                    Message.send(sender, "&cInvalid PotionEffect type.");
                    Message.NO.play((Player) sender);
                } else {
                    PotionMeta itemMeta = (PotionMeta) ((Player) sender).getItemInHand().getItemMeta();
                    try {
                        itemMeta.setBasePotionData(new PotionData(PotionType.getByEffect(ItemUtils.asPotionEffectType(args[0].toUpperCase())), NumberUtils.getBooleanAbbreviation(args[1]), NumberUtils.getBooleanAbbreviation(args[2])));
                        // Notify
                        Message.send(sender, "BasePotionEffect §8-> &f" + args[0].toUpperCase() + ":" + args[1] + ":" + args[2]);
                        Message.ITEM_EDIT.play((Player) sender);
                    } catch (Throwable e) {
                        switch (e.getMessage()) {
                            case "Potion Type is not upgradable":
                                Message.send(sender, "&cPotion Type is not upgradable");
                                Message.NO.play((Player) sender);
                                break;
                            case "Potion Type is not extendable":
                                Message.send(sender, "&cPotion Type is not extendable");
                                Message.NO.play((Player) sender);
                                break;
                            case "Potion cannot be both extended and upgraded":
                                Message.send(sender, "&cPotion cannot be both extended and upgraded");
                                Message.NO.play((Player) sender);
                                break;
                            default:
                                try {
                                    itemMeta.setMainEffect(ItemUtils.asPotionEffectType(args[0].toUpperCase()));
                                } catch (Throwable ignored2) {
                                }
                                // Notify
                                Message.send(sender, "BasePotionEffect §8-> &f" + args[0].toUpperCase());
                                Message.ITEM_EDIT.play((Player) sender);
                                break;
                        }
                    }
                    ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                }
                return true;
            });

}
