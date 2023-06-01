package com.code.sjaiaa.util;

import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayServer;

import java.util.ArrayList;

/**
 * @author sjaiaa
 * @date 2023/5/19 20:42
 * @discription
 */
public class PacketUtils  {

    public static int inBound, outBound = 0;
    public static int avgInBound, avgOutBound = 0;

    public static ArrayList<Packet<INetHandlerPlayServer>> packets = new ArrayList<>();

    private static TimeUtil packetTimer = new TimeUtil();
    private static TimeUtil wdTimer = new TimeUtil();

    private static int transCount = 0;
    private static int wdVL = 0;

    private static boolean isInventoryAction(short action) {
        return action > 0 && action < 100;
    }

    public static boolean isWatchdogActive() {
        return wdVL >= 8;
    }


//    public static void onPacket(PacketEvent event) {
//        handlePacket(event.getPacket());
//    }

//    private static void handlePacket(Packet<?> packet) {
//        if (packet.getClass().getSimpleName().startsWith("C")) outBound++;
//        else if (packet.getClass().getSimpleName().startsWith("S")) inBound++;
//
//        if (packet instanceof S32PacketConfirmTransaction)
//        {
//            if (!isInventoryAction(((S32PacketConfirmTransaction) packet).getActionNumber()))
//                transCount++;
//        }
//    }

//    public static void onTick(TickEvent.ClientTickEvent event) {
//        if (packetTimer.isDelay(1000L)) {
//            avgInBound = inBound; avgOutBound = outBound;
//            inBound = outBound = 0;
//            packetTimer.reset();
//        }
//        if (MinecraftInstance.getMinecraft().thePlayer == null || MinecraftInstance.getMinecraft().theWorld == null) {
//            //reset all checks
//            wdVL = 0;
//            transCount = 0;
//            wdTimer.reset();
//        } else if (wdTimer.isDelay(100L)) { // watchdog active when the transaction poll rate reaches about 100ms/packet.
//            wdVL += (transCount > 0) ? 1 : -1;
//            transCount = 0;
//            if (wdVL > 10) wdVL = 10;
//            if (wdVL < 0) wdVL = 0;
//            wdTimer.reset();
//        }
//    }

    //全名
    public static PacketType getPacketType(Packet<?> packet) {
        String className = packet.getClass().getSimpleName();
        if (className.startsWith("C")) {
            return PacketType.CLIENTSIDE;
        } else if (className.startsWith("S")) {
            return PacketType.SERVERSIDE;
        }
        // idk...
        return PacketType.UNKNOWN;
    }
    public enum PacketType {
        SERVERSIDE,
        CLIENTSIDE,
        UNKNOWN
    }
    /*
     * This code is from UnlegitMC/FDPClient. Please credit them when using this code in your repository.
     */
    public static void sendPacketNoEvent(Packet<INetHandlerPlayServer> packet) {
        packets.add(packet);
        MinecraftInstance.getMinecraft().getNetHandler().addToSendQueue(packet);
    }

    public static boolean handleSendPacket(Packet<?> packet) {
        if (packets.contains(packet)) {
            packets.remove(packet);
//            handlePacket(packet); // make sure not to skip silent packets.
            return true;
        }
        return false;
    }

    /**
     * @return wow
     */
    public boolean handleEvents() {
        return true;
    }

}