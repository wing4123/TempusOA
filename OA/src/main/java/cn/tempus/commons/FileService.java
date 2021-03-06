package cn.tempus.commons;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.ProcessEngine;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSONObject;

import cn.tempus.dao.EasyDao;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;

@Service
@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
public class FileService {
	
	@Autowired
    private EasyDao basicService;
	
	@Autowired
	ProcessEngine processengine;
	
	@Autowired
	JdbcTemplate jdbc;
	
	public void SaveFile(CommonsMultipartFile[] files,HttpServletRequest request) throws IOException{
    	String dir = GlobalCls.GP.getProperty("file.dir");
        String path = new SimpleDateFormat("yyyyMM").format(new Date());
        File filepath = new File(dir+"/"+path);
        if(!filepath.exists()){
        	filepath.mkdirs();
        }
        Map<String,Object> args = new HashMap<String,Object>();
        args.put("#SQL", "insert into TB_OA_fileinfo values (#{id},#{name},#{path},#{size},#{type},#{bid},#{btype},#{user},#{time})");
        args.put("bid", request.getParameter("bid"));
        args.put("btype", request.getParameter("btype"));
        args.put("user", ((JSONObject) JSONObject.toJSON(request.getSession().getAttribute("USER"))).getString("userid"));
        args.put("time", new Date());
        for(CommonsMultipartFile file: files){
        	
        	String id = UUID.randomUUID().toString();
        	args.put("id", id);
        	args.put("name", file.getOriginalFilename());
        	args.put("path", path+"/"+id);
        	args.put("size", (int)file.getSize());
        	args.put("type", StringUtils.substringAfterLast(file.getOriginalFilename(), "."));
        	basicService.InsertData(args);
        	
        	file.transferTo(new File(dir+"/"+path+"/"+id));
        }
	}
	
	/**
	 * @param id 文件id
	 * @return
	 */
	public int DeleteFileById(String id){
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("id", id);
		args.put("#SQL", "select fpath from TB_OA_fileinfo where fid=#{id}");
		String path = basicService.GetFirstValueBySqlWithWhere(args).toString();
		File file = new File(GlobalCls.GP.getProperty("file.dir")+"/"+path);
		if(file.exists()){
			file.delete();
		}
		
		args.put("#SQL", "delete from TB_OA_fileinfo where fid=#{id}");
		int result = basicService.DeleteData(args);
		
		return result;
	}
	
	/**
	 * @param bid 业务id
	 * @param btype 业务类型
	 * @return
	 */
	public int DeleteFileByBid(String bid,String btype){
		Map<String,Object> args = new HashMap<String,Object>();
		args.put("bid", bid);
		args.put("btype", btype);
		args.put("#SQL", "select fpath from TB_OA_fileinfo where fbusinessid=#{bid} and fbusinesstype=#{btype}");
		List<HashMap<String, Object>> paths = basicService.SelectListBySqlWithWhere(args);
		
		for(HashMap<String, Object> path: paths){
			File file = new File(GlobalCls.GP.getProperty("file.dir")+"/"+path.get("FPATH"));
			if(file.exists()){
				file.delete();
			}
		}
		
		args.put("#SQL", "delete from TB_OA_fileinfo where fbusinessid=#{bid} and fbusinesstype=#{btype}");
		int result = basicService.DeleteData(args);
		
		return result;
	}
	
	public ResponseEntity<byte[]> downloadPackage(String bid) throws ZipException, IOException {
		String sql = "select * from TB_OA_fileinfo where fbusinessid=?";
    	List<Map<String, Object>> attachments = jdbc.queryForList(sql, bid);
    	
    	String processinstanceid = processengine.getHistoryService().createHistoricProcessInstanceQuery().processInstanceBusinessKey(bid).notDeleted().singleResult().getId();
    	
    	//创建临时目录
    	File tempdir = new File(GlobalCls.GP.getProperty("file.dir")+"/"+UUID.randomUUID().toString());
    	tempdir.mkdir();
    	//创建压缩文件
    	ZipFile zipFile = new ZipFile(tempdir.getParent()+"/"+processinstanceid+".zip");
    	ArrayList<File> files = new ArrayList<>();
    	
    	for(Map<String,Object> attachment:attachments) {
    		File oldfile = new File(tempdir.getParent()+"/"+attachment.get("FPATH"));
    		File newfile = new File(tempdir.getPath()+"/"+attachment.get("FNAME"));
    		FileUtils.copyFile(oldfile, newfile);
    		files.add(newfile);
    	}
    	
    	//设置压缩文件参数
        ZipParameters parameters = new ZipParameters();
        //设置压缩方法
        //parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        //设置压缩级别
        //DEFLATE_LEVEL_FASTEST     - Lowest compression level but higher speed of compression
        //DEFLATE_LEVEL_FAST        - Low compression level but higher speed of compression
        //DEFLATE_LEVEL_NORMAL  - Optimal balance between compression level/speed
        //DEFLATE_LEVEL_MAXIMUM     - High compression level with a compromise of speed
        //DEFLATE_LEVEL_ULTRA       - Highest compression level but low speed
        //parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
        //设置压缩文件加密
        //parameters.setEncryptFiles(true);
        //设置加密方法
        //parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
        //设置aes加密强度
        //parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
        //设置密码
        //parameters.setPassword("wzx");
        //添加文件到压缩文件
        zipFile.addFiles(files, parameters);
        
        HttpHeaders headers = new HttpHeaders();    
        headers.setContentDispositionFormData("attachment", zipFile.getFile().getName());
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(zipFile.getFile()),headers, HttpStatus.OK);
        
        tempdir.delete();
    	
        return response;

	}

}
