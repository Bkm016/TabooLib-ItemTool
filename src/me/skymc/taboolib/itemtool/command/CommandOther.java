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
                    Message.INSTANCE.send(sender, "&cCommand disabled on console.");
                } else if (Items.isNull(((Player) sender).getItemInHand()) || !(((Player) sender).getItemInHand().getItemMeta() instanceof SkullMeta)) {
                    Message.INSTANCE.send(sender, "&cInvalid item.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else if (args.length == 0) {
                    Message.INSTANCE.send(sender, "&cInvalid arguments.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else {
                    Message.INSTANCE.send(sender, "SkullOwner §8-> &f" + args[0]);
                    Message.INSTANCE.getITEM_EDIT().play((Player) sender);
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
                    Message.INSTANCE.send(sender, "&cCommand disabled on console.");
                } else if (Items.isNull(((Player) sender).getItemInHand()) || !(((Player) sender).getItemInHand().getItemMeta() instanceof LeatherArmorMeta)) {
                    Message.INSTANCE.send(sender, "&cInvalid item.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else if (args.length == 0) {
                    Message.INSTANCE.send(sender, "&cInvalid arguments.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else {
                    Color color = Util.INSTANCE.asColor(args[0]);
                    Message.INSTANCE.send(sender, "ArmorColor §8-> &f" + color.getRed() + "-" + color.getGreen() + "-" + color.getBlue());
                    Message.INSTANCE.getITEM_EDIT().play((Player) sender);
                    // Action
                    LeatherArmorMeta itemMeta = (LeatherArmorMeta) ((Player) sender).getItemInHand().getItemMeta();
                    itemMeta.setColor(color);
                    ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                }
            });
}
