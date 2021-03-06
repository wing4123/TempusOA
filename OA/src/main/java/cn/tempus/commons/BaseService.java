package cn.tempus.commons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.alibaba.fastjson.JSONObject;

import cn.tempus.dao.EasyDao;

@Repository
public class BaseService {

	@Autowired
	public JdbcTemplate jdbc;
	
	@Autowired
	public NamedParameterJdbcTemplate namedJdbc;
	
	@Autowired
	public EasyDao easyDao;
	
	@Autowired
	HttpServletRequest request;
	
	/**
	 * 初始化菜单
	 * @param url 当前页面地址
	 */
	public void initmenu(String url){
		String username = ((JSONObject) JSONObject.toJSON(request.getSession().getAttribute("USER"))).getString("name");
		String language = RequestContextUtils.getLocaleResolver(request).resolveLocale(request).toString();
		Map<String,Object> paramMap = new HashMap<String,Object>();
		String sql = "select distinct a.fid,a.fnumber,a.f"+language+" ftext,a.ficon,a.fparentid,a.furl from TB_OA_menu a left join TB_OA_rolemenu b on b.fmenuid=a.fid left join TB_OA_role c on c.fid=b.froleid left join TB_OA_roleuser d on d.froleid=c.fid where d.fuserid=:user";
		if(username.equals("admin")) {
			sql = "select fid,fnumber,f"+language+" ftext,ficon,fparentid,furl from tb_oa_menu";
		}
		
		paramMap.put("language", language);
		paramMap.put("user", ((JSONObject) JSONObject.toJSON(request.getSession().getAttribute("USER"))).getString("userid"));
		
		List<Map<String,Object>> list = namedJdbc.queryForList(sql, paramMap);
		
		Object id = null;
		Object prevMenu = request.getSession().getAttribute("prevMenu");
		for(int i=0;i<list.size();i++){
			if(url.equals(list.get(i).get("FURL")) || (url=="" && prevMenu!=null && prevMenu.toString().equals(list.get(i).get("FURL")))){
				list.get(i).put("selected", true);
				id=list.get(i).get("FPARENTID");
				break;
			}
		}
		request.getSession().setAttribute("prevMenu", url);

		while(id!=null){
			for(int i=0;i<list.size();i++){
				if(list.get(i).get("FID").equals(id)){
					list.get(i).put("selected", true);
					id = list.get(i).get("FPARENTID");
					break;
				}
			}
		}
		
		list = listtotree(list,null);
		
		request.setAttribute("menu", list);
	}
	
	/**
	 * 封装菜单数据
	 * @param list 菜单数据
	 * @param pid 上级菜单id
	 * @return 树形菜单结构数据
	 */
	public static List<Map<String,Object>> listtotree(List<Map<String,Object>> list,String pid){
		List<Map<String,Object>> list0=new ArrayList<Map<String,Object>>();
		for(int i=0;i<list.size();i++){
			String fid=list.get(i).get("FID").toString();
			String fparentid=list.get(i).get("FPARENTID")==null?null:list.get(i).get("FPARENTID").toString();
			HashMap<String,Object> map0=new HashMap<String, Object>();
			map0.put("id",fid);
			map0.put("parentid",fparentid);
			map0.put("number",list.get(i).get("FNUMBER").toString());
			map0.put("text",list.get(i).get("FTEXT").toString());
			map0.put("icon",list.get(i).get("FICON"));
			map0.put("url",list.get(i).get("FURL"));
			map0.put("selected",list.get(i).get("selected"));
			map0.put("children",new ArrayList<HashMap<String,Object>>());
			if((pid==null && fparentid==null) || (fparentid!=null && fparentid.equals(pid))){
				map0.put("children",listtotree(list,fid));
				list0.add(map0);
			}
		}
		return list0;
	}
	
