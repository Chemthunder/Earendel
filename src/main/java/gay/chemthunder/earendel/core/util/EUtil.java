package gay.chemthunder.earendel.core.util;

import gay.chemthunder.earendel.api.cosmic.Signal;
import gay.chemthunder.earendel.core.Earendel;
import gay.chemthunder.earendel.core.index.EAttachmentTypes;
import gay.chemthunder.earendel.core.index.EDataComponentTypes;
import gay.chemthunder.earendel.core.networking.clientbound.SyncCastersPayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author Chemthunder
 */
@SuppressWarnings("unused")
public class EUtil {
    public static List<SignalPos> cache = new ArrayList<>();

    public static List<SignalPos> getPoints(Level level) {
        return new ArrayList<>(level.getAttachedOrCreate(EAttachmentTypes.CASTERS));
    }

    @Nullable
    public static SignalPos getSaved(Level level, GlobalPos pos) {
        List<SignalPos> origins = getPoints(level);
        for (SignalPos origin : origins) {
            if (origin.pos().equals(pos)) {
                return origin;
            }
        }
        return null;
    }

    @Nullable
    public static SignalPos getSaved(Level level, Signal signal) {
        List<SignalPos> origins = getPoints(level);
        for (SignalPos origin : origins) {
            if (origin.signal().equals(signal)) {
                return origin;
            }
        }
        return null;
    }

    public static void syncPoints(Level level) {
        if (!level.isClientSide()) {
            for (Player player : level.players()) {
                if (player instanceof ServerPlayer serverPlayer) {
                    ServerPlayNetworking.send(serverPlayer, new SyncCastersPayload(cache));
                }
            }
        }
        cache.clear();
    }

    public static void pushPoint(Level level, SignalPos origin) {
        List<SignalPos> origins = getPoints(level);
        origins.add(origin);
        level.setAttached(EAttachmentTypes.CASTERS, origins);

        cache = origins;
    }

    public static void pushPoint(Level level, ResourceKey<Level> levelKey, BlockPos pos, Signal signal) {
        pushPoint(level, SignalPos.of(levelKey, pos, signal));
    }

    public static void pushPoint(Level level, ResourceKey<Level> levelKey, BlockPos pos) {
        Optional<ResourceKey<Biome>> biomeResourceKey = level.getBiome(pos).unwrapKey();
        biomeResourceKey.ifPresent(resourceKey ->
                pushPoint(level, levelKey, pos, new Signal(resourceKey))
        );
    }

    public static void pushPoint(Level level, BlockPos pos) {
        pushPoint(level, level.dimension(), pos);
    }

    public static void popPoint(Level level, SignalPos origin) {
        List<SignalPos> origins = getPoints(level);
        origins.remove(origin);
        level.setAttached(EAttachmentTypes.CASTERS, origins);

        cache = origins;
    }

    public static void popPoint(Level level, ResourceKey<Level> levelKey, BlockPos pos, Signal signal) {
        popPoint(level, SignalPos.of(levelKey, pos, signal));
    }

    public static void popPoint(Level level, ResourceKey<Level> levelKey, BlockPos pos) {
        SignalPos saved = getSaved(level, GlobalPos.of(levelKey, pos));

        if (saved != null) {
            popPoint(level, saved);
            return;
        }

        Earendel.LOGGER.info("Cached origin is null! {} {}", levelKey.identifier(), pos.toShortString());
    }

    public static void popPoint(Level level, BlockPos pos) {
        popPoint(level, level.dimension(), pos);
    }

    public static boolean onClickSpyglass(ItemStack self, ItemStack other, Slot slot, ClickAction clickAction, Player player, SlotAccess carriedItem) {
        if (clickAction == ClickAction.SECONDARY) {
            if (self.has(EDataComponentTypes.HELD_STACK)) {
                ItemStack held = self.get(EDataComponentTypes.HELD_STACK);

                if (held != null) {
                    if (other.isEmpty()) {
                        carriedItem.set(held);
                        if (player.level().isClientSide()) {
                            player.playSound(SoundEvents.GLASS_STEP);
                        }
                        self.remove(EDataComponentTypes.HELD_STACK);
                        return true;
                    }
                }
            } else {
                if (other.is(Items.AMETHYST_SHARD)) {
                    self.set(EDataComponentTypes.HELD_STACK, other.split(1));
                    if (player.level().isClientSide()) {
                        player.playSound(SoundEvents.GLASS_PLACE);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public static void writeToSpyglassTooltip(ItemStack itemStack, Item.TooltipContext tooltipContext, TooltipDisplay tooltipDisplay, Consumer<Component> consumer, TooltipFlag tooltipFlag) {
        if (itemStack.has(EDataComponentTypes.HELD_STACK)) {
            consumer.accept(Component.empty()
                    .append(Component.literal("Lensed").withStyle(ChatFormatting.DARK_GRAY))
            );

            consumer.accept(Component.empty()
                    .append(Component.literal("Can be used to track signals broadcasted from Casters.").withStyle(ChatFormatting.DARK_GRAY))
            );
        }
    }

    public static boolean usingLensedSpyglass(Player player) {
        if (player.getActiveItem().is(Items.SPYGLASS) && player.isUsingItem()) {
            ItemStack stack = player.getActiveItem();
            return stack.has(EDataComponentTypes.HELD_STACK);
        }
        return false;
    }
}
