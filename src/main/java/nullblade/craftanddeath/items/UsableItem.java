package nullblade.craftanddeath.items;

import org.bukkit.event.player.PlayerInteractEvent;

public interface UsableItem {
    void onUse(PlayerInteractEvent e);
}
