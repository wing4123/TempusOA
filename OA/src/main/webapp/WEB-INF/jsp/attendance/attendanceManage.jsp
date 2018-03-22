<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/master/masterPage.tld" prefix="tempus" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="i18n" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="org.springframework.web.servlet.support.RequestContextUtils" language="java"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%
	String locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request).toString();
	String nowMonth = new SimpleDateFormat("yyyy-MM").format(new Date());
%>
<tempus:ContentPage materPageId="masterstep">

<tempus:Content contentPlaceHolderId="title">
    <title><i18n:message code="考勤管理" /></title>
    <link href="./assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
    <link href="./assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
    <link href="./assets/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css" />
    <link href="./assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.css" rel="stylesheet" type="text/css" />
    <link href="./assets/plugins/zTree_v3/css/metroStyle/metroStyle.css" rel="stylesheet" type="text/css" />
    <style>
    .blockUI{z-index: 10100 !important;}
    </style>
</tempus:Content>

<tempus:Content contentPlaceHolderId="main">
<div class="page-content" style="min-height: 1147px;">
    <div class="page-head">
        <div class="page-title">
            <h1><i18n:message code="考勤管理"  /></h1>
        </div>
    </div>
    <div style="background-color: white;padding: 20px;">
    	<div class="pull-right"  style="margin-top: -65px;">
    		<button class='btn btn-success' onclick="uploadfile()">上传考勤异常表</button>
    		<a class='btn btn-info' href="./attendances/export" target="_blank">导出查询结果</a>
    	</div>
    	<div class="form-horizontal">
	    	<div class="form-group">
	    		<label class="col-md-2 control-label">月份</label>
	    		<div class="col-md-4">
	       			<div class="input-group date date-picker" data-date-format="yyyy-mm" >
	                    <input id="search_month" name="month" value="<%=nowMonth %>" class="form-control" onchange="$('#datatable').DataTable().draw()" />
	                    <span class="input-group-btn" style="vertical-align: top;">
	                        <button class="btn default" type="button">
	                            <i class="fa fa-calendar"></i>
	                        </button>
	                    </span>
	                </div>
	       		</div>
	    		<label class="col-md-2 control-label">组织<span class="required">*</span></label>
	       		<div class="col-md-4">
	        		<div class="input-icon input-icon-sm right">
	                   <i class="fa fa-close" style="cursor: pointer;" onclick="$(this).next().val('');$(this).next().next().val('')"></i>
	                   <input type="text" class="form-control input-sm" readonly id="divisionname" onclick="$('#modal_division').modal('show');" />
	                   <input type="hidden" id="divisionnum" name="divisionnum" />
	                </div>
	        	</div>
	    	</div>
    	</div>
   		<table id="datatable" class="table table-striped table-bordered table-hover table-checkable order-column">
	        <thead>
	            <tr>
	            	<th>月份</th>
	            	<th>姓名</th>
	            	<th>工号</th>
	            	<th>事业部</th>
	            	<th>日期</th>
	            	<th>上班打开时间</th>
	            	<th>下班打卡时间</th>
	                <th>考勤状态</th>
	                <th>流程ID</th>
	                <th>单据类型</th>
	                <th>开始时间</th>
	            	<th>结束时间</th>
	            </tr>
	        </thead>
	        <tbody></tbody>
		</table>
    </div>
</div>

<div id="modal_upload" class="modal fade bs-modal-lg" tabindex="-1" data-backdrop="static" >
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
		    <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
		        <h4 class="modal-title">上传考勤异常表</h4>
		    </div>
		    <div class="modal-body" style="padding: 20px;max-height: 700px;overflow-y: auto;">
		    	<form id="form_bill" class="form-horizontal" role="form">
		    		<div class="form-group">
		    			<label class="col-md-1 control-label">月份</label>
			    		<div class="col-md-3">
			       			<div class="input-group date date-picker" data-date-format="yyyy-mm" >
			                    <input id="month" name="month" class="form-control" />
			                    <span class="input-group-btn" style="vertical-align: top;">
			                        <button class="btn default" type="button">
			                            <i class="fa fa-calendar"></i>
			                        </button>
			                    </span>
			                </div>
			       		</div>
			       		<label class="col-md-2 control-label">excel文件<span class="required">*</span></label>
                        <div class="col-md-6">
                            <div class="fileinput fileinput-new" data-provides="fileinput">
                                <div class="input-group input-large">
                                    <div class="form-control uneditable-input input-fixed input-medium" data-trigger="fileinput">
                                        <i class="fa fa-file fileinput-exists"></i>&nbsp;
                                        <span class="fileinput-filename"> </span>
                                    </div>
                                    <span class="input-group-addon btn green btn-file">
                                        <span class="fileinput-new"> 选择文件 </span>
                                        <span class="fileinput-exists"> 更改 </span>
                                        <input type="file" id="excelfile" name="excelfile" accept=".xlsx"> </span>
                                    <a href="javascript:;" class="input-group-addon btn red fileinput-exists" data-dismiss="fileinput"> 清空 </a>
                                </div>
                            </div>
                        </div>
		    		</div>
		    		<div id="tips" style="text-align: center;color: red;">
		    			正在处理请等待...
		    		</div>
		    	</form>
		    </div>
		    <div class="modal-footer">
		        <button type="button" data-dismiss="modal" class="btn dark btn-outline"><i18n:message code="common.close" /></button>
		        <button type="button" class="btn green" onclick="startupload()"><i18n:message code="开始上传" /></button>
		    </div>
		</div>
	</div>
