package gay.chemthunder.earendel.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.blaze3d.resource.GraphicsResourceAllocator;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.renderpearl.api.buffers.GpuBufferSlice;
import gay.chemthunder.earendel.core.client.PointRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.SubmitNodeStorage;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Chemthunder
 */
@Mixin(value = LevelRenderer.class)
public abstract class LevelRendererMixin {
    @Shadow @Final private SubmitNodeStorage submitNodeStorage;

    @Unique private PoseStack stack;
    @Unique private SubmitNodeStorage storedNode;

    @ModifyExpressionValue(
            method = "submitFeatures",
            at = @At(
                    value = "NEW",
                    target = "Lcom/mojang/blaze3d/vertex/PoseStack;"
            )
    )
    private PoseStack earendel$setInternalPoseStack(PoseStack poseStack) {
        stack = poseStack;
        return poseStack;
    }

    @Inject(method = "render", at = @At(value = "HEAD"))
    private void earendel$applyInternalStoredNodes(GraphicsResourceAllocator resourceAllocator, boolean renderOutline, CameraRenderState cameraState, GpuBufferSlice terrainFog, Vector4f fogColor, boolean shouldRenderSky, boolean consistentDepthRequired, CallbackInfo ci) {
        this.storedNode = this.submitNodeStorage;
    }

    @Inject(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/profiling/ProfilerFiller;popPush(Ljava/lang/String;)V",
                    ordinal = 3
            )
    )
    private void earendel$renderPoints(GraphicsResourceAllocator resourceAllocator, boolean renderOutline, CameraRenderState cameraState, GpuBufferSlice terrainFog, Vector4f fogColor, boolean shouldRenderSky, boolean consistentDepthRequired, CallbackInfo ci) {
        PointRenderer.render(this.stack, this.storedNode, cameraState);
    }
}
