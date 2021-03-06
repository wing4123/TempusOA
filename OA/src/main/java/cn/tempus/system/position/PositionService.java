package cn.tempus.system.position;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tempus.dao.EasyDao;
import com.alibaba.fastjson.JSONObject;

@Service
public class PositionService {
	
	@Autowired
    private EasyDao basicService;
	
	@Autowired
	HttpServletRequest request;
	
	public Map<String,Object> getRoleInfoById(String id){
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "select fid,fname,fremark from TB_OA_position where fid=#{id}");
		args.put("id", id);
		Map<String,Object> roleinfo = basicService.GetSinglerData(args);
		
		return roleinfo;
	}
	
	public int SavePosition(){
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "insert into TB_OA_position (fid,fname,fremark,fcreator,fcreatetime) values (#{id},#{name},#{remark},#{user},#{time})");
		args.put("id", UUID.randomUUID().toString());
		args.put("name", request.getParameter("name"));
		args.put("remark", request.getParameter("remark"));
		args.put("user", ((JSONObject) JSONObject.toJSON(request.getSession().getAttribute("USER"))).get("userid"));
		args.put("time", new Date());		
		int result=0;
		
		if(request.getParameter("id").equals("")){
			result = basicService.InsertData(args);
		}else{
			args.put("#SQL", "update TB_OA_position set fname=#{name},fremark=#{remark},flastupdator=#{user},flastupdatetime=#{time} where fid=#{id}");
			args.put("id", request.getParameter("id"));
			result = basicService.UpdateData(args);
		}
		
		return result;
	}
	
	public int DeletePosition(String id){
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "delete from TB_OA_position where fid=#{id}");
		args.put("id", id);
		int result = basicService.DeleteData(args);
		return result;
	}
	
	public List<HashMap<String,Object>> SelectPositionList(){
		List<HashMap<String,Object>> result = basicService.SelectListBySql("select fid,fname from TB_OA_position");
		return result;
	}
	
	public List<HashMap<String,Object>> getUserPosition(String id){
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "select fpositionid from TB_OA_userposition where fuserid=#{id}");
		args.put("id", id);
		List<HashMap<String,Object>> result = basicService.SelectListBySqlWithWhere(args);
		return result;
	}
	
}
