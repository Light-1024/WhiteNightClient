package com.code.sjaiaa.values;

import com.code.sjaiaa.WhiteNightClient;

/**
 * @author sjaiaa
 * @date 2023/5/21 17:17
 * @discription
 */
public class BaseValue <T> extends BaseValueConfig{
    private String baseName;
    private T defaultValue;
    private String info = "";
    public static boolean LoadingConfig;


    public BaseValue(String baseName,T defaultValue){
        this(baseName,defaultValue,"");
    }
    public BaseValue(String baseName,T defaultValue,String info){
        this.baseName = baseName;
        this.defaultValue = defaultValue;
        this.info = info;
    }
    public String getBaseName() {
        return baseName;
    }
    public String getInfo(){
        return info;
    }

    public void setValue(T defaultValue) {
        this.defaultValue = defaultValue;
        if(LoadingConfig){
            WhiteNightClient.configManager.getModConfig().saveConfig();
        }
    }

    public T getValue(){
        return defaultValue;
    }
}
