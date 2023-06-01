package com.code.sjaiaa.clickgui.element;

import com.code.sjaiaa.font.Fonts;
import com.code.sjaiaa.manager.ModuleManager;
import com.code.sjaiaa.mod.BaseMod;
import com.code.sjaiaa.mod.ModType;
import com.code.sjaiaa.util.*;
import com.code.sjaiaa.values.BaseValue;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sjaiaa
 * @date 2023/5/22 16:35
 * @discription
 */
public class ModuleElement extends BaseElement {
    int last;
    public static int scrollAmmount = 0;
    public List<BaseMod> baseMods = new ArrayList<>();
//    int minOffset = 0;
    public ModuleElement() {
        super(0, 0);
    }

    @Override
    public void drawScreen(int panelX, int panelY, int panelSizeX, int panelSizeY) {
        //所有的module都在这里画
//        RenderUtil.drawOutLine();
        //Mouse.getDWheel() 负数向上
        //获取Module列表

        baseMods = ModuleManager.getModulesInCategory(ModType.values()[ModeTypeElement.defaultSelect]);
        if(last != ModeTypeElement.defaultSelect){
            scrollAmmount = 0;
            last = ModeTypeElement.defaultSelect;
        }
        int i1 = Mouse.getDWheel() / 5;

        if(i1 != 0){
            if(scrollAmmount < 0 && i1 > 0){
                this.scrollAmmount += i1;
            }else{
                if(this.scrollAmmount > 0){
                    this.scrollAmmount = 0;
                }else if(Math.abs(this.scrollAmmount) < getMaxScroll(panelSizeY) + 20){
                    this.scrollAmmount += i1;
                }
            }

        }
//        Chat.onMessage(String.valueOf(scrollAmmount));

        RenderUtil.startGlScissor(panelX + panelSizeX / 4,panelY,panelSizeX,panelSizeY);
        //获取到最大偏移

        for (int i = 0; i< baseMods.size();i++) {

            BaseMod baseMod = baseMods.get(i);
            Fonts.JelloFont.boldFont.drawString(baseMod.getModID().length() > 18 ? baseMod.getModID().substring(0,18) + "...":baseMod.getModID(),(panelX + panelSizeX / 4 ) * 2 + 40,(panelY + i * 50 + scrollAmmount) * 2 + 50,baseMod.isState() ? ColorUtil.rainbow().getRGB(): Color.WHITE.getRGB());
//            int size = Fonts.JelloFont.boldFont.getStringWidth(baseMod.getModID());
            if (Utils.isFull()){
                RenderUtil.drawString(baseMod.modInfo().length() > 18 ? baseMod.modInfo().substring(0,18) + "...":baseMod.modInfo(),panelX + panelSizeX / 4 + 100,panelY + i * 50 + scrollAmmount + 25,Color.lightGray.getRGB());
            }
            //总共面积
            int max = panelX + panelSizeX - 10 - panelSizeX / 15;
            int min = panelX + panelSizeX - 10 - panelSizeX / 10;
            float top = panelY + 10 + i * 50 + scrollAmmount + 40 / 3f;
            float right = panelX + panelSizeX - 15;
            float bottom = panelY  + i * 50 + scrollAmmount + 35;
            int X = Utils.isFull() ? max: min;

            RenderUtil.drawRoundRectWithRect(X,(int)top + 2,(int)right ,(int)bottom - 2,3,Color.white.getRGB(),true);
            //阴影面积
            if (!baseMod.isState()){
                RenderUtil.drawRoundRectWithRect(X,top ,(right + X) / 2 - (Utils.isFull() ? 10:0),bottom,3,Color.lightGray.getRGB(),true);
            }else{
                RenderUtil.drawRoundRectWithRect((right + X) / 2 + (Utils.isFull() ? 10:0),top ,right,bottom ,3,Color.green.getRGB(),true);
            }
            //配置大小

            RenderUtil.drawOutLine(panelX + panelSizeX / 4 + 10,panelY + 10 + i * 50 + scrollAmmount, panelX + panelSizeX - 10, panelY + 50 + i * 50 + scrollAmmount, Color.WHITE.getRGB());
            baseMods.get(i).y = panelY + 10 + i * 50 + scrollAmmount;
        }


//            Chat.onMessage(String.valueOf(panelY + panelSizeY));
//        Chat.onMessage(String.valueOf(MinecraftInstance.getMinecraft().displayHeight));
//        for (int i = 0 ;i < maxSize;i++){
//            RenderUtil.drawOutLine((panelX + panelSizeX / 4) + 10, (int) (panelY + 10 + i * 50 + dWheel * 0.5f), panelX + panelSizeX - 10, (int) (panelY + 50 + i * 50 + dWheel * 0.5f), Color.white.getRGB());
//        }
//        for (BaseMod baseMod : modulesInCategory) {
//            RenderUtil.drawOutLine((panelX + panelSizeX / 4) + 10, panelY + 10 + offset, panelX + panelSizeX - 10, panelY + 50 + offset, Color.white.getRGB());
//            offset += 50 + dWheel;
//        }
        RenderUtil.stopGlScissor();
    }

    @Override
    public void MouseClick(int panelX, int panelY, int sizeWidth, int sizeHeight, int mouseX, int mouseY, int MouseType) {
        //首先判断是否在面板内
        if(mouseX > panelX + sizeWidth / 4 + 10 && mouseX < panelX + sizeWidth - 10 && mouseY > panelY && mouseY < panelY + sizeHeight){
            //判断点击范围
            for (BaseMod baseMod : baseMods) {
                if(mouseY >= baseMod.y && mouseY <= baseMod.y + 40){
                    //判断左键
                    if(MouseType == 0){
                        baseMod.setState(!baseMod.isState());
                        break;
                    }
                    //判断右键
                    if(MouseType == 1){
                        if(baseMod.getModes() != null){
                            MinecraftInstance.getMinecraft().displayGuiScreen(new ModuleEditGui(baseMod));
                        }

                        break;
                    }
                }
            }
        }
    }
    public int getMaxScroll(int panelSizeY){
        if(baseMods.isEmpty()){
            return 0;
        }
        int maxVisibleCount = (panelSizeY - 20) / 50 + 1;
        return getMaxY() - maxVisibleCount * 50;
    }
    public int getMaxY(){
        int offset = 0;
//        for (BaseMod baseMod : baseMods) {
//            if (baseMod.isOpen){
//                offset += baseMod.getInfoSize();
//            }
//        }
        return offset + baseMods.size() * 50;
    }
}
