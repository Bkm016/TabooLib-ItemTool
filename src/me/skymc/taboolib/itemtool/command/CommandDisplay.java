package me.skymc.taboolib.itemtool.command;

import com.google.common.collect.Lists;
import com.ilummc.tlib.resources.TLocale;
import me.skymc.taboolib.commands.builder.SimpleCommandBuilder;
import me.skymc.taboolib.common.inject.TInject;
import me.skymc.taboolib.inventory.ItemUtils;
import me.skymc.taboolib.itemtool.ItemTool;
import me.skymc.taboolib.itemtool.util.Message;
import me.skymc.taboolib.itemtool.util.Util;
import me.skymc.taboolib.string.ArrayUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.NumberConversions;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author 坏黑
 * @Since 2018-10-12 23:09
 */
public class CommandDisplay {

    @TInject
    private static SimpleCommandBuilder setName = SimpleCommandBuilder.create("setName", ItemTool.getInst())
            .silence()
            .forceRegister()
            .permission("itemTool.use")
            .description("设置物品名称")
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
                    Message.send(sender, "Name §8-> &f" + ArrayUtils.arrayJoin(args, 0));
                    Message.ITEM_EDIT.play((Player) sender);
                    // Action
                    ItemMeta itemMeta = ((Player) sender).getItemInHand().getItemMeta();
                    itemMeta.setDisplayName(TLocale.Translate.setColored(ArrayUtils.arrayJoin(args, 0)));
                    ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                }
                return true;
            });

    @TInject
    private static SimpleCommandBuilder replaceName = SimpleCommandBuilder.create("replaceName", ItemTool.getInst())
            .silence()
            .forceRegister()
            .permission("itemTool.use")
            .description("替换物品名称")
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.send(sender, "&cCommand disabled on console.");
                } else if (ItemUtils.isNull(((Player) sender).getItemInHand())) {
                    Message.send(sender, "&cInvalid item.");
                    Message.NO.play((Player) sender);
                } else if (args.length < 2) {
                    Message.send(sender, "&cInvalid arguments.");
                    Message.NO.play((Player) sender);
                } else {
                    Message.send(sender, "Name:" + args[0] + "&r &8-> &f" + ArrayUtils.arrayJoin(args, 1));
                    Message.ITEM_EDIT.play((Player) sender);
                    // Action
                    ItemMeta itemMeta = ((Player) sender).getItemInHand().getItemMeta();
                    List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : Lists.newArrayList();
                    if (itemMeta.hasDisplayName()) {
                        itemMeta.setDisplayName(itemMeta.getDisplayName().replace(TLocale.Translate.setColored(args[0]), TLocale.Translate.setColored(ArrayUtils.arrayJoin(args, 1))));
                    }
                    itemMeta.setLore(lore);
                    ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                }
                return true;
            });

    @TInject
    private static SimpleCommandBuilder replaceNameRegex = SimpleCommandBuilder.create("replaceNameRegex", ItemTool.getInst())
            .silence()
            .forceRegister()
            .permission("itemTool.use")
            .description("替换物品名称（Regex）")
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.send(sender, "&cCommand disabled on console.");
                } else if (ItemUtils.isNull(((Player) sender).getItemInHand())) {
                    Message.send(sender, "&cInvalid item.");
                    Message.NO.play((Player) sender);
                } else if (args.length < 2) {
                    Message.send(sender, "&cInvalid arguments.");
                    Message.NO.play((Player) sender);
                } else {
                    Message.send(sender, "Name:" + args[0] + "&r &8-> &f" + ArrayUtils.arrayJoin(args, 1));
                    Message.ITEM_EDIT.play((Player) sender);
                    // Action
                    ItemMeta itemMeta = ((Player) sender).getItemInHand().getItemMeta();
                    List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : Lists.newArrayList();
                    if (itemMeta.hasDisplayName()) {
                        itemMeta.setDisplayName(itemMeta.getDisplayName().replaceAll(TLocale.Translate.setColored(args[0]), TLocale.Translate.setColored(ArrayUtils.arrayJoin(args, 1))));
                    }
                    itemMeta.setLore(lore);
                    ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                }
                return true;
            });

    @TInject
    private static SimpleCommandBuilder addLore = SimpleCommandBuilder.create("addLore", ItemTool.getInst())
            .silence()
            .forceRegister()
            .permission("itemTool.use")
            .description("添加物品描述")
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
                    Message.send(sender, "Lore §8+ &f" + ArrayUtils.arrayJoin(args, 0));
                    Message.ITEM_EDIT.play((Player) sender);
                    // Action
                    ItemMeta itemMeta = ((Player) sender).getItemInHand().getItemMeta();
                    List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : Lists.newArrayList();
                    lore.add(TLocale.Translate.setColored(ArrayUtils.arrayJoin(args, 0)));
                    itemMeta.setLore(lore);
                    ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                }
                return true;
            });

    @TInject
    private static SimpleCommandBuilder insertLore = SimpleCommandBuilder.create("insertLore", ItemTool.getInst())
            .silence()
            .forceRegister()
            .permission("itemTool.use")
            .description("插入物品描述")
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.send(sender, "&cCommand disabled on console.");
                } else if (ItemUtils.isNull(((Player) sender).getItemInHand())) {
                    Message.send(sender, "&cInvalid item.");
                    Message.NO.play((Player) sender);
                } else if (args.length < 2) {
                    Message.send(sender, "&cInvalid arguments.");
                    Message.NO.play((Player) sender);
                } else if (!Util.isNumber(args[0])) {
                    Message.send(sender, "&cInvalid line.");
                    Message.NO.play((Player) sender);
                }else {
                    Message.send(sender, "Line:" + NumberConversions.toInt(args[0]) + " §8+ &f" + ArrayUtils.arrayJoin(args, 1));
                    Message.ITEM_EDIT.play((Player) sender);
                    // Action
                    ItemMeta itemMeta = ((Player) sender).getItemInHand().getItemMeta();
                    List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : Lists.newArrayList();
                    while (lore.size() < NumberConversions.toInt(args[0])) {
                        lore.add("");
                    }
                    if (lore.size() == NumberConversions.toInt(args[0])) {
                        lore.set(NumberConversions.toInt(args[0]) - 1, TLocale.Translate.setColored(ArrayUtils.arrayJoin(args, 1)));
                    } else {
                        lore.add(NumberConversions.toInt(args[0]) - 1, TLocale.Translate.setColored(ArrayUtils.arrayJoin(args, 1)));
                    }
                    itemMeta.setLore(lore);
                    ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                }
                return true;
            });

    @TInject
    private static SimpleCommandBuilder setLore = SimpleCommandBuilder.create("setLore", ItemTool.getInst())
            .silence()
            .forceRegister()
            .permission("itemTool.use")
            .description("设置物品描述")
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.send(sender, "&cCommand disabled on console.");
                } else if (ItemUtils.isNull(((Player) sender).getItemInHand())) {
                    Message.send(sender, "&cInvalid item.");
                    Message.NO.play((Player) sender);
                } else if (args.length < 2) {
                    Message.send(sender, "&cInvalid arguments.");
                    Message.NO.play((Player) sender);
                } else if (NumberConversions.toInt(args[0]) == 0) {
                    Message.send(sender, "&cInvalid line.");
                    Message.NO.play((Player) sender);
                } else {
                    Message.send(sender, "Line:" + NumberConversions.toInt(args[0]) + " §8-> &f" + ArrayUtils.arrayJoin(args, 1));
                    Message.ITEM_EDIT.play((Player) sender);
                    // Action
                    ItemMeta itemMeta = ((Player) sender).getItemInHand().getItemMeta();
                    List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : Lists.newArrayList();
                    while (lore.size() < NumberConversions.toInt(args[0])) {
                        lore.add("");
                    }
                    lore.set(NumberConversions.toInt(args[0]) - 1, TLocale.Translate.setColored(ArrayUtils.arrayJoin(args, 1)));
                    itemMeta.setLore(lore);
                    ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                }
                return true;
            });

    @TInject
    private static SimpleCommandBuilder removeLore = SimpleCommandBuilder.create("removeLore", ItemTool.getInst())
            .silence()
            .forceRegister()
            .permission("itemTool.use")
            .description("移除物品描述")
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.send(sender, "&cCommand disabled on console.");
                } else if (ItemUtils.isNull(((Player) sender).getItemInHand())) {
                    Message.send(sender, "&cInvalid item.");
                    Message.NO.play((Player) sender);
                } else if (args.length == 0) {
                    Message.send(sender, "&cInvalid arguments.");
                    Message.NO.play((Player) sender);
                } else if (NumberConversions.toInt(args[0]) == 0) {
                    Message.send(sender, "&cInvalid line.");
                    Message.NO.play((Player) sender);
                } else {
                    Message.send(sender, "Line:" + NumberConversions.toInt(args[0]) + " §8-> &4REMOVE");
                    Message.ITEM_EDIT.play((Player) sender);
                    // Action
                    ItemMeta itemMeta = ((Player) sender).getItemInHand().getItemMeta();
                    List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : Lists.newArrayList();
                    if (lore.size() >= NumberConversions.toInt(args[0])) {
                        lore.remove(NumberConversions.toInt(args[0]) - 1);
                    }
                    itemMeta.setLore(lore);
                    ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                }
                return true;
            });

    @TInject
    private static SimpleCommandBuilder replaceLore = SimpleCommandBuilder.create("replaceLore", ItemTool.getInst())
            .silence()
            .forceRegister()
            .permission("itemTool.use")
            .description("替换物品描述")
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.send(sender, "&cCommand disabled on console.");
                } else if (ItemUtils.isNull(((Player) sender).getItemInHand())) {
                    Message.send(sender, "&cInvalid item.");
                    Message.NO.play((Player) sender);
                } else if (args.length < 2) {
                    Message.send(sender, "&cInvalid arguments.");
                    Message.NO.play((Player) sender);
                } else {
                    Message.send(sender, "Lore:" + args[0] + "&r &8-> &f" + ArrayUtils.arrayJoin(args, 1));
                    Message.ITEM_EDIT.play((Player) sender);
                    // Action
                    ItemMeta itemMeta = ((Player) sender).getItemInHand().getItemMeta();
                    List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : Lists.newArrayList();
                    for (int i = 0; i < lore.size(); i++) {
                        lore.set(i, lore.get(i).replace(TLocale.Translate.setColored(args[0]), TLocale.Translate.setColored(ArrayUtils.arrayJoin(args, 1))));
                    }
                    itemMeta.setLore(lore);
                    ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                }
                return true;
            });

    @TInject
    private static SimpleCommandBuilder replaceLoreRegex = SimpleCommandBuilder.create("replaceLoreRegex", ItemTool.getInst())
            .silence()
            .forceRegister()
            .permission("itemTool.use")
            .description("替换物品描述（Regex）")
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.send(sender, "&cCommand disabled on console.");
                } else if (ItemUtils.isNull(((Player) sender).getItemInHand())) {
                    Message.send(sender, "&cInvalid item.");
                    Message.NO.play((Player) sender);
                } else if (args.length < 2) {
                    Message.send(sender, "&cInvalid arguments.");
                    Message.NO.play((Player) sender);
                } else {
                    Message.send(sender, "Lore:" + args[0] + "&r &8-> &f" + ArrayUtils.arrayJoin(args, 1));
                    Message.ITEM_EDIT.play((Player) sender);
                    // Action
                    ItemMeta itemMeta = ((Player) sender).getItemInHand().getItemMeta();
                    List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : Lists.newArrayList();
                    for (int i = 0; i < lore.size(); i++) {
                        lore.set(i, lore.get(i).replaceAll(TLocale.Translate.setColored(args[0]), TLocale.Translate.setColored(ArrayUtils.arrayJoin(args, 1))));
                    }
                    itemMeta.setLore(lore);
                    ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                }
                return true;
            });

    @TInject
    private static SimpleCommandBuilder clearLore = SimpleCommandBuilder.create("clearLore", ItemTool.getInst())
            .silence()
            .forceRegister()
            .aliases("cleanLore")
            .permission("itemTool.use")
            .description("清除物品描述")
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.send(sender, "&cCommand disabled on console.");
                } else if (ItemUtils.isNull(((Player) sender).getItemInHand())) {
                    Message.send(sender, "&cInvalid item.");
                    Message.NO.play((Player) sender);
                } else {
                    Message.send(sender, "Lore &8-> &4CLEAR");
                    Message.ITEM_EDIT.play((Player) sender);
                    // Action
                    ItemMeta itemMeta = ((Player) sender).getItemInHand().getItemMeta();
                    itemMeta.setLore(new ArrayList<>());
                    ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                }
                return true;
            });
}
