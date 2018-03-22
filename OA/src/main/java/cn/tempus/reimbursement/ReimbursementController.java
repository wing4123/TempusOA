package cn.tempus.reimbursement;

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

/** 
* @author 吴中贤 wing4123@163.com
* @date 2017年7月4日
* @Description: 费用报销单
*  
*/
@Controller
@RequestMapping("/Reimbursement")
public class ReimbursementController {
	
	@Autowired
	ReimbursementService reimbursementservice;
	
	@Autowired
	BaseService baseService;
	
	@Autowired
	HttpServletRequest request;
	
	@RequestMapping("/Create")
	public String Create(){
		baseService.initmenu( "/MyWorkFlow/Initiate");
		Map<String, Object> returnObject = new HashMap<String, Object>();
//		returnObject.put("company", baseService.easyDao.SelectListBySql("select flex_value companynum,description companyname from fnd_flex_values_vl where attribute4='OA'"));
		returnObject.put("costcategory",baseService.easyDao.SelectListBySql("select fid costvalue,fname costname from TB_OA_costcategory"));
		returnObject.put("company", baseService.easyDao.SelectListBySql("select fid,fname from TB_OA_department where ftype='2'"));
		
		request.setAttribute("returnObject", returnObject);
		return "reimbursement/create";
	}
	
	@RequestMapping("/Edit")
	public String Edit(){
		String id = request.getParameter("id");
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "select * from TB_OA_reimbursement where fid=#{id}");
		args.put("id", id);
		Map<String,Object> reimbursement = baseService.easyDao.GetSinglerData(args);
		
		Map<String, Object> returnObject = new HashMap<String, Object>();
		returnObject.put("reimbursement", reimbursement);
		returnObject.put("costcategory",baseService.easyDao.SelectListBySql("select fid costvalue,fname costname from TB_OA_costcategory"));
		returnObject.put("company", baseService.easyDao.SelectListBySql("select fid,fname from TB_OA_department where ftype='2'"));
		
		args.put("#SQL", "select * from TB_OA_reimbursement_entry where fparentid=#{id} order by fseq");
		returnObject.put("entry", baseService.easyDao.SelectListBySqlWithWhere(args));
		args.put("#SQL", "select * from TB_OA_reimbursement_relation where fparentid=#{id} order by fseq");
		returnObject.put("list3", baseService.easyDao.SelectListBySqlWithWhere(args));
		
