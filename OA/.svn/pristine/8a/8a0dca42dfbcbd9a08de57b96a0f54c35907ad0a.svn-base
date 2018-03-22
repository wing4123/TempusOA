package cn.tempus.email;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.stereotype.Component;

@Component
public class Email {
	private final String account = "TempusHoldITAlert@tempus.cn";
	
	private final String password = "tempus.oa";
	
	private final String host = "email.tempus.cn";

	//发送html邮件
	public HtmlEmail getHtmlEmail() throws EmailException {
		HtmlEmail email = new HtmlEmail();
		email.setCharset("UTF-8");
		email.setHostName(host);
		email.setAuthentication(account, password);
		email.setFrom(account);
		  
		return email;
	}
	
}
