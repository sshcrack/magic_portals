package me.sshcrack.magic_portals.fluid;

import me.sshcrack.magic_portals.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class PhantomsPower extends ForgeFlowingFluid {
    protected PhantomsPower(ForgeFlowingFluid.Properties properties) {
        super(properties);
    }

    public Fluid getFlowing() {
        return ModFluids.PHANTOM_POWER_FLOWING.get();
    }

    public Fluid getSource() {
        return ModFluids.PHANTOM_POWER_FLUID.get();
    }

    protected int getSlopeFindDistance(LevelReader worldIn) {
        return 4;
    }

    protected int getDropOff(LevelReader worldIn) {
        return 2;
    }

    public Item getBucket() {
        return ModItems.PHANTOM_POWER_BUCKET.get();
    }

    protected boolean canBeReplacedWith(FluidState fluidState, BlockGetter blockReader, BlockPos pos, Fluid fluid, Direction direction) {
        return direction == Direction.DOWN && !fluid.is(FluidTags.WATER);
    }

    public int getTickDelay(LevelReader p_205569_1_) {
        return 5;
    }

    protected float getExplosionResistance() {
        return 100.0F;
    }

    protected BlockState createLegacyBlock(FluidState state) {
        return ModFluids.PHANTOM_POWER_BLOCK.get().defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(state));
    }

    public boolean isSource(@Nonnull FluidState state) {
        return false;
    }

    public int getAmount(@Nonnull FluidState state) {
        return 0;
    }

    public static class Source extends ForgeFlowingFluid {
        public Source(ForgeFlowingFluid.Properties properties) {
            super(properties);
        }

        public int getAmount(FluidState state) {
            return 8;
        }

        public boolean isSource(FluidState state) {
            return true;
        }

        @OnlyIn(Dist.CLIENT)
        public void animateTick(Level worldIn, BlockPos pos, FluidState state, RandomSource random) {
            /*
            if (!state.isSource() && !(Boolean)state.getValue(FALLING)) {
                if (random.nextInt(64) == 0) {
                    worldIn.playSound((Player) null, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, SoundEvents.WATER_AMBIENT, SoundSource.BLOCKS, random.nextFloat() * 0.25F + 0.75F, random.nextFloat() + 0.5F);
                }
            } else if (random.nextInt(14) == 0) {
                if (random.nextInt(2) == 0) {
                    worldIn.addParticle((ParticleOptions) ModParticleTypes.BLOOD.get(), (double)pos.getX() + random.nextDouble(), (double)pos.getY() + random.nextDouble(), (double)pos.getZ() + random.nextDouble(), -0.01 + random.nextDouble() * 0.02, -0.01 + random.nextDouble() * 0.02, -0.01 + random.nextDouble() * 0.02);
                }

                worldIn.addParticle((ParticleOptions)ModParticleTypes.BLOOD_BIT.get(), (double)pos.getX() + random.nextDouble(), (double)pos.getY() + random.nextDouble(), (double)pos.getZ() + random.nextDouble(), -0.01 + random.nextDouble() * 0.02, -0.01 + random.nextDouble() * 0.02, -0.01 + random.nextDouble() * 0.02);
            }*/
        }
    }

    public static class Flowing extends ForgeFlowingFluid {
        public Flowing(ForgeFlowingFluid.Properties properties) {
            super(properties);
            this.registerDefaultState(this.getStateDefinition().any().setValue(LEVEL, 7));
        }

        protected void createFluidStateDefinition(StateDefinition.@NotNull Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        public int getAmount(FluidState state) {
            return state.getValue(LEVEL);
        }

        public boolean isSource(@NotNull FluidState state) {
            return false;
        }

        @OnlyIn(Dist.CLIENT)
        public void animateTick(@NotNull Level worldIn, @NotNull BlockPos pos, FluidState state, @NotNull RandomSource random) {
            /*
            if (!state.isSource() && !(Boolean)state.getValue(FALLING)) {
                if (random.nextInt(64) == 0) {
                    worldIn.playSound((Player)null, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, SoundEvents.WATER_AMBIENT, SoundSource.BLOCKS, random.nextFloat() * 0.25F + 0.75F, random.nextFloat() + 0.5F);
                }
            } else if (random.nextInt(12) == 0) {
                if (random.nextInt(3) == 0) {
                    worldIn.addParticle((ParticleOptions)ModParticleTypes.BLOOD.get(), (double)pos.getX() + random.nextDouble(), (double)pos.getY() + random.nextDouble() * (double)((float)(Integer)state.getValue(LEVEL) / 8.0F), (double)pos.getZ() + random.nextDouble(), -0.01 + random.nextDouble() * 0.02, -0.02 + random.nextDouble() * 0.02, -0.01 + random.nextDouble() * 0.02);
                }

                worldIn.addParticle((ParticleOptions)ModParticleTypes.BLOOD_BIT.get(), (double)pos.getX() + random.nextDouble(), (double)pos.getY() + random.nextDouble() * (double)((float)(Integer)state.getValue(LEVEL) / 8.0F), (double)pos.getZ() + random.nextDouble(), -0.01 + random.nextDouble() * 0.02, -0.01 + random.nextDouble() * 0.02, -0.01 + random.nextDouble() * 0.02);
            }
        */
        }
    }
}
