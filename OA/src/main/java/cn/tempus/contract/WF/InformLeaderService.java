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
* @date 2017年8月27日
* @Description: 合同知会领导
*  
*/
@Component("contract_InformLeaderService")
public class InformLeaderService implements Serializable, JavaDelegate {  
  
    private static final long serialVersionUID = 1L;  
	
	@Autowired
	EasyDao basicservice;
	
	@Autowired
	MessageService messageservice;
    
    @Override
    public void execute(DelegateExecution arg0) throws Exception {
    	String billid = arg0.getProcessBusinessKey();
    	Object line = arg0.getVariable("line");
    	Object starter = arg0.getVariable("sys_starter");
    	
    	String name = arg0.getEngineServices().getRepositoryService().getProcessDefinition(arg0.getProcessDefinitionId()).getName();
    	
    	Message message = new Message();
    	message.setTitle(name+"知会");
    	message.setContent(name+"知会");
    	message.setFromuser("sys");
    	message.setUrl("/MyWorkFlow/ShowPage?processinstanceid="+arg0.getProcessInstanceId());
    	
    	if(line.equals(1)){
    		//公司财务负责人
    		String companyfinanceleader = basicservice.GetFirstValueBySql("select b.fleader from TB_OA_Contract a inner join TB_OA_department b on b.fparentid=a.fcompany and b.fname='财务部' where a.fid='"+billid+"'").toString(); 
    		//公司负责人
    		String companyleader = basicservice.GetFirstValueBySql("select b.fleader from TB_OA_Contract a inner join TB_OA_department b on b.fid=a.fcompany where a.fid='"+billid+"'").toString(); 
    		//控股财务经理
    		String konggucaiwujingli = "1114";
//        	Object divisionnum = basicservice.GetFirstValueBySql("select a.fnumber from TB_OA_division a left join tb_user b on b.attribute13=a.fid where b.user_id='"+starter+"'");
//        	if(divisionnum.toString().startsWith("1.5.2")){
//        		konggucaiwujingli = "2102";
//        	}else{
//        		konggucaiwujingli = "1114";
//        	}
        	
        	List<String> tousers = new ArrayList<String>();
        	tousers.add(companyfinanceleader);
        	tousers.add(companyleader);
        	tousers.add(konggucaiwujingli);
        	message.setTousers(tousers);
    	}else if(line.equals(2)){
    		//公司财务负责人
    		String companyfinanceleader = basicservice.GetFirstValueBySql("select b.fleader from TB_OA_Contract a inner join TB_OA_department b on b.fparentid=a.fcompany and b.fname='财务部' where a.fid='"+billid+"'").toString(); 
    		//公司负责人
    		String companyleader = basicservice.GetFirstValueBySql("select b.fleader from TB_OA_Contract a inner join TB_OA_department b on b.fid=a.fcompany where a.fid='"+billid+"'").toString(); 
    		//财务总监
    		String caiwuzongjian = "7030";
    		
    		List<String> tousers = new ArrayList<String>();
        	tousers.add(companyfinanceleader);
        	tousers.add(companyleader);
        	tousers.add(caiwuzongjian);
        	message.setTousers(tousers);
    	}else if(line.equals(3)){
    		//公司财务负责人
    		String companyfinanceleader = basicservice.GetFirstValueBySql("select b.fleader from TB_OA_Contract a inner join TB_OA_department b on b.fparentid=a.fcompany and b.fname='财务部' where a.fid='"+billid+"'").toString(); 
    		//公司负责人
    		String companyleader = basicservice.GetFirstValueBySql("select b.fleader from TB_OA_Contract a inner join TB_OA_department b on b.fid=a.fcompany where a.fid='"+billid+"'").toString(); 
    		
    		List<String> tousers = new ArrayList<String>();
        	tousers.add(companyfinanceleader);
        	tousers.add(companyleader);
        	message.setTousers(tousers);
    	}
    	
    	messageservice.sendMessage(message);
    }
  
} 