package com.code.sjaiaa.values;

/**
 * @author sjaiaa
 * @date 2023/5/21 17:19
 * @discription
 */
public class FloatValue extends BaseValue<Float>{
    private float min;
    private float max;
    public FloatValue(String baseName,float min,float max,float defaultValue) {
        super(baseName,defaultValue);
        this.min = min;
        this.max = max;
    }

    public FloatValue(String baseName,String info,float min,float max,float defaultValue) {
        super(baseName,defaultValue,info);
        this.min = min;
        this.max = max;
    }
    public float getMin() {
        return min;
    }
    public float getMax() {
        return max;
    }


    @Override
    public void setValue(Float defaultValue) {
        if(defaultValue < min){
            super.setValue(min);
            return;
        }
        if (defaultValue > max){
            super.setValue(max);
            return;
        }
        super.setValue(defaultValue);
    }
}
