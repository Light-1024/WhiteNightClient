package com.code.sjaiaa.manager;

import com.code.sjaiaa.WhiteNightClient;
import com.code.sjaiaa.config.BindConfig;
import com.code.sjaiaa.config.Config;
import com.code.sjaiaa.config.ModConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sjaiaa
 * @date 2023/5/23 20:43
 * @discription
 */
public class ConfigManager {
    private final List<Config> configs = new ArrayList<>();
    private static BindConfig bindConfig = new BindConfig(new File(WhiteNightClient.MODID,"bind.config"));
    private static ModConfig modConfig = new ModConfig(new File(WhiteNightClient.MODID,"setting.config"));
    public ConfigManager(){
        configs.add(bindConfig);
        configs.add(modConfig);
    }
    public void loadConfigs(){
        checkConfigDirs();
        for (Config config : configs) {
            try {
                config.loadConfig();
            } catch (Exception e) {
                config.createConfig();
                config.saveConfig();
            }
        }
    }
    public boolean checkConfigDirs(){
        File file = new File(WhiteNightClient.MODID);
        if(file.exists() && file.isDirectory()){
            return true;
        }
        file.mkdirs();
        return false;
    }

    public BindConfig getBindConfig() {
        return bindConfig;
    }

    public static ModConfig getModConfig() {
        return modConfig;
    }
}
