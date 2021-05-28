package nullblade.craftanddeath.CustomMobs;

import nullblade.craftanddeath.main.AdvancedPlayer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class MobClass {
    public EntityPart[] parts;
    private float x , y, z;

    public MobClass(Location loc) {
        x = (float) loc.getX();
        y = (float) loc.getY();
        z = (float) loc.getZ();
    }

    public void sendDataToNew(Player p) {

    }

    public void sendDataToExisting(AdvancedPlayer p) {

    }

    public void destroyFor(Player p) {

    }
}
