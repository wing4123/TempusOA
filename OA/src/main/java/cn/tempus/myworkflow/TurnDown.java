package cn.tempus.myworkflow;
import java.util.Iterator;
import java.util.List;

import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntityManager;
import org.activiti.engine.impl.pvm.process.ActivityImpl;

public class TurnDown implements Command<Void> {
	protected String taskid;
	
	/**
	 * 构造参数 可以根据自己的业务需要添加更多的字段
	 * @param taskId
	 * @param desActivity
	 * @param currentActivity
	 */
	public TurnDown(String taskid) {
		this.taskid = taskid;
	}

	public Void execute(CommandContext commandContext) {
		TaskEntityManager taskEntityManager = Context.getCommandContext().getTaskEntityManager();
		TaskEntity taskentity = taskEntityManager.findTaskById(taskid);
		ExecutionEntity processInstance = taskentity.getProcessInstance();
    	ActivityImpl toactivity = processInstance.getProcessDefinition().findActivity("turndown");
		
    	//删除processInstance的所有task
		Iterator<TaskEntity> tasks = processInstance.getTasks().iterator();
		while (tasks.hasNext()) {
			TaskEntity taskEntity = tasks.next();
			taskEntityManager.deleteTask(taskEntity, "turn down", !taskentity.getId().equals(taskid));
		}
		
		//递归删除所有excution的所有task
		if(processInstance.getExecutions().size()>0){
			deletetask(processInstance);
		}
		
		processInstance.setVariable("turndowntaskid", taskid);
		processInstance.removeVariable("end");
		processInstance.removeVariable("approvals");
		
		processInstance.executeActivity(toactivity);
		processInstance.setActive(true);
		return null;
	}
	
    public void deletetask(ExecutionEntity processinstance){
    	List<ExecutionEntity> executions = processinstance.getExecutions();
    	while(executions.size()>0){
    		ExecutionEntity executionentity = executions.get(0);
			List<TaskEntity> tasks2 = executionentity.getTasks();
    		for(TaskEntity taskentity:tasks2){
    			Context.getCommandContext().getTaskEntityManager().deleteTask(taskentity, "turn down", !taskentity.getId().equals(taskid));
    		}
			
			if(executionentity.getExecutions().size()>0){
				deletetask(executionentity);
			}
			
//			executionentity.end();
			Context.getCommandContext().getHistoryManager().recordActivityEnd(executionentity);
			Context.getCommandContext().getExecutionEntityManager().deleteProcessInstance(executionentity.getId(),"trun down",false);
		}
    }
}