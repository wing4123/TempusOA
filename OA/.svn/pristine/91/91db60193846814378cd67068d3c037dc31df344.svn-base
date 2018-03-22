package cn.tempus.utils;

import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;

/**
 * @author 		吴中贤wing4123@163.com
 * @date   		2017年11月21日
 * @description 数据库二进制字段转换
 */
public class LobUtils {

	/**
	 * @param blob
	 * @description Blob转String
	 */
	public static String blob2String(Object blob){
		String blobString = null;
		try {
			blobString = new String(((Blob)blob).getBytes(1, (int)((Blob)blob).length()),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return blobString;
	}
	
	/**
	 * @param clob
	 * @description Clob转String
	 */
	public static String clob2String(Object clob){
		String clobString = null;
		try {
			clobString = ((Clob)clob).getSubString(1, (int)((Clob)clob).length());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return clobString;
	}
	
	/**
	 * @param blob
	 * @return Blob转Byte
	 */
	public static byte[] blob2Byte(Object blob){
		byte[] b = null;
		try {
			b = ((Blob)blob).getBytes(1, (int)((Blob)blob).length());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return b;
	}
}
