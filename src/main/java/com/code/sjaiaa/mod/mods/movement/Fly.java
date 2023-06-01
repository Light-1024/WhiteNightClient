package com.code.sjaiaa.mod.mods.movement;

import com.code.sjaiaa.mod.BaseMod;
import com.code.sjaiaa.mod.ModType;
import com.code.sjaiaa.util.MinecraftInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

/**
 * @author sjaiaa
 * @date 2023/5/23 18:31
 * @discription
 */
public class Fly extends BaseMod {
    public Fly() {
        super("ExampleFly", ModType.MOVEMENT);
    }

    @Override
    public String modInfo() {
        return "允许你进行飞行";
    }

    @Override
    public void onPlayerEvent(TickEvent.PlayerTickEvent event) {
//        MinecraftInstance.getPlayer().capabilities.isFlying = true;
        MinecraftInstance.getPlayer().motionY = 0;
        if (MinecraftInstance.getMinecraft().gameSettings.keyBindJump.isKeyDown()){
            MinecraftInstance.getPlayer().motionY += 0.3f;
        }
//        if(MinecraftInstance.getPlayer().ticksExisted % 3 == 0){
//            EntityPlayerSP player = MinecraftInstance.getPlayer();
//            MinecraftInstance.getMinecraft().getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(player.posX,player.posY - 0.1f,player.posZ,true));
//        }
    }
}
