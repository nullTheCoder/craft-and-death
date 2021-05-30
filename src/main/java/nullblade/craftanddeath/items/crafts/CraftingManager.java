package nullblade.craftanddeath.items.crafts;

import nullblade.craftanddeath.items.ItemManager;
import nullblade.craftanddeath.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class CraftingManager implements Listener {

    private static CraftingManager instance;

    private final Map<String, ItemStack> recipes;

    public CraftingManager() {
        instance = this;

        recipes = new HashMap<>();

        Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
    }


    public void registerRecipe(CustomRecipe recipe) {
        recipe.register();
        recipes.put(recipe.getKeyString(), recipe.getResult());

        RecipeBook.getInstance().addRecipe(recipe.category, recipe.getResult(), recipe.getItems());

    }

    @EventHandler
    public void onCraftPrepare(PrepareItemCraftEvent e) {
        CraftingInventory inventory = e.getInventory();
        boolean z = true;
        if (ItemManager.get(inventory.getResult()) != null) {
            inventory.setResult(new ItemStack(Material.AIR));
        }
        for (ItemStack i : inventory.getMatrix()) {
            if (ItemManager.get(i) != null) {
                z = false;
            }
        }
        if (z) {
            return;
        } else {
            inventory.setResult(new ItemStack(Material.AIR));
        }

        StringBuilder keys = new StringBuilder();
        for (ItemStack i : inventory.getMatrix()) {
            String id = ItemManager.get(i);
            if (id == null) {
                if (i != null)
                    keys.append(";").append(i.getType().toString());
            } else {
                keys.append(";").append(id);
            }
        }
        ItemStack r = recipes.get(keys.toString());
        if (r != null) {
            inventory.setResult(r);
        }
    }

    public static CraftingManager getInstance() {
        return instance;
    }
}
