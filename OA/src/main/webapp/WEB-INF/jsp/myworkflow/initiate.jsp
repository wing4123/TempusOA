<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/master/masterPage.tld" prefix="tempus" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="i18n" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="org.springframework.web.servlet.support.RequestContextUtils" language="java"%>
<%
	String locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request).toString();
%>
<tempus:ContentPage materPageId="masterstep">

<tempus:Content contentPlaceHolderId="title">
    <title><i18n:message code="发起申请"  /></title>
    <link href="../assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
    <link href="../assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
</tempus:Content>

<tempus:Content contentPlaceHolderId="main">
<div class="page-content" style="min-height: 1147px;">
    <!-- BEGIN PAGE HEAD-->
    <div class="page-head">
        <!-- BEGIN PAGE TITLE -->
        <div class="page-title">
            <h1><i18n:message code="发起申请"  /></h1>
        </div>
        <!-- END PAGE TITLE -->
    </div>
    <!-- END PAGE HEAD-->
    <!-- BEGIN PAGE BASE CONTENT -->
    <div style="background-color: white;padding: 20px;">
    	<div>
    		<label style="line-height: 34px;float: left;">流程类别:</label>
    		<div style="float: left;margin-left: 16px;">
    			<select id="category" class="form-control" onchange="$('#datatable').DataTable().draw()">
    				<option value="">全部</option>
   					<option value="1">财务管理模块</option>
   					<option value="2">人力资源模块</option>
    			</select>
    		</div>
    	</div>
    
   		<table id="datatable" class="table table-striped table-bordered table-hover table-checkable order-column">
	        <thead>
	            <tr>
	            	<th>流程编号</th>
	                <th>名称</th>
	                <th>类别</th>
	                <th>Action</th>
	            </tr>
	        </thead>
	        <tbody></tbody>
		</table>
    </div>
    <!-- END PAGE BASE CONTENT -->
</div>

<div id="modal_add" class="modal fade in" tabindex="-1" data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title"><i18n:message code="menu.modal_add_head" /></h4>
            </div>
            <div class="modal-body" style="padding: 20px;">
            	<form id="form_add" class="form-horizontal" role="from">
            		<input id="process_id" name="id" type="hidden"/>
            		<div class="from-body">
            			<div class="form-group">
            				<label class="col-md-3 control-label"><i18n:message code="common.name" /><span class="required">*</span></label>
            				<div class="col-md-9">
            					<div class="col-md-6" style="padding: 0px;">
									<div class="input-group">
		                                <span class="input-group-addon"><i18n:message code="chinese" /></span>
		                                <input id="process_zh_cn" name="zh_CN" class="form-control" required />
	                                </div>
                                </div>
       							<div class="col-md-6" style="padding: 0px;">
									<div class="input-group">
		                                <span class="input-group-addon"><i18n:message code="english" /></span>
		                                <input id="process_en_us" name="en_US" class="form-control" required />
	                                </div>
                                </div>
							</div>
            			</div>
            			<div class="form-group">
            				<label class="col-md-3 control-label"><i18n:message code="common.url" /><span class="required">*</span></label>
            				<div class="col-md-9"><input id="process_url" name="url" class="form-control" required /></div>
            			</div>
            			<div class="form-group">
            				<label class="col-md-3 control-label"><i18n:message code="common.remark" /></label>
            				<div class="col-md-9">
            					<textarea id="process_remark" name="remark" class="form-control"></textarea>
            				</div>
            			</div>
            		</div>
            	</form>
            </div>
            <div class="modal-footer">
                <button type="button" data-dismiss="modal" class="btn dark btn-outline"><i18n:message code="common.cancel" /></button>
                <button type="submit" class="btn green" onclick="$('#form_add').submit()"><i18n:message code="common.save" /></button>
            </div>
        </div>
    </div>
</div>

<div id="modal_flowchart" class="modal fade bs-example-modal-lg text-center" tabindex="-1" role="dialog">
	<div class="modal-dialog modal-lg" style="display: inline-block; width: auto;">
		<div class="modal-content">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
			<img  id="flowchart" src="" >
		</div>
	</div>
</div>
</tempus:Content>

