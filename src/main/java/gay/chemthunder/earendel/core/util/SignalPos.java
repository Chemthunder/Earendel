package gay.chemthunder.earendel.core.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import gay.chemthunder.earendel.api.cosmic.Signal;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

/**
 * @author Chemthunder
 */
public record SignalPos(GlobalPos pos, Signal signal) {
    public static final Codec<SignalPos> CODEC = RecordCodecBuilder.create(codec -> codec.group(
            GlobalPos.CODEC
                    .fieldOf("pos")
                    .forGetter(SignalPos::pos),
            Signal.CODEC
                    .fieldOf("signal")
                    .forGetter(SignalPos::signal)
    ).apply(codec, SignalPos::new));

    public static final StreamCodec<ByteBuf, SignalPos> STREAM_CODEC = ByteBufCodecs.fromCodec(CODEC);

    public static SignalPos of(ResourceKey<Level> levelKey, BlockPos pos, Signal signal) {
        return new SignalPos(GlobalPos.of(levelKey, pos), signal);
    }
}
