package com.code.sjaiaa.event;

import net.minecraft.network.Packet;

/**
 * @author sjaiaa
 * @date 2023/5/19 20:34
 * @discription
 */
public class PacketEvent extends CancellableEvent{
    public enum Type{
        RECEIVE,
        SEND
    }
    private Packet<?> packet;
    private Type type;
    public PacketEvent(Packet<?> packet, Type type) {
        this.packet = packet;
        this.type = type;
    }

    public Packet<?> getPacket() {
        return packet;
    }

    public Type getType() {
        return type;
    }
}