<tempus:Content contentPlaceHolderId="js">
<script src="../assets/global/scripts/datatable.js" type="text/javascript"></script>
<script src="../assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>
<script src="../assets/global/plugins/jstree/dist/jstree.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/jquery-validation/js/additional-methods.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/jquery-validation/js/localization/messages_<%=locale %>.min.js" type="text/javascript"></script>
<script>
$(function(){
	initdatatable();
	initvalidate();
});

var initdatatable = function(){
	$("#datatable").DataTable({
		serverSide: true,
		ordering: true,
		searching: true,
		order: [[0, "asc"]],
		lengthMenu: [[10,20,50,100],[10,20,50,100]],
      	pageLength: 20,
      	pagingType: "bootstrap_full_number",
		dom: 'lftipr',
		ajax: {
	        url: './getMyModelList',
	        data: function(data){
	        	data.category=$("#category").val();
	        	data.enable="1";
	        	data.deployed="1";
	        }
		},
	    columns: [
	    	{data:"FKEY",orderable: false},
	    	{data:"FNAME",orderable: false},
	    	{orderable: false,render:function(data, type, row){
	    		switch(row.FCATEGORY){
	    			case "1":return "财务管理模块";
	    			case "2":return "人力资源模块";
	    			default:return "未分类";
	    		}
	    	}},
	    	{orderable: false,render:function(data, type, row){
	    		return '<div class="btn-group">'
				    		+'<button class="btn btn-xs green dropdown-toggle" type="button" data-toggle="dropdown" aria-expanded="false">'
				    			+'<i class="fa fa-angle-down"></i> Actions'
			    			+'</button>'
			    			+'<ul class="dropdown-menu" role="menu">'
		    					+'<li><a href="'+row.FCREATEURL+'"><span class="icon-docs"></span>发起流程</a></li>'
		    					+'<li><a href="../modeler/diagram-viewer/index.html?processDefinitionId='+row.FPROCESSDEFINITIONID+'" target="_blank"><span class="icon-docs"></span>查看流程图</a></li>'
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

var add = function(){
	$("#form_add")[0].reset();
	$("#modal_add").modal("show");
}

var edit = function(id){
	$.get("./getProcessInfoById",{"id": id},function(data){
		$("#process_id").val(data.FID);
		$("#process_zh_cn").val(data.FZH_CN);
		$("#process_en_us").val(data.FEN_US);
		$("#process_url").val(data.FURL);
		$("#process_remark").val(data.FREMARK);
		$("#modal_add").modal("show");
	});
}

var deleterole = function(id){
	swal({
		title: '<i18n:message code="common.deletecomfirmtitle" />',
		text: '<i18n:message code="common.deletecomfirmtext" />',
		type: "warning",
		showCancelButton: true,
		confirmButtonColor: "#DD6B55",
		confirmButtonText: '<i18n:message code="common.confirm" />',
		cancelButtonText: '<i18n:message code="common.cancel" />',
		closeOnConfirm: false,
	},
	function(isConfirm){
		if (isConfirm) {
			$.get("./DeleteRole",{"id": id},function(){
				swal("Deleted!", "", "success");
				$("#datatable").DataTable().draw();
			});
		}
	});
}

var initvalidate = function() {
	$('#form_add').validate({
        errorElement: 'span',
        errorClass: 'help-block',
        focusInvalid: false,
        errorPlacement: function (error, element) { // render error placement for each input type
            var cont = $(element).parent('.input-group');
            if (cont.size() > 0) {
                cont.after(error);
            } else {
                element.after(error);
            }
        },
        highlight: function (element) { // hightlight error inputs
            $(element).closest('.form-group').addClass('has-error'); // set error class to the control group
        },
        unhighlight: function (element) { // revert the change done by hightlight
            $(element).closest('.form-group').removeClass('has-error'); // set error class to the control group
        },
        submitHandler: function (form) { //表单校验通过并提交表单
        	var data = $(form).serializeJSON();
        	$.post("./SaveProcess",data,function(){
        		$("#modal_add").modal("hide");
        		swal("Success!", "", "success");
        		$("#modal_add").modal("show");
        	});
        }
    });
}

var deploy = function(modelid){
	$.get("./Deploy",{"id": modelid},function(){
		swal("Success!", "", "success");
	});
}

var viewflowchart = function(modelid){
	$("#flowchart").attr("src","./getFlowChart?modelid="+modelid);
	$("#modal_flowchart").modal("show");
}

</script>
</tempus:Content>

</tempus:ContentPage>