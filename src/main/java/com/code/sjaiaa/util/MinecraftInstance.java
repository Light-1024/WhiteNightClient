package com.code.sjaiaa.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.ServerData;

/**
 * @author sjaiaa
 * @date 2023/5/16 15:22
 * @discription
 */
public class MinecraftInstance {
    public static Minecraft getMinecraft(){
        return Minecraft.getMinecraft();
    }
    private static ServerData getServerInfo(){
        return MinecraftInstance.getMinecraft().getCurrentServerData();
    }
    public static String[] ServerInfo(){
        if (getServerInfo() == null){
            return new String[]{"SingleServer","1ms"};
        }
        return new String[]{getServerInfo().serverIP, String.valueOf(getServerInfo().pingToServer)};
    }
    public static EntityPlayerSP getPlayer(){
        return getMinecraft().thePlayer;
    }
}
