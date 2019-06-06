package com.xfpay.utils.common;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @ClassName ConfigLoader
 * @Description 配置装载类
 * @date  2014年9月26日 下午4:26:31
 */
public class ConfigLoader {
	/** 写日志文件 */
	public final static Logger logger = Logger.getLogger(ConfigLoader.class);

	//配置文件获取属性
	private static ConfigLoader instance;

	// 配置文件a
	private static Properties property = null;

	private ConfigLoader()
	{
        initProperties();
	}

	/**
	 *
	 * 初始化配置文件
	 *
	 */
	private void initProperties()
	{
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("redis.properties");
        property = new Properties();
        try {
            property.load(inputStream);
        } catch (IOException e) {
            logger.error("【异常提示信息】"+e.getMessage());
        }
	}

	public synchronized static ConfigLoader instance() {
		if (instance == null) {
			instance = new ConfigLoader();
		}
		return instance;
	}

	/**
	 *
	 * 获取配置文件值
	 *
	 * @param key
	 * @return
	 */
	public String getVaule(String key) {
		return (String) property.get(key);
	}

	public static void main(String[] args) {
		String value = ConfigLoader.instance().getVaule("redis.port");
		System.out.println(value);
	}

}
