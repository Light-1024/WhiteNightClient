package com.code.sjaiaa.clickgui.element;

import com.code.sjaiaa.font.Fonts;
import com.code.sjaiaa.manager.ModuleManager;
import com.code.sjaiaa.mod.ModType;
import com.code.sjaiaa.util.*;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * @author sjaiaa
 * @date 2023/5/22 14:22
 * @discription
 */
public class ModeTypeElement extends BaseElement{
    public static int defaultSelect = 0;
    int offsetY = 0;
    public ModeTypeElement() {
        super(0, 0);
    }

    @Override
    public void drawScreen(int panelX, int panelY, int panelSizeX, int panelSizeY) {
        offsetY = 5;
        int right = panelX + panelSizeX / 4;
        int defaultY = panelY + panelSizeY / 3;
        /// TODO: 2023/5/23
//        RenderUtil.drawOutLine(panelX,defaultY,right,panelY + panelSizeY, Color.white.getRGB() );
        ModType[] values = ModType.values();
        for (int i = 0; i < values.length;i++){
//            Fonts.JelloFont.boldFont.drawString(values[i].name(),);
//            Fonts.JelloFont.boldFont.drawString(values[i].name(),panelX,defaultY + offsetY,panelX + panelSizeX / 4,defaultY + ((panelY + panelSizeY - defaultY) / 8) - 2 + offsetY,Color.white.getRGB());
            if(Utils.isFull()){
                Fonts.JelloFont.boldFont.drawString(values[i].name()  + "   " + ModuleManager.getModuleEnableSize(values[i]) + "/" + ModuleManager.getModuleAllSize(values[i]),panelX * 2 + 50,(defaultY + offsetY) * 2 + 9,defaultSelect == i? ColorUtil.rainbow().getRGB() : Color.white.getRGB());
            }else{
                Fonts.JelloFont.boldFont.drawString(values[i].name(),panelX * 2,(defaultY + offsetY) * 2,defaultSelect == i ? ColorUtil.rainbow().getRGB() : Color.white.getRGB());
            }
            offsetY += (panelY + panelSizeY - defaultY) / 8;
        }
    }

    @Override
    public void MouseClick(int panelX, int panelY, int sizeWidth, int sizeHeight, int mouseX, int mouseY, int MouseType) {
        if(mouseX > panelX && mouseX < panelX + sizeWidth / 4 && mouseY > panelY + sizeHeight / 3 && mouseY < panelY + sizeHeight){
            int defaultY = panelY + sizeHeight / 3;
            int offset = (panelY + sizeHeight - defaultY) / 8;
            int v1 = (mouseY - (panelY + sizeHeight / 3)) / offset;
            defaultSelect = Math.min(v1, 7);
        }
    }
}
