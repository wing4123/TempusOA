package cn.tempus.myworkflow.listener;

import java.io.Serializable;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.delegate.DelegateTask;  
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tempus.dao.EasyDao;  

@Service("myworkflow_JumpTaskListener")
public class InDepartment implements Serializable, TaskListener {  
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ProcessEngine processEngine;
	
	@Autowired
	EasyDao basicservice;
  
    @Override  
    public void notify(DelegateTask task) {
    	Object starter = task.getVariable("sys_starter");
    	Object previousassignee = task.getVariable("previousassignee");
		if(previousassignee!=null){
			//提交人的部门负责人
			Object myleader = basicservice.GetFirstValueBySql("select a.fleader from TB_OA_division a left join tb_user b on b.attribute13=a.fid where b.user_id='"+starter+"'");
			//上个节点审批人的直属上级
			Object assignee = basicservice.GetFirstValueBySql("select attribute14 from tb_user where user_id='"+previousassignee+"'");
			//上个节点审批人所在部门的负责人
			Object leader = basicservice.GetFirstValueBySql("select a.fleader from TB_OA_division a left join tb_user b on b.attribute13=a.fid where b.user_id='"+previousassignee+"'");
			if(previousassignee.equals(leader) || starter.equals(myleader) || previousassignee.equals(myleader)){
				task.removeVariable("previousassignee");
				processEngine.getTaskService().complete(task.getId());
			}else{
				task.setAssignee(assignee.toString());
				if(leader!=null && !leader.equals(previousassignee)){
					task.setVariable("previousassignee", assignee);
				}else{
					task.removeVariable("previousassignee");
				}
			}
		}else{
			processEngine.getTaskService().complete(task.getId());
		}
    }  
  
}  