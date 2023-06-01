package com.code.sjaiaa.mod.mods.render;

import com.code.sjaiaa.clickgui.WhiteNightClickGui;
import com.code.sjaiaa.mod.BaseMod;
import com.code.sjaiaa.mod.ModType;
import com.code.sjaiaa.util.MinecraftInstance;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author sjaiaa
 * @date 2023/5/17 15:18
 * @discription
 */
@SideOnly(Side.CLIENT)
public class ClickGui extends BaseMod {
    public ClickGui() {
        super("ClickGui", ModType.RENDER);
    }


    @Override
    public void onEnable() {
        MinecraftInstance.getMinecraft().displayGuiScreen(new WhiteNightClickGui());
        this.setState(false);
    }
}
