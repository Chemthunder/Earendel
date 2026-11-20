package gay.chemthunder.earendel.core.index;

import gay.chemthunder.earendel.core.Earendel;
import gay.chemthunder.earendel.core.item.CryingResolveItem;
import gay.chemthunder.earendel.core.item.DriveItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import org.chemthunder.arboreal.api.core.task.RegisterTask;

/**
 * @author Chemthunder
 */
public interface EItems {
    RegisterTask<Item> task = Earendel.MAIN.createTask(RegisterTask::new, BuiltInRegistries.ITEM);

    Item DRIVE = task.register("drive", new DriveItem(new Item.Properties()
            .setId(task.keyOf("drive"))
            .stacksTo(1)
    ));

    Item TRANSMITTER = task.register("transmitter", new BlockItem(EBlocks.TRANSMITTER, new Item.Properties()
            .setId(task.keyOf("transmitter"))
    ));

    Item INTERCEPTOR = task.register("interceptor", new BlockItem(EBlocks.INTERCEPTOR, new Item.Properties()
            .setId(task.keyOf("interceptor"))
    ));

    Item CASTER = task.register("caster", new BlockItem(EBlocks.CASTER, new Item.Properties()
            .setId(task.keyOf("caster"))
    ));

    Item CRYING_RESOLVE = task.register("crying_resolve", new CryingResolveItem(new Item.Properties()
            .setId(task.keyOf("crying_resolve"))
            .stacksTo(1)
    ));
}
