package nullblade.craftanddeath.CustomMobs;

import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import nullblade.craftanddeath.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class MobManager  {
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

        Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInstance(), this::updateEntityRendering, 0, 2);
        Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInstance(), this::handleSlowUpdate, 0, 5);
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
        try {
            for (MobClass e : mobs) {
                e.update();
                e.render();
                Collection<Entity> entities = e.world.getNearbyEntities(e.toLocation(), 32, 32, 32);
                for (Entity m : entities) {
                    if (m instanceof Player) {
                        e.newPlayer((Player) m);
                    }
                }
                for (PlayerConnection c : e.playersForWhoShowing) {
                    if (!entities.contains(c.player.getBukkitEntity().getPlayer())) {
                        e.destroyFor(c);
                    }
                }
            }
        } catch (ConcurrentModificationException ignored) {}
    }

    public void removeAll() {
        try {
            for (MobClass e : mobs) {
                for (PlayerConnection c : e.playersForWhoShowing) {
                    e.destroyFor(c);
                }
            }
        } catch (ConcurrentModificationException ignore) { }
    }

    public void removeFromList(MobClass m) {
        mobs.remove(m);
    }

    public void handleUse (PacketPlayInUseEntity u, Player pl) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), ()-> {
            if (u.a(((CraftWorld)pl.getWorld()).getHandle()) != null) {
                return;
            }
            try {
                Field f = u.getClass().getDeclaredField("a");
                f.setAccessible(true);
                int id = f.getInt(u);
                for (MobClass m : mobs) {
                    for (EntityPart p : m.parts) {
                        if (p.base.getId() == id) {
                            m.interactedWith(u.a(), id, p, pl);
                        }
                    }
                }

            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    public void handleSlowUpdate () {
        try {
            for (MobClass e : mobs) {
                e.slowUpdate();
            }
        } catch (ConcurrentModificationException ignored) {}
    }

}
