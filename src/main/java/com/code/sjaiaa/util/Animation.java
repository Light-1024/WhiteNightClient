package com.code.sjaiaa.util;

/**
 * @author sjaiaa
 * @date 2023/5/19 10:28
 * @discription
 */
public class Animation {
    long delay = 100L;
    public TimeUtil timeUtil;

    private int result = 0;
    public Animation() {
        timeUtil = new TimeUtil();
        timeUtil.reset();
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public void flow(int target, float speed) {
        if (timeUtil.isDelay(delay)){
            int diff = target - result;
            result = (int) (result + (diff > 0 ? Math.ceil(diff * speed) : Math.floor(diff * speed)));
            timeUtil.reset();
        }
    }

    public int getResult() {
        return result;
    }
}
