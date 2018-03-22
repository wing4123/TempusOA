package cn.tempus.myworkflow.listener;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tempus.commons.GlobalCls;
import cn.tempus.dao.EasyDao;
import cn.tempus.email.Email;

/** 
* @author 吴中贤 wing4123@163.com
* @date 2017年9月25日
* @Description: 流程结束修改表单状态
*/

@Service(value="endprocesslistener")
public class EndProcessListener implements ExecutionListener {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
    public EasyDao basicService;
	
	@Autowired
    public Email email;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		Object choose = execution.getVariable("choose");
		Object table = execution.getVariable("sys_table");
		Object billid = execution.getVariable("sys_billid");
		Object starter = execution.getVariable("sys_starter");
		ProcessInstance processinstance = execution.getEngineServices().getRuntimeService().createProcessInstanceQuery().processInstanceId(execution.getProcessInstanceId()).singleResult();
		
		
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "update "+table+" set fstatus=#{status} where fid=#{billid}");
		args.put("billid", billid);
		
		if(choose.equals("1")){
			args.put("status", "3");
		}else if(choose.equals("4")){
			args.put("status", "4");
		}
		basicService.UpdateData(args);
		
		HashMap<String,Object> starterinfo = basicService.GetSinglerData("select email_address FEMAIL,user_name FNAME from tb_user where user_id='"+starter+"'");
		
		HtmlEmail htmlemail = email.getHtmlEmail();
		if(choose.equals("1") && starterinfo.get("FEMAIL")!=null){
			htmlemail.addTo(starterinfo.get("FEMAIL").toString(), starterinfo.get("FNAME").toString());
    		htmlemail.setSubject("流程办结提醒");
    		htmlemail.setHtmlMsg("<html><div>Dear "+starterinfo.get("FNAME")+",</div><div style='text-indent:2em;'>您的流程：<span style='font-weight: bold;'>"+processinstance.getName()+"</span>已办结</div><a href='https://"+GlobalCls.GP.getProperty("server.ip")+":"+GlobalCls.GP.getProperty("server.port")+"/OA/MyWorkFlow/ShowPage?processinstanceid="+processinstance.getId()+"'>查看表单</a></html>");
    		htmlemail.send();
		}

		
		
	}

}
