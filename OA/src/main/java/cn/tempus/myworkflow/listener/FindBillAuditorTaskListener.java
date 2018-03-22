package cn.tempus.myworkflow.listener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.activiti.engine.delegate.DelegateTask;  
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tempus.dao.EasyDao;  

/** 
* @author 吴中贤 wing4123@163.com
* @date 2017年8月26日
* @Description: 财务审单人
*  
*/
@Service("FindBillAuditorTaskListener")
public class FindBillAuditorTaskListener implements Serializable, TaskListener {  
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	EasyDao basicservice;
  
    @Override  
    public void notify(DelegateTask task) {
    	Object starter = task.getVariable("sys_starter");
    	Object divisionid = basicservice.GetFirstValueBySql("select attribute13 from tb_user where user_id='"+starter+"'");
    	
    	while (divisionid!=null){
    		HashMap<String, Object> division = basicservice.GetSinglerData("select b.fid,b.fparentid,b.fname from TB_OA_division a left join TB_OA_division b on b.fparentid=a.fparentid where a.fid='"+divisionid+"' and b.fname='财务部'");
        	if(division!=null){
        		List<HashMap<String, Object>> userlist = basicservice.SelectListBySql("select a.user_id from tb_user a left join TB_OA_userposition b on b.fuserid=a.user_id left join TB_OA_position c on fid=b.fpositionid where a.attribute13='"+division.get("FID")+"' and c.fname='财务单据审核人'");
    			Collection<String> candidateUsers = new ArrayList<String>();
    			for(HashMap<String,Object> user: userlist){
    				candidateUsers.add(user.get("USER_ID").toString());
    			}
    			task.addCandidateUsers(candidateUsers);
    			
    			Object divisionlevel = basicservice.GetFirstValueBySql("select flevel from TB_OA_division where fid='"+divisionid+"'");
    			task.setVariable("divisionlevel", divisionlevel);
    			
    			divisionid = null;
        	}else{
        		divisionid=basicservice.GetFirstValueBySql("select fparentid from TB_OA_division where fid='"+divisionid+"'");
        	}
    	}
    }

}