		request.setAttribute("returnObject", returnObject);
		baseService.initmenu( "/MyWorkFlow/Initiate");
		return "reimbursement/edit";
	}
	
	@RequestMapping("/Save")
	@ResponseBody
	public int Save(){
		reimbursementservice.Save();
		return 0;
	}
	
	@RequestMapping("/Delete")
	@ResponseBody
	public boolean Delete(@RequestParam String id){
		boolean result = reimbursementservice.Delete(id);
		return result;
	}
	
	@RequestMapping("/getDepartmentList")
	@ResponseBody
	public List<HashMap<String,Object>> getDepartmentList(@RequestParam String companyid){
		List<HashMap<String,Object>> result = reimbursementservice.getDepartmentList(companyid);
		return result;
	}
	
	@RequestMapping("/getProjectList")
	@ResponseBody
	public List<HashMap<String,Object>> getProjectList(@RequestParam String companyid){
		List<HashMap<String,Object>> result = reimbursementservice.getProjectList(companyid);
		return result;
	}
	
	@RequestMapping("/getCostCategoryList")
	@ResponseBody
	public List<HashMap<String,Object>> getCostCategoryList(@RequestParam String companyid){
		List<HashMap<String,Object>> result = reimbursementservice.getCostCategoryList(companyid);
		return result;
	}
	
	@RequestMapping("/WFShow")
	public String WFShow(HttpServletResponse response){
		String id = request.getParameter("id");
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "select a.*,b.user_id,b.user_name,c.user_name FCREATORNAME,d.fname fdepartmentname from TB_OA_reimbursement a left join tb_user b on b.user_id=a.fpayee left join tb_user c on c.user_id=a.fcreator left join TB_OA_division d on d.fid=c.attribute13 where a.fid=#{id}");
		args.put("id", id);
		Map<String,Object> reimbursement = baseService.easyDao.GetSinglerData(args);
		
		Map<String, Object> returnObject = new HashMap<String, Object>();
		returnObject.put("reimbursement", reimbursement);
		//returnObject.put("company", baseService.easyDao.SelectListBySql("select flex_value companynum,description companyname from fnd_flex_values_vl where attribute4='OA'"));
		returnObject.put("company", baseService.easyDao.SelectListBySql("select fid,fname from TB_OA_department where ftype='2'"));
		returnObject.put("department", reimbursementservice.getDepartmentList(reimbursement.get("FCOMPANY").toString()));
		returnObject.put("costcategory",baseService.easyDao.SelectListBySql("select fid costvalue,fname costname from TB_OA_costcategory"));
				
		args.put("#SQL", "select * from TB_OA_reimbursement_entry where fparentid=#{id} order by fseq");
		returnObject.put("entry", baseService.easyDao.SelectListBySqlWithWhere(args));
		returnObject.put("id", id);
		
		args.put("#SQL", "select * from TB_OA_reimbursement_relation where fparentid=#{id} order by fseq");
		returnObject.put("list3", baseService.easyDao.SelectListBySqlWithWhere(args));
		
		request.setAttribute("returnObject", returnObject);
		return "reimbursement/wf_show";
	}
	
	@RequestMapping("/WFTurnDown")
	public String WFTurnDown(){
//		String id = request.getParameter("id");
//		Map<String,Object> args = new HashMap<String,Object>();
//		args.put("#SQL", "select a.*,b.user_id,b.user_name from TB_OA_reimbursement a left join tb_user b on b.user_id=a.fpayee where a.fid=#{id}");
//		args.put("id", id);
//		Map<String,Object> reimbursement = baseService.easyDao.GetSinglerData(args);
//		
//		Map<String, Object> returnObject = new HashMap<String, Object>();
//		returnObject.put("reimbursement", reimbursement);
//		returnObject.put("company", baseService.easyDao.SelectListBySql("select flex_value companynum,description companyname from fnd_flex_values_vl where attribute4='OA'"));
//		returnObject.put("department", reimbursementservice.getDepartmentList(reimbursement.get("FCOMPANY").toString()));
//		
//		args.put("#SQL", "select * from TB_OA_reimbursement_entry where fparentid=#{id} order by fseq");
//		returnObject.put("entry", baseService.easyDao.SelectListBySqlWithWhere(args));
//		returnObject.put("id", id);
//		
//		request.setAttribute("returnObject", returnObject);
		
		String id = request.getParameter("id");
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "select * from TB_OA_reimbursement where fid=#{id}");
		args.put("id", id);
		Map<String,Object> reimbursement = baseService.easyDao.GetSinglerData(args);
		
		Map<String, Object> returnObject = new HashMap<String, Object>();
		returnObject.put("reimbursement", reimbursement);
		returnObject.put("costcategory",baseService.easyDao.SelectListBySql("select fid costvalue,fname costname from TB_OA_costcategory"));
		returnObject.put("company", baseService.easyDao.SelectListBySql("select fid,fname from TB_OA_department where ftype='2'"));
		
		args.put("#SQL", "select * from TB_OA_reimbursement_entry where fparentid=#{id} order by fseq");
		returnObject.put("entry", baseService.easyDao.SelectListBySqlWithWhere(args));
		
		args.put("#SQL", "select * from TB_OA_reimbursement_relation where fparentid=#{id} order by fseq");
		returnObject.put("list3", baseService.easyDao.SelectListBySqlWithWhere(args));
		
		request.setAttribute("returnObject", returnObject);
		
		return "reimbursement/wf_turndown";
	}
	
	@RequestMapping("/WFEdit2")
	public String WFEdit2(HttpServletResponse response){
		String id = request.getParameter("id");
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "select a.*,c.user_name fcreatorname,d.fname fdepartmentname from TB_OA_reimbursement a left join tb_user c on c.user_id=a.fcreator left join TB_OA_division d on d.fid=c.attribute13 where a.fid=#{id}");
		args.put("id", id);
		Map<String,Object> reimbursement = baseService.easyDao.GetSinglerData(args);
		
		Map<String, Object> returnObject = new HashMap<String, Object>();
		returnObject.put("reimbursement", reimbursement);
		//returnObject.put("company", baseService.easyDao.SelectListBySql("select flex_value companynum,description companyname from fnd_flex_values_vl where attribute4='OA'"));
		returnObject.put("company", baseService.easyDao.SelectListBySql("select fid,fname from TB_OA_department where ftype='2'"));
		returnObject.put("department", reimbursementservice.getDepartmentList(reimbursement.get("FCOMPANY").toString()));
