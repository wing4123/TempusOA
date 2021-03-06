package cn.tempus.system.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.tempus.dao.EasyDao;

@Service
public class UserService {
	
	@Autowired
    private EasyDao basicService;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	HttpSession session;
	
	public Map<String,Object> getUserList(){
		int start = Integer.parseInt(request.getParameter("start"));
		int length = Integer.parseInt(request.getParameter("length"));
		String sql = "select rownum rn,t.* from (select user_id,user_code,user_name,to_char(start_active_date,'yyyy-mm-dd') start_active_date from tb_user order by user_code) t";
		int total = basicService.SelectCountBySql("select count(*) from("+sql+")");
		List<HashMap<String, Object>> data = basicService.SelectListBySql("select * from ("+sql+") where rn>"+start+" and rn<"+(start+length));
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("data", data);
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		result.put("draw", request.getParameter("draw"));
		
		return result;
	}
	
	public Map<String,Object> getUserInfoById(String id){
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "select a.user_id,a.user_code,a.user_name,a.CONTACT_TEL_NUM,a.EMAIL_ADDRESS,to_char(a.DATE_OF_BIRTH,'yyyy-mm-dd') DATE_OF_BIRTH,b.fid fdepartmentid,b.fname fdepartmentname,c.user_id fleaderid,c.user_name fleadername,d.fid fdivisionid,d.fname fdivisionname from tb_user a left join TB_OA_department b on b.fid=a.attribute15 left join tb_user c on c.user_id=a.attribute14 left join TB_OA_division d on d.fid=a.attribute13 where a.user_id=#{id}");
		args.put("id", id);
		Map<String,Object> userinfo = basicService.GetSinglerData(args);
		
		//args.put("#SQL", "select a.fid,a.fname,decode(c.positionid,null,'','selected') fselected from TB_OA_position a left join TB_OA_userposition b on b.fpositionid=a.fid and b.fuserid=#{id}");
		args.put("#SQL", "select fpositionid from TB_OA_userposition where fuserid=#{id}");
		userinfo.put("position", basicService.SelectListBySqlWithWhere(args));
		
		return userinfo;
	}
	
	public int SaveUser(){
		JSONObject user = (JSONObject) JSONObject.toJSON(request.getSession().getAttribute("USER"));
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "insert into tb_user (user_id,user_code,user_name,date_of_birth,contact_tel_num,email_address,attribute15,attribute14,attribute13,created_by,creation_date,last_updated_by,last_update_date,password) "
				+ "values(#{user_id},#{user_code},#{user_name},to_date(#{user_birthday},'yyyy-mm-dd'),#{user_phone},#{user_email},#{user_departmentid},#{user_leaderid},#{user_divisionid},#{user_creator},sysdate,#{user_creator},sysdate,'ba59abbe56e')");
		args.put("user_id", System.currentTimeMillis());
		args.put("user_code", request.getParameter("user_code"));
		args.put("user_name", request.getParameter("user_name"));
		args.put("user_birthday", request.getParameter("user_birthday"));
		args.put("user_phone", request.getParameter("user_phone"));
		args.put("user_email", request.getParameter("user_email"));
		args.put("user_departmentid", request.getParameter("user_departmentid"));
		args.put("user_divisionid", request.getParameter("user_divisionid"));
		args.put("user_leaderid", request.getParameter("user_leaderid"));
		args.put("user_creator", user.get("userid"));
		
		int result=0;
		
		if(StringUtils.isBlank(request.getParameter("user_id"))){
			result = basicService.InsertData(args);
		}else{
			args.put("#SQL", "update tb_user set user_code=#{user_code},user_name=#{user_name},DATE_OF_BIRTH=to_date(#{user_birthday},'yyyy-mm-dd'),CONTACT_TEL_NUM=#{user_phone},EMAIL_ADDRESS=#{user_email},ATTRIBUTE15=#{user_departmentid},attribute14=#{user_leaderid},attribute13=#{user_divisionid} where user_id=#{user_id}");
			args.put("user_id", request.getParameter("user_id"));
			result = basicService.UpdateData(args);
		}
		
		args.put("#SQL", "delete from TB_OA_userposition where fuserid=#{user_id}");
		basicService.DeleteData(args);
		String position = request.getParameter("user_position");
		String[] positions = request.getParameterValues("user_position[]");

		if(position!=null){
			args.put("#SQL", "insert into TB_OA_userposition values (#{user_id},#{position})");
			args.put("position", position);
		}else if(positions!=null){
			StringBuffer sql = new StringBuffer("insert into TB_OA_userposition ");
			for(int i=0;i<positions.length;i++){
				sql.append("select #{user_id} fuserid,#{positions["+i+"]} fposition from dual union ");
			}
			args.put("positions", positions);
			sql.delete(sql.length()-6, sql.length());
			args.put("#SQL", sql);
		}
		if(position!=null || positions!=null){
			basicService.InsertData(args);
		}
		
		return result;
	}
	
	public int DeleteUser(String id){
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "delete");
		args.put("id", id);
		int result = basicService.DeleteData(args);
		return result;
	}

}
