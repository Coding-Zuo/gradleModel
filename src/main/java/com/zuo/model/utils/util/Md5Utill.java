package com.zuo.model.utils.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.security.MessageDigest;

public class Md5Utill {
    /**
     * 字符串MD5
     * @param pwd
     * @return
     */
    public final static String md5(String pwd) {
        try {
            //使用平台的默认字符集将此 String 编码为 byte序列，并将结果存储到一个新的 byte数组中
            byte[] content = pwd.getBytes();

            //返回经过加密后的字符串
            return md5(content);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 文件MD5
     * @param path 文件路径
     * @return
     */
    public  final static String fileMd5(String path) {
        try {
            byte[] content = FileUtils.readFileToByteArray(new File(path));
            return md5(content);
        } catch (Exception e) {
            return null;
        }
    }

    private final static String md5(byte[] content) {
        //用于加密的字符
        char md5String[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            //信息摘要是安全的单向哈希函数，它接收任意大小的数据，并输出固定长度的哈希值。
            MessageDigest mdInst = MessageDigest.getInstance("MD5");

            //MessageDigest对象通过使用 update方法处理数据， 使用指定的byte数组更新摘要
            mdInst.update(content);

            // 摘要更新之后，通过调用digest（）执行哈希计算，获得密文
            byte[] md = mdInst.digest();

            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {   //  i = 0
                str[k++] = md5String[byte0 >>> 4 & 0xf];    //    5
                str[k++] = md5String[byte0 & 0xf];   //   F
            }

            //返回经过加密后的字符串
            return new String(str);

        } catch (Exception e) {
            return null;
        }
    }
}
