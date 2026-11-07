package gay.chemthunder.earendel.core.index;

import gay.chemthunder.earendel.core.Earendel;
import gay.chemthunder.earendel.core.item.CryingResolveItem;
import gay.chemthunder.earendel.core.item.DriveItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import org.chemthunder.arboreal.api.registration.Index;

/**
 * @author Chemthunder
 */
public interface EItems {
    Index<Item> index = Earendel.MAIN.createIndex(BuiltInRegistries.ITEM);

    Item DRIVE = index.register("drive", new DriveItem(new Item.Properties()
            .setId(index.keyOf("drive"))
            .stacksTo(1)
    ));

    Item TRANSMITTER = index.register("transmitter", new BlockItem(EBlocks.TRANSMITTER, new Item.Properties()
            .setId(index.keyOf("transmitter"))
    ));

    Item INTERCEPTOR = index.register("interceptor", new BlockItem(EBlocks.INTERCEPTOR, new Item.Properties()
            .setId(index.keyOf("interceptor"))
    ));

    Item CRYING_RESOLVE = index.register("crying_resolve", new CryingResolveItem(new Item.Properties()
            .setId(index.keyOf("crying_resolve"))
            .stacksTo(1)
    ));

    static void init() {}
}
