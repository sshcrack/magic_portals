package me.sshcrack.magic_portals;

import com.mojang.logging.LogUtils;
import me.sshcrack.magic_portals.block.ModBlocks;
import me.sshcrack.magic_portals.fluid.ModFluids;
import me.sshcrack.magic_portals.fluid.ModFluidTypes;
import me.sshcrack.magic_portals.item.ModItems;
import me.sshcrack.magic_portals.loot.ModLootModifiers;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MagicPortalsMod.MODID)
public class MagicPortalsMod
{
    public static final String MODID = "magic_portals";
    public static final Logger LOGGER = LogUtils.getLogger();

    public MagicPortalsMod()
    {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();


        // Register the commonSetup method for modloading
        eventBus.addListener(this::commonSetup);

        ModBlocks.register(eventBus);
        ModItems.register(eventBus);
        ModFluids.register(eventBus);
        ModFluidTypes.register(eventBus);
        ModLootModifiers.register(eventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            event.enqueueWork(() -> {
                ItemBlockRenderTypes.setRenderLayer(ModFluids.PHANTOM_POWER_FLUID.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.PHANTOM_POWER_FLOWING.get(), RenderType.translucent());
            });
            // Some client setup code
        }
    }
}
