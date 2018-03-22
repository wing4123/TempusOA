package cn.tempus.payment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tempus.commons.BaseService;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/Payment")
public class PaymentController{
	
	@Autowired
	private PaymentService myservice;
	
	@Autowired
	BaseService baseService;
	
	@Autowired
	HttpServletRequest request;
	
	@RequestMapping("/Create")
	public String Create(){
		baseService.initmenu( "/MyWorkFlow/Initiate");
		
		Map<String, Object> returnObject = new HashMap<String, Object>();
		List<HashMap<String,Object>> company = baseService.easyDao.SelectListBySql("select fid,fname from TB_OA_department where ftype='2'");
		request.setAttribute("returnObject", returnObject);
		request.setAttribute("company", company);
		
		return "payment/create";
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
		List<HashMap<String,Object>> company = baseService.easyDao.SelectListBySql("select fid,fname from TB_OA_department where ftype='2'");
		request.setAttribute("company", company);
		
		baseService.initmenu( "/MyWorkFlow/Initiate");
		return "payment/edit";
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
		Map<String,Object> returnObject = myservice.InitialEditData(id);
		List<HashMap<String,Object>> company = baseService.easyDao.SelectListBySql("select fid,fname from TB_OA_department where ftype='2'");
		request.setAttribute("company", company);
		
		request.setAttribute("returnObject", returnObject);
		return "payment/wf_show";
	}
	
	@RequestMapping("/WFEdit2")
	public String WFEdit2(HttpServletResponse response){
		String id = request.getParameter("id");
		Map<String,Object> returnObject = myservice.InitialEditData(id);
		List<HashMap<String,Object>> company = baseService.easyDao.SelectListBySql("select fid,fname from TB_OA_department where ftype='2'");
		request.setAttribute("company", company);
		
		request.setAttribute("returnObject", returnObject);
		return "payment/wf_edit2";
	}
	
	@RequestMapping("/WFShow2")
	public String WFShow2(HttpServletResponse response){
		String id = request.getParameter("id");
		String taskid = request.getParameter("taskid");
		Map<String,Object> returnObject = myservice.InitialEditData(id);
		List<HashMap<String,Object>> company = baseService.easyDao.SelectListBySql("select fid,fname from TB_OA_department where ftype='2'");
		request.setAttribute("company", company);
		
		Object taskname =  baseService.easyDao.GetFirstValueBySql("select decode(name_,'财务确认','accounting','出纳付款','teller') from act_ru_task where id_='"+taskid+"'");
		request.setAttribute("taskname", taskname);
		
		request.setAttribute("returnObject", returnObject);
		return "payment/wf_show2";
	}
	
	@RequestMapping("/WFEdit3")
	public String WFEdit3(HttpServletResponse response){
		String id = request.getParameter("id");
		Map<String,Object> returnObject = myservice.InitialEditData(id);
		List<HashMap<String,Object>> company = baseService.easyDao.SelectListBySql("select fid,fname from TB_OA_department where ftype='2'");
		request.setAttribute("company", company);
		
		String sql = "select e.bank_account_name fname from TB_OA_paymentrequest a left join TB_OA_department x on x.fid=a.fpaymentcompany left join fnd_flex_values_vl b on b.description=x.fname and flex_value_set_id=1014867 and attribute4='OA' left join HR_ALL_ORGANIZATION_UNITS c on c.name=b.description left join CE_BANK_ACCT_USES_ALL d on d.org_id=c.organization_id left join CE.CE_BANK_ACCOUNTS e on e.bank_account_id=d.bank_account_id where a.fid='"+id+"'";
		returnObject.put("paymentaccounts", baseService.easyDao.SelectListBySql(sql));
		
		request.setAttribute("returnObject", returnObject);
		return "payment/wf_edit3";
	}
	
	@RequestMapping("/WFShow3")
	public String WFShow3(HttpServletResponse response){
		String id = request.getParameter("id");
		Map<String,Object> returnObject = myservice.InitialEditData(id);
		List<HashMap<String,Object>> company = baseService.easyDao.SelectListBySql("select fid,fname from TB_OA_department where ftype='2'");
		request.setAttribute("company", company);
		
		String sql = "select e.bank_account_name fname from TB_OA_paymentrequest a left join TB_OA_department x on x.fid=a.fpaymentcompany left join fnd_flex_values_vl b on b.description=x.fname and flex_value_set_id=1014867 and attribute4='OA' left join HR_ALL_ORGANIZATION_UNITS c on c.name=b.description left join CE_BANK_ACCT_USES_ALL d on d.org_id=c.organization_id left join CE.CE_BANK_ACCOUNTS e on e.bank_account_id=d.bank_account_id where a.fid='"+id+"'";
		returnObject.put("paymentaccounts", baseService.easyDao.SelectListBySql(sql));
		
		request.setAttribute("returnObject", returnObject);
		return "payment/wf_show3";
	}
	
	@RequestMapping("/WFTurnDown")
	public String WFTurnDown(){
		String id = request.getParameter("id");
		Map<String,Object> returnObject = myservice.InitialEditData(id);
		List<HashMap<String,Object>> company = baseService.easyDao.SelectListBySql("select fid,fname from TB_OA_department where ftype='2'");
		request.setAttribute("company", company);
		
		request.setAttribute("returnObject", returnObject);
		return "payment/wf_turndown";
	}
	
	@RequestMapping("/getSupplierList")
	@ResponseBody
	public Map<String,Object> getSupplierlist(){
		Map<String,Object> args = new HashMap<String,Object>();
		StringBuffer sql = new StringBuffer("select vendor_id,vendor_name from Ap_Suppliers where (vendor_type_lookup_code<>'EMPLOYEE' or vendor_type_lookup_code is null) and (sysdate between start_date_active and end_date_active or end_date_active is null)");
		String search = request.getParameter("search[value]");
		if(!search.equals("")){
			sql.append(" and vendor_name like #{search}");
			args.put("search", "%"+search+"%");
		}
		args.put("#SQL", sql);
		Map<String,Object> result = baseService.getDateTableRecord(args);
		
		return result;
	}
	
	@RequestMapping("/getContractList")
	@ResponseBody
	public Map<String,Object> getContractList(){
		Map<String,Object> args = new HashMap<String,Object>();
		StringBuffer sql = new StringBuffer("select fid,fnumber,fname,fsidename from TB_OA_contract where fcreator=#{user} and fstatus='3'");
		String search = request.getParameter("search[value]");
		if(!search.equals("")){
			sql.append(" and (fnumber like #{search} or fname like #{search})");
			args.put("search", "%"+search+"%");
		}
		args.put("#SQL", sql);
		args.put("user", ((JSONObject) JSONObject.toJSON(request.getSession().getAttribute("USER"))).getString("userid"));
		Map<String,Object> result = baseService.getDateTableRecord(args);
		
		return result;
	}
	
	@RequestMapping("/Accounting")
	@ResponseBody
	public Map<String, Object> Accounting(){
		Map<String, Object> result = myservice.Accounting();
		return result;
	}
	
	@RequestMapping("/Teller")
	@ResponseBody
	public Map<String, Object> Teller(){
		Map<String, Object> result = myservice.Teller();
		return result;
	}
	
}
