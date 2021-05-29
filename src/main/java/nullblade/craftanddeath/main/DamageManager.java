package nullblade.craftanddeath.main;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import nullblade.craftanddeath.items.ArmourItem;
import nullblade.craftanddeath.items.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;

public class DamageManager implements Listener { // to add custom armour with custom defence, null has to rewrite the damage system.
    private static DamageManager instance;

    public static DamageManager getInstance() {
        return instance;
    }

    private Map<String, ArmourItem> armour;

    public DamageManager() {
        instance = this;

        armour = new HashMap<>();

        Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
    }

    public void registerArmourPiece(String id, ArmourItem a) {
        armour.put(id, a);
    }

    public ArmourItem getArmourPiece(String id) {
        return armour.get(id);
    }

    public int getDefence(ItemStack item) {
        String id = ItemManager.get(item);

        if (id == null) {
            switch (item.getType()) {
                case LEATHER_BOOTS:
                case LEATHER_HELMET:
                    return 1;
                case LEATHER_LEGGINGS:
                case LEATHER_CHESTPLATE:
                case CHAINMAIL_BOOTS:
                case GOLD_HELMET:
                case GOLD_BOOTS:
                    return 2;

                case CHAINMAIL_HELMET:
                    return 3;

                case CHAINMAIL_CHESTPLATE:
                case IRON_CHESTPLATE:
                    return 7;

                case CHAINMAIL_LEGGINGS:
                case GOLD_LEGGINGS:
                    return 4;

                case GOLD_CHESTPLATE:
                case IRON_BOOTS:
                case IRON_LEGGINGS:
                case IRON_HELMET:
                    return 5;

                case DIAMOND_BOOTS:
                case DIAMOND_LEGGINGS:
                case DIAMOND_HELMET:
                    return 6;

                case DIAMOND_CHESTPLATE:
                    return 8;

            }
        } else {
            ArmourItem armourItem = armour.get(id);
            if (armourItem == null) {
                return 0;
            } else {
                return armourItem.getDefence();
            }
        }
        return 0;
    }


    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof LivingEntity) {
            LivingEntity l = ((LivingEntity) e.getEntity());
            int defence = 0;
            for (ItemStack i : l.getEquipment().getArmorContents()) {
                defence += getDefence(i);
                defence += i.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
                if (e.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION || e.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
                    defence += i.getEnchantmentLevel(Enchantment.PROTECTION_EXPLOSIONS) * 2;
                } else if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                    defence += i.getEnchantmentLevel(Enchantment.PROTECTION_FALL) * 2;
                } else if (e.getCause() == EntityDamageEvent.DamageCause.FIRE || e.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) {
                    defence += i.getEnchantmentLevel(Enchantment.PROTECTION_FIRE) * 2;
                } else if (e.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
                    defence += i.getEnchantmentLevel(Enchantment.PROTECTION_PROJECTILE) * 2;
                }
            }

            if (l.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)) {
                for (PotionEffect po : l.getActivePotionEffects()) {
                    if (po.getType().equals(PotionEffectType.DAMAGE_RESISTANCE)) {
                        defence *= 1.2 + (0.2 * po.getAmplifier());
                    }
                }
            }

            float damageMultiplier = 1 - (((float) Math.min(defence, 15)) / 25) - (((float) Math.min(defence, 140)) / 400);

            double damage = e.getOriginalDamage(EntityDamageEvent.DamageModifier.BASE) * damageMultiplier * 2;
            e.setDamage(0);
            if (l instanceof CraftPlayer) {
                EntityPlayer p = ((CraftPlayer) l).getHandle();
                float d = (float) Math.min(p.getAbsorptionHearts(), damage);
                p.setAbsorptionHearts((p.getAbsorptionHearts() - d));
                damage -= d;
                l.removePotionEffect(PotionEffectType.BLINDNESS);
                l.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 1));

                AdvancedPlayer pl = Main.getInstance().getPlayer(p.getUniqueID());

                if (pl.oldTimer != -1) {
                    Bukkit.getScheduler().cancelTask(pl.oldTimer);
                }
                pl.oldTimer = Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                    l.removePotionEffect(PotionEffectType.BLINDNESS);
                }, (int) (5 * damage) + 15).getTaskId();

                if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                    l.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (int) (20 * damage), 1));
                }
            }
            l.setHealth(l.getHealth() - Math.min(damage, l.getHealth()));
        }
    }

    public int getPlayersDefence(Player p) {
        int defence = 0;
        for (ItemStack i : p.getEquipment().getArmorContents()) {
            defence += getDefence(i);
            defence += i.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
        }

        if (p.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)) {
            for (PotionEffect po : p.getActivePotionEffects()) {
                if (po.getType().equals(PotionEffectType.DAMAGE_RESISTANCE)) {
                    defence *= 1.2 + (0.2 * po.getAmplifier());
                }
            }
        }
        return defence;
    }
}
