package me.skymc.taboolib.itemtool.asm

import io.izzel.taboolib.module.inject.TInject
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.*

/**
 * @Author 坏黑
 * @Since 2018-10-14 16:22
 */
abstract class AsmHandler {

    abstract fun sendItemNBT(player: Player, itemStack: ItemStack)

    abstract fun addAttribute(itemStack: ItemStack, attribtue: String, value: String, equipment: String): ItemStack

    abstract fun removeAttribtue(itemStack: ItemStack, attribute: String): ItemStack

    abstract fun setItemTag(itemStack: ItemStack, key: String, value: String): ItemStack

    abstract fun getItemTag(itemStack: ItemStack, key: String): String

    abstract fun clearNBT(itemStack: ItemStack): ItemStack

    fun getItemTag(itemStack: ItemStack, key: String, def: String): String {
        return Optional.ofNullable(getItemTag(itemStack, key)).orElse(def)
    }

    companion object {

        @TInject(asm = "me.skymc.taboolib.itemtool.asm.AsmHandlerImpl")
        val asmHandler: AsmHandler? = null
    }
}
