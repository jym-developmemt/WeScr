/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.base.util;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.util.StringUtils;

/**
 * 加密解密工具类
 *
 * @author cp0
 * @since 0.0
 */
public class EncryptorUtil {

	// 加密模式
	private static final String ENCRYPT_MODE = "DES";

	/**
	 * MD5加密
	 *
	 * @param strText 原文字
	 * @return 变换后文字
	 */
	public static String MD5(String strText) {
		if (StringUtils.isEmpty(strText)) {
			return "";
		}

		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = strText.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Encrypts the string with the salt index
	 *
	 * @param orig
	 * @return
	 */
	public static String encryptString(String orig, String saltIndex) {
		if (StringUtils.isEmpty(orig)) {
			return "";
		}

		byte[] algorithm = saltIndex.getBytes();
		SecretKeySpec sksSpec = new SecretKeySpec(algorithm, ENCRYPT_MODE);
		byte[] encryptDataByte = new byte[orig.length()];
		try {
			Cipher cipher = Cipher.getInstance(ENCRYPT_MODE);
			cipher.init(Cipher.ENCRYPT_MODE, sksSpec);
			encryptDataByte = cipher.doFinal(orig.getBytes());
		} catch (Exception e) {
			return orig;
		}
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < encryptDataByte.length; i++) {
			buf.append(Integer.toString((encryptDataByte[i] & 0xf0) >> 4, 16));
			buf.append(Integer.toString((encryptDataByte[i] & 0x0f), 16));
		}
		return buf.toString().toUpperCase();
	}

	/**
	 * decode the Encryption
	 *
	 * @param code
	 * @return
	 */
	public static String decryptString(String orig, String saltIndex) {
		if (StringUtils.isEmpty(orig)) {
			return "";
		}

		try {
			if (orig == null) {
				return null;
			}

			byte[] algorithm = saltIndex.getBytes();
			SecretKeySpec sksSpec = new SecretKeySpec(algorithm, ENCRYPT_MODE);
			byte[] encryptDataByte = new byte[orig.length() / 2];
			for (int i = 0; i < encryptDataByte.length; i++) {
				String buf = orig.substring(i * 2, (i * 2) + 2);
				int num = Integer.parseInt(buf, 16);
				if (num >= 128) {
					num = num - 256;
				}
				encryptDataByte[i] = new Integer(num).byteValue();
			}

			byte[] dataByte = new byte[0];

			Cipher cipher = Cipher.getInstance(ENCRYPT_MODE);
			cipher.init(Cipher.DECRYPT_MODE, sksSpec);
			dataByte = cipher.doFinal(encryptDataByte);

			return new String(dataByte);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * BASE64加密
	 *
	 * @param orig
	 * @return
	 */
	public static String encodeStringBase64(String text) {
		try {
			Base64 base64 = new Base64();
			byte[] textByte = text.getBytes("UTF-8");
			return base64.encodeToString(textByte);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * BASE64解密
	 *
	 * @param orig
	 * @return
	 */
	public static String decodeStringBase64(String text) {
		try {
			Base64 base64 = new Base64();
			return new String(base64.decode(text), "UTF-8");
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * SHA加密
	 *
	 * @param orig
	 * @return
	 */
	public static String shaEncode(String inStr) {
		try {
			MessageDigest sha = MessageDigest.getInstance("SHA");
			byte[] byteArray = inStr.getBytes("UTF-8");
			byte[] shaBytes = sha.digest(byteArray);
			StringBuffer hexValue = new StringBuffer();
			for (int i = 0; i < shaBytes.length; i++) {
				int val = ((int) shaBytes[i]) & 0xff;
				if (val < 16) {
					hexValue.append("0");
				}
				hexValue.append(Integer.toHexString(val));
			}
			return hexValue.toString();
		} catch (Exception e) {
			return null;
		}
	}
}
