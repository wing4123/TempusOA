package cn.tempus.contract.WF;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import cn.tempus.message.Message;
import cn.tempus.message.MessageService;  

/** 
* @Description: 总部职能合同知会公司财务负责人
*  
*/
@Component("contract_InformCompanyFinanceLeaderListener")
public class InformCompanyFinanceLeaderListener implements Serializable, ExecutionListener {  
  
    private static final long serialVersionUID = 1L;  
    
    @Autowired
    JdbcTemplate jdbc;
	
	@Autowired
	MessageService messageservice;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
    	String billid = execution.getProcessBusinessKey();
    	
    	Message message = new Message();
    	message.setTitle("总部职能合同知会");
    	message.setContent("总部职能合同知会");
    	message.setFromuser("sys");
    	message.setUrl("/MyWorkFlow/ShowPage?processinstanceid="+execution.getProcessInstanceId());
    	
		//公司财务负责人
    	String companyfinanceleader = jdbc.queryForObject("select b.fleader from TB_OA_Contract a inner join TB_OA_department b on b.fparentid=a.fcompany and b.fname='财务部' where a.fid=?", String.class, billid); 
    	
    	List<String> tousers = new ArrayList<String>();
    	tousers.add(companyfinanceleader);
    	message.setTousers(tousers);
    	
    	messageservice.sendMessage(message);
	}
    

  
} 