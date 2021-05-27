package nullblade.craftanddeath.main;

import nullblade.craftanddeath.content.Content;
import nullblade.craftanddeath.items.CommandCustomGive;
import nullblade.craftanddeath.items.ItemEventManager;
import nullblade.craftanddeath.items.ItemManager;
import nullblade.craftanddeath.items.alloys.AlloyManager;
import nullblade.craftanddeath.items.crafts.CraftingManager;
import nullblade.craftanddeath.items.crafts.RecipeBook;
import nullblade.craftanddeath.items.crafts.RecipeBookCommand;
import nullblade.craftanddeath.mechanics.TemperatureAndThirst;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Main extends JavaPlugin implements Listener {

    private static Main instance;

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        players = new HashMap<>();

        Bukkit.getPluginManager().registerEvents(this, this);

        new DamageManager();

        new RecipeBook();

        for (Player p : Bukkit.getOnlinePlayers()) {
            players.put(p.getUniqueId(), new AdvancedPlayer(p));
        }

        new TemperatureAndThirst();

        new ItemManager();
        new CommandCustomGive();

        new RecipeBookCommand();
        new ItemEventManager();
        new CraftingManager();

        new AlloyManager();

        Content.init();

        RecipeBook.getInstance().finishRecipeBook();
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelAllTasks();
    }


    // Advanced players
    private Map<UUID, AdvancedPlayer> players;

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        players.put(e.getPlayer().getUniqueId(), new AdvancedPlayer(e.getPlayer()));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        players.get(e.getPlayer().getUniqueId()).onLeave();
        players.remove(e.getPlayer().getUniqueId());
    }

    public AdvancedPlayer getPlayer(UUID player) {
        return players.get(player);
    }
    public AdvancedPlayer getPlayer(Player player) {
        return players.get(player.getUniqueId());
    }
}
