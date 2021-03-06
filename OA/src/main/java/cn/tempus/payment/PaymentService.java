package cn.tempus.payment;

import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.tempus.commons.FileService;
import cn.tempus.dao.EasyDao;
import cn.tempus.myworkflow.MyWorkFlowService;
import com.alibaba.fastjson.JSONObject;

@Service
public class PaymentService {
	
	@Autowired
    private EasyDao basicService;
	
	@Autowired
	MyWorkFlowService myworkflowservice;
	
	@Autowired
	FileService fileservice;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	JdbcTemplate jdbc;
	
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
		
		args.put("paymentcompany", request.getParameter("paymentcompany"));
		args.put("invoicenums", request.getParameter("invoicenums"));
		args.put("contractid", request.getParameter("contractid"));
		args.put("paymentmethod", request.getParameter("paymentmethod"));
		args.put("paymentdate", request.getParameter("paymentdate"));
		args.put("smallamount", request.getParameter("smallamount"));
		args.put("bigamount", request.getParameter("bigamount"));
		args.put("currency", request.getParameter("currency"));
		args.put("supplierid", request.getParameter("supplierid"));
		args.put("bankaccount", request.getParameter("bankaccount"));
		args.put("openbank", request.getParameter("openbank"));
		args.put("remark", request.getParameter("remark"));
		
		int result=0;
		
		args.put("#SQL", "select count(*) from TB_OA_PAYMENTREQUEST where fid=#{id}");//to_date(#{begindate},'yyyy-mm-dd')
		if(basicService.SelectCountBySqlWithWhere(args)==0){
			args.put("#SQL", "insert into TB_OA_PAYMENTREQUEST (fid,fname,fpaymentcompany,finvoicenums,fcontractid,fpaymentmethod,fpaymentdate,fsmallamount,fbigamount,fcurrency,fsupplierid,fbankaccount,fopenbank,fremark,fcreator,fcreatetime,fstatus) values ("
					+ "#{id},#{name},#{paymentcompany},#{invoicenums},#{contractid},#{paymentmethod},to_date(#{paymentdate},'yyyy-mm-dd'),#{smallamount},#{bigamount},#{currency},#{supplierid},#{bankaccount},#{openbank},#{remark},#{user},#{time},#{status})");
			result = basicService.InsertData(args);
		}else{
			args.put("#SQL", "update TB_OA_PAYMENTREQUEST set fname=#{name},fpaymentcompany=#{paymentcompany},finvoicenums=#{invoicenums},fcontractid=#{contractid},fpaymentmethod=#{paymentmethod},fpaymentdate=to_date(#{paymentdate},'yyyy-mm-dd'),fsmallamount=#{smallamount},fbigamount=#{bigamount},fcurrency=#{currency},fsupplierid=#{supplierid},fbankaccount=#{bankaccount},fopenbank=#{openbank},fremark=#{remark},fcreatetime=#{time},fstatus=#{status} where fid=#{id}");
			result = basicService.UpdateData(args);
		}
		
		if(request.getParameter("status").equals("2")){
			Map<String,Object> vars = new HashMap<String,Object>();
			vars.put("form_amount", args.get("smallamount"));
			vars.put("form_currency", args.get("currency"));
			vars.put("form_paymentmethod", args.get("paymentmethod"));
			
			String processinstanceid = myworkflowservice.SaveAndStart("WF006", id, user.get("userid").toString(),vars,args.get("name").toString(),"TB_OA_paymentrequest");
			args.put("#SQL", "update TB_OA_PAYMENTREQUEST set fprocessinstanceid=#{processinstanceid} where fid=#{id}");
			args.put("processinstanceid", processinstanceid);
			basicService.UpdateData(args);
		}
		
		return result;
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean Delete(String id){
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "delete from TB_OA_paymentrequest where fid=#{id}");
		args.put("id", id);
		basicService.DeleteData(args);
		
