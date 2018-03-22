package cn.tempus.myworkflow;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.Expression;
import org.springframework.beans.factory.annotation.Autowired;

import cn.tempus.dao.EasyDao;

public class UpdateTableListener implements Serializable, ExecutionListener {
	
	@Autowired
    public EasyDao basicService;
	
	private static final long serialVersionUID = 1L;
	
	private Expression table;
	private Expression field;
	private Expression value;

	public Expression getTable() {
		return table;
	}

	public void setTable(Expression table) {
		this.table = table;
	}

	public Expression getField() {
		return field;
	}

	public void setField(Expression field) {
		this.field = field;
	}

	public Expression getValue() {
		return value;
	}

	public void setValue(Expression value) {
		this.value = value;
	}

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "update #{table} set #{field} = #{value}");
		args.put("table", table.getValue(execution));
		args.put("field", field.getValue(execution));
		args.put("value", value.getValue(execution));
		
		basicService.UpdateData(args);
		
	}

}
