package cn.tempus.api.payment;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ApiPaymentService {
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	NamedParameterJdbcTemplate namedJdbc;
	
	public Map<String, Object> save(){
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		String name = request.getParameter("name");
		String userId = request.getParameter("userId");
		String paymentCompany = request.getParameter("paymentCompany");
		String invoiceNums = request.getParameter("invoiceNums");
		//String contractId = request.getParameter("contractId");
		String paymentMethod = request.getParameter("paymentMethod");
		String paymentDate = request.getParameter("paymentDate");
		String smallAmount = request.getParameter("smallAmount");
		String bigAmount = request.getParameter("bigAmount");
		String currency = request.getParameter("currency");
		String supplierId=  request.getParameter("supplierId");
		String bankAccount = request.getParameter("bankAccount");
		String openBank = request.getParameter("openBank");
		String remark = request.getParameter("remark");
		
		if(StringUtils.isBlank(name)) {
			result.put("code", "error");
			result.put("msg", "字段name不能为空");
			return result;
		}
		if(StringUtils.isBlank(userId)) {
			result.put("code", "error");
			result.put("msg", "字段userId不能为空！");
			return result;
		}
		if(StringUtils.isBlank(paymentCompany)) {
			result.put("code", "error");
			result.put("msg", "字段paymentCompany不能为空！");
			return result;
		}
		if(StringUtils.isBlank(paymentMethod)) {
			result.put("code", "error");
			result.put("msg", "字段paymentMethod不能为空！");
			return result;
		}
		if(StringUtils.isBlank(paymentDate)) {
			result.put("code", "error");
			result.put("msg", "字段paymentDate不能为空！");
			return result;
		}
		if(StringUtils.isBlank(smallAmount)) {
			result.put("code", "error");
			result.put("msg", "字段smallAmount不能为空！");
			return result;
		}
		if(StringUtils.isBlank(bigAmount)) {
			result.put("code", "error");
			result.put("msg", "字段bigAmount不能为空！");
			return result;
		}
		if(StringUtils.isBlank(currency)) {
			result.put("code", "error");
			result.put("msg", "字段currency不能为空！");
			return result;
		}
		if(StringUtils.isBlank(supplierId)) {
			result.put("code", "error");
			result.put("msg", "字段supplierId不能为空！");
			return result;
		}
		if(StringUtils.isBlank(bankAccount)) {
			result.put("code", "error");
			result.put("msg", "字段bankAccount不能为空！");
			return result;
		}
		if(StringUtils.isBlank(openBank)) {
			result.put("code", "error");
			result.put("msg", "字段openBank不能为空！");
			return result;
		}
		if(StringUtils.isBlank(remark)) {
			result.put("code", "error");
			result.put("msg", "字段remark不能为空！");
			return result;
		}
		
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("id", UUID.randomUUID().toString());
		args.put("status", "1");
		
		args.put("name", name);
		args.put("user", userId);
		args.put("paymentcompany", paymentCompany);
		args.put("invoicenums", invoiceNums);
		args.put("paymentmethod", paymentMethod);
		args.put("paymentdate", paymentDate);
		args.put("smallamount", smallAmount);
		args.put("bigamount", bigAmount);
		args.put("currency", currency);
		args.put("supplierid", supplierId);
		args.put("bankaccount", bankAccount);
		args.put("openbank", openBank);
		args.put("remark", remark);
		
		
		String sql = "insert into TB_OA_PAYMENTREQUEST (fid,fname,fpaymentcompany,finvoicenums,fpaymentmethod,fpaymentdate,fsmallamount,fbigamount,fcurrency,fsupplierid,fbankaccount,fopenbank,fremark,fcreator,fcreatetime,fstatus) values ("
					+ ":id,:name,:paymentcompany,:invoicenums,:paymentmethod,to_date(:paymentdate,'yyyy-mm-dd'),:smallamount,:bigamount,:currency,:supplierid,:bankaccount,:openbank,:remark,:user,:sysdate,:status)";
		
		int r = namedJdbc.update(sql, args);
		if(r>0) {
			result.put("code", "success");
			result.put("msg", "ok");
		}else {
			result.put("code", "error");
			result.put("msg", "系统出错！");
		}
		
		return result;
	}
}	