//		returnObject.put("costcategory", baseService.easyDao.SelectListBySql("select flex_value costvalue,description costname from fnd_flex_values_vl where flex_value_set_id='1014869' and (flex_value like '6601______' or flex_value like '6602______')"));
		returnObject.put("costcategory",baseService.easyDao.SelectListBySql("select fid costvalue,fname costname from TB_OA_costcategory"));
		
		args.put("#SQL", "select * from TB_OA_reimbursement_entry where fparentid=#{id} and ftype=1 order by fseq");
		returnObject.put("entry", baseService.easyDao.SelectListBySqlWithWhere(args));
		returnObject.put("id", id);
		
		args.put("#SQL", "select * from TB_OA_reimbursement_relation where fparentid=#{id} order by fseq");
		returnObject.put("list3", baseService.easyDao.SelectListBySqlWithWhere(args));
		/*************************************************************/
		returnObject.put("company2", baseService.easyDao.SelectListBySql("select flex_value companynum,description companyname from fnd_flex_values_vl where attribute4='OA'"));
		//returnObject.put("project", baseService.easyDao.SelectListBySql("select flex_value projectvalue,description projectname from fnd_flex_values_vl where flex_value_set_id=1014872"));
		
		returnObject.put("costcategory2", baseService.easyDao.SelectListBySql("select flex_value costvalue,description costname from fnd_flex_values_vl where flex_value_set_id='1014869' and (flex_value like '6601______' or flex_value like '6602______' or flex_value='2221100301')"));
		
		request.setAttribute("returnObject", returnObject);
		
		return "reimbursement/wf_edit2";
	}
	
	@RequestMapping("/Save2")
	@ResponseBody
	public int Save2(){
		int result = reimbursementservice.Save2();
		return result;
	}
	
	@RequestMapping("/WFShow2")
	public String WFShow2(HttpServletResponse response){
		String id = request.getParameter("id");
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "select a.*,c.user_name fcreatorname,d.fname fdepartmentname from TB_OA_reimbursement a left join tb_user c on c.user_id=a.fcreator left join TB_OA_division d on d.fid=c.attribute13 where a.fid=#{id}");
		args.put("id", id);
		Map<String,Object> reimbursement = baseService.easyDao.GetSinglerData(args);
		
		Map<String, Object> returnObject = new HashMap<String, Object>();
		returnObject.put("reimbursement", reimbursement);
		returnObject.put("company", baseService.easyDao.SelectListBySql("select fid,fname from TB_OA_department where ftype='2'"));
		returnObject.put("department", reimbursementservice.getDepartmentList(reimbursement.get("FCOMPANY").toString()));
		returnObject.put("costcategory",baseService.easyDao.SelectListBySql("select fid costvalue,fname costname from TB_OA_costcategory"));
		
		args.put("#SQL", "select * from TB_OA_reimbursement_entry where fparentid=#{id} and ftype=1 order by fseq");
		returnObject.put("entry", baseService.easyDao.SelectListBySqlWithWhere(args));
		returnObject.put("id", id);
		
		args.put("#SQL", "select * from TB_OA_reimbursement_relation where fparentid=#{id} order by fseq");
		returnObject.put("list3", baseService.easyDao.SelectListBySqlWithWhere(args));
		/*************************************************************/
		returnObject.put("company2", baseService.easyDao.SelectListBySql("select flex_value companynum,description companyname from fnd_flex_values_vl where attribute4='OA'"));
		
		args.put("#SQL", "select * from TB_OA_reimbursement_entry where fparentid=#{id} and ftype=2 order by fseq");
		returnObject.put("entry2", baseService.easyDao.SelectListBySqlWithWhere(args));
		
		args.put("#SQL", "select flex_value fid,description fname from fnd_flex_values_vl where flex_value like #{companyid} AND flex_value_set_id='1014868' order by flex_value");
		args.put("companyid", reimbursement.get("FCOSTCOMPANY")+"____");
		returnObject.put("department2", baseService.easyDao.SelectListBySqlWithWhere(args));
		
		returnObject.put("costcenters", getDepartmentList(reimbursement.get("FCOSTCOMPANY").toString()));
		returnObject.put("costcategory2", baseService.easyDao.SelectListBySql("select flex_value fid,description fname from fnd_flex_values_vl where flex_value_set_id='1014869' and (flex_value like '6601______' or flex_value like '6602______' or flex_value='2221100301')"));
		returnObject.put("project", getProjectList(reimbursement.get("FCOSTCOMPANY").toString()));
		
		request.setAttribute("returnObject", returnObject);
		
		return "reimbursement/wf_show2";
	}
	
	@RequestMapping("/WFEdit3")
	public String WFEdit3(HttpServletResponse response){
		String id = request.getParameter("id");
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "select a.*,c.user_name fcreatorname,d.fname fdepartmentname from TB_OA_reimbursement a left join tb_user c on c.user_id=a.fcreator left join TB_OA_division d on d.fid=c.attribute13 where a.fid=#{id}");
		args.put("id", id);
		Map<String,Object> reimbursement = baseService.easyDao.GetSinglerData(args);
		
		Map<String, Object> returnObject = new HashMap<String, Object>();
		returnObject.put("reimbursement", reimbursement);
		returnObject.put("company", baseService.easyDao.SelectListBySql("select fid,fname from TB_OA_department where ftype='2'"));
		returnObject.put("department", reimbursementservice.getDepartmentList(reimbursement.get("FCOMPANY").toString()));
		returnObject.put("costcategory",baseService.easyDao.SelectListBySql("select fid costvalue,fname costname from TB_OA_costcategory"));
		
		args.put("#SQL", "select * from TB_OA_reimbursement_entry where fparentid=#{id} and ftype=1 order by fseq");
		returnObject.put("entry", baseService.easyDao.SelectListBySqlWithWhere(args));
		returnObject.put("id", id);
		
		args.put("#SQL", "select * from TB_OA_reimbursement_relation where fparentid=#{id} order by fseq");
		returnObject.put("list3", baseService.easyDao.SelectListBySqlWithWhere(args));
		/*************************************************************/
		returnObject.put("company2", baseService.easyDao.SelectListBySql("select flex_value companynum,description companyname from fnd_flex_values_vl where attribute4='OA'"));
		
		args.put("#SQL", "select * from TB_OA_reimbursement_entry where fparentid=#{id} and ftype=2 order by fseq");
		returnObject.put("entry2", baseService.easyDao.SelectListBySqlWithWhere(args));
		
		args.put("#SQL", "select flex_value fid,description fname from fnd_flex_values_vl where flex_value like #{companyid} AND flex_value_set_id='1014868' order by flex_value");
		args.put("companyid", reimbursement.get("FCOSTCOMPANY")+"____");
		returnObject.put("department2", baseService.easyDao.SelectListBySqlWithWhere(args));
		
		returnObject.put("costcenters", getDepartmentList(reimbursement.get("FCOSTCOMPANY").toString()));
		returnObject.put("costcategory2", baseService.easyDao.SelectListBySql("select flex_value fid,description fname from fnd_flex_values_vl where flex_value_set_id='1014869' and (flex_value like '6601______' or flex_value like '6602______' or flex_value='2221100301')"));
		returnObject.put("project", getProjectList(reimbursement.get("FCOSTCOMPANY").toString()));
		/****************************************************************/
		args.put("#SQL", "select e.bank_account_name fname from TB_OA_reimbursement a left join fnd_flex_values_vl b on b.flex_value=a.fcostcompany and flex_value_set_id=1014867 and attribute4='OA' left join HR_ALL_ORGANIZATION_UNITS c on c.name=b.description left join CE_BANK_ACCT_USES_ALL d on d.org_id=c.organization_id left join CE.CE_BANK_ACCOUNTS e on e.bank_account_id=d.bank_account_id where a.fid=#{id}");
		returnObject.put("paymentaccounts", baseService.easyDao.SelectListBySqlWithWhere(args));
		
		request.setAttribute("returnObject", returnObject);
		
		return "reimbursement/wf_edit3";
	}
	
	@RequestMapping("/Save3")
	@ResponseBody
	public Map<String,Object> Save3(@RequestParam String id,@RequestParam String paymentaccount,@RequestParam String paymentdate){
		Map<String,Object> result = reimbursementservice.Save3(id,paymentaccount,paymentdate);
		return result;
	} 
	
	@RequestMapping("/WFShow3")
	public String WFShow3(HttpServletResponse response){
		String id = request.getParameter("id");
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "select a.*,c.user_name fcreatorname,d.fname fdepartmentname from TB_OA_reimbursement a left join tb_user c on c.user_id=a.fcreator left join TB_OA_division d on d.fid=c.attribute13 where a.fid=#{id}");
		args.put("id", id);
		Map<String,Object> reimbursement = baseService.easyDao.GetSinglerData(args);
		
		Map<String, Object> returnObject = new HashMap<String, Object>();
		returnObject.put("reimbursement", reimbursement);
		returnObject.put("company", baseService.easyDao.SelectListBySql("select fid,fname from TB_OA_department where ftype='2'"));
		returnObject.put("department", reimbursementservice.getDepartmentList(reimbursement.get("FCOMPANY").toString()));
		returnObject.put("costcategory",baseService.easyDao.SelectListBySql("select fid costvalue,fname costname from TB_OA_costcategory"));
		
		args.put("#SQL", "select * from TB_OA_reimbursement_entry where fparentid=#{id} and ftype=1 order by fseq");
		returnObject.put("entry", baseService.easyDao.SelectListBySqlWithWhere(args));
		returnObject.put("id", id);
		
		args.put("#SQL", "select * from TB_OA_reimbursement_relation where fparentid=#{id} order by fseq");
		returnObject.put("list3", baseService.easyDao.SelectListBySqlWithWhere(args));
		/*************************************************************/
		returnObject.put("company2", baseService.easyDao.SelectListBySql("select flex_value companynum,description companyname from fnd_flex_values_vl where attribute4='OA'"));
		
		args.put("#SQL", "select * from TB_OA_reimbursement_entry where fparentid=#{id} and ftype=2 order by fseq");
		returnObject.put("entry2", baseService.easyDao.SelectListBySqlWithWhere(args));
		
		args.put("#SQL", "select flex_value fid,description fname from fnd_flex_values_vl where flex_value like #{companyid} AND flex_value_set_id='1014868' order by flex_value");
		args.put("companyid", reimbursement.get("FCOSTCOMPANY")+"____");
		returnObject.put("department2", baseService.easyDao.SelectListBySqlWithWhere(args));
		
		returnObject.put("costcenters", getDepartmentList(reimbursement.get("FCOSTCOMPANY").toString()));
		returnObject.put("costcategory2", baseService.easyDao.SelectListBySql("select flex_value fid,description fname from fnd_flex_values_vl where flex_value_set_id='1014869' and (flex_value like '6601______' or flex_value like '6602______' or flex_value='2221100301')"));
		returnObject.put("project", getProjectList(reimbursement.get("FCOSTCOMPANY").toString()));
		/****************************************************************/
		args.put("#SQL", "select e.bank_account_name fname from TB_OA_reimbursement a left join fnd_flex_values_vl b on b.flex_value=a.fcostcompany and flex_value_set_id=1014867 and attribute4='OA' left join HR_ALL_ORGANIZATION_UNITS c on c.name=b.description left join CE_BANK_ACCT_USES_ALL d on d.org_id=c.organization_id left join CE.CE_BANK_ACCOUNTS e on e.bank_account_id=d.bank_account_id where a.fid=#{id}");
		returnObject.put("paymentaccounts", baseService.easyDao.SelectListBySqlWithWhere(args));
		
		request.setAttribute("returnObject", returnObject);
		
		return "reimbursement/wf_show3";
	}
	
	@RequestMapping("/getRelationBill")
	@ResponseBody
	public Map<String,Object> getRelationBill(){
		Map<String,Object> args = new HashMap<String,Object>();
		StringBuffer sql = new StringBuffer("select * from (select fid,fname,famount,fcurrency,fprocessinstanceid,billtype from (select a.*,case when (c.fid is null and c.fstatus is null) or c.fstatus='4' then 0 else 1 end as mycode from(select fid,fname,famount,fcurrency,fstatus,fcreator,fprocessinstanceid,'业务招待费申请' billtype from TB_OA_businesshospitality union select fid,fname,ftotalamount famount,fcurrency,fstatus,fcreator,fprocessinstanceid,'出差申请' billtype from TB_OA_travel) a left join TB_OA_reimbursement_relation b on b.fprocessinstanceid=a.fprocessinstanceid left join TB_OA_reimbursement c on c.fid=b.fparentid where a.fcreator=#{user} and a.fstatus='3') group by fid,fname,famount,fcurrency,fprocessinstanceid,billtype having sum(mycode)=0) ");
		String search = request.getParameter("search[value]");
		if(!search.equals("")){
			sql.append(" and (fprocessinstanceid like #{search} or billtype like #{search} or fname like #{search} or fcurrency like #{search})");
			args.put("search", "%"+search+"%");
		}
		JSONObject user = (JSONObject) JSONObject.toJSON(request.getSession().getAttribute("USER"));
		args.put("#SQL", sql);
		args.put("user", user.getString("userid"));
		Map<String,Object> result = baseService.getDateTableRecord(args);
		
		return result;
	} 
	
}
