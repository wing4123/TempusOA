package cn.tempus.api.payment;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tempus.commons.BaseService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class ApiPaymentController{
	
	@Autowired
	private ApiPaymentService myservice;
	
	@Autowired
	BaseService baseService;
	
	@PostMapping("api/payment")
	public Map<String, Object> save(){
		Map<String, Object> result = myservice.save();
		return result;
	}
	
}
