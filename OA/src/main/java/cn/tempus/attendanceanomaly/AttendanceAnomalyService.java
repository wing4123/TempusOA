package cn.tempus.attendanceanomaly;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

import cn.tempus.commons.BaseService;
import cn.tempus.commons.FileService;
import cn.tempus.dao.EasyDao;
import cn.tempus.myworkflow.MyWorkFlowService;

@Service
public class AttendanceAnomalyService extends BaseService{
	
	@Autowired
    private EasyDao basicService;
	
	@Autowired
	MyWorkFlowService myworkflowservice;
	
	@Autowired
	FileService fileservice;
	
	@Autowired
	HttpServletRequest request;
	
	@Transactional(propagation=Propagation.REQUIRED)
	public int Save(){
		JSONObject user = (JSONObject) JSONObject.toJSON(request.getSession().getAttribute("USER"));
		String id = request.getParameter("id");
		
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("id", id);
		args.put("name", request.getParameter("name"));
		args.put("user", user.getString("userid"));
		args.put("time", new Date());
		args.put("status", StringUtils.isBlank(request.getParameter("status"))?"2":request.getParameter("status"));
		
		args.put("begintime", request.getParameter("begintime"));
		args.put("endtime", request.getParameter("endtime"));
		args.put("type", request.getParameter("type"));
		args.put("description", request.getParameter("description"));
		
		int result=0;
		
		args.put("#SQL", "select count(*) from TB_OA_attendanceanomaly where fid=#{id}");//to_date(#{begindate},'yyyy-mm-dd')
		if(basicService.SelectCountBySqlWithWhere(args)==0){
			args.put("#SQL", "insert into TB_OA_attendanceanomaly (fid,fname,fbegintime,fendtime,ftype,fdescription,fcreator,fcreatetime,fstatus) values ("
					+ "#{id},#{name},to_date(#{begintime},'yyyy-mm-dd hh24:mi'),to_date(#{endtime},'yyyy-mm-dd hh24:mi'),#{type},#{description},#{user},#{time},#{status})");
			result = basicService.InsertData(args);
		}else{
			args.put("#SQL", "update TB_OA_attendanceanomaly set fname=#{name},fbegintime=to_date(#{begintime},'yyyy-mm-dd hh24:mi'),fendtime=to_date(#{endtime},'yyyy-mm-dd hh24:mi'),ftype=#{type},fdescription=#{description},fcreatetime=#{time},fstatus=#{status} where fid=#{id}");
			result = basicService.UpdateData(args);
		}
		
		if(request.getParameter("status").equals("2")){
			Map<String,Object> vars = new HashMap<String,Object>();
			
			String processinstanceid = myworkflowservice.SaveAndStart("WF009", id, user.get("userid").toString(),vars,args.get("name").toString(),"TB_OA_attendanceanomaly");
			args.put("#SQL", "update TB_OA_attendanceanomaly set fprocessinstanceid=#{processinstanceid} where fid=#{id}");
			args.put("processinstanceid", processinstanceid);
			basicService.UpdateData(args);
		}
		
		return result;
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean Delete(String id){
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("id", id);
		args.put("#SQL", "delete from TB_OA_attendanceanomaly where fid=#{id}");
		basicService.DeleteData(args);
		
		fileservice.DeleteFileByBid(id, null);
		
		return true;
	}
	
	public Map<String,Object> InitialEditData(String id){
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("id", id);
		args.put("#SQL", "select a.*,b.user_name fcreatorname,c.fname fdepartmentname from TB_OA_attendanceanomaly a left join tb_user b on b.user_id=a.fcreator left join TB_OA_department c on c.fid=b.attribute15 where a.fid=#{id}");
		HashMap<String,Object> result = basicService.GetSinglerData(args);
		
		return result;
	}

}
