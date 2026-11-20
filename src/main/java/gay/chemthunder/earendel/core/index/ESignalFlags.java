package gay.chemthunder.earendel.core.index;

import gay.chemthunder.earendel.api.cosmic.SignalFlag;
import gay.chemthunder.earendel.core.Earendel;
import org.chemthunder.arboreal.api.core.task.RegisterTask;

import java.util.Objects;

/**
 * @author Chemthunder
 */
@SuppressWarnings("unused")
public interface ESignalFlags {
    RegisterTask<SignalFlag> task = Earendel.MAIN.createTask(RegisterTask::new, ERegistries.FLAG);

    SignalFlag TWIN = task.register("twin", (signal -> {
        int lastBit = 999;

        for (int bit : signal) {
            if (lastBit == bit) {
                return true;
            }

            lastBit = bit;
        }
        return false;
    }));

    SignalFlag MIRROR_MIRROR = task.register("mirror_mirror", (signal ->
                    Objects.equals(signal.bits().getFirst(), signal.bits().getLast())
            )
    );

    SignalFlag BALANCE = task.register("balance", (signal -> {
        for (int bit : signal) {
            if (!(bit % 2 == 0)) {
                return false;
            }
        }
        return true;
    }));

    SignalFlag NULLED = task.register("nulled", (signal -> {
        for (int bit : signal) {
            if (bit > 0) {
                return false;
            }
        }
        return true;
    }));
}
