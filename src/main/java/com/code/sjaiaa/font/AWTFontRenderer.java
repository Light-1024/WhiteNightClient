package com.code.sjaiaa.font;

import com.code.sjaiaa.util.MinecraftInstance;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

/**
 * @author sjaiaa
 * @date 2023/5/18 16:16
 * @discription
 */
public class AWTFontRenderer {
    static boolean assumeNonVolatile = false;
    //val font: Font, startChar: Int = 0, stopChar: Int = 255
    Font font;
    int startChar = 0;
    int stopChar = 0;
    static List<AWTFontRenderer> activeFontRenderers = new ArrayList();
    static int gcTicks = 0;
    private static final int GC_TICKS = 600;
    private static final int CACHED_FONT_REMOVAL_TIME = 30000;
    private Map<String, CachedFont> cachedStrings = new HashMap<>();

    public AWTFontRenderer(Font font){
        this(font,0,255);
    }

    public AWTFontRenderer(Font font, int startChar, int stopChar) {
        this.font = font;
        this.startChar = startChar;
        this.stopChar = stopChar;
        renderBitmap(startChar, stopChar);
        activeFontRenderers.add(this);
    }

    public static void garbageCollectionTick() {
        if (gcTicks++ > GC_TICKS) {
            for (AWTFontRenderer fontRenderer : activeFontRenderers) {
                fontRenderer.collectGarbage();
            }
            gcTicks = 0;
        }
    }

