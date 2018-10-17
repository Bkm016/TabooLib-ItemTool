package me.skymc.taboolib.itemtool.asm;

import me.skymc.taboolib.common.versioncontrol.SimpleVersionControl;
import me.skymc.taboolib.itemtool.ItemTool;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

/**
 * @Author 坏黑
 * @Since 2018-10-14 16:22
 */
public abstract class AsmHandler {

    private static AsmHandler asmHandler;

    public static void init() {
        try {
            asmHandler = (AsmHandler) SimpleVersionControl.create()
                    .from("v1_8_R3")
                    .from("v1_12_R1")
                    .target("me.skymc.taboolib.itemtool.asm.AsmHandlerImpl")
                    .useCache()
                    .translate(ItemTool.getInst())
                    .newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static AsmHandler getAsmHandler() {
        return asmHandler;
    }

    public abstract void sendItemNBT(Player player, ItemStack itemStack);

    public abstract ItemStack addAttribute(ItemStack itemStack, String attribtue, String value, String equipment);

    public abstract ItemStack removeAttribtue(ItemStack itemStack, String attribute);

    public abstract ItemStack setItemTag(ItemStack itemStack, String key, String value);

    public abstract String getItemTag(ItemStack itemStack, String key);

    public String getItemTag(ItemStack itemStack, String key, String def) {
        return Optional.ofNullable(getItemTag(itemStack, key)).orElse(def);
    }
}
