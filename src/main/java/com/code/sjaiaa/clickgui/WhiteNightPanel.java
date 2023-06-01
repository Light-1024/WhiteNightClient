package com.code.sjaiaa.clickgui;

import com.code.sjaiaa.clickgui.element.BaseElement;
import com.code.sjaiaa.clickgui.element.InfoElement;
import com.code.sjaiaa.clickgui.element.ModeTypeElement;
import com.code.sjaiaa.clickgui.element.ModuleElement;
import com.code.sjaiaa.util.MinecraftInstance;
import com.code.sjaiaa.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sjaiaa
 * @date 2023/5/22 11:11
 * @discription
 */
public class WhiteNightPanel extends GuiScreen {
    public List<BaseElement> elements = new ArrayList<>();
    int sizeWidth = Minecraft.getMinecraft().displayWidth / 3;
    int sizeHeight =((Minecraft.getMinecraft().displayHeight) / 3) + 5;
    public int x = (MinecraftInstance.getMinecraft().displayWidth) / 4 - sizeWidth / 2;
    public int y = (MinecraftInstance.getMinecraft().displayHeight) / 4 - sizeHeight / 2;
    public int lastMouseX;
    public int lastMouseY;
    public WhiteNightPanel(){
        if(this.elements.isEmpty()){
            this.elements.add(new InfoElement());
            this.elements.add(new ModeTypeElement());
            this.elements.add(new ModuleElement());
        }
    }
    //绘制面板
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        //display / 2才是大小
//        RenderUtil.drawRoundOutLine(((MinecraftInstance.getMinecraft().displayWidth / 4f) ),MinecraftInstance.getMinecraft().displayHeight / 4 ,20,Color.white.getRGB());
//        RenderUtil.drawRoundRect(this.x,this.y,this.x + sizeWidth,this.y + sizeHeight, ColorUtil.rainbow().getRGB());
        Color color = new Color(0, 0, 0, 0.6f);
//        Chat.onMessage(String.valueOf(x));
        sizeWidth = Minecraft.getMinecraft().displayWidth / 3;
        sizeHeight = ((Minecraft.getMinecraft().displayHeight) / 3) + 5;

        //删掉变玻璃效果
//        RenderUtil.drawRoundRect(x,y,x + sizeWidth,y + sizeHeight,color.getRGB());
        RenderUtil.drawRoundRectWithRect(x,y,x + sizeWidth,y + sizeHeight,15,color.getRGB(),true);
        RenderUtil.drawLine(x,y + sizeHeight / 3, x+ sizeWidth/4,y + sizeHeight / 3,Color.white.getRGB() );
        RenderUtil.drawLine(x+ sizeWidth/4,y , x+ sizeWidth/4,y + sizeHeight,Color.white.getRGB() );
        this.lastMouseX = mouseX;
        this.lastMouseY = mouseY;

        for (BaseElement element : elements) {
//            GL11.glPushMatrix();
//            GL11.glEnable(GL11.GL_SCISSOR_TEST);
//            GL11.glScissor(element.x, element.y, element.width, element.height);
            element.drawScreen(this.x,this.y,this.sizeWidth,this.sizeHeight);
//            GL11.glDisable(GL11.GL_SCISSOR_TEST);
//            GL11.glPopMatrix();
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for (BaseElement element : elements) {
            element.MouseClick(this.x,this.y,this.sizeWidth,this.sizeHeight,mouseX,mouseY,mouseButton);
        }
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        if(clickedMouseButton == 0){
            this.x = (int) (x + (this.lastMouseX - mouseX) * 0.35f);
            this.y = (int) (y + (this.lastMouseY - mouseY) * 0.35f);
        }
    }
}
