package cn.tempus.businesshospitality;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tempus.commons.BaseService;

@Controller
@RequestMapping("/BusinessHospitality")
public class BusinessHospitalityController {
	
	@Autowired
	BusinessHospitalityService myservice;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	BaseService baseService;
	
	@RequestMapping("/Create")
	public String Create(){
		baseService.initmenu("/MyWorkFlow/Initiate");
		return "businesshospitality/create";
	}
	
	@RequestMapping("/Save")
	@ResponseBody
	public int Save(){
		int result = myservice.Save();
		return result;
	}
	
	@RequestMapping("/Edit")
	public String Edit(){
		String id = request.getParameter("id");
		Map<String,Object> returnObject = myservice.InitialEditData(id);
		
		request.setAttribute("returnObject", returnObject);
		baseService.initmenu("/MyWorkFlow/Initiate");
		return "businesshospitality/edit";
	}
	
	@RequestMapping("/Delete")
	@ResponseBody
	public boolean Delete(@RequestParam String id){
		boolean result = myservice.Delete(id);
		return result;
	}
	
	@RequestMapping("/WFShow")
	public String WFShow(HttpServletResponse response){
		String id = request.getParameter("id");
		Map<String,Object> returnObject = myservice.InitialShowData(id);
		
		request.setAttribute("returnObject", returnObject);
		return "businesshospitality/wf_show";
	}
	
	@RequestMapping("/WFTurnDown")
	public String WFTurnDown(){
		String id = request.getParameter("id");
		Map<String,Object> returnObject = myservice.InitialEditData(id);
		
		request.setAttribute("returnObject", returnObject);
		return "businesshospitality/wf_turndown";
	}
	
}
