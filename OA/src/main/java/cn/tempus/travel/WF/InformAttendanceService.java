package cn.tempus.travel.WF;

import java.io.Serializable;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.activiti.engine.delegate.DelegateExecution;  
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tempus.dao.EasyDao;
import cn.tempus.message.Message;
import cn.tempus.message.MessageService;  

/** 
* @author 吴中贤 wing4123@163.com
* @date 2017年8月27日
* @Description: 知会考勤员
*  
*/
@Service("travel_InformAttendanceService")
public class InformAttendanceService implements Serializable, JavaDelegate {  
  
    private static final long serialVersionUID = 1L;  
	
	@Autowired
	EasyDao basicservice;
	
	@Autowired
	MessageService messageservice;
    
    @Override
    public void execute(DelegateExecution arg0) throws Exception {
    	Object starter = arg0.getVariable("sys_starter");
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
    	
    	List<String> tousers = new ArrayList<String>();
    	for(int i=0;i<userlist.size();i++) {
    		tousers.add(userlist.get(i).get("USER_ID").toString());
    	}
    	
    	Message message = new Message();
    	message.setTitle("出差申请知会知会");
    	message.setContent("出差申请知会知会");
    	message.setFromuser("sys");
    	message.setTousers(tousers);
    	message.setUrl("/MyWorkFlow/ShowPage?processinstanceid="+arg0.getProcessInstanceId());
    	
    	messageservice.sendMessage(message);
    }

} 