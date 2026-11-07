package gay.chemthunder.earendel.core.index;

import gay.chemthunder.earendel.core.Earendel;
import gay.chemthunder.earendel.core.item.DriveItem;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTabOutput;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import org.chemthunder.arboreal.api.registration.Index;

/**
 * @author Chemthunder
 */
public interface ECreativeModeTabs {
    Index<CreativeModeTab> index = Earendel.MAIN.createIndex(BuiltInRegistries.CREATIVE_MODE_TAB);

    ResourceKey<CreativeModeTab> KEY = ResourceKey.create(Registries.CREATIVE_MODE_TAB, Earendel.id("main"));
    CreativeModeTab MAIN = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, KEY, FabricCreativeModeTab.builder()
            .icon(DriveItem::getDisplayedStack)
            .title(Component.literal("Earendel").withColor(0xFF8032e6))
            .build());

    static void init() {
        CreativeModeTabEvents.modifyOutputEvent(KEY).register(ECreativeModeTabs::modifyOutput);
    }

    private static void modifyOutput(FabricCreativeModeTabOutput output) {
        for (Item item : EItems.index) {
            output.accept(item);
        }
    }
}
