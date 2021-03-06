package cn.tempus.travel;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.tempus.commons.FileService;
import cn.tempus.dao.EasyDao;
import cn.tempus.myworkflow.MyWorkFlowService;
import com.alibaba.fastjson.JSONObject;

@Service
public class TravelService {
	
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
		args.put("begindate", request.getParameter("begindate"));
		args.put("enddate", request.getParameter("enddate"));
		args.put("days", request.getParameter("days"));
		args.put("location", request.getParameter("location"));
		args.put("area", request.getParameter("area"));
		args.put("projecttype", request.getParameter("projecttype"));
		args.put("projectmanager", request.getParameter("projectleader"));
		args.put("cause", request.getParameter("cause"));
		args.put("hotelprice", request.getParameter("hotelprice"));
		args.put("hoteldays", request.getParameter("hoteldays"));
		args.put("hotelamount", request.getParameter("hotelamount"));
		args.put("hotelremark", request.getParameter("hotelremark"));
		args.put("foodprice", request.getParameter("foodprice"));
		args.put("fooddays", request.getParameter("fooddays"));
		args.put("foodamount", request.getParameter("foodamount"));
		args.put("foodremark", request.getParameter("foodremark"));
		args.put("otheramount", request.getParameter("otheramount"));
		args.put("otherremark", request.getParameter("otherremark"));
		args.put("businessamount", request.getParameter("businessamount"));
		args.put("businessclient", request.getParameter("businessclient"));
		args.put("businesslocation", request.getParameter("businesslocation"));
		args.put("businesscause", request.getParameter("businesscause"));
		args.put("businessjoinnumber", request.getParameter("businessjoinnumber"));
		args.put("businessstaff", request.getParameter("businessstaff"));
		args.put("totalamount", request.getParameter("totalamount"));
		args.put("currency", request.getParameter("currency"));
		args.put("name", request.getParameter("name"));
		args.put("user", user.getString("userid"));
		args.put("time", new Date());
		args.put("status", StringUtils.isBlank(request.getParameter("status"))?"2":request.getParameter("status"));
		
		int result=0;
		
		StringBuffer routsql = new StringBuffer("insert into TB_OA_travel_rout (fid,fparentid,FTRANSPORT,FDEPARTUREDATE,FDEPARTURECITY,FDESTINATIONCITY,FTRIPS,FCOST,FREMARK,FSEQ)");
		String[] transport = request.getParameterValues("transport[]")==null?request.getParameterValues("transport"):request.getParameterValues("transport[]");
		String[] departuredate = request.getParameterValues("departuredate[]")==null?request.getParameterValues("departuredate"):request.getParameterValues("departuredate[]");
		String[] departurecity = request.getParameterValues("departurecity[]")==null?request.getParameterValues("departurecity"):request.getParameterValues("departurecity[]");
		String[] destinationcity = request.getParameterValues("destinationcity[]")==null?request.getParameterValues("destinationcity"):request.getParameterValues("destinationcity[]");
		String[] trips = request.getParameterValues("trips[]")==null?request.getParameterValues("trips"):request.getParameterValues("trips[]");
		String[] cost = request.getParameterValues("cost[]")==null?request.getParameterValues("cost"):request.getParameterValues("cost[]");
		String[] remark = request.getParameterValues("remark[]")==null?request.getParameterValues("remark"):request.getParameterValues("remark[]");
		for(int i=0;cost!=null&&i<cost.length;i++){
			routsql.append(" select #{routid"+i+"} fid,#{id} fparentid,#{transport"+i+"} ftransport,to_date(#{departuredate"+i+"},'yyyy-mm-dd') fdeparturedate,#{departurecity"+i+"} fdeparturecity,#{destinationcity"+i+"} fdestinationcity,#{trips"+i+"} trips,#{cost"+i+"} cost,#{remark"+i+"} remark,#{seq"+i+"} fseq from dual union");
			args.put("seq"+i, i+1);
			args.put("routid"+i, UUID.randomUUID().toString());
			args.put("transport"+i, transport[i]);
			args.put("departuredate"+i, departuredate[i]);
			args.put("departurecity"+i, departurecity[i]);
			args.put("destinationcity"+i, destinationcity[i]);
			args.put("trips"+i, i<trips.length?trips[i]:null);
			args.put("cost"+i, cost[i]);
			args.put("remark"+i, i<remark.length?remark[i]:null);
		}
		args.put("#SQL", "delete from TB_OA_travel_rout where fparentid=#{id}");
		basicService.DeleteData(args);
		if(cost!=null && cost.length>0){
			args.put("#SQL", routsql.substring(0, routsql.length()-6));
			basicService.InsertData(args);
		}
		
