package nullblade.craftanddeath.CustomMobs;

import net.minecraft.server.v1_8_R3.EntityLiving;

// I must... not ... go ... insane
public class EntityPart {
    private float x, y, z;
    public EntityLiving base;

    public EntityPart (float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
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
