package cn.tempus.myworkflow.listener;

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
* @Description: 查找事业部分支/总部职能负责人
*  
*/

@Service("FindDivisionLeaderTaskListener1")
public class FindDivisionLeaderTaskListener1 implements Serializable, TaskListener {  
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	EasyDao basicservice;
  
    @Override  
    public void notify(DelegateTask task) {
    	Object starter = task.getVariable("sys_starter");
    	Object divisionid = basicservice.GetFirstValueBySql("select attribute13 from tb_user where user_id='"+starter+"'");
    	while (divisionid!=null){
    		HashMap<String, Object> division = basicservice.GetSinglerData("select fid,flevel,fparentid,NVL(fleader,'') fleader from TB_OA_division where fid='"+divisionid+"'");
    		if(division.get("FLEVEL")!=null && !division.get("FLEVEL").toString().equals("1")){
    			task.setAssignee(division.get("FLEADER").toString());
    			divisionid = null;
    		}else{
    			divisionid = division.get("FPARENTID");
    		}
    	}
    }

}