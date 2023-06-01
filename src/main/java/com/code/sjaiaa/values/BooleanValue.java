package com.code.sjaiaa.values;

/**
 * @author sjaiaa
 * @date 2023/5/21 17:27
 * @discription
 */
public class BooleanValue extends BaseValue<Boolean>{
    public BooleanValue(String baseName, Boolean defaultValue) {
        super(baseName, defaultValue);
    }
    public BooleanValue(String baseName,String info, Boolean defaultValue) {
        super(baseName, defaultValue,info);
    }
}
