package cn.tempus.reimbursement.WF;

import java.io.Serializable;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.delegate.DelegateExecution;  
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.tempus.dao.EasyDao;
import cn.tempus.message.Message;
import cn.tempus.message.MessageService;  

/** 
* @author 吴中贤 wing4123@163.com
* @date 2017年8月27日
* @Description: 知会公司领导
*  
*/
@Component("reimbursement_InformCompanyLeaderService")
public class InformCompanyLeaderService implements Serializable, JavaDelegate {  
  
    private static final long serialVersionUID = 1L;  
    
	@Autowired
	private ProcessEngine processEngine;
	
	@Autowired
	EasyDao basicservice;
	
	@Autowired
	MessageService messageservice;
    
    @Override
    public void execute(DelegateExecution arg0) throws Exception {
    	String billid = arg0.getProcessBusinessKey();
    	Object companyleader = basicservice.GetFirstValueBySql("select a.fleader from TB_OA_department a inner join TB_OA_reimbursement b on b.fcompany=a.fid where b.fid='"+billid+"'");
    	Message message = new Message();
    	message.setTitle("费用报销单知会");
    	message.setContent("费用报销单知会");
    	message.setFromuser("sys");
    	List<String> tousers = new ArrayList<String>();
    	tousers.add(companyleader.toString());
    	message.setTousers(tousers);
    	message.setUrl("/MyWorkFlow/ShowPage?processinstanceid="+arg0.getProcessInstanceId());
    	
    	messageservice.sendMessage(message);
    }
  
} 