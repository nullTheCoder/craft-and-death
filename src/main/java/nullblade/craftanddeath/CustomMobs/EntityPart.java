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
    public boolean rotationChanged = false;

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

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public void rotX(byte degrees) {
        int r = degrees + rotX;
        if (r > 128) {
            r -= 256;
        } else if (r < -128) {
            r += 256;
        }
        rotX = (byte) r;
        rotationChanged = true;
    }

    public void rotY(byte degrees) {
        int r = degrees + rotY;
        if (r > 128) {
            r -= 256;
        } else if (r < -128) {
            r += 256;
        }
        rotY = (byte) r;
        rotationChanged = true;
    }
}