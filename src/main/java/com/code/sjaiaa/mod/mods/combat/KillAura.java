package com.code.sjaiaa.mod.mods.combat;

import com.code.sjaiaa.event.PacketEvent;
import com.code.sjaiaa.manager.ModuleManager;
import com.code.sjaiaa.mod.BaseMod;
import com.code.sjaiaa.mod.ModType;
import com.code.sjaiaa.mod.mods.movement.Sprint;
import com.code.sjaiaa.util.CombatUtil;
import com.code.sjaiaa.util.MinecraftInstance;
import com.code.sjaiaa.util.PacketUtils;
import com.code.sjaiaa.util.rotation.Rotation;
import com.code.sjaiaa.util.rotation.RotationUtil;
import com.code.sjaiaa.values.BooleanValue;
import com.code.sjaiaa.values.FloatValue;
import com.code.sjaiaa.values.Mode;
import com.code.sjaiaa.values.ModeValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.server.S00PacketKeepAlive;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;

/**
 * @author sjaiaa
 * @date 2023/5/20 21:04
 * @discription
 */
public class KillAura extends BaseMod {
    EntityLivingBase target;
    BooleanValue rotation = new BooleanValue("rotation","是否在服务器转头",true);
    BooleanValue aim = new BooleanValue("Aim","是否开启自瞄",false);
    ModeValue targetType = new ModeValue("TargetType","索敌选项","Distance",new Mode("Distance","距离"),new Mode("Health","血量"));
    FloatValue reachValue = new FloatValue("Reach","攻击距离",3,5,3.7f);
    BooleanValue allSprint = new BooleanValue("allSprint","保持加速",false);
    BooleanValue Animal = new BooleanValue("Animal","是否攻击动物",true);
    BooleanValue Mob = new BooleanValue("Mob","是否攻击怪物",true);
    BooleanValue Player = new BooleanValue("Player","是否攻击玩家",true);

    public KillAura() {
        super("KillAura", ModType.COMBAT);
        this.addValues(rotation,aim,targetType, reachValue,allSprint,Animal,Mob,Player);
    }

    @Override
    public void onClientEvent(TickEvent.ClientTickEvent event) {
        CombatUtil.Target target1 = null;
        if (targetType.getValue().getModeName().equals("Distance")){
            target1 = CombatUtil.Target.DISTANCE;
        }else if (targetType.getValue().getModeName().equals("Health")){
            target1 = CombatUtil.Target.HEALTH;
        }
        List<EntityLivingBase> targetList = CombatUtil.getTargetEnemy(reachValue.getValue(), target1);

        if (targetList.isEmpty()){
            return;
        }
        for (EntityLivingBase entityLivingBase : targetList) {
            if (Animal.getValue()){
                if (CombatUtil.isAnimal(entityLivingBase)){
                    target = entityLivingBase;
                    break;
                }
            }
            if (Mob.getValue()){
                if (CombatUtil.isMob(entityLivingBase)){
                    target = entityLivingBase;
                    break;
                }
            }
            if (Player.getValue()){
                if (entityLivingBase instanceof EntityPlayer){
                    target = entityLivingBase;
                    break;
                }
            }
            return;
        }
        EntityPlayerSP thePlayer = Minecraft.getMinecraft().thePlayer;
        if (rotation.getValue() || aim.getValue()){
            Rotation rotation = RotationUtil.faceToTarget(target);
            RotationUtil.setServerRotation(rotation);
        }
        if (aim.getValue()){
            thePlayer.rotationYaw = RotationUtil.targetRotation.getYaw();
            thePlayer.rotationPitch = RotationUtil.targetRotation.getPitch();
        }

        if(thePlayer.ticksExisted % 5 == 0){
            Minecraft.getMinecraft().thePlayer.swingItem();
            Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
            if (allSprint.getValue() && MinecraftInstance.getPlayer().isSprinting() && !ModuleManager.getModByClass(Sprint.class).isState()){
                MinecraftInstance.getMinecraft().getNetHandler().addToSendQueue(new C0BPacketEntityAction(thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
                MinecraftInstance.getPlayer().setSprinting(true);
            }
        }

    }

    @Override
    public void onPacketEvent(PacketEvent event) {
        if(!rotation.getValue() || aim.getValue()){
            return;
        }
        Packet<?> packet = event.getPacket();
        EntityPlayerSP thePlayer = Minecraft.getMinecraft().thePlayer;
        if (packet instanceof C03PacketPlayer){
            if (target != null){
                if(packet instanceof C03PacketPlayer.C06PacketPlayerPosLook){
                    event.cancelEvent();
                    PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(thePlayer.posX,thePlayer.posY,thePlayer.posZ,RotationUtil.targetRotation.getYaw(),RotationUtil.targetRotation.getPitch(),thePlayer.onGround));
                    return;
                }
                if (packet instanceof C03PacketPlayer.C05PacketPlayerLook){
                    event.cancelEvent();
                    PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C05PacketPlayerLook(RotationUtil.targetRotation.getYaw(),RotationUtil.targetRotation.getPitch(),thePlayer.onGround));
                    return;
                }
                event.cancelEvent();
                PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C05PacketPlayerLook(RotationUtil.targetRotation.getYaw(),RotationUtil.targetRotation.getPitch(),thePlayer.onGround));
            }
        }
    }
}
