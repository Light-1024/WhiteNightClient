package com.code.sjaiaa.mixins;

import net.minecraft.network.play.client.C03PacketPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * @author sjaiaa
 * @date 2023/5/20 22:27
 * @discription
 */
@Mixin(C03PacketPlayer.class)
public interface C03PacketPlayerMixin {
    @Accessor("yaw")
    void setYaw(float yaw);
    @Accessor("pitch")
    void setPitch(float pitch);
    @Accessor("yaw")
    float getYaw();
    @Accessor("pitch")
    float getPitch();
}
