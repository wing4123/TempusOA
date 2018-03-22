package cn.tempus.reimbursement.WF;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.delegate.DelegateTask;  
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tempus.commons.GlobalCls;
import cn.tempus.dao.EasyDao;
import cn.tempus.email.Email;

/** 
* @author 吴中贤 wing4123@163.com
* @date 2017年10月11日
* @Description: 发送邮件通知提交人提交单据
*  
*/
@Service("reimbursement_sendemail")
public class SendEmailTaskListener implements Serializable, TaskListener {  
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	EasyDao basicService;
	
	@Autowired
    public Email email;
  
    @Override  
    public void notify(DelegateTask task) {
    	Object starter = task.getVariable("sys_starter");
    	Map<String,Object> starterinfo = basicService.GetSinglerData("select user_name fname,email_address femail from tb_user where user_id='"+starter+"'");
		ProcessInstance processinstance = task.getExecution().getEngineServices().getRuntimeService().createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
		Object shendanren = basicService.GetFirstValueBySql("select b.user_name from act_hi_taskinst a left join tb_user b on b.user_id=a.assignee_ where a.proc_inst_id_='"+task.getProcessInstanceId()+"' and name_ like '%审单人%' order by end_time_ desc ");
		
		if(starterinfo.get("FEMAIL")!=null) {
	    	try {
				HtmlEmail htmlemail = email.getHtmlEmail();
				htmlemail.addTo(starterinfo.get("FEMAIL").toString(), starterinfo.get("FNAME").toString());
				htmlemail.setSubject("费用报销交单提醒");
//				msg.setBody(MessageBody.getMessageBodyFromText(
//						"<html><div>Dear &nbsp;"+starterinfo.get("FNAME")+" ：</div><div style='text-indent:2em;'>您的费用报销流程：<span style='font-weight: bold;'>" + processinstance.getName()
//								+ "</span>已审批完成，请提交原始单据给:"+shendanren+"，以便尽快安排付款，谢谢。</div><a href='https://" + GlobalCls.GP.getProperty("server.ip") + ":"
//								+ GlobalCls.GP.getProperty("server.port") + "/OA/MyWorkFlow/ShowPage?processinstanceid="
//								+ processinstance.getId() + "'>查看表单</a></html>"));
				htmlemail.setHtmlMsg("<html><div>Dear &nbsp;"+starterinfo.get("FNAME")+" ：</div><div style='text-indent:2em;'>您的费用报销流程：<span style='font-weight: bold;'>" + processinstance.getName()
								+ "</span>已审批完成，请提交原始单据给:"+shendanren+"，以便尽快安排付款，谢谢。</div><a href='https://" + GlobalCls.GP.getProperty("server.ip") + ":"
								+ GlobalCls.GP.getProperty("server.port") + "/OA/MyWorkFlow/ShowPage?processinstanceid="
								+ processinstance.getId() + "'>查看表单</a></html>");
				htmlemail.send();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
    }
}