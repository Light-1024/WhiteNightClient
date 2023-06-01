package com.code.sjaiaa.mod;

import com.code.sjaiaa.WhiteNightClient;
import com.code.sjaiaa.event.EventInst;
import com.code.sjaiaa.event.PacketEvent;
import com.code.sjaiaa.util.MinecraftInstance;
import com.code.sjaiaa.values.BaseValue;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * @author sjaiaa
 * @date 2023/5/16 14:58
 * @discription
 */
public class BaseMod implements EventInst {
    public int x;
    public int y;
    private BaseValue<?>[] modes;
    private final String modID;
    private final ModType type;
    private boolean state = false;
    private int key = -1;

    public BaseMod(String modID, ModType type) {
        this.modID = modID;
        this.type = type;
    }
    public String modInfo(){
        return "";
    }
    public void addValues(BaseValue<?>... values){
        modes = values;
    }

    public BaseValue<?>[] getModes() {
        return modes;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }
    public void setState(boolean state) {
        this.state = state;
        if (MinecraftInstance.getPlayer() == null || MinecraftInstance.getMinecraft().theWorld == null){
            return;
        }
        if(state){
            onEnable();
//            NotificationManager.pushMessage(new Message(this.modID, Message.Type.ENABLE));
        }else{
            onDisable();
//            NotificationManager.pushMessage(new Message(this.modID, Message.Type.DISABLE));
        }
        WhiteNightClient.configManager.getModConfig().saveConfig();
    }
    public String getModID() {
        return modID;
    }

    public ModType getType() {
        return type;
    }
    public void onEnable(){}
    public void onDisable(){}
    public boolean isState() {
        return state;
    }
    @Override
    public void onPlayerEvent(TickEvent.PlayerTickEvent event) {}
    @Override
    public void onClientEvent(TickEvent.ClientTickEvent event) {}
    @Override
    public void onRender2D(RenderGameOverlayEvent event) {}

    @Override
    public void onKeyEvent(InputEvent.KeyInputEvent event) {}

    @Override
    public void onPacketEvent(PacketEvent event) {}

    @Override
    public void onAttackEvent(AttackEntityEvent event) {}


    @Override
    public String toString() {
        return modID;
    }
    public int getInfoSize(){
        return this.modes.length;
    }
}
