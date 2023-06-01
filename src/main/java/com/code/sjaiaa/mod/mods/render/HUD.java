package com.code.sjaiaa.mod.mods.render;

import com.code.sjaiaa.font.Fonts;
import com.code.sjaiaa.manager.ModuleManager;
import com.code.sjaiaa.mod.BaseMod;
import com.code.sjaiaa.mod.ModType;
import com.code.sjaiaa.util.*;
import com.code.sjaiaa.util.tabgui.TabUI;
import com.code.sjaiaa.values.BooleanValue;
import com.code.sjaiaa.values.FloatValue;
import com.code.sjaiaa.values.Mode;
import com.code.sjaiaa.values.ModeValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;


/**
 * @author sjaiaa
 * @date 2023/5/17 15:19
 * @discription
 */
public class HUD extends BaseMod {
    int defaultY = 31;
    private int selectY = 31;
    public static int yH = 12;
    private final TabUI tabUI = new TabUI();
    private final List<BaseMod> enableMod = new ArrayList<>();

    public HUD() {
        super("HUD", ModType.RENDER);
    }

    @Override
    public String modInfo() {
        return "打开HUD面板";
    }

    @Override
    public void onEnable() {
        tabUI.viewingCats = true;
        enableMod.clear();
    }

    @Override
    public void onDisable() {
        tabUI.viewingCats = false;
    }

    @Override
    public void onRender2D(RenderGameOverlayEvent event) {
        if (MinecraftInstance.getMinecraft().currentScreen != null && !(MinecraftInstance.getMinecraft().currentScreen instanceof GuiMainMenu)) return;
//        if (event.type != RenderGameOverlayEvent.ElementType.TEXT) {
//            return;
//            //35字体与minecraft字体对应关系 1 : 2
//            //如果调节字体记得改变maxLength
//        }
        ScaledResolution localScale = new ScaledResolution(MinecraftInstance.getMinecraft());
        RenderUtil.drawImage(new ResourceLocation("whitenight:arraylistshadow.png"), -2, MinecraftInstance.getMinecraft().isFullScreen() ? 50 : (int) (50 / 1.5f), 0, 0, 84, MinecraftInstance.getMinecraft().isFullScreen() ? 180 - 20: (int) (180 / 1.5f), 84, MinecraftInstance.getMinecraft().isFullScreen() ? 180 - 20: (int) (180 / 1.5f));
//        RenderUtil.drawImage(new ResourceLocation("examplemod:gish_hud.png"), 20, 10, 110, 28, 1);
        List<BaseMod> mods = ModuleManager.getMods();


        float hsize = MinecraftInstance.getMinecraft().isFullScreen() ? 1.5f : 1.2f;
        GL11.glPushMatrix();
        GL11.glScaled(hsize, hsize, 1);

        tabUI.drawUI(selectY,defaultY,yH);

        GL11.glPopMatrix();

        //绘制arraylist
        for (BaseMod mod : mods) {
            if (mod.isState()) {
                addMod(mod);
            } else {
                enableMod.remove(mod);
            }
        }
        float size = 1.5f;
        GL11.glPushMatrix();
        GL11.glScaled(size, size, 0);
        int arrayY = 0;
        enableMod.sort((o1, o2) -> Fonts.JelloFont.italicFont.getStringWidth(o2.getModID()) - Fonts.JelloFont.italicFont.getStringWidth(o1.getModID()));
            for (BaseMod baseMod : enableMod) {
//                Chat.onMessage(baseMod.getModID() + "," + String.valueOf(Fonts.JelloFont.getStringWidth(baseMod.getModID())));
                int stringWidth = Fonts.JelloFont.italicFont.getStringWidth(baseMod.getModID()) + 2;
                int target = (int) (localScale.getScaledWidth() * 2 / size/*乘放大倍数*/ - stringWidth);
                baseMod.x = AnimationUtils.flow(target, baseMod.x, 0.1f);
                RenderUtil.drawOutLine((int) (baseMod.x / 2f), arrayY, localScale.getScaledWidth(), arrayY + 3 + Fonts.JelloFont.italicFont.getHeight() / 2, ColorUtil.rainbow().getRGB());
                Fonts.JelloFont.italicFont.drawString(baseMod.getModID(), baseMod.x, arrayY * 2, ColorUtil.rainbow().getRGB());
                arrayY += yH;
            }
        GL11.glPopMatrix();
    }

    private void addMod(BaseMod mod) {
        if (!enableMod.contains(mod)) {
            mod.x = MinecraftInstance.getMinecraft().displayWidth / 2 + Fonts.JelloFont.getStringWidth(mod.getModID());
            enableMod.add(mod);
        }
    }
    @Override
    public void onKeyEvent(InputEvent.KeyInputEvent event) {
        int key = Keyboard.getEventKey();
        if (Keyboard.getEventKeyState()) {
            if (key == Keyboard.KEY_UP) {
                if(!tabUI.showModules){
                    if (selectY - yH < defaultY) {
                        selectY = defaultY + yH * (ModType.values().length - 1);
                    } else {
                        selectY -= yH;
                    }
                }
                tabUI.GuiUp();
            }
            if (key == Keyboard.KEY_DOWN) {
                if(!tabUI.showModules){
                    if (selectY + yH > defaultY + yH * (ModType.values().length - 1)) {
                        selectY = defaultY;
                    } else {
                        selectY += yH;
                    }
                }
                tabUI.GuiDown();
            }
            if (key == Keyboard.KEY_LEFT) {
                tabUI.GuiDisable();
            }
            if (key == Keyboard.KEY_RIGHT) {
                tabUI.GuiEnable();
            }
        }
    }
}