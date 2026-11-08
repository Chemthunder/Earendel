package gay.chemthunder.earendel.core.client.event;

import gay.chemthunder.earendel.api.event.ExtractItemEvent;
import gay.chemthunder.earendel.core.EarendelClient;
import gay.chemthunder.earendel.core.index.EItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.contextualbar.LocatorBar;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.EasingType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.joml.Matrix3x2fStack;

/**
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
public class DrawArtifactBackgroundEvent implements ExtractItemEvent {
    public void draw(GuiGraphicsExtractor gg, LivingEntity owner, Level level, ItemStack itemStack, int x, int y, int seed) {
        if (itemStack.is(EItems.CRYING_RESOLVE)) {
            Matrix3x2fStack matrix = gg.pose();

            matrix.pushMatrix();

            matrix.translate(x, y);

            matrix.scaleAround(
                    1.25F + (EasingType.IN_OUT_SINE.apply(EarendelClient.lerpingWave) * 0.4F),
                    8,
                    8
            );

            gg.blitSprite(
                    RenderPipelines.GUI_TEXTURED,
                    Identifier.fromNamespaceAndPath(
                            itemStack.getCreatorNamespace(),
                            BuiltInRegistries.ITEM.getKey(itemStack.getItem()).getPath() + "_underlay"
                    ),
                    0, 0,
                    16,
                    16,
                    ARGB.color(0.75F, 0xFF8032e6)
            );

            matrix.popMatrix();
        }
    }
}
