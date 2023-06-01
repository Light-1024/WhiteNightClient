package com.code.sjaiaa.manager;

import com.code.sjaiaa.command.BaseCommand;
import com.code.sjaiaa.command.BindCommand;
import com.code.sjaiaa.command.SettingCommand;
import com.code.sjaiaa.util.Chat;
import net.minecraft.command.CommandBase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sjaiaa
 * @date 2023/5/16 15:43
 * @discription
 */
public class CommandManager {
    private final List<BaseCommand> commands = new ArrayList<>();

    public CommandManager() {
        commands.add(new BindCommand());
        commands.add(new SettingCommand());
    }
    private final char cmdPrefix = '.';
    public void runCommands(String s)
    {
        String readString = s.trim().substring(Character.toString(cmdPrefix).length()).trim();
        boolean commandResolved = false;
        boolean hasArgs = readString.trim().contains(" ");
        String commandName = hasArgs ? readString.split(" ")[0] : readString.trim();
        String[] args = hasArgs ? readString.substring(commandName.length()).trim().split(" ") : new String[0];
        for(BaseCommand command: commands)
        {
            if(command.getCommandName().trim().equalsIgnoreCase(commandName.trim()))
            {
                command.processCommand(args);
                commandResolved = true;
                break;
            }
        }
        if(!commandResolved){
            Chat.onMessage("Cannot resolve internal command: \u00a7c" + commandName);
        }
    }
    public boolean findCommand(String args){
        if(!args.startsWith(".")){
            return false;
        }
        String trim = args.trim().split(" ")[0].replace(".","");
        boolean finder = false;
        for (BaseCommand command : commands) {
            if(command.getCommandName().equals(trim)){
                finder = true;
                break;
            }
        }
        return finder;
    }
}
