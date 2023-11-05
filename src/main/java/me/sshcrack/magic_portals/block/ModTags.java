package me.sshcrack.magic_portals.block;

import me.sshcrack.magic_portals.MagicPortalsMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTags {
    public static final TagKey<Block> SPIRIT_HEATED = ForgeRegistries.BLOCKS.tags().createTagKey(new ResourceLocation(MagicPortalsMod.MODID, "cauldron/spirit_heated"));

}
