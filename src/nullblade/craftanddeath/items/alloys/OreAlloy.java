package nullblade.craftanddeath.items.alloys;

import org.bukkit.Material;

public interface OreAlloy {
    int getChance();
    boolean dropOnBreak();
    boolean dropOnSmelt();
    Material getBase();
}
