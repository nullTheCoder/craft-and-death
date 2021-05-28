package nullblade.craftanddeath.CustomMobs;

import org.bukkit.Location;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class MobManager {
    private final Map<String, Class<MobClass>> classes;

    private Map<String, MobClass> mobs;

    public MobManager() {
        classes = new HashMap<>();
    }

    public void register(String id, Class<MobClass> class_) {

    }

    public void spawn(Location loc, String id) {
        String sLoc = loc.getWorld().toString() + "_" +  loc.getChunk().getX() + "_" + loc.getChunk().getZ();
        try {
            classes.get(id).getDeclaredConstructor(Location.class).newInstance(loc);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }


}
