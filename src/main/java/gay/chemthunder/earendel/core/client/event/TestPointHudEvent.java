package gay.chemthunder.earendel.core.client.event;

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3x2fStack;

/**
 * @author Chemthunder
 */
public class TestPointHudEvent implements HudElement {
    public void extractRenderState(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
        Vec3 point = new Vec3(100, 75, 100);

        Minecraft minecraft = Minecraft.getInstance();
        Camera camera = minecraft.gameRenderer.mainCamera();

        Matrix3x2fStack matrix = graphics.pose();


    }
}
