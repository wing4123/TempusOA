package cn.tempus.leave.WF;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;  
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tempus.dao.EasyDao;
import cn.tempus.message.Message;
import cn.tempus.message.MessageService;  

/** 
* @author 吴中贤 wing4123@163.com
* @date 2017年10月20日
* @Description: 考勤员
*  
*/
@Service("leave_infomAttendanceTaskListener")
public class InformAttendanceTaskListener implements TaskListener {  
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	EasyDao basicservice;
	
	@Autowired
	MessageService messageservice;
  
    @Override  
    public void notify(DelegateTask task) {
    	Object starter = task.getVariable("sys_starter");
    	String divisionnum = basicservice.GetFirstValueBySql("select b.fnumber from tb_user a left join TB_OA_division b on b.fid=a.attribute13 where a.user_id="+starter).toString();
    	
    	List<HashMap<String, Object>> userlist = new ArrayList<HashMap<String,Object>>();
    	Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "select a.user_id from tb_user a left join TB_OA_userposition b on b.fuserid=a.user_id left join TB_OA_position c on fid=b.fpositionid where c.fname=#{position}");
    	if(divisionnum.equals("1.5.1.1.1.1.1")) {
    		args.put("position","上海区域考勤员");
    		userlist = basicservice.SelectListBySqlWithWhere(args);
    	}else if(divisionnum.equals("1.5.1.1.1.1.2")) {
    		args.put("position","浙江区域考勤员");
    		userlist = basicservice.SelectListBySqlWithWhere(args);
    	}else if(divisionnum.equals("1.5.1.1.1.1.3")) {
    		args.put("position","江苏区域考勤员");
    		userlist = basicservice.SelectListBySqlWithWhere(args);
    	}else if(divisionnum.equals("1.5.1.1.1.1.4")) {
    		args.put("position","四川区域考勤员");
    		userlist = basicservice.SelectListBySqlWithWhere(args);
    	}else if(divisionnum.equals("1.5.1.1.1.1.5")) {
    		args.put("position","北方区域考勤员");
    		userlist = basicservice.SelectListBySqlWithWhere(args);
    	}else if(divisionnum.startsWith("1.5.1.1")) {
    		args.put("position","上海办公室考勤员");
    		userlist = basicservice.SelectListBySqlWithWhere(args);
    	}else if(divisionnum.startsWith("1.5")) {
    		args.put("position","大健康考勤员");
    		userlist = basicservice.SelectListBySqlWithWhere(args);
    	}else {
    		args.put("position","控股总部职能考勤员");
    		userlist = basicservice.SelectListBySqlWithWhere(args);
    	}
    	
    	Message message = new Message();
    	message.setTitle("考勤提前知会");
    	message.setContent("考勤提前知会");
    	message.setFromuser("sys");
    	List<String> tousers = new ArrayList<String>();
    	for(HashMap<String, Object> user: userlist) {
    		tousers.add(user.get("USER_ID").toString());
    	}
    	message.setTousers(tousers);
    	message.setUrl("/MyWorkFlow/ShowPage?processinstanceid="+task.getProcessInstanceId());
    	
    	messageservice.sendMessage(message);
    }
}