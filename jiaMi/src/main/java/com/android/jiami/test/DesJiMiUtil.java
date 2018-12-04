package com.android.jiami.test;

import java.security.InvalidKeyException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * Create by Xiangshifu
 * on 2018/11/22 4:53 PM
 *
 * @see {https://blog.csdn.net/hbcui1984/article/details/5065506}
 *  DES加密和解密过程中，密钥长度都必须是8的倍数
 *
 */
public class DesJiMiUtil {

    /**
     * 加密
     * @param dataSource
     * @param password
     * @return
     */
    public static byte[] descryTo(byte[] dataSource,String password){
        try {
            SecureRandom secureRandom = new SecureRandom();
            DESKeySpec desKeySpec = new DESKeySpec(password.getBytes());
            //创建一个密钥工厂，然后用他把DESKeySpec转换成
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = secretKeyFactory.generateSecret(desKeySpec);
            //Clipher实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES");
             //用密钥初始化Cipher
            cipher.init(Cipher.ENCRYPT_MODE,secretKey,secureRandom);
            //现在获取加密数据
            return cipher.doFinal(dataSource);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

    /**
     * 解密
     * @param src
     * @param password
     * @return
     */
    public static byte[] decrypt(byte[] src,String password){
        try {
            SecureRandom secureRandom = new SecureRandom();
            DESKeySpec desKeySpec = new DESKeySpec(password.getBytes());
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey =  secretKeyFactory.generateSecret(desKeySpec);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE,secretKey,secureRandom);
            return  cipher.doFinal(src);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
