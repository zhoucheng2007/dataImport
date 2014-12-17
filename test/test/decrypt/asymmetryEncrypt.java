/**
 * 
 */
package test.decrypt;

import java.math.BigInteger;

/**
 * @author zhoucheng
找两个素数：
p=47
q=59
这样
n=p*q=2773
t=(p-1)*(q-1)=2668
取e=63，满足e<t并且e和t互素
用perl简单穷举可以获得满足 e*d%t ==1的数d：
C:\Temp>perl -e "foreach $i (1..9999){ print($i),last if $i*63%2668==1 }"
847
即d＝847
 */
public class asymmetryEncrypt {
	
	int p=47;
	
	int q=59;
	
	int e=63;
	
	/**
	 * 
	 * @param x 明文
	 * @return
	 */

	public   int   encryptAndDecrypt(int x) {

		int d=getPrivateKeyByPublickey(e);			

		int y=encrypt(x, d);	

		int x2=decrypt(y, e);
		
		
		return y;

	}
	
	public int getN(int p,int q) {
		return p*q;
	}
	
	public int getT(int p,int q) {
		return (p-1)*(q-1);
	}
	
	
	public void setE(int e) {
		this.e = e;
	}

	public int getPrivateKeyByPublickey(int e) {
		int d=0;	
		int t=getT(p,q);	
		if(t%e==0) {
			System.out.println("e取值错误，取模余数为："+t%e);
			return Integer.MAX_VALUE;
		}
		for(int i=0;i<9999;i++) {
			if((i*e)%t==1) {
				d=i;
				break;
			}
		}
		System.out.println("公钥为："+e+"\n"+"私钥为 "+d);
		return d;
	}
	
	public int encrypt(int x,int d) {
		int n=getN(p,q);
		BigInteger b1=new BigInteger(String.valueOf(1));
		for(int i=0;i<d;i++) {
			b1=b1.multiply(new BigInteger(String.valueOf(x)));
		}	
		//密文
		int y=b1.mod(new BigInteger(String.valueOf(n))).intValue();
		System.out.println("明文为："+x+"\n"+"密文为 "+y);
		return y;
	}
	
	public int decrypt(int y,int e) {
		int n=getN(p,q);
		BigInteger b2=new BigInteger(String.valueOf(1));
		for(int i=0;i<e;i++) {
			b2=b2.multiply(new BigInteger(String.valueOf(y)));
		}
		
		//密文
		int x2=b2.mod(new BigInteger(String.valueOf(n))).intValue();
		
		System.out.println("密文为："+y+"\n"+"解密后明文为 "+x2);
		return x2;
	}
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		asymmetryEncrypt ae=new asymmetryEncrypt();
		//设置公钥
		ae.setE(63);
		//加解密
		ae.encryptAndDecrypt(322);

	}
}
