package me.skymc.taboolib.itemtool.command;

import com.google.common.collect.Lists;
import io.izzel.taboolib.module.command.lite.CommandBuilder;
import io.izzel.taboolib.module.inject.TInject;
import io.izzel.taboolib.util.item.Items;
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
    private static CommandBuilder setAttribute = CommandBuilder.create("addAttribute", ItemTool.getInst())
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
                    Message.INSTANCE.send(sender, "&cCommand disabled on console.");
                } else if (Items.isNull(((Player) sender).getItemInHand())) {
                    Message.INSTANCE.send(sender, "&cInvalid item.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else if (args.length < 3) {
                    Message.INSTANCE.send(sender, "&cInvalid arguments.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else if (Items.asAttribute(args[0]) == null) {
                    Message.INSTANCE.send(sender, "&cInvalid Attribtue.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else {
                    Message.INSTANCE.send(sender, "Attribute &8+ &f" + args[0].toUpperCase() + ":" + args[1].toUpperCase() + ":" + args[2]);
                    Message.INSTANCE.getITEM_EDIT().play((Player) sender);
                    // Action
                    ((Player) sender).setItemInHand(AsmHandler.Companion.getAsmHandler().addAttribute(((Player) sender).getItemInHand(), Items.asAttribute(args[0]), args[2], args[1].toLowerCase()));
                }
            });

    @TInject
    private static CommandBuilder removeAttribute = CommandBuilder.create("removeAttribute", ItemTool.getInst())
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
                    Message.INSTANCE.send(sender, "&cCommand disabled on console.");
                } else if (Items.isNull(((Player) sender).getItemInHand())) {
                    Message.INSTANCE.send(sender, "&cInvalid item.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else if (args.length == 0) {
                    Message.INSTANCE.send(sender, "&cInvalid arguments.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else if (Items.asAttribute(args[0]) == null) {
                    Message.INSTANCE.send(sender, "&cInvalid Attribtue.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else {
                    Message.INSTANCE.send(sender, "Attribute &8- &f" + args[0].toUpperCase());
                    Message.INSTANCE.getITEM_EDIT().play((Player) sender);
                    // Action
                    ((Player) sender).setItemInHand(AsmHandler.Companion.getAsmHandler().removeAttribtue(((Player) sender).getItemInHand(), Items.asAttribute(args[0])));
                }
            });
}
