package com.blogzhou.common;

import java.net.InetAddress;

public class UUID {
	 private static final int MAGIC_NUM_8 = 8;
	  private static final int MAGIC_NUM_16 = 16;
	  private static final int MAGIC_NUM_20 = 20;
	  private static final int MAGIC_NUM_28 = 28;
	  private static final int MAGIC_NUM_32 = 32;
	  private static final int IP;
	  private static short counter;
	  private static final int JVM;

	  public static String getUUID()
	  {
	    char[] buf = new char[32];

	    BytesHelper.toHexChars(getIP(), buf, 0);
	    BytesHelper.toHexChars(getJVM(), buf, 8);
	    BytesHelper.toHexChars(getHiTime(), buf, 16);
	    BytesHelper.toHexChars(getLoTime(), buf, 20);
	    BytesHelper.toHexChars(getCount(), buf, 28);

	    return new String(buf);
	  }

	  public static boolean validation(String uuid)
	  {
	    if ((uuid == null) || (uuid.length() != 32))
	      return false;

	    byte[] bt = uuid.getBytes();
	    for (int i = 0; i < 32; ++i)
	      if ((((bt[i] < 97) || (bt[i] > 102))) && (((bt[i] < 48) || (bt[i] > 57))) && (((bt[i] < 65) || (bt[i] > 70))))
	      {
	        return false;
	      }

	    return true;
	  }

	  protected static int getJVM()
	  {
	    return JVM;
	  }

	  protected static synchronized short getCount()
	  {
	    if (counter < 0)
	      counter = 0;

	    short tmp13_10 = counter; counter = (short)(tmp13_10 + 1); return tmp13_10;
	  }

	  protected static int getIP()
	  {
	    return IP;
	  }

	  protected static short getHiTime()
	  {
	    return (short)(int)(System.currentTimeMillis() >>> 32);
	  }

	  protected static int getLoTime()
	  {
	    return (int)System.currentTimeMillis();
	  }

	  static
	  {
	    int ipadd;
	    try
	    {
	      ipadd = BytesHelper.toInt(InetAddress.getLocalHost().getAddress());
	    } catch (Exception e) {
	      ipadd = 0;
	    }
	    IP = ipadd;

	    counter = 0;

	    JVM = (int)(System.currentTimeMillis() >>> 8);
	  }
}
