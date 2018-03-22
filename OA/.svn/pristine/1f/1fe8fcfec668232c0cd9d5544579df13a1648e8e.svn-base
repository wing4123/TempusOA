package cn.tempus.system.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tempus.commons.BaseService;

@Controller
@RequestMapping("/UserManage")
public class UserController {
	
	@Autowired
	UserService userservice;
	
	@Autowired
	BaseService baseService;
	
	@Autowired
	HttpServletRequest request;
	
	@RequestMapping("/UserManage")
	public String UserManage(){
		baseService.initmenu( "/UserManage/UserManage");
		return "system/user/UserManage";
	}
	
	@RequestMapping("/getUserList")
	@ResponseBody
	public Map<String,Object> getUserList(){
		String search = request.getParameter("search[value]");
		String deptnum = request.getParameter("deptnum");
		String divisionnum = request.getParameter("divisionnum");
		String includechild = request.getParameter("includechild");
		Map<String,Object> args = new HashMap<String,Object>();
		StringBuffer sql = new StringBuffer("select a.user_id,a.user_code,a.user_name,a.start_active_date,b.fnumber deptnum,b.fname departmentname,c.fname divisionname from tb_user a left join TB_OA_department b on b.fid=a.attribute15 left join TB_OA_division c on c.fid=a.ATTRIBUTE13 where 1=1");
		if(search!=null && !search.equals("")){
			sql.append(" and user_code like #{search} or user_name like #{search}");
			args.put("search", "%"+search+"%");
		}
		
		if(deptnum!=null && includechild.equals("1")){
			sql.append(" and b.fnumber like #{deptnum}||'.%' or b.fnumber=#{deptnum}");
			args.put("deptnum", deptnum);
		}else if(deptnum!=null && includechild.equals("0")) {
			sql.append(" and b.fnumber = #{deptnum}");
			args.put("deptnum", deptnum);
		}
		
		if(divisionnum!=null && includechild.equals("1")){
			sql.append(" and c.fnumber like #{divisionnum}||'.%' or c.fnumber=#{divisionnum}");
			args.put("divisionnum", divisionnum);
		}else if(divisionnum!=null && includechild.equals("0")) {
			sql.append(" and c.fnumber = #{divisionnum}");
			args.put("divisionnum", divisionnum);
		}
		
		args.put("#SQL", sql);
		Map<String,Object> result = baseService.getDateTableRecord(args);
		
		return result;
	}
	
	@RequestMapping("/getAllUserList")
	@ResponseBody
	public Map<String,Object> getAllUserList(){
		String search = request.getParameter("search[value]");
		Map<String,Object> args = new HashMap<String,Object>();
		StringBuffer sql = new StringBuffer("select a.user_id,a.user_code,a.user_name,a.start_active_date,b.fnumber deptnum,b.fname departmentname from tb_user a left join TB_OA_department b on b.fid=a.attribute15 where 1=1");
		if(search!=null && !search.equals("")){
			sql.append(" and user_code like #{search} or user_name like #{search}");
			args.put("search", "%"+search+"%");
		}
		
		args.put("#SQL", sql);
		Map<String,Object> result = baseService.getDateTableRecord(args);
		
		return result;
	}
	
	@RequestMapping("/SaveUser")
	@ResponseBody
	public int SaveMenu(){
		int result = userservice.SaveUser();
		
		return result;
	}
	
	@RequestMapping("/getUserInfoById")
	@ResponseBody
	public Map<String,Object> getUserInfoById(@RequestParam String id){
		Map<String,Object> userinfo = userservice.getUserInfoById(id);
		
		return userinfo;
	}
	
	@RequestMapping("/DeleteUser")
	@ResponseBody
	public int DeleteMenu(@RequestParam String id){
		int result = userservice.DeleteUser(id);
		return result;
	}
	
}
