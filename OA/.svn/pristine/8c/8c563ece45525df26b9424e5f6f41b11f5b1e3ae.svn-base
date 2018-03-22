package cn.tempus.payment.WF;

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
* @Description: 出纳
*  
*/
@Service("payment_FindTellerTaskListener")
public class FindTellerTaskListener implements Serializable, TaskListener {  
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	EasyDao basicservice;
  
    @Override  
    public void notify(DelegateTask task) {
    	
    	Object billid = task.getVariable("sys_billid");
    	Object table = task.getVariable("sys_table");
    	String companyid = basicservice.GetFirstValueBySql("select fpaymentcompany from "+table+" where fid='"+billid+"'").toString();
//    	String companyid = basicservice.GetFirstValueBySql("select c.fid from "+table+" a left join fnd_flex_values_vl b on b.flex_value=a.fcostcompany and b.FLEX_VALUE_SET_ID=1014867 and b.attribute4='OA' left join TB_OA_department c on c.fname=b.description where a.fid='"+billid+"'").toString();
    	 
		HashMap<String, Object> department = basicservice.GetSinglerData("select b.fid,b.fparentid,b.fname from TB_OA_department a left join TB_OA_department b on b.fparentid=a.fid where a.fid='"+companyid+"' and b.fname='财务部'");
    	if(department!=null){
    		List<HashMap<String, Object>> userlist = basicservice.SelectListBySql("select a.user_id from tb_user a left join TB_OA_userposition b on b.fuserid=a.user_id left join TB_OA_position c on fid=b.fpositionid where a.attribute15='"+department.get("FID")+"' and c.fname='出纳'");
			Collection<String> candidateUsers = new ArrayList<String>();
			for(HashMap<String,Object> user: userlist){
				candidateUsers.add(user.get("USER_ID").toString());
			}
			task.addCandidateUsers(candidateUsers);
    	}
    	
    }

}