		fileservice.DeleteFileByBid(id, null);
		
		return true;
	}
	
	public Map<String,Object> InitialEditData(String id){
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("id", id);
		args.put("#SQL", "select a.*,b.fid fcontractid,b.fnumber fcontractnum,b.fsidename,b.fprocessinstanceid fcontractprocessinstanceid,c.vendor_id,c.vendor_name,d.user_name fcreatorname,e.fname fdepartmentname,f.invoice_num from TB_OA_paymentrequest a left join TB_OA_contract b on b.fid=a.fcontractid left join Ap_Suppliers c on c.vendor_id=a.FSUPPLIERID left join tb_user d on d.user_id=a.fcreator left join TB_OA_department e on e.fid=d.attribute15 left join Ap_Invoices_All f on f.invoice_id=a.fapinvoiceid where a.fid=#{id}");
		
		return basicService.GetSinglerData(args);
	}
	
	//财务录入提交
	@Transactional(propagation=Propagation.REQUIRED)
	public Map<String,Object> Accounting(){
		String billid=request.getParameter("id");
		String invoicedescription = request.getParameter("invoicedescription");
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "update TB_OA_paymentrequest set finvoicedescription=#{invoicedescription} where fid=#{id}");
		args.put("invoicedescription", invoicedescription);
		args.put("id", billid);
		basicService.UpdateData(args);
		
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbc);
		SqlParameterSource param = new MapSqlParameterSource().addValue("P_OA_BILL_ID", billid);
		Map<String, Object> result = jdbcCall.withCatalogName("TB_OA_INVOICE_IMPORT_PKG").withProcedureName("IMPORT_OA_INVOICE_PAYMENT").execute(param);
		
		result.put("code", result.get("RS_CODE"));
		result.put("msg", result.get("RS_MSG"));
		result.put("invoiceid", result.get("P_INVOICE_ID"));
		
//		try {
//			Connection conn = DataSource.getConnection();
//			CallableStatement proc = null;
//			proc = conn.prepareCall("{ call TB_OA_INVOICE_IMPORT_PKG.IMPORT_OA_INVOICE_PAYMENT(?,?,?,?) }");
//			proc.setString(1, billid);
//			proc.registerOutParameter(2, Types.VARCHAR);
//			proc.registerOutParameter(3, Types.VARCHAR);
//			proc.registerOutParameter(4, Types.VARCHAR);
//			proc.execute();
//			String rs_code = proc.getString(2);
//			result.put("code", rs_code);
//			result.put("msg", proc.getString(3));
//			
//			if(rs_code.equals("S")){
//				String invoiceid = proc.getString(4);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
			
		return result;
	}
	
	//出纳付款
	@Transactional(propagation=Propagation.REQUIRED)
	public Map<String,Object> Teller(){
		String billid=request.getParameter("id");
		String paymentaccount = request.getParameter("paymentaccount");
		String appaymentdate = request.getParameter("appaymentdate");
		String sql = "update TB_OA_paymentrequest set FPAYMENTACCOUNT=?,FAPPAYMENTDATE=to_date(?,'yyyy-mm-dd') where fid=?";
		jdbc.update(sql, paymentaccount,appaymentdate,billid);
		
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbc);
		SqlParameterSource param = new MapSqlParameterSource().addValue("P_BILL_ID", billid);
		Map<String, Object> result = jdbcCall.withCatalogName("TB_OA_PAYMENT_IMPORT_PKG").withProcedureName("PAYMENT_CONTRACT").execute(param);
		
		result.put("code", result.get("X_SUCC_FLAG"));
		result.put("msg", result.get("X_ERROR_MSG"));
		
//		try {
//			Connection conn = DataSource.getConnection();
//			CallableStatement proc = null;
//			proc = conn.prepareCall("{ call TB_OA_PAYMENT_IMPORT_PKG.PAYMENT_CONTRACT(?,?,?) }");
//			proc.setString(1, billid);
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
