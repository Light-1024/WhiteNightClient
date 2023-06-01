package com.code.sjaiaa.mod.mods.movement;

import com.code.sjaiaa.event.PacketEvent;
import com.code.sjaiaa.mixins.C03PacketPlayerMixin;
import com.code.sjaiaa.mod.BaseMod;
import com.code.sjaiaa.mod.ModType;
import com.code.sjaiaa.util.Chat;
import com.code.sjaiaa.util.MinecraftInstance;
import com.code.sjaiaa.util.MovementUtil;
import com.code.sjaiaa.util.PacketUtils;
import com.code.sjaiaa.util.rotation.AngleHelper;
import com.code.sjaiaa.values.BooleanValue;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * @author sjaiaa
 * @date 2023/5/23 16:07
 * @discription
 */
public class Sprint extends BaseMod {
    boolean rotation;
    private BooleanValue AllDirection = new BooleanValue("allDirection", "全方位移动", true);

    public Sprint() {
        super("Sprint", ModType.MOVEMENT);
        this.addValues(AllDirection);
    }

    @Override
    public String modInfo() {
        return "强制疾跑";
    }

    @Override
    public void onPlayerEvent(TickEvent.PlayerTickEvent event) {
        if (!MovementUtil.playerIsMoving()){
            rotation = false;
        }
        EntityPlayerSP player = MinecraftInstance.getPlayer();
        if (player.getFoodStats().getFoodLevel() > 6F && MovementUtil.playerIsMoving()) {
            if (!player.isSprinting()) {
                if (!AllDirection.getValue()){
                    if (!(MinecraftInstance.getPlayer().moveForward > 0)){
                        return;
                    }
                }
                PacketUtils.sendPacketNoEvent(new C0BPacketEntityAction(player, C0BPacketEntityAction.Action.START_SPRINTING));
                player.setSprinting(true);
            }
        }
    }

    @Override
    public void onPacketEvent(PacketEvent event) {
        Packet<?> packet = event.getPacket();
        if(packet instanceof C03PacketPlayer){
            EntityPlayerSP playerSP = MinecraftInstance.getPlayer();
            if (playerSP.moveForward == 0 && playerSP.moveStrafing == 0){
                return;
            }
//            double v = AngleHelper.RadianToAngle((float) Math.atan2(playerSP.motionZ, playerSP.motionX)) - 90f - playerSP.rotationYaw;
//            float differentAngle = (float) (v - playerSP.rotationYaw);
            //0代表向前
            double direction = MovementUtil.getDirection();
            double v = MathHelper.wrapAngleTo180_double(direction ) - MathHelper.wrapAngleTo180_float(playerSP.rotationYaw) ;
            int n90 = (int) Math.floor(Math.abs(v / 90));
            int n45 = (int) Math.floor((Math.abs(v) - n90 * 90) / 45);
//            int nOthers = (int) Math.floor(Math.abs(v) - n90 * 90 - n45 * 45);

            // 如果 v 小于 0，需要取反
            if (v < 0) {
                n90 = -n90;
                n45 = -n45;
//                nOthers = -nOthers;
            }

            if (packet instanceof C03PacketPlayer.C04PacketPlayerPosition || packet instanceof C03PacketPlayer.C06PacketPlayerPosLook){
                event.cancelEvent();

                if(!rotation){
                    for (int i = 0; i < Math.abs(n90);i++){
                        PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(playerSP.posX,playerSP.posY,playerSP.posZ, playerSP.rotationYaw + (n90 > 0 ? 90 : -90),playerSP.rotationPitch,playerSP.onGround));
                    }
                    PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(playerSP.posX,playerSP.posY,playerSP.posZ,(playerSP.rotationYaw + (n45 > 0 ? 45 : -45)),playerSP.rotationPitch,playerSP.onGround));
                    rotation = true;
                }
                PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(playerSP.posX,playerSP.posY,playerSP.posZ, (float) direction, playerSP.rotationPitch, playerSP.onGround));
            }
        }
    }
}
