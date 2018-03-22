package cn.tempus.message;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.tempus.dao.EasyDao;

@Service
public class MessageService {
	
	@Autowired
    private EasyDao basicService;
	
	/**
	 * 发送消息
	 * @param message
	 * @return 数量
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public int sendMessage(Message message){
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("id", message.getId());
		args.put("title", message.getTitle());
		args.put("content", message.getContent());
		args.put("fromuser", message.getFromuser());
		args.put("url", message.getUrl());
		args.put("sendtime", new Date());
		
		StringBuffer sql = new StringBuffer("insert into TB_OA_message (fid,ftitle,fcontent,ffromuser,ftouser,furl,fsendtime,ftype)");
		List<String> tousers = message.getTousers();
		for(int i=0;i<tousers.size();i++){
			sql.append(" select #{id},#{title},#{content},#{fromuser},#{touser"+i+"},#{url},#{sendtime},'workflow' from dual union");
			args.put("touser"+i, tousers.get(i));
		}
		args.put("#SQL", sql.substring(0, sql.length()-6));
		int result = basicService.InsertData(args);
		
		return result;
	}
	
	
	
}


