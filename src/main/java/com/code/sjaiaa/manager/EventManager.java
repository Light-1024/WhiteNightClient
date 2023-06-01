package com.code.sjaiaa.manager;

import com.code.sjaiaa.WhiteNightClient;
import com.code.sjaiaa.event.EventInst;
import com.code.sjaiaa.event.PacketEvent;
import com.code.sjaiaa.mod.BaseMod;
import com.code.sjaiaa.util.Chat;
import com.code.sjaiaa.util.MinecraftInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.List;

/**
 * @author sjaiaa
 * @date 2023/5/16 14:49
 * @discription
 */
public class EventManager implements EventInst {
    @Override
    @SubscribeEvent
    public void onPlayerEvent(TickEvent.PlayerTickEvent event){
        List<BaseMod> mods = ModuleManager.getMods();
        for (BaseMod mod : mods) {
            if(mod.isState()){
                mod.onPlayerEvent(event);
            }
        }
    }
    @Override
    @SubscribeEvent
    public void onClientEvent(TickEvent.ClientTickEvent event){
        if(WhiteNightClient.timeUtil.isDelay(WhiteNightClient.defaultDelay)){
            WhiteNightClient.timeUtil.reset();
        }
        EntityPlayerSP thePlayer = MinecraftInstance.getMinecraft().thePlayer;
        if(thePlayer != null){
            Minecraft.getMinecraft().thePlayer.rotationYaw %= 360000;
        }
        List<BaseMod> mods = ModuleManager.getMods();
        for (BaseMod mod : mods) {
            if(mod.isState()){
                mod.onClientEvent(event);
            }
        }
    }
    @Override
    @SubscribeEvent
    public void onRender2D(RenderGameOverlayEvent event){
        if (event.type != RenderGameOverlayEvent.ElementType.TEXT) {
            return;
        }
        List<BaseMod> mods = ModuleManager.getMods();
        for (BaseMod mod : mods) {
            if(mod.isState()){
                mod.onRender2D(event);
            }
        }
    }

    @Override
    @SubscribeEvent
    public void onKeyEvent(InputEvent.KeyInputEvent event){
        int key = Keyboard.getEventKey();
        List<BaseMod> mods = ModuleManager.getMods();
        if (Keyboard.getEventKeyState()) {
            for (BaseMod mod : mods) {
                if(mod.getKey() == key){
                    mod.setState(!mod.isState());
                }
                if(mod.isState()){
                    mod.onKeyEvent(event);
                }
            }
        }
    }

    @Override
    public void onPacketEvent(PacketEvent event) {
//        Packet<?> packet = event.getPacket();
//        if(packet instanceof C03PacketPlayer){
////            RotationUtil.onUpdateServerRotation(packet);
////            RotationUtil.rotation(packet);
//        }
//        PacketUtils.onPacket(event);
        List<BaseMod> mods = ModuleManager.getMods();
        for (BaseMod mod : mods) {
            if(mod.isState()){
                mod.onPacketEvent(event);
            }
        }
    }
    @SubscribeEvent
    public void onAttackEvent(AttackEntityEvent event){
        if (!(event.entity instanceof EntityPlayerSP) || !(event.target instanceof EntityLivingBase)){
            return;
        }
        List<BaseMod> mods = ModuleManager.getMods();
        for (BaseMod mod : mods) {
            mod.onAttackEvent(event);
        }
    }
}
