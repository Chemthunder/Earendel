package gay.chemthunder.earendel.api.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.chemthunder.arboreal.api.event.ArborealEvent;
import org.chemthunder.arboreal.api.event.EventUtil;

/**
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
public interface ExtractItemEvent extends ArborealEvent {
    Event<ExtractItemEvent> EVENT = EventFactory.createArrayBacked(ExtractItemEvent.class,
            (events) -> (
                    gg,
                    owner,
                    level,
                    itemStack,
                    x,
                    y,
                    seed
            ) -> {
        for (ExtractItemEvent event : EventUtil.sortAndCollectEvents(events, ExtractItemEvent::getPriority)) {
            event.draw(gg, owner, level, itemStack, x, y, seed);
        }
    });

    void draw(GuiGraphicsExtractor gg, LivingEntity owner, Level level, ItemStack itemStack, int x, int y, int seed);
}