		StringBuffer businesssql = new StringBuffer("insert into TB_OA_travel_business (fid,fparentid,famount,fclient,flocation,fcause,fjoinnumber,fstaff,FSEQ)");
		String[] businessamount = request.getParameterValues("businessamount[]")==null?request.getParameterValues("businessamount"):request.getParameterValues("businessamount[]");
		String[] businessclient = request.getParameterValues("businessclient[]")==null?request.getParameterValues("businessclient"):request.getParameterValues("businessclient[]");
		String[] businesslocation = request.getParameterValues("businesslocation[]")==null?request.getParameterValues("businesslocation"):request.getParameterValues("businesslocation[]");
		String[] businesscause = request.getParameterValues("businesscause[]")==null?request.getParameterValues("businesscause"):request.getParameterValues("businesscause[]");
		String[] businessjoinnumber = request.getParameterValues("businessjoinnumber[]")==null?request.getParameterValues("businessjoinnumber"):request.getParameterValues("businessjoinnumber[]");
		String[] businessstaff = request.getParameterValues("businessstaff[]")==null?request.getParameterValues("businessstaff"):request.getParameterValues("businessstaff[]");
		for(int i=0;businessamount!=null&&i<businessamount.length;i++){
			businesssql.append(" select #{business"+i+"} fid,#{id} fparentid,#{businessamount"+i+"} businessamount,#{businessclient"+i+"} businessclient,#{businesslocation"+i+"} businesslocation,#{businesscause"+i+"} businesscause,#{businessjoinnumber"+i+"} businessjoinnumber,#{businessstaff"+i+"} businessstaff,#{seq"+i+"} fseq from dual union");
			args.put("seq"+i, i+1);
			args.put("business"+i, UUID.randomUUID().toString());
			args.put("businessamount"+i, businessamount[i]);
			args.put("businessclient"+i, businessclient[i]);
			args.put("businesslocation"+i, businesslocation[i]);
			args.put("businesscause"+i, businesscause[i]);
			args.put("businessjoinnumber"+i, businessjoinnumber[i]);
			args.put("businessstaff"+i, businessstaff[i]);
		}
		args.put("#SQL", "delete from TB_OA_travel_business where fparentid=#{id}");
		basicService.DeleteData(args);
		if(businessamount!=null && businessamount.length>0){
			args.put("#SQL", businesssql.substring(0, businesssql.length()-6));
			basicService.InsertData(args);
		}
		
		args.put("#SQL", "select count(*) from TB_OA_TRAVEL where fid=#{id}");
		if(basicService.SelectCountBySqlWithWhere(args)==0){
			args.put("#SQL", "insert into TB_OA_TRAVEL (fid,fname,fbegindate,fenddate,fdays,flocation,farea,fprojecttype,fprojectmanager,fcause,fhotelprice,fhoteldays,fhotelamount,fhotelremark,ffoodprice,ffooddays,ffoodamount,ffoodremark,fotheramount,fotherremark,ftotalamount,fcurrency,fcreator,fcreatetime,flastupdatetime,fstatus) values ("
					+ "#{id},#{name},to_date(#{begindate},'yyyy-mm-dd'),to_date(#{enddate},'yyyy-mm-dd'),#{days},#{location},#{area},#{projecttype},#{projectmanager},#{cause},#{hotelprice},#{hoteldays},#{hotelamount},#{hotelremark},#{foodprice},#{fooddays},#{foodamount},#{foodremark},#{fotheramount},#{fotherremark},#{totalamount},#{currency},#{user},#{time},#{time},#{status})");
			result = basicService.InsertData(args);
		}else{
			args.put("#SQL", "update TB_OA_TRAVEL set fname=#{name},fbegindate=to_date(#{begindate},'yyyy-mm-dd'),fenddate=to_date(#{enddate},'yyyy-mm-dd'),fdays=#{days},flocation=#{location},farea=#{area},fprojecttype=#{projecttype},fprojectmanager=#{projectmanager},fcause=#{cause},fhotelprice=#{hotelprice},fhoteldays=#{hoteldays},fhotelamount=#{hotelamount},fhotelremark=#{hotelremark},ffoodprice=#{foodprice},ffooddays=#{fooddays},ffoodamount=#{foodamount},ffoodremark=#{foodremark},fotheramount=#{otheramount},fotherremark=#{fotherremark},ftotalamount=#{totalamount},fcurrency=#{currency},flastupdator=#{user},flastupdatetime=#{time},fstatus=#{status} where fid=#{id}");
			result = basicService.UpdateData(args);
		}
		