    private void collectGarbage() {
        long currentTime = System.currentTimeMillis();

        Iterator<Map.Entry<String, CachedFont>> it = cachedStrings.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, CachedFont> entry = it.next();
            CachedFont cachedFont = entry.getValue();

            if (currentTime - cachedFont.getLastUsage() > CACHED_FONT_REMOVAL_TIME) {
                GL11.glDeleteLists(cachedFont.getDisplayList(), 1);
                cachedFont.setDeleted(true);
                it.remove();
            }
        }
    }

    private int fontHeight = -1;
    private CharLocation[] charLocations = new CharLocation[stopChar];
    private int textureID = 0;
    private int textureWidth = 0;
    private int textureHeight = 0;
    int height = getHeight();

    public int getHeight() {
        return (fontHeight - 8) / 2;
    }

    /**
     * Allows you to draw a string with the target font
     *
     * @param text  to render
     * @param x     location for target position
     * @param y     location for target position
     * @param color of the text
     */
    public void drawString( String text, double x,double y,int color) {
        float scale = 0.25F;
        float reverse = 1 / scale;

        GlStateManager.pushMatrix();
        GlStateManager.scale(scale, scale, scale);
        GL11.glTranslated(x * 2F, y * 2.0 - 2.0, 0.0);
        GlStateManager.bindTexture(textureID);
        //消除黑边
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);

        float red = ((color >> 16) & 0xff) / 255f;
        float green = ((color >> 8) & 0xff) / 255f;
        float blue = ((color) & 0xff) / 255f;
        float alpha = ((color >> 24) & 0xff) / 255f;

        GlStateManager.color(red, green, blue, alpha);

        float currX = 0.0F;

        CachedFont cached = cachedStrings.get(text);

        if (cached != null) {
            GL11.glCallList(cached.getDisplayList());
            cached.lastUsage = System.currentTimeMillis();
            GlStateManager.popMatrix();
            return;
        }

        int list = -1;

        if (assumeNonVolatile) {
            list = GL11.glGenLists(1);

            GL11.glNewList(list, GL11.GL_COMPILE_AND_EXECUTE);
        }

        GL11.glBegin(GL11.GL_QUADS);

        for (char c : text.toCharArray()) {
            if (c >= charLocations.length) {
                GL11.glEnd();

                // Ugly solution, because floating point numbers, but I think that shouldn't be that much of a problem
                GlStateManager.scale(reverse, reverse, reverse);
                MinecraftInstance.getMinecraft().fontRendererObj.drawString("$char", currX * scale + 1, 2f, color, false);
                currX += MinecraftInstance.getMinecraft().fontRendererObj.getStringWidth("$char") * reverse;

                GlStateManager.scale(scale, scale, scale);

                GlStateManager.bindTexture(textureID);

                GlStateManager.color(red, green, blue, alpha);

                GL11.glBegin(GL11.GL_QUADS);
            } else {
                CharLocation fontChar = charLocations[c];
                if (fontChar != null) {
                    drawChar(fontChar, (float) currX, 0f);
                    currX += fontChar.getWidth() - 8.0;
                }
            }
        }

        GL11.glEnd();

        if (assumeNonVolatile) {
            cachedStrings.put(text,new CachedFont(list, System.currentTimeMillis()));
            GL11.glEndList();
        }

        GlStateManager.popMatrix();
    }


    private void drawChar( CharLocation c, float x, float y) {
        float width = c.getWidth();
        float height = c.getHeight();
        float srcX = c.getX();
        float srcY = c.getY();
        float renderX = srcX / textureWidth;
        float renderY = srcY / textureHeight;
        float renderWidth = width / textureWidth;
        float renderHeight = height / textureHeight;

        GL11.glTexCoord2f(renderX, renderY);
        GL11.glVertex2f(x, y);
        GL11.glTexCoord2f(renderX, renderY + renderHeight);
        GL11.glVertex2f(x, y + height);
        GL11.glTexCoord2f(renderX + renderWidth, renderY + renderHeight);
        GL11.glVertex2f(x + width, y + height);
        GL11.glTexCoord2f(renderX + renderWidth, renderY);
        GL11.glVertex2f(x + width, y);
    }

    private void renderBitmap(int startChar, int stopChar) {
        charLocations = new CharLocation[stopChar];
        BufferedImage[] fontImages = new BufferedImage[stopChar];
                int rowHeight = 0;
        int charX = 0;
        int charY = 0;

        for (int targetChar = startChar; targetChar < stopChar; targetChar++) {
            BufferedImage fontImage = drawCharToImage((char) targetChar);
            CharLocation fontChar = new CharLocation(charX, charY, fontImage.getWidth(), fontImage.getHeight());

            if (fontChar.getHeight() > fontHeight)
                fontHeight = fontChar.getHeight();
            if (fontChar.getHeight() > rowHeight)
                rowHeight = fontChar.getHeight();


            charLocations[targetChar] = fontChar;
            fontImages[targetChar] = fontImage;

            charX += fontChar.getWidth();

            if (charX > 2048) {
                if (charX > textureWidth)
                    textureWidth = charX;

                charX = 0;
                charY += rowHeight;
                rowHeight = 0;
            }
        }
        textureHeight = charY + rowHeight;
        BufferedImage bufferedImage = new BufferedImage(textureWidth, textureHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = (Graphics2D) bufferedImage.getGraphics();
        graphics2D.setFont(font);
        graphics2D.setColor(new Color(255, 255, 255, 0));
        graphics2D.fillRect(0, 0, textureWidth, textureHeight);
        graphics2D.setColor(Color.white);

        for (int targetChar = startChar; targetChar < stopChar; targetChar++) {
            if (fontImages[targetChar] != null && charLocations[targetChar] != null) {
                graphics2D.drawImage(fontImages[targetChar], charLocations[targetChar].getX(), charLocations[targetChar].getY(), null);
            }
        }

        textureID = TextureUtil.uploadTextureImageAllocate(TextureUtil.glGenTextures(), bufferedImage, true, true);

    }
    private BufferedImage drawCharToImage(char ch) {
        Graphics2D graphics2D = (Graphics2D) new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).getGraphics();

        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics2D.setFont(font);

        FontMetrics fontMetrics = graphics2D.getFontMetrics();

        int charWidth = fontMetrics.charWidth(ch) + 8;
        if (charWidth <= 0)
            charWidth = 7;

        int charHeight = fontMetrics.getHeight() + 3;
        if (charHeight <= 0)
            charHeight = font.getSize();

        BufferedImage fontImage = new BufferedImage(charWidth, charHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = (Graphics2D)fontImage.getGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.setFont(font);
        graphics.setColor(Color.WHITE);
        graphics.drawString(String.valueOf(ch), 3, 1 + fontMetrics.getAscent());

        return fontImage;
    }

    public int getStringWidth(String text) {
        int width = 0;

        for (char c : text.toCharArray()) {
            CharLocation fontChar = charLocations[
                    c < charLocations.length ? c : (char) 3
                    ];

            if (fontChar != null) {
                width += fontChar.getWidth() - 8;
            }
        }

        return width / 2;
    }
}
