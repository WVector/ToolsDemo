package com.vector.libtools.algorithm;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

/**
 * Created by Vector
 * on 2016/9/2 0002.
 */
public class DES3 {
    /**
     * 密钥算法
     */
    public static final String KEY_ALGORITHM = "DESede";

    /**
     * 加密/解密算法/工作模式/填充方式
     */
    public static final String CIPHER_ALGORITHM = "DESede/ECB/PKCS5Padding";
    private static final String UTF8 = "UTF-8";


    /**
     * 生成密钥
     *
     * @return byte[] 二进制密钥
     */
    public static byte[] initkey() throws Exception {

        //实例化密钥生成器
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        //初始化密钥生成器
        kg.init(168);
        //生成密钥
        SecretKey secretKey = kg.generateKey();
        //获取二进制密钥编码形式
        return secretKey.getEncoded();
    }
    /**
     * 生成密钥
     * @return
     */
    public static String obtianKey() {

        try {
            return Base64Utils.encode(initkey());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 转换密钥
     *
     * @param key 二进制密钥
     * @return Key 密钥
     */
    public static Key toKey(byte[] key) throws Exception {
        //实例化Des密钥
        DESedeKeySpec dks = new DESedeKeySpec(key);
        //实例化密钥工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
        //生成密钥
        SecretKey secretKey = keyFactory.generateSecret(dks);
        return secretKey;
    }

    /**
     * 加密数据
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return byte[] 加密后的数据
     */
    public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        //还原密钥
        Key k = toKey(key);
        //实例化
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        //初始化，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, k);
        //执行操作
        return cipher.doFinal(data);
    }

    /**
     * 解密数据
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return byte[] 解密后的数据
     */
    public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        //欢迎密钥
        Key k = toKey(key);
        //实例化
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        //初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, k);
        //执行操作
        return cipher.doFinal(data);
    }

    public static String encrypt(String data, String key) {
        try {
           return Base64Utils.encode(encrypt(data.getBytes(UTF8), Base64Utils.decode(key)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    public static String decrypt(String data, String key) {
        try {
          return   new String(encrypt(data.getBytes(UTF8), Base64Utils.decode(key)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
