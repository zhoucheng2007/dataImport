package test.decrypt;

import java.security.MessageDigest;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;



public class Base64Test {

	public static void main(String[] args) throws Exception {
		//String s="NjE2NDZkNjk2ZTNhMzEzNDMxMzYzNzM5MzYzMTM3MzkzMDMxMzU=";
		//System.out.print(Base64.decodeFast(s));
		String s="admin";
		System.out.println(byte2hex(s.getBytes()));
		
	}

	static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0xFF);
			if (stmp.length() == 1)
				hs += ("0" + stmp);
			else
				hs += stmp;
		}
		return hs.toUpperCase();
	}
	
	/** 
	 * BASE64解密 
	 *  
	 * @param key 
	 * @return 
	 * @throws Exception 
	 */  
	public static byte[] decryptBASE64(String key) throws Exception {  
	    return (new BASE64Decoder()).decodeBuffer(key);  
	}  
	  
	/** 
	 * BASE64加密 
	 *  
	 * @param key 
	 * @return 
	 * @throws Exception 
	 */  
	public static String encryptBASE64(byte[] key) throws Exception {  
	    return (new BASE64Encoder()).encodeBuffer(key);  
	}  
	
	/** 
	 * MD5加密 
	 *  
	 * @param data 
	 * @return 
	 * @throws Exception 
	 */  
	public static byte[] encryptMD5(byte[] data) throws Exception {  
	  
	    MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);  
	    md5.update(data);  	  
	    return md5.digest();    
	}  
	
    public static byte[] encryptSHA(byte[] data) throws Exception {  	  
        MessageDigest sha = MessageDigest.getInstance(KEY_SHA);  
        sha.update(data);  
        return sha.digest();  
    }  
	
    /** 
     * 初始化HMAC密钥 
     *  
     * @return 
     * @throws Exception 
     */  
    public static String initMacKey() throws Exception {  
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);  
      
        SecretKey secretKey = keyGenerator.generateKey();  
        return encryptBASE64(secretKey.getEncoded());  
    }  
      
    /** 
     * HMAC加密 
     *  
     * @param data 
     * @param key 
     * @return 
     * @throws Exception 
     */  
    public static byte[] encryptHMAC(byte[] data, String key) throws Exception {      
        SecretKey secretKey = new SecretKeySpec(decryptBASE64(key), KEY_MAC);  
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());  
        mac.init(secretKey);        
        return mac.doFinal(data);        
    }  
    
}
