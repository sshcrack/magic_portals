package me.sshcrack.magic_portals.item;

import me.sshcrack.magic_portals.MagicPortalsMod;
import me.sshcrack.magic_portals.TimeSpaceWarper;
import me.sshcrack.magic_portals.fluid.ModFluids;
import net.joefoxe.hexerei.item.ModItemGroup;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.Keys.ITEMS, MagicPortalsMod.MODID);


    public static final RegistryObject<Item> TIME_SPACE_WRAPER = ITEMS.register("time_space_warper", TimeSpaceWarper::new);
    public static final RegistryObject<Item> RESONATING_ORB = ITEMS.register("resonating_orb", ResonatingOrbItem::new);
    public static RegistryObject<BucketItem> PHANTOM_POWER_BUCKET = ITEMS.register("phantom_power_bucket", () ->
        new BucketItem(ModFluids.PHANTOM_POWER_FLUID, (new Item.Properties()).stacksTo(1).tab(ModItemGroup.HEXEREI_GROUP))
    );

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
