package com.code.sjaiaa.font;

import com.google.gson.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;
import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@SideOnly(Side.CLIENT)
public class Fonts {

//    @FontDetails(fontName = "Roboto Medium", fontSize = 35)
//    public static GameFontRenderer font35;

//    @FontDetails(fontName = "Roboto Medium", fontSize = 40)
//    public static GameFontRenderer font40;
//
//    @FontDetails(fontName = "Roboto Bold", fontSize = 180)
//    public static GameFontRenderer fontBold180;

    @FontDetails(fontName = "Minecraft Font")
    public static final FontRenderer minecraftFont = Minecraft.getMinecraft().fontRendererObj;

    @FontDetails(fontName = "jello",fontSize = 35)
    public static GameFontRenderer JelloFont;


    private static final List<GameFontRenderer> CUSTOM_FONT_RENDERERS = new ArrayList<>();

    public static void loadFonts() {
//        long l = System.currentTimeMillis();

//        font35 = new GameFontRenderer(getFont("font:tahoma.ttf", 35));
        JelloFont = new GameFontRenderer(getFont("font:tahoma.ttf",35));
//        font40 = new GameFontRenderer(getFont("Roboto-Medium.ttf", 40));
//        fontBold180 = new GameFontRenderer(getFont("Roboto-Bold.ttf", 180));

//        try {
//            CUSTOM_FONT_RENDERERS.clear();
//
//            final File fontsFile = new File(LiquidBounce.fileManager.fontsDir, "fonts.json");
//
//            if(fontsFile.exists()) {
//                final JsonElement jsonElement = new JsonParser().parse(new BufferedReader(new FileReader(fontsFile)));
//
//                if(jsonElement instanceof JsonNull)
//                    return;
//
//                final JsonArray jsonArray = (JsonArray) jsonElement;
//
//                for(final JsonElement element : jsonArray) {
//                    if(element instanceof JsonNull)
//                        return;
//
//                    final JsonObject fontObject = (JsonObject) element;
//
//                    CUSTOM_FONT_RENDERERS.add(new GameFontRenderer(getFont(fontObject.get("fontFile").getAsString(), fontObject.get("fontSize").getAsInt())));
//                }
//            }else{
//                fontsFile.createNewFile();
//
//                final PrintWriter printWriter = new PrintWriter(new FileWriter(fontsFile));
//                printWriter.println(new GsonBuilder().setPrettyPrinting().create().toJson(new JsonArray()));
//                printWriter.close();
//            }
//        }catch(final Exception e) {
//            e.printStackTrace();
//        }

    }


    public static FontRenderer getFontRenderer(final String name, final int size) {
        for(final Field field : Fonts.class.getDeclaredFields()) {
            try {
                field.setAccessible(true);

                final Object o = field.get(null);

                if(o instanceof FontRenderer) {
                    final FontDetails fontDetails = field.getAnnotation(FontDetails.class);

                    if(fontDetails.fontName().equals(name) && fontDetails.fontSize() == size)
                        return (FontRenderer) o;
                }
            }catch(final IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        for (final GameFontRenderer liquidFontRenderer : CUSTOM_FONT_RENDERERS) {
            final Font font = liquidFontRenderer.defaultFont.font;

            if(font.getName().equals(name) && font.getSize() == size)
                return liquidFontRenderer;
        }

        return minecraftFont;
    }

    public static Object[] getFontDetails(final FontRenderer fontRenderer) {
        for(final Field field : Fonts.class.getDeclaredFields()) {
            try {
                field.setAccessible(true);

                final Object o = field.get(null);

                if(o.equals(fontRenderer)) {
                    final FontDetails fontDetails = field.getAnnotation(FontDetails.class);

                    return new Object[] {fontDetails.fontName(), fontDetails.fontSize()};
                }
            }catch(final IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        if (fontRenderer instanceof GameFontRenderer) {
            final Font font = ((GameFontRenderer) fontRenderer).defaultFont.font;

            return new Object[] {font.getName(), font.getSize()};
        }

        return null;
    }

    public static List<FontRenderer> getFonts() {
        final List<FontRenderer> fonts = new ArrayList<>();

        for(final Field fontField : Fonts.class.getDeclaredFields()) {
            try {
                fontField.setAccessible(true);

                final Object fontObj = fontField.get(null);

                if(fontObj instanceof FontRenderer) fonts.add((FontRenderer) fontObj);
            }catch(final IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        fonts.addAll(Fonts.CUSTOM_FONT_RENDERERS);

        return fonts;
    }

    private static Font getFont(final String fontName, final int size) {
        try {
            ResourceLocation resourceLocation = new ResourceLocation(fontName);
            System.out.println("local:" + resourceLocation);
            final InputStream inputStream = getResourceInputStream(new ResourceLocation(fontName));
            Font awtClientFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            awtClientFont = awtClientFont.deriveFont(Font.PLAIN, size);
            inputStream.close();
            return awtClientFont;
        }catch(final Exception e) {
            e.printStackTrace();
            return new Font("default", Font.PLAIN, size);
        }
    }
    protected static InputStream getResourceInputStream(ResourceLocation location) throws IOException
    {
        return Minecraft.getMinecraft().getResourceManager().getResource(location).getInputStream();
    }
}