package cn.tempus.myworkflow.listener;

import java.util.Map;

import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.ActivitiEvent;  
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import cn.tempus.commons.GlobalCls;
import cn.tempus.dao.EasyDao;
import cn.tempus.email.Email;
import cn.tempus.myworkflow.ProcessCoreService;

/** 
* @author 吴中贤 wing4123@163.com
* @date 2017年9月18日
* @Description: 全局监听
*  
*/
@Service(value="globaleventlistener")
public class GlobalEventListener implements ActivitiEventListener{
	
	@Autowired
	EasyDao basicservice;
	
	@Autowired
	ProcessCoreService processcoreservice;
	
	@Autowired
	JdbcTemplate jdbc;
	
	@Autowired
    public Email email;

    @Override
    public void onEvent(ActivitiEvent event){
    	Object entity = ((ActivitiEntityEvent) event).getEntity();
    	if(event.getType().equals(ActivitiEventType.TASK_CREATED)){
    		TaskEntity task = (TaskEntity)entity;
    		Object sys_starter = task.getVariable("sys_starter");
    		Object approvals = task.getVariable("approvals");
    		Object prev = task.getVariable("prev");
    		String assignee = task.getAssignee();
    		
    		if(task.getExecution().getCurrentActivityId()!="trundwon" && (assignee!=null && approvals!=null && approvals.toString().contains(assignee))){
    			task.getExecution().getEngineServices().getTaskService().addComment(task.getId(), task.getProcessInstanceId(), "自动通过，重复审批！");
    			task.getExecution().getEngineServices().getTaskService().setVariableLocal(task.getId(), "choose", "1");
    			task.getExecution().getEngineServices().getTaskService().complete(task.getId());
    		}else if(task.getExecution().getCurrentActivityId()!="trundwon" && (assignee!=null && approvals!=null && sys_starter.equals(assignee))){
    			task.getExecution().getEngineServices().getTaskService().addComment(task.getId(), task.getProcessInstanceId(), "自动通过，流程发起人无需审批！");
    			task.getExecution().getEngineServices().getTaskService().setVariableLocal(task.getId(), "choose", "1");
    			task.getExecution().getEngineServices().getTaskService().complete(task.getId());
    		}
    	}else if(event.getType().equals(ActivitiEventType.TASK_COMPLETED)){
    		TaskEntity task = (TaskEntity)entity;
    		String assignee = task.getAssignee();
    		Object approvals = task.getVariable("approvals");
    		task.setVariable("approvals", (approvals==null?"":approvals+"|")+assignee);
    	}else if(event.getType().equals(ActivitiEventType.PROCESS_COMPLETED)) {
    		ExecutionEntity execution = (ExecutionEntity)entity;
    		
    		Object choose = execution.getVariable("choose");
    		Object table = execution.getVariable("sys_table");
    		Object billid = execution.getVariable("sys_billid");
    		Object starter = execution.getVariable("sys_starter");
    		ProcessInstance processinstance = execution.getEngineServices().getRuntimeService().createProcessInstanceQuery().processInstanceId(execution.getProcessInstanceId()).singleResult();
    		
    		String sql = "update "+table+" set fstatus=? where fid=?";
    		jdbc.update(sql, choose.equals("1")?"3":"4",billid);
    		
    		Map<String,Object> starterinfo = jdbc.queryForMap("select email_address FEMAIL,user_name FNAME from tb_user where user_id='"+starter+"'");
    		
    		try {
				HtmlEmail htmlemail = email.getHtmlEmail();
				if(choose.equals("1") && starterinfo.get("FEMAIL")!=null){
					htmlemail.addTo(starterinfo.get("FEMAIL").toString(), starterinfo.get("FNAME").toString());
					htmlemail.setSubject("流程办结提醒");
					htmlemail.setHtmlMsg("<html><div>Dear "+starterinfo.get("FNAME")+",</div><div style='text-indent:2em;'>您的流程：<span style='font-weight: bold;'>"+processinstance.getName()+"</span>已办结</div><a href='https://"+GlobalCls.GP.getProperty("server.ip")+":"+GlobalCls.GP.getProperty("server.port")+"/OA/MyWorkFlow/ShowPage?processinstanceid="+processinstance.getId()+"'>查看表单</a></html>");
					htmlemail.send();
				}
			} catch (EmailException e) {
				e.printStackTrace();
			}
    		
    	}
    }

	@Override
	public boolean isFailOnException() {
		// TODO Auto-generated method stub
		return false;
	}
   
}  