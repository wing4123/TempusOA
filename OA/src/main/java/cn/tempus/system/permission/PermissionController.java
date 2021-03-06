package cn.tempus.system.permission;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContextUtils;

import cn.tempus.commons.BaseService;

@Controller
@RequestMapping("/PermissionManage")
public class PermissionController {
	
	@Autowired
	PermissionService permissonservice;
	
	@Autowired
	BaseService baseService;
	
	@Autowired
	HttpServletRequest request;
	
	@RequestMapping("/PermissionManage")
	public String PermissionManage(){
		baseService.initmenu( "/PermissionManage/PermissionManage");
		return "system/permission/PermissionManage";
	}
	
	@RequestMapping("/PermissionManage2")
	public String PermissionManage2(){
		baseService.initmenu( "/PermissionManage/PermissionManage2");
		return "system/permission/PermissionManage2";
	}
	
	@RequestMapping("/getRoleList")
	@ResponseBody
	public Map<String,Object> getRoleList(){
		String language = RequestContextUtils.getLocaleResolver(request).resolveLocale(request).toString();
		Map<String,Object> args = new HashMap<String,Object>();
		StringBuffer sql = new StringBuffer("select a.fid,a.fname,a.fremark,b.fmenus from TB_OA_role a left join (select a.froleid,listagg(b.f"+language+",',') within group (order by b.fnumber) fmenus from TB_OA_rolemenu a left join TB_OA_menu b on b.fid=a.fmenuid group by a.froleid) b on b.froleid=a.fid ");
		args.put("#SQL", sql);
		Map<String,Object> result = baseService.getDateTableRecord(args);
		
		return result;
	}
	
	@RequestMapping("/SaveRole")
	@ResponseBody
	public int SaveRole(){
		int result = permissonservice.SaveRole();
		
		return result;
	}
	
	@RequestMapping("/getRoleInfoById")
	@ResponseBody
	public Map<String,Object> getRoleInfoById(@RequestParam String id){
		Map<String,Object> userinfo = permissonservice.getRoleInfoById(id);
		
		return userinfo;
	}
	
	@RequestMapping("/DeleteRole")
	@ResponseBody
	public int DeleteMenu(@RequestParam String id){
		int result = permissonservice.DeleteRole(id);
		return result;
	}
	
	@RequestMapping("/SaveRoleMenu")
	@ResponseBody
	public int SaveRoleMenu(){
		int result = permissonservice.SaveRoleMenu();
		
		return result;
	}
	
	@RequestMapping("/getRoleMenuState")
	@ResponseBody
	public List<HashMap<String, Object>> getRoleMenuState(){
		String language = RequestContextUtils.getLocaleResolver(request).resolveLocale(request).toString();
		String roleid = request.getParameter("roleid");
		List<HashMap<String, Object>> result = permissonservice.getRoleMenuState(language,roleid);
		
		return result;
	}
	
	@RequestMapping("/getRoleUserList")
	@ResponseBody
	public List<HashMap<String,Object>> getRoleUserList(){
		String roleid = request.getParameter("roleid");
		return permissonservice.getRoleUserList(roleid);
	}
	
	@RequestMapping("/getUserList")
	@ResponseBody
	public List<HashMap<String, Object>> getUserList(@RequestParam String search,@RequestParam String roleid){
		List<HashMap<String, Object>> result = permissonservice.getUserList(search,roleid);
		
		return result;
	}
	
	@RequestMapping("/SaveRoleUser")
	@ResponseBody
	public int SaveRoleUser(){
		int result = permissonservice.SaveRoleUser();
		
		return result;
	}
	
	@RequestMapping("/getUserPermission")
	@ResponseBody
	public List<HashMap<String, Object>> getUserPermission(@RequestParam String userid){
		List<HashMap<String, Object>> result = permissonservice.getUserPermission(userid);
		
		return result;
	}
	
	@RequestMapping("/deleteUserPermission")
	@ResponseBody
	public int deleteUserPermission(@RequestParam String id){
		int result = permissonservice.deleteUserPermission(id);
		
		return result;
	}
	
	@RequestMapping("/addUserPermission")
	@ResponseBody
	public int addUserPermission(@RequestParam String userid,@RequestParam String divisionid){
		int result = permissonservice.addUserPermission(userid,divisionid);
		
		return result;
	}
	
	@GetMapping("/roles")
	@ResponseBody
	public Map<String, Object> getRolelist2(){
		String search = request.getParameter("search");
		Map<String, Object> param = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer("select fid,fname from tb_oa_role where 1=1");
		if(StringUtils.isNotBlank(search)) {
			sql.append(" and fname like '%'||:search||'%'");
			param.put("search", search);
		}
		Map<String, Object> result = baseService.getDateTableRecord(sql, param);
		
		return result;
	}
	
	@GetMapping("/roleUsers")
	@ResponseBody
	public Map<String, Object> getRoleUser(){
		String roleid = request.getParameter("roleid");
		String search = request.getParameter("search[value]");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("roleid", roleid);
		StringBuffer sql = new StringBuffer("select a.fid,b.user_code,b.user_name from tb_oa_roleuser a left join tb_user b on b.user_id=a.fuserid where a.froleid=:roleid");
		if(StringUtils.isNotBlank(search)) {
			sql.append(" and (b.user_name like '%'||:search||'%' or b.user_code like '%'||:search||'%')");
			param.put("search", search);
		}
		Map<String, Object> result = baseService.getDateTableRecord(sql, param);
		
		return result;
	}
	
