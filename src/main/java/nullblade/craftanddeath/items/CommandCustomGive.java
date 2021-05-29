package nullblade.craftanddeath.items;

import nullblade.craftanddeath.main.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CommandCustomGive implements CommandExecutor, TabCompleter {

    public CommandCustomGive() {
        Bukkit.getPluginCommand("cgive").setExecutor(this);
        Bukkit.getPluginCommand("cgive").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLine, String[] args) {
        if (args.length != 1) {
            return false;
        }
        String id = args[0];
        Player player = (Player) sender;
        ItemStack item = ItemManager.getInstance().get(id);
        if (item == null) {
            sender.sendMessage(Util.formatString("$(PC)Sorry, but $(SC)" + id + " $(PC)is not a correct item id."));
            return true;
        }
        player.getInventory().addItem(item);
        sender.sendMessage(Util.formatString("$(PC)Given $(SC)" + id + "$(PC) to you!"));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String cmdLine, String[] args) {
        if (args.length == 1) {
            return Util.smartAutocomplete(new ArrayList<>(ItemManager.getInstance().getItems().keySet()), args);
        }

        return null;
    }
}
