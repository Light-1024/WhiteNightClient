package com.code.sjaiaa.mixins;


import com.code.sjaiaa.WhiteNightClient;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author sjaiaa
 * @date 2023/5/16 11:03
 * @discription
 */
@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Shadow
    private static Logger logger;

    @Shadow
    private boolean fullscreen;

    @Inject(method="Lnet/minecraft/client/Minecraft;createDisplay()V", at = @At("TAIL"), cancellable = true)
    private void createDisplay(CallbackInfo ci) throws LWJGLException {
        Display.setResizable(true);
        Display.setTitle(WhiteNightClient.MODID + " " + WhiteNightClient.VERSION + " | " + "created by sjaiaa | QQgroup: 1092813027 | " + (WhiteNightClient.findNewUpdate ? "Find new update":"no update"));
    }

    @Shadow
    private void updateDisplayMode() {

    }
}