package cn.tempus.contract.oto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

import cn.tempus.commons.FileService;
import cn.tempus.dao.EasyDao;
import cn.tempus.myworkflow.MyWorkFlowService;

@Service
public class ContractService2 {
	
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
		args.put("type1", request.getParameter("type1"));
		args.put("type2", request.getParameter("type2"));
		args.put("company", request.getParameter("company"));
		args.put("template", request.getParameter("template"));
		args.put("templatereason", request.getParameter("templatereason"));
		args.put("newcontract", request.getParameter("newcontract"));
		args.put("oldcontractchange", request.getParameter("oldcontractchange"));
		args.put("sidename", request.getParameter("sidename"));
		args.put("content", request.getParameter("content"));
		args.put("amount", request.getParameter("amount"));
		args.put("currency", request.getParameter("currency"));
		args.put("term", request.getParameter("term"));
		
		args.put("status", request.getParameter("status"));
		args.put("user", user.getString("userid"));
		args.put("time", new Date());
		
		args.put("#SQL", "select flex_value fnumber from fnd_flex_values_vl a inner join TB_OA_department b on b.fname=a.description and b.fid=#{company} where a.flex_value_set_id='1014867' and a.attribute4='OA'");
		Object companynum = basicService.GetFirstValueBySqlWithWhere(args);

		String number = new SimpleDateFormat("yyyyMMdd").format(new Date());
		number = basicService.GetFirstValueBySql("select '"+args.get("type2")+"-"+companynum+"-"+"'||(to_number("+number+"00"+")+(select count(*)+1 from TB_OA_contract where fnumber like '%"+(companynum+"-"+number)+"%')) from dual").toString();
		StringBuffer sb = new StringBuffer(number);
		number = sb.insert(number.length()-2, "-").toString();
		
		args.put("number", number);
		
		args.put("name", request.getParameter("name"));
		
		int result=0;
		
		args.put("#SQL", "select count(*) from TB_OA_CONTRACT where fid=#{id}");
		if(basicService.SelectCountBySqlWithWhere(args)==0){
			args.put("#SQL", "insert into TB_OA_CONTRACT (fid,fnumber,fname,ftype1,ftype2,fcompany,ftemplate,ftemplatereason,fnewcontract,foldcontractchange,fsidename,fcontent,famount,fcurrency,fterm,fstatus,fcreator,fcreatetime)"
					+ " values (#{id},#{number},#{name},#{type1},#{type2},#{company},#{template},#{templatereason},#{newcontract},#{oldcontractchange},#{sidename},#{content},#{amount},#{currency},#{term},#{status},#{user},#{time})");
			result = basicService.InsertData(args);
		}else{
			args.put("#SQL", "update TB_OA_CONTRACT set ftype2=#{type2},fcompany=#{company},ftemplate=#{template},ftemplatereason=#{templatereason},fnewcontract=#{newcontract},foldcontractchange=#{oldcontractchange},fsidename=#{sidename},fcontent=#{content},famount=#{amount},fcurrency=#{currency},fterm=#{term},fstatus=#{status} where fid=#{id}");
			result = basicService.UpdateData(args);
		}
		
		if(request.getParameter("status").equals("2")){
			Map<String,Object> vars = new HashMap<String,Object>();
			vars.put("contracttype", args.get("type2"));
			vars.put("currency", args.get("currency"));
			vars.put("amount", args.get("amount"));
			
			if(args.get("type1").equals("1")){
				String processinstanceid = myworkflowservice.SaveAndStart("WF999", id, user.get("userid").toString(),vars,args.get("name").toString(),"TB_OA_CONTRACT");
				args.put("#SQL", "update TB_OA_CONTRACT set fprocessinstanceid=#{processinstanceid} where fid=#{id}");
				args.put("processinstanceid", processinstanceid);
			}else if(args.get("type1").equals("2")){
				String processinstanceid = myworkflowservice.SaveAndStart("WF998", id, user.get("userid").toString(),vars,args.get("name").toString(),"TB_OA_CONTRACT");
				args.put("#SQL", "update TB_OA_CONTRACT set fprocessinstanceid=#{processinstanceid} where fid=#{id}");
				args.put("processinstanceid", processinstanceid);
			}
			
			basicService.UpdateData(args);
		}
		
		return result;
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean Delete(String id){
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "delete from TB_OA_CONTRACT where fid=#{id}");
		args.put("id", id);
		basicService.DeleteData(args);
		
		fileservice.DeleteFileByBid(id, null);
		
		return true;
	}

}
