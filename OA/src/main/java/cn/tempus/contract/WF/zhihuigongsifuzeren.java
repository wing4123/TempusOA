package cn.tempus.contract.WF;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.delegate.DelegateTask;  
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tempus.dao.EasyDao;
import cn.tempus.message.Message;
import cn.tempus.message.MessageService;  

/** 
* @author 吴中贤 wing4123@163.com
* @date 2017年9月27日
* @Description: 公司负责人
*  
*/
@Service("contract_zhihuigongsifuzeren")
public class zhihuigongsifuzeren implements TaskListener {  
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	EasyDao basicservice;
	
	@Autowired
	MessageService messageservice;
  
    @Override  
    public void notify(DelegateTask task) {
    	String billid = task.getExecution().getProcessBusinessKey();
		String companyleader = basicservice.GetFirstValueBySql("select b.fleader from TB_OA_Contract a inner join TB_OA_department b on b.fid=a.fcompany where a.fid='"+billid+"'").toString(); 
    	
		Message message = new Message();
    	message.setTitle("职能部门合同");
    	message.setContent("oto事业部合同知会");
    	message.setFromuser("sys");
    	message.setUrl("/MyWorkFlow/ShowPage?processinstanceid="+task.getProcessInstanceId());
    	List<String> tousers = new ArrayList<String>();
    	tousers.add(companyleader);
    	message.setTousers(tousers);
		
    	messageservice.sendMessage(message);
		
    }
  
}