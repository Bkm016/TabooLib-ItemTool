package me.skymc.taboolib.itemtool.command;

import com.ilummc.tlib.resources.TLocale;
import me.skymc.taboolib.commands.builder.SimpleCommandBuilder;
import me.skymc.taboolib.common.inject.TInject;
import me.skymc.taboolib.inventory.ItemUtils;
import me.skymc.taboolib.itemtool.ItemTool;
import me.skymc.taboolib.itemtool.util.Message;
import me.skymc.taboolib.string.ArrayUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.util.NumberConversions;

/**
 * @Author 坏黑
 * @Since 2018-10-14 20:46
 */
public class CommandBook {

    @TInject
    private static SimpleCommandBuilder setName = SimpleCommandBuilder.create("setAuthor", ItemTool.getInst())
            .silence()
            .permission("itemTool.use")
            .description("设置书本作者")
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.send(sender, "&cCommand disabled on console.");
                } else if (ItemUtils.isNull(((Player) sender).getItemInHand()) || ((Player) sender).getItemInHand().getType() != Material.WRITTEN_BOOK) {
                    Message.send(sender, "&cInvalid item.");
                    Message.NO.play((Player) sender);
                } else if (args.length == 0) {
                    Message.send(sender, "&cInvalid arguments.");
                    Message.NO.play((Player) sender);
                } else {
                    Message.send(sender, "BookAuthor §8-> &f" + ArrayUtils.arrayJoin(args, 0));
                    Message.ITEM_EDIT.play((Player) sender);
                    // Action
                    BookMeta itemMeta = (BookMeta) ((Player) sender).getItemInHand().getItemMeta();
                    itemMeta.setAuthor(TLocale.Translate.setColored(ArrayUtils.arrayJoin(args, 0)));
                    ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                }
                return true;
            });

    @TInject
    private static SimpleCommandBuilder setGeneration = SimpleCommandBuilder.create("setGeneration", ItemTool.getInst())
            .silence()
            .permission("itemTool.use")
            .description("设置书本种类")
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.send(sender, "&cCommand disabled on console.");
                } else if (ItemUtils.isNull(((Player) sender).getItemInHand()) || ((Player) sender).getItemInHand().getType() != Material.WRITTEN_BOOK) {
                    Message.send(sender, "&cInvalid item.");
                    Message.NO.play((Player) sender);
                } else if (args.length == 0) {
                    Message.send(sender, "&cInvalid arguments.");
                    Message.NO.play((Player) sender);
                } else if (NumberConversions.toInt(args[0]) < 0 || NumberConversions.toInt(args[0]) > 3) {
                    Message.send(sender, "&cInvalid Generation.");
                    Message.NO.play((Player) sender);
                } else {
                    Message.send(sender, "BookGeneration §8-> &f" + NumberConversions.toInt(args[0]));
                    Message.ITEM_EDIT.play((Player) sender);
                    // Action
                    BookMeta itemMeta = (BookMeta) ((Player) sender).getItemInHand().getItemMeta();
                    try {
                        switch (NumberConversions.toInt(args[0])) {
                            case 0:
                                itemMeta.setGeneration(BookMeta.Generation.ORIGINAL);
                                break;
                            case 1:
                                itemMeta.setGeneration(BookMeta.Generation.COPY_OF_ORIGINAL);
                                break;
                            case 2:
                                itemMeta.setGeneration(BookMeta.Generation.COPY_OF_COPY);
                                break;
                            default:
                                itemMeta.setGeneration(BookMeta.Generation.TATTERED);
                                break;
                        }
                    } catch (Exception ignored) {
                    }
                    ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                }
                return true;
            });

}
