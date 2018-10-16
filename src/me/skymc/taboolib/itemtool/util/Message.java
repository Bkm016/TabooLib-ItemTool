package me.skymc.taboolib.itemtool.util;

import com.ilummc.tlib.resources.TLocale;
import me.skymc.taboolib.itemtool.ItemTool;
import me.skymc.taboolib.sound.SoundPack;
import org.bukkit.command.CommandSender;

/**
 * @Author 坏黑
 * @Since 2018-10-12 23:11
 */
public class Message {

    public static SoundPack ITEM_EDIT;
    public static SoundPack YES;
    public static SoundPack NO;

    public static void setupSounds() {
        ITEM_EDIT = new SoundPack(ItemTool.getConf().getString("Sounds.ITEM_EDIT"));
        YES = new SoundPack(ItemTool.getConf().getString("Sounds.YES"));
        NO = new SoundPack(ItemTool.getConf().getString("Sounds.NO"));
    }

    public static void send(CommandSender sender, String message) {
        if (sender.hasPermission("ItemTool.notify")) {
            sender.sendMessage(" §8[§3§k§l§o|§8] §7" + TLocale.Translate.setColored(message));
        }
    }

}
