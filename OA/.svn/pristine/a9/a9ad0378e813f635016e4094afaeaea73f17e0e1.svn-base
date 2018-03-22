package cn.tempus.businesshospitality.WF;

import java.io.Serializable;

import org.activiti.engine.delegate.DelegateTask;  
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tempus.dao.EasyDao;  

/** 
* @author 吴中贤 wing4123@163.com
* @date 2017年9月26日
* @Description: 判断提交人事业部级别
*  
*/

@Service("businesshospitality_judgedivisionleveltasklistener")
public class JudgeDivisionLevelTaskListener implements Serializable, TaskListener {  
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private EasyDao basicservice;
  
    @Override  
    public void notify(DelegateTask task) {
    	Object starter = task.getVariable("sys_starter");
    	Object level = basicservice.GetFirstValueBySql("select a.flevel from TB_OA_division a left join tb_user b on b.attribute13=a.fid where b.user_id='"+starter+"'");
    	task.setVariable("v_level", level);
    }

}