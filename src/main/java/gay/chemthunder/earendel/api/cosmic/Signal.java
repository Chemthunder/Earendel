package gay.chemthunder.earendel.api.cosmic;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import gay.chemthunder.earendel.core.index.ERegistries;
import gay.chemthunder.earendel.core.util.SignalColors;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * @author Chemthunder
 */
public record Signal(List<Integer> bits, ResourceKey<Biome> biomeKey, int color, boolean tweaked) implements Iterable<Integer> {
    public static final Integer MAX_BITS = 9;

    public Signal(List<Integer> bits, ResourceKey<Biome> biomeKey) {
        this(bits, biomeKey, SignalColors.getRandom(), false);
    }

    public Signal(ResourceKey<Biome> biomeKey) {
        List<Integer> bitList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            bitList.add(new Random().nextInt(-10, 10));
        }
        this(bitList, biomeKey, SignalColors.getRandom(), false);
    }

    public static final Codec<Signal> CODEC = RecordCodecBuilder.create(codec -> codec.group(
            Codec.INT
                    .listOf(1, MAX_BITS)
                    .fieldOf("bits")
                    .forGetter(Signal::bits),
            ResourceKey.codec(Registries.BIOME)
                    .fieldOf("biome")
                    .forGetter(Signal::biomeKey),
            Codec.INT
                    .fieldOf("color")
                    .forGetter(Signal::color),
            Codec.BOOL
                    .fieldOf("tweaked")
                    .forGetter(Signal::tweaked)
    ).apply(codec, Signal::new));

    public static final StreamCodec<ByteBuf, Signal> STREAM_CODEC = ByteBufCodecs.fromCodec(CODEC);

    public List<SignalFlag> getAcceptingFlags() {
        List<SignalFlag> list = new ArrayList<>();
        for (SignalFlag flag : ERegistries.FLAG) {
            if (flag.test(this)) {
                list.add(flag);
            }
        }
        return list;
    }

    @NotNull
    public Iterator<Integer> iterator() {
        return this.bits().iterator();
    }
}
