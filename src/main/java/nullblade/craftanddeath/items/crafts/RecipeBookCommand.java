package nullblade.craftanddeath.items.crafts;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class RecipeBookCommand implements CommandExecutor {

    public RecipeBookCommand() {
        Bukkit.getPluginCommand("recipebook").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLine, String[] args) {
        if (sender instanceof Player) {
            RecipeBook.getInstance().open((Player) sender);
        }

        return true;
    }
}
