package com.code.sjaiaa.clickgui.element;

/**
 * @author sjaiaa
 * @date 2023/5/22 12:23
 * @discription
 */
public abstract class BaseElement {
    //暂时还没想好xy可以拿来干什么,手跟上了脑子没跟上
    public int x;
    public int y;
    public BaseElement(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract void drawScreen(int panelX,int panelY,int panelSizeX,int panelSizeY);
    public abstract void MouseClick(int panelX,int panelY,int sizeWidth,int sizeHeight,int mouseX,int mouseY,int MouseType);
}
