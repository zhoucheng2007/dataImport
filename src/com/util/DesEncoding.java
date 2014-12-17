package com.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class DesEncoding {

	private String key;

	public DesEncoding(String keyPath) throws FileNotFoundException, IOException,
			ClassNotFoundException {

	}

	public DesEncoding() {

	}
	
	public void getKey(String strKey){
		this.key = strKey;
	}

	/**
	 * @throws Exception
	 */
	public String getEncString(String s){
		try {
			return byte2Hex(s.getBytes("gb2312"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @throws Exception
	 */
	public String getDesString(String s) {
		try {
			return new String(hex2Byte(s), "gb2312");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	protected static String byte2Hex(byte[] arrB) {
		int iLen = arrB.length;
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	protected static byte[] hex2Byte(String strIn) {
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;

		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}
}
