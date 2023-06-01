package com.code.sjaiaa.util.rotation;

import com.code.sjaiaa.mixins.C03PacketPlayerMixin;
import com.code.sjaiaa.util.Chat;
import com.code.sjaiaa.util.MinecraftInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.Packet;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;

/**
 * @author sjaiaa
 * @date 2023/5/20 21:20
 * @discription
 */
public class RotationUtil {
//    public static Rotation serverRotation = new Rotation(0,0);
    public static Rotation targetRotation = new Rotation(0,0);

    //更新服务器转向状态
//    public static void onUpdateServerRotation(Packet<?> packet) {
//        serverRotation.setYaw(((C03PacketPlayerMixin) packet).getPitch());
//        serverRotation.setPitch(((C03PacketPlayerMixin) packet).getYaw());
//    }
    private static double getDifferent(Vector3d player, Vector3d entity) {
        entity = entity.reverse();
        Vector3d diffVec = player.add(entity);
        return Math.sqrt(diffVec.lengthSqr());
    }
    //获取朝向
    public static Rotation faceToTarget(EntityLivingBase entityLivingBase){
        Minecraft minecraft = MinecraftInstance.getMinecraft();
        if(minecraft.thePlayer == null || minecraft.theWorld == null){
            return null;
        }
        return new Rotation((float) assistFaceEntity(minecraft.thePlayer,entityLivingBase), (float) targetPitch(minecraft.thePlayer,entityLivingBase));
    }
    //设置角度
    public static void setServerRotation(Rotation toRotation){
        if(toRotation != null){
            RotationUtil.setServerRotation(toRotation.getYaw(),toRotation.getPitch());
        }
    }
    public static void setServerRotation(float yaw,float pitch){
        targetRotation.setYaw(yaw);
        targetRotation.setPitch(pitch);
    }
//    public static void rotation(Packet<?> packet){
//        ((C03PacketPlayerMixin) packet).setYaw(targetRotation.getYaw());
//        ((C03PacketPlayerMixin) packet).setYaw(targetRotation.getPitch());
//    }
    private static double targetYaw(EntityLivingBase entity1,EntityLivingBase entity2){
        Vector3d player3d = new Vector3d(entity1.posX, 0, entity1.posZ);
        Vector3d entity3d = new Vector3d(entity2.posX, 0, entity2.posZ);

        double a = getDifferent(player3d, entity3d);
        double b = player3d.getZ() - entity3d.getZ();
        double v1 = AngleHelper.Trigonometric_Radian(AngleHelper.Type.COS, (float) (Math.abs(b) / (a == 0 ? 0.01f : a)));
        double v3 = AngleHelper.RadianToAngle((float) v1);
        Vector3d to = new Vector3d(entity3d.getX() - player3d.getX(), 0, entity3d.getZ() - player3d.getZ());
        if (to.getZ() < 0) {
            v3 = 180 - v3;
        }
        if (to.getX() > 0) {
            v3 = 360 - v3;
        }
        //Minecraft的脑瘫设定 旋转的时候一直会增加,出现摆头现象很正常 这个bug在1.16.5没有
        return AngleHelper.Angle_Fix(v3);
    }
    private static double targetPitch(EntityLivingBase entity1,EntityLivingBase entity2){

        double entity2Height = entity2.height / 2 + entity2.posY;
        double playerEyeHeight = entity1.getEyeHeight() + entity1.posY;

        double diffY = entity2Height - playerEyeHeight;
        double diffEyeToCenter = getDifferent(new Vector3d(entity1.posX,entity1.posY + entity1.getEyeHeight(),entity1.posZ),new Vector3d(entity2.posX,entity2Height,entity2.posZ));

        float sin = (float) (Math.abs(diffY) / (diffEyeToCenter == 0 ? 0.01f : diffEyeToCenter));
        return (diffY > 0 ? -1 : 1) * MathHelper.wrapAngleTo180_float((float) AngleHelper.Trigonometric_Angle(AngleHelper.Type.SIN, (sin > 1 ? 0.99f : sin)));
    }
    public static double assistFaceEntity(EntityLivingBase entity1,EntityLivingBase entity2) {
        //entity1 自己
        double diffX = entity2.posX - entity1.posX;
        double diffZ = entity2.posZ - entity1.posZ;
//        double yDifference;
//            yDifference = entity2.posY + entity2.getEyeHeight() - (entity1.posY + entity1.getEyeHeight());
//        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float rotationYaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
//        float rotationPitch = (float) -(Math.atan2(yDifference, dist) * 180.0D / Math.PI);
        return rotationYaw;
    }
}
