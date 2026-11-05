package gay.chemthunder.earendel.core.event;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.core.BlockPos;
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

    }

    public static class Use implements UseBlockCallback {
        public InteractionResult interact(Player player, Level level, InteractionHand hand, BlockHitResult hitResult) {
            BlockPos pos = hitResult.getBlockPos();
            BlockState state = level.getBlockState(pos);
            ItemStack stack = player.getItemInHand(hand);


            return InteractionResult.PASS;
        }
    }
}
