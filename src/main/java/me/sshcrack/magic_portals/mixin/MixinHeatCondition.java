package me.sshcrack.magic_portals.mixin;

import me.sshcrack.magic_portals.extension.CustomHeatRequirement;
import me.sshcrack.magic_portals.providers.CustomHeatHelper;
import net.joefoxe.hexerei.data.recipes.FluidMixingRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = FluidMixingRecipe.HeatCondition.class, remap = false)
public class MixinHeatCondition {

    @Inject(method="getSerializedName", at = @At("HEAD"), cancellable = true)
    private void magicPortals$modifyHeatCondition(CallbackInfoReturnable<String> cir) {
        CustomHeatHelper helper = (CustomHeatHelper) this;
        CustomHeatRequirement req = helper.magicPortal$getCustomHeat();

        if (req == CustomHeatRequirement.NONE)
            return;

        cir.setReturnValue(req.getSerializedName());
    }

    @Inject(method="getHeated", at=@At("HEAD"), cancellable = true)
    private static void magicPortals$getHeated(String str, CallbackInfoReturnable<FluidMixingRecipe.HeatCondition> cir) {
        CustomHeatRequirement req = CustomHeatRequirement.fromString(str);
        if (req != CustomHeatRequirement.NONE) {
            FluidMixingRecipe.HeatCondition condition = FluidMixingRecipe.HeatCondition.HEATED;
            CustomHeatHelper.class.cast(condition).magicPortal$setCustomHeat(req);

            cir.setReturnValue(condition);
        }
    }
}
