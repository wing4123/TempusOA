package cn.tempus.myworkflow;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.Expression;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

@Service
public class SetVariableslistener implements Serializable, ExecutionListener {
	
	private static final long serialVersionUID = 1L;
	
	private Expression variables;

	public Expression getVariables() {
		return variables;
	}

	public void setVariables(Expression variables) {
		this.variables = variables;
	}

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		String vars = variables.getValue(execution).toString();
		if(vars.startsWith("vars")){
			Map<String,Object> data = JSONObject.parseObject(variables.getValue(execution).toString().substring(5));
			execution.setVariables(data);
		}else if(vars.startsWith("localvars")){
			Map<String,Object> data = JSONObject.parseObject(variables.getValue(execution).toString().substring(10));
			execution.setVariablesLocal(data);
		}
	}

}
