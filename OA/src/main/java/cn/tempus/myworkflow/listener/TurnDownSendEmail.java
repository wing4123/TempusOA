package cn.tempus.myworkflow.listener;

import java.io.Serializable;
import java.util.HashMap;

import org.activiti.engine.delegate.DelegateTask;  
import org.activiti.engine.delegate.TaskListener;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tempus.commons.GlobalCls;
import cn.tempus.dao.EasyDao;
import cn.tempus.email.Email;

@Service(value="turndownsendemail")
public class TurnDownSendEmail implements Serializable, TaskListener {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	EasyDao basicService;
	
	@Autowired
    public Email email;
  
    @Override  
    public void notify(DelegateTask task) {
    	Object starter = task.getVariable("sys_starter");
    	Object turndowntaskid = task.getVariable("turndowntaskid");
		HashMap<String,Object> processinfo = basicService.GetSinglerData("select a.name_ ftaskname,c.name_ fprocessname,b.message_ fcomment from act_hi_taskinst a left join act_hi_comment b on b.task_id_=a.id_ and b.type_='comment' left join act_hi_procinst c on c.id_=a.proc_inst_id_ where a.id_='"+turndowntaskid+"'");
    	
		HashMap<String,Object> starterinfo = basicService.GetSinglerData("select email_address FEMAIL,user_name FNAME from tb_user where user_id='"+starter+"'");
    	
    	try {
//			EmailMessage msg = email.newmsg();
//			if (starterinfo.get("FEMAIL") != null) {
//				msg.getToRecipients().add(starterinfo.get("FEMAIL").toString());
//				msg.setSubject("流程驳回提醒");
//				msg.setBody(MessageBody.getMessageBodyFromText("<html><div>Dear "+starterinfo.get("FNAME")+",</div><div style='text-indent:2em;'>您的流程：<span style='font-weight: bold;'>" + processinfo.get("FPROCESSNAME")
//						+ "</span>在"+processinfo.get("FTASKNAME")+"节点被驳回，原因为："+processinfo.get("FCOMMENT")+"；请调整后抓紧时间再次提交，谢谢。</div><a href='https://"+GlobalCls.GP.getProperty("server.ip")+":"+GlobalCls.GP.getProperty("server.port")+"/OA/MyWorkFlow/ApprovalPage?taskid="
//						+ task.getId() + "'>办理任务</a></html>"));//BlobUtils.Blob2String(processinfo.get("FCOMMENT"))
//				msg.send();
//			}
			if (starterinfo.get("FEMAIL") != null) {
	    		HtmlEmail htmlemail = email.getHtmlEmail();
	    		htmlemail.addTo(starterinfo.get("FEMAIL").toString(), starterinfo.get("FNAME").toString());
	    		htmlemail.setSubject("流程驳回提醒");
	    		String msg = "<html><div>Dear "+starterinfo.get("FNAME")+",</div><div style='text-indent:2em;'>您的流程：<span style='font-weight: bold;'>" + processinfo.get("FPROCESSNAME")
							+ "</span>在"+processinfo.get("FTASKNAME")+"节点被驳回，原因为："+processinfo.get("FCOMMENT")+"；请调整后抓紧时间再次提交，谢谢。</div><a href='https://"+GlobalCls.GP.getProperty("server.ip")+":"+GlobalCls.GP.getProperty("server.port")+"/OA/MyWorkFlow/ApprovalPage?taskid="
							+ task.getId() + "'>办理任务</a></html>";
	    		htmlemail.setHtmlMsg(msg);
	    		htmlemail.send();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

}