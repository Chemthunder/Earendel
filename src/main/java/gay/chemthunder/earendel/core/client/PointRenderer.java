package gay.chemthunder.earendel.core.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import gay.chemthunder.earendel.core.Earendel;
import gay.chemthunder.earendel.core.EarendelClient;
import gay.chemthunder.earendel.core.client.event.PointHudEvents;
import gay.chemthunder.earendel.core.client.model.PlaneModel;
import gay.chemthunder.earendel.core.index.EAttachmentTypes;
import gay.chemthunder.earendel.core.index.client.EModelLayerLocations;
import gay.chemthunder.earendel.core.index.client.ERenderTypes;
import gay.chemthunder.earendel.core.util.SignalPos;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ARGB;
import net.minecraft.util.EasingType;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
public class PointRenderer {
    public static void render(PoseStack pose, SubmitNodeCollector node, CameraRenderState state) {
        Minecraft minecraft = Minecraft.getInstance();

        Player player = minecraft.player;
        if (player == null) return;

        if (player.getActiveItem().is(Items.SPYGLASS) && player.isUsingItem()) {

            EntityModelSet modelSet = minecraft.getEntityModels();

            Camera cam = minecraft.gameRenderer.mainCamera();
            Vec3 cPos = state.pos;

            Level local = minecraft.level;
            if (local == null) return;

            pose.pushPose();

            for (SignalPos signalPos : local.getAttachedOrCreate(EAttachmentTypes.CASTERS)) {
                PlaneModel plane = new PlaneModel(modelSet.bakeLayer(EModelLayerLocations.PLANE));

                BlockPos signalOrigin = signalPos.pos().pos();

                Vec3 pointVec = new Vec3(
                        signalOrigin.getX() + 0.5,
                        signalOrigin.getY() + 0.5,
                        signalOrigin.getZ() + 0.5
                );

                pose.pushPose();

                pose.translate(cPos.vectorTo(pointVec).normalize().scale(2));

                float pointSize = EasingType.OUT_EXPO.apply(PointHudEvents.pointScale) * 0.2F;

                pose.scale(pointSize, pointSize, pointSize);

                pose.rotate(cam.rotation());
                pose.rotate(Axis.XP, 30);

                pose.pushPose();

                pose.rotate(
                        Axis.YP.rotationDegrees(
                                (EarendelClient.age + minecraft.getDeltaTracker().getGameTimeDeltaTicks())
                                        / 2
                        )
                );

                node.submitModel(
                        plane,
                        Unit.INSTANCE,
                        pose,
                        RenderTypes.entityTranslucent(Earendel.id("textures/render/point.png")),
                        LightCoordsUtil.FULL_BRIGHT,
                        OverlayTexture.NO_OVERLAY,
                        signalPos.signal().color(),
                        null,
                        signalPos.signal().color()
                );

                pose.popPose();

                pose.popPose();
            }
            pose.popPose();
        }
    }
}



