package com.code.sjaiaa.clickgui.element;

import com.code.sjaiaa.font.Fonts;
import com.code.sjaiaa.util.Chat;
import com.code.sjaiaa.util.MinecraftInstance;
import com.code.sjaiaa.util.RenderUtil;
import com.code.sjaiaa.util.Utils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.Display;

import java.awt.*;

/**
 * @author sjaiaa
 * @date 2023/5/22 12:23
 * @discription
 */
public class InfoElement extends BaseElement{
    int yoffset = 10;
    public InfoElement() {
        super(0,0);
    }

    @Override
    public void drawScreen(int panelX, int panelY, int panelSizeX, int panelSizeY) {
        int right = panelX + panelSizeX / 4;
        int textOffset = 2 * panelX + 118;
        int baseYoffset = 2 * (panelY + yoffset);
//        640 399
        EntityPlayerSP thePlayer = MinecraftInstance.getMinecraft().thePlayer;
        // TODO: 2023/5/23
//        RenderUtil.drawOutLine(panelX,panelY,right,panelY + panelSizeY / 3,Color.BLUE.getRGB() );
        RenderUtil.drawFace(thePlayer.getLocationSkin(), panelX + 10, panelY + 10,8, 8, 8, 8, 44, 44, 64, 64);
        if(!(Utils.isFull())){
            return;
        }
        /// TODO: 2023/5/23  
//        RenderUtil.drawOutLine(panelX,panelY,right,panelY + panelSizeY / 3,Color.white.getRGB() );
        Fonts.JelloFont.boldFont.drawString("UserName:" + thePlayer.getName(),textOffset,baseYoffset,Color.white.getRGB());
        Fonts.JelloFont.boldFont.drawString("Online:" + false,textOffset,baseYoffset + 2 * yoffset,Color.white.getRGB());
        String[] info = MinecraftInstance.ServerInfo();
        Fonts.JelloFont.boldFont.drawString("ServerIP:" + info[0],textOffset,baseYoffset + 4 * yoffset,Color.white.getRGB());
        Fonts.JelloFont.boldFont.drawString("Ping:" + info[1],textOffset,baseYoffset + 6 * yoffset,Color.white.getRGB());
    }

    @Override
    public void MouseClick(int panelX, int panelY, int sizeWidth, int sizeHeight, int mouseX, int mouseY, int MouseType) {

    }
}
