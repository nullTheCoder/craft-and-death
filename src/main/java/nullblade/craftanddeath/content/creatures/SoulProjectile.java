package nullblade.craftanddeath.content.creatures;

import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.server.v1_8_R3.*;
import nullblade.craftanddeath.CustomMobs.EntityPart;
import nullblade.craftanddeath.CustomMobs.MobClass;
import nullblade.craftanddeath.CustomMobs.MobManager;
import nullblade.craftanddeath.main.DamageManager;
import nullblade.craftanddeath.main.Main;
import nullblade.craftanddeath.main.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.material.MaterialData;

import java.util.concurrent.atomic.AtomicReference;

public class SoulProjectile extends MobClass {

    private final AtomicReference<Location> headingTo;
    private final AtomicDouble targetedRot;

    public SoulProjectile(Location loc) {
        super(loc);
        headingTo = new AtomicReference<>();
        targetedRot = new AtomicDouble();
        parts = new EntityPart[6];

        WorldServer nmsWorld = ((CraftWorld)loc.getWorld()).getHandle();

        parts[1] = new EntityPart(0f, 0, 0, (byte)0, (byte)0);
        EntityArmorStand base = new EntityArmorStand(nmsWorld);
        base.setGravity(false);
        base.setInvisible(true);
        parts[1].base = base;

        parts[0] = new EntityPart(0f, 0f, 0f, (byte)5, (byte)0);
        base = new EntityArmorStand(nmsWorld);
        base.setGravity(false);
        base.setInvisible(true);
        parts[0].base = base;

        parts[2] = new EntityPart(0f, 1.2f, 0, (byte)0, (byte)0);
        base = new EntityArmorStand(nmsWorld);
        base.setGravity(false);
        base.setInvisible(true);
        base.setSmall(true);
        parts[2].base = base;

        parts[3] = new EntityPart(0f, 1.2f, 0f, (byte)5, (byte)0);
        base = new EntityArmorStand(nmsWorld);
        base.setGravity(false);
        base.setInvisible(true);
        base.setSmall(true);
        parts[3].base = base;

        parts[4] = new EntityPart(0f, 0.8f, 0, (byte)0, (byte)0);
        base = new EntityArmorStand(nmsWorld);
        base.setGravity(false);
        base.setInvisible(true);
        base.setSmall(true);
        parts[4].base = base;

        parts[5] = new EntityPart(0f, 0.4f, 0f, (byte)5, (byte)0);
        base = new EntityArmorStand(nmsWorld);
        base.setGravity(false);
        base.setInvisible(true);
        base.setSmall(true);
        parts[5].base = base;
    }
    @Override
    public void update() {
        parts[0].rotX((byte) 3);
        parts[1].rotX((byte) 9);
        parts[2].rotX((byte) 3);
        parts[3].rotX((byte) 9);
        parts[4].rotX((byte) 3);
        parts[5].rotX((byte) 9);
        if (headingTo.get() != null) {
            if (targetedRot.get() > rotX) {
                rotX += 1f;
            } else if (targetedRot.get() < rotX) {
                rotX -= 1f;
            }
            if (headingTo.get().distance(toLocation()) > 1.5) {
                x -= Math.sin(rotX)*1.5;
                z -= Math.cos(rotX)*1.5;
                if (headingTo.get().getY() > y) {
                    y += 0.5;
                } else if (headingTo.get().getY() < y) {
                    y -= 0.5;
                }
            }
        }
    }
    @Override
    public void slowUpdate() {
        headingTo.set(toLocation());
        for (Entity e : world.getNearbyEntities(toLocation(), 18, 18, 18)) {
            if (e instanceof Player) {
                headingTo.set(e.getLocation());
                Location toCord = e.getLocation().subtract(toLocation());
                targetedRot.set(Math.atan2(toCord.getX(), toCord.getZ()) - Math.PI);
                if (headingTo.get().distance(toLocation()) < 1.5) {
                    Bukkit.getScheduler().runTask(Main.getInstance(), () -> DamageManager.getInstance().damage(e, EntityDamageEvent.DamageCause.ENTITY_EXPLOSION, 0.5));
                    Util.sendParticle(EnumParticle.ENCHANTMENT_TABLE, toLocation(), 10);
                }
            }
        }
    }
    @Override
    public void sendEquipment(PlayerConnection c) {
        ItemStack bw = CraftItemStack.asNMSCopy(new MaterialData(Material.STAINED_GLASS, (byte)10).toItemStack(1));
        c.sendPacket(new PacketPlayOutEntityEquipment(parts[0].base.getId(), 4, bw));
        c.sendPacket(new PacketPlayOutEntityEquipment(parts[1].base.getId(), 4, bw));
        c.sendPacket(new PacketPlayOutEntityEquipment(parts[2].base.getId(), 4, bw));
        c.sendPacket(new PacketPlayOutEntityEquipment(parts[3].base.getId(), 4, bw));
        c.sendPacket(new PacketPlayOutEntityEquipment(parts[4].base.getId(), 4, bw));
        c.sendPacket(new PacketPlayOutEntityEquipment(parts[5].base.getId(), 4, bw));
    }

    @Override
    public void interactedWith (PacketPlayInUseEntity.EnumEntityUseAction useAction, int id, EntityPart entityPart, Player p) {
        if (useAction.equals(PacketPlayInUseEntity.EnumEntityUseAction.ATTACK)) {
            ItemStack rg = CraftItemStack.asNMSCopy(new MaterialData(Material.STAINED_GLASS, (byte)14).toItemStack(1));
            for (PlayerConnection c : playersForWhoShowing) {
                c.sendPacket(new PacketPlayOutEntityEquipment(parts[0].base.getId(), 4, rg));
                c.sendPacket(new PacketPlayOutEntityEquipment(parts[1].base.getId(), 4, rg));
                c.sendPacket(new PacketPlayOutEntityEquipment(parts[2].base.getId(), 4, rg));
                c.sendPacket(new PacketPlayOutEntityEquipment(parts[3].base.getId(), 4, rg));
                c.sendPacket(new PacketPlayOutEntityEquipment(parts[4].base.getId(), 4, rg));
                c.sendPacket(new PacketPlayOutEntityEquipment(parts[5].base.getId(), 4, rg));
                Bukkit.getScheduler().runTask(Main.getInstance(), () -> MobManager.getInstance().removeFromList(this));
                Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                    for (int i = 0; i < parts.length; i++) {
                        c.sendPacket(new PacketPlayOutEntityDestroy(parts[i].base.getId()));
                    }
                }, 20);
            }
        }
    }
}
