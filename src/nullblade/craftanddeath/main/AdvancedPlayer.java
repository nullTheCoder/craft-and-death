package nullblade.craftanddeath.main;

import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import nullblade.craftanddeath.items.ArmourItem;
import nullblade.craftanddeath.items.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * Advanced player
 */
public class AdvancedPlayer {

    private BukkitTask loop;
    private final BukkitTask loop2;

    public Player player;

    // thirst
    public int thirst;
    public double distanceTillNextThirstChange;

    // temperature
    public double temperature;
    public AtomicDouble envTemperature = new AtomicDouble(30);

    // hell
    public ArmourItem[] armourItems = new ArmourItem[4];

    public int oldTimer = -1;

    public long lastDeathOn;

    public AdvancedPlayer(Player player) {

        this.player = player;

        File f = new File("craftAndDeath/players/" + player.getUniqueId().toString() + ".yml");
        if (!f.exists()) {
            new File("craftAndDeath/players").mkdirs();
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(f);

        if (config.contains("current_thirst"))
            thirst = config.getInt("current_thirst");
        else
            thirst = 100;
        if (config.contains("current_temperature"))
            temperature = config.getDouble("current_temperature");
        else
            temperature = 36.6d;


        // Action bar updating and armour updating

        loop = Bukkit.getScheduler().runTaskTimer(Main.getInstance(), () -> {
            PacketPlayOutChat packet = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" +
                String.format("§7[§3Water: §f%d/100§7 | §3Temperature: §f%s˚C§7 | §3Defence: §f%d§7]"
                        , thirst, new DecimalFormat("#.0").format(temperature), DamageManager.getInstance().getPlayersDefence(player))
                + "\"}"), (byte) 2);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
            for (ArmourItem a : armourItems) {
                if (a != null) {
                    a.effectLoop(this);
                }
            }
        }, 0, 3
        );


        //armour effect update loop. used to not ask some hashmaps for the item every time
        loop = Bukkit.getScheduler().runTaskTimer(Main.getInstance(), () -> {
            int x = 0;
            for (ItemStack i : player.getEquipment().getArmorContents()) {
                armourItems[x] = DamageManager.getInstance().getArmourPiece(ItemManager.get(i));
                x++;
            }
            }, 0, 40
        );


        // saving loop

        loop2 = Bukkit.getScheduler().runTaskTimer(Main.getInstance(), this::save, 0, 100
        );
    }

    public void save() {
        File f = new File("craftAndDeath/players/" + player.getUniqueId().toString() + ".yml");

        YamlConfiguration config = YamlConfiguration.loadConfiguration(f);

        config.set("current_thirst", thirst);
        config.set("current_temperature", temperature);

        try {
            config.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void onLeave() {
        save();

        Bukkit.getScheduler().cancelTask(loop.getTaskId());
        Bukkit.getScheduler().cancelTask(loop2.getTaskId());
    }
}
