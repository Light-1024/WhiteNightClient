package com.code.sjaiaa.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import javax.swing.*;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Hashtable;

import static org.lwjgl.opengl.GL11.*;

/**
 * @author sjaiaa
 * @date 2023/5/17 15:26
 * @discription
 */
public class RenderUtil {
    public static void onStart() {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glPushMatrix();
    }

    public static void onStop() {
        GL11.glPopMatrix();
        GL11.glEnable(GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
    }

    public static void drawStringWithRect(String string, int x, int y, int colorString, int colorRect, int colorRect2) {
        RenderUtil.drawBorderedRect(x - 2, y - 2, x + MinecraftInstance.getMinecraft().fontRendererObj.getStringWidth(string) + 2, y + 10, 1, colorRect, colorRect2);
        MinecraftInstance.getMinecraft().fontRendererObj.drawString(string, x, y, colorString);
    }

    public static void drawString(String string, int x, int y, int colorString) {
        MinecraftInstance.getMinecraft().fontRendererObj.drawString(string, x, y, colorString);
    }

    public static void drawBorderedRect(double x, double y, double x2, double y2, float l1, int col1, int col2) {
        drawRect((int) x, (int) y, (int) x2, (int) y2, col2);

        float f = (float) (col1 >> 24 & 0xFF) / 255F;
        float f1 = (float) (col1 >> 16 & 0xFF) / 255F;
        float f2 = (float) (col1 >> 8 & 0xFF) / 255F;
        float f3 = (float) (col1 & 0xFF) / 255F;

        //GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);

        GL11.glPushMatrix();
        GL11.glColor4f(f1, f2, f3, f);
        GL11.glLineWidth(l1);
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glEnd();
        GL11.glPopMatrix();

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        //GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
    }

    public static void drawOutLine(int x, int y, int x2, int y2, int color) {
        onStart();
        GlStateManager.enableBlend();
        GLUtils.glColor(color);
        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x2, y2);
        GL11.glVertex2d(x, y2);
        GL11.glEnd();
        GlStateManager.disableBlend();
        onStop();
    }

    public static void drawRect(float left, float top, float right, float bottom, int color) {
        float var5;
        if (left < right) {
            var5 = left;
            left = right;
            right = var5;
        }
        if (top < bottom) {
            var5 = top;
            top = bottom;
            bottom = var5;
        }
        onStart();
        GlStateManager.enableBlend();
//        GlStateManager.disableTexture2D();
//        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GLUtils.glColor(color);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2d(left, bottom);
        GL11.glVertex2d(right, bottom);
        GL11.glVertex2d(right, top);
        GL11.glVertex2d(left, top);
        GL11.glEnd();
//        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        onStop();
    }

    public static void drawImage(ResourceLocation image, int x, int y, float width, float height, float alpha) {
        GL11.glPushMatrix();
        GL11.glDisable((int) 2929);
        GL11.glEnable((int) 3042);
        GL11.glDepthMask((boolean) false);

        OpenGlHelper.glBlendFunc((int) 770, (int) 771, (int) 1, (int) 0);
        GL11.glColor4f((float) 1.0f, (float) 1.0f, (float) 1.0f, alpha);
        MinecraftInstance.getMinecraft().getTextureManager().bindTexture(image);
        Gui.drawModalRectWithCustomSizedTexture((int) x, (int) y, (float) 0.0f, (float) 0.0f, (int) width, (int) height,
                (float) width, (float) height);
        GL11.glDepthMask((boolean) true);
        GL11.glDisable((int) 3042);
        GL11.glEnable((int) 2929);
        GL11.glPopMatrix();

        GL11.glColor4f((float) 1.0f, (float) 1.0f, (float) 1.0f, 1f);
    }

