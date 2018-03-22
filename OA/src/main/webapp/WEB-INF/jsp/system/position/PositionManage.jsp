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
    <title>职位管理</title>
    <link href="../assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
    <link href="../assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
    <link href="../assets/plugins/zTree_v3/css/metroStyle/metroStyle.css" rel="stylesheet" type="text/css" />
</tempus:Content>

<tempus:Content contentPlaceHolderId="main">
<div class="page-content" style="min-height: 1147px;">
    <!-- BEGIN PAGE HEAD-->
    <div class="page-head">
        <!-- BEGIN PAGE TITLE -->
        <div class="page-title">
            <h1><i18n:message code="权限管理" /></h1>
        </div>
        <!-- END PAGE TITLE -->
    </div>
    <!-- END PAGE HEAD-->
    <!-- BEGIN PAGE BASE CONTENT -->
    <div style="background-color: white;padding:15px;">
		<div>
			<button class="btn green" onclick="addposition()"><i class="fa fa-plus"></i><i18n:message code="common.add" /></button>
		</div>
		<div style="margin-top: 20px;">
			<table id="datatable" class="table table-striped table-bordered table-hover table-checkable order-column">
	            <thead>
	                <tr>
	                	<th>职位</th>
	                	<th>创建人</th>
	                	<th>创建时间</th>
	                	<th>最后修改人</th>
	                	<th>最后修改时间</th>
	                	<th>Action</th>
	                </tr>
	            </thead>
	            <tbody></tbody>
	    	</table>
    	</div>
    </div>
</div>
    <!-- END PAGE BASE CONTENT -->
<div id="modal_position" class="modal fade in" tabindex="-1" data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title"><i18n:message code="common.add" /></h4>
            </div>
            <div class="modal-body" style="padding: 20px;">
            	<form id="form_position" class="form-horizontal" role="from">
            		<input id="position_id" name="id" type="hidden"/>
            		<div class="from-body">
            			<div class="form-group">
            				<label class="col-md-3 control-label"><i18n:message code="common.name" /><span class="required">*</span></label>
            				<div class="col-md-9"><input id="position_name" name="name" class="form-control" placeholder="" required /></div>
            			</div>
            			<div class="form-group">
            				<label class="col-md-3 control-label"><i18n:message code="common.remark" /></label>
            				<div class="col-md-9"><textarea id="position_remark" name="remark" class="form-control"></textarea></div>
            			</div>
            		</div>
            	</form>
            </div>
            <div class="modal-footer">
                <button type="button" data-dismiss="modal" class="btn dark btn-outline"><i18n:message code="common.cancel" /></button>
                <button type="submit" class="btn green" onclick="$('#form_position').submit()"><i18n:message code="common.save" /></button>
            </div>
        </div>
    </div>
</div>
</tempus:Content>

<tempus:Content contentPlaceHolderId="js">
<script src="../assets/global/scripts/datatable.js" type="text/javascript"></script>
<script src="../assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>
<script src="../assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/jquery-validation/js/additional-methods.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/jquery-validation/js/localization/messages_<%=locale %>.min.js" type="text/javascript"></script>
<script src="../assets/plugins/zTree_v3/js/jquery.ztree.core.min.js" type="text/javascript"></script>
<script src="../assets/plugins/zTree_v3/js/jquery.ztree.excheck.min.js" type="text/javascript"></script>
<script>
$(function(){
	initdatatable();
	initvalidate();
});

var initdatatable = function(){
	$("#datatable").DataTable({
		serverSide: true,
		ordering: false,
		searching: false,
		order: [[0, "asc"]],
		lengthMenu: [[10,20,50,100],[10,20,50,100]],
      	pageLength: 20,
      	pagingType: "bootstrap_full_number",
		//dom: '<<t>lp>',
		ajax: {
	        url: './getPositionList'
		},
	    columns: [
	    	{data:"FNAME",orderable: false},
	    	{data:"FCREATORNAME",orderable: false},
	    	{data:"FCREATETIME",orderable: false},
	    	{data:"FLASTUPDATOR",orderable: false},
	    	{data:"FLASTUPDATETIME",orderable: false},
	    	{orderable: false,render:function(data, type, row){
	    		return '<div class="btn-group">'
				    		+'<button class="btn btn-xs green dropdown-toggle" type="button" data-toggle="dropdown" aria-expanded="false">'
				    			+'<i class="fa fa-angle-down"></i> Actions'
			    			+'</button>'
			    			+'<ul class="dropdown-menu" role="menu">'
		    					+'<li><a href="javascript:editposition(\''+row.FID+'\');"><span class="icon-docs"></span>Edit</a></li>'
		    					+'<li><a href="javascript:deleteposition(\''+row.FID+'\');"><span class="icon-docs"></span>Delete</a></li>'
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

var addposition = function(){
	$("#form_position")[0].reset();
	$("#modal_position").modal("show");
}

var deleteposition = function(id){
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
			$.get("./DeletePosition",{"id": id},function(){
				swal("Deleted!", "", "success");
				$("#datatable").DataTable().draw();
			});
		}
	});
}

var editposition = function(id){
	$.get("./getPositionInfoById",{"id":id},function(data){
		$("#position_id").val(data.FID);
		$("#position_name").val(data.FNAME);
		$("#position_remark").val(data.FREMARK);
		$("#modal_position").modal("show");
	});
}

var initvalidate = function() {
	$('#form_position').validate({
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
        	$.post("./SavePosition",data,function(){
        		$("#modal_position").modal("hide");
        		swal("Success!", "", "success");
        		$("#datatable").DataTable().draw();
        	});
        }
    });
}
</script>
</tempus:Content>
</tempus:ContentPage>