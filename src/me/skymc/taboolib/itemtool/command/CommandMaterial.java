package me.skymc.taboolib.itemtool.command;

import io.izzel.taboolib.module.command.lite.CommandBuilder;
import io.izzel.taboolib.module.inject.TInject;
import io.izzel.taboolib.util.item.Items;
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
    private static CommandBuilder setMaterial = CommandBuilder.create("setMaterial", ItemTool.getInst())
            .forceRegister()
            .aliases("setType")
            .permission("itemTool.use")
            .description("设置物品材质")
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.INSTANCE.send(sender, "&cCommand disabled on console.");
                } else if (Items.isNull(((Player) sender).getItemInHand())) {
                    Message.INSTANCE.send(sender, "&cInvalid item.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else if (args.length == 0) {
                    Message.INSTANCE.send(sender, "&cInvalid arguments.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else if (Items.asMaterial(args[0].toUpperCase()) == null) {
                    Message.INSTANCE.send(sender, "&cInvalid Material.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else {
                    Message.INSTANCE.send(sender, "Material §8-> &f" + args[0].toUpperCase());
                    Message.INSTANCE.getITEM_EDIT().play((Player) sender);
                    // Action
                    ((Player) sender).getItemInHand().setType(Items.asMaterial(args[0].toUpperCase()));
                }
            });

    @TInject
    private static CommandBuilder setData = CommandBuilder.create("setDurability", ItemTool.getInst())
            .forceRegister()
            .aliases("setData", "setDamage")
            .permission("itemTool.use")
            .description("设置物品耐久")
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.INSTANCE.send(sender, "&cCommand disabled on console.");
                } else if (Items.isNull(((Player) sender).getItemInHand())) {
                    Message.INSTANCE.send(sender, "&cInvalid item.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else if (args.length == 0) {
                    Message.INSTANCE.send(sender, "&cInvalid arguments.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else if (!Util.INSTANCE.isNumber(args[0])) {
                    Message.INSTANCE.send(sender, "&cInvalid number.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else {
                    Message.INSTANCE.send(sender, "Durability §8-> &f" + args[0]);
                    Message.INSTANCE.getITEM_EDIT().play((Player) sender);
                    // Action
                    ((Player) sender).getItemInHand().setDurability(NumberConversions.toShort(args[0]));
                }
            });

    @TInject
    private static CommandBuilder setAmount = CommandBuilder.create("setAmount", ItemTool.getInst())
            .forceRegister()
            .permission("itemTool.use")
            .description("设置物品数量")
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.INSTANCE.send(sender, "&cCommand disabled on console.");
                } else if (Items.isNull(((Player) sender).getItemInHand())) {
                    Message.INSTANCE.send(sender, "&cInvalid item.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else if (args.length == 0) {
                    Message.INSTANCE.send(sender, "&cInvalid arguments.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else if (!Util.INSTANCE.isNumber(args[0])) {
                    Message.INSTANCE.send(sender, "&cInvalid number.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else {
                    Message.INSTANCE.send(sender, "Amount §8-> &f" + args[0]);
                    Message.INSTANCE.getITEM_EDIT().play((Player) sender);
                    // Action
                    ((Player) sender).getItemInHand().setAmount(NumberConversions.toInt(args[0]));
                }
            });
}
