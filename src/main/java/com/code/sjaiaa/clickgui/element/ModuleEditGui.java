package com.code.sjaiaa.clickgui.element;

import com.code.sjaiaa.clickgui.WhiteNightClickGui;
import com.code.sjaiaa.font.Fonts;
import com.code.sjaiaa.mod.BaseMod;
import com.code.sjaiaa.util.*;
import com.code.sjaiaa.values.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;

/**
 * @author sjaiaa
 * @date 2023/5/22 21:54
 * @discription
 */
public class ModuleEditGui extends GuiScreen {
    int offset;

    public BaseMod mod;
    int scrollAmmount = 0;
    int sizeWidth = Minecraft.getMinecraft().displayWidth / 3;
    int sizeHeight = ((Minecraft.getMinecraft().displayHeight) / 3) + 5;
    public int x = (MinecraftInstance.getMinecraft().displayWidth) / 4 - sizeWidth / 2;
    public int y = (MinecraftInstance.getMinecraft().displayHeight) / 4 - sizeHeight / 2;

    public ModuleEditGui(BaseMod mod) {
        this.mod = mod;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
//        RenderUtil.drawRect();
//        RenderUtil.drawOutLine();
        int i1 = Mouse.getDWheel() / 5;
        sizeWidth = Minecraft.getMinecraft().displayWidth / 3;
        sizeHeight =((Minecraft.getMinecraft().displayHeight) / 3) + 5;
        x = (MinecraftInstance.getMinecraft().displayWidth) / 4 - sizeWidth / 2;
        y = (MinecraftInstance.getMinecraft().displayHeight) / 4 - sizeHeight / 2;

        if(i1 != 0){
            if(scrollAmmount < 0 && i1 > 0){
                this.scrollAmmount += i1;
            }else{
                if(this.scrollAmmount > 0){
                    this.scrollAmmount = 0;
                }else if(Math.abs(this.scrollAmmount) < getMaxScroll(sizeHeight) + 20){
                    this.scrollAmmount += i1;
                }
            }
        }

        int color = ColorUtil.rainbowAlpha(Color.white.getRGB()).getRGB();
        RenderUtil.drawRect(this.x,this.y,this.x + sizeWidth,this.y + sizeHeight, color);
        RenderUtil.drawOutLine(this.x,this.y,this.x + sizeWidth,this.y + sizeHeight, ColorUtil.rainbow().getRGB());
        BaseValue<?>[] modes = mod.getModes();
        int offset = 0;
        RenderUtil.startGlScissor(this.x,this.y,sizeWidth,sizeHeight);
        for (int i = 0;i < modes.length;i++) {
            int size = 2;
//            if(Utils.isFull()){
//                size = 2;
//            }
            BaseValue mode = modes[i];
            int leftX = this.x + 10;
            int topY = y + 10 + i * 50 + scrollAmmount + offset;
            int rightX = this.x + sizeWidth - 10;
            int bottomY = y + 50 + i * 50 + scrollAmmount + offset;
            int textOffsetY = 10;
            RenderUtil.drawOutLine(leftX,topY,rightX ,bottomY , Color.WHITE.getRGB());
            if(mode instanceof BooleanValue){
                int sizeWidth = Minecraft.getMinecraft().displayWidth / 3;
                int sizeHeight =((Minecraft.getMinecraft().displayHeight) / 3) + 5;
                int max = x + sizeWidth - 10 - sizeWidth / 15;
                int min = x + sizeWidth - 10 - sizeWidth / 10;
                float top = y + 10 + i * 50 + scrollAmmount + 40 / 3f;
                float right = x + sizeWidth - 15;
                float bottom = y  + i * 50 + scrollAmmount + 35;
                int X = Utils.isFull() ? max: min;
//                RenderUtil.drawString(mode.getBaseName(), leftX,topY,Color.RED.getRGB());
                RenderUtil.drawRoundRectWithRect(X,(int)top + 2,(int)right ,(int)bottom - 2,3,Color.white.getRGB(),true);
                //阴影面积

                if (!((BooleanValue) mode).getValue()){
                    RenderUtil.drawRoundRectWithRect(X,top ,(right + X) / 2 - (Utils.isFull() ? 10:0),bottom,3,Color.lightGray.getRGB(),true);
                }else{
                    RenderUtil.drawRoundRectWithRect((right + X) / 2 + (Utils.isFull() ? 10:0),top ,right,bottom ,3,Color.green.getRGB(),true);
                }

                GL11.glPushMatrix();
                GL11.glScalef(size,size,1);
                GL11.glColor4f(1,1,1,1);
                String s = mode.getBaseName();
                int stringWidth = Fonts.JelloFont.boldFont.getStringWidth(s);
                Fonts.JelloFont.boldFont.drawString(s,leftX ,topY + textOffsetY,Color.white.getRGB());
                if(Utils.isFull()){
                    Fonts.minecraftFont.drawString(mode.getInfo(),(leftX / 2 + stringWidth),topY / 2 + textOffsetY / 2,Color.white.getRGB());
                }
                GL11.glPopMatrix();
            }
            if(mode instanceof FloatValue){
                //浮点数百分比计算有点问题位置对应不上

//                RenderUtil.drawString(mode.getBaseName(), leftX,topY,Color.RED.getRGB());
                GL11.glPushMatrix();
                GL11.glScalef(size,size,1);
                String s = mode.getBaseName() + "  value:" + mode.getValue();
                int stringWidth = Fonts.JelloFont.boldFont.getStringWidth(s);
                Fonts.JelloFont.boldFont.drawString( s,leftX,topY + textOffsetY,Color.white.getRGB());
                if (Utils.isFull()){
                    Fonts.minecraftFont.drawString(mode.getInfo(), (int) ((leftX) / 2 + stringWidth / 1.5f),(topY + textOffsetY) / 2 + 2,Color.white.getRGB());
                }


                GL11.glPopMatrix();

                RenderUtil.drawOutLine(rightX - 110,topY + 15,rightX - 10,bottomY - 15,Color.white.getRGB());
                float value = (float) mode.getValue();
                float max = ((FloatValue) mode).getMax();
                float mappedValue = (value - ((FloatValue) mode).getMin()) / (max - ((FloatValue) mode).getMin());
                RenderUtil.drawRoundRectWithRect(rightX - 110 + 100 * mappedValue - 10, topY + 10, rightX - 110 + 100 * mappedValue + 10,topY + 10 + 20,3, Color.white.getRGB(),true);
//                Chat.onMessage(String.valueOf(value));
//                RenderUtil.drawRoundOutLine((float) (rightX - 110 + 100 * (double)mappedValue),topY + 20,10,Color.white.getRGB());
//                float value = (float) mode.getValue();
//                float max = ((FloatValue) mode).getMax();
////                RenderUtil.drawRoundOutLine(rightX - 120 ,topY + 10,rightX - 110 + 10 ,bottomY - 10,Color.white.getRGB());
////                Chat.onMessage("value" + String.valueOf((value / max)));
//                RenderUtil.drawRoundOutLine((float) (rightX - 110 + 100 * (double)(value / max)),topY + 20,10,Color.white.getRGB());
            }
            if (mode instanceof ModeValue){
                GL11.glPushMatrix();
                GL11.glScalef(size,size,1);
                String info = mode.getBaseName() + "[" + ((ModeValue) mode).getValue().getModeName() + "]";
                int stringWidth = Fonts.JelloFont.boldFont.getStringWidth(info);
                Fonts.JelloFont.boldFont.drawString( info,leftX,topY + textOffsetY ,Color.white.getRGB());
                if (Utils.isFull()){
                    Fonts.minecraftFont.drawString(mode.getInfo() + "     [Mod介绍]:" + ((ModeValue)mode).getValue().getModinfo(),leftX / 2 + stringWidth,topY / 2 + textOffsetY / 2 + 2,Color.white.getRGB());
                }

                GL11.glPopMatrix();
//                RenderUtil.drawRoundRectWithRect(10,10,100,100,Color.white.getRGB());
//                int i2 = ((ModeValue) mode).getModes().length * 12;
//                RenderUtil.drawOutLine(leftX,topY,rightX ,bottomY + i2, Color.WHITE.getRGB());
//                Fonts.JelloFont.boldFont.drawString(mode.getBaseName(),leftX * 2,topY * 2,Color.white.getRGB());
////                RenderUtil.drawString(mode.getBaseName(), leftX,topY,Color.RED.getRGB());
//                Mode[] modes1 = ((ModeValue) mode).getModes();
//                for (int j = 0; j<modes1.length; j++) {
//                    Fonts.JelloFont.boldFont.drawString(modes1[j].getModeName(),leftX * 2,topY * 2 + (j + 1) * 50,Color.white.getRGB());
//                }
//                offset += i2;
            }
            modes[i].y = y + 10 + i * 50 + scrollAmmount;
        }
        RenderUtil.stopGlScissor();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        int left = this.x + 10;
        int rightX = this.x + sizeWidth - 10;
        int top = this.y + 10;

        BaseValue<?>[] modes = mod.getModes();
        for (int i = 0; i < modes.length;i++) {
            if(mouseX > left && mouseX < rightX && mouseY > modes[i].y  && mouseY < modes[i].y + 40 ){

                if(modes[i] instanceof BooleanValue){
                    BooleanValue mode = (BooleanValue) modes[i];
                    mode.setValue(!mode.getValue());
                }
                if (modes[i] instanceof ModeValue){
                    ModeValue mode = (ModeValue) modes[i];
                    mode.setNextValue();
                }
                if (modes[i] instanceof FloatValue){
                    FloatValue mode = (FloatValue)modes[i];
                    //判断框的拉动
                    if(mouseX > rightX - 110 && mouseX < rightX - 10 && mouseY > modes[i].y + 10  && mouseY < modes[i].y - 20 + 50){
//                        //计算当前位置偏移
//                        int i1 = mouseX - (rightX - 110);
//                        //计算最大值百分比
//                        float v = i1 / 100f;
//                        //获取当前百分比值
//                        float v2 = v * mode.getMax();
//                        //获取当前值
//                        float result = Float.parseFloat(String.format("%.2f", v2));
//                        float min = Float.parseFloat(String.format("%.2f", mode.getMax() * 0.01));
//                        float max = Float.parseFloat(String.format("%.2f", mode.getMax() * 0.95));
//                        if(result <= min){
//                            result = 0f;
//                        }
//                        if(result >= max){
//                            result = mode.getMax();
//                        }
//                        mode.setValue(result);
                        setValue(mode,mouseX);
                    }
                }
                break;
            }
        }
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        //负责设置Float int类型变量
        int left = this.x + 10;
        int rightX = this.x + sizeWidth - 10;
        int top = this.y + 10;
        BaseValue<?>[] modes = mod.getModes();
        for (int i = 0; i < modes.length;i++) {
            if (!(modes[i] instanceof FloatValue)){
                continue;
            }
            if(mouseX > rightX - 110 && mouseX < rightX - 10 && mouseY > modes[i].y + 10  && mouseY < modes[i].y - 20 + 50){
                setValue(((FloatValue) modes[i]),mouseX);
            }
        }
    }

