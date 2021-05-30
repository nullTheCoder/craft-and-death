package nullblade.craftanddeath.CustomMobs;

import net.minecraft.server.v1_8_R3.PacketPlayOutEntity;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;


public class MobClass {
    public EntityPart[] parts;
    private float x, y, z;
    public List<PlayerConnection> playersForWhoRendering;

    public MobClass(Location loc) {
        playersForWhoRendering = new ArrayList<>();

        x = (float) loc.getX();
        y = (float) loc.getY();
        z = (float) loc.getZ();
    }

    public void render() {
        for (PlayerConnection c : playersForWhoRendering) {
            for (EntityPart p : parts) {
                if (p.changed.get()) {
                    if (((p.getX() + x) - p.lastGlobalX*32) < 128 && ((p.getX() + x) - p.lastGlobalX*32) > -128 &&
                        ((p.getY() + y) - p.lastGlobalY*32) < 128 && ((p.getY() + y) - p.lastGlobalY*32) > -128 &&
                        ((p.getZ() + z) - p.lastGlobalZ*32) < 128 && ((p.getZ() + z) - p.lastGlobalZ*32) > -128) {
                        c.sendPacket(new PacketPlayOutEntity.PacketPlayOutRelEntityMove(p.base.getId(),
                                (byte) ((p.getX() + x) - p.lastGlobalX * 32),
                                (byte) ((p.getY() + y) - p.lastGlobalY * 32),
                                (byte) ((p.getZ() + z) - p.lastGlobalZ * 32),
                                true));
                    }
                    else {
                        c.sendPacket(new PacketPlayOutEntityTeleport());
                    }
                }
            }
        }
    }

    public void newPlayer(Player p) {

    }

    public void destroyFor(Player p) {

    }
}
