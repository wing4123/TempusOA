package cn.tempus.reimbursement.WF;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.delegate.DelegateTask;  
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tempus.dao.EasyDao;  

/** 
* @author 吴中贤 wing4123@163.com
* @date 2017年8月26日
* @Description: 两个相邻的节点的审批人相同则跳过下一个
*  
*/
@Service("reimbursement_JumpTaskListener")
public class JumpTaskListener implements Serializable, TaskListener {  
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	EasyDao basicservice;
  
    @Override  
    public void notify(DelegateTask task) {
    	String executionid = task.getExecutionId();
    	String assignee = task.getAssignee();
    	
    	Object prev = task.getVariable("prev");
    	
    	if(prev!=null && prev.equals(executionid+assignee)) {
    		task.setAssignee(null);
    		task.getExecution().getEngineServices().getTaskService().complete(task.getId());
    	}else {
    		task.setVariable("prev", executionid+assignee);
    	}
    }

}