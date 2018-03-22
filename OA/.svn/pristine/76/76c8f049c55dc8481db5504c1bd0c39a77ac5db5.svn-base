package cn.tempus.reimbursement.WF;

import java.io.Serializable;

import org.activiti.engine.delegate.DelegateTask;  
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tempus.dao.EasyDao;  

/** 
* @author 吴中贤 wing4123@163.com
* @date 2017年8月9日
* @Description: 查找直属上级
*  
*/

@Service("reimbursement_FindLeaderTaskListener")
public class FindLeaderTaskListener implements Serializable, TaskListener {  
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	EasyDao basicservice;
  
    @Override  
    public void notify(DelegateTask task) {
    	Object starter = task.getVariable("sys_starter");
    	Object leader = basicservice.GetFirstValueBySql("select * from TB_OA_division a left join TB_OA_division b on b.fid=a.fparentid left join tb_user c on c.attribute13=a.fid where c.user_id='"+starter+"' ");
    	task.setAssignee(leader.toString());
    }

}