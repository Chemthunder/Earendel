package gay.chemthunder.earendel.core.networking.serverbound;

import gay.chemthunder.earendel.core.Earendel;
import gay.chemthunder.earendel.core.index.ETriggerTypes;
import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.chemthunder.arboreal.api.util.LevelUtil;

/**
 * @author Chemthunder
 */
public record GrantLocateCasterPayload() implements CustomPacketPayload {
    public static final Type<GrantLocateCasterPayload> TYPE = new Type<>(Earendel.id("grant_locate_caster"));

    public static final StreamCodec<ByteBuf, GrantLocateCasterPayload> CODEC = StreamCodec.unit(new GrantLocateCasterPayload());

    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class Receiver implements ServerPlayNetworking.PlayPayloadHandler<GrantLocateCasterPayload> {
        public void receive(GrantLocateCasterPayload payload, ServerPlayNetworking.Context context) {
            LevelUtil.grantCriterion(ETriggerTypes.LOCATE_CASTER, context.player());
        }
    }
}
