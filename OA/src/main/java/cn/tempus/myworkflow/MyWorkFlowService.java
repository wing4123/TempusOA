package cn.tempus.myworkflow;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Model;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import cn.tempus.dao.EasyDao;
import cn.tempus.email.Email;
import cn.tempus.utils.LobUtils;
import cn.tempus.utils.DateDistance;

@Service
public class MyWorkFlowService {
	
	@Autowired
    public EasyDao basicService;
	
	@Autowired
	ProcessCoreService processcoreservice;
	
	@Autowired
    public Email email;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	JdbcTemplate jdbc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public String AddModel() throws UnsupportedEncodingException{
		
		RepositoryService repositoryService = processEngine.getRepositoryService();
		
		JSONObject editor = new JSONObject();
		
		JSONObject stencilset = new JSONObject();
		stencilset.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
		editor.put("stencilset", stencilset);
		
		JSONObject model = new JSONObject();
		model.put(ModelDataJsonConstants.MODEL_NAME, "New process");
		model.put(ModelDataJsonConstants.MODEL_DESCRIPTION, "New process");
		model.put(ModelDataJsonConstants.MODEL_REVISION, 1);
		
		Model modelData = repositoryService.newModel();
        modelData.setMetaInfo(model.toString());
        repositoryService.saveModel(modelData);
        repositoryService.addModelEditorSource(modelData.getId(), editor.toString().getBytes("UTF-8"));
		
		return modelData.getId();
	}
	
	public Map<String, Object> getModelList(){
		int start = Integer.parseInt(request.getParameter("start"));
		int length = Integer.parseInt(request.getParameter("length"));
		List<Model> listmodel = repositoryService.createModelQuery().listPage(start, start+length);
		Map<String,Object> result = new HashMap<String,Object>();
		int count = (int)repositoryService.createModelQuery().count();
		result.put("recordsFiltered", count);
		result.put("recordsTotal", count);
		result.put("draw", request.getParameter("draw"));
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		for(Model model: listmodel){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("FMODELID", model.getId());
			map.put("FWFCODE", model.getKey());
			map.put("FNAME", model.getName());
			map.put("FDESCRIPTION", JSONObject.parseObject(model.getMetaInfo()).getString("description"));
			data.add(map);
		}
		result.put("data", data);
		
		return result;
	}
	
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public boolean DeleteModel(String id){	
		repositoryService.deleteModel(id);
		return true;
	}
	
