package nullblade.craftanddeath.mechanics;

import nullblade.craftanddeath.main.AdvancedPlayer;
import nullblade.craftanddeath.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.NumberConversions;

public class TemperatureAndThirst implements Listener {
    public TemperatureAndThirst() {
        Bukkit.getPluginManager().registerEvents(this, Main.getInstance());


        // thirst
        // thirst effects timer (checks for player's thirst and applies effects) and also for refilling thirst with water blocks
        Bukkit.getScheduler().runTaskTimer(Main.getInstance(), () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.isDead()) {
                    continue;
                }
                AdvancedPlayer a = Main.getInstance().getPlayer(p.getUniqueId());
                if (p.getWorld().getBlockAt(p.getLocation()).getType() == Material.STATIONARY_WATER) {
                    a.thirst += 40;
                    a.thirst = Math.min(a.thirst, 100);
                }
                if (a.thirst < 25) {
                    a.player.removePotionEffect(PotionEffectType.CONFUSION);
                    a.player.removePotionEffect(PotionEffectType.SLOW);
                    a.player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
                    a.player.removePotionEffect(PotionEffectType.WEAKNESS);
                    a.player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 40, 1));
                    a.player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 40, 1));
                    a.player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 2));
                    a.player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 40, 2));
                    if (a.thirst < 15 && a.thirst >= 5) {
                        a.player.removePotionEffect(PotionEffectType.POISON);
                        a.player.removePotionEffect(PotionEffectType.NIGHT_VISION);
                        a.player.removePotionEffect(PotionEffectType.BLINDNESS);
                        a.player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 40, 1));
                        a.player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 280, 1));
                        a.player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 280, 1));
                    } else if (a.thirst < 5) {
                        a.player.removePotionEffect(PotionEffectType.POISON);
                        a.player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 40, 200));
                        a.player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 40, 1));
                    }
                }
            }
            }, 0, 20
        );

        // temperature

        Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInstance(), () -> { //  async temperature calculation timer
                for (Player p : Bukkit.getOnlinePlayers()) {//  making it not async will lag out the server a pretty bit every (probably)
                    if (p.isDead()) {
                        continue;
                    }
                    float total = 0;
                    float endDivider = 0;
                    for (int x = -8 ; x < 8 ; x++) {
                        for (int y = -8 ; y < 8 ; y++) {
                            for (int z = -8 ; z < 8 ; z++) {
                                float a; // used in the math at the end
                                switch (p.getWorld().getBlockAt(p.getLocation().add(new Location(p.getWorld(), x, y, z))).getType()) {
                                    case STATIONARY_LAVA:
                                        a = 1500;
                                        break;
                                    case LAVA:
                                        a = 500;
                                        break;
                                    case ICE:
                                    case SNOW:
                                        a = -5;
                                        break;
                                    case PACKED_ICE:
                                    case SNOW_BLOCK:
                                        a = -10;
                                        break;
                                    case BURNING_FURNACE:
                                        a = 150;
                                        break;
                                    case TORCH:
                                        a = 80;
                                        break;
                                    case GLOWSTONE:
                                        a = 40;
                                        break;
                                    case FIRE:
                                        a = 50;
                                        break;
                                    default:
                                        a = 25;
                                        break;
                                }
                                final float i = ((Math.abs(x) + Math.abs(y) + Math.abs(z) + 1)/(a > 0 ? a : -a));
                                total += (a > 0 ? a : a*100) / i;
                                endDivider += 1f / i;
                            }
                        }
                    }
                    total /= endDivider;
                    switch (p.getWorld().getBiome(p.getLocation().getBlockX(), p.getLocation().getBlockZ())) { // . . .
                        case TAIGA:
                        case FROZEN_RIVER:
                        case ICE_PLAINS:
                            total -= 30;
                            break;
                        case DESERT:
                        case DESERT_HILLS:
                            total += 12;
                            break;
                        case HELL:
                            total += 20;
                            break;
                        case SKY:
                        case MEGA_SPRUCE_TAIGA_HILLS:
                            total -= 10;
                            break;
                        case EXTREME_HILLS:
                        case TAIGA_HILLS:
                        case COLD_BEACH:
                        case EXTREME_HILLS_PLUS:
                        case TAIGA_MOUNTAINS:
                        case COLD_TAIGA_MOUNTAINS:
                        case EXTREME_HILLS_MOUNTAINS:
                        case EXTREME_HILLS_PLUS_MOUNTAINS:
                            total -= 20;
                            break;
                        case FROZEN_OCEAN:
                            total -= 35;
                            break;
                        case ICE_MOUNTAINS:
                        case ICE_PLAINS_SPIKES:
                            total -= 50;
                            break;
                        case JUNGLE:
                        case JUNGLE_HILLS:
                        case JUNGLE_MOUNTAINS:
                            total += 5;
                            break;
                        case JUNGLE_EDGE:
                        case MESA:
                        case MESA_PLATEAU_FOREST:
                        case MESA_PLATEAU:
                        case MESA_BRYCE:
                            total += 3;
                            break;
                        case COLD_TAIGA:
                        case COLD_TAIGA_HILLS:
                            total -= 40;
                            break;
                        case MEGA_TAIGA:
                        case MEGA_TAIGA_HILLS:
                            total -= 17;
                            break;
                        case SAVANNA:
                        case SAVANNA_PLATEAU:
                            total += 8;
                            break;
                        case DESERT_MOUNTAINS:
                        case SAVANNA_MOUNTAINS:
                        case SAVANNA_PLATEAU_MOUNTAINS:
                            total += 15;
                            break;
                        case JUNGLE_EDGE_MOUNTAINS:
                            total += 2;
                            break;
                        case MESA_PLATEAU_FOREST_MOUNTAINS:
                        case MESA_PLATEAU_MOUNTAINS:
                            total += 4;
                            break;
                    }
                    total -= 10;

                    total += Math.sqrt(Math.abs(18075 - p.getWorld().getTime())) / 10; // time hell

                    int solidBlocks = 0;
                    for (int y = p.getLocation().getBlockY(); y < 255 ; y++) {
                        if (p.getWorld().getBlockAt(p.getLocation().getBlockX(), y, p.getLocation().getBlockZ()).getType() != Material.AIR) {
                            solidBlocks += 1;
                        }
                    }
                    total += solidBlocks * 0.3;

                    Main.getInstance().getPlayer(p.getUniqueId()).envTemperature.set(total);
                }
            }, 0, 40
        );

        Bukkit.getScheduler().runTaskTimer(Main.getInstance(), () -> { // sync temperature loop
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.isDead()) {
                    continue;
                }
                AdvancedPlayer a = Main.getInstance().getPlayer(p.getUniqueId());

                a.temperature += 0.3;
                if (a.temperature < 36.3) {
                    a.temperature += 0.1;
                    if (a.temperature < 36) {
                        a.temperature += 0.2;
                        if (a.temperature < 35.8 && p.getFoodLevel() > 2) {
                            a.temperature += 0.8;
                            p.setFoodLevel(p.getFoodLevel() - 1);
                        }
                        if (a.temperature < 35.4) {
                            a.player.removePotionEffect(PotionEffectType.CONFUSION);
                            a.player.removePotionEffect(PotionEffectType.SLOW);
                            a.player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
                            a.player.removePotionEffect(PotionEffectType.WEAKNESS);
                            a.player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 40, 1));
                            a.player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 40, 1));
                            a.player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 2));
                            a.player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 40, 2));
                            if (a.temperature < 34.8) {
                                p.setHealth(Math.max(p.getHealth() - 3, 0));
                            }
                        }
                    }
                }

                if (p.getFireTicks() > 0) {
                    a.temperature += 1;
                    a.thirst -= 3;
                }

                if (a.temperature > 37) {
                    a.temperature -= 0.3;
                    if (a.temperature > 38) {
                        if (a.thirst > 10) {
                            a.temperature -= 1.2;
                            a.thirst -= 2;
                        }
                        if (a.temperature > 39) {
                            a.player.removePotionEffect(PotionEffectType.BLINDNESS);
                            a.player.removePotionEffect(PotionEffectType.SLOW);
                            a.player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
                            a.player.removePotionEffect(PotionEffectType.WEAKNESS);
                            a.player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 1));
                            a.player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 40, 1));
                            a.player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 2));
                            a.player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 40, 2));
                            if (a.temperature > 41) {
                                p.setHealth(Math.max(p.getHealth() - 3, 0));
                            }
                        }
                    }
                }
                double tempDif = a.envTemperature.get() - a.temperature;
                if (tempDif > 0)
                    a.temperature += Math.cbrt(tempDif)/16;
                else
                    a.temperature += Math.cbrt(tempDif)/8;

            }
        }, 0, 20);

    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (e.getPlayer().isInsideVehicle()) return;
        AdvancedPlayer p = Main.getInstance().getPlayer(e.getPlayer().getUniqueId());

        double a = NumberConversions.square(e.getFrom().getX() - e.getTo().getX()) + NumberConversions.square(e.getFrom().getZ() - e.getTo().getZ());
        p.temperature += Math.cbrt(a)/(p.temperature/1.2);
        p.distanceTillNextThirstChange -= a;
        if (p.distanceTillNextThirstChange < 0) {
            p.distanceTillNextThirstChange = 30;
            p.thirst -= 1;
        }
    }

    @EventHandler // thirst from food
    public void onConsume(PlayerItemConsumeEvent e) {
        AdvancedPlayer p = Main.getInstance().getPlayer(e.getPlayer().getUniqueId());
        switch (e.getItem().getType()) {
            case POTION:
                p.thirst += 50;
                p.thirst = Math.min(p.thirst, 100);
                break;
            case APPLE:
                p.thirst += 5;
                p.thirst = Math.min(p.thirst, 100);
                break;
            case GOLDEN_APPLE:
                p.thirst = 100;
                break;
            case CARROT:
                p.thirst += 2;
                p.thirst = Math.min(p.thirst, 100);
                break;
            case MELON:
            case MUSHROOM_SOUP:
            case RABBIT_STEW:
                p.thirst += 15;
                p.thirst = Math.min(p.thirst, 100);
                break;
            case MILK_BUCKET:
                p.thirst += 10;
                p.thirst = Math.min(p.thirst, 100);
                break;
            default:
                p.thirst -= 10;
                break;
        }
    }
}