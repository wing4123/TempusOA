package cn.tempus.contract.WF;

import java.io.Serializable;

import org.activiti.engine.delegate.DelegateTask;  
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tempus.dao.EasyDao;  

/** 
* @author 吴中贤 wing4123@163.com
* @date 2017年8月9日
* @Description: 查找事业部分支财务负责人
*  
*/

@Service("contract_FindDivisionFinanceLeaderTaskListener")
public class FindDivisionFinanceLeaderTaskListener implements Serializable,TaskListener {  
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	EasyDao basicservice;
  
    @Override  
    public void notify(DelegateTask task) {
    	Object starter = task.getVariable("sys_starter");
    	Object divisionid = basicservice.GetFirstValueBySql("select attribute13 from tb_user where user_id='"+starter+"'");
    	while (divisionid!=null){
    		Object leader = basicservice.GetFirstValueBySql("select fleader from TB_OA_division where fparentid='"+divisionid+"' and fname='财务部'");
    		if(leader!=null){
    			task.setAssignee(leader.toString());
    			divisionid=null;
    		}else{
    			divisionid = basicservice.GetFirstValueBySql("select fparentid from TB_OA_division where fid='"+divisionid+"'");
    		}
    	}
    }

}