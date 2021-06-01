package nullblade.craftanddeath.CustomMobs;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;


public class MobClass {
    public EntityPart[] parts;
    public float x, y, z;
    public float rotX;
    public List<PlayerConnection> playersForWhoShowing;
    public World world;

    public MobClass(Location loc) {
        playersForWhoShowing = new ArrayList<>();
        world = loc.getWorld();

        x = (float) loc.getX();
        y = (float) loc.getY();
        z = (float) loc.getZ();
        rotX = 0;
    }

    public void update() {}

    public void render() {
        for (PlayerConnection c : playersForWhoShowing) {
            float sin = (float) Math.sin(rotX);
            float cos = (float) Math.cos(rotX);
            for (EntityPart p : parts) {
                float x_ = ((sin * p.getX()) + (cos * p.getZ()))+x;
                float y_ = p.getY() + y;
                float z_ = ((cos * p.getX()) + (sin * p.getZ()))+z;

                c.sendPacket(new PacketPlayOutEntity.PacketPlayOutRelEntityMove(p.base.getId(),
                        (byte) ((x_ * 32) - p.lastGlobalX),
                        (byte) ((y_ * 32) - p.lastGlobalY),
                        (byte) ((z_ * 32) - p.lastGlobalZ),
                        true));
                p.lastGlobalX += (byte) ((x_ * 32) - p.lastGlobalX);
                p.lastGlobalY += (byte) ((y_ * 32) - p.lastGlobalY);
                p.lastGlobalZ += (byte) ((z_ * 32) - p.lastGlobalZ);
            }
        }
    }

    public void newPlayer(Player pl) {
        PlayerConnection c = ((CraftPlayer)pl).getHandle().playerConnection;
        if (playersForWhoShowing.contains(c))
            return;
        playersForWhoShowing.add(c);
        float sin = (float) Math.sin(rotX);
        float cos = (float) Math.cos(rotX);
        for (EntityPart p : parts) {
            float x_ = ((sin * p.getX()) + (cos * p.getZ()))+x;
            float y_ = p.getY() + y;
            float z_ = ((cos * p.getX()) + (sin * p.getZ()))+z;

            p.base.locX = x_;
            p.base.locY = y_;
            p.base.locZ = z_;

            c.sendPacket(new PacketPlayOutSpawnEntityLiving(p.base));

            p.lastGlobalX = x_ * 32;
            p.lastGlobalY = y_ * 32;
            p.lastGlobalZ = z_ * 32;
        }
        sendEquipment(c);
    }

    public void destroyFor(Player pl) {
        PlayerConnection c = ((CraftPlayer)pl).getHandle().playerConnection;
        for (EntityPart p : parts) {
            c.sendPacket(new PacketPlayOutEntityDestroy(p.base.getId()));
        }
    }

    public void sendEquipment(PlayerConnection c) {

    }

    public Location toLocation() {
        return new Location(world, x, y, z);
    }

    public void coolKidRotate(float degrees) {
        
    }
}
