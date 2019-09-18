package me.skymc.taboolib.itemtool.command;

import io.izzel.taboolib.module.command.lite.CommandBuilder;
import io.izzel.taboolib.module.inject.TInject;
import io.izzel.taboolib.util.item.Items;
import me.skymc.taboolib.itemtool.ItemTool;
import me.skymc.taboolib.itemtool.asm.AsmHandler;
import me.skymc.taboolib.itemtool.util.Message;
import org.bukkit.entity.Player;

/**
 * @Author 坏黑
 * @Since 2018-10-16 23:11
 */
public class CommandNBT {

    @TInject
    private static CommandBuilder nbtInfo = CommandBuilder.create("nbtInfo", ItemTool.getInst())
            .forceRegister()
            .permission("itemTool.use")
            .description("查看物品详细 NBT 节点")
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.INSTANCE.send(sender, "&cCommand disabled on console.");
                } else if (Items.isNull(((Player) sender).getItemInHand())) {
                    Message.INSTANCE.send(sender, "&cInvalid item.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else {
                    Message.INSTANCE.getYES().play((Player) sender);
                    // Action
                    AsmHandler.Companion.getAsmHandler().sendItemNBT((Player) sender, ((Player) sender).getItemInHand());
                }
            });

    @TInject
    private static CommandBuilder nbtClear = CommandBuilder.create("nbtClear", ItemTool.getInst())
            .forceRegister()
            .aliases("nbtClean")
            .permission("itemTool.use")
            .description("移除物品所有 NBT 节点")
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.INSTANCE.send(sender, "&cCommand disabled on console.");
                } else if (Items.isNull(((Player) sender).getItemInHand())) {
                    Message.INSTANCE.send(sender, "&cInvalid item.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else {
                    Message.INSTANCE.send(sender, "NBT &8-> &4CLEAR");
                    Message.INSTANCE.getITEM_EDIT().play((Player) sender);
                    // Action
                    ((Player) sender).setItemInHand(AsmHandler.Companion.getAsmHandler().clearNBT(((Player) sender).getItemInHand()));
                }
            });
}
