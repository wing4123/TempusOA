package cn.tempus.print;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 吴中贤wing4123@163.com
 * @date 2017年11月17日 14点55分
 * @description 表单打印
 */
@Controller
@RequestMapping("/Print")
public class PrintController{
	
	@Autowired
	PrintService myservice;
	
	@Autowired
	HttpServletRequest request;
	
	//出差申请单
	@RequestMapping("/Travel")
	public String Travel(@RequestParam String id){
		Map<String,Object> returnObject = myservice.InitialTravelData(id);
		request.setAttribute("returnObject", returnObject);
		return "print/travel";
	}
	
	//合同审批单
	@RequestMapping("/Contract")
	public String Contract(@RequestParam String id){
		Map<String,Object> returnObject = myservice.InitialContractData(id);
		request.setAttribute("returnObject", returnObject);
		return "print/contract";
	}
	
	//费用报销单
	@RequestMapping("/Reimbursement")
	public String InitialReimbursementData(@RequestParam String id){
		Map<String,Object> returnObject = myservice.InitialReimbursementData(id);
		request.setAttribute("returnObject", returnObject);
		return "print/reimbursement";
	}
	
	//业务招待费申请单
	@RequestMapping("/BusinessHospitality")
	public String BusinessHospitality(@RequestParam String id){
		Map<String,Object> returnObject = myservice.InitialBusinessHospitalityData(id);
		request.setAttribute("returnObject", returnObject);
		return "print/businesshospitality";
	}
	
	//合同付款申请单
	@RequestMapping("/Payment")
	public String Payment(@RequestParam String id){
		Map<String,Object> returnObject = myservice.InitialPaymentData(id);
		request.setAttribute("returnObject", returnObject);
		return "print/payment";
	}
	
}
