<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="utf-8"%>
<%
	String basepath = request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	String time = request.getParameter("t");
%>
<!DOCTYPE HTML>
<html>
<head>
<title>上传附件</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<link href="../assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="../assets/global/css/components.min.css" rel="stylesheet" id="style_components" type="text/css" />
<link href="../assets/global/plugins/jquery-file-upload/css/jquery.fileupload.css" rel="stylesheet" type="text/css" />
<style>
	html,body{height: 100%;}
</style>
</head>
<body>

<div style="height: 100px;width: 100px;position: relative;top: 50%;left: 50%;margin: -50px 0px 0px -50px;">
	<span class="btn green fileinput-button">
	    <span> 添加附件 </span>
	    <!-- <input id="file" type="file" name="files[]" onchange="UploadFile(this)" capture="camera"  accept="image/*"  multiple /> -->
	    <input id="file" type="file" name="files[]" onchange="UploadFile(this)"   multiple />
	</span>
</div>
<div class="progress progress-striped active" style="margin-top: 16px;display: none;position: relative;top: 50%;margin-top: -50px">
	<div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="text-align: left;">
        <span style="color: black;margin-left: 16px;"></span>
    </div>
</div>

</body>
<script src="../assets/global/plugins/jquery.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="../js/jquery.qrcode.min.js" type="text/javascript"></script>
<script src="../assets/global/scripts/app.min.js" type="text/javascript"></script>
<script>

var UploadFile = function(file) {
	var time = parseInt("<%=time %>");
	var now = new Date().getTime();
	if(now-time>600000){
		alert("该页面已过期，请重新扫描二维码！");
		return false;
	}
	
	var files = file.files; // js 获取文件对象
	if(files.length==0){return;}
	$("#file").attr('disabled', true);
	$(".progress-bar").width("0%");
	$(".progress-bar span").html("正在上传...");
	$(".progress").show();
	// FormData 对象
    var form = new FormData();
	for(var i=0;i<files.length;i++){
		if(files[i].size > 1024*1024*50){
			$(".progress").hide();
			$("#file").removeAttr('disabled');
			alert("单个文件不能大于50MB");
			return;
		}
		form.append("file",files[i]);
    }
    form.append("bid","${id}");
    // XMLHttpRequest 对象
    var xhr = new XMLHttpRequest();
    xhr.open("post", "../MyWorkFlow/upload", true);
    xhr.onload = function() {
		$("#file").removeAttr('disabled');
		$(".progress-bar span").html("上传成功...");
    };
    xhr.upload.addEventListener("progress", progressFunction, true);
    xhr.send(form);
}

var progressFunction = function(evt) {
    if (evt.lengthComputable) {
        var completePercent = Math.round(evt.loaded / evt.total * 100)+ "%";
        $(".progress-bar").width(completePercent);
    }
}
	
</script>
</html>