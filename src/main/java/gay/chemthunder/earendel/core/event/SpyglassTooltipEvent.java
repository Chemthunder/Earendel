package gay.chemthunder.earendel.core.event;

import gay.chemthunder.earendel.core.index.EDataComponentTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.chemthunder.arboreal.api.event.server.ItemCallbacks;

import java.util.function.Consumer;

/**
 * @author Chemthunder
 */
public class SpyglassTooltipEvent implements ItemCallbacks.InjectTooltip {
    public void applyTooltip(ItemStack itemStack, Item.TooltipContext tooltipContext, TooltipDisplay tooltipDisplay, Consumer<Component> consumer, TooltipFlag tooltipFlag) {
        if (itemStack.is(Items.SPYGLASS)) {

        }
    }
}
