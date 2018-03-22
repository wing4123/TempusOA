package cn.tempus.myworkflow;

import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

@Service(value="settaskvariables")
public class SetTaskVariableslistener implements TaskListener {
	
	private static final long serialVersionUID = 1L;
	
	private Expression variables;

	public Expression getVariables() {
		return variables;
	}

	public void setVariables(Expression variables) {
		this.variables = variables;
	}

	@Override
	public void notify(DelegateTask task) {
		String vars = variables.getValue(task).toString();
		if(vars.startsWith("vars")){
			Map<String,Object> data = JSONObject.parseObject(variables.getValue(task).toString().substring(5));
			task.setVariables(data);
		}else if(vars.startsWith("localvars")){
			Map<String,Object> data = JSONObject.parseObject(variables.getValue(task).toString().substring(10));
			task.setVariablesLocal(data);
		}
	}

}
