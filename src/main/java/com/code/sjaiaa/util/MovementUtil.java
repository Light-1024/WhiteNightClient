package com.code.sjaiaa.util;

import net.minecraft.client.entity.EntityPlayerSP;

/**
 * @author sjaiaa
 * @date 2023/5/23 16:16
 * @discription
 */
public class MovementUtil {
    public static boolean playerIsMoving(){
        EntityPlayerSP player = MinecraftInstance.getPlayer();
        if(player.moveForward != 0 || player.moveStrafing != 0){
            return true;
        }
        return false;
    }
    public static double getDirection() {
        EntityPlayerSP player = MinecraftInstance.getPlayer();
        float rotationYaw = player.rotationYaw;

        if(player.moveForward < 0F)
            rotationYaw += 180F;

        float forward = 1F;
        if(player.moveForward < 0F)
            forward = -0.5F;
        else if(player.moveForward > 0F)
            forward = 0.5F;

        if(player.moveStrafing > 0F)
            rotationYaw -= 90F * forward;

        if(player.moveStrafing < 0F)
            rotationYaw += 90F * forward;

        return rotationYaw;
    }
}
