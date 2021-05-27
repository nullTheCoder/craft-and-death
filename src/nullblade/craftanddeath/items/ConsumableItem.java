package nullblade.craftanddeath.items;

import org.bukkit.event.player.PlayerItemConsumeEvent;

public interface ConsumableItem {
    void onConsume(PlayerItemConsumeEvent e);
}
