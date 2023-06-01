package com.code.sjaiaa.config;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.code.sjaiaa.manager.ModuleManager;
import com.code.sjaiaa.mod.BaseMod;
import com.code.sjaiaa.mod.mods.render.ClickGui;
import com.code.sjaiaa.mod.mods.render.HUD;
import com.code.sjaiaa.util.FileUtils;
import com.code.sjaiaa.values.*;
import org.lwjgl.input.Keyboard;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author sjaiaa
 * @date 2023/5/23 22:41
 * @discription
 */
public class ModConfig extends Config{
    public ModConfig(File configFile) {
        super(configFile);
    }

    //修改举例  {"Helloworld":{"enable":true,"floatValue":1.0,"anotherValue":true}}
    @Override
    public void loadConfig() throws Exception {
        JSONObject object = FileUtils.readerFileToJson(this.getConfigFile());
        Set<Map.Entry<String, Object>> entries = object.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            String modID = entry.getKey();
            JSONObject modeValue = (JSONObject)entry.getValue();
            BaseMod mod = ModuleManager.getMod(modID);
            boolean isState = modeValue.getBooleanValue("isState");
            mod.setState(isState);
            BaseValue<?>[] configs = mod.getModes();
            if (configs != null){
                for (BaseValue<?> config : configs) {
                    String baseName = config.getBaseName();
                    //获取到属性
                    if (config instanceof BooleanValue){
                        boolean o1 = (boolean) modeValue.get(baseName);
                        ((BooleanValue)config).setValue(o1);
                    }
                    if (config instanceof FloatValue){
                        float o1 = modeValue.getFloat(baseName);
                        ((FloatValue)config).setValue(o1);
                    }
                    if (config instanceof ModeValue){
                        String o1 = modeValue.getString(baseName);
                        ((ModeValue)config).setValue(o1);
                    }
                }
            }
        }
        BaseValue.LoadingConfig = true;
    }

    @Override
    public void createConfig() {
        BaseValue.LoadingConfig = true;
        File newFile = new File(getConfigFile().toURI());
        try {
            newFile.createNewFile();
            ModuleManager.getModByClass(HUD.class).setState(true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveConfig() {
        JSONObject object = new JSONObject();
        List<BaseMod> mods = ModuleManager.getMods();
        for (BaseMod mod : mods) {
            JSONObject object1 = new JSONObject();
            boolean state = mod.isState();
            object1.put("isState",state);
            BaseValue<?>[] modes = mod.getModes();
            if(modes != null){
                for (BaseValue<?> mode : modes) {
                    if (mode instanceof ModeValue){
                        object1.put(mode.getBaseName(),((Mode)mode.getValue()).getModeName());
                    }else{
                        object1.put(mode.getBaseName(),mode.getValue());
                    }

                }
            }
            object.put(mod.getModID(),object1);
        }
        FileUtils.writeJsonToFile(object,super.getConfigFile());
    }
}
