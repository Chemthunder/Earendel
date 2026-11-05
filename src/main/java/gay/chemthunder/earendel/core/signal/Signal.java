package gay.chemthunder.earendel.core.signal;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import gay.chemthunder.earendel.core.index.ERegistries;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * @author Chemthunder
 */
public record Signal(List<Integer> bits, boolean encrypted, int color) implements Iterable<Integer> {
    public static final Integer MAX_BITS = 9;

    public Signal(int[] freq, int color) {
        this(Arrays.stream(freq).boxed().toList(), false, color);
    }

    public static final Codec<Signal> CODEC = RecordCodecBuilder.create(codec -> codec.group(
            Codec.INT
                    .listOf(1, MAX_BITS)
                    .fieldOf("bits")
                    .forGetter(Signal::bits),
            Codec.BOOL
                    .fieldOf("encrypted")
                    .forGetter(Signal::encrypted),

            Codec.INT
                    .fieldOf("color")
                    .forGetter(Signal::color)
    ).apply(codec, Signal::new));

    public static final StreamCodec<ByteBuf, Signal> STREAM_CODEC = ByteBufCodecs.fromCodec(CODEC);

    public static Signal generate() {
        Random rand = new Random();
        return new Signal(
                new int[]{
                        rand.nextInt(-10, 10),
                        rand.nextInt(-10, 10),
                        rand.nextInt(-10, 10),
                        rand.nextInt(-10, 10),
                        rand.nextInt(-10, 10)
                },
                SignalColors.values.get(new Random().nextInt(SignalColors.values.size()))
        );
    }

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
