package nullblade.craftanddeath.items.crafts;

import org.bukkit.event.Listener;

public class SmeltingManager implements Listener {

//    private Map<String, ItemStack> smelts;
//
//    public SmeltingManager() {
//        smelts = new HashMap<>();
//    }
//
//    public void addSmelt(String ItemFrom, String ItemTo) {
//        Bukkit.getServer().addRecipe(new FurnaceRecipe(DreamingWorld.getInstance().getItemManager().get(ItemTo), DreamingWorld.getInstance().getItemManager().get(ItemFrom).getType()));
//        smelts.put(ItemFrom, DreamingWorld.getInstance().getItemManager().get(ItemTo));
//    }
//
//    @EventHandler
//    public void onSmelt(FurnaceSmeltEvent e) {
//        if (TagWizard.getItemTag(e.getSource(), "id") != null) {
//            if (smelts.containsKey(TagWizard.getItemTag(e.getSource(), "id"))) {
//                e.setResult(smelts.get(TagWizard.getItemTag(e.getSource(), "id")));
//            }
//            else {
//                e.setCancelled(true);
//            }
//        }
//    }

}
