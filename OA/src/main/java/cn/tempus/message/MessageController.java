package cn.tempus.message;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import cn.tempus.commons.BaseService;

/** 
* @author 吴中贤 wing4123@163.com
* @date 2017年8月28日
* @Description: 消息管理
*  
*/
@Controller

@RequestMapping("/Message")
public class MessageController{
	
	@Autowired
	MessageService messageservice;
	
	@Autowired
	BaseService baseService;
	
	@Autowired
	HttpServletRequest request;
	
	//消息列表页面
	@RequestMapping("/messageList")
	public String messageList(){
		baseService.initmenu( "");
		return "message/messagelist";
	}
	
	//获取消息数据
	@RequestMapping("/getMessageList")
	@ResponseBody
	public Map<String,Object> getMyToBeDo(){
		String search = request.getParameter("search[value]");
		String isread = request.getParameter("isread");
		Map<String,Object> args = new HashMap<String,Object>();
		StringBuffer sql = new StringBuffer("select a.* from TB_OA_message a where a.ftouser=#{user}");
		args.put("user", ((JSONObject) JSONObject.toJSON(request.getSession().getAttribute("USER"))).getString("userid"));
		if(!search.equals("")){
			sql.append(" and (a.ftitle like #{search} or a.fcontent like #{search})");
			args.put("search", "%"+search+"%");
		}
		if(isread.equals("0")){
			sql.append(" and a.freadtime is null");
		}else if(isread.equals("1")){
			sql.append(" and a.freadtime is not null");
		}
		args.put("#SQL", sql);
		
		return baseService.getDateTableRecord(args);
	}
	
	//阅读消息
	@RequestMapping("/readMessage")
	@ResponseBody
	public int readMessage(){
		String id = request.getParameter("id");
		String user = ((JSONObject) JSONObject.toJSON(request.getSession().getAttribute("USER"))).getString("userid");
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "update TB_OA_message set freadtime=#{time} where fid=#{id} and ftouser=#{user}");
		args.put("id", id);
		args.put("time", new Date());
		args.put("user", user);
		int result = baseService.easyDao.UpdateData(args);
		return result;
	}
	
	
	
}
