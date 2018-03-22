package cn.tempus.kuaidi100;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

@Service
public class Kuaidi100Service {
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	NamedParameterJdbcTemplate namedJdbc;
	
	public Map<String, Object> sub(){
		String param = request.getParameter("param");
		JSONObject paramJSON = JSONObject.parseObject(param);
		
		
		
		return paramJSON;
	}
}	

