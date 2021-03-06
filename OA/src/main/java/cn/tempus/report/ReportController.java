package cn.tempus.report;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import cn.tempus.commons.BaseService;

import com.alibaba.fastjson.JSONArray;

@Controller
@RequestMapping("/Report")
public class ReportController {
	
	@Autowired
	ReportService reportservice;
	
	@Autowired
	BaseService baseService;
	
	@Autowired
	HttpServletRequest request;
	
	@RequestMapping("/Travel")
	public String PermissionManage(){
		String userid = ((JSONObject) JSONObject.toJSON(request.getSession().getAttribute("USER"))).getString("userid");
		List<Map<String,Object>> permissiontree = reportservice.getpermission(userid);
		request.setAttribute("permissiontree", JSONArray.toJSON(permissiontree));
		
		baseService.initmenu( "/Report/Travel");
		return "report/travel";
	}
	
	@RequestMapping("/getTravelData")
	@ResponseBody
	public Map<String,Object> getTravelData(){
		String divisionnum = request.getParameter("divisionnum");
		String createdate_from = request.getParameter("createdate_from");
		String createdate_to = request.getParameter("createdate_to");
		
		Map<String,Object> args = new HashMap<String,Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("select j.* from (");
		sb.append("select a.fprocessinstanceid f1,a.fname f2,b.user_name f3,e.fname f4,decode(a.fstatus,'2','审批中','3','已办结','error') f5,to_char(a.fcreatetime,'yyyy-mm-dd') f6,to_char(a.fbegindate,'yyyy-mm-dd') f7,to_char(fenddate,'yyyy-mm-dd') f8,a.fdays f9,a.flocation f10,decode(a.farea,'1','内地','2','港澳台','3','海外') f11,a.fcause f12,a.fhotelprice f13,a.fhoteldays f14,a.fhotelamount f15,a.fhotelremark f16,a.ffoodprice f17,a.ffooddays f18,a.ffoodamount f19,a.ffoodremark f20,null f21,null f22,null f23,null f24,null f25,null f26,null f27,null f28,null f29,null f30,null f31,null f32,null f33,b.attribute13 xa,a.fotheramount f34,a.fotherremark f35 from TB_OA_travel a left join tb_user b on b.user_id=a.fcreator left join TB_OA_department e on e.fid=b.attribute15 where a.fstatus='2' or a.fstatus='3' or a.fstatus is null");
		sb.append(" union ");
		sb.append("select a.fprocessinstanceid f1,a.fname f2,b.user_name f3,e.fname f4,decode(a.fstatus,'2','审批中','3','已办结','error') f5,to_char(a.fcreatetime,'yyyy-mm-dd') f6,to_char(a.fbegindate,'yyyy-mm-dd') f7,to_char(fenddate,'yyyy-mm-dd') f8,null f9,a.flocation f10,decode(a.farea,'1','内地','2','港澳台','3','海外') f11,a.fcause f12,null f13,null f14,null f15,null f16,null f17,null f18,null f19,null f20,decode(c.ftransport,'1','飞机','2','火车','3','市内交通','4','私车公用') f21,c.fdeparturedate f22,c.fdeparturecity f23,c.fdestinationcity f24,c.ftrips f25,c.fcost f26,c.fremark f27,null f28,null f29,null f30,null f31,null f32,null f33,b.attribute13 xa,null f34,null f35 from TB_OA_travel a left join tb_user b on b.user_id=a.fcreator left join TB_OA_travel_rout c on c.fparentid=a.fid left join TB_OA_department e on e.fid=b.attribute15 where a.fstatus='2' or a.fstatus='3' or a.fstatus is null");
		sb.append(" union ");
		sb.append("select a.fprocessinstanceid f1,a.fname f2,b.user_name f3,e.fname f4,decode(a.fstatus,'2','审批中','3','已办结','error') f5,to_char(a.fcreatetime,'yyyy-mm-dd') f6,to_char(a.fbegindate,'yyyy-mm-dd') f7,to_char(fenddate,'yyyy-mm-dd') f8,null f9,a.flocation f10,decode(a.farea,'1','内地','2','港澳台','3','海外') f11,a.fcause f12,null f13,null f14,null f15,null f16,null f17,null f18,null f19,null f20,null f21,null f22,null f23,null f24,null f25,null f26,null f27,d.fclient f28,d.flocation f29,d.fcause f30,d.famount f31,d.fjoinnumber f32,d.fstaff f33,b.attribute13 xa,null f34,null f35 from TB_OA_travel a left join tb_user b on b.user_id=a.fcreator left join TB_OA_travel_business d on d.fparentid=a.fid left join TB_OA_department e on e.fid=b.attribute15 where a.fstatus='2' or a.fstatus='3' or a.fstatus is null");
		sb.append(") j left join TB_OA_division k on k.fid=j.xa inner join tb_oa_role_org l on l.forgid=k.fid and l.forgtype='division' where 1=1");
		if(divisionnum!="") {
			sb.append(" and (k.fnumber like #{divisionnum}||'.%' or k.fnumber=#{divisionnum})");
			args.put("divisionnum", divisionnum);
		}
		
		if(createdate_from!="") {
			sb.append(" and to_date(j.f6,'yyyy-mm-dd')>=to_date(#{createdate_form},'yyyy-mm-dd')");
			args.put("createdate_form", createdate_from);
		}
		if(createdate_to!="") {
			sb.append(" and to_date(j.f6,'yyyy-mm-dd')<to_date(#{createdate_to},'yyyy-mm-dd')+1");
			args.put("createdate_to", createdate_to);
		}
		
		args.put("#SQL", sb);
		
		Map<String, Object> result = reportservice.getReportData(args);
		
		reportservice.SaveReportArgs(args, request);
		
		return result;
	}
	
    @RequestMapping("ExportTravel")
    public ResponseEntity<byte[]> ExportTravel() throws IOException {
    	@SuppressWarnings("unchecked")
		Map<String,Object> args = (Map<String, Object>) request.getSession().getAttribute("report_args");
    	String sql = args.get("#SQL").toString();
    	args.put("#SQL", StringUtils.substringBeforeLast(sql, "where") );
		String[] header = new String[] {"序号","流程ID","标题","申请人","部门","审批状态","申请日期","开始日期","结束日期","出差天数","出差地点","地域","出差事由","酒店/住宿单价","酒店/住宿人天","酒店/住宿金额","酒店/住宿备注","餐饮单价","餐饮人天","餐饮金额","餐饮备注","其他金额","其他备注","交通工具","日期","出发城市","抵达城市","航班/车次/公里数","费用","其他说明","客户","地点","事由	金额","参加人数","公司陪同人员"};
		String[] columns = new String[] {"RN","F1","F2","F3","F4","F5","F6","F7","F8","F9","F10","F11","F12","F13","F14","F15","F16","F17","F18","F19","F20","F34","F35","F21","F22","F23","F24","F25","F26","F27","F28","F29","F30","F31","F32","F33"};
		byte[] excel = reportservice.Export(args, header, columns);
		args.put("#SQL", sql);
        HttpHeaders headers = new HttpHeaders();    
        headers.setContentDispositionFormData("attachment", "出差报表"+Calendar.getInstance().getTimeInMillis()+".xlsx");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);   
        return new ResponseEntity<byte[]>(excel,headers, HttpStatus.OK);
    }
	

}
