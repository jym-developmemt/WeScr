package we.base.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AESUtil {

	private static final String AES = "AES";

	/** 日志 */
	protected static Logger logger = LoggerFactory.getLogger(AESUtil.class);

	public static String decrypt(String content, String aesKey) {
		if (StringUtils.isBlank(content) || StringUtils.isBlank(aesKey)) {
			logger.error("[AESUtil.decrypt] encrypt or key is empty");
			return null;
		}

		try {
			byte[] raw = aesKey.getBytes();
			SecretKeySpec skeySpec = new SecretKeySpec(raw, AES);
			Cipher cipher = Cipher.getInstance(AES);
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			return new String(cipher.doFinal(Hex.decodeHex(content.toCharArray())));
		} catch (Exception e) {
			logger.error("AESUtil.decryptAES fail, error:[{}]", e.getMessage());
		}
		return null;
	}

	public static String encrypt(String content, String aesKey) {

		if (StringUtils.isBlank(content) || StringUtils.isBlank(aesKey)) {
			logger.error("[AESUtil.encrypt] encrypt or key is empty");
			return null;
		}

		try {
			byte[] raw = aesKey.getBytes();
			SecretKeySpec skeySpec = new SecretKeySpec(raw, AES);
			Cipher cipher = Cipher.getInstance(AES);
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			return new String(Hex.encodeHex(cipher.doFinal(content.getBytes())));
		} catch (Exception e) {
			logger.error("AESUtil.decryptAES fail, error:[{}]", e.getMessage());
			return null;
		}
	}
}
