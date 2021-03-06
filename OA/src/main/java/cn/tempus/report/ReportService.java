package cn.tempus.report;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tempus.commons.BaseService;
import cn.tempus.dao.EasyDao;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

@Service
public class ReportService extends BaseService {
	
	@Autowired
	HttpServletRequest request;
	
	public Map<String,Object> getReportData(Map<String,Object> args){
		int start = Integer.parseInt(request.getParameter("start"));
		int length = Integer.parseInt(request.getParameter("length"));
		StringBuffer sql = new StringBuffer("select rownum rn,t.* from ("+args.get("#SQL"));
		String orderString="";
		int i=0;
		while(true){
			String col= request.getParameter("order["+i+"][column]"); 			
			if(col==null)break;
			int column=Integer.parseInt(col.toString());
			orderString = ","+request.getParameter("columns["+column+"][data]")+" "+request.getParameter("order["+i+"][dir]"); 
			i++;
		}
		if(!orderString.equals("")){
			sql.append(" order by ").append(orderString.substring(1));
		}
		
		sql.append(") t");
		
		args.put("#SQL", "select count(*) from ( "+sql+")");
		int total = easyDao.SelectCountBySqlWithWhere(args);
		sql.insert(0, "select tt.* from (").append(") tt where rn>").append(start).append(" and rn<=").append(start+length);
		args.put("#SQL", sql);
		List<HashMap<String, Object>> data = easyDao.SelectListBySqlWithWhere(args);
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("data", data);
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		result.put("draw", request.getParameter("draw"));
		
		return result;
	}
	
	public List<Map<String,Object>> getpermission(String userid){
		Map<String,Object> args = new HashMap<String,Object>();
		String sql = "select b.fid,b.fnumber,b.fparentid from TB_OA_userpermission a left join TB_OA_division b on b.fid=a.fdivisionid where a.fuserid=?";
		List<Map<String,Object>> permissionlist = jdbc.queryForList(sql,userid);
		
		
		sql = "select * from TB_OA_division where fnumber like :divisionnum||'.%' or fnumber=:divisionnum ";
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		for(Map<String,Object> permission:permissionlist) {
			List<Map<String,Object>> list = jdbc.queryForList(sql, permission.get("FNUMBER"), permission.get("FNUMBER"));
			List<Map<String, Object>> tree = easyDao.listtotree_ztree(list, permission.get("FPARENTID")==null?null:permission.get("FPARENTID").toString(), new String[] {"FNUMBER"});
			result.addAll(tree);
		}
		return result;
	}
	
	public void SaveReportArgs(Map<String,Object> args, HttpServletRequest request) {
		int i=0;
		List<String> columns = new ArrayList<String>();
		while(true) {
			String column = request.getParameter("columns["+i+"][data]");
			if(column!=null) {
				columns.add(column);
				i++;
			}else {
				break;
			}
		}
		args.put("columns", columns);
		request.getSession().setAttribute("report_args", args);
	}
	
	public byte[] Export(Map<String,Object> args,String[] header,String[] columns) {
		List<HashMap<String,Object>> list = easyDao.SelectListBySqlWithWhere(args);
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet();
		XSSFRow headrow = sheet.createRow(0);
		for(int i=0;i<header.length;i++) {
			headrow.createCell(i).setCellValue(header[i]);
		}
		for(int i=0;i<list.size();i++) {
			XSSFRow bodyrow = sheet.createRow(i+1);
			HashMap<String, Object> row = list.get(i);
			for(int j=0;j<header.length;j++) {
				bodyrow.createCell(j).setCellValue(row.get(columns[j])==null?"":row.get(columns[j]).toString());
			}
		}
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			workbook.write(bos);
			workbook.close();
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return bos.toByteArray();
	}

}
