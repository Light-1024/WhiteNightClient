package com.code.sjaiaa.clickgui;

import com.code.sjaiaa.util.ColorUtil;
import com.code.sjaiaa.util.RenderUtil;
import net.minecraft.client.gui.GuiScreen;

import java.awt.*;
import java.io.IOException;

/**
 * @author sjaiaa
 * @date 2023/5/17 15:18
 * @discription
 */
public class WhiteNightClickGui extends GuiScreen {
    public static WhiteNightPanel panel = new WhiteNightPanel();

    //alpha越大越不透明
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        //绘制半透明底板
        int color = ColorUtil.rainbowAlpha(Color.white.getRGB()).getRGB();
        RenderUtil.drawRect(0, 0, this.width, this.height, color);
        panel.drawScreen(mouseX,mouseY,partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if(isInPanel(mouseX,mouseY)){
            panel.mouseClicked(mouseX,mouseY,mouseButton);
        }
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        //判断鼠标是否在范围内
        if(this.isInPanel(mouseX,mouseY)){
            panel.mouseClickMove(mouseX,mouseY,clickedMouseButton,timeSinceLastClick);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }
    public boolean isInPanel(int mouseX,int mouseY){
        if(mouseX >= panel.x && mouseY > panel.y && mouseX <= panel.x + panel.sizeWidth && mouseY <= panel.y + panel.sizeHeight){
            return true;
        }
        return false;
    }
}
