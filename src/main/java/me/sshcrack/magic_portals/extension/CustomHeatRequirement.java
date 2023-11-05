package me.sshcrack.magic_portals.extension;

import me.sshcrack.magic_portals.block.ModTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum CustomHeatRequirement implements StringRepresentable {
    NONE, SPIRITS;

    @Override
    public @NotNull String getSerializedName() {
        return switch (this) {
            case NONE -> "none";
            case SPIRITS -> "spirits";
        };
    }

    public static CustomHeatRequirement fromString(String string) {
        return switch (string) {
            case "none" -> NONE;
            case "spirits" -> SPIRITS;
            default -> NONE;
        };
    }

    public @Nullable TagKey<Block> getTag() {
        return switch (this) {
            case NONE -> null;
            case SPIRITS -> ModTags.SPIRIT_HEATED;
        };
    }
}
