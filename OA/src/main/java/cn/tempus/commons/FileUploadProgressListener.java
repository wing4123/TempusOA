package cn.tempus.commons;

import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

@Component
public class FileUploadProgressListener implements ProgressListener {
    private HttpSession session;
    public void setSession(HttpSession session){
        this.session=session;
        JSONObject status = new JSONObject();//保存上传状态
        session.setAttribute("status", status);
    }
    @Override
    public void update(long bytesRead, long contentLength, int items) {
    	JSONObject status = (JSONObject) JSONObject.toJSON(session.getAttribute("status"));
        status.put("ByteRead", bytesRead);
        status.put("contentLength", contentLength);
        status.put("items", items);
    }
}