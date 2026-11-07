package gay.chemthunder.earendel.core.util;

import java.util.List;
import java.util.Random;

/**
 * @author Chemthunder
 */
public class SignalColors {
    public static final List<Integer> values = List.of(
            0xFFffffff,
            0xFFff0000,
            0xFF03fc5e,
            0xFF6f03fc,
            0xFFff0084,
            0xFF00ffcc,
            0xFF0044ff
    );

    public static int getRandom() {
        return values.get(new Random().nextInt(0, values.size()));
    }
}
