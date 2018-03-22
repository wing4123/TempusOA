package cn.tempus.system.position;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tempus.commons.BaseService;

@Controller
@RequestMapping("/PositionManage")
public class PositionController {
	
	@Autowired
	PositionService positionservice;
	
	@Autowired
	BaseService baseService;
	
	@Autowired
	HttpServletRequest request;
	
	@RequestMapping("/PositionManage")
	public String PermissionManage(){
		baseService.initmenu( "/PositionManage/PositionManage");
		return "system/position/PositionManage";
	}
	
	@RequestMapping("/getPositionList")
	@ResponseBody
	public Map<String,Object> getPositionList(){
		String search = request.getParameter("search[value]");
		Map<String,Object> args = new HashMap<String,Object>();
		StringBuffer sql = new StringBuffer("select a.*,b.user_name fcreatorname,c.user_name flastupdatorname from TB_OA_position a left join tb_user b on b.user_id=a.fcreator left join tb_user c on c.user_id=a.flastupdator");
		if(StringUtils.isNotEmpty(search)){
			sql.append(" where a.fname like #{search}");
			args.put("search", "%"+search+"%");
		}
		args.put("#SQL", sql);
		Map<String,Object> result = baseService.getDateTableRecord(args);
		
		return result;
	}
	
	@RequestMapping("/SavePosition")
	@ResponseBody
	public int SavePosition(){
		int result = positionservice.SavePosition();
		
		return result;
	}
	
	@RequestMapping("/getPositionInfoById")
	@ResponseBody
	public Map<String,Object> getPositionInfoById(@RequestParam String id){
		Map<String,Object> userinfo = positionservice.getRoleInfoById(id);
		
		return userinfo;
	}
	
	@RequestMapping("/DeletePosition")
	@ResponseBody
	public int DeletePosition(@RequestParam String id){
		int result = positionservice.DeletePosition(id);
		return result;
	}
	
	@RequestMapping("/SelectPositionList")
	@ResponseBody
	public List<HashMap<String,Object>> SelectPositionList(){
		List<HashMap<String,Object>> result = positionservice.SelectPositionList();
		return result;
	}
	
	@RequestMapping("/getUserPosition")
	@ResponseBody
	public List<HashMap<String,Object>> getUserPosition(@RequestParam String userid){
		List<HashMap<String,Object>> result = positionservice.getUserPosition(userid);
		return result;
	}
	
}
