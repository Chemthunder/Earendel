package gay.chemthunder.earendel.core.event;

import gay.chemthunder.earendel.core.block.CasterBlock;
import gay.chemthunder.earendel.core.block.entity.CasterBlockEntity;
import gay.chemthunder.earendel.core.index.EAttachmentTypes;
import gay.chemthunder.earendel.core.index.EBlocks;
import gay.chemthunder.earendel.core.index.EDataComponentTypes;
import gay.chemthunder.earendel.core.index.EItems;
import gay.chemthunder.earendel.core.util.EUtil;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;

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

            if (level.getBlockEntity(pos) instanceof CasterBlockEntity caster) {
                if (!caster.hasAttached(EAttachmentTypes.STORED_STACK)) {
                    if (stack.is(EItems.DRIVE)) {
                        if (!stack.has(EDataComponentTypes.SIGNAL)) {
                            caster.setAttached(EAttachmentTypes.STORED_STACK, stack.split(1));
                            level.updateNeighborsAt(pos, EBlocks.CASTER);
                            return InteractionResult.SUCCESS;
                        }
                    }
                } else {
                    ItemStack i = caster.getAttached(EAttachmentTypes.STORED_STACK);

                    if (i != null) {
                        player.addItem(i);
                        caster.removeAttached(EAttachmentTypes.STORED_STACK);
                        level.updateNeighborsAt(pos, EBlocks.CASTER);
                        return InteractionResult.SUCCESS;
                    }
                }

                if (player.getActiveItem().isEmpty()) {
                    EUtil.pushPoint(level, pos);
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

