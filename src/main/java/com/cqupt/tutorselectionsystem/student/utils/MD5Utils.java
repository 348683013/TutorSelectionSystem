package com.cqupt.tutorselectionsystem.student.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密器
 */
public class MD5Utils {
	/**
	 * 使用md5的算法进行加密
	 */
	public static String md5(String plainText) {
		byte[] secretBytes = null;
		try {
			secretBytes = MessageDigest.getInstance("md5").digest(
					plainText.getBytes());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("没有md5这个算法！");
		}
		String md5code = new BigInteger(1, secretBytes).toString(16);// 16进制数字
		// 如果生成数字未满32位，需要前面补0
		for (int i = 0; i < 32 - md5code.length(); i++) {
			md5code = "0" + md5code;
		}
		return md5code;
	}

	/**
	 * 可逆的的加密解密方法；两次是解密，一次是加密
	 * @param inStr
	 * @return
	 */
	public static String convertMD5(String inStr){

		char[] a = inStr.toCharArray();
		for (int i = 0; i < a.length; i++){
			a[i] = (char) (a[i] ^ 't');
		}
		String s = new String(a);
		return s;

	}



	public static void main(String[] args) {
		String s = md5("f2667ff6-00bd-46fc-96d5-db93ae50f4f4");
		System.out.println("MD5后："+s);
		System.out.println("MD5后再加密："+convertMD5(s));
		System.out.println("MD5加密后解密："+convertMD5(convertMD5(s)));
		String s2 = convertMD5("f2667ff6-00bd-46fc-96d5-db93ae50f4f4");
		System.out.println("可逆的加密解密方法之加密："+s2);
		System.out.println("可逆的加密解密方法之解密："+convertMD5(s2));
	}

}

