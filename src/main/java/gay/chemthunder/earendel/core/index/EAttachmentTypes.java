package gay.chemthunder.earendel.core.index;

import gay.chemthunder.earendel.core.Earendel;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

/**
 * @author Chemthunder
 */
public interface EAttachmentTypes {
    AttachmentType<ItemStack> STORED_STACK = register("stored_stack",
            builder -> builder
                    .persistent(ItemStack.CODEC)
                    .syncWith(ItemStack.STREAM_CODEC, AttachmentSyncPredicate.all())
                    .initializer(() -> ItemStack.EMPTY)
    );

    static void init() {}

    static <T> AttachmentType<T> register(String name, Consumer<AttachmentRegistry.Builder<T>> consumer) {
        return AttachmentRegistry.create(Earendel.id(name), consumer);
    }
}
