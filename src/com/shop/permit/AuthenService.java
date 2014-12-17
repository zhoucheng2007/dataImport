package com.shop.permit;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.Signature;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.util.Base64;

import com.util.DesEncoding;

/**
 * 认证和授权的servlet服务
 * 
 * @author zhouqy
 * 
 */
public class AuthenService extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7092110465206512548L;

	private Log logger = LogFactory.getLog(AuthenService.class);

	private Object lock = new Object();

	private static long valideTime = 10 * 60 * 1000;

	protected void service(HttpServletRequest request,
			HttpServletResponse response){
		/**
		 * 获取登录账号
		 */
		try{
			String userId = request.getParameter("USERID");
			String IsAuthenNew = request.getParameter("IsAuthenNew");
			userId = userId.toUpperCase();
			String source = request.getParameter("RESOURCE");
			String appCode = request.getParameter("APP");
			String isHttpClient = request.getParameter("HTTPCLIENT");
			String sampleSSO=request.getParameter("sampleSSO");
			response.setCharacterEncoding("UTF-8");
			if (logger.isDebugEnabled()) {
				logger.debug("======userId========" + userId + "==IsAuthenNew==" + IsAuthenNew
						+ "==source==" + source+"==appCode=="+appCode+"==isHttpClient=="+isHttpClient+"==sampleSSO=="+sampleSSO);
			}
			StringBuffer s = new StringBuffer();
	
			/**
			 * 获取映射的单点登录用户
			 */
			Map ssoMap = this.getAppUserIdByUserIdAndAppCode(userId, appCode);
			String SSOUserId = ssoMap.get("APP_USER_ID") == null ? userId
					: (String) ssoMap.get("APP_USER_ID");
			if (logger.isDebugEnabled()) {
				logger.debug("SSOUri：" + SSOUserId);
			}
			String SSOUri=ssoMap.get("URI") == null ? ""
					: (String) ssoMap.get("URI");
			if (logger.isDebugEnabled()) {
				logger.debug("SSOUri：" + SSOUri);
			}
			if(sampleSSO!=null&&sampleSSO.equals("1")){
				source=SSOUri;
			}
			if(source!=null){
				source=source.replace("|", "&");
				if (logger.isDebugEnabled()) {
					logger.debug("source：" + source);
				}
			}
			if (logger.isDebugEnabled()) {
				logger.debug("获取的映射用户为：" + SSOUserId);
				logger.debug("获取的映射系统地址：" + source);
			}
	
			String expirationTime = getExpirationTime();
			s.append("<Result>");
			s.append("<UserID>" + SSOUserId);
			s.append("</UserID>");
			s.append("<Certificate>pwd</Certificate>");
			s.append("<expirationTime>" + expirationTime);
			s.append("</expirationTime>");
			if (IsAuthenNew == null || IsAuthenNew.equals("")) {
				s.append("<signatureValue>"
						+ getSignatureValueNew(SSOUserId, expirationTime));
				s.append("</signatureValue>");
			} else {
				s.append("<signatureValue>"
						+ getSignatureValue(SSOUserId, expirationTime));
				s.append("</signatureValue>");
			}
			s.append("</Result>");
			String tk = s.toString();
			if (isHttpClient != null && !"".equals(isHttpClient)
					&& "1".equals(isHttpClient)) {
				tk = URLEncoder.encode(tk, "utf-8");
			}
			//http://10.192.0.21:80/v3/jsp/split.jsp?user.cmd?method=getStruUserRoot
			if (source.indexOf("?") == -1) {
				source = source + "?tk=" + tk;
			} else {
				//获得第一个?的位置
				int index=source.indexOf("?");
				if (source.indexOf("?", index+1)==-1){
					//如果只有一个？号
					source = source + "&tk=" + tk;	
				}else{
					//如果多个？号
					if(source.indexOf("split.jsp")==-1){
						//根据第一个点的位置 获得第二个?的位置
						index=source.indexOf("?", index+1);
						try {
							source=replace(source,index+1, "&");
							source = source + "&tk=" + tk;	
						} catch (Throwable e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else{
						//loushang3 _tree
						source = source + "&tk=" + tk;	
					}	
				}
			}
			if (logger.isDebugEnabled()) {
				logger.debug("获取的映射系统地址+tk：" + source);
			}
			response.sendRedirect(source);
		}catch (Exception e){
			logger.error("出错了，",e);
		}

	}

	public Map getAppUserIdByUserIdAndAppCode(String userId, String appCode) {
		Map paramMap = new HashMap();
		paramMap.put("USER_ID", userId);
		paramMap.put("APP_CODE", appCode);
		Map map = V6SqlSessionUtil.getSqlSession().selectOne("AuthenService.commonSelect", paramMap);
		if (map == null) {
			map = new HashMap();
		}
		return map;
	}

	/**
	 * 获取超时限制时间
	 * 
	 * @return
	 */
	private static String getExpirationTime() {

		Calendar calendar = Calendar.getInstance();
		return Long.toString(calendar.getTimeInMillis() + valideTime);
	}

	/**
	 * 私钥签名
	 * 
	 * @param userId
	 * @param expirationTime
	 * @return
	 */
	private String getSignatureValue(String userId, String expirationTime) {

		String encryptedText = "";
		try { 

			// 需要加密的明文字符串
			String plainText = userId + expirationTime;
			byte[] plainByte = plainText.getBytes();
			// 计算散列值
			MessageDigest md = MessageDigest.getInstance("md5");
			plainByte = md.digest(plainByte);
			// 读取私钥文件
			InputStream file = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("private_key.dat");
			ObjectInputStream inputStream = new ObjectInputStream(file);
			PrivateKey privateKey = (PrivateKey) inputStream.readObject();

			Signature signature = Signature.getInstance("DSA");
			signature.initSign(privateKey);

			signature.update(plainByte);

			byte[] bytes = signature.sign();

			// 使用RSA算法加密
			// Cipher cipher = Cipher.getInstance("RSA");
			// cipher.init(Cipher.ENCRYPT_MODE, privateKey);
			// byte[] cipherByte = cipher.doFinal(plainByte);
			// 得到加密值，网络中使用HEX形式传输
			encryptedText = byte2hex(bytes);
		} catch (FileNotFoundException e) {
			if (logger.isDebugEnabled()) {
				logger.debug("can't read PRIVATE KEY file : " + e);
			}
		} catch (IOException e) {
			if (logger.isDebugEnabled()) {
				logger.debug("read PRIVATE KEY file error:" + e);
			}
		} catch (ClassNotFoundException e) {
			if (logger.isDebugEnabled()) {
				logger.debug("PRIVATE KEY file format error:" + e);
			}
		} catch (GeneralSecurityException e) {
			if (logger.isDebugEnabled()) {
				logger.debug("encryption error:" + e);
			}
		} catch (Exception e){
			logger.error("令牌加密出错，"+e);
		}
		return encryptedText;
	}

	private static String byte2hex(byte[] b) {
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
	 * 
	 * 
	 * @param userId
	 * @param expirationTime
	 * @return
	 */
	private static String getSignatureValueNew(String userId,
			String expirationTime) {

		String encryptedText = "";
		try {

			//
			String plainText = userId + ":" + expirationTime;
			DesEncoding des = new DesEncoding();
			encryptedText = des.getEncString(plainText);
			encryptedText = Base64.encodeBase64String(encryptedText.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptedText;
	}
	public static String replace(String str,int n,String newChar) throws Throwable{ 
		String s1=""; 
		String s2=""; 
		try{ 
		s1=str.substring(0,n-1); 
		s2=str.substring(n,str.length()); 
		}catch(Exception ex){ 
		throw new Throwable("替换的位数大于字符串的位数"); 
		} 
		return s1+newChar+s2; 
		} 

}
