package me.skymc.taboolib.itemtool.command;

import me.skymc.taboolib.commands.builder.SimpleCommandBuilder;
import me.skymc.taboolib.common.inject.TInject;
import me.skymc.taboolib.inventory.ItemUtils;
import me.skymc.taboolib.itemtool.ItemTool;
import me.skymc.taboolib.itemtool.util.Message;
import me.skymc.taboolib.itemtool.util.Util;
import org.bukkit.entity.Player;
import org.bukkit.util.NumberConversions;

/**
 * @Author 坏黑
 * @Since 2018-10-12 23:09
 */
public class CommandMaterial {

    @TInject
    private static SimpleCommandBuilder setMaterial = SimpleCommandBuilder.create("setMaterial", ItemTool.getInst())
            .silence()
            .forceRegister()
            .aliases("setType")
            .permission("itemTool.use")
            .description("设置物品材质")
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.send(sender, "&cCommand disabled on console.");
                } else if (ItemUtils.isNull(((Player) sender).getItemInHand())) {
                    Message.send(sender, "&cInvalid item.");
                    Message.NO.play((Player) sender);
                } else if (args.length == 0) {
                    Message.send(sender, "&cInvalid arguments.");
                    Message.NO.play((Player) sender);
                } else if (ItemUtils.asMaterial(args[0].toUpperCase()) == null) {
                    Message.send(sender, "&cInvalid Material.");
                    Message.NO.play((Player) sender);
                } else {
                    Message.send(sender, "Material §8-> &f" + args[0].toUpperCase());
                    Message.ITEM_EDIT.play((Player) sender);
                    // Action
                    ((Player) sender).getItemInHand().setType(ItemUtils.asMaterial(args[0].toUpperCase()));
                }
                return true;
            });

    @TInject
    private static SimpleCommandBuilder setData = SimpleCommandBuilder.create("setDurability", ItemTool.getInst())
            .silence()
            .forceRegister()
            .aliases("setData", "setDamage")
            .permission("itemTool.use")
            .description("设置物品耐久")
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.send(sender, "&cCommand disabled on console.");
                } else if (ItemUtils.isNull(((Player) sender).getItemInHand())) {
                    Message.send(sender, "&cInvalid item.");
                    Message.NO.play((Player) sender);
                } else if (args.length == 0) {
                    Message.send(sender, "&cInvalid arguments.");
                    Message.NO.play((Player) sender);
                } else if (!Util.isNumber(args[0])) {
                    Message.send(sender, "&cInvalid number.");
                    Message.NO.play((Player) sender);
                } else {
                    Message.send(sender, "Durability §8-> &f" + args[0]);
                    Message.ITEM_EDIT.play((Player) sender);
                    // Action
                    ((Player) sender).getItemInHand().setDurability(NumberConversions.toShort(args[0]));
                }
                return true;
            });

    @TInject
    private static SimpleCommandBuilder setAmount = SimpleCommandBuilder.create("setAmount", ItemTool.getInst())
            .silence()
            .forceRegister()
            .permission("itemTool.use")
            .description("设置物品数量")
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.send(sender, "&cCommand disabled on console.");
                } else if (ItemUtils.isNull(((Player) sender).getItemInHand())) {
                    Message.send(sender, "&cInvalid item.");
                    Message.NO.play((Player) sender);
                } else if (args.length == 0) {
                    Message.send(sender, "&cInvalid arguments.");
                    Message.NO.play((Player) sender);
                } else if (!Util.isNumber(args[0])) {
                    Message.send(sender, "&cInvalid number.");
                    Message.NO.play((Player) sender);
                } else {
                    Message.send(sender, "Amount §8-> &f" + args[0]);
                    Message.ITEM_EDIT.play((Player) sender);
                    // Action
                    ((Player) sender).getItemInHand().setAmount(NumberConversions.toInt(args[0]));
                }
                return true;
            });
}
