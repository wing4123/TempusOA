package cn.tempus.reimbursement;

import java.sql.SQLType;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.activiti.engine.ProcessEngine;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.tempus.commons.FileService;
import cn.tempus.dao.EasyDao;
import cn.tempus.myworkflow.MyWorkFlowService;

@Service
public class ReimbursementService {
	
	@Autowired
    private EasyDao basicService;
	
	@Autowired
	MyWorkFlowService myworkflowservice;
	
	@Autowired
	FileService fileservice;
	
	@Autowired
	ProcessEngine processengine;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	JdbcTemplate jdbc;
	
	@Autowired
	DataSource dataSource;
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.REQUIRED)
	public int Save(){
		Map<String,Object> user = (Map<String,Object>)request.getSession().getAttribute("USER");
		String id = request.getParameter("id");
		
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("id", id);
		args.put("name", request.getParameter("name"));
		args.put("type", request.getParameter("type"));
		args.put("company", request.getParameter("company"));
		args.put("currency", request.getParameter("currency"));
		args.put("payee", request.getParameter("payee"));
		args.put("totalamount", request.getParameter("total"));
		
		args.put("status", StringUtils.isBlank(request.getParameter("status"))?"2":request.getParameter("status"));
		args.put("user", user.get("userid"));
		args.put("time", new Date());
		
		int result=0;
		
		args.put("#SQL", "select count(*) from TB_OA_reimbursement where fid=#{id}");
		if(basicService.SelectCountBySqlWithWhere(args)==0){
			args.put("#SQL", "insert into TB_OA_reimbursement (fid,fname,fcompany,fcurrency,fpayee,ftotalamount,fcreator,fcreatetime,fstatus,ftype) values (#{id},#{name},#{company},#{currency},#{payee},#{totalamount},#{user},#{time},#{status},#{type})");
			result = basicService.InsertData(args);
		}else{
			args.put("#SQL", "update TB_OA_reimbursement set fname=#{name},fcompany=#{company},fcurrency=#{currency},fpayee=#{payee},ftotalamount=#{totalamount},fstatus=#{status},ftype=#{type} where fid=#{id}");
			result = basicService.UpdateData(args);
		}
		
		StringBuffer entrysql = new StringBuffer("insert into TB_OA_reimbursement_entry (fid,fparentid,fcostcategory,fcontent,famount,fseq,ftype)");
		String[] costcategorys = request.getParameterValues("costcategory[]")==null?request.getParameterValues("costcategory"):request.getParameterValues("costcategory[]");
		String[] contents = request.getParameterValues("content[]")==null?request.getParameterValues("content"):request.getParameterValues("content[]");
		String[] amounts = request.getParameterValues("amount[]")==null?request.getParameterValues("amount"):request.getParameterValues("amount[]");
		for(int i=0;i<costcategorys.length;i++){
			String entryid = UUID.randomUUID().toString();
			entrysql.append(" select #{entryid"+i+"} fid,#{id} fparentid,#{costcategory"+i+"} fcostcategory,#{content"+i+"} fcontent,#{amount"+i+"} famount,#{seq"+i+"} fseq,1 ftype from dual union");
			args.put("seq"+i, i+1);
			args.put("entryid"+i, entryid);
			args.put("costcategory"+i, costcategorys[i]);
			args.put("content"+i, contents[i]);
			args.put("amount"+i, amounts[i]);
		}
		
		args.put("#SQL", "delete from TB_OA_reimbursement_entry where fparentid=#{id} and ftype=1");
		basicService.DeleteData(args);
		args.put("#SQL", entrysql.substring(0, entrysql.length()-6));
		basicService.InsertData(args);
		
		
		StringBuffer relationsql = new StringBuffer("insert into TB_OA_reimbursement_relation (fparentid,fprocessinstanceid,ftitle,famount,fcurrency,fseq)");
		String[] relationname = request.getParameterValues("relationname[]")==null?request.getParameterValues("relationname"):request.getParameterValues("relationname[]");
		String[] relationprocessid = request.getParameterValues("relationprocessid[]")==null?request.getParameterValues("relationprocessid"):request.getParameterValues("relationprocessid[]");
		String[] relationamount = request.getParameterValues("relationamount[]")==null?request.getParameterValues("relationamount"):request.getParameterValues("relationamount[]");
		String[] relationcurrency = request.getParameterValues("relationcurrency[]")==null?request.getParameterValues("relationcurrency"):request.getParameterValues("relationcurrency[]");

		for(int i=0;relationprocessid!=null && i<relationprocessid.length && !relationprocessid[i].equals("");i++){
			relationsql.append(" select #{id} fparentid,#{relationprocessid"+i+"} fprocessinstanceid,#{relationname"+i+"} ftitle,#{relationamount"+i+"} famount,#{relationcurrency"+i+"} fcurrency,#{seq"+i+"} fseq from dual union");
			args.put("seq"+i, i+1);
			args.put("relationprocessid"+i, relationprocessid[i]);
			args.put("relationname"+i, relationname[i]);
			args.put("relationamount"+i, relationamount[i]);
			args.put("relationcurrency"+i, relationcurrency[i]);
		}
		
		args.put("#SQL", "delete from TB_OA_reimbursement_relation where fparentid=#{id}");
		basicService.DeleteData(args);
		if(args.get("type").equals("1") && relationprocessid!=null){
			args.put("#SQL", relationsql.substring(0, relationsql.length()-6));
			basicService.InsertData(args);
		}
		
		if(request.getParameter("status").equals("2")){
			Map<String,Object> processvars = new HashMap<String,Object>();
			processvars.put("form_type", Integer.parseInt(args.get("type").toString()));
			processvars.put("form_totalamount", args.get("totalamount"));
			processvars.put("form_currency", args.get("currency"));
			
			args.put("#SQL", "select a.fname from TB_OA_division a left join tb_user b on b.attribute13=a.fid where b.user_id=#{user}");
			
			processvars.put("finance", basicService.GetFirstValueBySqlWithWhere(args).toString().equals("财务部"));
			
			String processinstanceid = myworkflowservice.SaveAndStart("WF001", id, user.get("userid").toString(),processvars,args.get("name").toString(),"TB_OA_reimbursement");
			args.put("#SQL", "update TB_OA_reimbursement set fprocessinstanceid=#{processinstanceid} where fid=#{id}");
			args.put("processinstanceid", processinstanceid);
			basicService.UpdateData(args);
		}
		
		return result;
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean Delete(String id){
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "delete from TB_OA_reimbursement where fid=#{id}");
		args.put("id", id);
		basicService.DeleteData(args);
		
		args.put("#SQL", "delete from TB_OA_reimbursement_entry where fparentid=#{id}");
		basicService.DeleteData(args);
		
		args.put("#SQL", "delete from TB_OA_reimbursement_relation where fparentid=#{id}");
		basicService.DeleteData(args);
		
		fileservice.DeleteFileByBid(id, null);
		
		return true;
	}
	
	public List<HashMap<String,Object>> getDepartmentList(String companyid){
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "select flex_value fid,description fname from fnd_flex_values_vl where flex_value like #{companyid} AND flex_value_set_id='1014868' order by flex_value");
		args.put("companyid", companyid+"____");
		List<HashMap<String,Object>> result = basicService.SelectListBySqlWithWhere(args);
		
		return result;
	}
	
	public List<HashMap<String,Object>> getProjectList(String companyid){
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "select flex_value fid,description fname from fnd_flex_values_vl where flex_value_set_id='1014872' and (flex_value like #{companyid} or flex_value like 'AAAAA%' or flex_value like 'BBBBB%' or flex_value like 'CCCCC%' or flex_value like 'DDDDD%' or flex_value like 'EEEEE%') order by flex_value");
		args.put("companyid", companyid+"____");
		List<HashMap<String,Object>> result = basicService.SelectListBySqlWithWhere(args);
		
		return result;
	}
	
	public List<HashMap<String,Object>> getCostCategoryList(String companyid){
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "select flex_value fid,description fname from fnd_flex_values_vl where flex_value_set_id='1014872' and (flex_value like #{companyid} or flex_value='2221100301') order by flex_value");
		args.put("companyid", companyid+"____");
		List<HashMap<String,Object>> result = basicService.SelectListBySqlWithWhere(args);
		//returnObject.put("costcategory2", basicService.SelectListBySql("select flex_value costvalue,description costname from fnd_flex_values_vl where flex_value_set_id='1014869' and (flex_value like '6601______' or flex_value like '6602______')"));
		
		return result;
	}
	
	//财务录入提交
	public int Save2(){
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "update TB_OA_reimbursement set fcostcompany=#{costcompany},finvoicedate=to_date(#{invoicedate},'yyyy-mm-dd') where fid=#{id}");
		args.put("id", request.getParameter("id"));
		args.put("costcompany", request.getParameter("costcompany"));
		args.put("invoicedate", request.getParameter("invoicedate"));
		basicService.UpdateData(args);
		
		int result=0;
		
		StringBuffer entrysql = new StringBuffer("insert into TB_OA_reimbursement_entry (fid,fparentid,fcostcategory,fcontent,famount,fseq,ftype,fcostcenter,fproject) ");
		String[] costcenters = request.getParameterValues("costcenter[]")==null?new String[] {request.getParameter("costcenter")}:request.getParameterValues("costcenter[]");
		String[] costcategorys = request.getParameterValues("costcategory[]")==null?new String[] {request.getParameter("costcategory")}:request.getParameterValues("costcategory[]");
		String[] projects = request.getParameterValues("project[]")==null?new String[] {request.getParameter("project")}:request.getParameterValues("project[]");
		String[] contents = request.getParameterValues("content[]")==null?new String[] {request.getParameter("content")}:request.getParameterValues("content[]");
		String[] amounts = request.getParameterValues("amount[]")==null?new String[] {request.getParameter("amount")}:request.getParameterValues("amount[]");
		for(int i=0;i<costcategorys.length;i++){
			String entryid = UUID.randomUUID().toString();
			entrysql.append(" select #{entryid"+i+"} fid,#{id} fparentid,#{costcategory"+i+"} fcostcategory,#{content"+i+"} fcontent,#{amount"+i+"} famount,#{seq"+i+"} fseq,2 ftype,#{costcenter"+i+"} fcostcenter,#{project"+i+"} fproject from dual union");
			args.put("seq"+i, i+1);
			args.put("entryid"+i, entryid);
			args.put("costcenter"+i, costcenters[i]);
			args.put("costcategory"+i, costcategorys[i]);
			args.put("project"+i, projects[i]);
			args.put("content"+i, contents[i]);
			args.put("amount"+i, amounts[i]);	
		}
		
		args.put("#SQL", "delete from TB_OA_reimbursement_entry where fparentid=#{id} and ftype=2");
		basicService.DeleteData(args);
		args.put("#SQL", entrysql.substring(0, entrysql.length()-6));
		result = basicService.InsertData(args);
		
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbc);
		SqlParameterSource param = new MapSqlParameterSource().addValue("P_OA_BILL_ID", args.get("id"));
		Map<String, Object> resultMap = jdbcCall.withCatalogName("TB_OA_INVOICE_IMPORT_PKG").withProcedureName("IMPORT_OA_INVOICE").execute(param);
		
		Object rs_code = resultMap.get("RS_CODE");
		
		if(rs_code.equals("S")){
			Object invoiceid = resultMap.get("P_INVOICE_ID");
			args.put("#SQL", "select fprocessinstanceid from TB_OA_reimbursement where fid=#{id}");
			processengine.getRuntimeService().setVariable(basicService.GetFirstValueBySqlWithWhere(args).toString(), "invoiceid", invoiceid);
			result = 1;
		}else{
			result = 0;
		}
		
