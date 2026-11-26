package gay.chemthunder.earendel.core.event;

import gay.chemthunder.earendel.api.cosmic.Signal;
import gay.chemthunder.earendel.core.block.CasterBlock;
import gay.chemthunder.earendel.core.block.entity.CasterBlockEntity;
import gay.chemthunder.earendel.core.index.EBlocks;
import gay.chemthunder.earendel.core.index.EDataComponentTypes;
import gay.chemthunder.earendel.core.index.EItems;
import gay.chemthunder.earendel.core.util.EUtil;
import gay.chemthunder.earendel.api.cosmic.SignalOrigin;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

/**
 * @author Chemthunder
 */
public class CasterInteractionEvents {
    public static void init() {
        UseBlockCallback.EVENT.register(new Use());
        PlayerBlockBreakEvents.AFTER.register(new Break());
    }

    public static class Use implements UseBlockCallback {
        public InteractionResult interact(Player player, Level level, InteractionHand hand, BlockHitResult hitResult) {
            BlockPos pos = hitResult.getBlockPos();
            ItemStack stack = player.getItemInHand(hand);

            if (level.getBlockEntity(pos) instanceof CasterBlockEntity) {
                if (stack.is(EItems.DRIVE)) {
                    if (stack.has(EDataComponentTypes.SIGNAL)) {
                        Signal signal = stack.get(EDataComponentTypes.SIGNAL);

                        if (signal != null) {
                            if (!EUtil.exists(level, pos)) {
                                EUtil.pushPoint(level, pos, signal);
                                level.updateNeighborsAt(pos, EBlocks.CASTER);
                                return InteractionResult.SUCCESS;
                            } else {
                                player.sendOverlayMessage(Component.literal("You are unable to link a Caster at this position!"));
                                return InteractionResult.FAIL;
                            }
                        }
                    }
                }

                if (stack.is(Items.NAME_TAG)) {
                    if (stack.has(DataComponents.CUSTOM_NAME)) {
                        Component component = stack.get(DataComponents.CUSTOM_NAME);

                        if (component != null) {
                            SignalOrigin origin = EUtil.getSaved(level, GlobalPos.of(level.dimension(), pos));

                            if (origin != null) {
                                SignalOrigin named = new SignalOrigin(origin.pos(), origin.signal(), Optional.of(component.getString()));

                                EUtil.popPoint(level, origin);
                                EUtil.pushPoint(level, named);

                                player.sendOverlayMessage(Component.literal("Renamed Caster to " + component.getString()));
                                return InteractionResult.SUCCESS;
                            }
                        }
                    }
                }
            }
            return InteractionResult.PASS;
        }
    }

    public static class Break implements PlayerBlockBreakEvents.After {
        public void afterBlockBreak(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity) {
            if (state.getBlock() instanceof CasterBlock) {
                EUtil.popPoint(level, pos);
                EUtil.syncPoints(level);
            }
        }
    }
}

