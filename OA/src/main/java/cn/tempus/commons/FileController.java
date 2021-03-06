package cn.tempus.commons;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import net.lingala.zip4j.exception.ZipException;

/** 
* @author 吴中贤 wing4123@163.com
* @date 2017年7月12日
* @Description: 文件上传下载
*  
*/

@Controller
@RequestScope
@RequestMapping("/file")
public class FileController {
	
	@Autowired
	FileService fileservice;
	
	@Autowired
	BaseService baseService;
	
	@Autowired
	HttpServletRequest request;
	
    @RequestMapping("/upload")
    public void uploadone(@RequestParam("file") CommonsMultipartFile[] files, HttpServletRequest request, HttpServletResponse response) throws IOException {
    	fileservice.SaveFile(files,request);
    	response.getWriter().print(true);
    }
	
    @RequestMapping("/uploadmulti")
    public void upload( HttpServletResponse response) throws IOException {
    	 CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(request.getSession().getServletContext());
         //检查form中是否有enctype="multipart/form-data"
         if(multipartResolver.isMultipart(request)){
             //将request变成多部分request
             MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
            //获取multiRequest 中所有的文件名
             Iterator<String> iter=multiRequest.getFileNames();
             InputStream is = this.getClass().getClassLoader().getResourceAsStream("jdbc.properties");
 			 Properties p = new Properties();
 			 p.load(is);
 			 String filedir = p.getProperty("file.dir")+"/"+new SimpleDateFormat("yyyyMM").format(new Date());
 			 File dir = new File(filedir);
 			 if(!dir.exists()){
 				 dir.mkdirs();
 			 }
             while(iter.hasNext()){
                 MultipartFile file=multiRequest.getFile(iter.next().toString());
                 if(file!=null){
                     file.transferTo(new File(filedir+"/"+UUID.randomUUID().toString()));
                 }
             }
         }
    }
    
    @RequestMapping("/DeleteFileById")
    @ResponseBody
    public int DeleteFileById() throws IOException {
    	String id = request.getParameter("id");
    	int result = fileservice.DeleteFileById(id);
    	return result;
    }
    
    @RequestMapping("/DeleteFileByBid")
    @ResponseBody
    public int DeleteFileByBid() throws IOException {
    	String bid = request.getParameter("bid");
    	String btype = request.getParameter("btype");
    	int result = fileservice.DeleteFileByBid(bid,btype);
    	return result;
    }
    
    @RequestMapping("/getFileList")
    @ResponseBody
    public Map<String, Object> getFileList() throws IOException {
    	String bid = request.getParameter("bid");
    	String btype = request.getParameter("btype");
		Map<String,Object> args = new HashMap<String,Object>();
		StringBuffer sql = new StringBuffer("select a.*,b.user_name fcreatorname from TB_OA_fileinfo a left join tb_user b on b.user_id=a.fcreator where a.fbusinessid=#{bid}");
		if(btype==null){
			sql.append(" and fbusinesstype is null");
		}else{
			sql.append(" and fbusinesstype=#{btype}");
			args.put("btype", btype);
		}
		args.put("bid", bid);
		args.put("#SQL", sql);
		Map<String,Object> result = baseService.getDateTableRecord(args);
		
		return result;
    }
    
    
    /**
     * 下载附件
     */
    @RequestMapping("download")    
    public ResponseEntity<byte[]> download(@RequestParam String id) throws IOException {
    	Map<String,Object> args = new HashMap<String,Object>();
    	args.put("#SQL", "select * from TB_OA_fileinfo where fid=#{id}");
    	args.put("id", id);
    	HashMap<String, Object> fileinfo = baseService.easyDao.GetSinglerData(args);
        
        File file=new File(GlobalCls.GP.getProperty("file.dir")+"/"+fileinfo.get("FPATH"));
        String fileName = fileinfo.get("FNAME").toString();
        
		String agent = request.getHeader("USER-AGENT");
		if (null != agent && -1 != agent.indexOf("MSIE") || null != agent && -1 != agent.indexOf("Trident")) {// ie
			fileName = URLEncoder.encode(fileName, "UTF8");
		} else if (null != agent && -1 != agent.indexOf("Mozilla")) {// 火狐,chrome等
			fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
		}

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.OK);
    }
    
    /**
     * 打包下载附件
     * @throws ZipException 
     */
    @RequestMapping("package/{bid}")
    public ResponseEntity<byte[]> downloadPackage(@PathVariable String bid) throws IOException, ZipException {
    	return fileservice.downloadPackage(bid);
    }
    
    @RequestMapping("ViewImage")    
    public void ViewImage(HttpServletResponse response) throws IOException{
    	String id = request.getParameter("id");
    	
    	Map<String,Object> args = new HashMap<String,Object>();
    	args.put("#SQL", "select * from TB_OA_fileinfo where fid=#{id}");
    	args.put("id", id);
    	HashMap<String, Object> fileinfo = baseService.easyDao.GetSinglerData(args);
    	File file=new File(GlobalCls.GP.getProperty("file.dir")+"/"+fileinfo.get("FPATH"));
    	Object ftype = fileinfo.get("FTYPE");
    	
    	if(!ftype.equals(null) && !ftype.toString().toUpperCase().equals("JPG") && !ftype.toString().toUpperCase().equals("PNG") && !ftype.toString().toUpperCase().equals("GIF")){
        	response.setHeader("Content-Disposition","attachment; filename="+URLEncoder.encode(fileinfo.get("FNAME").toString(), "UTF-8"));
    	}
    	ServletOutputStream  out =response.getOutputStream();
    	out.write(FileUtils.readFileToByteArray(file));
    	out.close();
    }
    
    @RequestMapping("mobile")    
    public String mobile(){
    	String id = request.getParameter("id");
    	String table = request.getParameter("table");
    	request.setAttribute("id", id);
    	
    	return "myworkflow/mobileuploadattachment";
    }
    

}
