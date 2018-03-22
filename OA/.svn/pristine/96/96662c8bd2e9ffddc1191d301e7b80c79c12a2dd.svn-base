package cn.tempus.myworkflow;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLStreamException;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.repository.Model;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.socket.TextMessage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import cn.tempus.commons.BaseService;
import cn.tempus.commons.FileService;
import cn.tempus.websocket.MyWebSocketHandler;
import com.alibaba.fastjson.JSONObject;

/** 
* @author 吴中贤 wing4123@163.com
* @date 2017年7月4日
* @Description: 工作流
*  
*/
@Controller
@RequestMapping("/MyWorkFlow")
public class MyWorkFlowController{
	
	@Autowired
	MyWorkFlowService myworkflowservice;
	
	@Autowired
	FileService fileservice;
	
	@Autowired
	MyWebSocketHandler handler;
	
	@Autowired
	private ProcessEngine processEngine;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private IdentityService identityService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	BaseService baseService;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	HttpServletResponse response;
	
	@Autowired
	StringRedisTemplate template;
	
	//发起工作流
	@RequestMapping("/Initiate")
	public String MenuManage(){
		baseService.initmenu( "/MyWorkFlow/Initiate");
		return "myworkflow/initiate";
	}
	
	//获取模型
	@RequestMapping(value="/getMyModelList")
	@ResponseBody
	public Map<String,Object> getMyModelList() throws UnsupportedEncodingException{
		Map<String,Object> args = new HashMap<String,Object>();
		StringBuffer sql = new StringBuffer("select distinct a.id_ fmodelid,a.name_ fname,a.key_ fkey,a.fcreateurl,a.category_ fcategory,a.deployment_id_ fdeploymentid,a.fenable,b.id_ fprocessDefinitionId from act_re_model a left join act_re_procdef b on b.deployment_id_=a.deployment_id_ inner join tb_oa_roleprocess c on c.fprocessid=a.id_ where 1=1");
		String category =request.getParameter("category");
		if(!category.equals("")){
			sql.append(" and a.category_=:category");
			args.put("category", category);
		}
		String search = request.getParameter("search[value]");
		if(!search.equals("")){
			sql.append(" and (a.name_ like :search or a.key_ like :search)");
			args.put("search", "%"+search+"%");
		}
		
		Map<String,Object> result = baseService.getDateTableRecord(sql, args);
		
		return result;
	}
	
	//流程管理页面
	@RequestMapping(value="/ProcessManage")
	public String ProcessManage(){
		baseService.initmenu( "/MyWorkFlow/ProcessManage");
		List<HashMap<String,Object>> category = baseService.easyDao.SelectListBySql("select FID,FNAME from TB_OA_wfgroup where fparentid is null");
		request.setAttribute("category", category);
		
		return "myworkflow/ProcessManage";
	}
	
	//获取模型
	@RequestMapping(value="/getModelList")
	@ResponseBody
	public Map<String,Object> getModelList() throws UnsupportedEncodingException{
		Map<String,Object> args = new HashMap<String,Object>();
		//StringBuffer sql = new StringBuffer("select a.id_ fmodelid,a.name_ fname,a.key_ fkey,b.fcreateurl,c.fname fcategory from act_re_model a left join TB_OA_wfgroup b on b.fwfid=a.id_ left join TB_OA_wfgroup c on c.fid=b.fparentid where 1=1 ");
		
		StringBuffer sql = new StringBuffer("select a.id_ fmodelid,a.name_ fname,a.key_ fkey,a.fcreateurl,a.category_ fcategory,a.deployment_id_ fdeploymentid,a.fenable,b.id_ fprocessDefinitionId from act_re_model a left join act_re_procdef b on b.deployment_id_=a.deployment_id_ where 1=1");
		String category =request.getParameter("category");
		if(!category.equals("")){
			sql.append(" and a.category_=#{category}");
			args.put("category", category);
		}
		String search = request.getParameter("search[value]");
		if(!search.equals("")){
			sql.append(" and (a.name_ like #{search} or a.key_ like #{search})");
			args.put("search", "%"+search+"%");
		}
		String enable = request.getParameter("enable");
		if(StringUtils.isNotEmpty(enable)){
			sql.append(" and a.fenable=#{enable}");
			args.put("enable", enable);
		}
		String deployed = request.getParameter("deployed");
		if(StringUtils.isNotEmpty(deployed)){
			sql.append(" and a.deployment_id_ is not null");
		}
		
		args.put("#SQL", sql);
		Map<String,Object> result = baseService.getDateTableRecord(args);
		
		return result;
	}
	
