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
* @date 2017年9月27日
* @Description: 公司负责人
*  
*/
@Service("contract_FindCompanyLeaderListener")
public class FindCompanyLeaderListener implements Serializable, TaskListener {  
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	EasyDao basicservice;
  
    @Override  
    public void notify(DelegateTask task) {
    	String billid = task.getExecution().getProcessBusinessKey();
		String companyleader = basicservice.GetFirstValueBySql("select b.fleader from TB_OA_Contract a inner join TB_OA_department b on b.fid=a.fcompany where a.fid='"+billid+"'").toString(); 
    	task.setAssignee(companyleader);
    }
  
}