package com.wpy.cqu.xiaodi.encrypt;

import com.orhanobut.logger.Logger;

import org.apache.commons.codec.binary.Base64;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by wangpeiyu on 2018/4/5.
 * 采用AES加码方式
 */
public class AESEncrypt {

    private static IvParameterSpec ivSpec = null;

    private static SecretKeySpec keySpec = null;

    private static final String KEY = "xiaodipy20181688";

    static {
        keySpec = new SecretKeySpec(KEY.getBytes(), "AES");
        ivSpec = new IvParameterSpec(KEY.getBytes());
    }


    public static byte[] Encrypt(byte[] originData) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            return cipher.doFinal(originData);
        } catch (NoSuchAlgorithmException e) {
            Logger.e("NoSuchAlgorithmException,%s", e.toString());
            return null;
        } catch (NoSuchPaddingException e) {
            Logger.e("NoSuchPaddingException,%s", e.toString());
            return null;
        } catch (InvalidAlgorithmParameterException e) {
            Logger.e("InvalidAlgorithmParameterException,%s", e.toString());
            return null;
        } catch (InvalidKeyException e) {
            Logger.e("InvalidKeyException,%s", e.toString());
            return null;
        } catch (BadPaddingException e) {
            Logger.e("BadPaddingException,%s", e.toString());
            return null;
        } catch (IllegalBlockSizeException e) {
            Logger.e("IllegalBlockSizeException,%s", e.toString());
            return null;
        }
    }

    public static byte[] Decrypt(byte[] encryptData) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            return cipher.doFinal(encryptData);
        } catch (NoSuchAlgorithmException e) {
            Logger.e("NoSuchAlgorithmException,%s", e.toString());
            return null;
        } catch (NoSuchPaddingException e) {
            Logger.e("NoSuchPaddingException,%s", e.toString());
            return null;
        } catch (InvalidAlgorithmParameterException e) {
            Logger.e("InvalidAlgorithmParameterException,%s", e.toString());
            return null;
        } catch (InvalidKeyException e) {
            Logger.e("InvalidKeyException,%s", e.toString());
            return null;
        } catch (BadPaddingException e) {
            Logger.e("BadPaddingException,%s", e.toString());
            return null;
        } catch (IllegalBlockSizeException e) {
            Logger.e("IllegalBlockSizeException,%s", e.toString());
            return null;
        }
    }

    public static byte[] Base64Encrypt(byte[] data) {
        Base64 base64 = new Base64();
        return base64.encode(data);
    }

    public static byte[] Base64Decrypt(byte[] data) {
        Base64 base64 = new Base64();
        return base64.decode(data);
    }

    public static String Base64AESEncrypt(String data) {
        byte[] encryptPassByte = AESEncrypt.Encrypt(data.getBytes());
        String encryptPass = new String(AESEncrypt.Base64Encrypt(encryptPassByte));
        return encryptPass;
    }

}
