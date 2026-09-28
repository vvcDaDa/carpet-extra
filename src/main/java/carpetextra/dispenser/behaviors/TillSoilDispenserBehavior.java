package carpetextra.dispenser.behaviors;

import carpetextra.dispenser.DispenserItemUsageContext;
import carpetextra.utils.BlockTransformerUtils;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class TillSoilDispenserBehavior extends FallibleItemDispenserBehavior {
    @Override
    protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        this.setSuccess(true);
        ServerWorld world = pointer.world();
        Direction dispenserFacing = pointer.state().get(DispenserBlock.FACING);
        BlockPos frontBlockPos = pointer.pos().offset(dispenserFacing);

        // check block in front of dispenser and one block down
        for(int i = 0; i < 2; i++) {
            BlockPos hoeBlockPos = frontBlockPos.down(i);
            // The vanilla hoe's transformations are data driven in 26.3.
            if(BlockTransformerUtils.canTransform(world, hoeBlockPos, dispenserFacing.getOpposite(), stack)) {
                BlockHitResult hitResult = new BlockHitResult(Vec3d.of(hoeBlockPos), dispenserFacing.getOpposite(), hoeBlockPos, false);
                ItemUsageContext context = new DispenserItemUsageContext(world, stack, hitResult);

                // use on block, test if successful
                if(stack.getItem().useOnBlock(context).isAccepted()) {
                    // damage hoe, remove if broken
                    stack.damage(1, world, null, (_) -> stack.setCount(0));
                    return stack;
                }
            }
        }

        // fail to dispense
        this.setSuccess(false);
        return stack;
    }
}
