package gay.chemthunder.earendel.core.index.client;

import gay.chemthunder.earendel.core.Earendel;
import gay.chemthunder.earendel.core.client.model.PlaneModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;

/**
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
public interface EModelLayerLocations {

    ModelLayerLocation PLANE = new ModelLayerLocation(Earendel.id("plane"), "main");

    static void clinit() {
        ModelLayerRegistry.registerModelLayer(PLANE, PlaneModel::getLayerDefinition);
    }
}
