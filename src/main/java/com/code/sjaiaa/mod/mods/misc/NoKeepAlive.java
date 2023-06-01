package com.code.sjaiaa.mod.mods.misc;

import com.code.sjaiaa.event.PacketEvent;
import com.code.sjaiaa.mod.BaseMod;
import com.code.sjaiaa.mod.ModType;
import com.code.sjaiaa.util.Chat;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraft.network.Packet;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.server.S00PacketKeepAlive;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;

/**
 * @author sjaiaa
 * @date 2023/5/25 18:03
 * @discription
 */
public class NoKeepAlive extends BaseMod {
    public NoKeepAlive() {
        super("HookKeepAlive", ModType.MISC);
    }

    @Override
    public void onEnable() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FileOutputStream outputStream = new FileOutputStream("out.txt");
                    PrintWriter writer = new PrintWriter(outputStream);
                    LaunchClassLoader classLoader = (LaunchClassLoader)NoKeepAlive.class.getClassLoader();
                    Map<String, Class<?>> cachedClasses = ReflectionHelper.getPrivateValue(LaunchClassLoader.class, classLoader, "cachedClasses");
                    Set<Map.Entry<String, Class<?>>> entries = cachedClasses.entrySet();
                    for (Map.Entry<String, Class<?>> entry : entries) {
                        writer.write(entry.getKey() + " -> " + entry.getValue() + "\n");
                        writer.flush();
                    }
                    writer.close();
                    Chat.onMessage("写入完成");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        }).start();
    }

    @Override
    public void onPacketEvent(PacketEvent event) {
    }
}
