package cn.tempus.attendance;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import cn.tempus.commons.BaseService;
import cn.tempus.report.ReportService;

@Service
@Transactional
public class AttendanceService extends BaseService {
	
	@Autowired
	ReportService reportservice;
	
	@Autowired
	HttpServletRequest request;
	
	//单据审批历史
	@Transactional
	public boolean uploaAbnormalList(CommonsMultipartFile file,String month){

		XSSFWorkbook wb = null;
		try {
			wb = new XSSFWorkbook(file.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		XSSFSheet sheet = wb.getSheetAt(0);
//		Map<String, Object> delete = new HashMap<String, Object>();
//		delete.put("#SQL", "delete from TB_OA_attendanceupload where fmonth=#{month} and fusercode=#{usercode} and fdate=to_date(#{date},'yyyy-mm-dd')");
//		delete.put("month", month);
//		
//		Map<String, Object> args = new HashMap<String, Object>();
//		args.put("month", month);
//
//		args.put("#SQL", "insert into TB_OA_attendanceupload (fid,fmonth,fusercode,fusername,fdepartment,fdate,fbegintime,fendtime) values (#{id},#{month},#{usercode},#{username},#{department},to_date(#{date},'yyyy-mm-dd'),to_date(#{begintime},'yyyy-mm-dd hh24:mi:ss'),to_date(#{endtime},'yyyy-mm-dd hh24:mi:ss'))");
		int i = 1;
		List<Map<String,Object>> listInsertArgs = new ArrayList<>();
		List<Object[]> listDeleteArgs = new ArrayList<Object[]>();
		while(sheet.getRow(i) != null && sheet.getRow(i).getCell(0) != null && sheet.getRow(i).getCell(0).getStringCellValue() != null && sheet.getRow(i).getCell(0).getStringCellValue() != "") {
			XSSFRow row = sheet.getRow(i);
//			args.put("id", UUID.randomUUID().toString());
//			args.put("usercode", row.getCell(0).getStringCellValue());
//			args.put("username", row.getCell(1).getStringCellValue());
//			args.put("department", row.getCell(2).getStringCellValue());
//			args.put("date", row.getCell(3).getStringCellValue());
//			args.put("begintime", row.getCell(4).getStringCellValue());
//			args.put("endtime", row.getCell(5).getStringCellValue());
//			
//			delete.put("usercode", row.getCell(0).getStringCellValue());
//			delete.put("date", row.getCell(3).getStringCellValue());
//			easyDao.DeleteData(delete);
//			easyDao.InsertData(args);
			
			String[] deletArgs = new String[3];
			deletArgs[0] = month;
			deletArgs[1] = row.getCell(0).getStringCellValue();
			deletArgs[2] = row.getCell(3).getStringCellValue();
			listDeleteArgs.add(deletArgs);
			
			Map<String,Object> insertArgs = new HashMap<String,Object>();
			insertArgs.put("month", month);
			insertArgs.put("id", UUID.randomUUID().toString());
			insertArgs.put("usercode", row.getCell(0).getStringCellValue());
			insertArgs.put("username", row.getCell(1).getStringCellValue());
			insertArgs.put("department", row.getCell(2).getStringCellValue());
			insertArgs.put("date", row.getCell(3).getStringCellValue());
			insertArgs.put("begintime", row.getCell(4).getStringCellValue());
			insertArgs.put("endtime", row.getCell(5).getStringCellValue());
			listInsertArgs.add(insertArgs);
			
			i++;
		}
		String deletSql = "delete from TB_OA_attendanceupload where fmonth=? and fusercode=? and fdate=to_date(?,'yyyy-mm-dd')";
		jdbc.batchUpdate(deletSql, listDeleteArgs);
		
		String insertSql = "insert into TB_OA_attendanceupload (fid,fmonth,fusercode,fusername,fdepartment,fdate,fbegintime,fendtime) values (:id,:month,:usercode,:username,:department,to_date(:date,'yyyy-mm-dd'),to_date(:begintime,'yyyy-mm-dd hh24:mi:ss'),to_date(:endtime,'yyyy-mm-dd hh24:mi:ss'))";
		namedJdbc.batchUpdate(insertSql, listInsertArgs.toArray(new Map[listInsertArgs.size()]));
		
		
		/*
		try {
			Connection conn = dataSource.getConnection();
			CallableStatement proc = null;
			proc = conn.prepareCall("{ call TB_OA_PKG.Attendance_match(?) }");
			proc.setString(1, month);
			proc.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		*/
		return true;
	}
	
	public Map<String, Object> getAttendanceData(){
		String month = request.getParameter("month");
		String divisionnum = request.getParameter("divisionnum");
		String search = request.getParameter("search[value]");
		Map<String, Object> args = new HashMap<String, Object>();
		/*
		StringBuffer sb = new StringBuffer("select a.fid,a.fmonth,a.fusercode,a.fusername,to_char(a.fdate,'yyyy-mm-dd') fdate,a.fbegintime,a.fendtime,a.fprocessinstanceid,decode(a.fstatus,'2 ','审批中','3 ','已完成','66','全天旷工','40','上午迟到','60','上午旷工','64','上午旷工下午迟到','05','下午早退','06','下午旷工','56','上午早退下午旷工','45','上午迟到下午早退','65','上午旷工下午早退','46','上午迟到下午旷工','40','上午迟到','60','上午旷工','error') fstatus,decode(a.ftype,1,'出差',2,'请假',3,'外出','') ftype,a.fbegintime2,a.fendtime2,c.fname fdivisionname from TB_OA_attendanceupload a left join tb_user b on b.user_code=a.fusercode left join TB_OA_division c on c.fid=b.attribute13 where 1=1");
		if(month != "") {
			sb.append(" and a.fmonth=#{month}");
			args.put("month", month);
		}
		if(divisionnum != "") {
			sb.append(" and c.fnumber like #{divisionnum}||'%'");
			args.put("divisionnum", divisionnum);
		}
		if(search != "") {
			sb.append(" and (a.fusername like #{search}||'%' or a.fusercode like #{search}||'%' or a.fprocessinstanceid like #{search}||'%')");
			args.put("search", search);
		}
		*/
//		StringBuffer sb = new StringBuffer("select a.fmonth,a.fusercode,c.user_name fusername,d.fname FDIVISIONNAME,to_char(a.fdate,'yyyy-mm-dd') fdate,a.fbegintime,a.fendtime,b.FPROCESSINSTANCEID,b.FBEGINTIME fbegintime2,b.FENDTIME fendtime2,decode(b.FSTATUS,'2','审批中',3,'已完成','缺勤') fstatus,decode(b.FTYPE,1,'出差',2,'请假',3,'考勤异常',4,'公事外出') ftype from TB_OA_attendanceupload a left join v_oa_attendance b on b.user_code=a.fusercode and decode(a.fbegintime,null,a.fdate+INTERVAL '8' HOUR + INTERVAL '30' MINUTE,a.fbegintime)>=b.FBEGINTIME and decode(a.fendtime,null,a.fdate+INTERVAL '18' HOUR,a.fendtime)<=decode(to_char(b.FENDTIME,'hh24'),'00',b.FENDTIME+1,b.FENDTIME) left join tb_user c on c.user_code=a.fusercode left join TB_OA_division d on d.fid=c.attribute13 where 1=1");
		StringBuffer sb = new StringBuffer("select a.fmonth,a.fusercode,c.user_name fusername,d.fname FDIVISIONNAME,to_char(a.fdate,'yyyy-mm-dd') fdate,a.fbegintime,a.fendtime,b.FPROCESSINSTANCEID,b.FBEGINTIME fbegintime2,b.FENDTIME fendtime2,case when (a.fbegintime <= (a.fdate + INTERVAL '8' HOUR + INTERVAL '30' MINUTE) or e.fmintime<=(a.fdate + INTERVAL '8' HOUR + INTERVAL '30' MINUTE)) and (a.fendtime>=(a.fdate + INTERVAL '18' HOUR) or e.fmaxtime>=(a.fdate + INTERVAL '18' HOUR)) then decode(b.FSTATUS,'2','审批中',3,'已完成','缺勤') else '缺勤' end  fstatus,decode(b.FTYPE,1,'出差',2.1,'年假',2.2,'调休',2.3,'婚假',2.4,'产假',2.5,'陪产假',2.6,'产检假',2.7,'丧假',-2.1,'事假',-2.2,'病假',3,'考勤异常',4,'公事外出') ftype from TB_OA_attendanceupload a left join v_oa_attendance b on b.user_code=a.fusercode and trunc(b.FBEGINTIME ,'dd') <= to_date(a.fdate) and trunc(b.FENDTIME ,'dd') >= to_date(a.fdate) left join tb_user c on c.user_code=a.fusercode left join TB_OA_division d on d.fid=c.attribute13 left join (select a.fusercode,a.fdate,min(b.FBEGINTIME) fmintime,max(b.FENDTIME) fmaxtime from tb_oa_attendanceupload a left join v_oa_attendance b on trunc(b.FBEGINTIME ,'dd') <= to_date(a.fdate) and trunc(b.FENDTIME ,'dd') >= to_date(a.fdate) and a.fusercode=b.user_code group by a.fusercode,a.fdate) e on e.fusercode=a.fusercode and e.fdate=a.fdate where 1=1 ");
		if(month != "") {
			sb.append(" and a.fmonth=#{month}");
			args.put("month", month);
		}
		if(divisionnum != "") {
			sb.append(" and d.fnumber like #{divisionnum}||'%'");
			args.put("divisionnum", divisionnum);
		}
		if(search != "") {
			sb.append(" and (c.user_name like #{search}||'%' or a.fusercode like #{search}||'%' or a.fprocessinstanceid like #{search}||'%')");
			args.put("search", search);
		}

		args.put("#SQL", sb);
		Map<String, Object> result = getDateTableRecord(args);
		
		reportservice.SaveReportArgs(args, request);

		return result;
	}
	
	
	

}
