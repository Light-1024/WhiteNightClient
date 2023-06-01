package com.code.sjaiaa.mixins;

import com.code.sjaiaa.WhiteNightClient;
import com.code.sjaiaa.event.PacketEvent;
import com.code.sjaiaa.util.Chat;
import com.code.sjaiaa.util.PacketUtils;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author sjaiaa
 * @date 2023/5/19 20:32
 * @discription
 */
@Mixin(NetworkManager.class)
public class NetworkManagerMixin {
    @Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
    private void read(ChannelHandlerContext context, Packet<?> packet, CallbackInfo callback) {
        //服务器信息过滤
        if(PacketUtils.getPacketType(packet) != PacketUtils.PacketType.SERVERSIDE){
            return;
        }
        PacketEvent packetEvent = new PacketEvent(packet, PacketEvent.Type.RECEIVE);
        WhiteNightClient.eventManager.onPacketEvent(packetEvent);
        if(packetEvent.isCancelled()) {
            callback.cancel();
        }
    }

    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void send(Packet<?> packet, CallbackInfo callback) {
        if (PacketUtils.getPacketType(packet) != PacketUtils.PacketType.CLIENTSIDE){
            return;
        }
        PacketEvent packetEvent = new PacketEvent(packet, PacketEvent.Type.SEND);
        if(!PacketUtils.handleSendPacket(packet)){
            WhiteNightClient.eventManager.onPacketEvent(packetEvent);
        }
        if(packetEvent.isCancelled()){
            callback.cancel();
        }
        if (packet instanceof C00PacketKeepAlive){
            Chat.onMessage("KeepAlive");
        }
//        if(PacketUtils.getPacketType(packet) != PacketUtils.PacketType.CLIENTSIDE)
//            return;
//
//        if(!PacketUtils.handleSendPacket(packet)){
//            final PacketEvent event = new PacketEvent(packet, PacketEvent.Type.SEND);
//            ExampleMod.eventManager.onPacketEvent(event);
//
//            if(event.isCancelled()) {
//                callback.cancel();
//            }
////            else if (BlinkUtils.INSTANCE.pushPacket(packet))
////                callback.cancel();
//        }
    }
}
