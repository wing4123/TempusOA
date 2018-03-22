package cn.tempus.system.menu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContextUtils;

import cn.tempus.commons.BaseService;

@Controller
@RequestMapping("/MenuManage")
public class MenuController {
	
	@Autowired
	MenuService menuservice;
	
	@Autowired
	BaseService baseService;
	
	@Autowired
	HttpServletRequest request;
	
	@RequestMapping("/MenuManage")
	public String MenuManage(){
		baseService.initmenu( "/MenuManage/MenuManage");
		return "system/menu/MenuManage";
	}
	
	@RequestMapping("/getMenuTree")
	@ResponseBody
	public List<HashMap<String,Object>> getMenuTree(){
		String language = RequestContextUtils.getLocaleResolver(request).resolveLocale(request).toString();
		List<HashMap<String,Object>> menu = menuservice.getMenuTree(language);
		return menu;
	}
	
	@RequestMapping("/SaveMenu")
	@ResponseBody
	public int SaveMenu(){
		int result = menuservice.SaveMenu();
		
		return result;
	}
	
	@RequestMapping("/getMenuInfoById")
	@ResponseBody
	public Map<String,Object> getMenuInfoById(){
		String id = request.getParameter("id");
		String language = RequestContextUtils.getLocaleResolver(request).resolveLocale(request).toString();
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "select a.fid,a.fzh_cn,a.fen_us,a.ficon,a.furl,b.fid fparentid,b.f"+language+" fparenttext  from TB_OA_menu a left join TB_OA_menu b on b.fid=a.fparentid where a.fid=#{id}");
		args.put("id", id);
		HashMap<String,Object> menuinfo = baseService.easyDao.GetSinglerData(args);
		
		return menuinfo;
	}
	
	@RequestMapping("/DeleteMenu")
	@ResponseBody
	public int DeleteMenu(@RequestParam String id){
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "delete from TB_OA_menu where fid=#{id}");
		args.put("id", id);
		int result = baseService.easyDao.DeleteData(args);
		
		return result;
	}
	
}