//		try {
//			Connection conn = dataSource.getConnection();
//			CallableStatement proc = null;
//			proc = conn.prepareCall("{ call TB_OA_INVOICE_IMPORT_PKG.IMPORT_OA_INVOICE(?,?,?,?) }");
//			proc.setString(1, args.get("id").toString());
//			proc.registerOutParameter(2, Types.VARCHAR);
//			proc.registerOutParameter(3, Types.VARCHAR);
//			proc.registerOutParameter(4, Types.VARCHAR);
//			proc.execute();
//			String rs_code = proc.getString(2);
//			
//			if(rs_code.equals("S")){
//				String invoiceid = proc.getString(4);
//				args.put("#SQL", "select fprocessinstanceid from TB_OA_reimbursement where fid=#{id}");
//				processengine.getRuntimeService().setVariable(basicService.GetFirstValueBySqlWithWhere(args).toString(), "invoiceid", invoiceid);
//				result = 1;
//			}else{
//				result = 0;
//			}
//			
//		} catch (SQLException e) {
//			result = 0;
//			e.printStackTrace();
//		}
			
		return result;
	}
	
	//出纳付款
	public Map<String,Object> Save3(String id, String paymentaccount, String paymentdate){
		String sql = "update TB_OA_reimbursement set fpaymentaccount=?,fpaymentdate=to_date(?,'yyyy-mm-dd') where fid=?";
		jdbc.update(sql, paymentaccount, paymentdate, id);
		
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbc);
		SqlParameterSource param = new MapSqlParameterSource().addValue("P_BILL_ID", id);
		Map<String, Object> result = jdbcCall.withCatalogName("TB_OA_PAYMENT_IMPORT_PKG").withProcedureName("PAYMENT_REIMBURSEMENT").execute(param);
		
//		Map<String,Object> result = new HashMap<String,Object>();
//		try {
//			Connection conn = dataSource.getConnection();
//			CallableStatement proc = null;
//			proc = conn.prepareCall("{ call TB_OA_PAYMENT_IMPORT_PKG.PAYMENT_REIMBURSEMENT(?,?,?) }");
//			proc.setString(1, id);
//			proc.registerOutParameter(2, Types.VARCHAR);
//			proc.registerOutParameter(3, Types.VARCHAR);
//			proc.execute();
//			String rs_code = proc.getString(2);
//			String rs_msg = proc.getString(3);
//			
//			result.put("code", rs_code);
//			result.put("msg", rs_msg);
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		
		return result;
	}

}
