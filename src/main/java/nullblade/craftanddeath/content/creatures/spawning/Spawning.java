package nullblade.craftanddeath.content.creatures.spawning;

import net.minecraft.server.v1_8_R3.EnumParticle;
import nullblade.craftanddeath.CustomMobs.MobManager;
import nullblade.craftanddeath.main.Main;
import nullblade.craftanddeath.main.Util;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Random;

public class Spawning implements Listener {
    Random thread;

    public Spawning () {
        thread = new Random();
        Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        if (e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            if (thread.nextInt(100) <= 1) {
                if (e.getEntity().getKiller() != null) {
                    e.getEntity().getKiller().sendMessage("§4Something has taken the soul of the poor creature you just killed and it looks hungry for more.");
                }
                Util.sendParticle(EnumParticle.VILLAGER_ANGRY, e.getEntity().getLocation(), 50, 1.2f, 0.2f);
                Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> MobManager.getInstance().spawn(e.getEntity().getEyeLocation(), "soul_eater"), 30);
            }
        }
    }


}
