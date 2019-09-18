package me.skymc.taboolib.itemtool.command;

import com.google.common.collect.Lists;
import io.izzel.taboolib.module.command.lite.CommandBuilder;
import io.izzel.taboolib.module.inject.TInject;
import io.izzel.taboolib.util.item.Items;
import io.izzel.taboolib.util.lite.Numbers;
import me.skymc.taboolib.itemtool.ItemTool;
import me.skymc.taboolib.itemtool.util.Message;
import me.skymc.taboolib.itemtool.util.Util;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.util.NumberConversions;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @Author 坏黑
 * @Since 2018-10-13 23:52
 */
public class CommandPotion {

    @TInject
    private static CommandBuilder addPotion = CommandBuilder.create("addPotion", ItemTool.getInst())
            .forceRegister()
            .permission("itemTool.use")
            .description("添加药水效果")
            .tab((sender, args) -> {
                if (args.length == 1) {
                    return Arrays.stream(PotionEffectType.values()).filter(type -> type != null && type.getName().toUpperCase().startsWith(args[0].toUpperCase())).map(PotionEffectType::getName).collect(Collectors.toList());
                }
                return Lists.newArrayList();
            })
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.INSTANCE.send(sender, "&cCommand disabled on console.");
                } else if (Items.isNull(((Player) sender).getItemInHand()) || !(((Player) sender).getItemInHand().getItemMeta() instanceof PotionMeta)) {
                    Message.INSTANCE.send(sender, "&cInvalid item.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else if (args.length < 3) {
                    Message.INSTANCE.send(sender, "&cInvalid arguments.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else if (Items.asPotionEffectType(args[0].toUpperCase()) == null) {
                    Message.INSTANCE.send(sender, "&cInvalid PotionEffect type.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else if (NumberConversions.toInt(args[1]) <= 0) {
                    Message.INSTANCE.send(sender, "&cInvalid PotionEffect duration.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else if (NumberConversions.toInt(args[2]) < 0) {
                    Message.INSTANCE.send(sender, "&cInvalid PotionEffect amplifier.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else {
                    Message.INSTANCE.send(sender, "PotionEffect §8+ &f" + args[0].toUpperCase() + ":" + args[1] + ":" + args[2]);
                    Message.INSTANCE.getITEM_EDIT().play((Player) sender);
                    // Action
                    PotionMeta itemMeta = (PotionMeta) ((Player) sender).getItemInHand().getItemMeta();
                    itemMeta.addCustomEffect(new PotionEffect(Items.asPotionEffectType(args[0].toUpperCase()), NumberConversions.toInt(args[1]), NumberConversions.toInt(args[2])), true);
                    ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                }
            });

    @TInject
    private static CommandBuilder removePotion = CommandBuilder.create("removePotion", ItemTool.getInst())
            .forceRegister()
            .permission("itemTool.use")
            .description("移除物品效果")
            .tab((sender, args) -> {
                if (args.length == 1) {
                    return Arrays.stream(PotionEffectType.values()).filter(type -> type != null && type.getName().toUpperCase().startsWith(args[0].toUpperCase())).map(PotionEffectType::getName).collect(Collectors.toList());
                }
                return Lists.newArrayList();
            })
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.INSTANCE.send(sender, "&cCommand disabled on console.");
                } else if (Items.isNull(((Player) sender).getItemInHand()) || !(((Player) sender).getItemInHand().getItemMeta() instanceof PotionMeta)) {
                    Message.INSTANCE.send(sender, "&cInvalid item.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else if (args.length == 0) {
                    Message.INSTANCE.send(sender, "&cInvalid arguments.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else if (!args[0].equalsIgnoreCase("all") && Items.asPotionEffectType(args[0].toUpperCase()) == null) {
                    Message.INSTANCE.send(sender, "&cInvalid PotionEffect type.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else {
                    Message.INSTANCE.send(sender, "PotionEffect §8- &f" + args[0].toUpperCase());
                    Message.INSTANCE.getITEM_EDIT().play((Player) sender);
                    // Action
                    PotionMeta itemMeta = (PotionMeta) ((Player) sender).getItemInHand().getItemMeta();
                    if (args[0].equalsIgnoreCase("all")) {
                        itemMeta.clearCustomEffects();
                    } else {
                        itemMeta.removeCustomEffect(Items.asPotionEffectType(args[0].toUpperCase()));
                    }
                    ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                }
            });

    @TInject
    private static CommandBuilder setPotionColor = CommandBuilder.create("setPotionColor", ItemTool.getInst())
            .forceRegister()
            .permission("itemTool.use")
            .description("设置药水颜色")
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.INSTANCE.send(sender, "&cCommand disabled on console.");
                } else if (Items.isNull(((Player) sender).getItemInHand()) || !(((Player) sender).getItemInHand().getItemMeta() instanceof PotionMeta)) {
                    Message.INSTANCE.send(sender, "&cInvalid item.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else if (args.length == 0) {
                    Message.INSTANCE.send(sender, "&cInvalid arguments.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else if (Util.INSTANCE.asColor(args[0]) == null) {
                    Message.INSTANCE.send(sender, "&cInvalid PotionEffect color.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else {
                    Color color;
                    if (args[0].equalsIgnoreCase("r") || args[0].equalsIgnoreCase("random")) {
                        color = Color.fromBGR(Numbers.getRandom().nextInt(255), Numbers.getRandom().nextInt(255), Numbers.getRandom().nextInt(255));
                    } else {
                        color = Items.asColor(args[0]);
                    }
                    PotionMeta itemMeta = (PotionMeta) ((Player) sender).getItemInHand().getItemMeta();
                    try {
                        itemMeta.setColor(color);
                    } catch (Throwable ignored) {
                    }
                    ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                    // Message
                    Message.INSTANCE.send(sender, "PotionColor §8-> &f" + color.getBlue() + "-" + color.getGreen() + "-" + color.getRed());
                    Message.INSTANCE.getITEM_EDIT().play((Player) sender);
                }
            });

    @TInject
    private static CommandBuilder setBasePotion = CommandBuilder.create("setBasePotion", ItemTool.getInst())
            .forceRegister()
            .aliases("setMainPotion")
            .permission("itemTool.use")
            .description("设置基础药水效果")
            .tab((sender, args) -> {
                if (args.length == 1) {
                    return Arrays.stream(PotionType.values()).filter(type -> type.name().toUpperCase().startsWith(args[0].toUpperCase())).map(Enum::name).collect(Collectors.toList());
                }
                return Lists.newArrayList();
            })
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.INSTANCE.send(sender, "&cCommand disabled on console.");
                } else if (Items.isNull(((Player) sender).getItemInHand()) || !(((Player) sender).getItemInHand().getItemMeta() instanceof PotionMeta)) {
                    Message.INSTANCE.send(sender, "&cInvalid item.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else if (args.length < 3) {
                    Message.INSTANCE.send(sender, "&cInvalid arguments.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else if (PotionType.getByEffect(Items.asPotionEffectType(args[0].toUpperCase())) == null) {
                    Message.INSTANCE.send(sender, "&cInvalid PotionEffect type.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else {
                    PotionMeta itemMeta = (PotionMeta) ((Player) sender).getItemInHand().getItemMeta();
                    try {
                        itemMeta.setBasePotionData(new PotionData(PotionType.getByEffect(Items.asPotionEffectType(args[0].toUpperCase())), Numbers.getBoolean(args[1]), Numbers.getBoolean(args[2])));
                        // Notify
                        Message.INSTANCE.send(sender, "BasePotionEffect §8-> &f" + args[0].toUpperCase() + ":" + args[1] + ":" + args[2]);
                        Message.INSTANCE.getITEM_EDIT().play((Player) sender);
                    } catch (Throwable e) {
                        switch (e.getMessage()) {
                            case "Potion Type is not upgradable":
                                Message.INSTANCE.send(sender, "&cPotion Type is not upgradable");
                                Message.INSTANCE.getNO().play((Player) sender);
                                break;
                            case "Potion Type is not extendable":
                                Message.INSTANCE.send(sender, "&cPotion Type is not extendable");
                                Message.INSTANCE.getNO().play((Player) sender);
                                break;
                            case "Potion cannot be both extended and upgraded":
                                Message.INSTANCE.send(sender, "&cPotion cannot be both extended and upgraded");
                                Message.INSTANCE.getNO().play((Player) sender);
                                break;
                            default:
                                try {
                                    itemMeta.setMainEffect(Items.asPotionEffectType(args[0].toUpperCase()));
                                } catch (Throwable ignored2) {
                                }
                                // Notify
                                Message.INSTANCE.send(sender, "BasePotionEffect §8-> &f" + args[0].toUpperCase());
                                Message.INSTANCE.getITEM_EDIT().play((Player) sender);
                                break;
                        }
                    }
                    ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                }
            });

}
