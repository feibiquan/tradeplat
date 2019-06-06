/******************************************************************************
 * Copyright (C) 2014 ShenZhen YiHua Computer Co., Ltd.
 * All Rights Reserved.
 * 本软件为深圳怡化电脑股份有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.xfpay.utils.common;

import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @ClassName: SerializeUtil
 * @Description: TODO
 * @author John Gu
 * @date 2013-9-14上午10:21:50
 */
public class SerializeUtil {
	/** 写日志文件 */
	public final static Logger logger = Logger.getLogger(SerializeUtil.class);

	/**
	 * @Description: 序列化
	 * @author John Gu
	 * @date 2013-9-14上午10:22:12
	 * @param object
	 * @return
	 */
	public static byte[] serialize(Object object) {
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			// 序列化
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			byte[] bytes = baos.toByteArray();
			return bytes;
		} catch (Exception e) {
			logger.error("【异常提示信息】"+e);
		}
		return null;
	}

	/**
	 * @Description: 反序列化
	 * @author John Gu
	 * @date 2013-9-14上午10:22:25
	 * @param bytes
	 * @return
	 */
	public static Object unSerialize(byte[] bytes) {
		ByteArrayInputStream bais = null;
		try {
			// 反序列化
			bais = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception e) {
		}
		return null;
	}
}
