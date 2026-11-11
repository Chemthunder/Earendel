package gay.chemthunder.earendel.core.index;

import gay.chemthunder.earendel.core.Earendel;
import gay.chemthunder.earendel.core.util.ESerialization;
import gay.chemthunder.earendel.core.util.SignalPos;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
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

    AttachmentType<List<SignalPos>> CASTERS = register("casters",
            builder -> builder
                    .persistent(SignalPos.CODEC.listOf())
                    .syncWith(ESerialization.SIGNAL_POS_LIST_STREAM_CODEC, AttachmentSyncPredicate.all())
                    .initializer(ArrayList::new)
    );

    static void init() {}

    static <T> AttachmentType<T> register(String name, Consumer<AttachmentRegistry.Builder<T>> consumer) {
        return AttachmentRegistry.create(Earendel.id(name), consumer);
    }
}
