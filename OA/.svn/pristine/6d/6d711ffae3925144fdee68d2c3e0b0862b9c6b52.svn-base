package cn.tempus.contract.WF;

import java.io.Serializable;
import java.util.HashMap;

import org.activiti.engine.delegate.DelegateTask;  
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tempus.dao.EasyDao;  

/** 
* @author 吴中贤 wing4123@163.com
* @date 2017年9月27日
* @Description: 公司负责人
*  
*/
@Service("contract_zhinengbumenfuzeren")
public class zhinengbumenfuzerenListener implements Serializable, TaskListener {  
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	EasyDao basicservice;
  
    @Override  
    public void notify(DelegateTask task) {
    	Object starter = task.getVariable("sys_starter");
    	Object divisionid = basicservice.GetFirstValueBySql("select attribute13 from tb_user where user_id='"+starter+"'");
    	while (divisionid!=null){
    		HashMap<String, Object> division = basicservice.GetSinglerData("select fid,flevel,fparentid,NVL(fleader,'') fleader from TB_OA_division where fid='"+divisionid+"'");
    		if(division.get("FPARENTID").equals("8978d77e-18ed-4b42-81b6-1827f68a7547")){
    			task.setAssignee(division.get("FLEADER").toString());
    			divisionid = null;
    		}else{
    			divisionid = division.get("FPARENTID");
    		}
    	}
    }
  
}