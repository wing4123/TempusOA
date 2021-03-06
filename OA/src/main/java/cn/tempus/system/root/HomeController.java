package cn.tempus.system.root;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import cn.tempus.commons.BaseService;
import cn.tempus.utils.PropertyUtil;
import com.alibaba.fastjson.JSONObject;

@Controller
public class HomeController {
	
	@Autowired
	private HomeService homeservice;
	
	@Autowired
	BaseService baseService;
	
	@Autowired
	HttpServletRequest request;
	
	@RequestMapping("/ChangeLanguage")
	public void ChangeLanguage(){
		Locale locale = new Locale(request.getParameter("locale"));
		request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
	}
	
	@RequestMapping("/login")
	public String login(){
//		request.getSession().invalidate();
		
		return "home/login";
	}
	
	@RequestMapping("/DoLogin")
	@ResponseBody
	public boolean DoLogin(HttpServletResponse response){
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "select a.user_id,a.user_name,a.user_code,b.fid departmentid,b.fname departmentname from tb_user a left join TB_OA_department b on b.fid=a.attribute15 where a.user_code=#{username} and a.password=#{password}");
		args.put("username", username);
		args.put("password", PropertyUtil.getMD5(password));
		HashMap<String,Object> userobj = baseService.easyDao.GetSinglerData(args);
		
		boolean result = false;
		
		if(userobj!=null){
			Map<String,Object> user = new HashMap<String,Object>();
			user.put("userid", userobj.get("USER_ID"));
			user.put("name", userobj.get("USER_NAME"));
			user.put("code", userobj.get("USER_CODE"));
			user.put("departmentid", userobj.get("DEPARTMENTID"));
			user.put("departmentname", userobj.get("DEPARTMENTNAME"));
			
			
			request.getSession().setAttribute("USER", user);
			
			String sql = "select distinct a.furl from TB_OA_menu a left join TB_OA_rolemenu b on b.fmenuid=a.fid left join TB_OA_role c on c.fid=b.froleid left join TB_OA_roleuser d on d.froleid=c.fid where d.fuserid=#{user}";
			if(username.equals("admin")) {
				sql = "select furl from tb_oa_menu";
			}
					
			args.put("#SQL", sql);
			args.put("user", userobj.get("USER_ID"));
			List<HashMap<String, Object>> urllist = baseService.easyDao.SelectListBySqlWithWhere(args);
			StringBuffer myright = new StringBuffer();
			for(Map<String,Object> map : urllist){
				if(map!=null){
					myright.append(map.get("FURL")).append("|");
				}
			}
			request.getSession().setAttribute("MYRIGHTURL", myright);
			
			args.put("#SQL", "select distinct furl from TB_OA_menu");
			List<HashMap<String, Object>> allurllist = baseService.easyDao.SelectListBySqlWithWhere(args);
			StringBuffer allright = new StringBuffer();
			for(Map<String,Object> map : allurllist){
				if(map!=null){
					allright.append(map.get("FURL")).append("|");
				}
			}
			request.getSession().setAttribute("ALLRIGHTURL", allright);
			
	        Cookie cookie = new Cookie("userName",username);//创建新cookie
	        cookie.setMaxAge(60*24*7);// 设置存在时间为7天
	        cookie.setPath("/OA/login");//设置作用域
//	        Cookie language = new Cookie("userName",username);//创建新cookie
//	        cookie.setMaxAge(60*24*7);// 设置存在时间为7天
//	        cookie.setPath("/OA/login");//设置作用域
	        response.addCookie(cookie);//将cookie添加到response的cookie数组中返回给客户端
			
			result = true;
		}
		
		return result;
	}
	
	@RequestMapping("/index")
	public String home(){
		String userid = ((JSONObject) JSONObject.toJSON(request.getSession().getAttribute("USER"))).getString("userid");
		Map<String,Object> returnObject = homeservice.inithomedata(userid);
		
		//request.setAttribute("returnObject", returnObject);
		baseService.initmenu( "");
		return "home/index";
	}
	
	@RequestMapping("/logout")
	public String LogOut(){
		request.getSession().invalidate();
		return "redirect:/login";
	}
	
	@RequestMapping("/modal_ChangePassword")
	public String modal_ChangePassword(){
		return "layout/changepassword";
	}
	
	@RequestMapping("/ChangePassword")
	@ResponseBody
	public int ChangePassword(){
		String userid = ((JSONObject) JSONObject.toJSON(request.getSession().getAttribute("USER"))).getString("userid");
		String oldpassword = request.getParameter("oldpassword");
		String newpassword = request.getParameter("newpassword");
		int result = 0;
		
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("userid", userid);
		args.put("oldpassword", PropertyUtil.getMD5(oldpassword));
		args.put("newpassword", PropertyUtil.getMD5(newpassword));
		args.put("#SQL", "select count(*) from tb_user where user_id=#{userid} and password=#{oldpassword}");
		if(baseService.easyDao.SelectCountBySqlWithWhere(args)==1){
			args.put("#SQL", "update tb_user set password=#{newpassword} where user_id=#{userid}");
			result = baseService.easyDao.UpdateData(args);
		}
		
		return result;
	}
	
}
