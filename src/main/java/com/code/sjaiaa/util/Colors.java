package com.code.sjaiaa.util;

import java.awt.*;

/**
 * @author sjaiaa
 * @date 2023/5/15 15:10
 * @discription
 */
public class Colors {
    private int color;
    public Colors(int color){
        this.color = color;
    }
    public int getRed(){
        return (this.color >> 16);
    }
    public int getGreen(){
        return (this.color >> 8) & 0xff;
    }
    public int getBlue(){
        return (this.color) & 0xff;
    }
    public float getAlpha(){
        return ((this.color>>24) & 0xff) / (float)255;
    }
}
