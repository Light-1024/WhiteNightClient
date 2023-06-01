package com.code.sjaiaa.values;

/**
 * @author sjaiaa
 * @date 2023/5/21 17:28
 * @discription
 */
public class Mode {
    private String modeName;
    private String modinfo;
    public Mode(String modeName) {
        this(modeName,"");

    }
    public Mode(String modeName,String info) {
        this.modeName = modeName;
        this.modinfo = info;

    }
    public String getModeName() {
        return modeName;
    }

    public String getModinfo() {
        return modinfo;
    }
}
