package nullblade.craftanddeath.items;

import nullblade.craftanddeath.main.AdvancedPlayer;

public interface ArmourItem {
    int getDefence();

    /**
     * called every 3 ticks
     * @param p
     */
    void effectLoop(AdvancedPlayer p);
}