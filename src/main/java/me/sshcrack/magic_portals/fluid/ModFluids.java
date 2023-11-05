package me.sshcrack.magic_portals.fluid;

import me.sshcrack.magic_portals.MagicPortalsMod;
import me.sshcrack.magic_portals.block.ModBlocks;
import me.sshcrack.magic_portals.item.ModItems;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, MagicPortalsMod.MODID);
    public static final Material PHANTOM_POWER = (new Material.Builder(MaterialColor.WATER)).noCollider().nonSolid().replaceable().liquid().build();

    public static final RegistryObject<Fluid> PHANTOM_POWER_FLOWING = FLUIDS.register("phantom_power_flowing", () ->
        new PhantomsPower.Flowing(getBloodProperties())
    );
    public static final RegistryObject<PhantomsPower.Source> PHANTOM_POWER_FLUID = FLUIDS.register("phantom_power_fluid", () ->
        new PhantomsPower.Source(getBloodProperties())
    );

    public static final RegistryObject<LiquidBlock> PHANTOM_POWER_BLOCK = ModBlocks.BLOCKS.register("phantom_power", () ->
        new LiquidBlock(PHANTOM_POWER_FLUID, BlockBehaviour.Properties.of(PHANTOM_POWER).noCollission().explosionResistance(100.0F).noLootTable())
    );

    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }

    private static ForgeFlowingFluid.Properties getBloodProperties() {
        return (new ForgeFlowingFluid.Properties(ModFluidTypes.PHANTOM_POWER_TYPE, PHANTOM_POWER_FLUID, PHANTOM_POWER_FLOWING))
                .block(PHANTOM_POWER_BLOCK)
                .bucket(ModItems.PHANTOM_POWER_BUCKET)
                .slopeFindDistance(2)
                .levelDecreasePerBlock(2);
    }
}
