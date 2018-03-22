package cn.tempus.kuaidi100;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tempus.commons.BaseService;

@RestController
public class Kuaidi100Controller{
	
	@Autowired
	private Kuaidi100Service myservice;
	
	@Autowired
	BaseService baseService;
	
	@PostMapping("/kuaidi100/sub")
	public Map<String, Object> sub(){
		Map<String, Object> result = myservice.sub();
		return result;
	}
	
}
