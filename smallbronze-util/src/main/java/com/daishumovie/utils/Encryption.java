package com.daishumovie.utils;

import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Encryption {
	// $%#$##@#@9887776$%#$##@#
	private static final byte[] key = { 0x24, 0x25, 0x23, 0x24, 0x23, 0x23, 0x40, 0x23, 0x40, 0x39, 0x38, 0x38, 0x37,
			0x37, 0x37, 0x36, 0x24, 0x25, 0x23, 0x24, 0x23, 0x23, 0x40, 0x23 };
	// @9887776
	private static final byte[] keyiv = { 0x40, 0x39, 0x38, 0x38, 0x37, 0x37, 0x37, 0x36 };

	public static String newPasswordHash(String password, String securitySale) {
		Rfc2898DeriveBytesImpl keyGenerator = null;
		try {
			String md5Value = Md5.getMD5(password);
			byte[] data = md5Value.getBytes("UTF-16");
			// 去掉多余字节，怎么产生的尚不清楚
			byte[] data1 = new byte[data.length - 2];
			System.arraycopy(data, 3, data1, 0, data.length - 3);
			data1[data1.length - 1] = 0;
			byte[] str5 = TripleDESCrypto.des3EncodeCBC(key, keyiv, data1);
			String base = Base64Utils.encodeToString(str5);
			base = base.replaceAll("[\\t\\n\\r]", "");
			byte[] b = Base64Utils.decodeFromString(securitySale);
			keyGenerator = new Rfc2898DeriveBytesImpl(base, b, 10000);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (keyGenerator != null) {
			byte[] bKey = keyGenerator.getBytes(24);
			return Base64Utils.encodeToString(bKey);
		}
		return null;
	}

	public static String createSalt() {
		Random random=new Random();
		byte[] salt1 = new byte[16];
		random.nextBytes(salt1);
        return Base64Utils.encodeToString(salt1);
    }

}

class Rfc2898DeriveBytesImpl {

	private Mac _hmacSha1;
	private byte[] _salt;
	private int _iterationCount;

	private byte[] _buffer = new byte[20];
	private int _bufferStartIndex = 0;
	private int _bufferEndIndex = 0;
	private int _block = 1;

	/**
	 * Creates new instance.
	 * 
	 * @param password
	 *            The password used to derive the key.
	 * @param salt
	 *            The key salt used to derive the key.
	 * @param iterations
	 *            The number of iterations for the operation.
	 * @throws NoSuchAlgorithmException
	 *             HmacSHA1 algorithm cannot be found.
	 * @throws InvalidKeyException
	 *             Salt must be 8 bytes or more. -or- Password cannot be null.
	 */
	Rfc2898DeriveBytesImpl(byte[] password, byte[] salt, int iterations)
			throws NoSuchAlgorithmException, InvalidKeyException {
		if ((salt == null) || (salt.length < 8)) {
			throw new InvalidKeyException("Salt must be 8 bytes or more.");
		}
		if (password == null) {
			throw new InvalidKeyException("Password cannot be null.");
		}
		this._salt = salt;
		this._iterationCount = iterations;
		this._hmacSha1 = Mac.getInstance("HmacSHA1");
		this._hmacSha1.init(new SecretKeySpec(password, "HmacSHA1"));
	}

	/**
	 * Creates new instance.
	 * 
	 * @param password
	 *            The password used to derive the key.
	 * @param salt
	 *            The key salt used to derive the key.
	 * @param iterations
	 *            The number of iterations for the operation.
	 * @throws NoSuchAlgorithmException
	 *             HmacSHA1 algorithm cannot be found.
	 * @throws InvalidKeyException
	 *             Salt must be 8 bytes or more. -or- Password cannot be null.
	 * @throws UnsupportedEncodingException
	 *             UTF-8 encoding is not supported.
	 */
	Rfc2898DeriveBytesImpl(String password, byte[] salt, int iterations)
			throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
		this(password.getBytes("UTF8"), salt, iterations);
	}

	/**
	 * Returns a pseudo-random key from a password, salt and iteration count.
	 * 
	 * @param count
	 *            Number of bytes to return.
	 * @return Byte array.
	 */
	byte[] getBytes(int count) {
		byte[] result = new byte[count];
		int resultOffset = 0;
		int bufferCount = this._bufferEndIndex - this._bufferStartIndex;

		if (bufferCount > 0) { // if there is some data in buffer
			if (count < bufferCount) { // if there is enough data in buffer
				System.arraycopy(this._buffer, this._bufferStartIndex, result, 0, count);
				this._bufferStartIndex += count;
				return result;
			}
			System.arraycopy(this._buffer, this._bufferStartIndex, result, 0, bufferCount);
			this._bufferStartIndex = this._bufferEndIndex = 0;
			resultOffset += bufferCount;
		}

		while (resultOffset < count) {
			int needCount = count - resultOffset;
			this._buffer = this.func();
			if (needCount > 20) { // we one (or more) additional passes
				System.arraycopy(this._buffer, 0, result, resultOffset, 20);
				resultOffset += 20;
			} else {
				System.arraycopy(this._buffer, 0, result, resultOffset, needCount);
				this._bufferStartIndex = needCount;
				this._bufferEndIndex = 20;
				return result;
			}
		}
		return result;
	}

	private byte[] func() {
		this._hmacSha1.update(this._salt, 0, this._salt.length);
		byte[] tempHash = this._hmacSha1.doFinal(getBytesFromInt(this._block));

		this._hmacSha1.reset();
		byte[] finalHash = tempHash;
		for (int i = 2; i <= this._iterationCount; i++) {
			tempHash = this._hmacSha1.doFinal(tempHash);
			for (int j = 0; j < 20; j++) {
				finalHash[j] = (byte) (finalHash[j] ^ tempHash[j]);
			}
		}
		if (this._block == 2147483647) {
			this._block = -2147483648;
		} else {
			this._block += 1;
		}

		return finalHash;
	}

	private static byte[] getBytesFromInt(int i) {
		return new byte[] { (byte) (i >>> 24), (byte) (i >>> 16), (byte) (i >>> 8), (byte) i };
	}

}

class TripleDESCrypto {
	/**
	 * CBC加密
	 * 
	 * @param key
	 *            密钥
	 * @param keyiv
	 *            IV
	 * @param data
	 *            明文
	 * @return Base64编码的密文
	 * @throws Exception
	 */
	static byte[] des3EncodeCBC(byte[] key, byte[] keyiv, byte[] data) throws Exception {
		Key desKey = null;
		DESedeKeySpec spec = new DESedeKeySpec(key);
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		desKey = keyfactory.generateSecret(spec);
		Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");
		IvParameterSpec ips = new IvParameterSpec(keyiv);
		cipher.init(Cipher.ENCRYPT_MODE, desKey, ips);
		byte[] bOut = cipher.doFinal(data);
		return bOut;
	}
}