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
    <title>权限管理</title>
    <link href="../assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
    <link href="../assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
    <link href="../assets/plugins/zTree_v3/css/metroStyle/metroStyle.css" rel="stylesheet" type="text/css" />
    <link href="../assets/plugins/multi-select/css/multi-select.css" rel="stylesheet" type="text/css" />
    <style>
    	.wing-tools{margin-bottom: 10px;}
		.search{width: 200px;height: 30px;padding: 6xp 12px;border: 1px solid #c2cad8;vertical-align: middle;font-size: 12px;padding: 5px 10px;float: right;}
    	.table>tbody>tr.active>td{background-color: #eef1f5 !important}
    </style>
</tempus:Content>

<tempus:Content contentPlaceHolderId="main">
<div class="page-content">
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
   	<div class="row" style="min-height: inherit;">
   		<div class="col-md-4">
	   		<div class="portlet box blue-hoki">
	            <div class="portlet-title">
	                <div class="caption"><i class="fa fa-gift"></i>角色</div>
	                <div class="actions">
	                	<button class="btn btn-default btn-sm" onclick="addRole()"><i class="fa fa-plus"></i><i18n:message code="common.add" /></button>
	                    <button class="btn btn-default btn-sm" onclick="editRole()"><i class="fa fa-pencil"></i><i18n:message code="common.edit" /></button>
	                    <button class="btn btn-default btn-sm" onclick="deleteRole()"><i class="fa fa-trash"></i><i18n:message code="common.delete" /></button>
	                </div>
	            </div>
	            <div class="portlet-body">
	            	<div><input id="search_role" class="form-control" placeholder="搜索" onkeyup="$('#datatable_role').DataTable().draw()" /></div>
	            	<table class="table table-striped table-bordered table-condensed" id="datatable_role" style="width: 100%;">
			            <thead>
			                <tr style="display: none;">
			                	<th>FID</th>
			                    <th>名称</th>
			                </tr>
			            </thead>
			            <tbody></tbody>
		            </table>
				</div>
	        </div>
        </div>
        <div class="col-md-8 portlet box blue-hoki">
            <div class="portlet-title">
                <div class="caption"><i class="fa fa-user"></i>权限</div>
                <div class="actions">
                    <a href="javascript:adduser();" class="btn btn-default btn-sm"><i class="fa fa-plus"></i>Add</a>
                </div>
            </div>
            <div class="portlet-body">
            	<div class="tabbable tabbable-tabdrop tabbable-custom">
                    <ul class="nav nav-tabs">
                        <li class="active">
                            <a href="#tab1" data-toggle="tab">用户</a>
                        </li>
                        <li>
                            <a href="#tab2" data-toggle="tab">菜单</a>
                        </li>
                        <li>
                            <a href="#tab3" data-toggle="tab">流程</a>
                        </li>
                        <li>
                            <a href="#tab4" data-toggle="tab">事业部</a>
                        </li>
                        <li>
                            <a href="#tab5" data-toggle="tab">部门</a>
                        </li>
                    </ul>
                    <div class="tab-content">
                        <div class="tab-pane active" id="tab1">
                       		<div class="wing-tools">
			            		<a id="prompt_user" class="btn btn-success btn-sm" data-url="./prompt_user" data-modal="#modal_user" data-toggle="modal"><i class="fa fa-plus"></i><i18n:message code="common.add" /></a>
			                    <button class="btn btn-danger btn-sm" onclick="deleteUser()"><i class="fa fa-trash"></i><i18n:message code="common.delete" /></button>
			            	</div>
							<table id="datatable_user" class="table table-striped table-bordered table-hover table-checkable order-column">
					            <thead>
					                <tr>
					                	<th>
			                                <label class="mt-checkbox mt-checkbox-single mt-checkbox-outline">
			                                    <input type="checkbox" class="group-checkable" data-set="#datatable_user .checkboxes" />
			                                    <span></span>
			                                </label>
			                            </th>
					                    <th>工号</th>
					                    <th>姓名</th>
					                </tr>
					            </thead>
					            <tbody></tbody>
				            </table>
                        </div>
                        <div class="tab-pane" id="tab2">
                            <button class="btn btn-info btn-sm" onclick="editMenu()"><i class="fa fa-trash"></i><i18n:message code="修改" /></button>
                            <div id="roleMenu" class="ztree"></div>
                        </div>
                        <div class="tab-pane" id="tab3">
                        	<div class="wing-tools">
			                    <button id="prompt_process" class="btn btn-success btn-sm" data-url="./prompt_process" data-modal="#modal_process" ><i class="fa fa-plus"></i><i18n:message code="common.add" /></button>
			                    <button class="btn btn-danger btn-sm" onclick="deleteProcess()"><i class="fa fa-trash"></i><i18n:message code="common.delete" /></button>
			            	</div>
							<table id="datatable_process" class="table table-striped table-bordered table-hover table-checkable order-column" style="width: 100%;">
					            <thead>
					                <tr>
					                	<th>
			                                <label class="mt-checkbox mt-checkbox-single mt-checkbox-outline">
			                                    <input type="checkbox" class="group-checkable" />
			                                    <span></span>
			                                </label>
			                            </th>
					                    <th>流程编号</th>
					                    <th>流程名称</th>
					                </tr>
					            </thead>
					            <tbody></tbody>
				            </table>
                        </div>
                        <div class="tab-pane" id="tab4">
                       		<div class="wing-tools">
			            		<a id="prompt_division" class="btn btn-success btn-sm" data-url="./prompt_org?orgType=division" data-modal="#modal_org" data-toggle="modal"><i class="fa fa-plus"></i><i18n:message code="common.add" /></a>
			                    <button class="btn btn-danger btn-sm" onclick="deleteOrg('division')"><i class="fa fa-trash"></i><i18n:message code="common.delete" /></button>
			            	</div>
							<table id="datatable_division" class="table table-striped table-bordered table-hover table-checkable order-column" style="width: 100%;">
					            <thead>
					                <tr>
					                	<th>
			                                <label class="mt-checkbox mt-checkbox-single mt-checkbox-outline">
			                                    <input type="checkbox" class="group-checkable" data-set="#datatable_user .checkboxes" />
			                                    <span></span>
			                                </label>
			                            </th>
					                    <th>编号</th>
					                    <th>名称</th>
					                </tr>
					            </thead>
					            <tbody></tbody>
				            </table>
                        </div>
                        <div class="tab-pane" id="tab5">
                       		<div class="wing-tools">
			            		<a id="prompt_department" class="btn btn-success btn-sm" data-url="./prompt_org?orgType=department" data-modal="#modal_org" data-toggle="modal"><i class="fa fa-plus"></i><i18n:message code="common.add" /></a>
			                    <button class="btn btn-danger btn-sm" onclick="deleteOrg('department')"><i class="fa fa-trash"></i><i18n:message code="common.delete" /></button>
			            	</div>
							<table id="datatable_department" class="table table-striped table-bordered table-hover table-checkable order-column" style="width: 100%;">
					            <thead>
					                <tr>
					                	<th>
			                                <label class="mt-checkbox mt-checkbox-single mt-checkbox-outline">
			                                    <input type="checkbox" class="group-checkable" data-set="#datatable_user .checkboxes" />
			                                    <span></span>
			                                </label>
			                            </th>
					                    <th>编号</th>
					                    <th>名称</th>
					                </tr>
					            </thead>
					            <tbody></tbody>
				            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
   	</div>
    
    
</div>

<div id="modal_user" class="modal fade bs-modal-lg" tabindex="-1" data-backdrop="static" ></div>

<div id="modal_menu" class="modal fade in" tabindex="-1" data-backdrop="static" data-keyboard="true" >
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">添加菜单权限</h4>
            </div>
            <div class="modal-body" style="padding: 20px;">
            	<div id="editMenu" class="ztree"></div>
            </div>
            <div class="modal-footer">
                <button type="button" data-dismiss="modal" class="btn dark btn-outline"><i18n:message code="common.cancel" /></button>
                <button type="button" class="btn green" onclick="saverolemenu()"><i18n:message code="common.save" /></button>
            </div>
        </div>
    </div>
</div>

<div id="modal_process" class="modal fade bs-modal-lg" tabindex="-1" data-backdrop="static" ></div>

<div id="modal_org" class="modal fade bs-modal-lg" tabindex="-1" data-backdrop="static" ></div>

<div id="modal_role" class="modal fade in" tabindex="-1" data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title"><i18n:message code="common.add" /></h4>
            </div>
            <div class="modal-body" style="padding: 20px;">
            	<form id="form_role" class="form-horizontal" role="from">
            		<div class="from-body">
            			<div class="form-group">
            				<label class="col-md-3 control-label"><i18n:message code="common.name" /><span class="required">*</span></label>
            				<div class="col-md-9"><input id="role_name" name="name" class="form-control" placeholder=""/></div>
            				<input id="role_id" name="id" type="hidden"/>
            			</div>
            			<div class="form-group">
            				<label class="col-md-3 control-label"><i18n:message code="common.remark" /></label>
            				<div class="col-md-9"><textarea id="role_remark" name="remark" class="form-control"></textarea></div>
            			</div>
            		</div>
            	</form>
            </div>
            <div class="modal-footer">
                <button type="button" data-dismiss="modal" class="btn dark btn-outline"><i18n:message code="common.cancel" /></button>
                <button type="submit" class="btn green" onclick="$('#form_role').submit()"><i18n:message code="common.save" /></button>
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
<script src="../assets/plugins/zTree_v3/js/jquery.ztree.core.min.js" type="text/javascript"></script>
<script src="../assets/plugins/zTree_v3/js/jquery.ztree.excheck.min.js" type="text/javascript"></script>
<script>
var selectRoleId="";
$(function(){
	initvalidate_role();
	
	initdatatable_role();
	initdatatable_user();
	initdatatable_process();
	initdatatable_division();
	initdatatable_department();
	
	$("#prompt_user,#prompt_process,#prompt_division,#prompt_department").on("click",function(){
		if(selectRoleId != ""){
			var me = $(this);
			$(me.data("modal")).load($(this).data("url"), function(){
				$(me.data("modal")).modal("show");
			});
		}
	});
});

var initdatatable_role = function(){
	var datatable = $("#datatable_role");
	datatable.DataTable({
		serverSide: true,
		ordering: true,
		searching: true,
		order: [[0, "asc"]],
		lengthMenu: [[10,20,50,100],[10,20,50,100]],
      	pageLength: 999999999,
      	pagingType: "bootstrap_full_number",
		dom: 't',
        scrollY: 620,
        deferRender:    true,
        scrollX:        true,
        scroller: {
            loadingIndicator: true
        },
        ajax: function ( data, callback, settings ) {
        	var search = $("#search_role").val();
	    	data.search = search;
            $.get("./roles",data,function(result){callback(result)});
        },
        columns: [
        	{data:"FNAME", visible: false},
        	{data:"FNAME", visible: true}
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
	
	datatable.find('tbody').on('click', 'tr', function () {
	    if ($(this).hasClass('selected') ) {
	       	//$(this).removeClass('selected');
	    } else {
	    	datatable.find('tr.selected').removeClass('selected');
			$(this).addClass('selected');
			selectRoleId = $("#datatable_role").DataTable().rows('.selected').data()[0].FID;
			$('#datatable_user').DataTable().draw();
			initRoleMenu(); 
			$("#datatable_process").DataTable().draw();
			$("#datatable_division").DataTable().draw();
			$("#datatable_department").DataTable().draw();
	    }
	});
}

var initvalidate_role = function() {
	$('#form_role').validate({
        errorElement: 'span',
        errorClass: 'help-block',
        focusInvalid: true,
        rules: {
            name: {
                required: true
            },
            remark: {
            	required: true,
            	maxlength: 250
            }
        },
        messages: {
            name: {
                required: "Department name is required."
            }
        },
        invalidHandler: function (event, validator) { //表单校验失败
			console.log("error");
            //App.scrollTo(error, -200);
        },
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
        	$.post("./SaveRole",data,function(){
        		$("#modal_role").modal("hide");
        		swal("Success!", "", "success");
        		$("#datatable_role").DataTable().draw();
        	});
        }
    });
}

var addRole = function(){
	$("#form_role")[0].reset();
	$("#modal_role").modal("show");
}

var deleteRole = function(){
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
			$.get("./DeleteRole",{"id": selectRoleId},function(){
				swal("Deleted!", "", "success");
				$("#datatable_role").DataTable().draw();
			});
		}
	});
}

var editRole = function(){
	if(selectRoleId!=""){
		$.get("./getRoleInfoById",{"id": selectRoleId},function(data){
			$("#role_id").val(data.FID);
			$("#role_name").val(data.FNAME);
			$("#role_remark").val(data.FREMARK);
			$("#modal_role").modal("show");
		});
	}
}

var initdatatable_user = function(){
	var table = $("#datatable_user");
	table.DataTable({
		serverSide: true,
		ordering: false,
		searching: true,
		order: [[1, "desc"]],
		lengthMenu: [[10,20,50,999999999],[10,20,50,'all']],
      	pageLength: 20,
      	pagingType: "bootstrap_full_number",
      	bStateSave: true,
		dom: 'lftipr',
        ajax: function ( data, callback, settings ) {
       		data.roleid=  selectRoleId;
            $.get("./roleUsers",data,function(result){callback(result)});
        },
        columns: [
        	{render:function(data, type, row){
        		return '<label class="mt-checkbox mt-checkbox-single mt-checkbox-outline">'
		                	+'<input type="checkbox" class="checkboxes" value="'+ row.FID +'" />'
		                    +'<span></span>'
		                +'</label>';
        	}, orderable: false},
        	{data:"USER_CODE", orderable: false},
        	{data:"USER_NAME", orderable: false}
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
	
	table.find('.group-checkable').change(function () {
        var set = jQuery(this).attr("data-set");
        var checked = jQuery(this).is(":checked");
        jQuery(set).each(function () {
            if (checked) {
                $(this).prop("checked", true);
                $(this).parents('tr').addClass("active");
            } else {
                $(this).prop("checked", false);
                $(this).parents('tr').removeClass("active");
            }
        });
    });

    table.on('change', 'tbody tr .checkboxes', function () {
        $(this).parents('tr').toggleClass("active");
    });
}

var addUser = function(){
	var ids = [];
	$("#datatable_modaluser tbody tr .checkboxes:checked").each(function(){
		ids.push($(this).val());
	});
	
	$.post("./addUser",{"roleid": selectRoleId,"ids": ids},function(){
		$("#datatable_user").DataTable().draw();
		$("#modal_user").modal("hide");
	});
}

var deleteUser = function(){
	var ids = [];
	$("#datatable_user tbody tr .checkboxes:checked").each(function(){
		ids.push($(this).val());
	});
	
	$.post("./deleteUser",{"roleid": selectRoleId,"ids": ids},function(){
		$("#datatable_user").DataTable().draw();
	});
}

var zTreeObj;
var zTreesettings={
	check: {
		enable: true,   //true / false 分别表示 显示 / 不显示 复选框或单选框
		autoCheckTrigger: false,   //true / false 分别表示 触发 / 不触发 事件回调函数
		chkStyle: "checkbox",   //勾选框类型(checkbox 或 radio）
		chkboxType: { "Y": "ps", "N": "ps" }   //勾选 checkbox 对于父子节点的关联关系
	},
	view:{
		nameIsHTML: false,
		showIcon: true
	}
}

var zTreesettings2={
		check: {
			enable: false,   //true / false 分别表示 显示 / 不显示 复选框或单选框
			autoCheckTrigger: false,   //true / false 分别表示 触发 / 不触发 事件回调函数
			chkStyle: "checkbox",   //勾选框类型(checkbox 或 radio）
			chkboxType: { "Y": "ps", "N": "ps" }   //勾选 checkbox 对于父子节点的关联关系
		},
		view:{
			nameIsHTML: false,
			showIcon: true
		}
	}

var initRoleMenu = function(){
	$.get("./getRoleMenu",{"roleid": selectRoleId},function(data){
		$.fn.zTree.init($("#roleMenu"), zTreesettings2,data);
	});
}

var editMenu = function(){
	if(selectRoleId!=""){
		$.get("./getRoleMenuState",{"roleid": selectRoleId},function(data){
			zTreeObj = $.fn.zTree.init($("#editMenu"), zTreesettings,data);
		});
		$("#modal_menu").modal("show");
	}
}

var saverolemenu = function(){
	var nodes = zTreeObj.getCheckedNodes(true);
	if(nodes.length==0){
		swal("Error!","没有菜单被选中","error");
	}else{
		var menus = [];
		$.each(nodes,function(i,n){
			menus.push(n.id);
		});
		
		$.post("./SaveRoleMenu",{roleid: selectRoleId,menus: menus},function(data){
			initRoleMenu();
			$("#modal_menu").modal("hide");
		});
	}
}

var initdatatable_process = function(){
	var table = $("#datatable_process");
	table.DataTable({
		serverSide: true,
		ordering: true,
		searching: true,
		order: [[1, "asc"]],
		lengthMenu: [[10,20,50,999999999],[10,20,50,'all']],
      	pageLength: 20,
      	pagingType: "bootstrap_full_number",
      	bStateSave: true,
		dom: 'lftipr',
        ajax: function ( data, callback, settings ) {
       		data.roleid=  selectRoleId;
            $.get("./getProcess",data,function(result){callback(result)});
        },
        columns: [
        	{render:function(data, type, row){
        		return '<label class="mt-checkbox mt-checkbox-single mt-checkbox-outline">'
		                	+'<input type="checkbox" class="checkboxes" value="'+ row.FID +'" />'
		                    +'<span></span>'
		                +'</label>';
        	}, orderable: false},
        	{data:"FKEY", orderable: true},
        	{data:"FNAME", orderable: false}
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
	
	table.find('.group-checkable').change(function () {
        var checked = jQuery(this).is(":checked");
        table.find("tbody .checkboxes").each(function () {
            if (checked) {
                $(this).prop("checked", true);
                $(this).parents('tr').addClass("active");
            } else {
                $(this).prop("checked", false);
                $(this).parents('tr').removeClass("active");
            }
        });
    });

    table.on('change', 'tbody tr .checkboxes', function () {
        $(this).parents('tr').toggleClass("active");
    });
}

var addProcess = function(){
	var ids = [];
	$("#datatable_process_modal tbody tr .checkboxes:checked").each(function(){
		ids.push($(this).val());
	});
	
	$.post("./addProcess",{"roleid": selectRoleId,"ids": ids},function(){
		$("#datatable_process").DataTable().draw();
		$("#modal_process").modal("hide");
	});
}

var deleteProcess = function(){
	var ids = [];
	$("#datatable_process tbody tr .checkboxes:checked").each(function(){
		ids.push($(this).val());
	});
	
	$.post("./deleteProcess",{"roleid": selectRoleId,"ids": ids},function(){
		$("#datatable_process").DataTable().draw();
	});
}

var initdatatable_division = function(){
	var table = $("#datatable_division");
	table.DataTable({
		serverSide: true,
		ordering: true,
		searching: true,
		order: [[1, "asc"]],
		lengthMenu: [[10,20,50,999999999],[10,20,50,'all']],
      	pageLength: 20,
      	pagingType: "bootstrap_full_number",
      	bStateSave: true,
		dom: 'lftipr',
        ajax: function ( data, callback, settings ) {
       		data.roleid =  selectRoleId;
       		data.orgType =  "division";
            $.get("./getRoleOrg",data,function(result){callback(result)});
        },
        columns: [
        	{render:function(data, type, row){
        		return '<label class="mt-checkbox mt-checkbox-single mt-checkbox-outline">'
		                	+'<input type="checkbox" class="checkboxes" value="'+ row.FID +'" />'
		                    +'<span></span>'
		                +'</label>';
        	}, orderable: false},
        	{data:"FNUMBER", orderable: true},
        	{data:"FLONGNAME", orderable: false}
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
	
	table.find('.group-checkable').change(function () {
        var checked = jQuery(this).is(":checked");
        table.find("tbody .checkboxes").each(function () {
            if (checked) {
                $(this).prop("checked", true);
                $(this).parents('tr').addClass("active");
            } else {
                $(this).prop("checked", false);
                $(this).parents('tr').removeClass("active");
            }
        });
    });

    table.on('change', 'tbody tr .checkboxes', function () {
        $(this).parents('tr').toggleClass("active");
    });
}

var initdatatable_department = function(){
	var table = $("#datatable_department");
	table.DataTable({
		serverSide: true,
		ordering: true,
		searching: true,
		order: [[1, "asc"]],
		lengthMenu: [[10,20,50,999999999],[10,20,50,'all']],
      	pageLength: 20,
      	pagingType: "bootstrap_full_number",
      	bStateSave: true,
		dom: 'lftipr',
        ajax: function ( data, callback, settings ) {
       		data.roleid =  selectRoleId;
       		data.orgType =  "department";
            $.get("./getRoleOrg",data,function(result){callback(result)});
        },
        columns: [
        	{render:function(data, type, row){
        		return '<label class="mt-checkbox mt-checkbox-single mt-checkbox-outline">'
		                	+'<input type="checkbox" class="checkboxes" value="'+ row.FID +'" />'
		                    +'<span></span>'
		                +'</label>';
        	}, orderable: false},
        	{data:"FNUMBER", orderable: true},
        	{data:"FLONGNAME", orderable: false}
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
	
	table.find('.group-checkable').change(function () {
        var checked = jQuery(this).is(":checked");
        table.find("tbody .checkboxes").each(function () {
            if (checked) {
                $(this).prop("checked", true);
                $(this).parents('tr').addClass("active");
            } else {
                $(this).prop("checked", false);
                $(this).parents('tr').removeClass("active");
            }
        });
    });

    table.on('change', 'tbody tr .checkboxes', function () {
        $(this).parents('tr').toggleClass("active");
    });
}

var addOrg = function(orgType){
	var ids = [];
	$("#datatable_org_modal tbody tr .checkboxes:checked").each(function(){
		ids.push($(this).val());
	});
	
	$.post("./addOrg",{"roleid": selectRoleId,"orgType": orgType,"ids": ids},function(){
		$("#datatable_"+orgType).DataTable().draw();
		$("#modal_org").modal("hide");
	});
}

var deleteOrg = function(orgType){
	var ids = [];
	$("#datatable_"+orgType+" tbody tr .checkboxes:checked").each(function(){
		ids.push($(this).val());
	});
	
	$.post("./deleteOrg",{"roleid": selectRoleId,"ids": ids},function(){
		$("#datatable_"+orgType).DataTable().draw();
	});
}

</script>
</tempus:Content>

</tempus:ContentPage>