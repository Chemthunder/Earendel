package gay.chemthunder.earendel.core.client.event;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import gay.chemthunder.earendel.core.Earendel;
import gay.chemthunder.earendel.core.client.model.PlaneModel;
import gay.chemthunder.earendel.core.index.client.EModelLayerLocations;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderEvents;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.util.Unit;
import net.minecraft.world.phys.Vec3;

import java.util.List;

/**
 * @author Chemthunder
 */
public class TestPointRenderingEvent implements LevelRenderEvents.EndMain {
    public static final List<Vec3> TEST_POINTS = List.of(
            new Vec3(0, 100, 0),
            new Vec3(50, 50, 50),
            new Vec3(25, 44, 66)
    );

    public void endMain(LevelRenderContext context) {
        PoseStack pose = context.poseStack();
        Camera camera = context.gameRenderer().mainCamera();

        Vec3 cameraPosition = camera.position();

        Minecraft minecraft = Minecraft.getInstance();

//        EntityModelSet modelSet = minecraft.getEntityModels();
//
//        pose.pushPose();
//
//        for (Vec3 vec3 : TEST_POINTS) {
//            Vec3 translationPoint = new Vec3(
//                    vec3.x - cameraPosition.x,
//                    vec3.y - cameraPosition.y,
//                    vec3.z - cameraPosition.z
//            );
//
//            pose.pushPose();
//
//            pose.translate(translationPoint);
//
//            pose.rotate(Axis.XP.rotationDegrees(90));
//
//            PlaneModel plane = new PlaneModel(modelSet.bakeLayer(EModelLayerLocations.PLANE));
//
//            context.submitNodeCollector().submitModel(
//                    plane,
//                    Unit.INSTANCE,
//                    pose,
//                    Earendel.id("textures/render/point.png"),
//                    OverlayTexture.NO_OVERLAY,
//                    OverlayTexture.WHITE_OVERLAY_V,
//                    0xFFffffff
//            );
//
//            pose.popPose();
//        }
//
//        pose.popPose();
    }
}
