package com.code.sjaiaa.command;

import com.code.sjaiaa.manager.ModuleManager;
import com.code.sjaiaa.mod.BaseMod;
import com.code.sjaiaa.util.Chat;
import com.code.sjaiaa.values.*;

/**
 * @author sjaiaa
 * @date 2023/5/21 17:58
 * @discription
 */
public class SettingCommand extends BaseCommand{

    @Override
    public String getCommandName() {
        return "set";
    }

    @Override
    public String getCommandUsage() {
        return ".set <Mod> <setting1> value <setting2> value...";
    }

    @Override
    public void processCommand(String[] args) {
        if(args.length < 3){
            Chat.onMessage(getCommandUsage());
            return;
        }
        BaseMod mod = ModuleManager.getMod(args[0]);
        if(mod == null){
            Chat.onMessage("未找到Mod");
            return;
        }
        if((args.length - 1) % 2 != 0){
            Chat.onMessage(getCommandUsage());
            return;
        }
        BaseValue<?>[] modes = mod.getModes();

        for (int i = 1;i < args.length; i += 2){
            try {
                for (BaseValue<?> type : modes) {
                    if (type.getBaseName().equals(args[i])){
                        if (type.getValue() instanceof Float){
                            FloatValue types = (FloatValue) type;
                            types.setValue(Float.parseFloat(args[i + 1]));
                        }
                        if (type.getValue() instanceof Mode){
                            ModeValue types = (ModeValue) type;
                            types.setValue(args[i + 1]);
                        }
                        if (type.getValue() instanceof Boolean){
                            BooleanValue types = (BooleanValue) type;
                            types.setValue(Boolean.parseBoolean(args[i + 1]));
                        }
                        break;
                    }
                }
            }catch (Exception ignored){}
        }
    }
}
