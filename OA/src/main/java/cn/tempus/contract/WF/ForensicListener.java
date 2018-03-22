package cn.tempus.contract.WF;

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

@Service("contract_ForensicListener")
public class ForensicListener implements Serializable, TaskListener {  
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	EasyDao basicservice;
  
    @Override  
    public void notify(DelegateTask task) {
    	List<HashMap<String, Object>> users = basicservice.SelectListBySql("select b.user_id from TB_OA_division a inner join tb_user b on b.attribute13=a.fid where a.fname='风控法务部'");
    	Collection<String> candidateUsers = new ArrayList<String>();
    	for(HashMap<String,Object> user:users){
    		candidateUsers.add(user.get("USER_ID").toString());
    	}
    	task.addCandidateUsers(candidateUsers);
    }  
  
}  