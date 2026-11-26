package gay.chemthunder.earendel.api.cosmic;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

import java.util.Optional;

/**
 * @author Chemthunder
 */
@SuppressWarnings("unused")
public record SignalOrigin(GlobalPos pos, Signal signal, Optional<String> name) {
    public static final Codec<SignalOrigin> CODEC = RecordCodecBuilder.create(codec -> codec.group(
            GlobalPos.CODEC
                    .fieldOf("pos")
                    .forGetter(SignalOrigin::pos),
            Signal.CODEC
                    .fieldOf("signal")
                    .forGetter(SignalOrigin::signal),
            Codec.STRING
                    .optionalFieldOf("name")
                    .forGetter(SignalOrigin::name)
    ).apply(codec, SignalOrigin::new));

    public static final StreamCodec<ByteBuf, SignalOrigin> STREAM_CODEC = ByteBufCodecs.fromCodec(CODEC);

    public static SignalOrigin of(ResourceKey<Level> levelKey, BlockPos pos, Signal signal, Optional<String> name) {
        return new SignalOrigin(GlobalPos.of(levelKey, pos), signal, name);
    }

    public static SignalOrigin of(ResourceKey<Level> levelKey, BlockPos pos, Signal signal) {
        return new SignalOrigin(GlobalPos.of(levelKey, pos), signal, Optional.empty());
    }
}
