package com.code.sjaiaa.mixins;

import net.minecraft.network.play.client.C01PacketChatMessage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * @author sjaiaa
 * @date 2023/5/20 11:41
 * @discription
 */
@Mixin(C01PacketChatMessage.class)
public interface C01PacketChatMessageMixin{
    @Accessor("message")
    void setMessage(String message);
}