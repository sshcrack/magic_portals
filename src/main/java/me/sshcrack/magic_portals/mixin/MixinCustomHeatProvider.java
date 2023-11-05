package me.sshcrack.magic_portals.mixin;

import me.sshcrack.magic_portals.extension.CustomHeatRequirement;
import me.sshcrack.magic_portals.providers.CustomHeatHelper;
import net.joefoxe.hexerei.data.recipes.FluidMixingRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = FluidMixingRecipe.HeatCondition.class,remap = false)
public class MixinCustomHeatProvider implements CustomHeatHelper {
    @Unique
    private CustomHeatRequirement magicPortal$customHeat = CustomHeatRequirement.NONE;

    @Override
    public void magicPortal$setCustomHeat(CustomHeatRequirement req) {
        this.magicPortal$customHeat = req;
    }

    @Override
    public CustomHeatRequirement magicPortal$getCustomHeat() {
        return this.magicPortal$customHeat;
    }
}
