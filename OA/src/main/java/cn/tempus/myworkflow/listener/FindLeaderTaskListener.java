package cn.tempus.myworkflow.listener;

import java.io.Serializable;

import org.activiti.engine.delegate.DelegateTask;  
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service("cn_tempus_myworkflow_listener_FindLeaderTaskListener")
public class FindLeaderTaskListener implements Serializable, TaskListener {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	JdbcTemplate jdbc;
  
    @Override  
    public void notify(DelegateTask task) {
    	Object starter = task.getVariable("sys_starter");
    	
    	String leader = jdbc.queryForObject("select attribute14 from tb_user where user_id=?", String.class, starter);
		task.setAssignee(leader);
    }

}