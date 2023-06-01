package com.code.sjaiaa.command;

import com.code.sjaiaa.util.Chat;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

/**
 * @author sjaiaa
 * @date 2023/5/16 15:47
 * @discription
 */
public abstract class BaseCommand {

    public abstract String getCommandName();

    public abstract String getCommandUsage();
    public abstract void processCommand(String[] args);
}
