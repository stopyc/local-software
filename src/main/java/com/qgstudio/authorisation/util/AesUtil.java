package com.qgstudio.authorisation.util;




import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class AesUtil {

    private static final String AES = "AES";
    private static final String UTF8="utf-8";

    public static String getAESKey(int size,String secure) throws NoSuchAlgorithmException {
        KeyGenerator kgen = KeyGenerator.getInstance(AES);

        /**可以根据参数生成固定的秘钥*/
        if (null == secure || secure.equals("")){
            kgen.init(size, new SecureRandom());
        }else{
            kgen.init(size, new SecureRandom(secure.getBytes()));
        }

        /**生成秘钥并转base64编码*/
        SecretKey secretKey = kgen.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();
        String encodeKey = Base64.getEncoder().encodeToString(enCodeFormat);
        System.out.println("AES秘钥::"+encodeKey);
        return encodeKey;
    }



    public static String encrypt(String content, String aesKey) throws Exception {
        /**base64解码秘钥 并转换为AES专用密钥*/
        SecretKeySpec key = new SecretKeySpec(Base64.getDecoder().decode(aesKey), AES);
        Cipher cipher = Cipher.getInstance(AES);
        byte[] byteContent = content.getBytes(UTF8);
        /**初始化为加密模式的密码器 */
        cipher.init(Cipher.ENCRYPT_MODE, key);
        /**加密并转出base64返回密文*/
        byte[] result = cipher.doFinal(byteContent);
        return Base64.getEncoder().encodeToString(result);
    }


    public static String decrypt(String content, String aesKey) throws Exception {
        /**将秘钥,密文 base64字节*/
        byte[] decodeKey = Base64.getDecoder().decode(aesKey);
        byte[] decodeContent = Base64.getDecoder().decode(content);
        /**转换为AES专用密钥*/
        SecretKeySpec key = new SecretKeySpec(decodeKey, AES);
        /**创建 初始化 容器*/
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] result = cipher.doFinal(decodeContent);
        /**返回明文*/
        return new String(result,UTF8);
    }
}