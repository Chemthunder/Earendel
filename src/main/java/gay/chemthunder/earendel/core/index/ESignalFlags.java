package gay.chemthunder.earendel.core.index;

import gay.chemthunder.earendel.core.Earendel;
import gay.chemthunder.earendel.core.util.SignalFlag;
import net.minecraft.core.Registry;

import java.util.Objects;

/**
 * @author Chemthunder
 */
public interface ESignalFlags {
    SignalFlag TWIN = register("twin", (signal -> {
        int lastBit = 0;

        for (int bit : signal) {
            if (lastBit == bit) {
                return true;
            }

            lastBit = bit;
        }
        return false;
    }));

    SignalFlag MIRROR_MIRROR = register("mirror_mirror", (signal ->
                    Objects.equals(signal.bits().getFirst(), signal.bits().getLast())
            )
    );

    SignalFlag BALANCE = register("balance", (signal -> {
        for (int bit : signal) {
            if (!(bit % 2 == 0)) {
                return false;
            }
        }
        return true;
    }));

    SignalFlag NULLED = register("nulled", (signal -> {
        for (int bit : signal) {
            if (bit > 0) {
                return false;
            }
        }
        return true;
    }));

    static void init() {}

    private static SignalFlag register(String name, SignalFlag flag) {
        return Registry.register(ERegistries.FLAG, Earendel.id(name), flag);
    }
}
