package dev.blackilykat.no_double_sneak.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.network.packet.s2c.play.EntityTrackerUpdateS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    @Inject(method = "onEntityTrackerUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/data/DataTracker;writeUpdatedEntries(Ljava/util/List;)V", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD)
    private void no_double_sneak$fixBug(EntityTrackerUpdateS2CPacket packet, CallbackInfo ci, Entity entity) {
        if(!entity.equals(MinecraftClient.getInstance().player)) return;
        packet.getTrackedValues().removeIf(entry -> entry.getData().getType().equals(TrackedDataHandlerRegistry.ENTITY_POSE));
    }
}
