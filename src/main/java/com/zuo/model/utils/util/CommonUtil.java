package com.zuo.model.utils.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class CommonUtil {

    /**
     * 生成新的UUID，不含字符"-"
     * @return
     */
    public static String newUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String newRegCode() {
        return randomCode(8);
    }

    public static String randomCode(int digit) {
        Random random = new Random();
        random.setSeed(new Date().getTime());
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < digit; i++) {
            builder.append(random.nextInt(10));
        }
        return builder.toString();
    }

    /**
     * 截取文件后缀名
     * @param fileName 文件名
     * @return
     */
    public static String getFileExt(String fileName) {
        try {
            int index = fileName.lastIndexOf(".");
            if (index >= 0) {
                return fileName.substring(index + 1);
            } else {
                return "";
            }
        } catch (Exception ex) {
            return "";
        }
    }

    /**
     * 读取文本文件内容
     * @param path
     * @return
     */
    public static String readTxtFile(String path) {
        try {
            byte[] content = FileUtils.readFileToByteArray(new File(path));
            return new String(content, "UTF-8");
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 将文本写入文本文件
     * @param txt
     * @param path
     * @return
     */
    public static Boolean writeTxtFile(String txt, String path) {
        try {
            FileOutputStream fos = new FileOutputStream(path);
            fos.write(txt.getBytes());
            fos.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
