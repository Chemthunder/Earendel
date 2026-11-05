package gay.chemthunder.earendel.core.signal;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

/**
 * @author Chemthunder
 */
public record SignalModifier(String name, int color) {
    public static final Codec<SignalModifier> CODEC = RecordCodecBuilder.create(codec -> codec.group(
            Codec.STRING
                    .fieldOf("name")
                    .forGetter(SignalModifier::name),
            Codec.INT
                    .fieldOf("color")
                    .forGetter(SignalModifier::color)
    ).apply(codec, SignalModifier::new));

    public static final StreamCodec<ByteBuf, SignalModifier> STREAM_CODEC = ByteBufCodecs.fromCodec(CODEC);
}
