package me.sshcrack.magic_portals.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.sshcrack.magic_portals.MagicPortalsMod;
import me.sshcrack.magic_portals.extension.CustomHeatRequirement;
import me.sshcrack.magic_portals.providers.CustomHeatHelper;
import net.joefoxe.hexerei.data.recipes.FluidMixingRecipe;
import net.joefoxe.hexerei.data.recipes.MixingCauldronRecipe;
import net.joefoxe.hexerei.tileentity.MixingCauldronTile;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = MixingCauldronTile.class, remap = false)
public class MixinMixingCauldronTile {
    @Unique
    @Nullable
    private FluidMixingRecipe.HeatCondition magicPortals$heatCond;

    @WrapOperation(method="*", at = @At(value = "INVOKE", target = "Lnet/joefoxe/hexerei/data/recipes/FluidMixingRecipe;getHeatCondition()Lnet/joefoxe/hexerei/data/recipes/FluidMixingRecipe$HeatCondition;"))
    private FluidMixingRecipe.HeatCondition magicPortals$storeRecipe(FluidMixingRecipe recipe, Operation<FluidMixingRecipe.HeatCondition> operation) {
        FluidMixingRecipe.HeatCondition cond = operation.call(recipe);
        this.magicPortals$heatCond = cond;

        return cond;
    }


    @WrapOperation(method="*", at = @At(value = "INVOKE", target = "Lnet/joefoxe/hexerei/data/recipes/MixingCauldronRecipe;getHeatCondition()Lnet/joefoxe/hexerei/data/recipes/FluidMixingRecipe$HeatCondition;"))
    private FluidMixingRecipe.HeatCondition magicPortals$storeRecipe(MixingCauldronRecipe recipe, Operation<FluidMixingRecipe.HeatCondition> operation) {
        FluidMixingRecipe.HeatCondition cond = operation.call(recipe);
        this.magicPortals$heatCond = cond;

        return cond;
    }


    @WrapOperation(
            method= "*",
            at=@At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/tags/TagKey;)Z")
    )
    private boolean magicPortals$redirectFirstInnerHeat(BlockState instance, TagKey<Block> e, Operation<Boolean> operation) {
        if (this.magicPortals$heatCond != null) {
            CustomHeatRequirement req = (CustomHeatHelper.class.cast(this.magicPortals$heatCond)).magicPortal$getCustomHeat();
            if(req != CustomHeatRequirement.NONE) {
                return operation.call(instance, req.getTag());
            }
        } else {
            MagicPortalsMod.LOGGER.warn("Recipe is null");
        }

        return operation.call(instance, e);
    }
}
