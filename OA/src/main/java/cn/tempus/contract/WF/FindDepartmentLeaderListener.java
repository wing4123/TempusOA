package cn.tempus.contract.WF;

import java.io.Serializable;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.delegate.DelegateTask;  
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tempus.dao.EasyDao;  

/** 
* @author 吴中贤 wing4123@163.com
* @date 2017年8月26日
* @Description: 部门负责人
*  
*/
@Service("contract_FindDepartmentLeaderListener")
public class FindDepartmentLeaderListener implements Serializable, TaskListener {  
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	EasyDao basicservice;
  
    @Override  
    public void notify(DelegateTask task) {
    	Object creator = task.getVariable("sys_starter");
    	Object leader = basicservice.GetFirstValueBySql("select a.fleader from TB_OA_division a left join tb_user b on b.attribute13=a.fid where b.user_id='"+creator+"'");
    	task.setAssignee(leader.toString());
    }  
  
}