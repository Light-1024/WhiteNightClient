package com.code.sjaiaa.config;

import java.io.File;

/**
 * @author sjaiaa
 * @date 2023/5/23 16:04
 * @discription
 */
public abstract class Config {
    private File configFile;

    public Config(File configFile) {
        this.configFile = configFile;
    }
    public abstract void loadConfig() throws Exception;

    public File getConfigFile() {
        return configFile;
    }

    public void setConfigFile(File configFile) {
        this.configFile = configFile;
    }

    public abstract void createConfig();
    public abstract void saveConfig();
}
