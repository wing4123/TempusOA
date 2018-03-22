package cn.tempus.businesshospitality.WF;

import java.io.Serializable;
import java.util.HashMap;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.delegate.DelegateTask;  
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tempus.dao.EasyDao;  

/** 
* @author 吴中贤 wing4123@163.com
* @date 2017年8月9日
* @Description: 查找事业部分支负责人
*  
*/

@Service("businesshospitality_FindDivisionLeaderTaskListener1")
public class FindDivisionLeaderTaskListener1 implements Serializable, TaskListener {  
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	EasyDao basicservice;
	
	@Autowired
	private ProcessEngine processengine;
    
    @Override  
    public void notify(DelegateTask task) {
    	Object starter = task.getVariable("sys_starter");
    	Object divisionid = basicservice.GetFirstValueBySql("select attribute13 from tb_user where user_id='"+starter+"'");
    	while (divisionid!=null){
    		HashMap<String, Object> division = basicservice.GetSinglerData("select fid,flevel,fparentid,NVL(fleader,'') fleader from TB_OA_division where fid='"+divisionid+"'");
			if(division.get("FLEVEL")!=null && "25".contains(division.get("FLEVEL").toString())){
				task.setAssignee(division.get("FLEADER").toString());
				divisionid = null;
				task.setVariable("level", division.get("FLEVEL").toString());
			}else if(division.get("FLEVEL")!=null && "34".contains(division.get("FLEVEL").toString())){
				processengine.getTaskService().complete(task.getId());
				divisionid = null;
				task.setVariable("level", division.get("FLEVEL").toString());
			}else{
    			divisionid = division.get("FPARENTID");
    		}
    	}
    }

}