package gay.chemthunder.earendel.core.networking.clientbound;

import gay.chemthunder.earendel.core.Earendel;
import gay.chemthunder.earendel.core.client.screen.InterceptorScreen;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.ItemStack;

/**
 * @author Chemthunder
 */
public record OpenInterceptorPayload(ItemStack stack, GlobalPos pos) implements CustomPacketPayload {
    public static final Type<OpenInterceptorPayload> TYPE = new Type<>(Earendel.id("open_interceptor"));

    public static final StreamCodec<RegistryFriendlyByteBuf, OpenInterceptorPayload> CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC, OpenInterceptorPayload::stack,
            GlobalPos.STREAM_CODEC, OpenInterceptorPayload::pos,
            OpenInterceptorPayload::new
    );

    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<OpenInterceptorPayload> {
        public void receive(OpenInterceptorPayload payload, ClientPlayNetworking.Context context) {
            try {
                context.client().setScreenAndShow(new InterceptorScreen(payload.stack, payload.pos));
            } catch (Exception e) {
                Earendel.LOGGER.error(e.getMessage());
            }
        }
    }
}
