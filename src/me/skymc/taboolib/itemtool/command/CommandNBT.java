package me.skymc.taboolib.itemtool.command;

import me.skymc.taboolib.commands.builder.SimpleCommandBuilder;
import me.skymc.taboolib.common.inject.TInject;
import me.skymc.taboolib.inventory.ItemUtils;
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
    private static SimpleCommandBuilder nbtInfo = SimpleCommandBuilder.create("nbtInfo", ItemTool.getInst())
            .silence()
            .permission("itemTool.use")
            .description("查看物品详细 NBT 节点")
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.send(sender, "&cCommand disabled on console.");
                } else if (ItemUtils.isNull(((Player) sender).getItemInHand())) {
                    Message.send(sender, "&cInvalid item.");
                    Message.NO.play((Player) sender);
                } else {
                    Message.YES.play((Player) sender);
                    AsmHandler.getAsmHandler().sendItemNBT((Player) sender, ((Player) sender).getItemInHand());
                }
                return true;
            });
}
