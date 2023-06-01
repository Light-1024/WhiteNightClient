package com.code.sjaiaa.util;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author sjaiaa
 * @date 2023/5/23 20:57
 * @discription
 */
public class FileUtils {
    public static JSONObject readerFileToJson(File file) throws Exception {
        FileInputStream inputStream = new FileInputStream(file);
        BufferedReader read = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        String line;
        StringBuilder builder = new StringBuilder();
        while ((line = read.readLine()) != null) {
            builder.append(line);
        }
        return JSON.parseObject(builder.toString());
    }

    public static boolean writeJsonToFile(JSONObject object, File file) {
        try {
            //测试是否覆盖
            FileWriter writer = new FileWriter(file);
            String s = object.toJSONString();
            writer.write(s);
            writer.flush();
            writer.close();

        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
