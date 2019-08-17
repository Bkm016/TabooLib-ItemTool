package me.skymc.taboolib.itemtool.command;

import io.izzel.taboolib.module.command.lite.CommandBuilder;
import io.izzel.taboolib.module.inject.TInject;
import io.izzel.taboolib.util.item.Items;
import me.skymc.taboolib.itemtool.ItemTool;
import me.skymc.taboolib.itemtool.util.Message;
import me.skymc.taboolib.itemtool.util.Util;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

/**
 * @Author 坏黑
 * @Since 2018-10-14 10:46
 */
public class CommandOther {

    @TInject
    private static CommandBuilder setName = CommandBuilder.create("setSkullOwner", ItemTool.getInst())
            .forceRegister()
            .permission("itemTool.use")
            .description("设置头颅皮肤")
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.send(sender, "&cCommand disabled on console.");
                } else if (Items.isNull(((Player) sender).getItemInHand()) || !(((Player) sender).getItemInHand().getItemMeta() instanceof SkullMeta)) {
                    Message.send(sender, "&cInvalid item.");
                    Message.NO.play((Player) sender);
                } else if (args.length == 0) {
                    Message.send(sender, "&cInvalid arguments.");
                    Message.NO.play((Player) sender);
                } else {
                    Message.send(sender, "SkullOwner §8-> &f" + args[0]);
                    Message.ITEM_EDIT.play((Player) sender);
                    // Action
                    SkullMeta itemMeta = (SkullMeta) ((Player) sender).getItemInHand().getItemMeta();
                    itemMeta.setOwner(args[0]);
                    ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                }
            });

    @TInject
    private static CommandBuilder setArmorColor = CommandBuilder.create("setArmorColor", ItemTool.getInst())
            .forceRegister()
            .permission("itemTool.use")
            .description("设置皮革颜色")
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.send(sender, "&cCommand disabled on console.");
                } else if (Items.isNull(((Player) sender).getItemInHand()) || !(((Player) sender).getItemInHand().getItemMeta() instanceof LeatherArmorMeta)) {
                    Message.send(sender, "&cInvalid item.");
                    Message.NO.play((Player) sender);
                } else if (args.length == 0) {
                    Message.send(sender, "&cInvalid arguments.");
                    Message.NO.play((Player) sender);
                } else {
                    Color color = Util.asColor(args[0]);
                    Message.send(sender, "ArmorColor §8-> &f" + color.getRed() + "-" + color.getGreen() + "-" + color.getBlue());
                    Message.ITEM_EDIT.play((Player) sender);
                    // Action
                    LeatherArmorMeta itemMeta = (LeatherArmorMeta) ((Player) sender).getItemInHand().getItemMeta();
                    itemMeta.setColor(color);
                    ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                }
            });
}
