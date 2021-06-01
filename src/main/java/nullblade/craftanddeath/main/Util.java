package nullblade.craftanddeath.main;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Util {

    public static String formatString(String string) {
        return string.replace("$(PC)", "§3").replace("$(SC)", "§b").replace('&', '\u00a7');
    }


    public static Set<String> setFromJson(String json) {
        return json == null ? new HashSet<>() : new Gson().fromJson(json, new TypeToken<Set<String>>() {}.getType());
    }

    public static String addToJsonSet(String json, String element) {
        Set<String> set = setFromJson(json);

        set.add(element);
        return new Gson().toJson(set);
    }


    public static List<String> smartAutocomplete(List<String> variants, String[] args) {
        List<String> autocomplete = new ArrayList<>();

        for (String variant : variants) {
            if (variant.startsWith(args[args.length - 1])) {
                autocomplete.add(variant);
            }
        }

        return autocomplete;
    }

    public static void sendParticle(EnumParticle type, Location location, int count) {
        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(type, false, (float) location.getX(), (float) location.getY(), (float) location.getZ(), 0.5f, 0.5f, 0.5f, 1.0f, count);

        for (Entity player : location.getWorld().getNearbyEntities(location, 32, 32, 32)) {
            if (player instanceof CraftPlayer) {
                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
            }
        }
    }

    public static void sendParticle(EnumParticle type, Location location, int count, float distance, float speed) {
        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(type, false, (float) location.getX(), (float) location.getY(), (float) location.getZ(), distance, distance, distance, speed, count);

        for (Entity player : location.getWorld().getNearbyEntities(location, 64, 64, 64)) {
            if (player instanceof CraftPlayer) {
                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
            }
        }
    }

    public static void sendParticle(EnumParticle type, Location location, int count, float distance, float speed, int i) {
        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(type, false, (float) location.getX(), (float) location.getY(), (float) location.getZ(), distance, distance, distance, speed, count, i);

        for (Entity player : location.getWorld().getNearbyEntities(location, 64, 64, 64)) {
            if (player instanceof CraftPlayer) {
                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
            }
        }
    }
}
