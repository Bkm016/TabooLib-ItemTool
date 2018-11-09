package me.skymc.taboolib.itemtool.command;

import me.skymc.taboolib.commands.builder.SimpleCommandBuilder;
import me.skymc.taboolib.common.inject.TInject;
import me.skymc.taboolib.inventory.ItemUtils;
import me.skymc.taboolib.itemtool.ItemTool;
import me.skymc.taboolib.itemtool.util.Message;
import me.skymc.taboolib.itemtool.util.Util;
import me.skymc.taboolib.other.NumberUtils;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.util.NumberConversions;

/**
 * @Author 坏黑
 * @Since 2018-10-14 20:56
 */
public class CommandFirework {

    @TInject
    private static SimpleCommandBuilder setFireworkPower = SimpleCommandBuilder.create("setFireworkPower", ItemTool.getInst())
            .silence()
            .forceRegister()
            .permission("itemTool.use")
            .description("设置烟花强度")
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.send(sender, "&cCommand disabled on console.");
                } else if (ItemUtils.isNull(((Player) sender).getItemInHand()) || ((Player) sender).getItemInHand().getType() != Material.FIREWORK) {
                    Message.send(sender, "&cInvalid item.");
                    Message.NO.play((Player) sender);
                } else if (args.length == 0) {
                    Message.send(sender, "&cInvalid arguments.");
                    Message.NO.play((Player) sender);
                } else {
                    Message.send(sender, "FireworkPower §8-> &f" + NumberConversions.toInt(args[0]));
                    Message.ITEM_EDIT.play((Player) sender);
                    // Action
                    FireworkMeta itemMeta = (FireworkMeta) ((Player) sender).getItemInHand().getItemMeta();
                    itemMeta.setPower(NumberConversions.toInt(args[0]));
                    ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                }
                return true;
            });

    @TInject
    private static SimpleCommandBuilder addFireworkEffect = SimpleCommandBuilder.create("addFireworkEffect", ItemTool.getInst())
            .silence()
            .forceRegister()
            .permission("itemTool.use")
            .description("添加烟花效果")
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.send(sender, "&cCommand disabled on console.");
                } else if (ItemUtils.isNull(((Player) sender).getItemInHand()) || ((Player) sender).getItemInHand().getType() != Material.FIREWORK) {
                    Message.send(sender, "&cInvalid item.");
                    Message.NO.play((Player) sender);
                } else if (args.length < 3) {
                    Message.send(sender, "&cInvalid arguments.");
                    Message.NO.play((Player) sender);
                } else if (asType(args[0]) == null) {
                    Message.send(sender, "&cInvalid FireworkEffectType.");
                    Message.NO.play((Player) sender);
                } else {
                    FireworkEffect fireworkEffect = FireworkEffect.builder()
                            .with(asType(args[0]))
                            .withColor(Util.asColorArray(args[1]))
                            .withFade(Util.asColorArray(args[2]))
                            .flicker(args.length > 3 ? NumberUtils.getBoolean(args[3]) : false)
                            .trail(args.length > 4 ? NumberUtils.getBoolean(args[3]) : false)
                            .build();
                    FireworkMeta itemMeta = (FireworkMeta) ((Player) sender).getItemInHand().getItemMeta();
                    itemMeta.addEffect(fireworkEffect);
                    ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                    // Message
                    Message.send(sender, "FireworkEffect §8+ &f" + fireworkEffect);
                    Message.ITEM_EDIT.play((Player) sender);
                }
                return true;
            });

    @TInject
    private static SimpleCommandBuilder removeFireworkEffect = SimpleCommandBuilder.create("removeFireworkEffect", ItemTool.getInst())
            .silence()
            .forceRegister()
            .permission("itemTool.use")
            .description("删除烟花效果")
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.send(sender, "&cCommand disabled on console.");
                } else if (ItemUtils.isNull(((Player) sender).getItemInHand()) || ((Player) sender).getItemInHand().getType() != Material.FIREWORK) {
                    Message.send(sender, "&cInvalid item.");
                    Message.NO.play((Player) sender);
                } else {
                    Message.send(sender, "FireworkEffect §8- &6LATEST");
                    Message.ITEM_EDIT.play((Player) sender);
                    // Action
                    FireworkMeta itemMeta = (FireworkMeta) ((Player) sender).getItemInHand().getItemMeta();
                    if (itemMeta.getEffectsSize() > 0) {
                        itemMeta.removeEffect(itemMeta.getEffectsSize() - 1);
                    }
                    ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                }
                return true;
            });

    private static FireworkEffect.Type asType(String name) {
        try {
            return FireworkEffect.Type.valueOf(name.toUpperCase());
        } catch (Exception ignored) {
            return null;
        }
    }
}
