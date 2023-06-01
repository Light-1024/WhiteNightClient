package com.code.sjaiaa;

import com.code.sjaiaa.font.Fonts;
import com.code.sjaiaa.manager.CommandManager;
import com.code.sjaiaa.manager.ConfigManager;
import com.code.sjaiaa.manager.EventManager;
import com.code.sjaiaa.manager.ModuleManager;
import com.code.sjaiaa.util.TimeUtil;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.FMLInjectionData;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.util.Map;

@Mod(modid = WhiteNightClient.MODID, version = WhiteNightClient.VERSION)
public class WhiteNightClient
{
    public static final String MODID = "whitenight";
    public static final String VERSION = "1.0";
    public static ModuleManager moduleManager;
    public static EventManager eventManager;
    public static CommandManager commandManager;
    public static ConfigManager configManager;
    public static long defaultDelay = 500L;
    public static boolean findNewUpdate;
    //公共计时器
    public static TimeUtil timeUtil = new TimeUtil();

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
		// some example code
        moduleManager = new ModuleManager();
        eventManager = new EventManager();
        commandManager = new CommandManager();
        configManager = new ConfigManager();
        Fonts.loadFonts();
        configManager.loadConfigs();
        MinecraftForge.EVENT_BUS.register(eventManager);

    }
}
