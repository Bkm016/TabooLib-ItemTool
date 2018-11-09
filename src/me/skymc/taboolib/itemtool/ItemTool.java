package me.skymc.taboolib.itemtool;

import me.skymc.taboolib.common.configuration.TConfiguration;
import me.skymc.taboolib.itemtool.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @Author 坏黑
 * @Since 2018-10-12 19:57
 */
public class ItemTool extends JavaPlugin {

    private static ItemTool inst;
    private static TConfiguration conf;

    @Override
    public void onLoad() {
        inst = this;
        conf = TConfiguration.createInResource(this, "config.yml");
        conf.listener(() -> {
            Message.setupSounds();
            Message.send(Bukkit.getConsoleSender(), "reload successfully.");
        }).runListener();
    }

    @Override
    public void onEnable() {
        if (!TabooLibSetup.checkVersion(this, 4.61)) {
            setEnabled(false);
        }
    }

    public static ItemTool getInst() {
        return inst;
    }

    public static TConfiguration getConf() {
        return conf;
    }
}
