package me.skymc.taboolib.itemtool.command;

import com.google.common.collect.Lists;
import io.izzel.taboolib.module.command.lite.CommandBuilder;
import io.izzel.taboolib.module.inject.TInject;
import io.izzel.taboolib.module.locale.TLocale;
import io.izzel.taboolib.util.ArrayUtil;
import io.izzel.taboolib.util.item.Items;
import me.skymc.taboolib.itemtool.ItemTool;
import me.skymc.taboolib.itemtool.util.Message;
import me.skymc.taboolib.itemtool.util.Util;
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
    private static CommandBuilder setName = CommandBuilder.create("setName", ItemTool.getInst())
            .forceRegister()
            .permission("itemTool.use")
            .description("设置物品名称")
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.send(sender, "&cCommand disabled on console.");
                } else if (Items.isNull(((Player) sender).getItemInHand())) {
                    Message.send(sender, "&cInvalid item.");
                    Message.NO.play((Player) sender);
                } else if (args.length == 0) {
                    Message.send(sender, "&cInvalid arguments.");
                    Message.NO.play((Player) sender);
                } else {
                    Message.send(sender, "Name §8-> &f" + ArrayUtil.arrayJoin(args, 0));
                    Message.ITEM_EDIT.play((Player) sender);
                    // Action
                    ItemMeta itemMeta = ((Player) sender).getItemInHand().getItemMeta();
                    itemMeta.setDisplayName(TLocale.Translate.setColored(ArrayUtil.arrayJoin(args, 0)));
                    ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                }
            });

    @TInject
    private static CommandBuilder replaceName = CommandBuilder.create("replaceName", ItemTool.getInst())
            .forceRegister()
            .permission("itemTool.use")
            .description("替换物品名称")
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.send(sender, "&cCommand disabled on console.");
                } else if (Items.isNull(((Player) sender).getItemInHand())) {
                    Message.send(sender, "&cInvalid item.");
                    Message.NO.play((Player) sender);
                } else if (args.length < 2) {
                    Message.send(sender, "&cInvalid arguments.");
                    Message.NO.play((Player) sender);
                } else {
                    Message.send(sender, "Name:" + args[0] + "&r &8-> &f" + ArrayUtil.arrayJoin(args, 1));
                    Message.ITEM_EDIT.play((Player) sender);
                    // Action
                    ItemMeta itemMeta = ((Player) sender).getItemInHand().getItemMeta();
                    List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : Lists.newArrayList();
                    if (itemMeta.hasDisplayName()) {
                        itemMeta.setDisplayName(itemMeta.getDisplayName().replace(TLocale.Translate.setColored(args[0]), TLocale.Translate.setColored(ArrayUtil.arrayJoin(args, 1))));
                    }
                    itemMeta.setLore(lore);
                    ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                }
            });

    @TInject
    private static CommandBuilder replaceNameRegex = CommandBuilder.create("replaceNameRegex", ItemTool.getInst())
            .forceRegister()
            .permission("itemTool.use")
            .description("替换物品名称（Regex）")
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.send(sender, "&cCommand disabled on console.");
                } else if (Items.isNull(((Player) sender).getItemInHand())) {
                    Message.send(sender, "&cInvalid item.");
                    Message.NO.play((Player) sender);
                } else if (args.length < 2) {
                    Message.send(sender, "&cInvalid arguments.");
                    Message.NO.play((Player) sender);
                } else {
                    Message.send(sender, "Name:" + args[0] + "&r &8-> &f" + ArrayUtil.arrayJoin(args, 1));
                    Message.ITEM_EDIT.play((Player) sender);
                    // Action
                    ItemMeta itemMeta = ((Player) sender).getItemInHand().getItemMeta();
                    List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : Lists.newArrayList();
                    if (itemMeta.hasDisplayName()) {
                        itemMeta.setDisplayName(itemMeta.getDisplayName().replaceAll(TLocale.Translate.setColored(args[0]), TLocale.Translate.setColored(ArrayUtil.arrayJoin(args, 1))));
                    }
                    itemMeta.setLore(lore);
                    ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                }
            });

    @TInject
    private static CommandBuilder addLore = CommandBuilder.create("addLore", ItemTool.getInst())
            .forceRegister()
            .permission("itemTool.use")
            .description("添加物品描述")
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.send(sender, "&cCommand disabled on console.");
                } else if (Items.isNull(((Player) sender).getItemInHand())) {
                    Message.send(sender, "&cInvalid item.");
                    Message.NO.play((Player) sender);
                } else if (args.length == 0) {
                    Message.send(sender, "&cInvalid arguments.");
                    Message.NO.play((Player) sender);
                } else {
                    Message.send(sender, "Lore §8+ &f" + ArrayUtil.arrayJoin(args, 0));
                    Message.ITEM_EDIT.play((Player) sender);
                    // Action
                    ItemMeta itemMeta = ((Player) sender).getItemInHand().getItemMeta();
                    List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : Lists.newArrayList();
                    lore.add(TLocale.Translate.setColored(ArrayUtil.arrayJoin(args, 0)));
                    itemMeta.setLore(lore);
                    ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                }
            });

    @TInject
    private static CommandBuilder insertLore = CommandBuilder.create("insertLore", ItemTool.getInst())
            .forceRegister()
            .permission("itemTool.use")
            .description("插入物品描述")
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.send(sender, "&cCommand disabled on console.");
                } else if (Items.isNull(((Player) sender).getItemInHand())) {
                    Message.send(sender, "&cInvalid item.");
                    Message.NO.play((Player) sender);
                } else if (args.length < 2) {
                    Message.send(sender, "&cInvalid arguments.");
                    Message.NO.play((Player) sender);
                } else if (!Util.isNumber(args[0]) ||  NumberConversions.toInt(args[0]) < 1) {
                    Message.send(sender, "&cInvalid line.");
                    Message.NO.play((Player) sender);
                } else {
                    Message.send(sender, "Line:" + NumberConversions.toInt(args[0]) + " §8+ &f" + ArrayUtil.arrayJoin(args, 1));
                    Message.ITEM_EDIT.play((Player) sender);
                    // Action
                    ItemMeta itemMeta = ((Player) sender).getItemInHand().getItemMeta();
                    List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : Lists.newArrayList();
                    while (lore.size() < NumberConversions.toInt(args[0])) {
                        lore.add("");
                    }
                    if (lore.size() == NumberConversions.toInt(args[0]) - 1) {
                        lore.set(NumberConversions.toInt(args[0]) - 1, TLocale.Translate.setColored(ArrayUtil.arrayJoin(args, 1)));
                    } else {
                        lore.add(NumberConversions.toInt(args[0]) - 1, TLocale.Translate.setColored(ArrayUtil.arrayJoin(args, 1)));
                    }
                    itemMeta.setLore(lore);
                    ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                }
            });

    @TInject
    private static CommandBuilder setLore = CommandBuilder.create("setLore", ItemTool.getInst())
            .forceRegister()
            .permission("itemTool.use")
            .description("设置物品描述")
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.send(sender, "&cCommand disabled on console.");
                } else if (Items.isNull(((Player) sender).getItemInHand())) {
                    Message.send(sender, "&cInvalid item.");
                    Message.NO.play((Player) sender);
                } else if (args.length < 2) {
                    Message.send(sender, "&cInvalid arguments.");
                    Message.NO.play((Player) sender);
                } else if (NumberConversions.toInt(args[0]) == 0) {
                    Message.send(sender, "&cInvalid line.");
                    Message.NO.play((Player) sender);
                } else {
                    Message.send(sender, "Line:" + NumberConversions.toInt(args[0]) + " §8-> &f" + ArrayUtil.arrayJoin(args, 1));
                    Message.ITEM_EDIT.play((Player) sender);
                    // Action
                    ItemMeta itemMeta = ((Player) sender).getItemInHand().getItemMeta();
                    List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : Lists.newArrayList();
                    while (lore.size() < NumberConversions.toInt(args[0])) {
                        lore.add("");
                    }
                    lore.set(NumberConversions.toInt(args[0]) - 1, TLocale.Translate.setColored(ArrayUtil.arrayJoin(args, 1)));
                    itemMeta.setLore(lore);
                    ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                }
            });

    @TInject
    private static CommandBuilder removeLore = CommandBuilder.create("removeLore", ItemTool.getInst())
            .forceRegister()
            .permission("itemTool.use")
            .description("移除物品描述")
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.send(sender, "&cCommand disabled on console.");
                } else if (Items.isNull(((Player) sender).getItemInHand())) {
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
            });

    @TInject
    private static CommandBuilder replaceLore = CommandBuilder.create("replaceLore", ItemTool.getInst())
            .forceRegister()
            .permission("itemTool.use")
            .description("替换物品描述")
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.send(sender, "&cCommand disabled on console.");
                } else if (Items.isNull(((Player) sender).getItemInHand())) {
                    Message.send(sender, "&cInvalid item.");
                    Message.NO.play((Player) sender);
                } else if (args.length < 2) {
                    Message.send(sender, "&cInvalid arguments.");
                    Message.NO.play((Player) sender);
                } else {
                    Message.send(sender, "Lore:" + args[0] + "&r &8-> &f" + ArrayUtil.arrayJoin(args, 1));
                    Message.ITEM_EDIT.play((Player) sender);
                    // Action
                    ItemMeta itemMeta = ((Player) sender).getItemInHand().getItemMeta();
                    List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : Lists.newArrayList();
                    for (int i = 0; i < lore.size(); i++) {
                        lore.set(i, lore.get(i).replace(TLocale.Translate.setColored(args[0]), TLocale.Translate.setColored(ArrayUtil.arrayJoin(args, 1))));
                    }
                    itemMeta.setLore(lore);
                    ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                }
            });

    @TInject
    private static CommandBuilder replaceLoreRegex = CommandBuilder.create("replaceLoreRegex", ItemTool.getInst())
            .forceRegister()
            .permission("itemTool.use")
            .description("替换物品描述（Regex）")
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.send(sender, "&cCommand disabled on console.");
                } else if (Items.isNull(((Player) sender).getItemInHand())) {
                    Message.send(sender, "&cInvalid item.");
                    Message.NO.play((Player) sender);
                } else if (args.length < 2) {
                    Message.send(sender, "&cInvalid arguments.");
                    Message.NO.play((Player) sender);
                } else {
                    Message.send(sender, "Lore:" + args[0] + "&r &8-> &f" + ArrayUtil.arrayJoin(args, 1));
                    Message.ITEM_EDIT.play((Player) sender);
                    // Action
                    ItemMeta itemMeta = ((Player) sender).getItemInHand().getItemMeta();
                    List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : Lists.newArrayList();
                    for (int i = 0; i < lore.size(); i++) {
                        lore.set(i, lore.get(i).replaceAll(TLocale.Translate.setColored(args[0]), TLocale.Translate.setColored(ArrayUtil.arrayJoin(args, 1))));
                    }
                    itemMeta.setLore(lore);
                    ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                }
            });

    @TInject
    private static CommandBuilder clearLore = CommandBuilder.create("clearLore", ItemTool.getInst())
            .forceRegister()
            .aliases("cleanLore")
            .permission("itemTool.use")
            .description("清除物品描述")
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.send(sender, "&cCommand disabled on console.");
                } else if (Items.isNull(((Player) sender).getItemInHand())) {
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
            });
}
