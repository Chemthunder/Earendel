package gay.chemthunder.earendel.core.client.item;

import com.mojang.serialization.MapCodec;
import gay.chemthunder.earendel.api.cosmic.Signal;
import gay.chemthunder.earendel.core.index.EDataComponentTypes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

/**
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
public record DriveTintSource() implements ItemTintSource {
    public static final MapCodec<DriveTintSource> MAP_CODEC = MapCodec.unit(new DriveTintSource());

    public int calculate(ItemStack itemStack, @Nullable ClientLevel level, @Nullable LivingEntity owner) {
        if (itemStack.has(EDataComponentTypes.SIGNAL)) {
            Signal signal = itemStack.get(EDataComponentTypes.SIGNAL);

            if (signal != null) {
                return signal.color();
            }
        }
        return 0xFF000000;
    }

    public MapCodec<? extends ItemTintSource> type() {
        return MAP_CODEC;
    }
}
