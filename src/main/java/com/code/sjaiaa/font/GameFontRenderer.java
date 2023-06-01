package com.code.sjaiaa.font;

import com.code.sjaiaa.util.ColorUtil;
import com.code.sjaiaa.util.MinecraftInstance;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static org.lwjgl.opengl.GL20.glUseProgram;

/**
 * @author sjaiaa
 * @date 2023/5/18 16:13
 * @discription
 */
public class GameFontRenderer extends FontRenderer {
    private Font font;
    static int height;
    int size;
    AWTFontRenderer defaultFont;
    public AWTFontRenderer boldFont;
    public AWTFontRenderer italicFont;
    public AWTFontRenderer boldItalicFont;

    public GameFontRenderer(Font font) {
        super(MinecraftInstance.getMinecraft().gameSettings, new ResourceLocation("textures/font/ascii.png"), MinecraftInstance.getMinecraft().getTextureManager(), false);
        FONT_HEIGHT = height;
        this.font = font;
        defaultFont = new AWTFontRenderer(font);
        boldFont = new AWTFontRenderer(font.deriveFont(Font.BOLD));
        italicFont = new AWTFontRenderer(font.deriveFont(Font.ITALIC));
        boldItalicFont = new AWTFontRenderer(font.deriveFont(Font.BOLD | Font.ITALIC));
    }

    public int getHeight() {
        return defaultFont.height / 2;
    }

    public int getSize() {
        return defaultFont.font.getSize();
    }
    public int drawString(String s, float x, float y, int color) {
        return drawString(s, x, y, color, false);
    }
    public int drawCenteredString(String s,float x,float y,int color,boolean shadow){
        return drawString(s, x - getStringWidth(s) / 2F, y, color, shadow);
    }
    public int drawCenteredString(String s,float x,float y,int color){
        return drawStringWithShadow(s, x - getStringWidth(s) / 2F, y, color);
    }

    @Override
    public int drawStringWithShadow(String text, float x, float y, int color) {
        return drawString(text, x, y, color, true);
    }

    @Override
    public int drawString(String text, float x, float y, int color, boolean dropShadow) {
        String currentText = text;
//        val event = TextEvent(currentText)
//        LiquidBounce.eventManager.callEvent(event)
//        currentText = event.text ?: return 0
        float currY = y - 3F;
//        val rainbow = RainbowFontShader.INSTANCE.isInUse
//
//        if (shadow) {
//            glUseProgram(0)
//
//            drawText(currentText, x + 1f, currY + 1f, Color(0, 0, 0, 150).rgb, true)
//        }
        return super.drawString(currentText, x, currY, color, dropShadow);
    }
    private int drawText(String text, float x, float y, int color, boolean ignoreColor, boolean rainbow) {
        if (text == null) {
            return 0;
        }
        if (text.isEmpty()) {
            return (int) x;
        }

//        int rainbowShaderId = RainbowFontShader.INSTANCE.getProgramId();

//        if (rainbow) {
//            glUseProgram(rainbowShaderId);
//        }

        GlStateManager.translate(x - 1.5, y + 0.5, 0.0);
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
        GlStateManager.enableTexture2D();

        int currentColor = color;

        if ((currentColor & -0x4000000) == 0) {
            currentColor |= -16777216;
        }

        int defaultColor = currentColor;

        int alpha = (currentColor >> 24 & 0xff);

        if (text.contains("§")) {
            String[] parts = text.split("§");

            AWTFontRenderer currentFont = defaultFont;

            double width = 0.0;

            // Color code states
            boolean randomCase = false;
            boolean bold = false;
            boolean italic = false;
            boolean strikeThrough = false;
            boolean underline = false;

            for (int index = 0; index < parts.length; index++) {
                String part = parts[index];

                if (part.isEmpty()) {
                    continue;
                }

                if (index == 0) {
                    currentFont.drawString(part, width, 0.0, currentColor);
                    width += currentFont.getStringWidth(part);
                } else {
                    String words = part.substring(1);
                    char type = part.charAt(0);

                    int colorIndex = getColorIndex(type);

                    if (colorIndex >= 0 && colorIndex <= 15) {
                        if (!ignoreColor) {
                            currentColor = ColorUtil.hexColors[colorIndex] | (alpha << 24);

                            if (rainbow) {
                                glUseProgram(0);
                            }
                        }

                        bold = false;
                        italic = false;
                        randomCase = false;
                        underline = false;
                        strikeThrough = false;
                    } else if (colorIndex == 16) {
                        randomCase = true;
                    } else if (colorIndex == 17) {
                        bold = true;
                    } else if (colorIndex == 18) {
                        strikeThrough = true;
                    } else if (colorIndex == 19) {
                        underline = true;
                    } else if (colorIndex == 20) {
                        italic = true;
                    } else if (colorIndex == 21) {
                        currentColor = color;

                        if ((currentColor & -67108864) == 0) {
                            currentColor |= -16777216;
                        }

//                        if (rainbow) {
//                            glUseProgram(rainbowShaderId);
//                        }

                        bold = false;
                        italic = false;
                        randomCase = false;
                        underline = false;
                        strikeThrough = false;
                    }

                    if (bold && italic) {
                        currentFont = boldItalicFont;
                    } else if (bold) {
                        currentFont = boldFont;
                    } else if (italic) {
                        currentFont = italicFont;
                    } else {
                        currentFont = defaultFont;
                    }

                    currentFont.drawString(words, width, 0.0, currentColor);

//                    if (strikeThrough) {
//                        RenderUtils.drawLine(width / 2.0 + 1, currentFont.height / 3.0,
//                                (width + currentFont.getStringWidth(words)) / 2.0 + 1, currentFont.height / 3.0,
//                                FONT_HEIGHT / 16F);
//                    }
//
//                    if (underline) {
//                        RenderUtils.drawLine(width / 2.0 + 1, currentFont.height / 2.0,
//                                (width + currentFont.getStringWidth(words)) / 2.0 + 1, currentFont.height / 2.0,
//                                FONT_HEIGHT / 16F);
//                    }

                    width += currentFont.getStringWidth(words);
                }
            }
        } else {
            defaultFont.drawString(text, 0.0, 0.0, currentColor);
        }

        GlStateManager.disableBlend();
        GlStateManager.translate(-(x - 1.5), -(y + 0.5), 0.0);
        GlStateManager.color(1f, 1f, 1f, 1f);

        return (int) (x + getStringWidth(text));
    }

