package com.vector.libtools.algorithm;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class DES {
    /**
     * 工作模式、填充模式、初始化向量这三种因素一个都不能少。
     */
    private static final byte[] iv = {1, 2, 3, 4, 5, 6, 7, 8};
    private static final String DES_ALGORITHM = "DES";
    private static final String TEST_KEY = "1qazxsw2";
    //算法名称/加密模式/填充方式
    //DES共有四种工作模式-->>ECB：电子密码本模式、CBC：加密分组链接模式、CFB：加密反馈模式、OFB：输出反馈模式
    private static final String CIPHER_ALGORITHM = "DES/CBC/PKCS5Padding";

    /**
     * CBC
     * 需要初始化向量IV，来加密第一块C0.
     * 有点就是比ECB好
     * 缺点不利于并行计算、误差会迭代，还需要初始向量
     * ECB
     * 优点就是简单，可以并行计算，不会迭代误差
     * 缺点就是隐藏不了模式
     */
    private DES() {

    }

    public static String encrypt(String data, String encryptKey) {
        try {
            //初始化向量
            IvParameterSpec zeroIv = new IvParameterSpec(iv);
            SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), DES_ALGORITHM);

            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
            byte[] encryptedData = cipher.doFinal(data.getBytes("UTF-8"));
            return Base64Utils.encode(encryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String decrypt(String data, String decryptKey) {
        try {
            byte[] byteMi = Base64Utils.decode(data);
            IvParameterSpec zeroIv = new IvParameterSpec(iv);
            SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), DES_ALGORITHM);

            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
            byte decryptedData[] = cipher.doFinal(byteMi);
            return new String(decryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String encrypt(String encryptString) {
        return encrypt(encryptString, TEST_KEY);
    }

    public static String decrypt(String decryptString) {
        return decrypt(decryptString, TEST_KEY);
    }
}
