package carpetextra.mixins;

import net.minecraft.component.type.BlockTransformerComponent;
import net.minecraft.item.ItemUsageContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockTransformerComponent.class)
public class BlockTransformerComponent_allowUseWithoutPlayerMixin {
    @Inject(method = "isPreemptedByShield", at = @At("HEAD"), cancellable = true)
    private static void allowUseWithoutPlayer(ItemUsageContext context, CallbackInfoReturnable<Boolean> cir) {
        if (context.getPlayer() == null) {
            cir.setReturnValue(false);
        }
    }
}
