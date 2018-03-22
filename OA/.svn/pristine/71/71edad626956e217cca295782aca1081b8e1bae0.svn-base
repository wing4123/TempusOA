package cn.tempus.dao;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

public interface BasicDaoI {

	@SelectProvider(type = BasicSqlProvider.class, method = "GetAllData")
	public List< HashMap<String,Object> > GetAllData(String tablename);
	
	@SelectProvider(type = BasicSqlProvider.class, method = "GetAllDataCount")
	public int GetAllDataCount(String tablename);
	
	@SelectProvider(type = BasicSqlProvider.class, method = "GetAllDataWithWhere")
	public List< HashMap<String,Object> > GetAllDataWithWhere(Map<String, Object> args);
	
	@SelectProvider(type = BasicSqlProvider.class, method = "SelectListBySql")
	public List< HashMap<String,Object> > SelectListBySql(String sql);
	
	@SelectProvider(type = BasicSqlProvider.class, method = "SelectCountBySql")
	public int SelectCountBySql(String sql);
	
	@SelectProvider(type = BasicSqlProvider.class, method = "SelectListBySqlWithWhere")
	public List< HashMap<String,Object> > SelectListBySqlWithWhere(Map<String, Object> args);
	
	@SelectProvider(type = BasicSqlProvider.class, method = "SelectListBySqlWithWhere")
	public List< LinkedHashMap<String,Object> > SelectListBySqlWithWhere2(Map<String, Object> args);
	
	@SelectProvider(type = BasicSqlProvider.class, method = "SelectCountBySqlWithWhere")
	public int SelectCountBySqlWithWhere(Map<String, Object> args);
	
	@InsertProvider(type = BasicSqlProvider.class,method = "InsertDataBySql") 
	@Options(useGeneratedKeys=false )
	public int InsertData(Map<String, Object> args);
	
	@UpdateProvider(type = BasicSqlProvider.class,method = "UpdateDataBySql") 
	//@Options(useGeneratedKeys=false )
	public int UpdateData(Map<String, Object> args);
	
	@DeleteProvider(type = BasicSqlProvider.class,method = "DeleteDataBySql")
	public int DeleteData(Map<String, Object> args);
	
}
