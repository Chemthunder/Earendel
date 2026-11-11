package gay.chemthunder.earendel.mixin;

import gay.chemthunder.earendel.core.util.EUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.TooltipDisplay;
import org.chemthunder.arboreal.api.item.Tooltip;
import org.spongepowered.asm.mixin.Mixin;

import java.util.function.Consumer;

/**
 * @author Chemthunder
 */
@Mixin(value = SpyglassItem.class)
public abstract class SpyglassItemMixin extends Item implements Tooltip {
    public SpyglassItemMixin(Properties properties) {
        super(properties);
    }

    public boolean overrideOtherStackedOnMe(ItemStack self, ItemStack other, Slot slot, ClickAction clickAction, Player player, SlotAccess carriedItem) {
        return EUtil.onClickSpyglass(self, other, slot, clickAction, player, carriedItem);
    }

    public void applyTooltip(ItemStack itemStack, TooltipContext tooltipContext, TooltipDisplay tooltipDisplay, Consumer<Component> consumer, TooltipFlag tooltipFlag) {
        EUtil.writeToSpyglassTooltip(itemStack, tooltipContext, tooltipDisplay, consumer, tooltipFlag);
    }
}
