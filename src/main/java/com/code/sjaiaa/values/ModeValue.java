package com.code.sjaiaa.values;

import java.util.Arrays;

/**
 * @author sjaiaa
 * @date 2023/5/21 17:28
 * @discription
 */
public class ModeValue extends BaseValue<Mode>{
    private Mode[] modes;
    public ModeValue(String baseName,String defaultValue,Mode... modes) {
        super(baseName, null);
        this.modes = modes;
        for (Mode mode : modes) {
            if (mode.getModeName().equals(defaultValue)){
                super.setValue(mode);
                return;
            }
        }
    }
    public ModeValue(String baseName,String info,String defaultValue,Mode... modes) {
        super(baseName, null,info);
        this.modes = modes;
        for (Mode mode : modes) {
            if (mode.getModeName().equals(defaultValue)){
                super.setValue(mode);
                return;
            }
        }
    }
    public void setValue(String mode){
        for (Mode mode1 : modes) {
            if (mode1.getModeName().equals(mode)){
                super.setValue(mode1);
                return;
            }
        }
    }
    public void setNextValue(){
        boolean finder = false;
        for (Mode mode : modes) {
            if(finder){
                super.setValue(mode);
                return;
            }
            if (mode.getModeName().equals(super.getValue().getModeName())){
                finder = true;
            }
        }
        if (finder){
            super.setValue(modes[0]);
        }
    }

    public Mode[] getModes() {
        return modes;
    }

}
