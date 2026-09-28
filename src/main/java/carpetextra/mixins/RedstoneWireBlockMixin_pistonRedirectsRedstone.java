package carpetextra.mixins;

import carpetextra.CarpetExtraSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.PistonBlock;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RedstoneWireBlock.class)
public abstract class RedstoneWireBlockMixin_pistonRedirectsRedstone
{
    @Inject(
            method = "wireConnectsTo(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;)Z",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private static void onConnectsTo(BlockState state, BlockView world, BlockPos pos, Direction dir, CallbackInfoReturnable<Boolean> cir)
    {
        if ( CarpetExtraSettings.pistonRedirectsRedstone ) {
            if (state.getBlock() instanceof PistonBlock)
            {
                cir.setReturnValue(dir != null && dir != state.get(PistonBlock.FACING).getOpposite());
            }
        }
    }
}
