package com.hhyzoa.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

public class PropertiesUtil {
	
	private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
	private static Properties props;
	
	static {
		props =  new  Properties();
		InputStream in = Object.class.getResourceAsStream("/conf/common_config.properties");
		try {
			props.load(in);
			in.close();
		}catch(IOException e) {
			logger.error("加载properties文件失败......");
			e.printStackTrace();
		}
		
		props.list(System.out);
		
	}
	
	public static String getString(String key) {
		return StringUtil.trim(props.getProperty(key));
	}
	
	public static void main(String[] args) {
		System.out.println(PropertiesUtil.getString("DB_BACKUP_SCRIPT"));
	}

}
