package com.qgstudio.authorisation.util;


/**
 * @program: ClientDemo
 * @description:
 * @author: stop.yc
 * @create: 2022-07-29 09:33
 **/
public class Declassify {
    /**
     * @Description: 检验
     * @Param: [lic]
     * @return: int
     * @Author: stop.yc
     * @Date: 2022/7/28
     */
    public static boolean check(String lic) throws Exception {

        String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCwMGl48mquEjgZRen2Wi0BejRgp0qVhY0YwKHwjcTN4TMprdAK0jABI+Rl1ckHPwX4rkwx03e3NLhAKis/uqEpsw9/YMkOut8qWRQkh9KAzAos5dN1DGgM2XXm1EluJKB/AvwBdgrHgxuuG+uhKbi2arh3/OY4oxqtfPkkvhRexwIDAQAB";
        //激活

        if (StringUtil.isEmpty(lic)) {
            return false;
        }
        //1.把code前24位拆出来
        String aesKey = lic.substring(0, 24);

        //2.解析长度
        int encDataLength = Integer.parseInt(lic.substring(24, 27), 16);

        //3.把密文截取出来
        String encData = lic.substring(27, 27 + encDataLength);

        //4.截取签名信息
        String sign = lic.substring(27 + encDataLength);

        //5.校验
        boolean verify = RSAUtil.verify(encData, RSAUtil.getPublicKey(PUBLIC_KEY), sign);

        System.out.println("校验结果:  " + verify);

        if (!verify) {
            return false;
        }
        String decrypt = AesUtil.decrypt(encData, aesKey);

        return '0' != (decrypt.charAt(0));
    }
}