    @Override
    public int getColorCode(char character) {
        return ColorUtil.hexColors[getColorIndex(character)];
    }

    @Override
    public int getStringWidth(String text) {
        String currentText = text;

//        TextEvent event = new TextEvent(currentText);
//        LiquidBounce.eventManager.callEvent(event);
//        currentText = event.getText() != null ? event.getText() : "";

        if (currentText.contains("§")) {
            String[] parts = currentText.split("§");

            AWTFontRenderer currentFont = defaultFont;
            int width = 0;
            boolean bold = false;
            boolean italic = false;

            for (int i = 0; i < parts.length; i++) {
                String part = parts[i];

                if (part.isEmpty()) {
                    continue;
                }

                if (i == 0) {
                    width += currentFont.getStringWidth(part);
                } else {
                    String words = part.substring(1);
                    char type = part.charAt(0);
                    int colorIndex = getColorIndex(type);

                    if (colorIndex < 16) {
                        bold = false;
                        italic = false;
                    } else if (colorIndex == 17) {
                        bold = true;
                    } else if (colorIndex == 20) {
                        italic = true;
                    } else if (colorIndex == 21) {
                        bold = false;
                        italic = false;
                    }

                    currentFont = bold && italic ? boldItalicFont :
                            bold ? boldFont :
                                    italic ? italicFont :
                                            defaultFont;

                    width += currentFont.getStringWidth(words);
                }
            }

            return width / 2;
        } else {
            return defaultFont.getStringWidth(currentText) / 2;
        }
    }

    @Override
    public int getCharWidth(char character) {
        return getStringWidth(String.valueOf(character));
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {

    }

    @Override
    protected void bindTexture(ResourceLocation location) {

    }

    public static int getColorIndex(char type) {
        if (type >= '0' && type <= '9') {
            return type - '0';
        } else if (type >= 'a' && type <= 'f') {
            return type - 'a' + 10;
        } else if (type >= 'k' && type <= 'o') {
            return type - 'k' + 16;
        } else if (type == 'r') {
            return 21;
        } else {
            return -1;
        }
    }

}
