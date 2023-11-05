package me.sshcrack.magic_portals.fluid;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.math.Vector3f;
import me.sshcrack.magic_portals.MagicPortalsMod;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.SoundAction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.forgespi.Environment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class ModFluidTypes {
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, MagicPortalsMod.MODID);


    public static final ResourceLocation PHANTOM_POWER_STILL_RL = new ResourceLocation(MagicPortalsMod.MODID, "block/blood_still");
    public static final ResourceLocation PHANTOM_POWER_FLOWING_RL = new ResourceLocation(MagicPortalsMod.MODID, "block/blood_flow");
    public static final ResourceLocation PHANTOM_POWER_OVERLAY_RL = new ResourceLocation(MagicPortalsMod.MODID, "block/blood_overlay");

    public static final RegistryObject<FluidType> PHANTOM_POWER_TYPE = FLUID_TYPES.register("phantom_power_fluid", () -> {
        return new FluidType(FluidType.Properties.create().supportsBoating(true).canHydrate(true).lightLevel(0).density(1500).viscosity(2000).sound(SoundAction.get("bucket_fill"), SoundEvents.HONEY_DRINK)) {
            public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
                consumer.accept(new IClientFluidTypeExtensions() {
                    private static final ResourceLocation STILL;
                    private static final ResourceLocation FLOW;
                    private static final ResourceLocation OVERLAY;
                    private static final ResourceLocation VIEW_OVERLAY;

                    public ResourceLocation getStillTexture() {
                        return STILL;
                    }

                    public ResourceLocation getFlowingTexture() {
                        return FLOW;
                    }

                    public ResourceLocation getOverlayTexture() {
                        return OVERLAY;
                    }

                    public ResourceLocation getRenderOverlayTexture(Minecraft mc) {
                        return VIEW_OVERLAY;
                    }

                    public int getTintColor() {
                        return -100663297;
                    }

                    public @NotNull Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
                        int color = this.getTintColor();
                        return new Vector3f(0.1875F, 0.015686275F, 0.015686275F);
                    }

                    public void modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick, float nearDistance, float farDistance, FogShape shape) {
                        nearDistance = -8.0F;
                        farDistance = 4.0F;
                        if (farDistance > renderDistance) {
                            farDistance = renderDistance;
                            shape = FogShape.CYLINDER;
                        }

                        RenderSystem.setShaderFogStart(nearDistance);
                        RenderSystem.setShaderFogEnd(farDistance);
                        RenderSystem.setShaderFogShape(shape);
                    }

                    static {
                        STILL = net.joefoxe.hexerei.fluid.ModFluidTypes.BLOOD_STILL_RL;
                        FLOW = net.joefoxe.hexerei.fluid.ModFluidTypes.BLOOD_FLOWING_RL;
                        OVERLAY = net.joefoxe.hexerei.fluid.ModFluidTypes.BLOOD_OVERLAY_RL;
                        VIEW_OVERLAY = new ResourceLocation("textures/block/nether_wart_block.png");
                    }
                });
            }
        };
    });

    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
    }
}
