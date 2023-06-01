package com.code.sjaiaa.mixins;

import com.code.sjaiaa.WhiteNightClient;
import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author sjaiaa
 * @date 2023/5/16 16:02
 * @discription
 */
@Mixin(EntityPlayerSP.class)
public class EntityPlayerSPMixin {
    @Inject(method = "Lnet/minecraft/client/entity/EntityPlayerSP;sendChatMessage(Ljava/lang/String;)V",at = @At("HEAD"),cancellable = true)
    public void onEnableCommand(String message, CallbackInfo info){
        if(WhiteNightClient.commandManager.findCommand(message)){
            WhiteNightClient.commandManager.runCommands(message);
            info.cancel();
        }
    }


}
