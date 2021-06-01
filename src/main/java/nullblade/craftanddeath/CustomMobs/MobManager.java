package nullblade.craftanddeath.CustomMobs;

import nullblade.craftanddeath.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MobManager {
    private static MobManager instance;

    public static MobManager getInstance() {
        return instance;
    }

    private final Map<String, Class<? extends MobClass>> classes;

    private final List<MobClass> mobs;

    public MobManager() {
        instance = this;
        classes = new HashMap<>();
        mobs = new ArrayList<>();

        Bukkit.getScheduler().runTaskTimer(Main.getInstance(), this::updateEntityRendering, 0, 2);

    }

    public void register(String id, Class<? extends MobClass> class_) {
        classes.put(id, class_);
    }

    public Map<String, Class<? extends MobClass>> getClasses() {
        return classes;
    }

    public void spawn(Location loc, String id) {
        try {
            mobs.add(classes.get(id).getDeclaredConstructor(Location.class).newInstance(loc));
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void updateEntityRendering() {
        for (MobClass e : mobs) {
            Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () ->
            {
                e.update();
                e.render();
                for (Entity m : e.world.getNearbyEntities(e.toLocation(), 64, 64, 64)) {
                    if (m instanceof Player) {
                        e.newPlayer((Player) m);
                    }
                }
            });
        }
    }
}
