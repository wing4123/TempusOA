package cn.tempus;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.lettuce.DefaultLettucePool;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyTest {
	
	@Autowired
	JdbcTemplate jdbc;
	
	@Autowired
	SimpleJdbcCall jdbcCall;
	
	@Autowired
	DataSource dataSource;
	
	public static void main(String[] args) {
		System.out.println(UUID.randomUUID().toString());
	}
	
	@GetMapping("/updateDepartment")
	public int updateDepartment() {
		int result = 0;
		String sql = "select * from tb_oa_division";
		List<Map<String, Object>> departments = jdbc.queryForList(sql);
		String updateSQL = "update tb_oa_division set flongname=? where fid=?";
		for(Map<String, Object> department:departments) {
			String fid = (String)department.get("FID");
			String longName = getLongName(fid);
			jdbc.update(updateSQL, longName, fid);
			System.out.println(longName);
			result++;
		}
		return result;
	}
	
	public String getLongName(String id) {
		Map<String, Object> department = jdbc.queryForMap("select fname,fparentid from tb_oa_division where fid=?", id);
		if(department.get("FPARENTID")!=null) {
			return getLongName(department.get("FPARENTID").toString()) + " - " + department.get("FNAME");
		}else {
			return department.get("FNAME").toString();
		}
	}
	
	@GetMapping("/call1/{p1}/{p2}")
	public Map<String, Object> testCall1(@PathVariable int p1, @PathVariable int p2) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource);
		SqlParameterSource in = new MapSqlParameterSource().addValue("P1", p1).addValue("P2", p2);
		Map<String, Object> result = jdbcCall.withCatalogName("TB_TEST_PKG").withProcedureName("TEST1").execute(in);
		return result;
	}
	
	@GetMapping("/call2/{p1}/{p2}")
	public Map<String, Object> testCall2(@PathVariable int p1, @PathVariable int p2) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource);
		SqlParameterSource in = new MapSqlParameterSource().addValue("P4", p1).addValue("P5", p2);
		Map<String, Object> result = jdbcCall.withCatalogName("TB_TEST_PKG").withProcedureName("TEST2").execute(in);
		return result;
	}
}