	@RequestMapping("/prompt_user")
	public String prop_user(){
		return "system/permission/prompt_user";
	}
	
	@GetMapping("/modalUser")
	@ResponseBody
	public Map<String, Object> modalUser(){
		String roleid = request.getParameter("roleid");
		String search = request.getParameter("search[value]");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("roleid", roleid);
		StringBuffer sql = new StringBuffer("select b.user_id fid,b.user_code,b.user_name from tb_oa_roleuser a right join tb_user b on b.user_id=a.fuserid and a.froleid = :roleid where a.fid is null");
		if(StringUtils.isNotBlank(search)) {
			sql.append(" and (b.user_name like '%'||:search||'%' or b.user_code like '%'||:search||'%')");
			param.put("search", search);
		}
		Map<String, Object> result = baseService.getDateTableRecord(sql, param);
		
		return result;
	}
	
	@PostMapping("/addUser")
	@ResponseBody
	public int addUser(){
		return permissonservice.addUser();
	}
	
	@PostMapping("/deleteUser")
	@ResponseBody
	public int deleteUser(){
		return permissonservice.deleteUser();
	}
	
	@RequestMapping("/getRoleMenu")
	@ResponseBody
	public List<Map<String,Object>> getDivisionTree(@RequestParam String roleid){
		List<Map<String,Object>> roleMenu = permissonservice.getRoleMenu(roleid);
		return roleMenu;
	}
	
	/*-*****************************************************************/
	
	@GetMapping("/getProcess")
	@ResponseBody
	public Map<String, Object> getProcess(){
		String roleid = request.getParameter("roleid");
		String search = request.getParameter("search[value]");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("roleid", roleid);
		StringBuffer sql = new StringBuffer("select a.fid,b.key_ fkey,b.name_ fname from tb_oa_roleprocess a left join act_re_model b on b.id_=a.fprocessid where a.froleid=:roleid and b.deployment_id_ is not null");
		if(StringUtils.isNotBlank(search)) {
			sql.append(" and (b.name_ like '%'||:search||'%' or b.key_ like '%'||:search||'%')");
			param.put("search", search);
		}
		Map<String, Object> result = baseService.getDateTableRecord(sql, param);
		
		return result;
	}
	
	@RequestMapping("/prompt_process")
	public String prop_process(){
		return "system/permission/prompt_process";
	}
	
	@GetMapping("/prcessModal")
	@ResponseBody
	public Map<String, Object> prcessModal(){
		String roleid = request.getParameter("roleid");
		String search = request.getParameter("search[value]");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("roleid", roleid);
		StringBuffer sql = new StringBuffer("select b.id_ fid,b.key_ fkey,b.name_ fname from tb_oa_roleprocess a right join act_re_model b on b.id_=a.fprocessid and a.froleid = :roleid where a.fid is null");
		if(StringUtils.isNotBlank(search)) {
			sql.append(" and (b.name_ like '%'||:search||'%' or b.key_ like '%'||:search||'%')");
			param.put("search", search);
		}
		Map<String, Object> result = baseService.getDateTableRecord(sql, param);
		
		return result;
	}
	
	@PostMapping("/addProcess")
	@ResponseBody
	public int addProcess(){
		return permissonservice.addProcess();
	}
	
	@PostMapping("/deleteProcess")
	@ResponseBody
	public int deleteProcess(){
		return permissonservice.deleteProcess();
	}
	
	/*-*****************************************************************/
	
	@GetMapping("/getRoleOrg")
	@ResponseBody
	public Map<String, Object> getRoleOrg(){
		String orgType = request.getParameter("orgType");
		String roleid = request.getParameter("roleid");
		String search = request.getParameter("search[value]");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("roleid", roleid);
		param.put("orgType", orgType);
		StringBuffer sql = new StringBuffer("select a.fid,b.fnumber,b.flongname from tb_oa_role_org a left join tb_oa_"+orgType+" b on b.fid=a.forgid where a.froleid=:roleid and a.forgtype=:orgType ");
		if(StringUtils.isNotBlank(search)) {
			sql.append(" and b.fname like '%'||:search||'%'");
			param.put("search", search);
		}
		Map<String, Object> result = baseService.getDateTableRecord(sql, param);
		
		return result;
	}
	
	@RequestMapping("/prompt_org")
	public String prompt_org(@RequestParam String orgType){
		request.setAttribute("orgType", orgType);
		return "system/permission/prompt_org";
	}
	
	@GetMapping("/getModalOrg")
	@ResponseBody
	public Map<String, Object> getModalOrg(){
		String orgType= request.getParameter("orgType");
		String roleid = request.getParameter("roleid");
		String search = request.getParameter("search[value]");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("roleid", roleid);
		param.put("orgType", orgType);
		StringBuffer sql = new StringBuffer("select b.fid,b.fnumber,b.flongname from tb_oa_role_org a right join tb_oa_"+orgType+" b on b.fid=a.forgid and a.froleid = :roleid where a.fid is null");
		if(StringUtils.isNotBlank(search)) {
			sql.append(" and b.fname like '%'||:search||'%'");
			param.put("search", search);
		}
		Map<String, Object> result = baseService.getDateTableRecord(sql, param);
		
		return result;
	}
	
	@PostMapping("/addOrg")
	@ResponseBody
	public int addOrg(){
		return permissonservice.addOrg();
	}
	
	@PostMapping("/deleteOrg")
	@ResponseBody
	public int deleteOrg(){
		return permissonservice.deleteOrg();
	}
	
}