	/**
	 * @param list
	 * @param pid 父级id
	 * @param params 节点属性
	 * @return
	 */
	public List<HashMap<String,Object>> listtotree_ztree(List<HashMap<String,Object>> list,String pid,String[] params){
		Iterator<HashMap<String,Object>> it = list.iterator();
		List<HashMap<String,Object>> list0=new ArrayList<HashMap<String,Object>>();
		while(it.hasNext()){
			HashMap<String,Object> itn = it.next();
			String fid=itn.get("FID").toString();
			String fparentid=itn.get("FPARENTID")==null?null:itn.get("FPARENTID").toString();
			HashMap<String,Object> map0=new HashMap<String, Object>();
			map0.put("id",fid);
			map0.put("name",itn.get("FNAME"));
			if(params!=null){
				for(String p:params){
					map0.put(p,itn.get(p));
				}
			}
			if((pid==null && fparentid==null) || (fparentid!=null && fparentid.equals(pid))){
				it.remove();
				map0.put("children",listtotree_ztree(list,fid,params));
				list0.add(map0);
				it = list.iterator();
			}
		}
		return list0;
	}
	
	/**
	 * @param sql
	 * @param paramMap 
	 * @return DataTable需要的数据格式
	 */
	public Map<String,Object> getDateTableRecord(StringBuffer sql, Map<String,Object> paramMap){
		int start = Integer.parseInt(request.getParameter("start"));
		int length = Integer.parseInt(request.getParameter("length"));
		sql.insert(0, "select rownum rn,t.* from (");
		String orderString="";
		int i=0;
		while(true){
			String col= request.getParameter("order["+i+"][column]"); 			
			if(col==null)break;
			int column=Integer.parseInt(col.toString());
			orderString = ","+request.getParameter("columns["+column+"][data]")+" "+request.getParameter("order["+i+"][dir]"); 
			i++;
		}
		if(!orderString.equals("")){
			sql.append(" order by ").append(orderString.substring(1));
		}
		
		sql.append(") t");
		
		int total = namedJdbc.queryForObject("select count(*) from ("+sql+")", paramMap, Integer.class);
		sql.insert(0, "select rownum seq,tt.* from (").append(") tt where rn>").append(start).append(" and rn<=").append(start+length);
		List<Map<String, Object>> data = namedJdbc.queryForList(sql.toString(), paramMap);
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("data", data);
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		result.put("draw", request.getParameter("draw"));
		
		return result;
	}
	
	public Map<String,Object> getDateTableRecord(Map<String,Object> args){
	
	int start = Integer.parseInt(request.getParameter("start"));
	int length = Integer.parseInt(request.getParameter("length"));
	StringBuffer sql = new StringBuffer("select rownum rn,t.* from ("+args.get("#SQL"));
	String orderString="";
	int i=0;
	while(true){
		String col= request.getParameter("order["+i+"][column]"); 			
		if(col==null)break;
		int column=Integer.parseInt(col.toString());
		orderString = ","+request.getParameter("columns["+column+"][data]")+" "+request.getParameter("order["+i+"][dir]"); 
		i++;
	}
	if(!orderString.equals("")){
		sql.append(" order by ").append(orderString.substring(1));
	}
	
	sql.append(") t");
	
	args.put("#SQL", "select count(*) from ( "+sql+")");
	int total = easyDao.SelectCountBySqlWithWhere(args);
	sql.insert(0, "select rownum seq,tt.* from (").append(") tt where rn>").append(start).append(" and rn<=").append(start+length);
	args.put("#SQL", sql);
	List<HashMap<String, Object>> data = easyDao.SelectListBySqlWithWhere(args);
	Map<String,Object> result = new HashMap<String, Object>();
	result.put("data", data);
	result.put("recordsTotal", total);
	result.put("recordsFiltered", total);
	result.put("draw", request.getParameter("draw"));
	
	return result;
}
	
	/**
	 * @param sql
	 * @param pageStart 第几页， 从1开始
	 * @param pageSize  每页数据
	 * @return 返回拼接后的sql
	 */
	public String BuildPageSql(String sql,int pageStart,int pageSize){
		return "select * from ( select rownum r,t_.* from ("+sql+" ) t_ ) where r>="+pageStart+" and r<"+(pageStart+pageSize);
	}
	
}
