package cn.tempus.commons;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class GlobalCls {
	
public static Properties G_Const_Paramter=LoadParamter();
	
	public static Properties GP = LoadParamter();
	
	private static Properties LoadParamter(){
		InputStream is = GlobalCls.class.getClassLoader().getResourceAsStream("jdbc.properties");
		Properties properties = new Properties();
		try {
			properties.load(is);
			return properties;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
