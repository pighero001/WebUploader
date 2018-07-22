package ToolClass.Token;

import ToolClass.EncryptionAlgorithm.AESOperator;
import ToolClass.EncryptionAlgorithm.Base64;

/**
 * @program: WebUploader
 * @description: Token解密
 * @author: Dai Yuanchuan
 * @create: 2018-07-06 10:57
 **/
public class TokenDecryption {

    /**
     * 针对本项目的Token解密
     * @return
     */
    public String Decryption (String token){
        try {
            // Base64解密后用AES解密
            return new AESOperator().getInstance().decrypt(new Base64().decode(token));
        } catch (Exception e) {
            return "{\"errMsg\":\"Token decryption failure\",\"cause\":\""+String.valueOf(e).split(":")[1]+"\"}";
        }
    }
}
