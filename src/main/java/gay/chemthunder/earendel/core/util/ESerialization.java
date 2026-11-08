package gay.chemthunder.earendel.core.util;

import com.mojang.serialization.Codec;
import gay.chemthunder.earendel.api.cosmic.Monad;
import gay.chemthunder.earendel.core.index.ERegistries;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.biome.Biome;

/**
 * @author Chemthunder
 */
@SuppressWarnings({"SameParameterValue", "unused"})
public interface ESerialization {
    StreamCodec<ByteBuf, Holder<Biome>> BIOME_HOLDER_STREAM_CODEC = stream(Biome.CODEC);
    StreamCodec<ByteBuf, Monad> MONAD_REGISTRY_STREAM_CODEC = stream(ERegistries.MONAD);

    private static <T> StreamCodec<ByteBuf, T> stream(Codec<T> codec) {
        return ByteBufCodecs.fromCodec(codec);
    }

    private static <T> StreamCodec<ByteBuf, T> stream(Registry<T> registry) {
        return ByteBufCodecs.fromCodec(registry.byNameCodec());
    }
}
