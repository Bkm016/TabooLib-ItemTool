package me.skymc.taboolib.itemtool;

import io.izzel.taboolib.module.inject.TInject;

/**
 * @Author 坏黑
 * @Since 2018-10-12 19:57
 */
@ItemToolPlugin.Version(5.06)
public class ItemTool extends ItemToolPlugin {

    @TInject
    private static ItemTool inst;

    public static ItemTool getInst() {
        return inst;
    }
}
