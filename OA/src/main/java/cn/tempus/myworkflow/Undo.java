package cn.tempus.myworkflow;
import java.util.Iterator;
import java.util.List;

import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityManager;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntityManager;
import org.activiti.engine.impl.pvm.process.ActivityImpl;

/**
 * 撤回
 */
public class Undo implements Command<Void> {
	protected String processinstanceid;
	
	/**
	 * 构造参数 可以根据自己的业务需要添加更多的字段
	 * @param taskId
	 * @param desActivity
	 * @param currentActivity
	 */
	public Undo(String processinstanceid) {
		this.processinstanceid = processinstanceid;
	}

	public Void execute(CommandContext commandContext) {
		TaskEntityManager taskEntityManager = Context.getCommandContext().getTaskEntityManager();
		ExecutionEntityManager executionEntityManager = Context.getCommandContext().getExecutionEntityManager();
		ExecutionEntity executionentity = executionEntityManager.findExecutionById(processinstanceid);
		ExecutionEntity processInstance = executionentity.getProcessInstance();
    	ActivityImpl toactivity = processInstance.getProcessDefinition().findActivity("turndown");
		
    	//删除processInstance的所有task
		Iterator<TaskEntity> tasks = processInstance.getTasks().iterator();
		while (tasks.hasNext()) {
			TaskEntity taskEntity = tasks.next();
			taskEntityManager.deleteTask(taskEntity, "Undo", true);
		}
		
		//递归删除所有excution的所有task
		if(processInstance.getExecutions().size()>0){
			deletetask(processInstance);
		}
		
		processInstance.removeVariable("end");
		processInstance.removeVariable("approvals");
		processInstance.setVariable("choose", 2);
		
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
    			Context.getCommandContext().getTaskEntityManager().deleteTask(taskentity, "undo", true);
    		}
			
			if(executionentity.getExecutions().size()>0){
				deletetask(executionentity);
			}
			
//			executionentity.end();
			Context.getCommandContext().getHistoryManager().recordActivityEnd(executionentity);
			Context.getCommandContext().getExecutionEntityManager().deleteProcessInstance(executionentity.getId(),"undo",false);
		}
    }
}