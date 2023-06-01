package com.code.sjaiaa.util;

import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.util.ChatComponentTranslation;

/**
 * @author sjaiaa
 * @date 2023/5/16 15:20
 * @discription
 */
public class Chat {
    public static void onMessage(String message){
        if (MinecraftInstance.getPlayer() == null){
            return;
        }
        MinecraftInstance.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentTranslation(message));
    }
}
