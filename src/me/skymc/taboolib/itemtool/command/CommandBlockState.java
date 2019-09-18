package me.skymc.taboolib.itemtool.command;

import io.izzel.taboolib.module.command.lite.CommandBuilder;
import io.izzel.taboolib.module.inject.TInject;
import io.izzel.taboolib.module.inject.TListener;
import io.izzel.taboolib.util.item.Items;
import me.skymc.taboolib.itemtool.ItemTool;
import me.skymc.taboolib.itemtool.util.Message;
import me.skymc.taboolib.itemtool.util.Util;
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
    private static CommandBuilder setChestContents = CommandBuilder.create("setChestContents", ItemTool.getInst())
            .forceRegister()
            .permission("itemTool.use")
            .description("设置箱子内容")
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.INSTANCE.send(sender, "&cCommand disabled on console.");
                } else if (Items.isNull(((Player) sender).getItemInHand()) || !ChestContents.isChest(((Player) sender).getItemInHand().getType())) {
                    Message.INSTANCE.send(sender, "&cInvalid item.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else {
                    Message.INSTANCE.send(sender, "ChestContents &8-> &6EDITING");
                    Message.INSTANCE.getITEM_EDIT().play((Player) sender);
                    // Action
                    Inventory inventory = Bukkit.createInventory(new ChestContents(), 27, "Turn in Items");
                    BlockStateMeta itemMeta = (BlockStateMeta) ((Player) sender).getItemInHand().getItemMeta();
                    Chest blockState = (Chest) itemMeta.getBlockState();
                    inventory.setContents(blockState.getInventory().getContents());
                    ((Player) sender).openInventory(inventory);
                }
            });

    @TInject
    private static CommandBuilder setSpawnerType = CommandBuilder.create("setSpawnerType", ItemTool.getInst())
            .forceRegister()
            .permission("itemTool.use")
            .description("设置刷怪笼类型")
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.INSTANCE.send(sender, "&cCommand disabled on console.");
                } else if (Items.isNull(((Player) sender).getItemInHand()) || ((Player) sender).getItemInHand().getType() != Material.MOB_SPAWNER) {
                    Message.INSTANCE.send(sender, "&cInvalid item.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else if (args.length == 0) {
                    Message.INSTANCE.send(sender, "&cInvalid arguments.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else if (Util.INSTANCE.asEntityType(args[0]) == null) {
                    Message.INSTANCE.send(sender, "&cInvalid EntityType.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else {
                    Message.INSTANCE.send(sender, "SpawnerType &8-> &f" + args[0].toUpperCase());
                    Message.INSTANCE.getITEM_EDIT().play((Player) sender);
                    // Action
                    BlockStateMeta itemMeta = (BlockStateMeta) ((Player) sender).getItemInHand().getItemMeta();
                    CreatureSpawner blockState = (CreatureSpawner) itemMeta.getBlockState();
                    blockState.setSpawnedType(Util.INSTANCE.asEntityType(args[0]));
                    itemMeta.setBlockState(blockState);
                    ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                }
            });

    @TInject
    private static CommandBuilder setSpawnerDelay = CommandBuilder.create("setSpawnerDelay", ItemTool.getInst())
            .permission("itemTool.use")
            .description("设置刷怪笼间隔")
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.INSTANCE.send(sender, "&cCommand disabled on console.");
                } else if (Items.isNull(((Player) sender).getItemInHand()) || ((Player) sender).getItemInHand().getType() != Material.MOB_SPAWNER) {
                    Message.INSTANCE.send(sender, "&cInvalid item.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else if (args.length == 0) {
                    Message.INSTANCE.send(sender, "&cInvalid arguments.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else if (NumberConversions.toInt(args[0]) < 0) {
                    Message.INSTANCE.send(sender, "&cInvalid delay number.");
                    Message.INSTANCE.getNO().play((Player) sender);
                } else {
                    Message.INSTANCE.send(sender, "SpawnerDelay &8-> &f" + args[0]);
                    Message.INSTANCE.getITEM_EDIT().play((Player) sender);
                    // Action
                    BlockStateMeta itemMeta = (BlockStateMeta) ((Player) sender).getItemInHand().getItemMeta();
                    CreatureSpawner blockState = (CreatureSpawner) itemMeta.getBlockState();
                    blockState.setDelay(NumberConversions.toInt(args[0]));
                    itemMeta.setBlockState(blockState);
                    ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                }
            });

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if (e.getInventory().getHolder() instanceof ChestContents) {
            if (!ChestContents.isChest(e.getPlayer().getItemInHand().getType())) {
                Message.INSTANCE.send(e.getPlayer(), "&cInvalid item.");
                Message.INSTANCE.getNO().play((Player) e.getPlayer());
            } else {
                Message.INSTANCE.send(e.getPlayer(), "ChestContents &8-> &aSAVED");
                Message.INSTANCE.getITEM_EDIT().play((Player) e.getPlayer());
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
