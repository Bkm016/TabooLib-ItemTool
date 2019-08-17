package me.skymc.taboolib.itemtool.asm;

import io.izzel.taboolib.module.inject.TFunction;
import io.izzel.taboolib.module.lite.SimpleVersionControl;
import me.skymc.taboolib.itemtool.ItemTool;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

/**
 * @Author 坏黑
 * @Since 2018-10-14 16:22
 */
@TFunction(enable = "init")
public abstract class AsmHandler {

    private static AsmHandler asmHandler;

    static void init() {
        try {
            asmHandler = (AsmHandler) SimpleVersionControl.createNMS("me.skymc.taboolib.itemtool.asm.AsmHandlerImpl").useCache() .translate(ItemTool.getInst()).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract void sendItemNBT(Player player, ItemStack itemStack);

    public abstract ItemStack addAttribute(ItemStack itemStack, String attribtue, String value, String equipment);

    public abstract ItemStack removeAttribtue(ItemStack itemStack, String attribute);

    public abstract ItemStack setItemTag(ItemStack itemStack, String key, String value);

    public abstract String getItemTag(ItemStack itemStack, String key);

    public abstract ItemStack clearNBT(ItemStack itemStack);

    public String getItemTag(ItemStack itemStack, String key, String def) {
        return Optional.ofNullable(getItemTag(itemStack, key)).orElse(def);
    }

    public static AsmHandler getAsmHandler() {
        return asmHandler;
    }
}
