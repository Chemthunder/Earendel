package gay.chemthunder.earendel.core.event;

import gay.chemthunder.earendel.core.index.EItems;
import gay.chemthunder.earendel.core.networking.clientbound.TweakSignalPayload;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.SwingAnimation;
import net.minecraft.world.level.Level;

/**
 * @author Chemthunder
 */
public class TweakSignalEvent implements UseItemCallback {
    public InteractionResult interact(Player player, Level level, InteractionHand hand) {
        ItemStack main = player.getMainHandItem();
        ItemStack off = player.getOffhandItem();

        if (main.is(ItemTags.SWORDS) && off.is(EItems.DRIVE)) {
            player.swing(InteractionHand.MAIN_HAND, SwingAnimation.DEFAULT, true);
            if (player instanceof ServerPlayer serverPlayer) {
                ServerPlayNetworking.send(serverPlayer, new TweakSignalPayload(off));
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