    @Override
    public void handleKeyboardInput() throws IOException {
        int key = Keyboard.getEventKey();
        if(Keyboard.getEventKeyState()){
            if(key == 1){
                onGuiClosed();
                Minecraft.getMinecraft().displayGuiScreen(new WhiteNightClickGui());
            }
        }
    }

    public void setValue(FloatValue mode,float mouseX){
        int rightX = this.x + sizeWidth - 10;
        //计算当前位置偏移
        int i1 = (int) (mouseX - (rightX - 110));
        //计算最大值百分比
        float v = i1 / 100f;
        //获取当前百分比值
        float v2 = v * mode.getMax();
        //获取当前值
        float result = Float.parseFloat(String.format("%.2f", v2));
        float min = Float.parseFloat(String.format("%.2f", mode.getMax() * 0.01));
        float max = Float.parseFloat(String.format("%.2f", mode.getMax() * 0.95));
        if(result <= min){
            result = 0f;
        }
        if(result >= max){
            result = mode.getMax();
        }
        mode.setValue(result);



    }
    public int getMaxScroll(int panelSizeY){
        if(mod.getModes().length == 0){
            return 0;
        }
        int maxVisibleCount = (panelSizeY - 10 - 10) / 50 + 1;
        return getMaxY() - maxVisibleCount * 50;
    }
    public int getMaxY(){
        int offset = 0;
//        for (BaseMod baseMod : baseMods) {
//            if (baseMod.isOpen){
//                offset += baseMod.getInfoSize();
//            }
//        }
        return offset + mod.getModes().length * 50;
    }
}
