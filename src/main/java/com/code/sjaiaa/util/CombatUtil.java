package com.code.sjaiaa.util;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author sjaiaa
 * @date 2023/5/20 21:57
 * @discription
 */
public class CombatUtil {
    public static List<EntityLivingBase> getTargetEnemy(float search,Target targetType){
        EntityPlayerSP thePlayer = MinecraftInstance.getMinecraft().thePlayer;
        WorldClient theWorld = MinecraftInstance.getMinecraft().theWorld;
        if (theWorld == null || thePlayer == null){
            return new ArrayList<>();
        }
        List<Entity> loadedEntityList = MinecraftInstance.getMinecraft().theWorld.getLoadedEntityList();
        return loadedEntityList.stream().filter(entity -> {
            if (!(entity instanceof EntityLivingBase)) {
                return false;
            }
            if(entity instanceof EntityPlayerSP){
                return false;
            }
            if(((EntityLivingBase) entity).getHealth() < 0){
                return false;
            }
            return !(thePlayer.getDistanceToEntity(entity) > search);
        }).sorted((o1, o2) -> {
            if (targetType == Target.HEALTH) {
                float v1 = ((EntityLivingBase) o1).getHealth();
                float v2 = ((EntityLivingBase) o2).getHealth();
                return (v1 - v2 >= 0f) ? 1 : -1;
            }
            if (targetType == Target.DISTANCE) {
                double v1 = thePlayer.getDistanceSqToEntity(o1);
                double v2 = thePlayer.getDistanceSqToEntity(o2);
                return (v1 - v2 >= 0f) ? 1 : -1;
            }
            return 0;
        }).map(entity -> (EntityLivingBase)entity).collect(Collectors.toList());
    }
    public enum Target{
        HEALTH,
        DISTANCE
    }
    public static boolean isAnimal(final Entity entity) {
        return entity instanceof EntityAnimal || entity instanceof EntitySquid || entity instanceof EntityGolem ||
                entity instanceof EntityBat;
    }

    public static boolean isMob(final Entity entity) {
        return entity instanceof EntityMob || entity instanceof EntityVillager || entity instanceof EntitySlime ||
                entity instanceof EntityGhast || entity instanceof EntityDragon;
    }
}
