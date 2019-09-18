package me.skymc.taboolib.itemtool.command;

import com.google.common.collect.Maps;
import io.izzel.taboolib.module.command.TCommandHandler;
import io.izzel.taboolib.module.command.lite.CommandBuilder;
import io.izzel.taboolib.module.inject.TInject;
import me.skymc.taboolib.itemtool.ItemTool;
import me.skymc.taboolib.itemtool.util.Message;
import org.bukkit.command.Command;
import org.bukkit.command.PluginCommand;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author 坏黑
 * @Since 2018-10-14 14:13
 */
public class CommandMain {

    @TInject
    private static CommandBuilder itemTool = CommandBuilder.create("itemTool", ItemTool.getInst())
            .permission("itemTool.use")
            .execute((sender, args) -> {
                if (args.length == 0) {
                    Map<String, String> commands = TCommandHandler.getCommandMap().getCommands().stream().filter(command -> command instanceof PluginCommand && !command.getName().equalsIgnoreCase("itemTool") && ((PluginCommand) command).getPlugin().equals(ItemTool.getInst())).collect(Collectors.toMap(Command::getName, Command::getDescription, (a, b) -> b, Maps::newTreeMap));
                    Message.INSTANCE.send(sender, "");
                    Message.INSTANCE.send(sender, "&7&l----- &f&lTabooLib ItemTool Commands &7&l-----");
                    Message.INSTANCE.send(sender, "");
                    commands.forEach((key, value) -> Message.INSTANCE.send(sender, " §7/&f" + key + " &8- &7" + value));
                    Message.INSTANCE.send(sender, "");
                } else {
                    Message.INSTANCE.send(sender, "&cInvalid arguments.");
                }
            });

}
