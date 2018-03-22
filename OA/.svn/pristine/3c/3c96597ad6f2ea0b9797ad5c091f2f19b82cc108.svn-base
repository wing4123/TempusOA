package cn.tempus.contract.WF;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.delegate.DelegateExecution;  
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.tempus.dao.EasyDao;
import cn.tempus.message.Message;
import cn.tempus.message.MessageService;  

/** 
* @author 吴中贤 wing4123@163.com
* @date 2017年12月13日
* @Description: 总部职能合同 知会公司负责人
*  
*/
@Component("contract_InformCompanyLeaderExecutionListener")
public class InformCompanyLeaderExecutionListener implements Serializable, JavaDelegate {  
  
    private static final long serialVersionUID = 1L;  
	
	@Autowired
	EasyDao basicservice;
	
	@Autowired
	MessageService messageservice;
    
    @Override
    public void execute(DelegateExecution arg0) throws Exception {
    	String billid = arg0.getProcessBusinessKey();
    	
    	Message message = new Message();
    	message.setTitle("oto事业部合同知会");
    	message.setContent("oto事业部合同知会");
    	message.setFromuser("sys");
    	message.setUrl("/MyWorkFlow/ShowPage?processinstanceid="+arg0.getProcessInstanceId());
    	
		//公司负责人
		String companyleader = basicservice.GetFirstValueBySql("select b.fleader from TB_OA_Contract a inner join TB_OA_department b on b.fid=a.fcompany where a.fid='"+billid+"'").toString(); 
		
		List<String> tousers = new ArrayList<String>();
		tousers.add(companyleader);
		message.setTousers(tousers);
    	
    	messageservice.sendMessage(message);
    }
  
} 