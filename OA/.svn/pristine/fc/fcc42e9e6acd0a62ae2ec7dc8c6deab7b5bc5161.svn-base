package cn.tempus.print;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tempus.dao.EasyDao;
import cn.tempus.myworkflow.MyWorkFlowService;
import cn.tempus.utils.LobUtils;
import cn.tempus.utils.DateDistance;

@Service
public class PrintService {
	
	@Autowired
    private EasyDao basicService;
	
	@Autowired
	MyWorkFlowService myworkflowservice;
	
	//单据审批历史
	public List<HashMap<String,Object>> getApprovalHistory(String processinstanceid){
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("#SQL", "select a.name_ taskname,b.user_name assignee,to_char(a.start_time_,'yyyy-mm-dd hh24:mi:ss') starttime,to_char(a.end_time_,'yyyy-mm-dd hh24:mi:ss') endtime,c.message_ fcomment,decode(d.text_,'1','同意','2','驳回','3','重新提交','4','放弃') foption from act_hi_taskinst a left join tb_user b on b.user_id=a.assignee_ left join act_hi_comment c on c.task_id_=a.id_ left join act_hi_varinst d on d.task_id_=a.id_ and d.name_='choose' where a.proc_inst_id_=#{processinstanceid} and a.assignee_ is not null and c.action_='AddComment' order by a.start_time_");
		args.put("processinstanceid", processinstanceid);
		List<HashMap<String,Object>> list = basicService.SelectListBySqlWithWhere(args);
		for(int i=0;i<list.size();i++){
			list.get(i).put("usetime", DateDistance.getDistanceTime(list.get(i).get("STARTTIME"), list.get(i).get("ENDTIME")));
		}
		args.put("#SQL", "select '开始' taskname,c.user_name assignee,to_char(b.start_time_,'yyyy-mm-dd hh24:mi:ss') starttime,'' endtime, null fcomment,'' foption from act_hi_procinst b left join tb_user c on c.user_id=b.start_user_id_ where b.proc_inst_id_=#{processinstanceid}");
		list.add(0, basicService.SelectListBySqlWithWhere(args).get(0));
		args.put("#SQL", "select '结束' taskname,'' assignee,'' starttime,to_char(b.end_time_,'yyyy-mm-dd hh24:mi:ss') endtime, null fcomment,'' foption from act_hi_procinst b left join tb_user c on c.user_id=b.start_user_id_ where b.proc_inst_id_=#{processinstanceid} and b.end_time_ is not null");
		list.addAll(basicService.SelectListBySqlWithWhere(args));
		return list;
	}
	
	//初始化出差申请单数据
	public Map<String,Object> InitialTravelData(String id){
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
		
		List<HashMap<String,Object>> approvalhistory = getApprovalHistory(travel.get("FPROCESSINSTANCEID").toString());
		result.put("approvalhistory", approvalhistory);
		
		return result;
	}
	
	//初始化合同审批单数据
	public Map<String,Object> InitialContractData(String id){
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "select a.*,b.user_name fcreatorname,c.fname fdepartmentname,d.fname fcompanyname,e.fname fcontracttype from TB_OA_contract a left join tb_user b on b.user_id=a.fcreator left join TB_OA_department c on c.fid=b.attribute15 left join TB_OA_department d on d.fid=a.fcompany left join TB_OA_contract_type e on e.fvalue=a.ftype2 where a.fid=#{id}");
		args.put("id", id);
		HashMap<String,Object> contract = basicService.GetSinglerData(args);
		Map<String,Object> returnObject = new HashMap<String,Object>();
		returnObject.put("contract", contract);
		
		List<HashMap<String,Object>> approvalhistory = getApprovalHistory(contract.get("FPROCESSINSTANCEID").toString());
		returnObject.put("approvalhistory", approvalhistory);
		
		return returnObject;
	}
	
	//初始化费用报销单数据
	public Map<String,Object> InitialReimbursementData(String id){
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "select a.fprocessinstanceid,a.ftype,a.ftotalamount,decode(a.fcurrency,'HKD','港币','CNY','人民币','USD','美元') fcurrency,to_char(a.fcreatetime,'yyyy-mm-dd') fcreatetime,b.user_name fcreatorname,c.fname fdepartmentname,d.fname fcompanyname from TB_OA_reimbursement a left join tb_user b on b.user_id=a.fcreator left join TB_OA_department c on c.fid=b.attribute15 left join TB_OA_department d on d.fid=a.fcompany  where a.fid=#{id}");
		args.put("id", id);
		HashMap<String,Object> reimbursement = basicService.GetSinglerData(args);
		Map<String,Object> returnObject = new HashMap<String,Object>();
		returnObject.put("reimbursement", reimbursement);
		
		args.put("#SQL", "select b.fname,a.fcontent,a.famount from TB_OA_reimbursement_entry a left join TB_OA_costcategory b on b.fid=a.fcostcategory where fparentid=#{id} order by fseq");
		returnObject.put("entry", basicService.SelectListBySqlWithWhere(args));
		
		args.put("#SQL", "select ftitle,famount,decode(fcurrency,'HKD','港币','CNY','人民币','USD','美元') fcurrency from TB_OA_reimbursement_relation where fparentid=#{id} order by fseq");
		returnObject.put("relation", basicService.SelectListBySqlWithWhere(args));
		
		List<HashMap<String,Object>> approvalhistory = getApprovalHistory(reimbursement.get("FPROCESSINSTANCEID").toString());
		returnObject.put("approvalhistory", approvalhistory);
		
		return returnObject;                                                                                                                       
	}
	
	//初始化业务招待费申请单数据
	public Map<String,Object> InitialBusinessHospitalityData(String id){
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("id", id);
		args.put("#SQL", "select a.*,b.user_name fcreatorname,c.fname fdepartmentname from TB_OA_businesshospitality a left join tb_user b on b.user_id=a.fcreator left join TB_OA_department c on c.fid=b.attribute15 where a.fid=#{id}");
		HashMap<String,Object> result = basicService.GetSinglerData(args);
		List<HashMap<String,Object>> approvalhistory = getApprovalHistory(result.get("FPROCESSINSTANCEID").toString());
		result.put("approvalhistory", approvalhistory);
		return result;
	}
	
	//初始化合同付款申请单数据
	public Map<String,Object> InitialPaymentData(String id){
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("id", id);
		args.put("#SQL", "select a.*,b.fid fcontractid,b.fnumber fcontractnum,b.fsidename,b.fprocessinstanceid fcontractprocessinstanceid,c.vendor_id,c.vendor_name,d.user_name fcreatorname,e.fname fdepartmentname,f.invoice_num,g.fname fpaymentcompanyname from TB_OA_paymentrequest a left join TB_OA_contract b on b.fid=a.fcontractid left join Ap_Suppliers c on c.vendor_id=a.FSUPPLIERID left join tb_user d on d.user_id=a.fcreator left join TB_OA_department e on e.fid=d.attribute15 left join Ap_Invoices_All f on f.invoice_id=a.fapinvoiceid left join TB_OA_department g on g.fid=a.fpaymentcompany where a.fid=#{id}");
		HashMap<String,Object> result = basicService.GetSinglerData(args);
		List<HashMap<String,Object>> approvalhistory = getApprovalHistory(result.get("FPROCESSINSTANCEID").toString());
		result.put("approvalhistory", approvalhistory);
		
		return result;
	}

}