	//获取模型属性
	@RequestMapping(value="/getModelSetting")
	@ResponseBody
	public Map<String,Object> getModelSetting() throws UnsupportedEncodingException{
		String id = request.getParameter("modelid");
		String sql = "select id_ fmodelid,name_ fname,key_ fkey,fcreateurl,category_ fcategory,deployment_id_ fdeploymentid,fenable from act_re_model where id_=?";
		Map<String,Object> result = baseService.jdbc.queryForMap(sql, id);
		return result;
	}
	
	//保存模型属性
	@RequestMapping(value="/saveModelSetting")
	@ResponseBody
	public int saveModelSetting() throws UnsupportedEncodingException{
		String id = request.getParameter("id");
		String createurl = request.getParameter("createurl");
		String enable = request.getParameter("enable");
		String category = request.getParameter("category");
		String sql = "update act_re_model set fcreateurl=?,fenable=?,category_=? where id_=?";
		int result = baseService.jdbc.update(sql, createurl,enable,category,id);
		return result;
	}
	
	//删除模型
	@RequestMapping("/DeleteModel")
	@ResponseBody
	public boolean DeleteMenu(@RequestParam String id){
		boolean b = myworkflowservice.DeleteModel(id);
		return b;
	}
	
	//部署流程
	@RequestMapping("/Deploy")
	@ResponseBody
	public boolean PublishProcess(@RequestParam String id) throws JsonProcessingException, IOException{
		Model modelData = processEngine.getRepositoryService().getModel(id);  
        ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));  
        BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
        byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);
        String processName = modelData.getName() + ".bpmn20.xml";
        repositoryService.createDeployment().name(modelData.getName()).addString(processName, new String(bpmnBytes,"UTF-8")).deploy();
        return true;
	}
	
	//添加模型
	@RequestMapping("/AddModel")
	public void AddModel() throws IOException{
		String modelid = myworkflowservice.AddModel();
		response.sendRedirect(request.getContextPath() + "/modeler/modeler3.jsp?modelId=" + modelid);
	}
	
	//导出模型
	@RequestMapping("/ExportModel")
	public ResponseEntity<byte[]> ExportModel() throws IOException{
		String id = request.getParameter("id");
		return myworkflowservice.ExportModel(id);
	}
	
	//导入模型
	@RequestMapping("/ImportModel")
	@ResponseBody
    public void ImportModel(@RequestParam("file") CommonsMultipartFile[] files, HttpServletResponse response) throws IOException, XMLStreamException {
    	myworkflowservice.ImportModel(files,request);
    }
	
	//我的待办任务页面
	@RequestMapping("/MyToBeDo")
	public String MyToBeDo(){
		baseService.initmenu( "/MyWorkFlow/MyToBeDo");
		return "myworkflow/mytobedo";
	}
	
	//获取我的待办任务
	@RequestMapping("/getMyToBeDo")
	@ResponseBody
	public Map<String,Object> getMyToBeDo(){
		String search = request.getParameter("search[value]");
		Map<String,Object> args = new HashMap<String,Object>();
		StringBuffer sql = new StringBuffer("select distinct a.id_ ftaskid,a.name_ ftaskname,c.name_ fprocessinstanceName,d.user_name fstartuser,c.start_time_ fstarttime,a.create_time_ fcreatetime,b.name_ fbillname,c.business_key_ fformid,a.PROC_DEF_ID_ fprocessdefinitionid,a.PROC_INST_ID_ fprocessinstanceid from act_ru_task a left join act_re_procdef b on b.id_=a.proc_def_id_ left join act_hi_procinst c on c.id_=a.proc_inst_id_ left join tb_user d on d.user_id=c.start_user_id_ left join act_ru_identitylink e on e.task_id_=a.id_ where (e.user_id_=#{user} or a.assignee_=#{user}) ");
		args.put("user", ((JSONObject) JSONObject.toJSON(request.getSession().getAttribute("USER"))).getString("userid"));
		if(!search.equals("")){
			sql.append(" and ( a.id_ like #{search} or a.name_ like #{search} or d.user_name like #{search} or b.name_ like #{search} or a.PROC_INST_ID_ like #{search})");
			args.put("search", "%"+search+"%");
		}
		args.put("#SQL", sql);
		
		return baseService.getDateTableRecord(args);
	}
	
	//我的历史任务页面
	@RequestMapping("/MyHistoryTask")
	public String MyHistoryTask(){
		baseService.initmenu( "/MyWorkFlow/MyHistoryTask");
		return "myworkflow/myhistorytask";
	}
	
	//获取我的历史任务
	@RequestMapping("/getMyHistoryTask")
	@ResponseBody
	public Map<String,Object> getMyHistoryTask(){
		String search = request.getParameter("search[value]");
		Map<String,Object> args = new HashMap<String,Object>();
		StringBuffer sql = new StringBuffer("select distinct a.id_ ftaskid,a.name_ ftaskname,d.user_name fstartuser,c.start_time_ fstarttime,a.start_time_ fcreatetime,a.end_time_ ftaskendtime,b.name_ fbillname,c.business_key_ fformid,a.PROC_DEF_ID_ fprocessdefinitionid,a.PROC_INST_ID_ fprocessinstanceid from act_hi_taskinst a left join act_re_procdef b on b.id_=a.proc_def_id_ left join act_hi_procinst c on c.id_=a.proc_inst_id_ left join tb_user d on d.user_id=c.start_user_id_ left join act_ru_identitylink e on e.task_id_=a.id_ where  a.assignee_=#{user} and a.end_time_ is not null and c.delete_reason_ is null ");
		args.put("user", ((JSONObject) JSONObject.toJSON(request.getSession().getAttribute("USER"))).getString("userid"));
		if(!search.equals("")){
			sql.append(" and ( a.id_ like #{search} or a.name_ like #{search} or d.user_name like #{search} or b.name_ like #{search} or a.PROC_INST_ID_ like #{search})");
			args.put("search", "%"+search+"%");
		}
		
		args.put("#SQL", sql);
		
		return baseService.getDateTableRecord(args);
	}
	
	//我发起的流程页面
	@RequestMapping("/MyProcess")
	public String MyProcess(){
		List<HashMap<String, Object>> allProcess = myworkflowservice.getAllProcess();
		request.setAttribute("allProcess", allProcess);
		
		baseService.initmenu( "/MyWorkFlow/MyProcess");
		return "myworkflow/myprocess";
	}
	
	//获取我发起的流程
	@RequestMapping("/getMyProcess")
	@ResponseBody
	public Map<String,Object> getMyProcess(){
		String search = request.getParameter("search[value]");
		String isend = request.getParameter("isend");
		String processType = request.getParameter("processType");
		Map<String,Object> args = new HashMap<String,Object>();
		StringBuffer sql = new StringBuffer("select a.id_ fprocessinstanceid,a.name_ proc_inst_name,a.start_time_ fstarttime,a.end_time_ fendtime,decode(a.end_time_,null,0,1) fisend,b.id_ fproc_def_id from act_hi_procinst a left join act_re_procdef b on b.id_=a.proc_def_id_ where a.start_user_id_=#{user} and a.delete_reason_ is null");
		args.put("user", ((JSONObject) JSONObject.toJSON(request.getSession().getAttribute("USER"))).getString("userid"));

		if(!search.equals("")){
			sql.append(" and ( a.id_ like #{search} or b.name_ like #{search})");
			args.put("search", "%"+search+"%");
		}
		
		if(isend.equals("0")){
			sql.append(" and a.end_time_ is null");
		}else if(isend.equals("1")){
			sql.append(" and a.end_time_ is not null");
		}
		
		if(processType!="") {
			sql.append(" and b.key_=#{processType}");
			args.put("processType", processType);
		}
		args.put("#SQL", sql);
		
		return baseService.getDateTableRecord(args);
	}
	
	//流程监控页面
	@RequestMapping("/ProcessMonitoring")
	public String ProcessMonitoring(){
		baseService.initmenu( "/MyWorkFlow/ProcessMonitoring");
		return "myworkflow/ProcessMonitoring";
	}
	
	//所有的待办任务
	@RequestMapping("/getAllRuntimeTask")
	@ResponseBody
	public Map<String,Object> getAllRuntimeTask(){
		String search = request.getParameter("search[value]");
		Map<String,Object> args = new HashMap<String,Object>();
		StringBuffer sql = new StringBuffer("select a.id_ ftaskid,a.name_ ftaskname,d.user_name fstartuser,c.start_time_ fstarttime,a.create_time_ fcreatetime,b.name_ fbillname,a.PROC_DEF_ID_ processdefinitionid,a.PROC_INST_ID_ processinstanceid from act_ru_task a left join act_re_procdef b on b.id_=a.proc_def_id_ left join act_hi_procinst c on c.id_=a.proc_inst_id_ left join tb_user d on d.user_id=c.start_user_id_");
		if(!search.equals("")){
			sql.append(" where a.id_ like #{search} or a.name_ like #{search} or d.user_name like #{search} or b.name_ like #{search} or a.proc_inst_id_ like #{search}");
			args.put("search", "%"+search+"%");
		}
		args.put("#SQL", sql);
		
		return baseService.getDateTableRecord(args);
	}
	
	//查看流程图
	@RequestMapping(value = "/getFlowChart")
	public void getFlowChart() throws IOException{
		myworkflowservice.getFlowChart(response);
	}
	
	//查看当前任务流程图
	@RequestMapping(value = "/getRuntimeFlowChart")
	public void getRuntimeFlowChart() throws IOException{
		myworkflowservice.getRuntimeFlowChart(response);
	}
	
	//指定任务办理人
	@RequestMapping(value = "/AppointAssignee")
	@ResponseBody
	public void AppointAssignee(@RequestParam String taskid,@RequestParam String userid) throws IOException{
		myworkflowservice.AppointAssignee(taskid,userid);
	}
	
	//审批页面
	@RequestMapping(value = "/ApprovalPage")
	public String ApprovalPage(){
		Boolean exist = myworkflowservice.InitApproval();
		
		if(exist){
			baseService.initmenu( "/MyWorkFlow/MyToBeDo");
			return "myworkflow/Approvalpage2";
		}else{
			Map<String,Object> error = new HashMap<String,Object>();
			error.put("message", "该任务不存在！");
			request.setAttribute("error", error);
			return "error/error";
		}
	}
	
	//查看页面
	@RequestMapping(value = "/ShowPage")
	public String ShowlPage() throws SQLException{
		boolean b= myworkflowservice.InitShow();
		if(b) {
			baseService.initmenu("");
			return "myworkflow/ShowPage";
		}else {
			Map<String,Object> error = new HashMap<String,Object>();
			error.put("message", "该流程不存在或已撤回");
			request.setAttribute("error", error);
			return "error/error";
		}
	}
	
	//历史审批页面
	@RequestMapping(value = "/ApprovalPage_History")
	public String ApprovalPage_History(){
		myworkflowservice.InitApproval_History();
		
		baseService.initmenu( "/MyWorkFlow/MyHistoryTask");
		return "myworkflow/Approvalpage_history";
	}
	
	//审批操作
	@RequestMapping(value = "/Approval")
	@ResponseBody
	public void Approve(){
		String userid = ((JSONObject) JSONObject.toJSON(request.getSession().getAttribute("USER"))).getString("userid");
		String choose = request.getParameter("choose");
		String comment = request.getParameter("comment");
		String taskid = request.getParameter("taskid");
		myworkflowservice.approval(taskid, comment, userid, choose);
	}
	
	//我的草稿
	@RequestMapping("/MyDraft")
	public String Create(){
		baseService.initmenu( "/MyWorkFlow/MyDraft");
		return "myworkflow/mydraft";
	}
	
	//获取草稿列表
	@RequestMapping("/getMyDraftList")
	@ResponseBody
	public Map<String,Object> getMyDraftList(){
		Map<String,Object> args = new HashMap<String,Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from v_oa_mydraft where fcreator=#{user}");
		
		String search =request.getParameter("search[value]");
		if(StringUtils.isNotBlank(search)){
			sql.append(" and fname like #{search}");
			args.put("search", "%"+search+"%");
		}
		args.put("#SQL", sql);
		args.put("user", ((JSONObject) JSONObject.toJSON(request.getSession().getAttribute("USER"))).getString("userid"));
		
		return baseService.getDateTableRecord(args);
	}
	
	//手机上传附件页面
	@RequestMapping("/uploadfile")
	public String uploadfile(){
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		
		return "myworkflow/mobileuploadattachment";
	}
	
	//手机上传附件
    @RequestMapping("/upload")
    public void uploadone(@RequestParam("file") CommonsMultipartFile[] files, HttpServletRequest request, HttpServletResponse response) throws IOException {
    	fileservice.SaveFile(files,request);
    	
    	//handler.sendMessageToUser(((JSONObject) JSONObject.toJSON(request.getSession().getAttribute("USER"))).getString("userid"), new TextMessage("hehe"));
    	
    	template.convertAndSend("ws", ((JSONObject) JSONObject.toJSON(request.getSession().getAttribute("USER"))).getString("userid"));
    	response.getWriter().print(true);
    }
    
	//手机上传附件 --压缩
    @RequestMapping("/upload_blob")
    public void uploadblob(@RequestParam("file") CommonsMultipartFile[] files) throws IOException {
    	fileservice.SaveFile(files,request);
    	
    	handler.sendMessageToUser(((JSONObject) JSONObject.toJSON(request.getSession().getAttribute("USER"))).getString("userid"), new TextMessage("hehe"));
    	
    	response.getWriter().print(true);
    }
    
	//冻结流程
    @RequestMapping("/suspendProcessInstanceById")
    @ResponseBody
    public void suspendProcessInstanceById(@RequestParam("processinstanceid") String processinstanceid) throws IOException {
    	runtimeService.suspendProcessInstanceById(processinstanceid);
    }
    
	//启动流程--测试专用
    @RequestMapping("/startProcess")
    @ResponseBody
    public String startProcess(@RequestParam("key") String key) throws IOException {
		ProcessInstance processinstance = runtimeService.startProcessInstanceByKey(key);
		return "processDefinitionId="+processinstance.getProcessDefinitionId()+"&"+"processInstanceId="+processinstance.getId();
    }
    
	//完成任务--测试专用
    @RequestMapping("/completeTask")
    @ResponseBody
    public String completeTask(@RequestParam("taskid") String id) throws IOException {
    	taskService.complete(id);
    	return "1";
    }
    
    //结束流程（正常完成）
    @RequestMapping("/completeProcessinstance")
    @ResponseBody
    public void completeProcessinstance() {
    	String processinstanceid = request.getParameter("processinstanceid");
    	String activityid = request.getParameter("activityid");
    	((RuntimeServiceImpl)runtimeService).getCommandExecutor().execute(new JumpCmd(processinstanceid,activityid==null?"end":activityid,null));
    }
    
    //撤销
    @RequestMapping("/undo/{processinstanceid}")
    @ResponseBody
    public String undo(@PathVariable String processinstanceid) {
//		TaskServiceImpl taskServiceImpl=(TaskServiceImpl)taskService;
//		taskServiceImpl.getCommandExecutor().execute(new Undo(processinstanceid));
    	
    	String editurl = myworkflowservice.Undo(processinstanceid);

    	return editurl;
    }
}
