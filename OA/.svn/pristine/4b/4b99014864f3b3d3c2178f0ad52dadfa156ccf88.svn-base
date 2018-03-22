package cn.tempus.myworkflow.listener;

import java.io.Serializable;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.delegate.DelegateTask;  
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tempus.dao.EasyDao;  

/** 
* @author 吴中贤 wing4123@163.com
* @date 2017年9月24日
* @Description: 部门负责人
*  
*/
@Service("FindDivisionLeaderTaskListener")
public class FindDivisionLeaderTaskListener implements Serializable, TaskListener {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	EasyDao basicservice;
  
    @Override  
    public void notify(DelegateTask task) {
    	Object starter = task.getVariable("sys_starter");
    	Object leader = basicservice.GetFirstValueBySql("select attribute14 from tb_user where user_id='"+starter+"'");
    	Object divisionleader = basicservice.GetFirstValueBySql("select a.fleader from TB_OA_division a left join tb_user b on b.attribute13=a.fid where b.user_id='"+starter+"'");
    	if(divisionleader.equals(starter) || divisionleader.equals(leader)){
    		task.getExecution().getEngineServices().getTaskService().complete(task.getId());
    	}else{
    		task.setAssignee(divisionleader.toString());
    	}
		
    }

}