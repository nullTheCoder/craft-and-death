package nullblade.craftanddeath.content.creatures;

import net.minecraft.server.v1_8_R3.*;
import nullblade.craftanddeath.CustomMobs.EntityPart;
import nullblade.craftanddeath.CustomMobs.MobClass;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.material.MaterialData;

public class SoulEater extends MobClass {
    public SoulEater(Location loc) {
        super(loc);
        parts = new EntityPart[9];

        WorldServer nmsWorld = ((CraftWorld)loc.getWorld()).getHandle();

        parts[0] = new EntityPart(0, 0, 0, (byte)0, (byte)0);
        EntityArmorStand base = new EntityArmorStand(nmsWorld);
        base.setGravity(false);
        base.setInvisible(true);
        parts[0].base = base;

        parts[1] = new EntityPart(0.5f, 0, 0, (byte)0, (byte)0);
        base = new EntityArmorStand(nmsWorld);
        base.setGravity(false);
        base.setInvisible(true);
        parts[1].base = base;

        parts[2] = new EntityPart(1f, 0, 0, (byte)0, (byte)0);
        base = new EntityArmorStand(nmsWorld);
        base.setGravity(false);
        base.setInvisible(true);
        parts[2].base = base;

        parts[3] = new EntityPart(1.5f, 0, 0, (byte)0, (byte)0);
        base = new EntityArmorStand(nmsWorld);
        base.setGravity(false);
        base.setInvisible(true);
        parts[3].base = base;

        parts[5] = new EntityPart(2f, 0, 0, (byte)0, (byte)0);
        base = new EntityArmorStand(nmsWorld);
        base.setGravity(false);
        base.setInvisible(true);
        parts[5].base = base;

        parts[6] = new EntityPart(2.5f, 0, 0, (byte)0, (byte)0);
        base = new EntityArmorStand(nmsWorld);
        base.setGravity(false);
        base.setInvisible(true);
        parts[6].base = base;

        parts[7] = new EntityPart(3f, 0, 0, (byte)0, (byte)0);
        base = new EntityArmorStand(nmsWorld);
        base.setGravity(false);
        base.setInvisible(true);
        parts[7].base = base;

        parts[8] = new EntityPart(3.5f, 0, 0, (byte)0, (byte)0);
        base = new EntityArmorStand(nmsWorld);
        base.setGravity(false);
        base.setInvisible(true);
        parts[8].base = base;

        parts[4] = new EntityPart(-0.5f, 0.6f, 0, (byte)0, (byte)0);
        base = new EntityArmorStand(nmsWorld);
        base.setGravity(false);
        base.setInvisible(true);
        base.setSmall(true);
        parts[4].base = base;
    }
    @Override
    public void update() {
        rotX += 0.2;
    }
    @Override
    public void sendEquipment(PlayerConnection c) {
        ItemStack bw = CraftItemStack.asNMSCopy(new MaterialData(Material.WOOL, (byte)15).toItemStack(1));
        ItemStack rw = CraftItemStack.asNMSCopy(new MaterialData(Material.WOOL, (byte)14).toItemStack(1));
        c.sendPacket(new PacketPlayOutEntityEquipment(parts[0].base.getId(), 4, bw));
        c.sendPacket(new PacketPlayOutEntityEquipment(parts[1].base.getId(), 4, bw));
        c.sendPacket(new PacketPlayOutEntityEquipment(parts[2].base.getId(), 4, bw));
        c.sendPacket(new PacketPlayOutEntityEquipment(parts[3].base.getId(), 4, bw));
        c.sendPacket(new PacketPlayOutEntityEquipment(parts[5].base.getId(), 4, bw));
        c.sendPacket(new PacketPlayOutEntityEquipment(parts[6].base.getId(), 4, bw));
        c.sendPacket(new PacketPlayOutEntityEquipment(parts[7].base.getId(), 4, bw));
        c.sendPacket(new PacketPlayOutEntityEquipment(parts[8].base.getId(), 4, bw));
        c.sendPacket(new PacketPlayOutEntityEquipment(parts[4].base.getId(), 4, rw));
    }

}
