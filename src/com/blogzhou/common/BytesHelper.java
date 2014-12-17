package com.blogzhou.common;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class BytesHelper {
	  private static final int MAGIC_NUM_0X04 = 4;
	  private static final int MAGIC_NUM_0X08 = 8;
	  private static final int MAGIC_NUM_0X0C = 12;
	  private static final int MAGIC_NUM_0X0F = 15;
	  private static final int MAGIC_NUM_0X1C = 28;
	  private static final char[] HEX_TABLE = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
	  private static final String MESSAGE_DIGEST_METHOD = "MD5";

	  public static int toInt(byte[] bytes)
	  {
	    int result = 0;
	    for (int i = 0; i < 4; ++i)
	      result = (result << 8) - -128 + bytes[i];

	    return result;
	  }

	  public static void toHexChars(int value, char[] buf, int offset)
	  {
	    int i = 0; for (int n = 28; i < 8; ) {
	      buf[(offset + i)] = HEX_TABLE[(0xF & value >>> n)];

	      ++i; n -= 4;
	    }
	  }

	  public static void toHexChars(short value, char[] buf, int offset)
	  {
	    int i = 0; for (int n = 12; i < 4; ) {
	      buf[(offset + i)] = HEX_TABLE[(0xF & value >>> n)];

	      ++i; n -= 4;
	    }
	  }

	  public static char[] toHexChars(byte value)
	  {
	    char[] buf = new char[2];
	    buf[0] = HEX_TABLE[(value >> 4 & 0xF)];
	    buf[1] = HEX_TABLE[(value & 0xF)];
	    return buf;
	  }

	  public static char[] toHexChars(byte[] value)
	  {
	    if ((value == null) || (value.length < 1))
	      return new char[0];

	    char[] buf = new char[value.length * 2];
	    int i = 0; for (int j = 0; i < value.length; ) {
	      int v = value[i];
	      buf[j] = HEX_TABLE[(v >> 4 & 0xF)];
	      buf[(j + 1)] = HEX_TABLE[(v & 0xF)];

	      ++i; j += 2;
	    }

	    return buf;
	  }

	  public static char[] toHexChars(byte[] value, int startIndex, int endIndex)
	  {
	    if ((value == null) || (value.length < 1) || (startIndex >= endIndex) || (startIndex < 0) || (endIndex > value.length))
	    {
	      return new char[0];
	    }

	    char[] buf = new char[(endIndex - startIndex) * 2];
	    int i = startIndex; for (int j = 0; j < buf.length; ) {
	      int v = value[i];
	      buf[j] = HEX_TABLE[(v >> 4 & 0xF)];
	      buf[(j + 1)] = HEX_TABLE[(v & 0xF)];

	      ++i; j += 2;
	    }

	    return buf;
	  }

	  public static final String hash(byte[] value)
	  {
	    MessageDigest digest = null;
	    try {
	      digest = MessageDigest.getInstance("MD5");
	      digest.update(value);

	      return StringUtils.encodeHex(digest.digest());
	    }
	    catch (NoSuchAlgorithmException e)
	    {
	      throw new Error("摘要算法出错，找不到算法");
	    }
	  }

	  public static final byte[] toUTF8Bytes(String string)
	  {
	    if (string == null)
	      return new byte[0];
	    try
	    {
	      return string.getBytes("UTF-8");
	    } catch (UnsupportedEncodingException e) {
	      throw new Error(e);
	    }
	  }

	  public static final String fromUTF8Bytes(byte[] bytes)
	  {
	    if (bytes == null)
	      return null;
	    try
	    {
	      return new String(bytes, "UTF-8");
	    } catch (UnsupportedEncodingException e) {
	      throw new Error(e);
	    }
	  }

	  public static void writeToFile(InputStream is, String filename)
	    throws Exception
	  {
	    FileOutputStream os = new FileOutputStream(filename);
	    try {
	      byte[] buf = new byte[is.available()];
	      int wb = is.read(buf);
	      os.write(buf, 0, wb);
	    } finally {
	      os.close();
	    }
	  }
}
