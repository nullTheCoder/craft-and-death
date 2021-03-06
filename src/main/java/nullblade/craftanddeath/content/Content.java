package nullblade.craftanddeath.content;

import nullblade.craftanddeath.content.items.*;
import nullblade.craftanddeath.content.items.armour.noset.LeatherRobe;
import nullblade.craftanddeath.content.items.armour.sets.iceplatearmour.IcePlateBoots;
import nullblade.craftanddeath.content.items.armour.sets.iceplatearmour.IcePlateChestplate;
import nullblade.craftanddeath.content.items.armour.sets.iceplatearmour.IcePlateHelmet;
import nullblade.craftanddeath.content.items.armour.sets.iceplatearmour.IcePlateLeggings;

public class Content {
    public static void init() {

        CraftSections.initialize();



        // items
        new FireProofFiber();
        new FireProofLeather();
        new IceChunk();
        new IcePlate();
        new IceBottle();

        // usable items
        new Thermometer();

        //armour
        new LeatherRobe();

        new IcePlateBoots();
        new IcePlateLeggings();
        new IcePlateChestplate();
        new IcePlateHelmet();
    }
}
