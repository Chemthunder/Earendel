package gay.chemthunder.earendel.core.util;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.Holder;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.biome.Biome;

/**
 * @author Chemthunder
 */
public class ESerialization {
    public static final StreamCodec<ByteBuf, Holder<Biome>> BIOME_HOLDER_STREAM_CODEC = ByteBufCodecs.fromCodec(Biome.CODEC);
}
