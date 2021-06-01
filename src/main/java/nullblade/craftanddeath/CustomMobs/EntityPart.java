package nullblade.craftanddeath.CustomMobs;

import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.Vector3f;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.atomic.AtomicBoolean;

// I must... not ... go ... insane
public class EntityPart {
    private float x, y, z;
    private byte rotX, rotY;
    public EntityLiving base;
    public AtomicBoolean changed = new AtomicBoolean(false);
    public float lastGlobalX, lastGlobalY, lastGlobalZ;
    public EntityPart[] children = new EntityPart[0];
    public int sinceLastTp;

    public EntityPart (float x, float y, float z, byte rotX, byte rotY) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.rotX = rotX;
        this.rotY = rotY;
    }

    public byte getRotX() {
        return rotX;
    }

    public byte getRotY() {
        return rotY;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }
}