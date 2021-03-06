package cn.tempus.system.department;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tempus.commons.BaseService;

@Controller
@RequestMapping("/DepartmentManage")
public class DepartmentController {
	
	@Autowired
	DepartmentService departmentservice;
	
	@Autowired
	BaseService baseService;
	
	@Autowired
	HttpServletRequest request;
	
	@RequestMapping("/getDepartmentTree")
	@ResponseBody
	public List<HashMap<String,Object>> getDepartmentTree(){
		List<HashMap<String,Object>> menu = departmentservice.getDepartmentTree();
		return menu;
	}
	
	@RequestMapping("/getParentDepartmentTree")
	@ResponseBody
	public List<HashMap<String,Object>> getParentDepartmentTree(){
		String id = request.getParameter("id");
		List<HashMap<String,Object>> menu = departmentservice.getParentDepartmentTree(id);
		return menu;
	}
	
	@RequestMapping("/SaveDepartment")
	@ResponseBody
	public int SaveDepartment(){
		int result = departmentservice.SaveDepartment();
		
		return result;
	}
	
	@RequestMapping("/getDepartmentInfoById")
	@ResponseBody
	public Map<String,Object> getMenuInfoById(){
		String id = request.getParameter("id");
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "select a.fid,a.fname,a.fremark,b.fid fparentid,b.fname fparentname,c.user_id fleaderid,c.user_name fleadername,a.ftype from TB_OA_department a left join TB_OA_department b on b.fid=a.fparentid left join tb_user c on c.user_id=a.fleader where a.fid=#{id}");
		args.put("id", id);
		HashMap<String,Object> departmentinfo = baseService.easyDao.GetSinglerData(args);
		
		return departmentinfo;
	}
	
	@RequestMapping("/DeleteDepartment")
	@ResponseBody
	public int DeleteDepartment(){
		String id = request.getParameter("id");
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "delete from TB_OA_department where fid=#{id}");
		args.put("id", id);
		int result = baseService.easyDao.DeleteData(args);
		
		return result;
	}
	
	@RequestMapping("/getDivisionTree")
	@ResponseBody
	public List<Map<String,Object>> getDivisionTree(){
		List<Map<String,Object>> divisiontree = departmentservice.getDivisionTree();
		return divisiontree;
	}
	
	@RequestMapping("/getParentDivisionTree")
	@ResponseBody
	public List<Map<String,Object>> getParentDivisionTree(){
		String id = request.getParameter("id");
		List<Map<String,Object>> menu = departmentservice.getParentDivisionTree(id);
		return menu;
	}
	
	@RequestMapping("/SaveDivision")
	@ResponseBody
	public int SaveDivision(){
		int result = departmentservice.SaveDivision();
		
		return result;
	}
	
	@RequestMapping("/getDivisionInfoById")
	@ResponseBody
	public Map<String,Object> getDivisionInfoById(){
		String id = request.getParameter("id");
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "select a.fid,a.fnumber,a.fname,a.fremark,b.fid fparentid,b.fname fparentname,c.user_id leaderid,c.user_name leadername,a.flevel from TB_OA_division a left join TB_OA_division b on b.fid=a.fparentid left join tb_user c on c.user_id=a.fleader where a.fid=#{id}");
		args.put("id", id);
		HashMap<String,Object> departmentinfo = baseService.easyDao.GetSinglerData(args);
		
		return departmentinfo;
	}
	
	@RequestMapping("/DeleteDivision")
	@ResponseBody
	public int DeleteDivision(){
		String id = request.getParameter("id");
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "delete from TB_OA_division where fid=#{id}");
		args.put("id", id);
		int result = baseService.easyDao.DeleteData(args);
		
		return result;
	}
	
}
