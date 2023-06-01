package com.code.sjaiaa.config;


import com.alibaba.fastjson2.JSONObject;
import com.code.sjaiaa.manager.ModuleManager;
import com.code.sjaiaa.mod.BaseMod;
import com.code.sjaiaa.mod.mods.render.ClickGui;
import com.code.sjaiaa.util.FileUtils;
import org.lwjgl.input.Keyboard;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author sjaiaa
 * @date 2023/5/23 16:04
 * @discription
 */
public class BindConfig extends Config{
    public BindConfig(File configFile) {
        super(configFile);
    }

    @Override
    public void loadConfig() throws Exception {
        JSONObject object = FileUtils.readerFileToJson(this.getConfigFile());
        Set<Map.Entry<String, Object>> entries = object.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            String modID = entry.getKey();
            int state = (int) entry.getValue();
            ModuleManager.getMod(modID).setKey(state);
        }
    }

    @Override
    public void createConfig() {
        File newFile = new File(getConfigFile().toURI());
        try {
            newFile.createNewFile();
            ModuleManager.getModByClass(ClickGui.class).setKey(Keyboard.KEY_RSHIFT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveConfig() {
        JSONObject object = new JSONObject();
        List<BaseMod> mods = ModuleManager.getMods();
        for (BaseMod mod : mods) {
            object.put(mod.getModID(),mod.getKey());
        }
        FileUtils.writeJsonToFile(object,super.getConfigFile());
    }
}
