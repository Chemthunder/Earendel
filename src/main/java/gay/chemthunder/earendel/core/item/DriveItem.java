package gay.chemthunder.earendel.core.item;

import gay.chemthunder.earendel.api.cosmic.Signal;
import gay.chemthunder.earendel.core.index.EDataComponentTypes;
import gay.chemthunder.earendel.core.index.EItems;
import gay.chemthunder.earendel.core.networking.clientbound.OpenSignalPayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biomes;
import org.chemthunder.arboreal.api.item.Tooltip;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author Chemthunder
 */
public class DriveItem extends Item implements Tooltip {
    public DriveItem(Properties properties) {
        super(properties);
    }

    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (stack.has(EDataComponentTypes.SIGNAL)) {
            Signal signal = stack.get(EDataComponentTypes.SIGNAL);

            if (signal != null) {
                if (player instanceof ServerPlayer serverPlayer) {
                    ServerPlayNetworking.send(serverPlayer, new OpenSignalPayload(signal));
                }
            }
        }
        return super.use(level, player, hand);
    }

    public static ItemStack getDisplayedStack() {
        ItemStack stack = EItems.DRIVE.getDefaultInstance();
        stack.set(EDataComponentTypes.SIGNAL, new Signal(List.of(1, 1, 1, 1, 1), Biomes.BADLANDS, 0xFF34eb98));
        return stack;
    }

    public void applyTooltip(ItemStack itemStack, TooltipContext tooltipContext, TooltipDisplay tooltipDisplay, Consumer<Component> consumer, TooltipFlag tooltipFlag) {
        if (itemStack.has(EDataComponentTypes.SIGNAL)) {
            Signal signal = itemStack.get(EDataComponentTypes.SIGNAL);

            if (signal != null) {
                consumer.accept(Component.empty()
                        .append(Component.literal("[").withStyle(ChatFormatting.DARK_GRAY))
                        .append(Component.literal(signal.bits().toString()).withStyle(ChatFormatting.LIGHT_PURPLE))
                        .append(Component.literal("]").withStyle(ChatFormatting.DARK_GRAY))
                );
                consumer.accept(Component.empty()
                        .append(Component.literal("Found in ").withStyle(ChatFormatting.DARK_GRAY))
                        .append(Component.literal(signal.biomeKey().identifier().getPath()).withStyle(ChatFormatting.LIGHT_PURPLE))
                );
            }
        }
    }
}
