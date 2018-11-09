package me.skymc.taboolib.itemtool.command;

import com.google.common.collect.Maps;
import me.skymc.taboolib.commands.builder.SimpleCommandBuilder;
import me.skymc.taboolib.commands.internal.TCommandHandler;
import me.skymc.taboolib.common.inject.TInject;
import me.skymc.taboolib.itemtool.ItemTool;
import me.skymc.taboolib.itemtool.util.Message;
import org.bukkit.command.Command;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author 坏黑
 * @Since 2018-10-14 14:13
 */
public class CommandMain {

    @TInject
    private static SimpleCommandBuilder itemTool = SimpleCommandBuilder.create("itemTool", ItemTool.getInst())
            .silence()
            .permission("itemTool.use")
            .execute((sender, args) -> {
                if (args.length == 0) {
                    Map<String, String> commands = TCommandHandler.getCommandMap().getCommands().stream().filter(command -> command instanceof PluginCommand && !command.getName().equalsIgnoreCase("itemTool") && ((PluginCommand) command).getPlugin().equals(ItemTool.getInst())).collect(Collectors.toMap(Command::getName, Command::getDescription, (a, b) -> b, Maps::newTreeMap));
                    Message.send(sender, "");
                    Message.send(sender, "&7&l----- &f&lTabooLib ItemTool Commands &7&l-----");
                    Message.send(sender, "");
                    commands.forEach((key, value) -> Message.send(sender, " §7/&f" + key + " &8- &7" + value));
                    Message.send(sender, "");
                } else if (args[0].equalsIgnoreCase("undo")) {
                    if (!(sender instanceof Player)) {
                        Message.send(sender, "&cCommand disabled on console.");
                        return true;
                    }

                } else {
                    Message.send(sender, "&cInvalid arguments.");
                }
                return true;
            });

}
