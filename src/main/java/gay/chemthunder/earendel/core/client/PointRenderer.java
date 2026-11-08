package gay.chemthunder.earendel.core.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import gay.chemthunder.earendel.core.Earendel;
import gay.chemthunder.earendel.core.client.model.PlaneModel;
import gay.chemthunder.earendel.core.index.client.EModelLayerLocations;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Unit;
import net.minecraft.world.phys.Vec3;

import java.util.List;

/**
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
public class PointRenderer {
    public static final List<Vec3> TEST_POINTS = List.of(
            new Vec3(0, 100, 0),
            new Vec3(3, 100, 0),
            new Vec3(6, 100, 0)
    );

    public static void render(PoseStack pose, SubmitNodeCollector node, CameraRenderState state) {
        Minecraft minecraft = Minecraft.getInstance();

        EntityModelSet modelSet = minecraft.getEntityModels();

        Vec3 cameraPosition = state.pos;

        pose.pushPose();

        for (Vec3 vec3 : TEST_POINTS) {
            Vec3 translationPoint = new Vec3(
                    vec3.x - cameraPosition.x,
                    vec3.y - cameraPosition.y,
                    vec3.z - cameraPosition.z
            );

            PlaneModel plane = new PlaneModel(modelSet.bakeLayer(EModelLayerLocations.PLANE));

            pose.pushPose();

            pose.translate(translationPoint);

            pose.rotate(Axis.XP.rotationDegrees(90));

            node.submitModel(
                    plane,
                    Unit.INSTANCE,
                    pose,
                    Earendel.id("textures/render/point.png"),
                    OverlayTexture.NO_OVERLAY,
                    OverlayTexture.WHITE_OVERLAY_V,
                    0xFFffffff
            );

            pose.popPose();

            pose.pushPose();

            pose.translate(translationPoint);

            node.submitModel(
                    plane,
                    Unit.INSTANCE,
                    pose,
                    Earendel.id("textures/render/point.png"),
                    OverlayTexture.NO_OVERLAY,
                    OverlayTexture.WHITE_OVERLAY_V,
                    0xFFffffff
            );

            pose.popPose();
        }

        pose.popPose();
    }
}


