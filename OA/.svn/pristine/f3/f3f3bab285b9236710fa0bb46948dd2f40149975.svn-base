package cn.tempus.businesshospitality;

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

import cn.tempus.commons.FileService;
import cn.tempus.dao.EasyDao;
import cn.tempus.myworkflow.MyWorkFlowService;

@Service
public class BusinessHospitalityService {
	
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
		args.put("amount", request.getParameter("amount"));
		args.put("currency", request.getParameter("currency"));
		args.put("client", request.getParameter("client"));
		args.put("location", request.getParameter("location"));
		args.put("cause", request.getParameter("cause"));
		args.put("date", request.getParameter("date"));
		args.put("peoplenumber", request.getParameter("peoplenumber"));
		args.put("staff", request.getParameter("staff"));
		args.put("user", user.getString("userid"));
		args.put("time", new Date());
		args.put("status", StringUtils.isBlank(request.getParameter("status"))?"2":request.getParameter("status"));
		
		int result=0;
		
		args.put("#SQL", "select count(*) from TB_OA_businesshospitality where fid=#{id}");
		if(basicService.SelectCountBySqlWithWhere(args)==0){
			args.put("#SQL", "insert into TB_OA_businesshospitality (fid,fname,famount,fcurrency,fclient,flocation,fcause,fdate,fpeoplenumber,fstaff,fcreator,fcreatetime,fstatus) values (#{id},#{name},#{amount},#{currency},#{client},#{location},#{cause},to_date(#{date},'yyyy-mm-dd'),#{peoplenumber},#{staff},#{user},#{time},#{status})");
			result = basicService.InsertData(args);
		}else{
			args.put("#SQL", "update TB_OA_businesshospitality set fname=#{name},famount=#{amount},fcurrency=#{currency},fclient=#{client},flocation=#{location},fcause=#{cause},fdate=to_date(#{date},'yyyy-mm-dd'),fpeoplenumber=#{peoplenumber},fstaff=#{staff},fstatus=#{status} where fid=#{id}");
			result = basicService.UpdateData(args);
		}
		
		if(request.getParameter("status").equals("2")){
			Map<String,Object> vars = new HashMap<String,Object>();
			vars.put("form_amount", args.get("amount"));
			vars.put("form_currency", args.get("currency"));
			vars.put("allowend", "1");
			
			String processinstanceid = myworkflowservice.SaveAndStart("WF003", id, user.get("userid").toString(),vars,args.get("name").toString(),"TB_OA_businesshospitality");
			args.put("#SQL", "update TB_OA_businesshospitality set fprocessinstanceid=#{processinstanceid} where fid=#{id}");
			args.put("processinstanceid", processinstanceid);
			basicService.UpdateData(args);
		}
		
		return result;
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean Delete(String id){
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "delete from TB_OA_businesshospitality where fid=#{id}");
		args.put("id", id);
		basicService.DeleteData(args);
		
		fileservice.DeleteFileByBid(id, null);
		
		return true;
	}
	
	public Map<String,Object> InitialEditData(String id){
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("id", id);
		args.put("#SQL", "select * from TB_OA_businesshospitality where fid=#{id}");
		HashMap<String,Object> businesshospitality = basicService.GetSinglerData(args);
		
		return businesshospitality;
	}
	
	public Map<String,Object> InitialShowData(String id){
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("id", id);
		args.put("#SQL", "select a.*,b.user_name fcreatorname,c.fname fdepartmentname from TB_OA_businesshospitality a left join tb_user b on b.user_id=a.fcreator left join TB_OA_department c on c.fid=b.attribute15 where a.fid=#{id}");
		HashMap<String,Object> businesshospitality = basicService.GetSinglerData(args);
		
		return businesshospitality;
	}

}
