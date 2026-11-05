package gay.chemthunder.earendel.core.event;

import gay.chemthunder.earendel.core.block.TransmitterBlock;
import gay.chemthunder.earendel.core.block.entity.TransmitterBlockEntity;
import gay.chemthunder.earendel.core.index.EAttachmentTypes;
import gay.chemthunder.earendel.core.index.ETriggerTypes;
import gay.chemthunder.earendel.core.index.EDataComponentTypes;
import gay.chemthunder.earendel.core.networking.clientbound.OpenSignalPayload;
import gay.chemthunder.earendel.core.signal.Signal;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.SwingAnimation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.chemthunder.arboreal.api.util.LevelUtil;

/**
 * @author Chemthunder
 */
public class TransmitterInteractionEvents {
    public static void init() {
        UseBlockCallback.EVENT.register(new Use());
    }

    public static class Use implements UseBlockCallback {
        public InteractionResult interact(Player player, Level level, InteractionHand hand, BlockHitResult hitResult) {
            BlockPos pos = hitResult.getBlockPos();
            BlockState state = level.getBlockState(pos);
            ItemStack stack = player.getItemInHand(hand);

            if (level.getBlockEntity(pos) instanceof TransmitterBlockEntity transmitter) {
                if (!transmitter.hasAttached(EAttachmentTypes.STORED_STACK)) {
                    if (stack.has(EDataComponentTypes.SIGNAL)) {
                        Signal signal = stack.get(EDataComponentTypes.SIGNAL);
                        if (signal != null) {
                            transmitter.setAttached(EAttachmentTypes.STORED_STACK, stack.split(1));
                            level.playSound(
                                    null,
                                    pos.getX(),
                                    pos.getY(),
                                    pos.getZ(),
                                    SoundEvents.IRON_BREAK,
                                    SoundSource.BLOCKS,
                                    1,
                                    0.1F
                            );
                            return InteractionResult.SUCCESS;
                        }
                    }
                } else {
                    if (stack.isEmpty()) {
                        if (player.isCrouching()) {
                            if (!state.getValue(TransmitterBlock.LOADED)) {
                                level.setBlockAndUpdate(pos, state.setValue(TransmitterBlock.LOADED, true));
                                player.swing(hand, SwingAnimation.DEFAULT, true);
                                level.playSound(
                                        null,
                                        pos.getX(),
                                        pos.getY(),
                                        pos.getZ(),
                                        SoundEvents.IRON_TRAPDOOR_CLOSE,
                                        SoundSource.BLOCKS,
                                        1,
                                        0.1F
                                );
                                return InteractionResult.SUCCESS;
                            } else {
                                transmitter.setFired(true);
                                player.swing(hand, SwingAnimation.DEFAULT, true);
                                level.playSound(
                                        null,
                                        pos.getX(),
                                        pos.getY(),
                                        pos.getZ(),
                                        SoundEvents.ALLAY_THROW,
                                        SoundSource.BLOCKS,
                                        1,
                                        0.4F
                                );
                                LevelUtil.grantCriterion(ETriggerTypes.BROADCAST_SIGNAL, player);
                                return InteractionResult.CONSUME;
                            }
                        } else {
                            if (player instanceof ServerPlayer serverPlayer) {
                                ItemStack heldStack = transmitter.getAttached(EAttachmentTypes.STORED_STACK);

                                if (heldStack != null) {
                                    if (heldStack.has(EDataComponentTypes.SIGNAL)) {
                                        Signal signal = heldStack.get(EDataComponentTypes.SIGNAL);

                                        if (signal != null) {
                                            ServerPlayNetworking.send(
                                                    serverPlayer,
                                                    new OpenSignalPayload(signal)
                                            );
                                            return InteractionResult.CONSUME;
                                        }
                                    }
                                }
                            }
                            return InteractionResult.SUCCESS;
                        }
                    }
                }
            }
            return InteractionResult.PASS;
        }
    }
}
