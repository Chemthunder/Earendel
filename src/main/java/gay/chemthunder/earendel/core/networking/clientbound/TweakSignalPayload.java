package gay.chemthunder.earendel.core.networking.clientbound;

import gay.chemthunder.earendel.core.Earendel;
import gay.chemthunder.earendel.core.client.screen.TweakerScreen;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;

/**
 * @author Chemthunder
 */
public record TweakSignalPayload(ItemStack stack) implements CustomPacketPayload {
    public static final Type<TweakSignalPayload> TYPE = new Type<>(Earendel.id("tweak_signal"));

    public static final StreamCodec<RegistryFriendlyByteBuf, TweakSignalPayload> CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC, TweakSignalPayload::stack,
            TweakSignalPayload::new
    );

    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<TweakSignalPayload> {
        public void receive(TweakSignalPayload payload, ClientPlayNetworking.Context context) {
            context.client().setScreenAndShow(new TweakerScreen(payload.stack));

            context.client().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.ITEM_BREAK.value(), 0.3F));
            context.client().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.COPPER_GOLEM_SPIN, 0.6F));
            context.client().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.COPPER_GOLEM_SPAWN, 0.6F));
        }
    }
}
