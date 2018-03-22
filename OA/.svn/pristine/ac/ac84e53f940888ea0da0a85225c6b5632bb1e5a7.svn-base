package cn.tempus.dao;

import java.util.Map;



public class BasicSqlProvider {

	public String GetAllData(String tablename){
	
		return "select * from "+tablename;
	}
	

	public String GetAllDataCount(String tablename){
		return "select count(*) from "+tablename;
	}
	
	public String GetAllDataWithWhere(Map<String, Object> args){
		return "select * from "+args.get("#TABLENAME").toString();
	}
	
	public String SelectListBySql(String sql){
		return sql;
	}
	
	public String SelectCountBySql(String sql){
		return sql;
	}
	
	public String SelectListBySqlWithWhere(Map<String, Object> args){
		return args.get("#SQL").toString();
	}
	
	public String SelectCountBySqlWithWhere(Map<String, Object> args){
		return args.get("#SQL").toString();
	}
	
	public String InsertDataBySql(Map<String, Object> args){
		return args.get("#SQL").toString();
	}
	
	public String UpdateDataBySql(Map<String, Object> args){
		return args.get("#SQL").toString();
	}
	
	public String DeleteDataBySql(Map<String, Object> args){
		return args.get("#SQL").toString();
	}
	
	
	
}
