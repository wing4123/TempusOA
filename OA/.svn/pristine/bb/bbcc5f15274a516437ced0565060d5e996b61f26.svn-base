/*
 * @Deloitte.com
 */
package cn.tempus.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;


/**
 * 
 * get some path
 * load some properities file
 * 
 * @author Zhong, An-Jing
 *
 * Mar 10, 2014 11:17:02 AM
 */

public class PropertyUtil {

	private static Properties prop = null;
	
	public static String getClassPath(){
		URL u = Thread.currentThread().getContextClassLoader().getResource("");
		return u.getPath().replaceAll("%20", " ");
	}

	/**
	 * query the properties
	 * @param propName
	 * @return
	 */
	public static String getProp(String propName) {
		String rt = "";
		try {
			if (prop == null) {
				prop = PropertyUtil.load("system.properties");
			}
			rt = prop.getProperty(propName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rt;
	}

	/**
	 * load the property file: filename, it should be placed in classpath
	 * 
	 * @param fileName
	 * @return
	 */
	private static Properties load(String fileName) {
		Properties pro = new Properties();
		try {
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("/config/"+fileName);
			pro.load(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("load config file faiture！");
		}
		return pro;
	}
	
	
	public static String getMD5(String password) { 
		 String reuslt = "";
			try
			{
				MessageDigest md = MessageDigest.getInstance("MD5");				
				md.update(password.getBytes()); 
				byte b[] = md.digest(); 		
				int i; 		
				StringBuffer buf = new StringBuffer(""); 
				for (int offset = 0; offset < b.length; offset++) { 
					i = b[offset]; 
					if(i<0) i+= 256; 
					if(i<16) 
					buf.append("0"); 
					buf.append(Integer.toHexString(i)); 
				} 
				reuslt = buf.toString().substring(10,21);
				
			}
			catch (NoSuchAlgorithmException e)
			{
				e.printStackTrace();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return reuslt;
			
		} 

	
}
