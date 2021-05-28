package nullblade.craftanddeath.content;

import net.minecraft.server.v1_8_R3.EntityArmorStand;
import nullblade.craftanddeath.CustomMobs.EntityPart;
import nullblade.craftanddeath.CustomMobs.MobClass;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

public class SoulEater extends MobClass {
    private float x, y, z;

    public SoulEater(Location loc) {
        super(8);

        parts[0] = new EntityPart();
        EntityArmorStand base = new EntityArmorStand(((CraftWorld)loc.getWorld()).getHandle());
        base.setEquipment();
    }
}
