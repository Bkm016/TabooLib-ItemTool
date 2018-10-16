package me.skymc.taboolib.itemtool.command;

import me.skymc.taboolib.commands.builder.SimpleCommandBuilder;
import me.skymc.taboolib.common.inject.TInject;
import me.skymc.taboolib.inventory.ItemUtils;
import me.skymc.taboolib.itemtool.ItemTool;
import me.skymc.taboolib.itemtool.util.Message;
import me.skymc.taboolib.itemtool.util.Util;
import me.skymc.taboolib.listener.TListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.util.NumberConversions;

/**
 * @Author 坏黑
 * @Since 2018-10-14 13:16
 */
@TListener
public class CommandBlockState implements Listener {

    @TInject
    private static SimpleCommandBuilder setChestContents = SimpleCommandBuilder.create("setChestContents", ItemTool.getInst())
            .silence()
            .permission("itemTool.use")
            .description("设置箱子内容")
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.send(sender, "&cCommand disabled on console.");
                } else if (ItemUtils.isNull(((Player) sender).getItemInHand()) || !ChestContents.isChest(((Player) sender).getItemInHand().getType())) {
                    Message.send(sender, "&cInvalid item.");
                    Message.NO.play((Player) sender);
                } else {
                    Message.send(sender, "ChestContents &8-> &6EDITING");
                    Message.ITEM_EDIT.play((Player) sender);
                    // Action
                    Inventory inventory = Bukkit.createInventory(new ChestContents(), 27, "Turn in Items");
                    BlockStateMeta itemMeta = (BlockStateMeta) ((Player) sender).getItemInHand().getItemMeta();
                    Chest blockState = (Chest) itemMeta.getBlockState();
                    inventory.setContents(blockState.getInventory().getContents());
                    ((Player) sender).openInventory(inventory);
                }
                return true;
            });

    @TInject
    private static SimpleCommandBuilder setSpawnerType = SimpleCommandBuilder.create("setSpawnerType", ItemTool.getInst())
            .silence()
            .permission("itemTool.use")
            .description("设置刷怪笼类型")
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.send(sender, "&cCommand disabled on console.");
                } else if (ItemUtils.isNull(((Player) sender).getItemInHand()) || ((Player) sender).getItemInHand().getType() != Material.MOB_SPAWNER) {
                    Message.send(sender, "&cInvalid item.");
                    Message.NO.play((Player) sender);
                } else if (args.length == 0) {
                    Message.send(sender, "&cInvalid arguments.");
                    Message.NO.play((Player) sender);
                } else if (Util.asEntityType(args[0]) == null) {
                    Message.send(sender, "&cInvalid EntityType.");
                    Message.NO.play((Player) sender);
                } else {
                    Message.send(sender, "SpawnerType &8-> &f" + args[0].toUpperCase());
                    Message.ITEM_EDIT.play((Player) sender);
                    // Action
                    BlockStateMeta itemMeta = (BlockStateMeta) ((Player) sender).getItemInHand().getItemMeta();
                    CreatureSpawner blockState = (CreatureSpawner) itemMeta.getBlockState();
                    blockState.setSpawnedType(Util.asEntityType(args[0]));
                    itemMeta.setBlockState(blockState);
                    ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                }
                return true;
            });

    @TInject
    private static SimpleCommandBuilder setSpawnerDelay = SimpleCommandBuilder.create("setSpawnerDelay", ItemTool.getInst())
            .silence()
            .permission("itemTool.use")
            .description("设置刷怪笼间隔")
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.send(sender, "&cCommand disabled on console.");
                } else if (ItemUtils.isNull(((Player) sender).getItemInHand()) || ((Player) sender).getItemInHand().getType() != Material.MOB_SPAWNER) {
                    Message.send(sender, "&cInvalid item.");
                    Message.NO.play((Player) sender);
                } else if (args.length == 0) {
                    Message.send(sender, "&cInvalid arguments.");
                    Message.NO.play((Player) sender);
                } else if (NumberConversions.toInt(args[0]) < 0) {
                    Message.send(sender, "&cInvalid delay number.");
                    Message.NO.play((Player) sender);
                } else {
                    Message.send(sender, "SpawnerDelay &8-> &f" + args[0]);
                    Message.ITEM_EDIT.play((Player) sender);
                    // Action
                    BlockStateMeta itemMeta = (BlockStateMeta) ((Player) sender).getItemInHand().getItemMeta();
                    CreatureSpawner blockState = (CreatureSpawner) itemMeta.getBlockState();
                    blockState.setDelay(NumberConversions.toInt(args[0]));
                    itemMeta.setBlockState(blockState);
                    ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                }
                return true;
            });

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if (e.getInventory().getHolder() instanceof ChestContents) {
            if (!ChestContents.isChest(e.getPlayer().getItemInHand().getType())) {
                Message.send(e.getPlayer(), "&cInvalid item.");
                Message.NO.play((Player) e.getPlayer());
            } else {
                Message.send(e.getPlayer(), "ChestContents &8-> &aSAVED");
                Message.ITEM_EDIT.play((Player) e.getPlayer());
                // Action
                BlockStateMeta itemMeta = (BlockStateMeta) e.getPlayer().getItemInHand().getItemMeta();
                Chest blockState = (Chest) itemMeta.getBlockState();
                blockState.getInventory().setContents(e.getInventory().getContents());
                itemMeta.setBlockState(blockState);
                e.getPlayer().getItemInHand().setItemMeta(itemMeta);
            }
        }
    }

    private static class ChestContents implements InventoryHolder {

        @Override
        public Inventory getInventory() {
            return null;
        }

        public static boolean isChest(Material material) {
            return material == Material.CHEST || material == Material.TRAPPED_CHEST;
        }
    }
}
