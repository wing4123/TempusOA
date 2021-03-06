package cn.tempus.system.permission;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.alibaba.fastjson.JSONObject;

import cn.tempus.dao.EasyDao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

@Service
public class PermissionService {
	
	@Autowired
    private JdbcTemplate jdbc;
	
	@Autowired
	private EasyDao basicService;
	
	@Autowired
	HttpServletRequest request;
	
	public Map<String,Object> getRoleInfoById(String id){
		String sql = "select fid,fname,fremark from TB_OA_role where fid=:id";
		Map<String,Object> roleinfo = jdbc.queryForMap(sql,id);
		
		return roleinfo;
	}
	
	public List<HashMap<String,Object>> getRoleUserList(String roleid){
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "select a.user_id fid,a.user_name fname,a.user_code fcode,case when b.fuserid is null then 0 else 1 end as fselected from tb_user a left join TB_OA_roleuser b on b.fuserid=a.user_id and b.froleid=#{roleid} where sysdate between start_active_date and NVL(end_active_date,sysdate) and allow_login_flag='Y' ");
		args.put("roleid", roleid);
		return basicService.SelectListBySqlWithWhere(args);
	}
	
	@SuppressWarnings("unchecked")
	public int SaveRole(){
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "insert into TB_OA_role (fid,fname,fremark,fcreator,fcreatetime) values (#{id},#{name},#{remark},#{user},#{time})");
		args.put("id", UUID.randomUUID().toString());
		args.put("name", request.getParameter("name"));
		args.put("remark", request.getParameter("remark"));
		args.put("user", ((Map<String,Object>)request.getSession().getAttribute("USER")).get("userid"));
		args.put("time", new Date());
		int result=0;
		
		if(request.getParameter("id").equals("")){
			result = basicService.InsertData(args);
		}else{
			args.put("#SQL", "update TB_OA_role set fname=#{name},fremark=#{remark} where fid=#{id}");
			args.put("id", request.getParameter("id"));
			result = basicService.UpdateData(args);
		}
		
		return result;
	}
	
	@Transactional
	public int DeleteRole(String id){
		String sql = "delete from tb_oa_role where fid=?";
		jdbc.update(sql, id);
		sql = "delete from tb_oa_roleuser where froleid=?";
		jdbc.update(sql, id);
		sql = "delete from tb_oa_rolemenu where froleid=?";
		jdbc.update(sql, id);
		sql = "delete from tb_oa_roleprocess where froleid=?";
		jdbc.update(sql, id);
		sql = "delete from tb_oa_role_org where froleid=?";
		jdbc.update(sql, id);
		
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.REQUIRED) 
	public int SaveRoleMenu(){
		String[] menus = request.getParameterValues("menus[]");
		String roleid = request.getParameter("roleid");
		
		Map<String,Object> delete = new HashMap<String,Object>();
		delete.put("#SQL", "delete from TB_OA_rolemenu where froleid=#{roleid}");
		delete.put("roleid", roleid);
		basicService.DeleteData(delete);
		
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("roleid", roleid);
		args.put("user", ((Map<String,Object>)request.getSession().getAttribute("USER")).get("userid"));
		args.put("time", new Date());
		StringBuffer sql = new StringBuffer("insert into TB_OA_rolemenu ");
		for(int i=0;i<menus.length;i++){
			sql.append("select #{id"+i+"},#{roleid},#{menuid"+i+"},#{user},#{time} from dual union ");
			args.put("id"+i, UUID.randomUUID().toString());
			args.put("menuid"+i, menus[i]);
		}
		args.put("#SQL", sql.substring(0,sql.length()-7));
		
		int result = basicService.InsertData(args);
		
		return result;
	}
	
	public List<HashMap<String,Object>> getRoleMenuState(String language,String roleid){
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "select a.fid,a.f"+language+" ftext,a.ficon,a.fparentid,b.fid bid from TB_OA_menu a left join TB_OA_rolemenu b on b.fmenuid=a.fid and b.froleid=#{roleid}");
		args.put("language", language);
		args.put("roleid", roleid);
		List<HashMap<String,Object>> list = basicService.SelectListBySqlWithWhere(args);
		
		return listtotree(list,null);
	}
	
	public static List<HashMap<String,Object>> listtotree(List<HashMap<String,Object>> list,String pid){
		List<HashMap<String,Object>> list0=new ArrayList<HashMap<String,Object>>();
		for(int i=0;i<list.size();i++){
			String fid=list.get(i).get("FID").toString();
			String fparentid=list.get(i).get("FPARENTID")==null?null:list.get(i).get("FPARENTID").toString();
			HashMap<String,Object> map0=new HashMap<String, Object>();
			map0.put("id",fid);
			map0.put("name",list.get(i).get("FTEXT").toString());
			//map0.put("icon",list.get(i).get("FICON"));
			map0.put("checked", list.get(i).get("BID")!=null);
			map0.put("children",new ArrayList<HashMap<String,Object>>());
			if((pid==null && fparentid==null) || (fparentid!=null && fparentid.equals(pid))){
//				list.remove(i);
//				i=i-1;
				map0.put("children",listtotree(list,fid));
				list0.add(map0);
			}
		}
		return list0;
	}
	
	public List<HashMap<String,Object>> getUserList(String search,String roleid){
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("roleid", roleid);
		StringBuffer sql = new StringBuffer("select a.user_id,a.user_code,a.user_name,decode(b.fuserid,null,'\"\"','checked=\"checked\"') checked from tb_user a left join TB_OA_roleuser b on b.fuserid=a.user_id and b.froleid=#{roleid}");
		if(search!=""){
			sql.append(" where user_code like #{search} or user_name like #{search}");
			args.put("search", "%"+search+"%");
		}
		args.put("#SQL", sql);
		
		List<HashMap<String,Object>> list = basicService.SelectListBySqlWithWhere(args);
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.REQUIRED) 
	public int SaveRoleUser(){
		String[] users = request.getParameterValues("users[]");
		String roleid = request.getParameter("roleid");
		
		Map<String,Object> delete = new HashMap<String,Object>();
		delete.put("#SQL", "delete from TB_OA_roleuser where froleid=#{roleid}");
		delete.put("roleid", roleid);
		basicService.DeleteData(delete);
		
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("roleid", roleid);
		args.put("user", ((Map<String,Object>)request.getSession().getAttribute("USER")).get("userid"));
		args.put("time", new Date());
		StringBuffer sql = new StringBuffer("insert into TB_OA_roleuser ");
		for(int i=0;i<users.length;i++){
			sql.append("select #{id"+i+"},#{roleid},#{userid"+i+"},#{user},#{time} from dual union ");
			args.put("id"+i, UUID.randomUUID().toString());
			args.put("userid"+i, users[i]);
		}
		args.put("#SQL", sql.substring(0,sql.length()-7));
		
		int result = basicService.InsertData(args);
		
		return result;
	}
	
	public List<HashMap<String,Object>> getUserPermission(String userid){
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "select a.fid,a.fuserid,b.fname,b.fnumber from TB_OA_userpermission a left join TB_OA_division b on b.fid=a.fdivisionid where a.fuserid=#{userid}");
		args.put("userid", userid);
		
		List<HashMap<String,Object>> list = basicService.SelectListBySqlWithWhere(args);
		
		return list;
	}
	
	public int deleteUserPermission(String id){
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "delete from TB_OA_userpermission where fid=#{id}");
		args.put("id", id);
		
		int result = basicService.DeleteData(args);
		
		return result;
	}
	
	public int addUserPermission(String userid,String divisionid){
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "insert into TB_OA_userpermission values (#{id},#{userid},#{divisionid})");
		args.put("id", UUID.randomUUID().toString());
		args.put("userid", userid);
		args.put("divisionid", divisionid);
		
		int result = basicService.InsertData(args);
		
		return result;
	}
	
	public int addUser() {
		String roleid = request.getParameter("roleid");
		String[] ids = request.getParameterValues("ids[]");
		
		String sql = "insert into tb_oa_roleuser (fid,froleid,fuserid,fcreator,fcreatetime) values (?,?,?,?,sysdate)";
		BatchPreparedStatementSetter pss = new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, UUID.randomUUID().toString());
				ps.setString(2, roleid);
				ps.setString(3, ids[i]);
				ps.setString(4, ((JSONObject)JSONObject.toJSON(request.getSession().getAttribute("USER"))).getString("userid"));
			}
			
			@Override
			public int getBatchSize() {
				return ids.length;
			}
		};
		
		int[] result = jdbc.batchUpdate(sql, pss);
		
		return result.length;
	}
	
	public int deleteUser(){
		String[] ids = request.getParameterValues("ids[]");
		
		String sql = "delete from tb_oa_roleuser where fid = ?";
		BatchPreparedStatementSetter pss = new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, ids[i]);
			}
			
			@Override
			public int getBatchSize() {
				return ids==null?0:ids.length;
			}
		};
		int[] result = jdbc.batchUpdate(sql, pss);
		
		return result.length;
	}
	
	public List<Map<String,Object>> getRoleMenu(String roleid){
		String language = RequestContextUtils.getLocaleResolver(request).resolveLocale(request).toString();
		List<Map<String,Object>> list = jdbc.queryForList("select a.fid,a.fparentid,a.fnumber,a.f"+language+" fname,'true' \"open\" from tb_oa_menu a left join tb_oa_rolemenu b on b.fmenuid=a.fid where b.froleid=?", roleid);
		List<Map<String, Object>> roleMenu = basicService.listtotree_ztree(list, null, "open");
		
		return roleMenu;
	}
	
	public List<Map<String,Object>> getModalRoleMenu(String roleid){
		return null;
	}
	
	public int addProcess() {
		String roleid = request.getParameter("roleid");
		String[] ids = request.getParameterValues("ids[]");
		
		String sql = "insert into tb_oa_roleprocess (fid,froleid,fprocessid,fcreator,fcreatetime) values (?,?,?,?,sysdate)";
		BatchPreparedStatementSetter pss = new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, UUID.randomUUID().toString());
				ps.setString(2, roleid);
				ps.setInt(3, Integer.parseInt(ids[i]));
				ps.setString(4, ((JSONObject)JSONObject.toJSON(request.getSession().getAttribute("USER"))).getString("userid"));
			}
			
			@Override
			public int getBatchSize() {
				return ids.length;
			}
		};
		
		int[] result = jdbc.batchUpdate(sql, pss);
		
		return result.length;
	}
	
	public int deleteProcess(){
		String[] ids = request.getParameterValues("ids[]");
		
		String sql = "delete from tb_oa_roleprocess where fid = ?";
		BatchPreparedStatementSetter pss = new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, ids[i]);
			}
			
			@Override
			public int getBatchSize() {
				return ids==null?0:ids.length;
			}
		};
		int[] result = jdbc.batchUpdate(sql, pss);
		
		return result.length;
	}
	
	public int addOrg() {
		String roleid = request.getParameter("roleid");
		String orgType = request.getParameter("orgType");
		String[] ids = request.getParameterValues("ids[]");
		
		String sql = "insert into tb_oa_role_org (fid,froleid,forgid,forgtype,fcreator,fcreatetime) values (?,?,?,?,?,sysdate)";
		BatchPreparedStatementSetter pss = new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, UUID.randomUUID().toString());
				ps.setString(2, roleid);
				ps.setString(3, ids[i]);
				ps.setString(4, orgType);
				ps.setString(5, ((JSONObject)JSONObject.toJSON(request.getSession().getAttribute("USER"))).getString("userid"));
			}
			
			@Override
			public int getBatchSize() {
				return ids.length;
			}
		};
		
		int[] result = jdbc.batchUpdate(sql, pss);
		
		return result.length;
	}
	
	public int deleteOrg(){
		String[] ids = request.getParameterValues("ids[]");
		
		String sql = "delete from tb_oa_role_org where fid = ?";
		BatchPreparedStatementSetter pss = new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, ids[i]);
			}
			
			@Override
			public int getBatchSize() {
				return ids==null?0:ids.length;
			}
		};
		int[] result = jdbc.batchUpdate(sql, pss);
		
		return result.length;
	}

}
