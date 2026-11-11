package gay.chemthunder.earendel.core.index.client;

import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;

import java.util.function.BiFunction;

/**
 * @author Chemthunder
 */
public interface ERenderTypes {
    BiFunction<Identifier, Boolean, RenderType> ENTITY_TRANSLUCENT_LIGHTLESS = Util.memoize(
            (texture, affectsOutline) -> {
        RenderSetup state = RenderSetup.builder(RenderPipelines.ENTITY_TRANSLUCENT)
                .setOitPipelines(RenderPipelines.OIT_ENTITY)
                .withTexture("Sampler0", texture)
                .useOverlay()
                .affectsCrumbling()
                .sortOnUpload()
                .setOutline(affectsOutline ? RenderSetup.OutlineProperty.AFFECTS_OUTLINE : RenderSetup.OutlineProperty.NONE)
                .createRenderSetup();
        return RenderType.create("entity_translucent_lightless", state);
    });

    static void clinit() {}

    static RenderType entityTranslucentLightless(final Identifier texture) {
        return ENTITY_TRANSLUCENT_LIGHTLESS.apply(texture, false);
    }
}
