package gay.chemthunder.earendel.core.client.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.util.Unit;

/**
 * @author AcoYT
 */
public class PlaneModel extends Model.Simple {
    private final ModelPart bone;

    public PlaneModel(ModelPart root) {
        super(root, RenderTypes::entityCutout);
        this.bone = root.getChild("bone");
    }

    public static LayerDefinition getLayerDefinition() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition part = mesh.getRoot();
        PartDefinition bone = part.addOrReplaceChild(
                "bone",
                CubeListBuilder.create()
                        .texOffs(-16, 0)
                        .addBox(-8.0F, 0.0F, -8.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F)
        );

        return LayerDefinition.create(mesh, 16, 16);
    }
}
