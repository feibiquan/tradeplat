package com.xfpay.utils.common;

import com.alibaba.fastjson.JSONObject;
import com.xfpay.utils.kit.StreamKit;

import javax.crypto.Cipher;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class RSAUtils {
	/**
	 * 加密算法RSA
	 */
	public static final String KEY_ALGORITHM = "RSA";

	/**
	 * 签名算法
	 */
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

	/**
	 * 获取公钥的key
	 */
	private static final String PUBLIC_KEY = "RSAPublicKey";

	/**
	 * RSA2
	 */
	public static final String SIGN_TYPE_RSA2 = "RSA2";
	/**
	 * SHA1WithRSA
	 */
	public static final String SIGN_ALGORITHMS = "SHA1WithRSA";
	/**
	 * SHA256WithRSA
	 */
	public static final String SIGN_SHA256RSA_ALGORITHMS = "SHA256WithRSA";
	/**
	 * 获取私钥的key
	 */
	private static final String PRIVATE_KEY = "RSAPrivateKey";

	/**
	 * RSA最大加密明文大小
	 */
	private static final int MAX_ENCRYPT_BLOCK = 117;

	/**
	 * RSA最大解密密文大小
	 */
	private static final int MAX_DECRYPT_BLOCK = 128;

	/**
	 * <p>
	 * 生成密钥对(公钥和私钥)
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> genKeyPair() throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		keyPairGen.initialize(1024);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		Map<String, Object> keyMap = new HashMap<String, Object>(2);
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}

	/**
	 * <p>
	 * 用私钥对信息生成数字签名
	 * </p>
	 * 
	 * @param data
	 *            已加密数据
	 * @param privateKey
	 *            私钥(BASE64编码)
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String sign(byte[] data, String privateKey, String signType) throws Exception {
		if (KEY_ALGORITHM.equals(signType)) {
			return rsaSign(data, privateKey, "UTF-8");
		} else if (SIGN_TYPE_RSA2.equals(signType)) {
			return rsa256Sign(data, privateKey, "UTF-8");
		} else {
			throw new RuntimeException("Sign Type is Not Support : signType=" + signType);
		}

	}

	public static PrivateKey getPrivateKeyFromPKCS8(String algorithm, InputStream ins) throws Exception {

		if (ins == null || StringUtils.isEmpty(algorithm)) {
			return null;
		}

		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

		byte[] encodedKey = Base64Utils.decode(StreamKit.readText(ins));

		return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
	}

	private static String rsa256Sign(byte[] data, String privateKey, String string) throws Exception {

		PrivateKey priKey = getPrivateKeyFromPKCS8(KEY_ALGORITHM, new ByteArrayInputStream(privateKey.getBytes()));

		java.security.Signature signature = java.security.Signature.getInstance(SIGN_SHA256RSA_ALGORITHMS);
		signature.initSign(priKey);
		signature.update(data);
		return Base64Utils.encode(signature.sign());
	}

	private static String rsaSign(byte[] data, String privateKey, String string) throws Exception {

		PrivateKey priKey = getPrivateKeyFromPKCS8(KEY_ALGORITHM, new ByteArrayInputStream(privateKey.getBytes()));
		java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
		signature.initSign(priKey);
		signature.update(data);
		return Base64Utils.encode(signature.sign());
	}

	/**
	 * <p>
	 * 校验数字签名
	 * </p>
	 * 
	 * @param data
	 *            已加密数据
	 * @param publicKey
	 *            公钥(BASE64编码)
	 * @param sign
	 *            数字签名
	 * 
	 * @return
	 * @throws Exception
	 * 
	 */
	public static boolean verify(byte[] data, String publicKey, String sign, String signType) throws Exception {
		if (KEY_ALGORITHM.equals(signType)) {
			return verifyRSA(data, publicKey, sign);
		} else if (SIGN_TYPE_RSA2.equals(signType)) {
			return verifyRSA2(data, publicKey, sign);
		} else {
			throw new RuntimeException("verify Type is Not Support : signType=" + signType);
		}

	}

	private static boolean verifyRSA2(byte[] data, String publicKey, String sign) throws Exception {
		PublicKey pubKey = getPublicKeyFromX509("RSA", publicKey);
		java.security.Signature signature = java.security.Signature.getInstance(SIGN_SHA256RSA_ALGORITHMS);
		signature.initVerify(pubKey);
		signature.update(data);
		return signature.verify(Base64Utils.decode(sign));
	}

	private static boolean verifyRSA(byte[] data, String publicKey, String sign) throws Exception {
		PublicKey pubKey = getPublicKeyFromX509("RSA", publicKey);
		java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
		signature.initVerify(pubKey);
		signature.update(data);
		return signature.verify(Base64Utils.decode(sign));
	}

	private static PublicKey getPublicKeyFromX509(String algorithm, String publicKey) throws Exception {
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
		byte[] encodedKey = Base64Utils.decode(publicKey);
		return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
	}

	/**
	 * <P>
	 * 私钥解密
	 * </p>
	 * 
	 * @param encryptedData
	 *            已加密数据
	 * @param privateKey
	 *            私钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey) throws Exception {
		Key privateK = getPrivateKeyFromPKCS8(KEY_ALGORITHM, new ByteArrayInputStream(privateKey.getBytes()));

		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, privateK);
		int inputLen = encryptedData.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 瀵规暟鎹垎娈佃В瀵�
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();

		return decryptedData;
	}

	/**
	 * <p>
	 * 公钥解密
	 * </p>
	 * 
	 * @param encryptedData
	 *            已加密数据
	 * @param publicKey
	 *            公钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey) throws Exception {
		Key publicK = getPublicKeyFromX509(KEY_ALGORITHM, publicKey);
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, publicK);
		int inputLen = encryptedData.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return decryptedData;
	}

	/**
	 * <p>
	 * 公钥加密
	 * </p>
	 * 
	 * @param data
	 *            源数据
	 * @param publicKey
	 *            公钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception {
		Key pubKey = getPublicKeyFromX509(KEY_ALGORITHM, publicKey);

		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 瀵规暟鎹垎娈靛姞瀵�
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptedData = Base64.getEncoder().encode(out.toByteArray());
		out.close();

		return encryptedData;
	}

	 /**
     * <p>
     * 私钥加密
     * </p>
     * 
     * @param data 源数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String privateKey)
            throws Exception {
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

	/**
	 * <p>
	 * 获取私钥
	 * </p>
	 * 
	 * @param keyMap
	 *            密钥对
	 * @return
	 * @throws Exception
	 */
	public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PRIVATE_KEY);
		return Base64Utils.encode(key.getEncoded());
	}

	/**
	 * <p>
	 * 获取公钥
	 * </p>
	 * 
	 * @param keyMap
	 *            密钥对
	 * @return
	 * @throws Exception
	 */
	public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PUBLIC_KEY);
		return Base64Utils.encode(key.getEncoded());
	}

	public static void main(String[] args) {
		try {
			Map<String, Object> map = RSAUtils.genKeyPair();
			Map<String, String> json=new HashMap<>();
			json.put("sigin", "dfadsfsadfs");
			json.put("name1", "打法是否打法是否打法是否打法是否打法是否打法是否打法是否打法是否打法是否打法是否打法是否打法是否打法是否打法是否打法是否打法是否打法是否打法是否打法是否打法是否打法是否打法是否打法是否打法是否打法是否打法是否打法是否打法是否打法是否打法是否打法是否");
			json.put("name2", "打法是否");
			json.put("name3", "打法是否");
			json.put("name4", "打法是否");
			json.put("name5", "打法是否");
			json.put("name6", "打法是否");
			json.put("name7", "打法是否");
			json.put("name8", "打法是否");
			json.put("name9", "打法是否");
			json.put("name2", "打法是否");
			json.put("name10", "打法是否");
			
			String publicKeyStr=getPublicKey(map);
			String privateKeyStr=getPrivateKey(map);
			String content=JSONObject.toJSONString(json);
			String sign=RSAUtils.sign(content.getBytes(), privateKeyStr, RSAUtils.SIGN_TYPE_RSA2);
			System.out.println(sign);
			System.out.println(RSAUtils.verify(content.getBytes(), publicKeyStr, sign, RSAUtils.SIGN_TYPE_RSA2));
			
			//System.out.println(RSAUtils.decryptByPublicKey(sign.getBytes(), publicKeyStr));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
