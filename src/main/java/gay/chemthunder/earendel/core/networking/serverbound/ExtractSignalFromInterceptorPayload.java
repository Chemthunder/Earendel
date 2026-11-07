package gay.chemthunder.earendel.core.networking.serverbound;

import gay.chemthunder.earendel.core.Earendel;
import gay.chemthunder.earendel.core.block.entity.InterceptorBlockEntity;
import gay.chemthunder.earendel.core.index.EAttachmentTypes;
import gay.chemthunder.earendel.core.index.ETriggerTypes;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.chemthunder.arboreal.api.util.LevelUtil;

/**
 * @author Chemthunder
 */
public record ExtractSignalFromInterceptorPayload(ItemStack stack, GlobalPos pos) implements CustomPacketPayload {
    public static final Type<ExtractSignalFromInterceptorPayload> TYPE = new Type<>(Earendel.id("extract_signal_from_interceptor"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ExtractSignalFromInterceptorPayload> CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC, ExtractSignalFromInterceptorPayload::stack,
            GlobalPos.STREAM_CODEC, ExtractSignalFromInterceptorPayload::pos,
            ExtractSignalFromInterceptorPayload::new
    );

    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class Receiver implements ServerPlayNetworking.PlayPayloadHandler<ExtractSignalFromInterceptorPayload> {
        public void receive(ExtractSignalFromInterceptorPayload payload, ServerPlayNetworking.Context context) {
            ServerPlayer serverPlayer = context.player();
            serverPlayer.addItem(payload.stack);

            Level level = context.server().getLevel(serverPlayer.level().dimension());

            if (level != null) {
                if (level.getBlockEntity(payload.pos.pos()) instanceof InterceptorBlockEntity interceptor) {
                    interceptor.removeAttached(EAttachmentTypes.STORED_STACK);
                }
            }

            LevelUtil.grantCriterion(ETriggerTypes.ETCH_SIGNAL, serverPlayer);
        }
    }
}