</div>

<div id="modal_division" class="modal fade bs-modal-lg" tabindex="-1" data-backdrop="static" >
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
		    <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
		        <h4 class="modal-title">选择组织</h4>
		    </div>
		    <div class="modal-body" style="padding: 20px;max-height: 700px;overflow-y: auto;">
		    	<div id="divisiontree" class="ztree"></div>
		    </div>
		    <div class="modal-footer">
		        <button type="button" data-dismiss="modal" class="btn dark btn-outline"><i18n:message code="common.cancel" /></button>
		        <button type="button" class="btn green" onclick="selectdivision()"><i18n:message code="确定" /></button>
		    </div>
		</div>
	</div>
</div>

</tempus:Content>

<tempus:Content contentPlaceHolderId="js">
<script src="./assets/global/scripts/datatable.js" type="text/javascript"></script>
<script src="./assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
<script src="./assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>
<script src="./assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" type="text/javascript"></script>
<script src="./assets/global/plugins/bootstrap-datepicker/locales/bootstrap-datepicker.<%=locale %>.min.js" type="text/javascript"></script>
<script src="./assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.js" type="text/javascript"></script>
<script src="./assets/plugins/zTree_v3/js/jquery.ztree.core.min.js" type="text/javascript"></script>
<script>
$(function(){
 	$('.date-picker').datepicker({
        autoclose: true,
		weekStart: 0,
	 	startView: 2,  
        maxViewMode: 2,
        minViewMode: 1,
        forceParse: true,
        pickerPosition: "bottom-left",
	 	language: "<%=locale %>"
    });
	
	initdatatable();
	initztree_division();
});

var initdatatable = function(){
	$("#datatable").DataTable({
		serverSide: true,
		ordering: true,
		searching: true,
		order: [[1, "desc"]],
		lengthMenu: [[10,20,50,100],[10,20,50,100]],
      	pageLength: 20,
      	pagingType: "bootstrap_full_number",
		//dom: '<<t>lp>',
        ajax: function ( data, callback, settings ) {
        	data.month = $("#search_month").val();
        	data.divisionnum = $("#divisionnum").val();
            $.get("./attendances/data",data,function(result){callback(result)});
        },
	    columns: [
	    	{data:"FMONTH",orderable: false,width: "100"},
	    	{data:"FUSERNAME"},
	    	{data:"FUSERCODE"},
	    	{data:"FDIVISIONNAME"},
	    	{data:"FDATE"},
	    	{data:"FBEGINTIME"},
	    	{data:"FENDTIME"},
	    	{data:"FSTATUS"},
	    	{data:"FPROCESSINSTANCEID"},
	    	{data:"FTYPE"},
	    	{data:"FBEGINTIME2"},
	    	{data:"FENDTIME2"}
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

var uploadfile = function(){
	$("#tips").empty();
	
	$("#modal_upload").modal("show");
}

var startupload = function(){
	var files = $("#excelfile")[0].files; // js 获取文件对象
	if(files.length==0){alert("请选择文件");return false;}
	// FormData 对象
    var form = new FormData();
	if(files[0].size > 1024*1024*50){
		swal("Error!", "单个文件不能大于50MB", "error");
		return;
	}
	form.append("excelfile",files[0]);
	if($("#month").val()==""){alert("请选择月份");return false;}
    form.append("month",$("#month").val());
    // XMLHttpRequest 对象
    var xhr = new XMLHttpRequest();
    xhr.open("post", "./attendances/upload", true);
    xhr.onload = function() {
    	if(xhr.status==200){
    		$("#tips").html("上传完成...");
    		$('#datatable').DataTable().draw();
    	}else{
    		$("#tips").html("上传失败...");
    	}
    	App.unblockUI('#modal_upload');
    };
    xhr.send(form);
	App.blockUI({
        target: '#modal_upload',
        animate: true
    });
    $("#tips").html("正在处理请等待...");
}

var initztree_division = function(){
	zTreeObj = $.fn.zTree.init($("#divisiontree"), {},${divisiontree});
}

var selectdivision = function(){
	var division = $.fn.zTree.getZTreeObj("divisiontree").getSelectedNodes();
	console.log(division);
	$("#divisionname").val(division[0].name);
	$("#divisionnum").val(division[0].FNUMBER);
	$("#modal_division").modal("hide");
	$('#datatable').DataTable().draw();
}

</script>
</tempus:Content>

</tempus:ContentPage>