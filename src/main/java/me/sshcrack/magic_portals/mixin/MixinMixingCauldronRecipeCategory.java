package me.sshcrack.magic_portals.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import me.sshcrack.magic_portals.extension.CustomHeatRequirement;
import me.sshcrack.magic_portals.providers.CustomHeatHelper;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import net.joefoxe.hexerei.data.recipes.MixingCauldronRecipe;
import net.joefoxe.hexerei.integration.jei.MixingCauldronRecipeCategory;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.ArrayList;
import java.util.List;

@Mixin(value = MixingCauldronRecipeCategory.class,remap = false)
public abstract class MixinMixingCauldronRecipeCategory {
    @Shadow public abstract boolean isHovering(double mouseX, double mouseY, double x, double y, double width, double height);

    @Shadow private Block heatSource;
    @Unique
    private MixingCauldronRecipe magicPortals$recipe;


    @Inject(
            method = "draw(Lnet/joefoxe/hexerei/data/recipes/MixingCauldronRecipe;Lmezz/jei/api/gui/ingredient/IRecipeSlotsView;Lcom/mojang/blaze3d/vertex/PoseStack;DD)V",
            at= @At(value="HEAD")
    )
    private void magicPortals$storeRecipe(MixingCauldronRecipe recipe, IRecipeSlotsView view, PoseStack matrixStack, double mouseX, double mouseY, CallbackInfo ci) {
        this.magicPortals$recipe = recipe;
    }

    @WrapOperation(
        method = "draw(Lnet/joefoxe/hexerei/data/recipes/MixingCauldronRecipe;Lmezz/jei/api/gui/ingredient/IRecipeSlotsView;Lcom/mojang/blaze3d/vertex/PoseStack;DD)V",
        at=@At(
                value = "INVOKE",
                target = "Lnet/joefoxe/hexerei/integration/jei/FluidMixingRecipeCategory;getTagStack(Lnet/minecraft/tags/TagKey;)Lnet/minecraft/world/level/block/Block;"
        )
    )
    private Block magicPortals$changeCustomBlock(TagKey<Block> key, Operation<Block> operation) {
        if(this.magicPortals$recipe != null) {
            CustomHeatRequirement req = CustomHeatHelper.class.cast(this.magicPortals$recipe.getHeatCondition()).magicPortal$getCustomHeat();

            this.magicPortals$recipe = null;
            if(req != CustomHeatRequirement.NONE) {
                return operation.call(req.getTag());
            }
        }

        return operation.call(key);
    }

    @Inject(
            method = "getTooltipStrings(Lnet/joefoxe/hexerei/data/recipes/MixingCauldronRecipe;Lmezz/jei/api/gui/ingredient/IRecipeSlotsView;DD)Ljava/util/List;",
            at=@At("HEAD"),
            cancellable = true
    )
    private void magicPortals$changeTooltip(MixingCauldronRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY, CallbackInfoReturnable<List<Component>> cir) {
        CustomHeatRequirement req = CustomHeatHelper.class.cast(recipe.getHeatCondition()).magicPortal$getCustomHeat();
        if(req == CustomHeatRequirement.NONE || !this.isHovering(mouseX, mouseY, 79.0, 59.0, 24.0, 18.0) ) {
            return;
        }

        ArrayList<Component> tooltip = new ArrayList<>();
        tooltip.add(Component.translatable("tooltip.magic_portals.spirit_heated"));
        if (Screen.hasShiftDown()) {
            tooltip.add(Component.translatable("<%s>", Component.translatable("tooltip.hexerei.shift").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(11167232)))).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(10066329))));
            tooltip.add(Component.translatable("tooltip.magic_portals.recipe_spirit1").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(10066329))));
            tooltip.add(Component.translatable("tooltip.magic_portals.recipe_spirit2").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(10066329))));
            tooltip.add(Component.translatable("tooltip.magic_portals.recipe_spirit3").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(10066329))));
            tooltip.add(Component.translatable("tooltip.magic_portals.recipe_spirit4").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(10066329))));
            tooltip.add(Component.translatable("Spirit source shown: - %s", Component.translatable(this.heatSource.getDescriptionId()).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(13391138)))).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(10066329))));
        } else {
            tooltip.add(Component.translatable("[%s]", Component.translatable("tooltip.hexerei.shift").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(11184640)))).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(10066329))));
            tooltip.add(Component.translatable("tooltip.magic_portals.recipe_spirit").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(10066329))));
        }
        cir.setReturnValue(tooltip);
    }
}
