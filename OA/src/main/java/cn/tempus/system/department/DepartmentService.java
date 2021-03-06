package cn.tempus.system.department;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tempus.commons.BaseService;
import cn.tempus.dao.EasyDao;
import com.alibaba.fastjson.JSONObject;

@Service
public class DepartmentService extends BaseService{
	
	@Autowired
	HttpServletRequest request;
	
	public List<HashMap<String,Object>> getDepartmentTree(){
		List<HashMap<String,Object>> list = easyDao.SelectListBySql("select fid,fparentid,fnumber,fname from TB_OA_department order by fnumber");
		List<HashMap<String, Object>> departmenttree = easyDao.listtotree(list, null, new String[] {});
		return departmenttree;
	}
	
	public List<HashMap<String,Object>> getParentDepartmentTree(String id){
		String sql = id.equals("")?"select fid,fparentid,fnumber,fname from TB_OA_department":"select fid,fparentid,fnumber,fname from TB_OA_department where fnumber not like (select fnumber from TB_OA_department where fid='"+id+"')||'%'";
		List<HashMap<String,Object>> list = easyDao.SelectListBySql(sql);
		List<HashMap<String, Object>> departmenttree = easyDao.listtotree(list, null, new String[] {});
		return departmenttree;
	}
	
	public void deletechildrendept(List<HashMap<String,Object>> list,Object id){
		for(int i=0;i<list.size();i++){
			if(list.get(i).get("FID").equals(id)){
				Object pid = list.get(i).get("FPARENTID");
				list.remove(i);
				deletechildrendept(list, pid);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public int SaveDepartment(){
		String parentid = request.getParameter("parentid");
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "insert into TB_OA_department (fid,fparentid,fnumber,fname,flongname,fleader,ftype,fremark,fcreator,fcreatetime) values (#{id},#{parentid},("+(parentid.equals("")?"select nvl(max(to_number(fnumber)),0)+1 from TB_OA_department where fparentid is null":"select max(a.fnumber)||'.'||(nvl(max(to_number(substr(b.fnumber,length(a.fnumber)+2))),0)+1) from TB_OA_department a left join TB_OA_department b on b.fparentid=a.fid where a.fid=#{parentid}")+"),#{name},decode(#{parentid},'',#{name},(select flongname||' - '||#{name} from tb_oa_department where fid=#{parentid})),#{leader},#{type},#{remark},#{user},#{time})");
		args.put("id", UUID.randomUUID().toString());
		args.put("parentid", request.getParameter("parentid"));
		args.put("name", request.getParameter("name"));
		args.put("leader", request.getParameter("leader"));
		args.put("type", request.getParameter("type"));
		args.put("remark", request.getParameter("remark"));
		args.put("user", ((Map<String,Object>)request.getSession().getAttribute("USER")).get("userid"));
		args.put("time", new Date());
		
		int result=0;
		
		if(request.getParameter("id").equals("")){
			result = easyDao.InsertData(args);
		}else{
			args.put("#SQL", "update TB_OA_department set fparentid=#{parentid},fname=#{name},flongname=decode(#{parentid},'',#{name},(select flongname||' - '||#{name} from tb_oa_department where fid=#{parentid})),fleader=#{leader},ftype=#{type},fremark=#{remark},flastupdator=#{user},flastupdatetime=#{time} where fid=#{id}");
			args.put("id", request.getParameter("id"));
			result = easyDao.UpdateData(args);
		}
		
		return result;
	}
	
	public List<Map<String,Object>> getDivisionTree(){
		List<Map<String,Object>> list = jdbc.queryForList("select fid,fparentid,fnumber,fname from TB_OA_division order by fnumber");
		List<Map<String, Object>> divisiontree = easyDao.listtotree_ztree(list, null, new String[] {"FNUMBER"});
		return divisiontree;
	}
	
	public List<Map<String,Object>> getParentDivisionTree(String id){
		String sql = id.equals("")?"select fid,fparentid,fnumber,fname from TB_OA_division order by fnumber":"select fid,fparentid,fnumber,fname from TB_OA_division where fnumber not like (select fnumber from TB_OA_division where fid='"+id+"')||'%' order by fnumber";
		List<Map<String,Object>> list = jdbc.queryForList(sql);
//		if(!id.equals("")){
//			while(id!=null){
//				for(int i=0;i<list.size();i++){
//					Object fid = list.get(i).get("FID");
//					if(id.equals(list.get(i).get("FID")) || id.equals(list.get(i).get("FPARENTID"))){
//						list.remove(i);
//						id=fid==null?null:fid.toString();
//						break;
//					}else if(i==list.size()-1){
//						id=null;
//					}
//				}
//			}
//		}
		List<Map<String, Object>> departmenttree = easyDao.listtotree_ztree(list, null, new String[] {});
		return departmenttree;
	}
	
	public int SaveDivision(){
		String parentid = request.getParameter("parentid");
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "insert into TB_OA_division (fid,fparentid,fnumber,fname,flongname,fleader,flevel,fremark,fcreator,fcreatetime) values (#{id},#{parentid},("+(parentid.equals("")?"select nvl(max(to_number(fnumber)),0)+1 from TB_OA_DIVISION where fparentid is null":"select max(a.fnumber)||'.'||(nvl(max(to_number(substr(b.fnumber,length(a.fnumber)+2))),0)+1) from TB_OA_division a left join TB_OA_division b on b.fparentid=a.fid where a.fid=#{parentid}")+"),#{name},decode(#{parentid},'',#{name},(select flongname||' - '||#{name} from tb_oa_division where fid=#{parentid})),#{leader},#{level},#{remark},#{user},#{time})");
		args.put("id", UUID.randomUUID().toString());
		args.put("parentid", request.getParameter("parentid"));
		args.put("name", request.getParameter("name"));
		args.put("leader", request.getParameter("leader"));
		args.put("level", request.getParameter("level"));
		args.put("remark", request.getParameter("remark"));
		args.put("user", ((JSONObject) JSONObject.toJSON(request.getSession().getAttribute("USER"))).getString("userid"));
		args.put("time", new Date());
		
		int result=0;
		
		if(request.getParameter("id").equals("")){
			result = easyDao.InsertData(args);
		}else{
			args.put("#SQL", "update TB_OA_division set fparentid=#{parentid},fname=#{name},flongname=decode(#{parentid},'',#{name},(select flongname||' - '||#{name} from tb_oa_division where fid=#{parentid})),fleader=#{leader},flevel=#{level},fremark=#{remark},flastupdator=#{user},flastupdatetime=#{time} where fid=#{id}");
			args.put("id", request.getParameter("id"));
			result = easyDao.UpdateData(args);
		}
		
		return result;
	}
	
}
