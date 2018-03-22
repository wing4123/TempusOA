package cn.tempus.payment.WF;

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
* @date 2017年10月16日 13点45分
* @Description: 公司财务负责人
*  
*/
@Service("payment_FindCompanyFinanceLeaderTaskListener")
public class FindCompanyLeaderTaskListener implements Serializable, TaskListener {  
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	EasyDao basicservice;
  
    @Override  
    public void notify(DelegateTask task) {
    	String billid = task.getExecution().getProcessBusinessKey();
    	Object companyleader = basicservice.GetFirstValueBySql("select b.fleader from TB_OA_paymentrequest a left join TB_OA_department b on b.fparentid=a.fpaymentcompany and b.fname='财务部' where a.fid='"+billid+"'");
    	task.setAssignee(companyleader.toString());
    }

}