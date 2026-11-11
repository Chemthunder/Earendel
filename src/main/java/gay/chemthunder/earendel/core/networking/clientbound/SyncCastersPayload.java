package gay.chemthunder.earendel.core.networking.clientbound;

import gay.chemthunder.earendel.api.cosmic.Signal;
import gay.chemthunder.earendel.core.Earendel;
import gay.chemthunder.earendel.core.index.EAttachmentTypes;
import gay.chemthunder.earendel.core.util.ESerialization;
import gay.chemthunder.earendel.core.util.SignalPos;
import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.level.Level;

import java.util.List;

/**
 * @author Chemthunder
 */
public record SyncCastersPayload(List<SignalPos> uploadedSignalPositions) implements CustomPacketPayload {
    public static final Type<SyncCastersPayload> TYPE = new Type<>(Earendel.id("sync_casters"));

    public static final StreamCodec<ByteBuf, SyncCastersPayload> CODEC = StreamCodec.composite(
            ESerialization.SIGNAL_POS_LIST_STREAM_CODEC, SyncCastersPayload::uploadedSignalPositions,
            SyncCastersPayload::new
    );

    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<SyncCastersPayload> {
        public void receive(SyncCastersPayload payload, ClientPlayNetworking.Context context) {
            Level level = context.client().level;

            if (level != null) {
                List<SignalPos> uploaded = payload.uploadedSignalPositions();
                level.setAttached(EAttachmentTypes.CASTERS, uploaded);

                if (Earendel.LOADER.isDevelopmentEnvironment()) {
                    Earendel.LOGGER.info("[CLIENT] origins {}", level.getAttached(EAttachmentTypes.CASTERS));
                }
            }
        }
    }
}
