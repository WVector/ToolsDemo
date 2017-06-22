package com.vector.libtools;

import com.vector.libtools.algorithm.AES;
import com.vector.libtools.algorithm.Base64Utils;
import com.vector.libtools.algorithm.CoderUtils;
import com.vector.libtools.algorithm.DES;
import com.vector.libtools.algorithm.DES3;
import com.vector.libtools.algorithm.DESCoder;
import com.vector.libtools.algorithm.DH;
import com.vector.libtools.algorithm.DSA;
import com.vector.libtools.algorithm.IDCard;
import com.vector.libtools.algorithm.PBE;
import com.vector.libtools.algorithm.RSA;

import org.junit.Test;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.IvParameterSpec;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    private static final byte[] iv = {1, 2, 3, 4, 5, 6, 7, 8};

    @Test
    public void addition_isCorrect() throws Exception {

        IDCard idCard = new IDCard();
        System.out.println(idCard.verify("150222199001272c34"));

        assertEquals(4, 2 + 2);
    }

    @Test
    public void timer() throws Exception {


        assertEquals(4, 2 + 2);
    }

    @Test
    public void des() throws Exception {
//        String decryptDES = DES.encrypt("对称加密", "12345678");
//        System.out.println(">" + decryptDES + "<");
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        for (byte b : zeroIv.getIV()) {
            System.out.println(b);
        }
        assertEquals(4, 2 + 2);
    }

    @Test
    public void desede() throws Exception {
        String str = "喔喔";
//        System.out.println("原文：\t" + str);
//        //初始化密钥
//        byte[] key = DESede.initkey();
//        String desedeKey = Base64Utils.encode(key);
//        System.out.println("密钥：\t" + desedeKey);

        //MuBex1ihFQ7EHz5oGc516bbCj/uKVPRd

        String desedeKey = "MuBex1ihFQ7EHz5oGc516bbCj/uKVPRd";

        //加密数据
        byte[] data = DES3.encrypt(str.getBytes(), Base64Utils.decode(desedeKey));
        System.out.println("加密后：\t" + Base64Utils.encode(data));
        //解密数据
//        data = DESede.decrypt(data, Base64Utils.decode(desedeKey));
//        System.out.println("解密后：\t" + new String(data));

//        System.out.println(DESede.encrypt(str, "23223323"));

//        82K+y6JSdNo=
//        M1co0J/2yFk= M1co0J/2yFk=
        assertEquals(4, 2 + 2);
    }

    @Test
    public void desede1() throws Exception {
//        String desedeKey = "12121212";
//
//        Base64Utils.encode(DES3.encrypt("we".getBytes(), desedeKey.getBytes()));


        assertEquals(4, 2 + 2);
    }

    @Test
    public void des2() throws Exception {
        String str = "DES";
        System.out.println("原文：" + str);
        //初始化密钥
        byte[] key = DESCoder.initkey();
        System.out.println("密钥：" + Base64Utils.encode(key));
        //加密数据
        byte[] data = DESCoder.encrypt(str.getBytes(), key);
        System.out.println("加密后：" + Base64Utils.encode(data));
        //解密数据
        data = DESCoder.decrypt(data, key);
        System.out.println("解密后：" + new String(data));


        assertEquals(4, 2 + 2);
    }

    @Test
    public void des3() throws Exception {
        String desedeKey = "1qazxsw2";

        System.out.println(Base64Utils.encode(DESCoder.encrypt("we".getBytes(), desedeKey.getBytes())));

//        U0HcTqbZDQQ=
        System.out.println(DES.encrypt("we", desedeKey));
        assertEquals(4, 2 + 2);
    }

    @Test
    public void aes() throws Exception {
        String str = "AES";
        System.out.println("原文：" + str);
        //初始化密钥
        byte[] key = AES.initkey();
        System.out.println("密钥：" + Base64Utils.encode(key));
        //加密数据
        byte[] data = AES.encrypt(str.getBytes(), key);
        System.out.println("加密后：" + Base64Utils.encode(data));
        //解密数据
        data = AES.decrypt(data, key);
        System.out.println("解密后：" + new String(data));
        assertEquals(4, 2 + 2);
    }

    @Test
    public void pbe() throws Exception {
        //待加密数据
        String str = "PBE";
        //设定的口令密码
        String password = "azsxadc";

        System.out.println("原文：\t" + str);
        System.out.println("密码：\t" + password);

        //初始化盐
        byte[] salt = PBE.initSalt();
        System.out.println("盐：\t" + Base64Utils.encode(salt));
        //加密数据
        byte[] data = PBE.encrypt(str.getBytes(), password, salt);
        System.out.println("加密后：\t" + Base64Utils.encode(data));
        //解密数据
        data = PBE.decrypt(data, password, salt);
        System.out.println("解密后：" + new String(data));
        assertEquals(4, 2 + 2);
    }

    @Test
    public void dh() throws Exception {
        //生成甲方的密钥对
        Map<String, Object> keyMap1 = DH.initKey();
        //甲方的公钥
        byte[] publicKey1 = DH.getPublicKey(keyMap1);

        //甲方的私钥
        byte[] privateKey1 = DH.getPrivateKey(keyMap1);
        System.out.println("甲方公钥：\n" + Base64Utils.encode(publicKey1));
        System.out.println("甲方私钥：\n" + Base64Utils.encode(privateKey1));

        //由甲方的公钥产生的密钥对
        Map<String, Object> keyMap2 = DH.initKey(publicKey1);
        byte[] publicKey2 = DH.getPublicKey(keyMap2);
        byte[] privateKey2 = DH.getPrivateKey(keyMap2);
        System.out.println("乙方公钥：\n" + Base64Utils.encode(publicKey2));
        System.out.println("乙方私钥：\n" + Base64Utils.encode(privateKey2));

        //组装甲方的本地加密密钥,由乙方的公钥和甲方的私钥组合而成
        byte[] key1 = DH.getSecretKey(publicKey2, privateKey1);
        System.out.println("甲方的本地密钥：\n" + Base64Utils.encode(key1));

        //组装乙方的本地加密密钥，由甲方的公钥和乙方的私钥组合而成
        byte[] key2 = DH.getSecretKey(publicKey1, privateKey2);
        System.out.println("乙方的本地密钥：\n" + Base64Utils.encode(key2));


//        System.out.println("================密钥对构造完毕，开始进行加密数据的传输=============");
//        String str="密码交换算法";
//        System.out.println("\n===========甲方向乙方发送加密数据==============");
//        System.out.println("原文:"+str);
//        System.out.println("===========使用甲方本地密钥对进行数据加密==============");
//        //甲方进行数据的加密
//        byte[] code1=DHCoder.encrypt(str.getBytes(), key1);
//        System.out.println("加密后的数据："+Base64Utils.encode(code1));
//
//        System.out.println("===========使用乙方本地密钥对数据进行解密==============");
//        //乙方进行数据的解密
//        byte[] decode1=DHCoder.decrypt(code1, key2);
//        System.out.println("乙方解密后的数据："+new String(decode1)+"\n\n");
//
//        System.out.println("===========反向进行操作，乙方向甲方发送数据==============\n\n");
//
//        str="乙方向甲方发送数据DH";
//
//        System.out.println("原文:"+str);
//
//        //使用乙方本地密钥对数据进行加密
//        byte[] code2=DHCoder.encrypt(str.getBytes(), key2);
//        System.out.println("===========使用乙方本地密钥对进行数据加密==============");
//        System.out.println("加密后的数据："+Base64Utils.encode(code2));
//
//        System.out.println("=============乙方将数据传送给甲方======================");
//        System.out.println("===========使用甲方本地密钥对数据进行解密==============");
//
//        //甲方使用本地密钥对数据进行解密
//        byte[] decode2=DHCoder.decrypt(code2, key1);
//
//        System.out.println("甲方解密后的数据："+new String(decode2));
        assertEquals(4, 2 + 2);
    }

    @Test
    public void rsa() throws Exception {

        //初始化密钥
        //生成密钥对
        Map<String, Object> keyMap = RSA.initKey();
        //公钥
        byte[] publicKey = RSA.getPublicKey(keyMap);

        //私钥
        byte[] privateKey = RSA.getPrivateKey(keyMap);
        System.out.println("公钥：\n" + Base64Utils.encode(publicKey));
        System.out.println("私钥：\n" + Base64Utils.encode(privateKey));

//        System.out.println("================密钥对构造完毕,甲方将公钥公布给乙方，开始进行加密数据的传输=============");
//        String str="RSA密码交换算法";
//        System.out.println("\n===========甲方向乙方发送加密数据==============");
//        System.out.println("原文:"+str);
//        //甲方进行数据的加密
//        byte[] code1=RSACoder.encryptByPrivateKey(str.getBytes(), privateKey);
//        System.out.println("加密后的数据："+Base64Utils.encode(code1));
//        System.out.println("===========乙方使用甲方提供的公钥对数据进行解密==============");
//        //乙方进行数据的解密
//        byte[] decode1=RSACoder.decryptByPublicKey(code1, publicKey);
//        System.out.println("乙方解密后的数据："+new String(decode1)+"\n\n");
//
//        System.out.println("===========反向进行操作，乙方向甲方发送数据==============\n\n");
//
//        str="乙方向甲方发送数据RSA算法";
//
//        System.out.println("原文:"+str);
//
//        //乙方使用公钥对数据进行加密
//        byte[] code2=RSACoder.encryptByPublicKey(str.getBytes(), publicKey);
//        System.out.println("===========乙方使用公钥对数据进行加密==============");
//        System.out.println("加密后的数据："+Base64Utils.encode(code2));
//
//        System.out.println("=============乙方将数据传送给甲方======================");
//        System.out.println("===========甲方使用私钥对数据进行解密==============");
//
//        //甲方使用私钥对数据进行解密
//        byte[] decode2=RSACoder.decryptByPrivateKey(code2, privateKey);
//
//        System.out.println("甲方解密后的数据："+new String(decode2));
    }

    @Test
    public void initKey() throws Exception {

        //实例化密钥生成器
        KeyGenerator kg = KeyGenerator.getInstance("AES");
        //初始化密钥生成器，AES要求密钥长度为128位、192位、256位
        kg.init(128);
        //生成密钥
        SecretKey secretKey = kg.generateKey();
        //获取二进制密钥编码形式
        byte[] encoded = secretKey.getEncoded();

        System.out.println(encoded.length);

        for (byte b : encoded) {
            System.out.println(b);
        }


        assertEquals(4, 2 + 2);
    }

    @Test
    public void dhKeyGenerate() throws Exception {

        //实例化密钥生成器
        String KEY_ALGORITHM = "DH";
        int KEY_SIZE = 512;


        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        //初始化密钥生成器
        keyPairGenerator.initialize(KEY_SIZE);
        //生成密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        //甲方公钥
        DHPublicKey publicKey = (DHPublicKey) keyPair.getPublic();
        //甲方私钥
        DHPrivateKey privateKey = (DHPrivateKey) keyPair.getPrivate();

        System.out.println("公钥" + Base64Utils.encode(publicKey.getEncoded()));
        System.out.println("私钥" + Base64Utils.encode(privateKey.getEncoded()));

        assertEquals(4, 2 + 2);
    }

    @Test
    public void rsaKeyGenerate() throws Exception {

        //实例化密钥生成器
        String KEY_ALGORITHM = "RSA";
        int KEY_SIZE = 512;


        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        //初始化密钥生成器
        keyPairGenerator.initialize(KEY_SIZE);
        //生成密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        //甲方公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        //甲方私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        System.out.println("公钥" + Base64Utils.encode(publicKey.getEncoded()));
        System.out.println("私钥" + Base64Utils.encode(privateKey.getEncoded()));

        assertEquals(4, 2 + 2);
    }

    /**
     * 使用RSA签名
     *
     * @throws Exception
     */
    @Test
    public void rsaSign() throws Exception {
        //初始化密钥
        //生成密钥对
        Map<String, Object> keyMap = RSA.initKey();
        //公钥
        byte[] publicKey = RSA.getPublicKey(keyMap);

        //私钥
        byte[] privateKey = RSA.getPrivateKey(keyMap);
        System.out.println("公钥：\n" + Base64Utils.encode(publicKey));
        System.out.println("私钥：\n" + Base64Utils.encode(privateKey));

        System.out.println("================密钥对构造完毕,甲方将公钥公布给乙方，开始进行加密数据的传输=============");
        String str = "RSA数字签名算法";
        System.out.println("原文:" + str);
        //甲方进行数据的加密
        byte[] sign = RSA.sign(str.getBytes(), privateKey);
        System.out.println("产生签名：" + Base64Utils.encode(sign));
        //验证签名
        boolean status = RSA.verify(str.getBytes(), publicKey, sign);
        System.out.println("状态：" + status + "\n\n");

        assertEquals(4, 2 + 2);
    }

    /**
     * DSA签名
     *
     * @throws Exception
     */
    @Test
    public void dsaSign() throws Exception {
        //初始化密钥
        //生成密钥对
        Map<String, Object> keyMap = DSA.initKey();
        //公钥
        byte[] publicKey = DSA.getPublicKey(keyMap);

        //私钥
        byte[] privateKey = DSA.getPrivateKey(keyMap);
        System.out.println("公钥：\n" + Base64Utils.encode(publicKey));
        System.out.println("私钥：\n" + Base64Utils.encode(privateKey));

        System.out.println("================密钥对构造完毕,甲方将公钥公布给乙方，开始进行加密数据的传输=============");
        String str = "DSA数字签名算法";
        System.out.println("原文:" + str);
        //甲方进行数据的加密
        byte[] sign = DSA.sign(str.getBytes(), privateKey);
        System.out.println("产生签名：" + Base64Utils.encode(sign));
        //验证签名
        boolean status = DSA.verify(str.getBytes(), publicKey, sign);
        System.out.println("状态：" + status + "\n\n");
    }

    @Test
    public void crc23() throws Exception {


        System.out.println(CoderUtils.getFileCRC32("E:\\视频\\电影\\[迅雷下载www.2tu.cc]孤独的生还者.HD1280高清英语中字.mkv"));
        assertEquals(4, 2 + 2);


    }

}