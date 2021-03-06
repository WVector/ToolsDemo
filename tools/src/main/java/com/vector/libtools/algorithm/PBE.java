package com.vector.libtools.algorithm;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

/**
 * Created by Vector
 * on 2016/9/5 0005.
 */
public class PBE {
    /**
     * JAVA6支持以下任意一种算法
     * PBEWITHMD5ANDDES
     * PBEWITHMD5ANDTRIPLEDES
     * PBEWITHSHAANDDESEDE
     * PBEWITHSHA1ANDRC2_40
     * PBKDF2WITHHMACSHA1
     * */
    public static final String ALGORITHM="PBEWITHMD5andDES";

    /**
     * 迭代次数
     * */
    public static final int ITERATION_COUNT=100;

    /**
     * 盐初始化
     * 盐长度必须为8字节
     * @return byte[] 盐
     * */
    public static byte[] initSalt() throws Exception{
        //实例化安全随机数
        SecureRandom random=new SecureRandom();
        //产出盐
        return random.generateSeed(8);
    }

    /**
     * 转换密钥
     * @param password 密码
     * @return Key 密钥
     * */
    private static Key toKey(String password) throws Exception{
        //密钥彩礼转换
        PBEKeySpec keySpec=new PBEKeySpec(password.toCharArray());
        //实例化
        SecretKeyFactory keyFactory=SecretKeyFactory.getInstance(ALGORITHM);
        //生成密钥
        SecretKey secretKey=keyFactory.generateSecret(keySpec);

        return secretKey;
    }
    /**
     * 加密
     * @param data 待加密数据
     * @param password 密码
     * @param salt 盐
     * @return byte[] 加密数据
     *
     * */
    public static byte[] encrypt(byte[] data,String password,byte[] salt) throws Exception{
        //转换密钥
        Key key=toKey(password);
        //实例化PBE参数材料
        PBEParameterSpec paramSpec=new PBEParameterSpec(salt,ITERATION_COUNT);
        //实例化
        Cipher cipher=Cipher.getInstance(ALGORITHM);
        //初始化
        cipher.init(Cipher.ENCRYPT_MODE, key,paramSpec);
        //执行操作
        return cipher.doFinal(data);
    }
    /**
     * 解密
     * @param data 待解密数据
     * @param password 密码
     * @param salt 盐
     * @return byte[] 解密数据
     *
     * */
    public static byte[] decrypt(byte[] data,String password,byte[] salt) throws Exception{
        //转换密钥
        Key key=toKey(password);
        //实例化PBE参数材料
        PBEParameterSpec paramSpec=new PBEParameterSpec(salt,ITERATION_COUNT);
        //实例化
        Cipher cipher=Cipher.getInstance(ALGORITHM);
        //初始化
        cipher.init(Cipher.DECRYPT_MODE, key,paramSpec);
        //执行操作
        return cipher.doFinal(data);
    }

    /**
     *
     * @param args 使用PBE算法对数据进行加解密
     * @throws Exception 使用PBE算法对数据进行加解密
     */
    public static void main(String[] args) throws Exception {
        //待加密数据
        String str="PBE";
        //设定的口令密码
        String password="azsxadc";

        System.out.println("原文：\t"+str);
        System.out.println("密码：\t"+password);

        //初始化盐
        byte[] salt= PBE.initSalt();
        System.out.println("盐：\t"+ Base64Utils.encode(salt));
        //加密数据
        byte[] data= PBE.encrypt(str.getBytes(), password, salt);
        System.out.println("加密后：\t"+Base64Utils.encode(data));
        //解密数据
        data= PBE.decrypt(data, password, salt);
        System.out.println("解密后："+new String(data));
    }
}
