package cn.tempus.businessout;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

import cn.tempus.commons.FileService;
import cn.tempus.dao.EasyDao;
import cn.tempus.myworkflow.MyWorkFlowService;

@Service
public class BusinessOutService {
	
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
		
		args.put("totalhours", request.getParameter("totalhours"));
		
		int result=0;
		
		StringBuffer entrysql = new StringBuffer("insert into TB_OA_businessout_entry (fid,fparentid,fbegintime,fendtime,fhours,fcause,fseq)");
		String[] begintime = request.getParameterValues("begintime[]")==null?request.getParameterValues("begintime"):request.getParameterValues("begintime[]");
		String[] endtime = request.getParameterValues("endtime[]")==null?request.getParameterValues("endtime"):request.getParameterValues("endtime[]");
		String[] hours = request.getParameterValues("hours[]")==null?request.getParameterValues("hours"):request.getParameterValues("hours[]");
		String[] cause = request.getParameterValues("cause[]")==null?request.getParameterValues("cause"):request.getParameterValues("cause[]");
		for(int i=0;i<hours.length;i++){
			entrysql.append(" select #{entryid"+i+"} fid,#{id} fparentid,to_date(#{begintime"+i+"},'yyyy-mm-dd hh24:mi') fbegintime,to_date(#{endtime"+i+"},'yyyy-mm-dd hh24:mi') fendtime,#{hours"+i+"} hours,#{cause"+i+"} fcause,#{seq"+i+"} fseq from dual union");
			args.put("seq"+i, i+1);
			args.put("entryid"+i, UUID.randomUUID().toString());
			args.put("begintime"+i, begintime[i]);
			args.put("endtime"+i, endtime[i]);
			args.put("hours"+i, hours[i]);
			args.put("cause"+i, cause[i]);
		}
		args.put("#SQL", "delete from TB_OA_businessout_entry where fparentid=#{id}");
		basicService.DeleteData(args);
		if(hours.length>0){
			args.put("#SQL", entrysql.substring(0, entrysql.length()-6));
			basicService.InsertData(args);
		}
		
		args.put("#SQL", "select count(*) from TB_OA_businessout where fid=#{id}");//to_date(#{begindate},'yyyy-mm-dd')
		if(basicService.SelectCountBySqlWithWhere(args)==0){
			args.put("#SQL", "insert into TB_OA_businessout (fid,fname,ftotalhours,fcreator,fcreatetime,fstatus) values ("
					+ "#{id},#{name},#{totalhours},#{user},#{time},#{status})");
			result = basicService.InsertData(args);
		}else{
			args.put("#SQL", "update TB_OA_businessout set fname=#{name},ftotalhours=#{totalhours},fcreatetime=#{time},fstatus=#{status} where fid=#{id}");
			result = basicService.UpdateData(args);
		}
		
		if(request.getParameter("status").equals("2")){
			Map<String,Object> vars = new HashMap<String,Object>();
			vars.put("form_totalhours", args.get("totalhours"));
			
			String processinstanceid = myworkflowservice.SaveAndStart("WF010", id, user.get("userid").toString(),vars,args.get("name").toString(),"TB_OA_businessout");
			args.put("#SQL", "update TB_OA_businessout set fprocessinstanceid=#{processinstanceid} where fid=#{id}");
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
		args.put("#SQL", "select a.*,b.user_name fcreatorname,c.fname fdepartmentname from TB_OA_businessout a left join tb_user b on b.user_id=a.fcreator left join TB_OA_department c on c.fid=b.attribute15 where a.fid=#{id}");
		HashMap<String,Object> result = basicService.GetSinglerData(args);
		args.put("#SQL", "select * from TB_OA_businessout_entry where fparentid=#{id} order by fseq");
		result.put("entry", basicService.SelectListBySqlWithWhere(args));
		
		return result;
	}

}
