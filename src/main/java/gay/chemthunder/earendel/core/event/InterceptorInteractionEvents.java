package gay.chemthunder.earendel.core.event;

import gay.chemthunder.earendel.core.block.entity.InterceptorBlockEntity;
import gay.chemthunder.earendel.core.index.EAttachmentTypes;
import gay.chemthunder.earendel.core.index.EDataComponentTypes;
import gay.chemthunder.earendel.core.index.EItems;
import gay.chemthunder.earendel.core.networking.clientbound.OpenInterceptorPayload;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

/**
 * @author Chemthunder
 */
public class InterceptorInteractionEvents {
    public static void init() {
        UseBlockCallback.EVENT.register(new Use());
    }

    public static class Use implements UseBlockCallback {
        public InteractionResult interact(Player player, Level level, InteractionHand hand, BlockHitResult hitResult) {
            BlockPos pos = hitResult.getBlockPos();
            ItemStack stack = player.getItemInHand(hand);

            if (level.getBlockEntity(pos) instanceof InterceptorBlockEntity interceptor) {
                if (!interceptor.hasAttached(EAttachmentTypes.STORED_STACK)) {
                    if (stack.is(EItems.DRIVE)) {
                        if (!stack.has(EDataComponentTypes.SIGNAL)) {
                            interceptor.setAttached(EAttachmentTypes.STORED_STACK, stack.split(1));
                            return InteractionResult.SUCCESS;
                        }
                    }
                } else {
                    if (player instanceof ServerPlayer serverPlayer) {
                        ServerPlayNetworking.send(serverPlayer, new OpenInterceptorPayload(
                                interceptor.getAttached(EAttachmentTypes.STORED_STACK),
                                GlobalPos.of(level.dimension(), pos)
                        ));
                        return InteractionResult.SUCCESS;
                    }
                }
            }
            return InteractionResult.PASS;
        }
    }
}
