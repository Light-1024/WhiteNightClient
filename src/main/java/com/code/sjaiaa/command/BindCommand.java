package com.code.sjaiaa.command;

import com.code.sjaiaa.WhiteNightClient;
import com.code.sjaiaa.manager.ModuleManager;
import com.code.sjaiaa.mod.BaseMod;
import com.code.sjaiaa.util.Chat;
import org.lwjgl.input.Keyboard;

/**
 * @author sjaiaa
 * @date 2023/5/16 15:26
 * @discription
 */
public class BindCommand extends BaseCommand {
    @Override
    public String getCommandName() {
        return "bind";
    }

    @Override
    public String getCommandUsage() {
        return ".bind <BaseModName> key";
    }

    @Override
    public void processCommand(String[] args) {
        if (args.length < 2) {
            Chat.onMessage(getCommandUsage());
            return;
        }
        BaseMod mod = ModuleManager.getMod(args[0]);
        if (mod == null) {
            Chat.onMessage("未找到Mod");
            return;
        }
        try {
            String s = args[1].toUpperCase();
            if (s.length() != 1){
                throw new Exception("Error");
            }
            mod.setKey(Keyboard.getKeyIndex(s));
            Chat.onMessage("[" + mod.getModID() + "]设置按键为:" + s);
        } catch (Exception e) {
            mod.setKey(Keyboard.KEY_NONE);
            Chat.onMessage("[" + mod.getModID() + "]设置按键为:null");
        }
        WhiteNightClient.configManager.getBindConfig().saveConfig();
    }
}
