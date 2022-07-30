package com.qgstudio.authorisation.util;


import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAUtil {

    private static final String RSA = "RSA";
    private static final String UTF8 = "utf-8";
    /**
     * 算法名称
     */
    private static final String ALGORITHM = "RSA";
    /**
     * 签名算法 MD5withRSA 或 SHA1WithRSA 等
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    /**
     * 密钥长度默认是1024位:
     * 加密的明文最大长度 = 密钥长度 - 11（单位是字节，即byte）
     */
    private static final int KEY_SIZE = 1024;
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * 生成秘钥对
     *
     * @param size   密钥长度 于原文长度对应 以及越长速度越慢
     * @param secure 用于生成秘钥的参数
     * @throws NoSuchAlgorithmException
     */
    public static void genKeyPair(int size, String secure) throws NoSuchAlgorithmException {
        /**基于RSA算法生成公钥和私钥对对象*/
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(RSA);

        /**可以根据参数生成固定的秘钥*/
        if (null == secure || secure.equals("")) {
            keyPairGen.initialize(size, new SecureRandom());
        } else {
            keyPairGen.initialize(size, new SecureRandom(secure.getBytes()));
        }
        /**生成一个密钥对*/
        KeyPair keyPair = keyPairGen.generateKeyPair();
        /**得到私钥 并用base64加密*/
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        String privateKeyString = Base64.getEncoder().encodeToString(privateKey.getEncoded());
        System.out.println("秘钥::" + privateKeyString);
        System.out.println("私钥长度::" + privateKeyString.length());

        /**得到公钥 并用base64加密*/
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        System.out.println("公钥::" + publicKeyString);
        System.out.println("公钥长度::" + publicKeyString.length());
    }

    /**
     * 私钥签名
     *
     * @param data       待签名数据
     * @param privateKey 私钥
     * @return 签名
     */
    public static String sign(String data, PrivateKey privateKey) throws Exception {
        byte[] keyBytes = privateKey.getEncoded();
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        PrivateKey key = keyFactory.generatePrivate(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(key);
        signature.update(data.getBytes());
        return new String(Base64.getEncoder().encode(signature.sign()));  // 对签名内容进行Base64编码加密
    }

    /**
     * 公钥验签
     *
     * @param srcData   原始字符串
     * @param publicKey 公钥
     * @param sign      签名
     * @return 是否验签通过
     */
    public static boolean verify(String srcData, PublicKey publicKey, String sign) throws Exception {
        byte[] keyBytes = publicKey.getEncoded();
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        PublicKey key = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(key);
        signature.update(srcData.getBytes());
        return signature.verify(Base64.getDecoder().decode(sign.getBytes())); // 对验签结果进行Base64编码解密
    }

    /**
     * 公钥加密
     *
     * @param str       加密内容
     * @param publicKey 公钥
     * @return
     * @throws Exception
     */
    public static String encrypt(String str, String publicKey) throws Exception {
        /**base64解码公钥*/
        byte[] decoded = Base64.getDecoder().decode(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance(RSA).generatePublic(new X509EncodedKeySpec(decoded));
        /**RSA加密*/
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        byte[] content = cipher.doFinal(str.getBytes(UTF8));
        /**base64将字节转字符*/
        String outStr = Base64.getEncoder().encodeToString(content);
        return outStr;
    }

    /**
     * 私钥解密
     *
     * @param str        密文
     * @param privateKey 私钥
     * @return
     * @throws Exception
     */
    public static String decrypt(String str, String privateKey) throws Exception {
        /**base64把密文转称字节*/
        byte[] inputByte = Base64.getDecoder().decode(str);
        /**base64解码私钥*/
        byte[] decoded = Base64.getDecoder().decode(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        /**RSA解密*/
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }

    /**
     * 私钥字符串转PrivateKey实例
     *
     * @param privateKey 私钥字符串
     * @return
     */
    public static PrivateKey getPrivateKey(String privateKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        byte[] decodedKey = Base64.getDecoder().decode(privateKey.getBytes("UTF-8"));// 对私钥进行Base64编码解密
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
        return keyFactory.generatePrivate(keySpec);
    }


    /**
     * 公钥字符串转PublicKey实例
     *
     * @param publicKey 公钥字符串
     * @return
     */
    public static PublicKey getPublicKey(String publicKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        byte[] decodedKey = Base64.getDecoder().decode(publicKey.getBytes("UTF-8")); // 对公钥进行Base64编码解密
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        return keyFactory.generatePublic(keySpec);
    }

}