	//导出模型
    public ResponseEntity<byte[]> ExportModel(String id) throws IOException {
		JsonNode editorNode = objectMapper.readTree(repositoryService.getModelEditorSource(id));
	    BpmnModel bpmnModel = new BpmnJsonConverter().convertToBpmnModel(editorNode);
	    String filename = bpmnModel.getMainProcess().getId() + ".bpmn20.xml";
	    byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(bpmnModel);
    	
        HttpHeaders headers = new HttpHeaders();    
//        headers.setContentDispositionFormData("attachment", filename,Charset.forName("UTF-8"));
        headers.setContentDispositionFormData("attachment", filename);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);   
        return new ResponseEntity<byte[]>(bpmnBytes,headers, HttpStatus.OK);
    }
    
    //导入模型
    public void ImportModel(CommonsMultipartFile[] files,HttpServletRequest request) throws XMLStreamException, IOException{
    	for(CommonsMultipartFile file: files){
			if (file.getOriginalFilename().endsWith(".bpmn20.xml") || file.getOriginalFilename().endsWith(".bpmn")) {
//				XMLInputFactory xmlFactory  = XMLInputFactory.newInstance();
//				XMLStreamReader reader = xmlFactory.createXMLStreamReader(file.getInputStream());
//				BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(reader);
//			  
//				if (bpmnModel.getMainProcess() != null && bpmnModel.getMainProcess().getId() != null  && !bpmnModel.getLocationMap().isEmpty()) {
//					String processName = bpmnModel.getMainProcess().getName();
//					Model modelData = repositoryService.newModel();
//					ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
//					modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, processName);
//					modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, processName);
//					modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
//					modelData.setMetaInfo(modelObjectNode.toString());
//					modelData.setName(processName);
//				      
//					repositoryService.saveModel(modelData);
//					  
//					BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
//					ObjectNode editorNode = jsonConverter.convertToJson(bpmnModel);
//					  
//					repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("UTF-8"));
//				}
				
				XMLInputFactory xmlFactory  = XMLInputFactory.newInstance();
				XMLStreamReader reader = xmlFactory.createXMLStreamReader(file.getInputStream());
				BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(reader);
			  
				String modelName = bpmnModel.getMainProcess().getName();
				Model modelData = processEngine.getRepositoryService().newModel();
				ObjectNode modelObjectNode = objectMapper.createObjectNode();
				modelObjectNode.put("name", modelName);
				modelObjectNode.put("description", modelName);
				modelObjectNode.put("revision", 1);
				modelData.setMetaInfo(modelObjectNode.toString());
				modelData.setName(modelName);
				processEngine.getRepositoryService().saveModel(modelData);
				  
				BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
				ObjectNode bpmnJson = jsonConverter.convertToJson(bpmnModel);
				  
				processEngine.getRepositoryService().addModelEditorSource(modelData.getId(), bpmnJson.toString().getBytes());
			}
    	}
    }
	
	/**
	 * @param wfcode 流程编号
	 * @param billid 单据id
	 * @param userid 用户id
	 * @param processvars 流程变量
	 * @return 流程实例id
	 */
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public String SaveAndStart(String wfcode,String billid,String userid, Map<String,Object> processvars,String processinstancename,String tablename){
		Map<String ,Object > sysvar=new HashMap<String, Object>();
		sysvar.put("sys_starter", userid);
		sysvar.put("sys_billid", billid);
		sysvar.put("sys_wfcode", wfcode);
		sysvar.put("sys_table", tablename);
		
		sysvar.put("choose", 0);
		
		if(processvars!=null){
			sysvar.putAll(processvars);
		}
		
		identityService.setAuthenticatedUserId(userid);
		ProcessInstance processinstance = runtimeService.startProcessInstanceByKey(wfcode, billid,sysvar);
		runtimeService.setProcessInstanceName(processinstance.getId(), processinstancename);
		return processinstance.getId();
	}
	
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public boolean SaveAndStartNext(String wfcode,String billid,String userid){	
		Map<String ,Object > sysvar=new HashMap<String, Object>();
		sysvar.put("sys_starter", userid);
		sysvar.put("sys_billid", billid);
		sysvar.put("sys_wfcode", wfcode);
		
		identityService.setAuthenticatedUserId(userid);
		ProcessInstance processinstance = runtimeService.startProcessInstanceByKey(wfcode, billid,sysvar);
		taskService.createTaskQuery().processInstanceId(processinstance.getId()).singleResult().setAssignee(userid);
		taskService.complete(taskService.createTaskQuery().processInstanceId(processinstance.getId()).singleResult().getId());
		
		return true;
	}
	
	public Map<String, Object> getAllRuntimeTask(){
		int start = Integer.parseInt(request.getParameter("start"));
		int length = Integer.parseInt(request.getParameter("length"));
		List<Task> tasklist = taskService.createTaskQuery().orderByTaskCreateTime().desc().listPage(start, start+length);
		int count = (int)taskService.createTaskQuery().count();
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("recordsFiltered", count);
		result.put("recordsTotal", count);
		result.put("draw", request.getParameter("draw"));
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		for(Task task: tasklist){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("FTASKID", task.getId());
			map.put("FBILLNAME", repositoryService.createProcessDefinitionQuery().processDefinitionId(task.getProcessDefinitionId()).singleResult().getName());
			map.put("FCREATETIME", task.getCreateTime());
			data.add(map);
		}
		result.put("data", data);
		
		return result;
	}
	
	//查看模型流程图
	public void getFlowChart(HttpServletResponse response) throws IOException{
		String modelid = request.getParameter("modelid");
		Model modelData = processEngine.getRepositoryService().getModel(modelid);
        ObjectNode modelNode = (ObjectNode) objectMapper.readTree(repositoryService.getModelEditorSource(modelData.getId()));  
        BpmnModel bpmnmodel = new BpmnJsonConverter().convertToBpmnModel(modelNode);
		ProcessDiagramGenerator diagramGenerator = processEngine.getProcessEngineConfiguration().getProcessDiagramGenerator();
		InputStream imageStream = diagramGenerator.generateDiagram(bpmnmodel, "jpg", "宋体", "宋体", "宋体", null);
		response.setContentType("image/jpeg");
		OutputStream out = response.getOutputStream();
        byte[] imagebyte = new byte[imageStream.available()];
        imageStream.read(imagebyte);
        out.write(imagebyte);
        out.flush();
	}
	
	//查看正在运行的流程图
	public void getRuntimeFlowChart(HttpServletResponse response) throws IOException{
		String taskid = request.getParameter("taskid");
		HistoricTaskInstance historytask = historyService.createHistoricTaskInstanceQuery().taskId(taskid).singleResult();
		
		List<HistoricActivityInstance> highLightedActivitList =  historyService.createHistoricActivityInstanceQuery().processInstanceId(historytask.getProcessInstanceId()).list();
        //高亮环节id集合
        List<String> highLightedActivities = new ArrayList<String>();
        //高亮线路id集合
        ProcessDefinitionEntity definitionEntity = (ProcessDefinitionEntity)repositoryService.getProcessDefinition(historytask.getProcessDefinitionId());
        List<String> highLightedFlows = getHighLightedFlows(definitionEntity,highLightedActivitList);
        for(HistoricActivityInstance tempActivity : highLightedActivitList){
            String activityId = tempActivity.getActivityId();
            highLightedActivities.add(activityId);
        }
        
        BpmnModel bpmnmodel = repositoryService.getBpmnModel(historytask.getProcessDefinitionId());
        ProcessDiagramGenerator diagramGenerator = processEngine.getProcessEngineConfiguration().getProcessDiagramGenerator();
		InputStream imageStream = diagramGenerator.generateDiagram(bpmnmodel, "png", highLightedActivities, highLightedFlows, "宋体", "宋体", "宋体", null, 1.0);
		response.setContentType("image/png");
		OutputStream out = response.getOutputStream();
        byte[] imagebyte = new byte[imageStream.available()];
        imageStream.read(imagebyte);
        out.write(imagebyte);
        out.flush();
	}
	
	/**
     * 获取需要高亮的线
     * @param processDefinitionEntity
     * @param historicActivityInstances
     * @return
     */
    private List<String> getHighLightedFlows(ProcessDefinitionEntity processDefinitionEntity,List<HistoricActivityInstance> historicActivityInstances) {
        List<String> highFlows = new ArrayList<String>();// 用以保存高亮的线flowId
        for (int i = 0; i < historicActivityInstances.size() - 1; i++) {// 对历史流程节点进行遍历
            ActivityImpl activityImpl = processDefinitionEntity.findActivity(historicActivityInstances.get(i).getActivityId());// 得到节点定义的详细信息
            List<ActivityImpl> sameStartTimeNodes = new ArrayList<ActivityImpl>();// 用以保存后需开始时间相同的节点
            ActivityImpl sameActivityImpl1 = processDefinitionEntity.findActivity(historicActivityInstances.get(i + 1).getActivityId());
            // 将后面第一个节点放在时间相同节点的集合里
            sameStartTimeNodes.add(sameActivityImpl1);
            for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {
                HistoricActivityInstance activityImpl1 = historicActivityInstances.get(j);// 后续第一个节点
                HistoricActivityInstance activityImpl2 = historicActivityInstances.get(j + 1);// 后续第二个节点
                if (activityImpl1.getStartTime().equals(activityImpl2.getStartTime())) {
                    // 如果第一个节点和第二个节点开始时间相同保存
                    ActivityImpl sameActivityImpl2 = processDefinitionEntity.findActivity(activityImpl2.getActivityId());
                    sameStartTimeNodes.add(sameActivityImpl2);
                } else {
                    // 有不相同跳出循环
                    break;
                }
            }
            List<PvmTransition> pvmTransitions = activityImpl.getOutgoingTransitions();// 取出节点的所有出去的线
            for (PvmTransition pvmTransition : pvmTransitions) {
                // 对所有的线进行遍历
                ActivityImpl pvmActivityImpl = (ActivityImpl) pvmTransition.getDestination();
                // 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示
                if (sameStartTimeNodes.contains(pvmActivityImpl)) {
                    highFlows.add(pvmTransition.getId());
                }
            }
        }
        return highFlows;
    }
    
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public boolean AppointAssignee(String taskid,String userid){	
		taskService.setAssignee(taskid, userid);
		
		return true;
	}
	
	//审批操作
	public void approval(String taskid,String comment,String userid, String choose){
//		taskService.claim(taskid, userid);
		identityService.setAuthenticatedUserId(userid);
		Task task = taskService.createTaskQuery().taskId(taskid).singleResult();
		ProcessInstance processinstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
		taskService.addComment(taskid, processinstance.getId(), comment);
		Map<String,Object> var = new HashMap<String,Object>();
		var.put("choose", choose);
		runtimeService.setVariables(task.getProcessInstanceId(), var);
		taskService.setVariablesLocal(taskid,var);
		if(task.getAssignee()==null) {
			taskService.claim(taskid, userid);
		}
		if(choose.equals("2")){
			TaskServiceImpl taskServiceImpl=(TaskServiceImpl)taskService;
			taskServiceImpl.getCommandExecutor().execute(new TurnDown(taskid));
		}else{
			Object allowend = taskService.getVariable(taskid, "allowend");
    		if(allowend!=null && allowend.equals("1")){
    			int count = basicService.SelectCountBySql("select count(a.fuserid) from  TB_OA_userposition a left join TB_OA_position b on b.fid=a.fpositionid where a.fuserid='"+userid+"' and b.fname='执董'");
    			if(count>0){
        			try {
        				processcoreservice.endProcess(taskid);
        			} catch (Exception e) {
        				e.printStackTrace();
        			}
        		}else{
        			taskService.complete(taskid);
        		}
    		}else{
    			taskService.complete(taskid);
    		}
			
//			taskService.complete(taskid);
		}
	}
	
	//初始化审批界面
	public boolean InitApproval(){
		String taskid = request.getParameter("taskid");
		request.setAttribute("taskid", taskid);
		Task task = taskService.createTaskQuery().taskId(taskid).singleResult();
		if(task!=null){
			Map<String, Object> args = new HashMap<String, Object>();
			args.put("#SQL", "select a.name_ taskname,b.user_name assignee,to_char(a.start_time_,'yyyy-mm-dd hh24:mi:ss') starttime,to_char(a.end_time_,'yyyy-mm-dd hh24:mi:ss') endtime,c.message_ fcomment,decode(d.text_,'1','同意','2','驳回','3','重新提交','4','放弃') foption from act_hi_taskinst a left join tb_user b on b.user_id=a.assignee_ left join act_hi_comment c on c.task_id_=a.id_ left join act_hi_varinst d on d.task_id_=a.id_ and d.name_='choose' where a.proc_inst_id_=#{processinstanceid} and a.assignee_ is not null and c.action_='AddComment' order by a.start_time_");
			args.put("processinstanceid", task.getProcessInstanceId());
			List<HashMap<String,Object>> list = basicService.SelectListBySqlWithWhere(args);
			for(int i=0;i<list.size();i++){
				list.get(i).put("usetime", DateDistance.getDistanceTime(list.get(i).get("STARTTIME"), list.get(i).get("ENDTIME")));
			}
			args.put("#SQL", "select '开始' taskname,c.user_name assignee,to_char(b.start_time_,'yyyy-mm-dd hh24:mi:ss') starttime,'' endtime, null fcomment,'' foption from act_hi_procinst b left join tb_user c on c.user_id=b.start_user_id_ where b.proc_inst_id_=#{processinstanceid}");
			list.add(0, basicService.SelectListBySqlWithWhere(args).get(0));
			
			request.setAttribute("history", list);
			request.setAttribute("formid", runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult().getBusinessKey());
			request.setAttribute("formurl", runtimeService.getVariable(task.getExecutionId() ,"approvalurl"));
			request.setAttribute("choose", runtimeService.getVariable(task.getExecutionId() ,"choose"));
			
			args.put("taskid", taskid);
			args.put("#SQL", "select name_ from act_ru_task where id_=#{taskid}");
			Object taskname = basicService.GetFirstValueBySqlWithWhere(args);
			request.setAttribute("uploadfile", taskname!=null && taskname.toString().contains("法务部"));
		}
		
		return task!=null;
	}
	
	//初始化查看界面
	public boolean InitShow(){
		String taskid = request.getParameter("taskid");
		String processinstanceid = request.getParameter("processinstanceid");
		//request.setAttribute("taskid", taskid);
		processinstanceid = processinstanceid==null?historyService.createHistoricTaskInstanceQuery().taskId(taskid).singleResult().getProcessInstanceId():processinstanceid;
		
		HistoricProcessInstance historyprocessintance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processinstanceid).notDeleted().singleResult();
		if(historyprocessintance!=null) {
			Map<String, Object> args = new HashMap<String, Object>();
			args.put("#SQL", "select a.id_ ftaskid,a.name_ taskname,b.user_name assignee,to_char(a.start_time_,'yyyy-mm-dd hh24:mi:ss') starttime,to_char(a.end_time_,'yyyy-mm-dd hh24:mi:ss') endtime,c.message_ fcomment,decode(d.text_,'0','提交','1','同意','2','驳回','3','重新提交','4','放弃') foption from act_hi_taskinst a left join tb_user b on b.user_id=a.assignee_ left join act_hi_comment c on c.task_id_=a.id_ and c.action_='AddComment' left join act_hi_varinst d on d.task_id_=a.id_ and d.name_='choose' where a.proc_inst_id_=#{processinstanceid} and (a.assignee_ is not null or a.end_time_ is null)  order by a.start_time_");
			args.put("processinstanceid", historyprocessintance.getId());
			List<HashMap<String,Object>> list = basicService.SelectListBySqlWithWhere(args);
			args.put("#SQL", "select b.user_name fusername from act_ru_identitylink a left join tb_user b on b.user_id=a.user_id_ where a.type_='candidate' and a.task_id_=#{taskid} ");
			for(int i=0;i<list.size();i++){
				list.get(i).put("usetime", DateDistance.getDistanceTime(list.get(i).get("STARTTIME"), list.get(i).get("ENDTIME")));
				if(list.get(i).get("ASSIGNEE")==null) {
					args.put("taskid", list.get(i).get("FTASKID"));
					List<HashMap<String,Object>> candidateusers = basicService.SelectListBySqlWithWhere(args);
					String users = "";
					for(HashMap<String,Object> candidateuser:candidateusers) {
						users = users+","+candidateuser.get("FUSERNAME");
					}
					list.get(i).put("ASSIGNEE", users.length()>0?users.substring(1):"");
				}
			}
			args.put("#SQL", "select '开始' taskname,c.user_name assignee,to_char(b.start_time_,'yyyy-mm-dd hh24:mi:ss') starttime,'' endtime, null fcomment,'' foption from act_hi_procinst b left join tb_user c on c.user_id=b.start_user_id_ where b.proc_inst_id_=#{processinstanceid}");
			list.add(0, basicService.SelectListBySqlWithWhere(args).get(0));
			args.put("#SQL", "select '结束' taskname,'' assignee,'' starttime,to_char(b.end_time_,'yyyy-mm-dd hh24:mi:ss') endtime, null fcomment,'' foption from act_hi_procinst b left join tb_user c on c.user_id=b.start_user_id_ where b.proc_inst_id_=#{processinstanceid} and b.end_time_ is not null");
			list.addAll(basicService.SelectListBySqlWithWhere(args));
			
			historyService.createHistoricVariableInstanceQuery().processInstanceId(historyprocessintance.getId()).variableName("choose");
			
			request.setAttribute("processinstanceid", historyprocessintance.getId());
			request.setAttribute("history", list);
			request.setAttribute("formid", historyService.createHistoricProcessInstanceQuery().processInstanceId(historyprocessintance.getId()).singleResult().getBusinessKey());
			request.setAttribute("formurl", historyService.createHistoricVariableInstanceQuery().processInstanceId(historyprocessintance.getId()).variableName("showurl").singleResult().getValue());
			request.setAttribute("choose", historyService.createHistoricVariableInstanceQuery().processInstanceId(historyprocessintance.getId()).variableName("choose").excludeTaskVariables().singleResult().getValue());
			request.setAttribute("sys_starter", historyService.createHistoricVariableInstanceQuery().processInstanceId(historyprocessintance.getId()).variableName("sys_starter").singleResult().getValue());
			
			return true;
		}else {
			return false;
		}
	}
	
	//初始化历史审批界面
	public void InitApproval_History(){
		String taskid = request.getParameter("taskid");
		String processinstanceid = request.getParameter("processinstanceid");
		//request.setAttribute("taskid", taskid);
		processinstanceid = processinstanceid==null?historyService.createHistoricTaskInstanceQuery().taskId(taskid).singleResult().getProcessInstanceId():processinstanceid;
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("#SQL", "select a.name_ taskname,b.user_name assignee,to_char(a.start_time_,'yyyy-mm-dd hh24:mi:ss') starttime,to_char(a.end_time_,'yyyy-mm-dd hh24:mi:ss') endtime,c.full_msg_ fcomment,decode(d.text_,'1','同意','2','驳回','3','重新提交'，'4','放弃') foption from act_hi_taskinst a left join tb_user b on b.user_id=a.assignee_ left join act_hi_comment c on c.task_id_=a.id_ left join act_hi_varinst d on d.task_id_=a.id_ and d.name_='choose' where a.proc_inst_id_=#{processinstanceid} and a.assignee_ is not null  order by a.start_time_");
		args.put("processinstanceid", processinstanceid);
		List<HashMap<String,Object>> list = basicService.SelectListBySqlWithWhere(args);
		for(int i=0;i<list.size();i++){
			list.get(i).put("usetime", DateDistance.getDistanceTime(list.get(i).get("STARTTIME"), list.get(i).get("ENDTIME")));
			list.get(i).put("COMMENT", LobUtils.blob2String(list.get(i).get("FCOMMENT")));
		}
		request.setAttribute("history", list);
		request.setAttribute("formid", historyService.createHistoricProcessInstanceQuery().processInstanceId(processinstanceid).singleResult().getBusinessKey());
		request.setAttribute("formurl", historyService.createHistoricVariableInstanceQuery().processInstanceId(processinstanceid).variableName("choose").singleResult().getValue());
	}
	
	public List<HashMap<String, Object>> getAllProcess(){
		String sql = "select proc_key,proc_name from (SELECT ROW_NUMBER() OVER(PARTITION BY key_ ORDER BY version_ desc) rn, key_ proc_key,name_ proc_name FROM act_re_procdef) where rn=1";
		
		return basicService.SelectListBySql(sql);
	}
	
	public String Undo(String processinstanceid){
    	Object billid = historyService.createHistoricVariableInstanceQuery().processInstanceId(processinstanceid).excludeTaskVariables().variableName("sys_billid").singleResult().getValue();
    	Object table = historyService.createHistoricVariableInstanceQuery().processInstanceId(processinstanceid).excludeTaskVariables().variableName("sys_table").singleResult().getValue();
    	
    	runtimeService.deleteProcessInstance(processinstanceid,"Undo");//删除流程
    	
    	String sql = "update "+table+" set fstatus='1',fprocessinstanceid=null where fprocessinstanceid=?";
		jdbc.update(sql,processinstanceid);
		
		sql = "select fediturl from v_oa_mydraft where fid=?";
		String editurl = jdbc.queryForObject(sql, String.class, billid);
		
		return editurl;
	}
	
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
	private ManagementService managementService;

}
