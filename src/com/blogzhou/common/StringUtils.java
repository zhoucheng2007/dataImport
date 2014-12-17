package com.blogzhou.common;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StringUtils {
	  private static final int MAGIC_NUM_8192 = 8192;
	  private static final String MESSAGE_DIGEST_METHOD = "MD5";
	  private static final int MESSAGE_DIGEST_LENGTH = 16;
	  private static final int MAGIC_NUM_0X0F = 15;
	  private static final int MAGIC_NUM_0X04 = 4;
	  private static final String MESSAGE_DIGESET_STRING_ENCODING = "UTF-8";

	  public static final String hash(String data)
	  {
	    MessageDigest digest = null;
	    try {
	      digest = MessageDigest.getInstance("MD5");
	      digest.update(data.getBytes("UTF-8"));

	      return encodeHex(digest.digest());
	    }
	    catch (NoSuchAlgorithmException e)
	    {
	      throw new Error("摘要算法出错，找不到算法");
	    }
	    catch (UnsupportedEncodingException e) {
	      throw new Error("摘要算法出错，不支持的编码");
	    }
	  }

	  public static final String encodeHex(byte[] bytes)
	  {
	    char[] xlat = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	    char[] buf = new char[bytes.length << 1];

	    int i = 0; for (int n = 0; i < bytes.length; ) {
	      int c = bytes[i];
	      buf[n] = xlat[(0xF & c >> 4)];
	      buf[(n + 1)] = xlat[(0xF & c)];

	      ++i; n += 2;
	    }

	    return new String(buf);
	  }

	  public static String strEncode(String str, String srcEnc, String desEnc)
	  {
	    if ((str == null) || (srcEnc == null) || (desEnc == null) || (srcEnc.equals(desEnc)))
	      return str;

	    try
	    {
	      return new String(str.getBytes(srcEnc), desEnc);
	    } catch (UnsupportedEncodingException e) {
	      throw new Error(e);
	    }
	  }
}
