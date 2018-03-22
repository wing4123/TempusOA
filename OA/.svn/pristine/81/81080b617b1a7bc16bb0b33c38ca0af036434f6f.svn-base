<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="i18n" %>
<%@ page import="org.springframework.web.servlet.support.RequestContextUtils" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
	String basepath = request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	String scheme = request.getScheme();
%>
    <link href="../assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
    <link href="../assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
    <link href="../assets/global/plugins/jquery-file-upload/css/jquery.fileupload.css" rel="stylesheet" type="text/css" />
    <link href="../assets/global/plugins/jquery-file-upload/css/jquery.fileupload-ui.css" rel="stylesheet" type="text/css" />
   	<hr/>
   	<div style="float: left;height: 34px;line-height: 34px;">附件：</div>
   	<span class="btn green fileinput-button">
        <i class="fa fa-plus"></i>
        <span> <i18n:message code="common.addattachments" /> </span>
        <input id="file" type="file" name="files[]" onchange="UploadFile(this)"  multiple >
    </span>
    <button class="btn btn-success" onclick="createrqcode();$('#modal_qrcode').modal('show')"><i18n:message code="common.addattachmentsbyphone" /></button>
    <div class="progress" style="margin-top: 16px;display: none;">
		<div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="60"aria-valuemin="0" aria-valuemax="100" style="text-align: left;">
			<span style="color: black;margin-left: 16px;">40% 完成</span>
		</div>
	</div>
  		<table id="filelist" class="table table-striped table-bordered table-hover table-checkable order-column" style="margin-top: 16px;">
        <thead>
            <tr>
            	<th>序号</th>
            	<th>文件名称</th>
            	<th>文件大小</th>
            	<th>上传人</th><th>上传时间</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody></tbody>
	</table>
	<div id="modal_qrcode" class="modal fade bs-modal-lg" tabindex="-1">
		<div class="modal-dialog" style="width: 300px;height: 300px;">
			<div id="qrcode"></div>
		</div>
	</div>

<script src="../assets/global/scripts/datatable.js" type="text/javascript"></script>
<script src="../assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>
<script src="../js/jquery.qrcode.min.js" type="text/javascript"></script>

<script>
var websocket = new WebSocket("<%=(scheme.equals("http")?"ws":"wss") %>://<%=basepath %>/ws?uid=${USER.userid}");
websocket.onmessage = function(event){
	$("#filelist").DataTable().draw();
}

$(function(){
    initfilelist();
    
});

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
			swal("Error!", "单个文件不能大于50MB", "error");
			$(".progress").hide();
			$("#file").removeAttr('disabled');
			return;
		}
		form.append("file",files[i]);
    }
    form.append("bid",$("#id").val());
    // XMLHttpRequest 对象
    var xhr = new XMLHttpRequest();
    xhr.open("post", "../file/upload", true);
    xhr.onload = function() {
		$("#file").removeAttr('disabled');
		$(".progress-bar span").html("上传成功...");
		$("#filelist").DataTable().draw();
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

var initfilelist = function(){
	$("#filelist").DataTable({
		serverSide: true,
		ordering: true,
		searching: false,
		order: [[1, "asc"]],
		lengthMenu: [[10,20,50,100],[10,20,50,100]],
      	pageLength: 10,
      	pagingType: "bootstrap_full_number",
		dom: '<<t>lp>',
		ajax: {
	        url: '../file/getFileList',
	        data: function(data){
	        	data.bid=$("#id").val();
	        }
		},
	    columns: [
	    	{data:"RN",orderable: false},
	    	{data:"FNAME"},
	    	{data:"FSIZE",render:function(data, type, row){
	    		if(row.FSIZE < 1024){
	    			return row.FSIZE+"B";
	    		}else if(row.FSIZE > 1024 && row.FSIZE < 1024*1024){
	    			return (row.FSIZE/1024).toFixed(2)+"KB";
	    		}else if(row.FSIZE > 1024*1024 && row.FSIZE < 1024*1024*1024){
	    			return (row.FSIZE / (1024 * 1024)).toFixed(2)+"MB";
	    		}
	    	}},
	    	{data:"FCREATORNAME"},{data:"FCREATETIME"},
	    	{orderable: false,render:function(data, type, row){
	    		return '<div class="btn-group">'
			    		+'<button class="btn btn-xs green dropdown-toggle" type="button" data-toggle="dropdown" aria-expanded="false">'
			    			+'<i class="fa fa-angle-down"></i> Actions'
		    			+'</button>'
		    			+'<ul class="dropdown-menu" role="menu">'
		    				+((row.FTYPE.toUpperCase()=="JPG" || row.FTYPE.toUpperCase()=="PNG" || row.FTYPE.toUpperCase()=="GIF")?'<li><a href="../file/ViewImage?id='+row.FID+'" target="_blank"><span class="icon-docs"></span>查看图片</a></li>':'')
							+'<li><a href="../file/download?id='+row.FID+'"><span class="icon-docs"></span>下载</a></li>'
							+'<li><a href="javascript:deletefile(\''+row.FID+'\');"><span class="icon-docs"></span>删除</a></li>'
						+'</ul>'
					+' </div>';
	    	}}
	    ],
        language: {
        	processin: "<i18n:message code="datatable.processin" />",
        	loadingRecords: "<i18n:message code="datatable.loadingRecords" />",
            zeroRecord: "<i18n:message code="datatable.zeroRecord" />",
            emptyTable: "<i18n:message code="datatable.emptyTable" />",
            info: "<i18n:message code="datatable.info" />",
            infoFiltered: "<i18n:message code="datatable.infoFiltered" />",
           	lengthMenu: "<i18n:message code="datatable.lengthMenu" />",
           	search: "<i18n:message code="datatable.search" />",
            paginate:{
            	first: "<i18n:message code="datatable.first" />",
            	last: "<i18n:message code="datatable.last" />",
            	next: "<i18n:message code="datatable.next" />",
            	previous: "<i18n:message code="datatable.previous" />"
            }
        }
	});
}

var deletefile = function(id){
	$.get("../file/DeleteFileById",{"id":id},function(){
		swal("Success!", "删除成功", "success");
		$("#filelist").DataTable().draw();
	});
}

var createrqcode = function(){
	var url = "<%=url %>/file/mobile?id="+$("#id").val()+"&t="+new Date().getTime();
	console.log(url);
	$("#qrcode").empty();
	$("#qrcode").qrcode({
		width:400,
		height:400,
		render: "canvas",
		text: url
	});
}

</script>
