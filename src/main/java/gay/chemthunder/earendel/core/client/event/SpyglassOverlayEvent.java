package gay.chemthunder.earendel.core.client.event;

import gay.chemthunder.earendel.api.event.ExtractItemEvent;
import gay.chemthunder.earendel.core.Earendel;
import gay.chemthunder.earendel.core.index.EDataComponentTypes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

/**
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
public class SpyglassOverlayEvent implements ExtractItemEvent.Post {
    public void draw(GuiGraphicsExtractor gg, LivingEntity owner, Level level, ItemStack itemStack, int x, int y, int seed) {
        if (itemStack.is(Items.SPYGLASS)) {
            if (itemStack.has(EDataComponentTypes.HELD_STACK)) {
                gg.blitSprite(
                        RenderPipelines.GUI_TEXTURED,
                        Earendel.id("spyglass_lensed_overlay"),
                        x,
                        y,
                        16,
                        16
                );
            }
        }
    }
}
