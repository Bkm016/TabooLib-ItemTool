package me.skymc.taboolib.itemtool.command;

import io.izzel.taboolib.module.command.lite.CommandBuilder;
import io.izzel.taboolib.module.inject.TInject;
import io.izzel.taboolib.module.locale.TLocale;
import io.izzel.taboolib.util.ArrayUtil;
import io.izzel.taboolib.util.item.Items;
import me.skymc.taboolib.itemtool.ItemTool;
import me.skymc.taboolib.itemtool.util.Message;
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
    private static CommandBuilder setName = CommandBuilder.create("setAuthor", ItemTool.getInst())
            .forceRegister()
            .permission("itemTool.use")
            .description("设置书本作者")
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.send(sender, "&cCommand disabled on console.");
                } else if (Items.isNull(((Player) sender).getItemInHand()) || ((Player) sender).getItemInHand().getType() != Material.WRITTEN_BOOK) {
                    Message.send(sender, "&cInvalid item.");
                    Message.NO.play((Player) sender);
                } else if (args.length == 0) {
                    Message.send(sender, "&cInvalid arguments.");
                    Message.NO.play((Player) sender);
                } else {
                    Message.send(sender, "BookAuthor §8-> &f" + ArrayUtil.arrayJoin(args, 0));
                    Message.ITEM_EDIT.play((Player) sender);
                    // Action
                    BookMeta itemMeta = (BookMeta) ((Player) sender).getItemInHand().getItemMeta();
                    itemMeta.setAuthor(TLocale.Translate.setColored(ArrayUtil.arrayJoin(args, 0)));
                    ((Player) sender).getItemInHand().setItemMeta(itemMeta);
                }
            });

    @TInject
    private static CommandBuilder setGeneration = CommandBuilder.create("setGeneration", ItemTool.getInst())
            .forceRegister()
            .permission("itemTool.use")
            .description("设置书本种类")
            .execute((sender, args) -> {
                if (!(sender instanceof Player)) {
                    Message.send(sender, "&cCommand disabled on console.");
                } else if (Items.isNull(((Player) sender).getItemInHand()) || ((Player) sender).getItemInHand().getType() != Material.WRITTEN_BOOK) {
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
            });

}
