package carpetextra.utils;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BlockTransformerComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;

public final class BlockTransformerUtils {
    private BlockTransformerUtils() {
    }

    public static boolean hasTransformer(ItemStack stack, RegistryKey<BlockTransformerComponent> key) {
        RegistryEntry<BlockTransformerComponent> transformer = stack.get(DataComponentTypes.BLOCK_TRANSFORMER);
        return transformer != null && transformer.matchesKey(key);
    }

    public static boolean canTransform(ServerWorld world, BlockPos pos, Direction clickedFace, ItemStack stack) {
        RegistryEntry<BlockTransformerComponent> transformer = stack.get(DataComponentTypes.BLOCK_TRANSFORMER);
        if (transformer == null) {
            return false;
        }

        for (BlockTransformerComponent.Transform transform : transformer.value().transforms()) {
            if (!transform.disallowedFaces().contains(clickedFace)
                    && transform.blockStateProvider().value().getOrNull(world, Random.create(pos.asLong()), pos) != null) {
                return true;
            }
        }
        return false;
    }
}
