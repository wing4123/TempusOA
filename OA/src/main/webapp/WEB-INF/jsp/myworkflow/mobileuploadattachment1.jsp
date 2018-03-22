<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="utf-8"%>
<%
	String basepath = request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
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
</head>
<body>
<span class="btn green fileinput-button">
    <span> Add files... </span>
    <input id="file" type="file" name="files[]" onchange="UploadFile(this)" capture="camera" accept="image/*"  multiple >
</span>
<div class="progress" style="margin-top: 16px;display: none;">
	<div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="60"aria-valuemin="0" aria-valuemax="100" style="text-align: left;">
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
		
		var image = new Image(),
		canvas = document.createElement("canvas"),
		ctx = canvas.getContext('2d');
		
        var reader = new FileReader();//读取客户端上的文件
        reader.onload = function() {
            var url = reader.result;//读取到的文件内容.这个属性只在读取操作完成之后才有效,并且数据的格式取决于读取操作是由哪个方法发起的.所以必须使用reader.onload，
            image.src=url;//reader读取的文件内容是base64,利用这个url就能实现上传前预览图片
        };
        image.onload = function() {
            var w = image.naturalWidth,
                h = image.naturalHeight;
            canvas.width = w;
            canvas.height = h;
            ctx.drawImage(image, 0, 0, w, h, 0, 0, w, h);
            
            var canvasdata = canvas.toDataURL("image/jpeg", 0.2);
            //dataURL 的格式为 “data:image/png;base64,****”,逗号之前都是一些说明性的文字，我们只需要逗号之后的就行了
			canvasdata = canvasdata.split(',')[1];
			canvasdata = window.atob(canvasdata);
			var ia = new Uint8Array(canvasdata.length);
			for (var j = 0; j < canvasdata.length; j++) {
			      ia[j] = canvasdata.charCodeAt(j);
			};
			 //canvas.toDataURL 返回的默认格式就是 image/png
			console.log(i);
			var blob = new Blob([ia], {type: files[i].type});
			 
/* 			var imageobj = {
				"name": files[i].name,
				"blob": blob
			} */
            
            form.append("file",blob);
            
        };
        reader.readAsDataURL(files[i]);
		
		
    }
    form.append("bid","${id}");
    // XMLHttpRequest 对象
    var xhr = new XMLHttpRequest();
    xhr.open("post", "../MyWorkFlow/upload_blob", true);
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