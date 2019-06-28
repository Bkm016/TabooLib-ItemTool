package me.skymc.taboolib.itemtool.command;

import com.google.common.collect.Lists;
import me.skymc.taboolib.commands.builder.SimpleCommandBuilder;
import me.skymc.taboolib.common.inject.TInject;
import me.skymc.taboolib.inventory.ItemUtils;
import me.skymc.taboolib.itemtool.ItemTool;
import me.skymc.taboolib.itemtool.asm.AsmHandler;
import me.skymc.taboolib.itemtool.util.Message;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @Author 坏黑
 * @Since 2018-10-14 19:50
 */
public class CommandAttribute {

    static String[][] ARGUMENTS = {
            {
                "damage", "health", "attackspeed", "luck", "armor", "speed", "knockback"
            },
            {
                "mainhand", "offhand", "head", "chest", "legs", "feet"
            }
    };

    @TInject
    private static SimpleCommandBuilder setAttribute = SimpleCommandBuilder.create("addAttribute", ItemTool.getInst())
            .silence()
            .forceRegister()
            .permission("itemTool.use")
            .description("添加物品属性")
            .tab((sender, args) -> {
                if (args.length == 1) {
                    return Arrays.stream(ARGUMENTS[0]).filter(argument -> argument.startsWith(args[0].toLowerCase())).collect(Collectors.toList());
                }
                if (args.length == 2) {
                    return Arrays.stream(ARGUMENTS[1]).filter(argument -> argument.startsWith(args[1].toLowerCase())).collect(Collectors.toList());
                }
                return Lists.newArrayList();
            })
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.send(sender, "&cCommand disabled on console.");
                } else if (ItemUtils.isNull(((Player) sender).getItemInHand())) {
                    Message.send(sender, "&cInvalid item.");
                    Message.NO.play((Player) sender);
                } else if (args.length < 3) {
                    Message.send(sender, "&cInvalid arguments.");
                    Message.NO.play((Player) sender);
                } else if (ItemUtils.asAttribute(args[0]) == null) {
                    Message.send(sender, "&cInvalid Attribtue.");
                    Message.NO.play((Player) sender);
                } else {
                    Message.send(sender, "Attribute &8+ &f" + args[0].toUpperCase() + ":" + args[1].toUpperCase() + ":" + args[2]);
                    Message.ITEM_EDIT.play((Player) sender);
                    // Action
                    ((Player) sender).setItemInHand(AsmHandler.getAsmHandler().addAttribute(((Player) sender).getItemInHand(), ItemUtils.asAttribute(args[0]), args[2], args[1].toLowerCase()));
                }
                return true;
            });

    @TInject
    private static SimpleCommandBuilder removeAttribute = SimpleCommandBuilder.create("removeAttribute", ItemTool.getInst())
            .silence()
            .forceRegister()
            .permission("itemTool.use")
            .description("移除物品属性")
            .tab((sender, args) -> {
                if (args.length == 1) {
                    return Arrays.stream(ARGUMENTS[0]).filter(argument -> argument.startsWith(args[0].toLowerCase())).collect(Collectors.toList());
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
                } else if (ItemUtils.asAttribute(args[0]) == null) {
                    Message.send(sender, "&cInvalid Attribtue.");
                    Message.NO.play((Player) sender);
                } else {
                    Message.send(sender, "Attribute &8- &f" + args[0].toUpperCase());
                    Message.ITEM_EDIT.play((Player) sender);
                    // Action
                    ((Player) sender).setItemInHand(AsmHandler.getAsmHandler().removeAttribtue(((Player) sender).getItemInHand(), ItemUtils.asAttribute(args[0])));
                }
                return true;
            });
}
