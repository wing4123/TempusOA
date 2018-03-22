package cn.tempus.system.root;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tempus.dao.EasyDao;

@Service(value="homeservice")
public class HomeService {
	
	@Autowired
    private EasyDao basicService;
	
	/**
	 * 初始化首页数据
	 * @param userid
	 * @return
	 */
	public Map<String,Object> inithomedata(String userid){
		Map<String,Object> homedata = new HashMap<String,Object>();
		
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("user", userid);
		args.put("#SQL", "select distinct a.id_ ftaskid,a.name_ ftaskname,d.user_name fstartuser,c.start_time_ fstarttime,a.create_time_ fcreatetime,b.name_ fbillname,c.business_key_ fformid,a.PROC_DEF_ID_ processdefinitionid,a.PROC_INST_ID_ processinstanceid from act_ru_task a left join act_re_procdef b on b.id_=a.proc_def_id_ left join act_hi_procinst c on c.id_=a.proc_inst_id_ left join tb_user d on d.user_id=c.start_user_id_ left join act_ru_identitylink e on e.task_id_=a.id_ where (e.user_id_=#{user} or a.assignee_=#{user}) ");
		
		homedata.put("mytobedo", basicService.SelectListBySqlWithWhere(args));
		
		
		return homedata;
	}
	
	/**
	 * 顶部我的待办任务
	 * @param userid
	 * @return
	 */
	public List<HashMap<String,Object>> mytobedo(String userid){
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("user", userid);
		args.put("#SQL", "select * from (select distinct a.id_ ftaskid,a.name_ ftaskname,d.user_name fstartuser,c.start_time_ fstarttime,to_char(a.create_time_,'yyyy-mm-dd') fcreatetime,b.name_ fprocessname,c.business_key_ fformid,a.PROC_DEF_ID_ processdefinitionid,a.PROC_INST_ID_ processinstanceid from act_ru_task a left join act_re_procdef b on b.id_=a.proc_def_id_ left join act_hi_procinst c on c.id_=a.proc_inst_id_ left join tb_user d on d.user_id=c.start_user_id_ left join act_ru_identitylink e on e.task_id_=a.id_ where e.user_id_=#{user} or a.assignee_=#{user}) order by fcreatetime desc");
		
		return basicService.SelectListBySqlWithWhere(args);
	}
	
	/**
	 * 顶部我的消息
	 * @param userid
	 * @return
	 */
	public List<HashMap<String,Object>> getMyMessageList(String userid){
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("user", userid);
		args.put("#SQL", "select fid,ftitle,fcontent,furl,to_char(fsendtime,'yyyy-mm-dd') fsendtime from TB_OA_message where ftouser=#{user} and freadtime is null");
		
		return basicService.SelectListBySqlWithWhere(args);
	}
	
}
