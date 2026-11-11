package gay.chemthunder.earendel.mixin.client;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import gay.chemthunder.earendel.api.event.ExtractItemEvent;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

/**
 * @author Chemthunder
 */
@Mixin(value = GuiGraphicsExtractor.class)
public abstract class GuiGraphicsExtractorMixin {
    @WrapMethod(method = "item(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;III)V")
    private void earendel$drawSineWaveBehindItem(LivingEntity owner, Level level, ItemStack itemStack, int x, int y, int seed, Operation<Void> original) {
        GuiGraphicsExtractor gg = (GuiGraphicsExtractor) (Object) this;

        ExtractItemEvent.PRE.invoker().draw(gg, owner, level, itemStack, x, y, seed);
        original.call(owner, level, itemStack, x, y, seed);
        ExtractItemEvent.POST.invoker().draw(gg, owner, level, itemStack, x, y, seed);
    }
}