		if(request.getParameter("status").equals("2")){
			Map<String,Object> vars = new HashMap<String,Object>();
			vars.put("form_amount", args.get("totalamount"));
			vars.put("form_currency", args.get("currency"));
			vars.put("allowend", "1");
			
			String processinstanceid = myworkflowservice.SaveAndStart("WF004", id, user.get("userid").toString(),vars,args.get("name").toString(),"TB_OA_TRAVEL");
			args.put("#SQL", "update TB_OA_TRAVEL set fprocessinstanceid=#{processinstanceid} where fid=#{id}");
			args.put("processinstanceid", processinstanceid);
			basicService.UpdateData(args);
		}
		
		return result;
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean Delete(String id){
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "delete from TB_OA_travel where fid=#{id}");
		args.put("id", id);
		basicService.DeleteData(args);
		args.put("#SQL", "delete from TB_OA_travel_rout where fparentid=#{id}");
		
		fileservice.DeleteFileByBid(id, null);
		
		return true;
	}
	
	public Map<String,Object> InitialEditData(String id){
		Map<String,Object> result = new HashMap<String,Object>();
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("id", id);
		args.put("#SQL", "select a.*,b.user_name fcreatorname,to_char(a.fcreatetime,'yyyy-mm-dd') applytime,c.fname fdepartmentname,d.user_id fprojectmanagerid,d.user_name fprojectmanagername from TB_OA_TRAVEL a left join tb_user b on b.user_id=a.fcreator left join TB_OA_department c on c.fid=b.attribute15 left join tb_user d on d.user_id=a.fprojectmanager where a.fid=#{id}");
		HashMap<String,Object> travel = basicService.GetSinglerData(args);
		result.put("travel", travel);
		
		args.put("#SQL", "select * from TB_OA_travel_rout where fparentid=#{id} order by fseq");
		List<HashMap<String,Object>> travelrouts = basicService.SelectListBySqlWithWhere(args);
		result.put("travelrouts", travelrouts);
		
		args.put("#SQL", "select * from TB_OA_travel_business where fparentid=#{id} order by fseq");
		List<HashMap<String,Object>> business = basicService.SelectListBySqlWithWhere(args);
		result.put("business", business);
		
		return result;
	}
	
	public Map<String,Object> InitialShowData(String id){
		Map<String,Object> result = new HashMap<String,Object>();
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("id", id);
		args.put("#SQL", "select a.*,b.user_name fcreatorname,c.fname fdepartmentname,d.user_id fprojectmanagerid,d.user_name fprojectmanagername from TB_OA_TRAVEL a left join tb_user b on b.user_id=a.fcreator left join TB_OA_department c on c.fid=b.attribute15 left join tb_user d on d.user_id=a.fprojectmanager where a.fid=#{id}");
		HashMap<String,Object> travel = basicService.GetSinglerData(args);
		result.put("travel", travel);
		
		args.put("#SQL", "select * from TB_OA_travel_rout where fparentid=#{id} order by fseq");
		List<HashMap<String,Object>> travelrouts = basicService.SelectListBySqlWithWhere(args);
		result.put("travelrouts", travelrouts);
		
		args.put("#SQL", "select * from TB_OA_travel_business where fparentid=#{id} order by fseq");
		List<HashMap<String,Object>> business = basicService.SelectListBySqlWithWhere(args);
		result.put("business", business);
		
		return result;
	}

}
