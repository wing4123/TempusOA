package cn.tempus.overtime.WF;

import java.io.Serializable;
import java.util.HashMap;

import org.activiti.engine.delegate.DelegateTask;  
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tempus.dao.EasyDao;  

/** 
* @author 吴中贤 wing4123@163.com
* @date 2017年8月9日
* @Description: 所属区域
*  
*/

@Service("overtime_AreaTaskListener")
public class FindAreaTaskListener implements Serializable,TaskListener {  
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	EasyDao basicservice;
  
    @Override  
    public void notify(DelegateTask task) {
    	Object starter = task.getVariable("sys_starter");
    	Object divisionid = basicservice.GetFirstValueBySql("select attribute13 from tb_user where user_id='"+starter+"'");
    	while (divisionid!=null){
    		HashMap<String, Object> division = basicservice.GetSinglerData("select fid,flevel,fparentid,fname from TB_OA_division where fid='"+divisionid+"'");
    		if("2".equals(division.get("FLEVEL"))){
    			if(division.get("FNAME").equals("OTO事业部-北方大区")){
    				task.setVariable("area", "north");
    			}else{
    				task.setVariable("area", "south");
    			}
    			divisionid = null;
    		}else if("5".equals(division.get("FLEVEL"))) {
    			task.setVariable("area", "headquarters");
    			divisionid = null;
    		}else{
    			divisionid = division.get("FPARENTID");
    		}
    	}
    }

}