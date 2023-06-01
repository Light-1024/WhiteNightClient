package com.code.sjaiaa.util.tabgui;

import com.code.sjaiaa.font.Fonts;
import com.code.sjaiaa.manager.ModuleManager;
import com.code.sjaiaa.mod.BaseMod;
import com.code.sjaiaa.mod.ModType;
import com.code.sjaiaa.mod.mods.render.HUD;
import com.code.sjaiaa.util.AnimationUtils;
import com.code.sjaiaa.util.ColorUtil;
import com.code.sjaiaa.util.RenderUtil;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

/**
 * @author sjaiaa
 * @date 2023/5/19 15:13
 * @discription 绘制面板
 */
public class TabUI {
    private List<ModType> types = Arrays.asList(ModType.values());
    int offset = 5;
    int nowY = 0;
    int maxLength;
    public int currentCategory;
    public boolean viewingCats = true;
    public boolean showModules;
    int selectedIndex;
    public int modMaxLength = 0;
    int selectMax = ModType.values().length;
    public void drawUI(int selectY,int defaultY,int yH){
        int y = 30;
        for (ModType value : ModType.values()) {
            int stringWidth = Fonts.JelloFont.getStringWidth(value.name()) + offset;
            if (maxLength < stringWidth) {
                maxLength = stringWidth;
            }
            char[] chars = value.name().toLowerCase().toCharArray();
            chars[0] = String.valueOf(chars[0]).toUpperCase().charAt(0);

            Fonts.JelloFont.boldFont.drawString(String.valueOf(chars), offset, (y ) * 2 + 4, ColorUtil.rainbow().getRGB());
            Fonts.JelloFont.boldFont.drawString(">", maxLength * 2 - offset, (y ) * 2 + 4, Color.WHITE.getRGB());
            y += yH;
        }
        RenderUtil.drawOutLine(0, defaultY, maxLength + 5, y + 2, ColorUtil.rainbow().getRGB());
        //绘制内容
        int color2 = (75 << 24) | 255 << 16 | 255 << 8 | 255;
        RenderUtil.drawRect(0, defaultY, maxLength + 5, y + 2, color2);
//        SlowMove(selectY, 0.1f);
        if(!showModules){
            nowY = AnimationUtils.flow(selectY, nowY, 0.1f);
        }
        RenderUtil.drawImage(new ResourceLocation("whitenight:TabGUISelector.png"), 0,nowY, 0, 0, maxLength + 5, 14, 150, 34);
        if(showModules){
            drawMods();
        }
    }
    public void GuiUp(){
        if(viewingCats){
            if(selectedIndex <= 0){
                selectedIndex = selectMax;
                return;
            }
            selectedIndex--;
        }
    }
    public void GuiDown(){
        if(viewingCats){
            if(selectedIndex >= selectMax){
                selectedIndex = 0;
                return;
            }
            selectedIndex++;
        }
    }
    public void GuiDisable(){
        if(viewingCats){
            if(showModules){
                showModules = false;
                selectedIndex = currentCategory;
                selectMax = types.size() - 1;
            }
        }
    }
    public void GuiEnable(){
        if(viewingCats){
            //如果没有开启mods
            if(!showModules){
                //把当前select赋值给current储存
                currentCategory = selectedIndex;
                List<BaseMod> modulesInCategory = ModuleManager.getModulesInCategory(types.get(selectedIndex));
                //如果列表是空的
                if(modulesInCategory.isEmpty()){
                    selectMax = types.size() - 1;
                    showModules = false;
                    return;
                }
                //否则重新触发赋值
                selectMax = modulesInCategory.size() - 1;
                selectedIndex = 0;
                showModules = true;
            }else {
                //如果当前选择的current大小不是0
                if(ModuleManager.getModulesInCategory(types.get(currentCategory)).size() != 0){
                    //通过序号获取mod
                    //获取所有mod
                    List<BaseMod> modulesInCategory = ModuleManager.getModulesInCategory(types.get(currentCategory));

                    //获取我当前选择的那个Mod
                    BaseMod baseMod = modulesInCategory.get(selectedIndex);
                    //开启Mod
                    baseMod.setState(!baseMod.isState());
                }
            }
        }
    }

    public void drawMods(){
        int y = 0;
        if(!ModuleManager.getModulesInCategory(types.get(currentCategory)).isEmpty()){
            List<BaseMod> modulesInCategory = ModuleManager.getModulesInCategory(types.get(currentCategory));

            for (BaseMod baseMod : modulesInCategory) {
                modMaxLength = Fonts.JelloFont.getStringWidth(baseMod.getModID()) + offset;
                // * 放大倍数
                Fonts.JelloFont.boldFont.drawString(baseMod.getModID(),(maxLength + offset) * 2,2 * nowY + (y ) * 2 + 4,Color.WHITE.getRGB());
//                RenderUtil.drawImage(new ResourceLocation("examplemod:TabGUISelector.png"), (maxLength + offset),2 * nowY + (y + 2) * 2 / 2 + 4, 0, 0, modMaxLength + 5, 14, 150, 34);
                y += HUD.yH;
            }
            RenderUtil.drawOutLine((maxLength + offset),nowY,(maxLength + offset) + modMaxLength * 2 + 2, nowY + (y + 2),Color.WHITE.getRGB());
            RenderUtil.drawImage(new ResourceLocation("whitenight:TabGUISelector.png"), (maxLength + offset),(selectedIndex * HUD.yH) + nowY, 0, 0, modMaxLength * 2 + 2, 14, 150, 34);
        }
    }
}
