package me.sshcrack.magic_portals.mixin;


import com.google.gson.JsonObject;
import me.sshcrack.magic_portals.extension.CustomHeatRequirement;
import me.sshcrack.magic_portals.providers.CustomHeatHelper;
import net.joefoxe.hexerei.data.recipes.FluidMixingRecipe;
import net.joefoxe.hexerei.data.recipes.MixingCauldronRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = MixingCauldronRecipe.Serializer.class,remap = false)
public abstract class MixinMixingCauldronRecipe$Serializer {
    @Inject(method = "fromJson(Lnet/minecraft/resources/ResourceLocation;Lcom/google/gson/JsonObject;)Lnet/joefoxe/hexerei/data/recipes/MixingCauldronRecipe;", at = @At("RETURN"))
    private void magicPortals$fromJson(ResourceLocation recipeId, JsonObject json, CallbackInfoReturnable<MixingCauldronRecipe> cir) {
        String heatRequirement = GsonHelper.getAsString(json, "heatRequirement", "none");
        CustomHeatRequirement requirement = CustomHeatRequirement.fromString(heatRequirement);

        if (requirement != CustomHeatRequirement.NONE) {
            FluidMixingRecipe.HeatCondition cond = cir.getReturnValue().getHeatCondition();
            CustomHeatHelper.class.cast(cond).magicPortal$setCustomHeat(requirement);
        }
    }

    @Inject(method="fromNetwork(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/network/FriendlyByteBuf;)Lnet/joefoxe/hexerei/data/recipes/MixingCauldronRecipe;", at=@At("RETURN"))
    private void magicPortals$fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer, CallbackInfoReturnable<MixingCauldronRecipe> cir) {
        CustomHeatRequirement requirement = buffer.readEnum(CustomHeatRequirement.class);

        if (requirement != CustomHeatRequirement.NONE) {
            FluidMixingRecipe.HeatCondition cond = cir.getReturnValue().getHeatCondition();
            CustomHeatHelper.class.cast(cond).magicPortal$setCustomHeat(requirement);
        }
    }

    @Inject(method="toNetwork(Lnet/minecraft/network/FriendlyByteBuf;Lnet/joefoxe/hexerei/data/recipes/MixingCauldronRecipe;)V", at=@At("RETURN"))
    private void magicPortals$toNetwork(FriendlyByteBuf buffer, MixingCauldronRecipe recipe, CallbackInfo ci) {
        buffer.writeEnum(((CustomHeatHelper) recipe).magicPortal$getCustomHeat());
    }
}