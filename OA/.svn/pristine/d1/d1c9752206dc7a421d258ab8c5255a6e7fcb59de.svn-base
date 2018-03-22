package cn.tempus.contract.oto;

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
import cn.tempus.dao.EasyDao;

/** 
* @author 吴中贤 wing4123@163.com
* @date 2017年8月24日
* @Description: oto事业部合同
*  
*/
@Controller
@RequestMapping("/Contract2")
public class ContractController2{
	
	@Autowired
	ContractService2 contractservice;
	
	@Autowired
	BaseService baseService;
	
	@Autowired
	EasyDao easyDao;
	
	@Autowired
	HttpServletRequest request;
	
	@RequestMapping("/Create")
	public String Create(){
		String type = request.getParameter("type");
		Map<String,Object> returnObject = new HashMap<String,Object>();
		returnObject.put("type", type);
		
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "select * from TB_OA_contract_type where ftype=#{type}");
		args.put("type", type);
		List<HashMap<String,Object>> typelist = easyDao.SelectListBySqlWithWhere(args);
		returnObject.put("typelist", typelist);
		
		//returnObject.put("company", basicService.SelectListBySql("select flex_value fnumber,description fname from fnd_flex_values_vl where attribute4='OA'"));
		returnObject.put("company", easyDao.SelectListBySql("select fid,fname from TB_OA_department where ftype='2'"));
		returnObject.put("old", true);
		
		request.setAttribute("returnObject", returnObject);
		baseService.initmenu( "/MyWorkFlow/Initiate");
		return "contract/oto/create";
	}
	
	@RequestMapping("/Save")
	@ResponseBody
	public int Save(){
		int result = contractservice.Save();
		return result;
	}
	
	@RequestMapping("/Edit")
	public String Edit(){
		String id = request.getParameter("id");
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "select * from TB_OA_contract where fid=#{id}");
		args.put("id", id);
		HashMap<String,Object> contract = easyDao.GetSinglerData(args);
		Map<String,Object> returnObject = new HashMap<String,Object>();
		returnObject.put("contract", contract);
		
		List<HashMap<String,Object>> typelist = easyDao.SelectListBySql("select * from TB_OA_contract_type where ftype='"+contract.get("FTYPE1")+"'");
		returnObject.put("typelist", typelist);
		
		returnObject.put("company", easyDao.SelectListBySql("select fid,fname from TB_OA_department where ftype='2'"));
	
		request.setAttribute("returnObject", returnObject);
		baseService.initmenu( "/MyWorkFlow/Initiate");
		return "contract/oto/edit";
	}
	
	@RequestMapping("/Delete")
	@ResponseBody
	public boolean Delete(@RequestParam String id){
		boolean result = contractservice.Delete(id);
		return result;
	}
	
	@RequestMapping("/WFShow")
	public String WFShow(HttpServletResponse response){
		String id = request.getParameter("id");
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "select * from TB_OA_contract where fid=#{id}");
		args.put("id", id);
		HashMap<String,Object> contract = easyDao.GetSinglerData(args);
		Map<String,Object> returnObject = new HashMap<String,Object>();
		returnObject.put("contract", contract);
		
		List<HashMap<String,Object>> typelist = easyDao.SelectListBySql("select * from TB_OA_contract_type where ftype='"+contract.get("FTYPE1")+"'");
		returnObject.put("typelist", typelist);
		
		returnObject.put("company", easyDao.SelectListBySql("select fid,fname from TB_OA_department where ftype='2'"));
		
		request.setAttribute("returnObject", returnObject);
		return "contract/oto/wf_show";
	}
	
	@RequestMapping("/WFTurnDown")
	public String WFTurnDown(){
		String id = request.getParameter("id");
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "select * from TB_OA_contract where fid=#{id}");
		args.put("id", id);
		HashMap<String,Object> contract = easyDao.GetSinglerData(args);
		Map<String,Object> returnObject = new HashMap<String,Object>();
		returnObject.put("contract", contract);
		
		List<HashMap<String,Object>> typelist = easyDao.SelectListBySql("select * from TB_OA_contract_type where ftype='"+contract.get("FTYPE1")+"'");
		returnObject.put("typelist", typelist);
		
		returnObject.put("company", easyDao.SelectListBySql("select fid,fname from TB_OA_department where ftype='2'"));
		
		request.setAttribute("returnObject", returnObject);
		
		return "contract/oto/wf_turndown";
	}
	
}
