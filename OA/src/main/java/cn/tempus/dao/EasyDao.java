package cn.tempus.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EasyDao {
	
	public HashMap<String,Object> GetSinglerData(String sql){
		List< HashMap<String,Object> > list=SelectListBySql(sql);
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	public HashMap<String,Object> GetSinglerData(Map<String, Object> args){
		List< HashMap<String,Object> > list=SelectListBySqlWithWhere(args);
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	public HashMap<String,Object> SelectKVMap(String sql,String keyName,String valueName){
		List< HashMap<String,Object> > list=SelectListBySql(sql);
		HashMap<String,Object> map=new HashMap<String, Object>();
		for(HashMap<String,Object> item:list){
			map.put(item.get(keyName).toString(), item.get(valueName));
		}
		return map;
	}
	
	public HashMap<String,Object> SelectKVMap(Map<String, Object> args,String keyName,String valueName){
		List< HashMap<String,Object> > list=SelectListBySqlWithWhere(args);
		HashMap<String,Object> map=new HashMap<String, Object>();
		for(HashMap<String,Object> item:list){
			map.put(item.get(keyName).toString(), item.get(valueName));
		}
		return map;
	}
	
	public HashMap<String,HashMap<String,Object>>  SelectKVMapTable(String sql,String keyName){
		List< HashMap<String,Object> > list=SelectListBySql(sql);
		HashMap<String,HashMap<String,Object>> map=new HashMap<String, HashMap<String,Object>>();
		for(HashMap<String,Object> item:list){
			map.put(item.get(keyName).toString(), item);
		}
		return map;
	}
	
	public HashMap<String,HashMap<String,Object>>  SelectKVMapTable(Map<String, Object> args,String keyName){
		List< HashMap<String,Object> > list=SelectListBySqlWithWhere(args);
		HashMap<String,HashMap<String,Object>> map=new HashMap<String, HashMap<String,Object>>();
		for(HashMap<String,Object> item:list){
			map.put(item.get(keyName).toString(), item);
		}
		return map;
	}
	
	
	public List< HashMap<String,Object> > GetAllData(String tablename){
		return basicDao.GetAllData(tablename);
	}
	public int GetAllDataCount(String tablename){
		return basicDao.GetAllDataCount(tablename);
	}
	public List< HashMap<String,Object> > GetAllDataWithWhere(Map<String, Object> args){
		return basicDao.GetAllDataWithWhere(args);
	}
	public List< HashMap<String,Object> > SelectListBySql(String sql){
		return basicDao.SelectListBySql(sql);
	}
	public int SelectCountBySql(String sql){
		return basicDao.SelectCountBySql(sql);
	}
	public List< HashMap<String,Object> > SelectListBySqlWithWhere(Map<String, Object> args){
		return basicDao.SelectListBySqlWithWhere(args);
	}
	public List< LinkedHashMap<String,Object> > SelectListBySqlWithWhere2(Map<String, Object> args){
		return basicDao.SelectListBySqlWithWhere2(args);
	}
	public int SelectCountBySqlWithWhere(Map<String, Object> args){	
		return basicDao.SelectCountBySqlWithWhere(args);
	}
	public int InsertData(Map<String, Object> args){
		return basicDao.InsertData(args);
	}
	public int UpdateData(Map<String, Object> args){
		return basicDao.UpdateData(args);
	}
	public int DeleteData(Map<String, Object> args){
		return basicDao.DeleteData(args);
	}
	
	public Object GetFirstValueBySql(String sql){
		List< HashMap<String,Object> > list=SelectListBySql(sql);
		if(list.size()>0){
			if(list.get(0)==null)return null;
			return list.get(0).values().toArray()[0];
		}else{
			return null;
		}
		 
	}
	
	public Object GetFirstValueBySqlWithWhere(Map<String, Object> args){
		List< HashMap<String,Object> > list=SelectListBySqlWithWhere(args);
		if(list.size()>0){
			if(list.get(0)==null)return null;
			return list.get(0).values().toArray()[0];
		}else{
			return null;
		}
	}
	
	public String BuildPageSql(String sqlandorder,int ipageStart,int ipagesize){
		return "select * from ( select rownum r,t_.* from ("+sqlandorder+" ) t_ ) where r>"+ipageStart+" and r<="+(ipageStart+ipagesize);
	}
	
	public List<HashMap<String,Object>> listtotree(List<HashMap<String,Object>> list,String pid,String[] params){
		List<HashMap<String,Object>> list0=new ArrayList<HashMap<String,Object>>();
		for(int i=0;i<list.size();i++){
			String fid=list.get(i).get("FID").toString();
			String fparentid=list.get(i).get("FPARENTID")==null?null:list.get(i).get("FPARENTID").toString();
			HashMap<String,Object> map0=new HashMap<String, Object>();
			map0.put("id",fid);
			map0.put("text",list.get(i).get("FNAME"));
			Map<String,Object> attribute = new HashMap<String,Object>();
			attribute.put("parentid",fparentid);
			attribute.put("number",list.get(i).get("FNUMBER"));
			map0.put("a_attr", attribute);
			for(String p:params){
				attribute.put(p,list.get(i).get(p));
			}
			map0.put("children",new ArrayList<HashMap<String,Object>>());
			if((pid==null && fparentid==null) || (fparentid!=null && fparentid.equals(pid))){
				//list.remove(i);
				//i=i-1;
				map0.put("children",listtotree(list,fid,params));
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
/*	public List<HashMap<String,Object>> listtotree_ztree(List<HashMap<String,Object>> list,String pid,String[] params){
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
	}*/
	
	public List<Map<String,Object>> listtotree_ztree(List<Map<String,Object>> list,String pid,String ... params){
		Iterator<Map<String,Object>> it = list.iterator();
		List<Map<String,Object>> list0=new ArrayList<Map<String,Object>>();
		while(it.hasNext()){
			Map<String, Object> itn = it.next();
			String fid=itn.get("FID").toString();
			String fparentid=itn.get("FPARENTID")==null?null:itn.get("FPARENTID").toString();
			Map<String,Object> map0=new HashMap<String, Object>();
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
	
	@Autowired
	private BasicDaoI basicDao; 
}
