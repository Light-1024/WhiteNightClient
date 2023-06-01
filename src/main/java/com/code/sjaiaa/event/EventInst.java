package com.code.sjaiaa.event;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * @author sjaiaa
 * @date 2023/5/16 14:58
 * @discription
 */
public interface EventInst {
    void onPlayerEvent(TickEvent.PlayerTickEvent event);
    void onClientEvent(TickEvent.ClientTickEvent event);
    void onRender2D(RenderGameOverlayEvent event);
    void onKeyEvent(InputEvent.KeyInputEvent event);

    void onPacketEvent(PacketEvent event);
    void onAttackEvent(AttackEntityEvent event);

}
