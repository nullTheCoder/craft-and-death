package nullblade.craftanddeath.content.creatures;

import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.WorldServer;
import nullblade.craftanddeath.CustomMobs.EntityPart;
import nullblade.craftanddeath.CustomMobs.MobClass;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

public class SoulEater extends MobClass {
    public SoulEater(Location loc) {
        super(loc);
        parts = new EntityPart[2];

        WorldServer nmsWorld = ((CraftWorld)loc.getWorld()).getHandle();

        parts[0] = new EntityPart(0, 0, 0);
        EntityArmorStand base = new EntityArmorStand(nmsWorld);
        base.setEquipment(0, new ItemStack(Item.getById(Material.LOG.getId())));
        base.setGravity(false);
        parts[0].base = base;

        parts[1] = new EntityPart(1, 0, 0);
        base = new EntityArmorStand(nmsWorld);
        base.setEquipment(0, new ItemStack(Item.getById(Material.LOG.getId())));
        base.setGravity(false);
        parts[1].base = base;
    }
}
