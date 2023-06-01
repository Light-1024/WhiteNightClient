package com.code.sjaiaa.util;

import com.code.sjaiaa.WhiteNightClient;

import java.awt.*;

/**
 * @author sjaiaa
 * @date 2023/5/17 15:37
 * @discription
 */
public class ColorUtil {
    static float realAlpha = 0.1f;
    public static int[] hexColors = new int[16];
    static{
        for (int i = 0; i < 16; i++) {
            int baseColor = (i >> 3 & 1) * 85;
            int red = (i >> 2 & 1) * 170 + baseColor + (i == 6 ? 85 : 0);
            int green = (i >> 1 & 1) * 170 + baseColor;
            int blue = (i & 1) * 170 + baseColor;

            hexColors[i] = (red & 255) << 16 | (green & 255) << 8 | (blue & 255);
        }
    }
    public static int ModifyColorAlpha(int color,int alpha){
        int rgb = color & 0xffffff;
        return rgb | alpha << 24;
    }
    public static int rgbaToColor(int red,int green,int blue,int alpha){
        red = red % 255;
        green = green % 255;
        blue = blue % 255;
        alpha = alpha % 255;
        int color = alpha << 8;
        color = (color | red) << 8;
        color = (color | green) << 8;
        return color | blue;
    }
    public static Colors getColor(int color){
        return new Colors(color);
    }
    public static Color rainbow() {
        Color currentColor = new Color(Color.HSBtoRGB((System.nanoTime() + 400000L) / 10000000000F % 1, 1F, 1F));
        return new Color(currentColor.getRed() / 255F, currentColor.getGreen() / 255f, currentColor.getBlue() / 255F, currentColor.getAlpha() / 255F);
    }
    public static Color rainbowAlpha(int color){

        int red = color >> 16 & 0xff;
        int green = color >> 8 & 0xff;
        int blue = color & 0xff;
        int alpha = 255;

        long timeElapsed = System.currentTimeMillis() % 500; // 获取已经过去的时间
        float percentage = (float) timeElapsed / 500; // 计算时间占总时间的百分比
        if (WhiteNightClient.timeUtil.isDelay(WhiteNightClient.defaultDelay)){
            float normalizedAlpha = 0.5f * percentage + 0.15f; // 计算标准化 alpha 值变化速度
            realAlpha = Math.max(0.3f, Math.min(0.5f, normalizedAlpha)); // 将 alpha 值限制在 0.1 - 0.8 之间
        }
        return new Color(red / 255f, green / 255f, blue / 255f, realAlpha);
    }
}
