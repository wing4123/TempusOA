package cn.tempus.contract.WF;

import java.util.HashMap;

import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.ActivitiEvent;  
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tempus.dao.EasyDao;
import cn.tempus.myworkflow.ProcessCoreService;

/** 
* @author 吴中贤 wing4123@163.com
* @date 2017年9月18日
* @Description: 全局监听
*  
*/
@Service(value="contract_globaleventlistener")
public class GlobalEventListener implements ActivitiEventListener{
	
	@Autowired
	EasyDao basicservice;
	
	@Autowired
	ProcessCoreService processcoreservice;

    @Override
    public void onEvent(ActivitiEvent event){
    	Object entity = ((ActivitiEntityEvent) event).getEntity();
    	if(event.getType().equals(ActivitiEventType.TASK_CREATED)){
    		TaskEntity task = (TaskEntity)entity;
    		Object sys_starter = task.getVariable("sys_starter");
    		Object approvals = task.getVariable("approvals");
    		Object prev = task.getVariable("prev");
    		String assignee = task.getAssignee();
    		
//    		if(task.getExecution().getCurrentActivityId()!="trundwon" && (assignee!=null && approvals!=null && (approvals.toString().contains(assignee) || sys_starter.equals(assignee)))){
    		if(approvals!=null && assignee!=null && (approvals.toString().contains(assignee) || sys_starter.equals(assignee))){
    			task.setAssignee(null);
    			task.complete(null,false);
    		}
    	}else if(event.getType().equals(ActivitiEventType.TASK_COMPLETED)){
    		TaskEntity task = (TaskEntity)entity;
    		String assignee = task.getAssignee();
    		Object approvals = task.getVariable("approvals");
    		task.setVariable("approvals", (approvals==null?"":approvals+"|")+assignee);
    	}
    }

	@Override
	public boolean isFailOnException() {
		// TODO Auto-generated method stub
		return false;
	}
   
}  