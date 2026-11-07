package gay.chemthunder.earendel.mixin.client;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import gay.chemthunder.earendel.core.EarendelClient;
import gay.chemthunder.earendel.core.index.EItems;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.util.EasingType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.joml.Matrix3x2fStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/**
 * @author Chemthunder
 */
@Mixin(value = GuiGraphicsExtractor.class)
public abstract class GuiGraphicsExtractorMixin {
    @Shadow public abstract void blitSprite(RenderPipeline renderPipeline, Identifier location, int x, int y, int width, int height, int color);

    @Shadow public abstract Matrix3x2fStack pose();

    @WrapMethod(method = "item(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;III)V")
    private void earendel$drawSineWaveBehindItem(LivingEntity owner, Level level, ItemStack itemStack, int x, int y, int seed, Operation<Void> original) {
        if (itemStack.is(EItems.CRYING_RESOLVE)) {
            Matrix3x2fStack matrix = this.pose();

            matrix.pushMatrix();

            matrix.translate(x, y);

            matrix.scaleAround(
                    1.5F + (EasingType.IN_OUT_SINE.apply(EarendelClient.lerpingWave) * 1.5F),
                    8,
                    8
            );

            this.blitSprite(
                    RenderPipelines.GUI_TEXTURED,
                    Identifier.fromNamespaceAndPath(
                            itemStack.getCreatorNamespace(),
                            BuiltInRegistries.ITEM.getKey(itemStack.getItem()).getPath() + "_underlay"
                    ),
                    0, 0,
                    16,
                    16,
                    0xFF8032e6
            );

            matrix.popMatrix();

            original.call(owner, level, itemStack, x, y, seed);
            return;
        }
        original.call(owner, level, itemStack, x, y, seed);
    }
}
