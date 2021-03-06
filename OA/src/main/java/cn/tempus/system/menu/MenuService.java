package cn.tempus.system.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tempus.dao.EasyDao;

@Service
public class MenuService {
	
	@Autowired
    private EasyDao basicService;
	
	@Autowired
	HttpServletRequest request;
	
	public List<HashMap<String,Object>> getMenuTree(String language){
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "select fid,fnumber,f"+language+" ftext,ficon,fparentid from TB_OA_menu ");
		args.put("language", language);
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
			map0.put("parentid",fparentid);
			map0.put("number",list.get(i).get("FNUMBER").toString());
			map0.put("text",list.get(i).get("FTEXT").toString());
			map0.put("icon",list.get(i).get("FICON"));
			map0.put("url",list.get(i).get("FURL"));
			map0.put("children",new ArrayList<HashMap<String,Object>>());
			if((pid==null && fparentid==null) || (fparentid!=null && fparentid.equals(pid))){
				map0.put("children",listtotree(list,fid));
				list0.add(map0);
			}
		}
		return list0;
	}
	
	@SuppressWarnings("unchecked")
	public int SaveMenu(){
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("#SQL", "insert into TB_OA_menu (fid,fparentid,fnumber,fzh_cn,fen_us,ficon,furl,fcreator,fcreatetime) values (#{id},#{parentid},#{number},#{zh_CN},#{en_US},#{icon},#{url},#{user},#{time})");
		args.put("id", UUID.randomUUID().toString());
		args.put("parentid", request.getParameter("parentmenu"));
		args.put("zh_CN", request.getParameter("zh_CN"));
		args.put("en_US", request.getParameter("en_US"));
		args.put("icon", request.getParameter("icon"));
		args.put("url", request.getParameter("url"));
		args.put("user", ((Map<String,Object>)request.getSession().getAttribute("USER")).get("userid"));
		args.put("time", new Date());
		
		int result=0;
		
		if(request.getParameter("id").equals("")){
			String parentid = request.getParameter("parentmenu");
			Object number = basicService.GetFirstValueBySql("select fnumber from TB_OA_menu where fid='"+parentid+"'");
			if(number!=null){
				number = number + "." + basicService.GetFirstValueBySql("select count(*)+1 fnumber from TB_OA_menu  where fparentid='"+parentid+"'");
			}else{
				number = basicService.GetFirstValueBySql("select count(*)+1 fnumber from TB_OA_menu  where fparentid is null");
			}
			args.put("number", number);
			result = basicService.InsertData(args);
		}else{
			args.put("#SQL", "update TB_OA_menu set fparentid=#{parentid},fzh_cn=#{zh_CN},fen_us=#{en_US},ficon=#{icon},furl=#{url},fupdator=#{user},fupdatetime=#{time} where fid=#{id}");
			args.put("id", request.getParameter("id"));
			result = basicService.UpdateData(args);
		}
		
		return result;
	}

}
