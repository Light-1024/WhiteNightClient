package com.code.sjaiaa.util;

/**
 * @author sjaiaa
 * @date 2023/5/18 11:26
 * @discription
 */
public class TimeUtil {
    long time = 0;
    public boolean isDelay(long delay){
        if(System.currentTimeMillis() - time < delay){
            return false;
        }
        return true;
    }
    public void reset(){
        time = System.currentTimeMillis();
    }
}
