package me.skymc.taboolib.itemtool.util;

import io.izzel.taboolib.module.locale.TLocale;
import io.izzel.taboolib.util.lite.SoundPack;
import org.bukkit.command.CommandSender;

/**
 * @Author 坏黑
 * @Since 2018-10-12 23:11
 */
public class Message {

    public static SoundPack ITEM_EDIT = new SoundPack("UI_BUTTON_CLICK-1-1");
    public static SoundPack YES = new SoundPack("ENTITY_VILLAGER_YES-1-1");
    public static SoundPack NO = new SoundPack("ENTITY_VILLAGER_NO-1-1");

    public static void send(CommandSender sender, String message) {
        if (sender.hasPermission("ItemTool.notify")) {
            sender.sendMessage(" §8[§3§k§l§o|§8] §7" + TLocale.Translate.setColored(message));
        }
    }
}
