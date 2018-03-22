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
* @Description: 查找公司财务负责人
*  
*/

@Service("reimbursement_FindDepartmentFinanceLeaderTaskListener")
public class FindDepartmentFinanceLeaderTaskListener implements Serializable,TaskListener {  
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	EasyDao basicservice;
  
    @Override  
    public void notify(DelegateTask task) {
    	Object leader = basicservice.GetFirstValueBySql("select c.fleader from TB_OA_department a left join TB_OA_REIMBURSEMENT b on b.FCOMPANY=a.fid left join TB_OA_department c on c.fparentid=a.fid and c.fname='财务部' where b.FPROCESSINSTANCEID='"+task.getProcessInstanceId()+"'");
    	
    	task.setAssignee(leader.toString());
    }

}