package gay.chemthunder.earendel.api.cosmic;

import gay.chemthunder.earendel.core.block.entity.TransmitterBlockEntity;
import gay.chemthunder.earendel.core.index.ERegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chemthunder
 */
@SuppressWarnings("unused")
public interface Monad {
    default List<ItemStack> getDroppableItems(Level level, @Nullable LivingEntity target) {
        return new ArrayList<>();
    }

    default void createBlessing(Level level, @Nullable LivingEntity target) {}

    default Identifier getId() {
        return ERegistries.MONAD.getKey(this);
    }

    boolean canBeCalledUpon(Level level, @Nullable LivingEntity living, TransmitterBlockEntity transmitter);
}
