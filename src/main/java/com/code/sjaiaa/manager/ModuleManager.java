package com.code.sjaiaa.manager;

import com.code.sjaiaa.mod.BaseMod;
import com.code.sjaiaa.mod.ModType;
import com.code.sjaiaa.mod.mods.misc.NoKeepAlive;
import com.code.sjaiaa.mod.mods.movement.Fly;
import com.code.sjaiaa.mod.mods.movement.Sprint;
import com.code.sjaiaa.mod.mods.render.ClickGui;
import com.code.sjaiaa.mod.mods.render.HUD;
import com.code.sjaiaa.mod.mods.combat.KillAura;
import com.code.sjaiaa.util.MinecraftInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sjaiaa
 * @date 2023/5/16 14:57
 * @discription
 */
public class ModuleManager {
    private static List<BaseMod> mods = new ArrayList<>();
    public ModuleManager(){
        mods.add(new HUD());
        mods.add(new ClickGui());
        mods.add(new KillAura());
        mods.add(new Sprint());
        mods.add(new Fly());
        mods.add(new NoKeepAlive());
//        mods.add(new Notification());
    }
    public static List<BaseMod> getMods() {
        return mods;
    }

    public static BaseMod getMod(String id){
        for (BaseMod mod : mods) {
            if(mod.getModID().equalsIgnoreCase(id)){
                return mod;
            }
        }
        return null;
    }
    public static List<BaseMod> getModulesInCategory(ModType modType){
       return mods.stream().filter(mod -> mod.getType() == modType).sorted((o1, o2) -> MinecraftInstance.getMinecraft().fontRendererObj.getStringWidth(o2.getModID()) - MinecraftInstance.getMinecraft().fontRendererObj.getStringWidth(o1.getModID())).collect(Collectors.toList());
    }
    public static int getModuleEnableSize(ModType modType){
        return (int) mods.stream().filter(mod -> mod.getType() == modType).filter(BaseMod::isState).count();
    }
    public static int getModuleAllSize(ModType modType){
        return (int) mods.stream().filter(mod -> mod.getType() == modType).count();
    }
    public static BaseMod getModByClass(Class<? extends BaseMod> modClass){
        for (BaseMod mod : mods) {
            if (modClass.isInstance(mod)){
                return mod;
            }
        }
        return null;
    }
}
