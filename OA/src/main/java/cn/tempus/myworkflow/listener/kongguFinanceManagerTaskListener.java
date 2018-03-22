package cn.tempus.myworkflow.listener;

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

/** 
* @author 吴中贤 wing4123@163.com
* @date 2017年8月10日
* @Description: 控股财务经理（蒋卞）
*  
*/

@Service("kongguFinanceManagerTaskListener")
public class kongguFinanceManagerTaskListener implements Serializable, TaskListener {  
	
	private static final long serialVersionUID = 1L;

	@Autowired
	EasyDao basicservice;
  
    @Override  
    public void notify(DelegateTask task) {
    	Object starter = task.getVariable("sys_starter");
    	Object divisionnum = basicservice.GetFirstValueBySql("select a.fnumber from TB_OA_division a left join tb_user b on b.attribute13=a.fid where b.user_id='"+starter+"'");
    	if(divisionnum.toString().startsWith("1.5.2")){
    		task.setAssignee("2102");
    	}else{
    		task.setAssignee("1114");
    	}
    
    }

}