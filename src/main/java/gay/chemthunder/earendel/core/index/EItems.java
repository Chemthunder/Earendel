package gay.chemthunder.earendel.core.index;

import gay.chemthunder.earendel.core.Earendel;
import gay.chemthunder.earendel.core.item.DriveItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import org.chemthunder.arboreal.api.registration.Index;

/**
 * @author Chemthunder
 */
public interface EItems {
    Index<Item> index = Earendel.MAIN.createIndex(BuiltInRegistries.ITEM);

    Item DRIVE = index.register("drive", new DriveItem(new Item.Properties()
            .setId(ResourceKey.create(Registries.ITEM, index.id("drive")))
            .stacksTo(1)
    ));

    Item TRANSMITTER = index.register("transmitter", new BlockItem(EBlocks.TRANSMITTER, new Item.Properties()
            .setId(ResourceKey.create(Registries.ITEM, index.id("transmitter")))
    ));

    static void init() {}
}
