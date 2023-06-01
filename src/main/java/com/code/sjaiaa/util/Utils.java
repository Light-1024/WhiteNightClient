package com.code.sjaiaa.util;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import java.awt.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {
    public static boolean isContainerEmpty(Container container) {
        int i = 0;
        int slotAmount = container.inventorySlots.size() == 90 ? 54 : 27;
        while (i < slotAmount) {
            if (container.getSlot(i).getHasStack()) {
                return false;
            }
            ++i;
        }
        return true;
    }
    public static String getMD5(String input) {
        StringBuilder res = new StringBuilder();
        try {
            byte[] md5;
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(input.getBytes());
            byte[] arrby = md5 = algorithm.digest();
            int n = arrby.length;
            int n2 = 0;
            while (n2 < n) {
                byte aMd5 = arrby[n2];
                String tmp = Integer.toHexString(255 & aMd5);
                if (tmp.length() == 1) {
                    res.append("0").append(tmp);
                } else {
                    res.append(tmp);
                }
                ++n2;
            }
        }
        catch (NoSuchAlgorithmException algorithm) {
            // empty catch block
        }
        return res.toString();
    }
    public static ScaledResolution getMaxSize(){
        // 获取当前 Minecraft 实例
        Minecraft mc = Minecraft.getMinecraft();

        // 获取可用屏幕尺寸
        return new ScaledResolution(mc);
    }
    public static boolean isFull(){
        DisplayMode displaymode = Display.getDesktopDisplayMode();
        //因为y轴有空隙
        if (displaymode.getWidth() == MinecraftInstance.getMinecraft().displayWidth && displaymode.getHeight() - MinecraftInstance.getMinecraft().displayHeight <= 100){
            return true;
        }
        return false;
    }
}

