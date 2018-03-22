package cn.tempus.leave;

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
public class LeaveService {
	
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
		
		args.put("quanxin", request.getParameter("quanxin"));
		args.put("kouxin", request.getParameter("kouxin"));
		args.put("heji", request.getParameter("heji"));
		args.put("cause", request.getParameter("cause"));
		args.put("workingarrangements", request.getParameter("workingarrangements"));
		args.put("handoverperson", request.getParameter("handoverperson"));
		
		StringBuffer entrysql = new StringBuffer("insert into TB_OA_leave_entry (fid,fparentid,ftype,fbegintime,fendtime,fdays,fremark,fseq)");
		String[] type = request.getParameterValues("type[]")==null?request.getParameterValues("type"):request.getParameterValues("type[]");
		String[] begintime = request.getParameterValues("begintime[]")==null?request.getParameterValues("begintime"):request.getParameterValues("begintime[]");
		String[] endtime = request.getParameterValues("endtime[]")==null?request.getParameterValues("endtime"):request.getParameterValues("endtime[]");
		String[] days = request.getParameterValues("days[]")==null?request.getParameterValues("days"):request.getParameterValues("days[]");
		String[] remark = request.getParameterValues("remark[]")==null?request.getParameterValues("remark"):request.getParameterValues("remark[]");
		for(int i=0;i<type.length;i++){
			entrysql.append(" select #{entryid"+i+"} fid,#{id} fparentid,#{type"+i+"} ftype,to_date(#{begintime"+i+"},'yyyy-mm-dd hh24:mi') fbegintime,to_date(#{endtime"+i+"},'yyyy-mm-dd hh24:mi') fendtime,#{days"+i+"} fdays,#{remark"+i+"} fremark,#{seq"+i+"} fseq from dual union");
			args.put("seq"+i, i+1);
			args.put("entryid"+i, UUID.randomUUID().toString());
			args.put("type"+i, type[i]);
			args.put("begintime"+i, begintime[i]);
			args.put("endtime"+i, endtime[i]);
			args.put("days"+i, days[i]);
			args.put("remark"+i, remark[i]);
		}
		args.put("#SQL", "delete from TB_OA_leave_entry where fparentid=#{id}");
		basicService.DeleteData(args);
		if(type.length>0){
			args.put("#SQL", entrysql.substring(0, entrysql.length()-6));
			basicService.InsertData(args);
		}
		
		int result=0;
		
		args.put("#SQL", "select count(*) from TB_OA_leave where fid=#{id}");//to_date(#{begindate},'yyyy-mm-dd')
		if(basicService.SelectCountBySqlWithWhere(args)==0){
			args.put("#SQL", "insert into TB_OA_leave (fid,fname,fcause,fworkingarrangements,fhandoverperson,fquanxin,fkouxin,fheji,fcreator,fcreatetime,fstatus) values ("
					+ "#{id},#{name},#{cause},#{workingarrangements},#{handoverperson},#{quanxin},#{kouxin},#{heji},#{user},#{time},#{status})");
			result = basicService.InsertData(args);
		}else{
			args.put("#SQL", "update TB_OA_leave set fname=#{name},fcause=#{cause},fworkingarrangements=#{workingarrangements},fhandoverperson=#{handoverperson},fquanxin=#{quanxin},fkouxin=#{kouxin},fheji=#{heji},fcreatetime=#{time},fstatus=#{status} where fid=#{id}");
			result = basicService.UpdateData(args);
		}
		
		if(request.getParameter("status").equals("2")){
			Map<String,Object> vars = new HashMap<String,Object>();
			vars.put("form_heji", args.get("heji"));
			
			String processinstanceid = myworkflowservice.SaveAndStart("WF007", id, user.get("userid").toString(),vars,args.get("name").toString(),"TB_OA_leave");
			args.put("#SQL", "update TB_OA_LEAVE set fprocessinstanceid=#{processinstanceid} where fid=#{id}");
			args.put("processinstanceid", processinstanceid);
			basicService.UpdateData(args);
		}
		
		return result;
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean Delete(String id){
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("id", id);
		args.put("#SQL", "delete from TB_OA_LEAVE where fid=#{id}");
		basicService.DeleteData(args);
		
		args.put("#SQL", "delete from TB_OA_LEAVE_ENTRY where fparentid=#{id}");
		basicService.DeleteData(args);
		
		fileservice.DeleteFileByBid(id, null);
		
		return true;
	}
	
	public Map<String,Object> InitialEditData(String id){
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("id", id);
		args.put("#SQL", "select a.*,b.user_name,d.fname departmentname,c.user_id fhandoverpersonid,c.user_name fhandoverpersonname from TB_OA_leave a left join tb_user b on b.user_id=a.fcreator left join tb_user c on c.user_id=a.fhandoverperson left join TB_OA_department d on d.fid=b.attribute15 where a.fid=#{id}");
		HashMap<String,Object> result = basicService.GetSinglerData(args);
		
		args.put("#SQL", "select ftype,to_char(fbegintime,'yyyy-mm-dd hh24:mi') fbegintime,to_char(fendtime,'yyyy-mm-dd hh24:mi') fendtime,fdays,fremark from TB_OA_leave_entry where fparentid=#{id} order by fseq");
		result.put("entry", basicService.SelectListBySqlWithWhere(args));
		
		return result;
	}

}
