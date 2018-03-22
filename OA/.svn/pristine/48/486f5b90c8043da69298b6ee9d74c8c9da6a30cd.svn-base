package cn.tempus.attendance;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSONObject;

import cn.tempus.commons.BaseService;
import cn.tempus.report.ReportService;
import cn.tempus.system.department.DepartmentService;

/**
 * @author 吴中贤wing4123@163.com
 * @date 2017年12月8日
 * @description 考勤管理
 */
@Controller
public class AttendanceController{
	
	@Autowired
	AttendanceService myservice;
	
	@Autowired
	DepartmentService departmentservice;
	
	@Autowired
	ReportService reportservice;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	BaseService baseService;
	
	/**
	 * 考勤管理页面
	 */
	@GetMapping(value = "/attendances")
	public String attendanceManage(){
		List<Map<String,Object>> divisiontree = departmentservice.getDivisionTree();
		request.setAttribute("divisiontree", JSONObject.toJSON(divisiontree));
		
		baseService.initmenu("/attendances");
		return "attendance/attendanceManage";
	}
	
	/**
	 * 获取考勤数据
	 */
	@GetMapping(value = "/attendances/data")
	@ResponseBody
	public Map<String, Object> getAttendanceData() {
		Map<String, Object> result = myservice.getAttendanceData();
		return result;
	}
	
	@PostMapping(value = "/attendances/upload")
	@ResponseBody
	public boolean uploaAbnormalList(@RequestParam("excelfile") CommonsMultipartFile file, @RequestParam("month") String month) {
		boolean result = myservice.uploaAbnormalList(file, month);
		return result;
	}
	
    @GetMapping("/attendances/export")
    public ResponseEntity<byte[]> export() throws IOException {
    	@SuppressWarnings("unchecked")
		Map<String,Object> args = (Map<String, Object>) request.getSession().getAttribute("report_args");
    	String sql = args.get("#SQL").toString();
    	args.put("#SQL", StringUtils.substringBeforeLast(sql, "where") );
		String[] header = new String[] {"序号","月份","姓名","工号","事业部","日期","上班打卡时间","下班打卡时间","考勤状态","流程ID","单据类型","开始时间","结束时间"};
		String[] columns = new String[] {"RN","FMONTH","FUSERNAME","FUSERCODE","FDIVISIONNAME","FDATE","FBEGINTIME","FENDTIME","FSTATUS","FPROCESSINSTANCEID","FTYPE","FBEGINTIME2","FENDTIME2"};
		
		byte[] excel = reportservice.Export(args, header, columns);
		args.put("#SQL", sql);
        HttpHeaders headers = new HttpHeaders();    
//        headers.setContentDispositionFormData("attachment", "考勤异常表"+Calendar.getInstance().getTimeInMillis()+".xlsx");
        headers.setContentDispositionFormData("attachment", new String(("考勤异常表"+Calendar.getInstance().getTimeInMillis()+".xlsx").getBytes("UTF-8"),"ISO-8859-1"));
//        headers.setContentDispositionFormData("attachment", URLEncoder.encode("考 勤异++常ss表"+Calendar.getInstance().getTimeInMillis()+".xlsx", "UTF-8"));
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(excel,headers,HttpStatus.OK);
    }
	
	
}
