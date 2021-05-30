package nullblade.craftanddeath.CustomMobs;

import org.bukkit.Location;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class MobManager {
    private static MobManager instance;

    public static MobManager getInstance() {
        return instance;
    }

    private final Map<String, Class<? extends MobClass>> classes;

    private final Map<String, MobClass> mobs;

    public MobManager() {
        instance = this;
        classes = new HashMap<>();
        mobs = new HashMap<>();
    }

    public void register(String id, Class<? extends MobClass> class_) {
        classes.put(id, class_);
    }

    public Map<String, Class<? extends MobClass>> getClasses() {
        return classes;
    }

    public void spawn(Location loc, String id) {
        String sLoc = loc.getWorld().toString() + "_" +  loc.getChunk().getX() + "_" + loc.getChunk().getZ();
        try {
            mobs.put(sLoc, classes.get(id).getDeclaredConstructor(Location.class).newInstance(loc));
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }





}