    public static void drawImage(URL resourceName, float left, float top, float right, float bottom) {

        //glEnable(GL_TEXTURE_2D);
        GL11.glPushMatrix();
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        //glOrtho(0, 800, 600, 0, -1, 1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        //GL11.glScalef(1.5f, 1.5f, 1.5f);
        try {
            getTexture(resourceName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // clear screen
        //glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        // translate to the right location and prepare to draw
        //glTranslatef(300, 200, 0);
        // draw a quad textured to match the sprite
        GL11.glBegin(GL11.GL_QUADS);
        {
            GL11.glTexCoord2f(0, 0);
            GL11.glVertex2f(left, bottom);

            GL11.glTexCoord2f(0, 1);
            GL11.glVertex2f(bottom, right);

            GL11.glTexCoord2f(1, 1);
            GL11.glVertex2f(right, top);

            GL11.glTexCoord2f(1, 0);
            GL11.glVertex2f(top, left);
        }
        //glDeleteTextures(texture);
        GL11.glEnd();
        GL11.glPopMatrix();
        //glDisable(GL_TEXTURE_2D);
    }

    static int textures = -1;

    public static void getTexture(URL resourceName) throws IOException {
        if (textures == -1) {
            textures = GL11.glGenTextures();
            GL11.glBindTexture(GL_TEXTURE_2D, textures);
        } else {
            GL11.glBindTexture(GL_TEXTURE_2D, textures);
            return;
        }

        BufferedImage bufferedImage = loadImage(resourceName);
        ByteBuffer textureBuffer = convertImageData(bufferedImage);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        // produce a texture from the byte buffer
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, bufferedImage.getWidth(),
                bufferedImage.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE,
                textureBuffer);
    }

    public static void drawFace(ResourceLocation skin, int x, int y, int u, int v, int uWidth, int vHeight, int width, int height, int tileWidth, int tileHeight) {
        MinecraftInstance.getMinecraft().getTextureManager().bindTexture(skin);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glColor4f(1, 1, 1, 1);
        Gui.drawScaledCustomSizeModalRect(x, y, u, v, uWidth, vHeight, width, height, tileWidth, tileHeight);
        GL11.glDisable(GL11.GL_BLEND);
    }

    private static BufferedImage loadImage(URL url) throws IOException {
        // due to an issue with ImageIO and mixed signed code
        // we are now using good oldfashioned ImageIcon to load
        // images and the paint it on top of a new BufferedImage
        Image img = new ImageIcon(url).getImage();
        BufferedImage bufferedImage = new BufferedImage(img.getWidth(null) + 400, img
                .getHeight(null) + 400, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = bufferedImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // 设置抗锯齿
        g.drawImage(img, 100, 100, null);
        g.dispose();

        return bufferedImage;
    }

    /**
     * Convert the buffered image to a texture
     */
    private static ByteBuffer convertImageData(BufferedImage bufferedImage) {
        ByteBuffer imageBuffer;
        WritableRaster raster;
        BufferedImage texImage;

        ColorModel glAlphaColorModel = new ComponentColorModel(ColorSpace
                .getInstance(ColorSpace.CS_sRGB), new int[]{8, 8, 8, 8},
                true, false, Transparency.TRANSLUCENT, DataBuffer.TYPE_BYTE);

        raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE,
                bufferedImage.getWidth(), bufferedImage.getHeight(), 4, null);
        texImage = new BufferedImage(glAlphaColorModel, raster, true,
                new Hashtable());

        // copy the source image into the produced image
        Graphics2D g = texImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // 设置抗锯齿
        g.setColor(new Color(0f, 0f, 0f, 0f));
        g.fillRect(100, 100, 256, 256);
        g.drawImage(bufferedImage, 100, 100, null);

        // build a byte buffer from the temporary image
        // that be used by OpenGL to produce a texture.
        byte[] data = ((DataBufferByte) texImage.getRaster().getDataBuffer())
                .getData();

        imageBuffer = ByteBuffer.allocateDirect(data.length);
        imageBuffer.order(ByteOrder.nativeOrder());
        imageBuffer.put(data, 0, data.length);
        imageBuffer.flip();

        return imageBuffer;
    }

    public static void drawImage(ResourceLocation resourceLocation, int x, int y, int u, int v, int width, int height, int textureWidth, int textureHeight) {
        GL11.glPushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        MinecraftInstance.getMinecraft().getTextureManager().bindTexture(resourceLocation);
        Gui.drawModalRectWithCustomSizedTexture(x, y, u, v, width, height, textureWidth, textureHeight);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GL11.glPopMatrix();
    }
    public static void startGlScissor(int x, int y, int width, int height) {
        Minecraft mc = Minecraft.getMinecraft();
        int scaleFactor = 1;
        int k = mc.gameSettings.guiScale;
        if (k == 0) {
            k = 1000;
        }
        while (scaleFactor < k && mc.displayWidth / (scaleFactor + 1) >= 320 && mc.displayHeight / (scaleFactor + 1) >= 240) {
            ++scaleFactor;
        }
        GL11.glPushMatrix();
        GL11.glEnable(3089);
        GL11.glScissor((int)(x * scaleFactor), (int)(mc.displayHeight - (y + height) * scaleFactor), (int)(width * scaleFactor), (int)(height * scaleFactor));
    }
    public static void stopGlScissor(){
        GL11.glDisable(3089);
        GL11.glPopMatrix();
    }

    public static void drawRoundOutLine(float x, float y, float r,int color) {
        onStart();

        GlStateManager.enableBlend();
        GLUtils.glColor(color);
        glBegin(GL_LINE_LOOP);
        for (int i = 0; i < 360; i++) {
            float theta = i * (float) Math.PI / 180.0f;
            float px = x + r * (float) Math.cos(theta);
            float py = y + r * (float) Math.sin(theta);
            glVertex2f(px, py);
        }
        glEnd();
        glColor4f(1, 1, 1, 1);
        GlStateManager.enableBlend();
        onStop();

    }

//    public static void drawRound(float x, float y, float r,int color){
//        onStart();
//        GlStateManager.enableBlend();
//        GLUtils.glColor(color);
//        glBegin(GL_LINE_LOOP);
//        for (int i = 0; i < 360; i++) {
//            float theta = i * (float) Math.PI / 180.0f;
//            float px = x + r * (float) Math.cos(theta);
//            float py = y + r * (float) Math.sin(theta);
//            glVertex2f(px, py);
//        }
//        glEnd();
//        glColor4f(1, 1, 1, 1);
//        GlStateManager.enableBlend();
//        onStop();
//    }
    public static void makeScissorBox(final float x, final float y, final float x2, final float y2) {
        final ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        final int factor = scaledResolution.getScaleFactor();
        glScissor((int) (x * factor), (int) ((scaledResolution.getScaledHeight() - y2) * factor), (int) ((x2 - x) * factor), (int) ((y2 - y) * factor));
    }
    //绘制圆角矩形
    public static void drawRoundRect(double d, double e, double g, double h, int color)
    {
        drawRect(d+1, e, g-1, h, color);
        drawRect(d, e+1, d+1, h-1, color);
        drawRect(d+1, e+1, d+0.5, e+0.5, color);
        drawRect(d+1, e+1, d+0.5, e+0.5, color);
        drawRect(g-1, e+1, g-0.5, e+0.5, color);
        drawRect(g-1, e+1, g, h-1, color);
        drawRect(d+1, h-1, d+0.5, h-0.5, color);
        drawRect(g-1, h-1, g-0.5, h-0.5, color);
    }
    public static void drawRoundRectWithRect(float paramXStart, float paramYStart, float paramXEnd, float paramYEnd, float radius, int color, boolean popPush)
    {
        float alpha = (color >> 24 & 0xFF) / 255.0F;
        float red = (color >> 16 & 0xFF) / 255.0F;
        float green = (color >> 8 & 0xFF) / 255.0F;
        float blue = (color & 0xFF) / 255.0F;

        float z = 0;
        if (paramXStart > paramXEnd) {
            z = paramXStart;
            paramXStart = paramXEnd;
            paramXEnd = z;
        }

        if (paramYStart > paramYEnd) {
            z = paramYStart;
            paramYStart = paramYEnd;
            paramYEnd = z;
        }

        double x1 = (double)(paramXStart + radius);
        double y1 = (double)(paramYStart + radius);
        double x2 = (double)(paramXEnd - radius);
        double y2 = (double)(paramYEnd - radius);

        if (popPush) glPushMatrix();
        glEnable(GL_BLEND);
        glDisable(GL_TEXTURE_2D);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_LINE_SMOOTH);
        glLineWidth(1);
        //LB+使用的是GL.glColor 会导致颜色窜
        GlStateManager.color(red, green, blue, alpha);
        glBegin(GL_POLYGON);

        double degree = Math.PI / 180;
        for (double i = 0; i <= 90; i += 1)
            glVertex2d(x2 + Math.sin(i * degree) * radius, y2 + Math.cos(i * degree) * radius);
        for (double i = 90; i <= 180; i += 1)
            glVertex2d(x2 + Math.sin(i * degree) * radius, y1 + Math.cos(i * degree) * radius);
        for (double i = 180; i <= 270; i += 1)
            glVertex2d(x1 + Math.sin(i * degree) * radius, y1 + Math.cos(i * degree) * radius);
        for (double i = 270; i <= 360; i += 1)
            glVertex2d(x1 + Math.sin(i * degree) * radius, y2 + Math.cos(i * degree) * radius);
        glEnd();

        glEnable(GL_TEXTURE_2D);
        glDisable(GL_BLEND);
        glDisable(GL_LINE_SMOOTH);
        if (popPush) glPopMatrix();
    }
    public static void drawRect(double x, double y, double x2, double y2, int color) {
        glEnable(GL_BLEND);
        glDisable(GL_TEXTURE_2D);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_LINE_SMOOTH);
        glPushMatrix();
        GLUtils.glColor(color);
        glBegin(GL_QUADS);
        glVertex2d(x2, y);
        glVertex2d(x, y);
        glVertex2d(x, y2);
        glVertex2d(x2, y2);
        glEnd();
        glPopMatrix();
        glEnable(GL_TEXTURE_2D);
        glDisable(GL_BLEND);
        glDisable(GL_LINE_SMOOTH);
    }
    public static void drawLine(double x1,double y1,double x2,double y2,int color){
        onStart();
        GlStateManager.enableBlend();
        GLUtils.glColor(color);
        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex2d(x1, y1);
        GL11.glVertex2d(x2, y2);
        GL11.glEnd();
        GlStateManager.disableBlend();
        onStop();
    }
}
