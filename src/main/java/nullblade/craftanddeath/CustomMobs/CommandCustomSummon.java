package nullblade.craftanddeath.CustomMobs;

import nullblade.craftanddeath.main.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandCustomSummon implements CommandExecutor, TabCompleter {


    public CommandCustomSummon () {
        Bukkit.getPluginCommand("customsummon").setExecutor(this);
        Bukkit.getPluginCommand("customsummon").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLine, String[] args) {
        if (args.length != 1) {
            return false;
        }
        String id = args[0];
        Player player = (Player) sender;
        if (!MobManager.getInstance().getClasses().containsKey(id)) {
            sender.sendMessage(Util.formatString("$(PC)Sorry, but $(SC)" + id + " $(PC)is not a correct entity id."));
            return true;
        }
        MobManager.getInstance().spawn(player.getLocation(), id);
        sender.sendMessage(Util.formatString("$(PC)Summoned $(SC)" + id + "$(PC) at you!"));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String cmdLine, String[] args) {
        if (args.length == 1) {
            return Util.smartAutocomplete(new ArrayList<>(MobManager.getInstance().getClasses().keySet()), args);
        }
        return null;
    }


}
