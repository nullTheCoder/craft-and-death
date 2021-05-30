package nullblade.craftanddeath.items.crafts;

import nullblade.craftanddeath.items.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.util.HashMap;
import java.util.Map;

public class CustomRecipe {

    private String[] shape;
    private ItemStack result;

    private ShapedRecipe shapedRecipe;

    private Map<Character, Material> vanillaItems;
    private Map<Character, String> customItems;

    public String category;

    public CustomRecipe(ItemStack item) {
        vanillaItems = new HashMap<>();
        customItems = new HashMap<>();

        shapedRecipe = new ShapedRecipe(item);

        result = item;
    }


    public void shape(String[] shape) {
        shapedRecipe.shape(shape);
        this.shape = shape;
    }


    public void setVanillaIngredient(char symbol, Material ingredient) {
        vanillaItems.put(symbol, ingredient);
        shapedRecipe.setIngredient(symbol, ingredient);
    }

    public void setCustomIngredient(char symbol, String id) {
        ItemStack item = ItemManager.getInstance().get(id);

        if (item == null) {
            Bukkit.getLogger().warning("Item with id '" + id + "' not found");
            return;
        }

        shapedRecipe.setIngredient(symbol, item.getData());
        customItems.put(symbol, id);
        vanillaItems.put(symbol, item.getType());
    }


    public ItemStack getResult() {
        return result;
    }


    protected String getKeyString() {
        StringBuilder keys = new StringBuilder();
        for (String y : shape) {
            for (char x : y.toCharArray()) {
                keys.append(";").append(getIngredient(x));
            }
        }
        return keys.toString();
    }


    protected String[] getShape() {
        return shape;
    }

    protected String getIngredient(char c) {
        String ingredient;
        if (customItems.containsKey(c)) {
            ingredient = customItems.get(c);
        } else if (vanillaItems.containsKey(c)) {
            ingredient = vanillaItems.get(c).toString();
        } else {
            ingredient = Material.AIR.toString();
        }

        return ingredient;
    }

    protected ItemStack getIngredientItem(char c) {
        ItemStack ingredient;
        if (customItems.containsKey(c)) {
            ingredient = ItemManager.getInstance().get(customItems.get(c));
        } else ingredient = new ItemStack(vanillaItems.getOrDefault(c, Material.AIR));

        return ingredient;
    }


    protected void register() {
        Bukkit.addRecipe(shapedRecipe);
    }

    public ItemStack[] getItems() {
        return new ItemStack[] {
            getIngredientItem(shape[0].charAt(0)), getIngredientItem(shape[0].charAt(1)), getIngredientItem(shape[0].charAt(2)),
            getIngredientItem(shape[1].charAt(0)), getIngredientItem(shape[1].charAt(1)), getIngredientItem(shape[1].charAt(2)),
            getIngredientItem(shape[2].charAt(0)), getIngredientItem(shape[2].charAt(1)), getIngredientItem(shape[2].charAt(2))
        };

    }
}
