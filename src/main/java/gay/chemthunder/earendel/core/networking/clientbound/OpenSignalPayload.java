package gay.chemthunder.earendel.core.networking.clientbound;

import gay.chemthunder.earendel.core.Earendel;
import gay.chemthunder.earendel.core.client.screen.SignalScreen;
import gay.chemthunder.earendel.core.util.Signal;
import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

/**
 * @author Chemthunder
 */
public record OpenSignalPayload(Signal signal) implements CustomPacketPayload {
    public static final Type<OpenSignalPayload> TYPE = new Type<>(Earendel.id("open_signal"));

    public static final StreamCodec<RegistryFriendlyByteBuf, OpenSignalPayload> CODEC = StreamCodec.composite(
            Signal.STREAM_CODEC, OpenSignalPayload::signal,
            OpenSignalPayload::new
    );

    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<OpenSignalPayload> {
        public void receive(OpenSignalPayload payload, ClientPlayNetworking.Context context) {
            try {
                context.client().execute(() -> context.client().setScreenAndShow(new SignalScreen(payload.signal())));
            } catch (Exception e) {
                Earendel.LOGGER.error(e.getMessage());
            }
        }
    }
}
