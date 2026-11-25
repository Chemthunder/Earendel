package gay.chemthunder.earendel.core.networking.serverbound;

import com.mojang.serialization.Codec;
import gay.chemthunder.earendel.api.cosmic.Signal;
import gay.chemthunder.earendel.core.Earendel;
import gay.chemthunder.earendel.core.index.EDataComponentTypes;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.ItemStack;

import java.util.List;

/**
 * @author Chemthunder
 */
public record ApplySignalTweakPayload(ItemStack stack, List<Integer> newBits) implements CustomPacketPayload {
    public static final Type<ApplySignalTweakPayload> TYPE = new Type<>(Earendel.id("apply_signal_tweak"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ApplySignalTweakPayload> CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC, ApplySignalTweakPayload::stack,
            ByteBufCodecs.fromCodec(Codec.INT.listOf()), ApplySignalTweakPayload::newBits,
            ApplySignalTweakPayload::new
    );

    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class Receiver implements ServerPlayNetworking.PlayPayloadHandler<ApplySignalTweakPayload> {
        public void receive(ApplySignalTweakPayload payload, ServerPlayNetworking.Context context) {
            Signal signal = payload.stack.get(EDataComponentTypes.SIGNAL);

            if (signal != null) {
                context.player().getOffhandItem().set(EDataComponentTypes.SIGNAL, new Signal(
                        payload.newBits,
                        signal.biomeKey(),
                        signal.color(),
                        true
                ));
            }
        }
    }
}
