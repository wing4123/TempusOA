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
    <title><i18n:message code="usermanage.title"  /></title>
    <link href="../assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
    <link href="../assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
    <link href="../assets/global/plugins/jstree/dist/themes/default/style.min.css" rel="stylesheet" type="text/css" />
    <link href="../assets/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css" />
    <link href="../assets/plugins/zTree_v3/css/metroStyle/metroStyle.css" rel="stylesheet" type="text/css" />
    <link href="../assets/global/plugins/bootstrap-select/css/bootstrap-select.css" rel="stylesheet" type="text/css" />
</tempus:Content>

<tempus:Content contentPlaceHolderId="main">
<div class="page-content" style="min-height: 1147px;">
    <!-- BEGIN PAGE HEAD-->
    <div class="page-head">
        <!-- BEGIN PAGE TITLE -->
        <div class="page-title">
            <h1><i18n:message code="usermanage.title"  /></h1>
        </div>
        <!-- END PAGE TITLE -->
    </div>
    <!-- END PAGE HEAD-->
    <!-- BEGIN PAGE BASE CONTENT -->
   	<div class="row">
   		<div class="col-md-3">
	   		<div class="portlet box blue-hoki">
	            <div class="portlet-title">
	                <div class="caption"><i class="fa fa-gift"></i>部门</div>
	                <div class="tools">
					    <a href="javascript:;" class="collapse" data-original-title="" title=""> </a>
					</div>
	                <div class="actions">
	                	<button class="btn btn-default btn-sm" onclick="adddept()"><i class="fa fa-plus"></i><i18n:message code="common.add" /></button>
	                    <button class="btn btn-default btn-sm" onclick="editdept()"><i class="fa fa-pencil"></i><i18n:message code="common.edit" /></button>
	                    <button class="btn btn-default btn-sm" onclick="deletedept()"><i class="fa fa-trash"></i><i18n:message code="common.delete" /></button>
	                </div>
	            </div>
	            <div class="portlet-body">
	            	<form id="includechild_dept" class="form-horizontal" style="margin: -15px 0px;" >
	            		<div class="form-group">
		                    <label class="col-md-3 control-label">包含下级:</label>
		                    <div class="col-md-9">
		                        <div class="mt-radio-inline">
		                            <label class="mt-radio">
		                                <input type="radio" name="includechild" value="1" checked="checked" onclick='refreshuserlist_department()'> 是  <span></span>
		                            </label>
		                            <label class="mt-radio">
		                                <input type="radio" name="includechild" value="0" onclick='refreshuserlist_department()'> 否   <span></span>
		                            </label>
		                        </div>
		                    </div>
		                </div>
	            	</form>
	            	<div id="tree_dept" class="tree-demo"></div>
				</div>
	        </div>
	        
	        <div class="portlet box blue-hoki">
	            <div class="portlet-title">
	                <div class="caption"><i class="fa fa-gift"></i>事业群</div>
	                <div class="tools">
					    <a href="javascript:;" class="collapse" data-original-title="" title=""> </a>
					</div>
	                <div class="actions">
	                	<button class="btn btn-default btn-sm" onclick="adddivision()"><i class="fa fa-plus"></i><i18n:message code="common.add" /></button>
	                    <button class="btn btn-default btn-sm" onclick="editdivision()"><i class="fa fa-pencil"></i><i18n:message code="common.edit" /></button>
	                    <button class="btn btn-default btn-sm" onclick="deletedivision()"><i class="fa fa-trash"></i><i18n:message code="common.delete" /></button>
	                </div>
	            </div>
	            <div class="portlet-body">
	            	<form id="includechild_division" class="form-horizontal" style="margin: -15px 0px;" >
	            		<div class="form-group">
		                    <label class="col-md-3 control-label">包含下级:</label>
		                    <div class="col-md-9">
		                        <div class="mt-radio-inline">
		                            <label class="mt-radio">
		                                <input type="radio" name="includechild" value="1" checked="checked" onclick='refreshuserlist_division()'> 是  <span></span>
		                            </label>
		                            <label class="mt-radio">
		                                <input type="radio" name="includechild" value="0" onclick='refreshuserlist_division()'> 否   <span></span>
		                            </label>
		                        </div>
		                    </div>
		                </div>
	            	</form>
	            	<div id="tree_division" class="ztree"></div>
				</div>
	        </div>
        
        </div>
        <div class="col-md-9 portlet box blue-hoki">
            <div class="portlet-title">
                <div class="caption"><i class="fa fa-user"></i>User</div>
                <div class="actions">
                    <a href="javascript:adduser();" class="btn btn-default btn-sm"><i class="fa fa-plus"></i>Add</a>
                </div>
            </div>
            <div class="portlet-body">
				<table id="datatable" class="table table-striped table-bordered table-hover table-checkable order-column">
		            <thead>
		                <tr>
		                	<th>USER_ID</th>
		                    <th>工号</th>
		                    <th>姓名</th>
		                    <th>部门</th>
		                    <th>事业部</th>
		                    <th>生效日期</th>
		                    <th>Action</th>
		                </tr>
		            </thead>
		            <tbody></tbody>
	            </table>
            </div>
        </div>
   	</div>
    <!-- END PAGE BASE CONTENT -->
</div>
<div id="modal_add" class="modal fade in" tabindex="-1" data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title"><i18n:message code="common.add" /></h4>
            </div>
            <div class="modal-body" style="padding: 20px;">
            	<form id="form_adddept" class="form-horizontal" role="from">
            		<input id="dept_id" name="id" type="hidden"/>
            		<div class="from-body">
            			<div class="form-group">
            				<label class="col-md-3 control-label"><i18n:message code="common.name" /><span class="required">*</span></label>
            				<div class="col-md-9"><input id="dept_name" name="name" class="form-control" placeholder=""/></div>
            			</div>
            			<div class="form-group">
            				<label class="col-md-3 control-label"><i18n:message code="usermanage.parentname" /></label>
            				<div class="col-md-9"><input id="dept_parentname" class="form-control" placeholder="" readonly="readonly" onclick="showparentdept()"/></div>
            				<input id="dept_parentid" name="parentid" type="hidden"/>
            			</div>
            			<div class="form-group">
            				<label class="col-md-3 control-label"><i18n:message code="部门负责人" /></label>
            				<div class="col-md-9"><input id="dept_leadername" class="form-control" placeholder="" readonly="readonly" onclick="showmodal_leader()"/></div>
            				<input id="dept_leaderid" name="leader" type="hidden"/>
            			</div>
            			<div class="form-group">
            				<label class="col-md-3 control-label"><i18n:message code="组织类型" /></label>
            				<div class="col-md-9">
	            				<div class="mt-radio-inline" id="dept_type">
		                            <label class="mt-radio">
		                                <input type="radio" name="type" value="1" checked> 部门 <span></span>
		                            </label>
		                            <label class="mt-radio">
		                                <input type="radio" name="type" value="2"> 公司 <span></span>
		                            </label>
		                        </div>
	                        </div>
            			</div>
            			<div class="form-group">
            				<label class="col-md-3 control-label"><i18n:message code="common.remark" /></label>
            				<div class="col-md-9"><textarea id="dept_remark" name="remark" class="form-control"></textarea></div>
            			</div>
            		</div>
            	</form>
            </div>
            <div class="modal-footer">
                <button type="button" data-dismiss="modal" class="btn dark btn-outline"><i18n:message code="common.cancel" /></button>
                <button type="submit" class="btn green" onclick="$('#form_adddept').submit()"><i18n:message code="common.save" /></button>
            </div>
        </div>
    </div>
</div>

<div id="modal_user" class="modal fade in" tabindex="-1" data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">修改用户信息</h4>
            </div>
            <div class="modal-body" style="padding: 20px;">
            	<form id="form_user" class="form-horizontal" role="from">
            		<div class="from-body">
            			<div class="form-group">
            				<label class="col-md-3 control-label">工号<span class="required">*</span></label>
            				<div class="col-md-9"><input id="user_code" name="user_code" class="form-control" placeholder=""/></div>
            				<input id="user_id" name="user_id" type="hidden"/>
            			</div>
            			<div class="form-group">
            				<label class="col-md-3 control-label">姓名<span class="required">*</span></label>
            				<div class="col-md-9"><input id="user_name" name="user_name" class="form-control" placeholder="" /></div>
            			</div>
            			<div class="form-group">
            				<label class="col-md-3 control-label">生日</label>
            				<div class="col-md-9">
                                <div class="input-group date date-picker" data-date-format="yyyy-mm-dd" >
                                    <input id="user_birthday" name="user_birthday" class="form-control" placeholder=""/>
                                    <span class="input-group-btn">
                                        <button class="btn default" type="button">
                                            <i class="fa fa-calendar"></i>
                                        </button>
                                    </span>
                                </div>
                            </div>
            			</div>
            			<div class="form-group">
            				<label class="col-md-3 control-label">手机</label>
            				<div class="col-md-9"><input id="user_phone" name="user_phone" class="form-control" placeholder=""/></div>
            			</div>
            			<div class="form-group">
            				<label class="col-md-3 control-label">Email</label>
            				<div class="col-md-9">
	            				<div class="input-group">
		                            <span class="input-group-addon">
		                                <i class="fa fa-envelope"></i>
		                            </span>
		                            <input id="user_email" name="user_email" class="form-control"/>
	                            </div>
                            </div>
            			</div>
            			<div class="form-group">
            				<label class="col-md-3 control-label">部门<span class="required">*</span></label>
            				<div class="col-md-9"><input id="user_departmentname" name="user_departmentname" class="form-control" placeholder="" readonly="readonly" onclick="showparentdept()" style="cursor: pointer;" /></div>
            				<input id="user_departmentid" name="user_departmentid" type="hidden"/>
            			</div>
            			<div class="form-group">
            				<label class="col-md-3 control-label">直属领导<span class="required">*</span></label>
            				<div class="col-md-9"><input id="user_leadername" class="form-control" placeholder="" readonly="readonly" onclick="showmodal_leader()" style="cursor: pointer;" /></div>
            				<input id="user_leaderid" name="user_leaderid" type="hidden"/>
            			</div>
            			<div class="form-group">
            				<label class="col-md-3 control-label">事业群<span class="required">*</span></label>
            				<div class="col-md-9"><input id="user_divisionname" name="user_divisionname" class="form-control" placeholder="" readonly="readonly" onclick="showparentdivision()" style="cursor: pointer;" /></div>
            				<input id="user_divisionid" name="user_divisionid" type="hidden"/>
            			</div>
            			<div class="form-group">
            				<label class="col-md-3 control-label">职位</label>
            				<div class="col-md-9">
	            				<select id="user_position" name="user_position" class="bs-select form-control" data-live-search="true" data-size="8" multiple>
		                        </select>
            				</div>
            			</div>
            			
            		</div>
            	</form>
            </div>
            <div class="modal-footer">
                <button type="button" data-dismiss="modal" class="btn dark btn-outline"><i18n:message code="common.cancel" /></button>
                <button type="submit" class="btn green" onclick="$('#form_user').submit()"><i18n:message code="common.save" /></button>
            </div>
        </div>
    </div>
</div>

<div id="modal_parentdept" class="modal fade in" tabindex="-1" data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">部门</h4>
            </div>
            <div class="modal-body" style="padding: 20px;">
            	<div id="tree_parentdept" class="tree-demo"></div>
            </div>
            <div class="modal-footer">
                <button type="button" data-dismiss="modal" class="btn dark btn-outline"><i18n:message code="common.cancel" /></button>
                <button type="button" data-dismiss="modal" class="btn green" onclick="selectparentdept()"><i18n:message code="common.save" /></button>
            </div>
        </div>
    </div>
</div>

<div id="modal_division_add" class="modal fade in" tabindex="-1" data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title"><i18n:message code="common.add" /></h4>
            </div>
            <div class="modal-body" style="padding: 20px;">
            	<form id="form_adddivision" class="form-horizontal" role="from">
            		<input id="division_id" name="id" type="hidden"/>
            		<div class="from-body">
            			<div class="form-group">
            				<label class="col-md-3 control-label"><i18n:message code="common.name" /><span class="required">*</span></label>
            				<div class="col-md-9"><input id="division_name" name="name" class="form-control" placeholder=""/></div>
            			</div>
            			<div class="form-group">
            				<label class="col-md-3 control-label"><i18n:message code="上级事业部" /></label>
            				<div class="col-md-9"><input id="division_parentname" class="form-control" placeholder="" readonly="readonly" onclick="showparentdivision()"/></div>
            				<input id="division_parentid" name="parentid" type="hidden"/>
            			</div>
            			<div class="form-group">
            				<label class="col-md-3 control-label"><i18n:message code="负责人" /></label>
            				<div class="col-md-9"><input id="division_leadername" class="form-control" placeholder="" readonly="readonly" onclick="showmodal_leader()"/></div>
            				<input id="division_leaderid" name="leader" type="hidden"/>
            			</div>
            			<div class="form-group">
            				<label class="col-md-3 control-label"><i18n:message code="级别" /></label>
            				<div class="col-md-9">
	            				<select id="division_level" name="level" class="form-control">
		                        	<option value="1">无</option>
		                        	<option value="2">事业部分支机构</option>
		                        	<option value="3">事业部</option>
		                        	<option value="4">事业群</option>
		                        	<option value="5">总部职能部门</option>
		                        </select>
	                        </div>
            			</div>
            			<div class="form-group">
            				<label class="col-md-3 control-label"><i18n:message code="common.remark" /></label>
            				<div class="col-md-9"><textarea id="division_remark" name="remark" class="form-control"></textarea></div>
            			</div>
            		</div>
            	</form>
            </div>
            <div class="modal-footer">
                <button type="button" data-dismiss="modal" class="btn dark btn-outline"><i18n:message code="common.cancel" /></button>
                <button type="submit" class="btn green" onclick="$('#form_adddivision').submit()"><i18n:message code="common.save" /></button>
            </div>
        </div>
    </div>
</div>

<div id="modal_userpermission" class="modal fade bs-modal-lg" tabindex="-1" data-backdrop="static" >
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
		    <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
		        <h4 class="modal-title">用户组织权限</h4>
		    </div>
		    <div class="modal-body" style="padding: 20px;max-height: 700px;overflow-y: auto;">
		    	<table id="table_userpermission" class="table table-striped table-bordered table-hover table-checkable order-column" >
		            <thead>
		                <tr>
		                    <th>组织名称</th>
		                    <th>组织编号</th>
		                    <th>操作</th>
		                </tr>
		            </thead>
		            <tbody></tbody>
	            </table>
		    </div>
		    <div class="modal-footer">
		        <button type="button" class="btn green" onclick="showparentdivision()"><i18n:message code="添加" /></button>
		        <button type="button" data-dismiss="modal" class="btn dark btn-outline"><i18n:message code="关闭" /></button>
		    </div>
		</div>
	</div>
</div>

<div id="modal_parentdivision" class="modal fade in" tabindex="-1" data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">事业部</h4>
            </div>
            <div class="modal-body" style="padding: 20px;">
            	<div id="tree_parentdivision" class="ztree"></div>
            </div>
            <div class="modal-footer">
                <button type="button" data-dismiss="modal" class="btn dark btn-outline"><i18n:message code="common.cancel" /></button>
                <button type="button" class="btn green" onclick="selectparentdivision()"><i18n:message code="common.save" /></button>
            </div>
        </div>
    </div>
</div>

<div id="modal_leader" class="modal fade bs-modal-lg" tabindex="-1" data-backdrop="static" >
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
		    <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
		        <h4 class="modal-title">选择直属领导</h4>
		    </div>
		    <div class="modal-body" style="padding: 20px;max-height: 700px;overflow-y: auto;">
		    	<table id="datatable_leader" class="table table-striped table-bordered table-hover table-checkable order-column" >
		            <thead>
		                <tr>
		                    <th>工号</th>
		                    <th>姓名</th>
		                    <th>部门</th>
		                </tr>
		            </thead>
		            <tbody></tbody>
	            </table>
		    </div>
		    <div class="modal-footer">
		        <button type="button" data-dismiss="modal" class="btn dark btn-outline"><i18n:message code="common.cancel" /></button>
		        <button type="button" class="btn green" onclick="selectuser()"><i18n:message code="确定" /></button>
		    </div>
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
<script src="../assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/bootstrap-datepicker/locales/bootstrap-datepicker.<%=locale %>.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/jquery-validation/js/localization/messages_<%=locale %>.min.js" type="text/javascript"></script>
<script src="../assets/plugins/zTree_v3/js/jquery.ztree.core.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/bootstrap-select/js/bootstrap-select.min.js" type="text/javascript"></script>
<script>
var scope="user_department";
var userid;
$(function(){
	initjstree1();
	initjstree2();
	initztree_division();
	initdatatable();
	initvalidate();
	initvalidate_user();
	$('.date-picker').datepicker({
        autoclose: true,
        language: "<%=locale %>"
    });
	initdatatable_leader();
	initvalidate_division();
	
	initposition();
});

var datatablefilter={};
var initdatatable = function(){
	$("#datatable").DataTable({
		serverSide: true,
		ordering: true,
		searching: true,
		order: [[0, "asc"]],
		lengthMenu: [[10,20,50,100],[10,20,50,100]],
      	pageLength: 20,
      	pagingType: "bootstrap_full_number",
		//dom: '<<t>lp>',
		ajax: {
	        url: '../UserManage/getUserList',
	        //data: {includechild:$('input:radio[name="includechild"]:checked').val(), deptnum: $("#tree_dept").jstree().get_selected(true)[0]==null?"":$("#tree_parentdept").jstree().get_selected(true)[0].FNUMBER}
	    	data: function(data){
	    		//data.includechild=$('input:radio[name="includechild"]:checked').val();
	    		//data.deptnum=$("#tree_dept").jstree().get_selected(true)[0]==null?"":$("#tree_dept").jstree().get_selected(true)[0].a_attr.number;
	    		$.extend(data, datatablefilter);
	    	}
		},
	    columns: [
	    	{data:"USER_ID"},
	    	{data:"USER_CODE"},
	    	{data:"USER_NAME"},
	    	{data:"DEPARTMENTNAME"},
	    	{data:"DIVISIONNAME"},
	    	{data:"START_ACTIVE_DATE"},
	    	{orderable: false,render:function(data, type, row){
	    		return '<div class="btn-group">'
				    		+'<button class="btn btn-xs green dropdown-toggle" type="button" data-toggle="dropdown" aria-expanded="false">'
				    			+'<i class="fa fa-angle-down"></i> Actions'
			    			+'</button>'
			    			+'<ul class="dropdown-menu" role="menu">'
		    					+'<li><a href="javascript:edituser(\''+row.USER_ID+'\');"><span class="icon-docs"></span>修改</a></li>'
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

var initjstree1 = function(){
	$("#tree_dept").jstree({
        core : {
            themes : {
                responsive: false
            }, 
            check_callback : true,
            data : {
                url :'../DepartmentManage/getDepartmentTree'
            }
        },
        types : {
            "default" : {
                icon : "fa fa-folder icon-state-warning icon-lg"
            },
            file : {
                icon : "fa fa-file icon-state-warning icon-lg"
            }
        },
        state : { key : "demo3" },
        plugins : [ "dnd", "state", "types" ]
    }).bind('click.jstree', function(event) {
    	refreshuserlist_department();
    });
}

var refreshuserlist_department = function(){
	var deptnum=$("#tree_dept").jstree().get_selected(true)[0];
	$('input:radio[name="sex"]:checked').val();
	datatablefilter={"includechild":$("#includechild_dept input:radio[name='includechild']:checked").val(),deptnum:deptnum==null?"":deptnum.a_attr.number}
	$("#datatable").DataTable().draw();
}

var initjstree2 = function(){
	$("#tree_parentdept").jstree({
        core : {
            themes : {
                responsive: false
            }, 
            check_callback : true,
            data : []
        },
        types : {
            "default" : {
                icon : "fa fa-folder icon-state-warning icon-lg"
            },
            file : {
                icon : "fa fa-file icon-state-warning icon-lg"
            }
        },
        state : { key : "demo3" },
        plugins : [ "dnd", "state", "types" ]
    });
}

var adddept = function(){
	//$("#form_adddept")[0].reset();
	$("#form_adddept :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected");
	$("#modal_add").modal("show");
	
	$.get("../DepartmentManage/getParentDepartmentTree",{"id":""},function(data){
		$("#tree_parentdept").jstree(true).settings.core.data=data; 
		$("#tree_parentdept").jstree(true).refresh(); 
	});
	scope="dept_parent";
}

var showparentdept = function(){
	$("#modal_parentdept").modal("show");
}

var selectparentdept = function(){
	var parentdept = $("#tree_parentdept").jstree().get_selected(true)[0];
	$("#"+scope+"name").val(parentdept.text);
	$("#"+scope+"id").val(parentdept.id);
}

var initvalidate = function() {
	$('#form_adddept').validate({
        errorElement: 'span',
        errorClass: 'help-block',
        focusInvalid: true,
        rules: {
            name: {
                required: true
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
        	$.post("../DepartmentManage/SaveDepartment",data,function(){
        		$("#modal_add").modal("hide");
        		swal("Success!", "", "success");
        		$("#tree_dept").jstree(true).refresh();
        	});
        }
    });
}

var deletedept = function(){
	var dept = $("#tree_dept").jstree().get_selected(true)[0];
	if(dept.children.length>0){
		swal("Error!", "该部门下有子部门，无法删除", "error");
	}else{
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
				$.get("../DepartmentManage/DeleteDepartment",{"id":dept.id},function(){
					swal("Deleted!", "", "success");
					$("#tree_dept").jstree(true).refresh();
				});
			}
		});
	}
}

var editdept = function(){
	var parent = $("#tree_dept").jstree().get_selected(true)[0];
	if(parent==null){
		swal("Error!", "请选择需要修改的部门！", "error");
	}else{
		$.get("../DepartmentManage/getDepartmentInfoById",{"id":parent==null?"":parent.id},function(data){
			$("#dept_id").val(data.FID);
			$("#dept_name").val(data.FNAME);
			$("#dept_remark").text(data.FREMARK);
			$("#dept_parentname").val(data.FPARENTNAME);
			$("#dept_parentid").val(data.FPARENTID);
			$("#dept_leadername").val(data.FLEADERNAME);
			$("#dept_leaderid").val(data.FLEADERID);
			$("#dept_type input[value='"+data.FTYPE+"']").prop("checked",true);
			$("#modal_add").modal("show");
		});
		
		$.get("../DepartmentManage/getParentDepartmentTree",{"id":parent==null?"":parent.id},function(data){
			$("#tree_parentdept").jstree(true).settings.core.data=data; 
			$("#tree_parentdept").jstree(true).refresh(); 
		});
		scope="dept_parent";
	}
}

var edituser = function(id){
	getuserposition(id);
	$.get("./getUserInfoById",{"id":id},function(data){
		$("#user_id").val(data.USER_ID);
		$("#user_code").val(data.USER_CODE);
		$("#user_name").val(data.USER_NAME);
		$("#user_phone").val(data.CONTACT_TEL_NUM);
		$("#user_email").val(data.EMAIL_ADDRESS);
		$("#user_birthday").val(data.DATE_OF_BIRTH);
		$("#user_departmentid").val(data.FDEPARTMENTID);
		$("#user_departmentname").val(data.FDEPARTMENTNAME);
		$("#user_leaderid").val(data.FLEADERID);
		$("#user_leadername").val(data.FLEADERNAME);
		$("#user_divisionid").val(data.FDIVISIONID);
		$("#user_divisionname").val(data.FDIVISIONNAME);
		
		/* var positionhtml="";
		for(var i=0;i<data.position.length;i++){
			positionhtml=positionhtml+"<option value='"+data.position[i].FID+"' "+data.position[i].FSELECTED+" >"+data.position[i].FNAME+"</option>"
		}
		$("#user_position").append(positionhtml); */
		
		$("#modal_user").modal("show");
		
		$.get("../DepartmentManage/getParentDepartmentTree",{"id":""},function(data){
			$("#tree_parentdept").jstree(true).settings.core.data=data; 
			$("#tree_parentdept").jstree(true).refresh(); 
		});
		scope="user_department";
		
		$.get("../DepartmentManage/getParentDivisionTree",{"id":""},function(data){
			zTreeObj = $.fn.zTree.init($("#tree_parentdivision"), zTreesettings,data);
		});
	});
}

var adduser = function(){
	$("#form_user").get(0).reset();
	$("#modal_user").modal("show");
	
	$.get("../DepartmentManage/getParentDepartmentTree",{"id":""},function(data){
		$("#tree_parentdept").jstree(true).settings.core.data=data; 
		$("#tree_parentdept").jstree(true).refresh(); 
	});
	scope="user_department";
	
	$.get("../DepartmentManage/getParentDivisionTree",{"id":""},function(data){
		zTreeObj = $.fn.zTree.init($("#tree_parentdivision"), zTreesettings,data);
	});
}

var initvalidate_user = function() {
	$('#form_user').validate({
        errorElement: 'span',
        errorClass: 'help-block',
        focusInvalid: true,
        rules: {
            user_code: {
                required: true
            },
            user_name: {
                required: true
            },
            user_birthday: {
                required: false,
                date: true
            },
            user_phone: {
                required: false,
                digits: true,
                maxlength: 11,
                minlength: 11
            },
            user_email: {
                required: false,
                email: true
            },
            user_departmentname: {
                required: true
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
        	$.post("./SaveUser",data,function(){
        		$("#modal_user").modal("hide");
        		swal("Success!", "修改成功", "success");
        		$("#datatable").DataTable().draw();
        	});
        }
    });
}

var initdatatable_leader = function(){
	var datatable = $("#datatable_leader");

	datatable.DataTable({
		serverSide: true,
		ordering: true,
		searching: true,
		order: [[0, "asc"]],
		lengthMenu: [[10,20,50,100],[10,20,50,100]],
      	pageLength: 10,
      	pagingType: "bootstrap_full_number",
		//dom: '<<t>lp>',
		ajax: {
	        url: './getAllUserList'
		},
	    columns: [
	    	{data:"USER_CODE"},
	    	{data:"USER_NAME"},
	    	{data:"DEPARTMENTNAME"}
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
	       	$(this).removeClass('selected');
	    } else {
	    	datatable.find('tr.selected').removeClass('selected');
			$(this).addClass('selected');
	    }
	});
}

var showmodal_leader = function(){
	$("#modal_leader").modal("show");
}

var selectuser = function(){
	var data = $("#datatable_leader").DataTable().rows('.selected').data();
	if(data.length==0){
		swal("Error!", "没有选择人员", "error");
	}else{
		if(!$("#modal_user").is(":hidden")){
			$("#user_leadername").val(data[0].USER_NAME);
			$("#user_leaderid").val(data[0].USER_ID);
			$("#modal_leader").modal("hide");
		}else if(!$("#modal_division_add").is(":hidden")){
			$("#division_leadername").val(data[0].USER_NAME);
			$("#division_leaderid").val(data[0].USER_ID);
			$("#modal_leader").modal("hide");
		}else if(!$("#modal_add").is(":hidden")){
			$("#dept_leadername").val(data[0].USER_NAME);
			$("#dept_leaderid").val(data[0].USER_ID);
			$("#modal_leader").modal("hide");
		}
		
		console.log(data[0]);
	}
}

var adddivision = function(){
	//$("#form_adddivision")[0].reset();
	$("#form_adddivision :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected");
	$("#modal_division_add").modal("show");
	
	$.get("../DepartmentManage/getParentDivisionTree",{"id":""},function(data){
		zTreeObj = $.fn.zTree.init($("#tree_parentdivision"), zTreesettings,data);
	});
}

var zTreeObj;
var zTreesettings={
	view:{
		showIcon: true
	},
	callback: {
		onClick: function(event, treeId, treeNode){
			if(treeId=="tree_division"){
				refreshuserlist_division();
			}
		}
	}
}

var initztree_division = function(){
	$.get("../DepartmentManage/getDivisionTree",{},function(data){
		zTreeObj = $.fn.zTree.init($("#tree_division"), zTreesettings,data);
	});
}

var showparentdivision = function(){
	//var treeObj = $.fn.zTree.getZTreeObj("tree_division");
	//var id = treeObj.getSelectedNodes()[0].id;
	
	$("#modal_parentdivision").modal("show");
}

var selectparentdivision = function(){
	var parentdivision = $.fn.zTree.getZTreeObj("tree_parentdivision").getSelectedNodes();
	if(parentdivision.length==1){
		if(!$("#modal_user").is(":hidden")){
			$("#user_divisionname").val(parentdivision[0].name);
			$("#user_divisionid").val(parentdivision[0].id);
			$("#modal_parentdivision").modal("hide");
		}else if(!$("#modal_userpermission").is(":hidden")){
			$("#division_parentname").val(parentdivision[0].name);
			$("#division_parentid").val(parentdivision[0].id);
			$.get("../PermissionManage/addUserPermission",{"userid": userid,"divisionid": parentdivision[0].id},function(){
				getuserpermission(userid);
				$("#modal_parentdivision").modal("hide");
			});
		}else{
			$("#division_parentname").val(parentdivision[0].name);
			$("#division_parentid").val(parentdivision[0].id);
			$("#modal_parentdivision").modal("hide");
		}
	}
}

var initvalidate_division = function() {
	$('#form_adddivision').validate({
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
        	$.post("../DepartmentManage/SaveDivision",data,function(){
        		$("#modal_division_add").modal("hide");
        		swal("Success!", "保存成功", "success");
        		initztree_division();
        	});
        }
    });
}

var deletedivision = function(){
	var division = $.fn.zTree.getZTreeObj("tree_division").getSelectedNodes();
	if(division.length==0){
		swal("Error!", "请选择需要删除的事业部", "error");
	}else if(division[0].children.length>0){
		swal("Error!", "该事业部包含下级事业部，无法删除", "error");
	}else{
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
				$.get("../DepartmentManage/DeleteDivision",{"id":division[0].id},function(){
					swal("Deleted!", "删除成功", "success");
					initztree_division();
				});
			}
		});
	}
}

var editdivision = function(){
	var division = $.fn.zTree.getZTreeObj("tree_division").getSelectedNodes();
	if(division.length==0){
		swal("Error!", "请选择需要修改的部门！", "error");
	}else{
		$("#modal_division_add").modal("show");
		$.get("../DepartmentManage/getDivisionInfoById",{"id":division[0].id},function(data){
			$("#division_id").val(data.FID);
			$("#division_name").val(data.FNAME);
			$("#division_remark").text(data.FREMARK);
			$("#division_parentname").val(data.FPARENTNAME);
			$("#division_parentid").val(data.FPARENTID);
			$("#division_leadername").val(data.LEADERNAME);
			$("#division_leaderid").val(data.LEADERID);
			$("#division_level option[value='"+data.FLEVEL+"']").prop("selected",true);
			
			$("#modal_division_add").modal("show");
		});
		console.log(division[0].id);
		$.get("../DepartmentManage/getParentDivisionTree",{"id":division[0].id},function(data){
			zTreeObj = $.fn.zTree.init($("#tree_parentdivision"), zTreesettings,data);
		});
	}
}

var refreshuserlist_division = function(){
	var treeNode = $.fn.zTree.getZTreeObj("tree_division").getSelectedNodes();
	datatablefilter={includechild: $("#includechild_division input:radio[name='includechild']:checked").val(),divisionnum: treeNode.length==0?"":treeNode[0].FNUMBER}
	$("#datatable").DataTable().draw();
}

var initposition = function(){
	$.get("../PositionManage/SelectPositionList",{},function(data){
		//var html="";
		for(var i=0;i<data.length;i++){
			$("#user_position").append("<option id='"+data[i].FID+"' value='"+data[i].FID+"' >"+data[i].FNAME+"</option>");
		}
		//$("#user_position").html(html);
		$("#user_position").selectpicker();
	});
}

var getuserposition = function(userid){
	console.log(userid);
	$.get("../PositionManage/getUserPosition",{"userid":userid},function(data){
		$("#user_position option").prop("selected", false);
		for(var i=0;i<data.length;i++){
			$("#"+data[i].FPOSITIONID).prop("selected",true);
		}
		//$("#user_position").selectpicker();
		$("#user_position").selectpicker('refresh');
		$("#user_position").selectpicker('render');
	});
}

var getuserpermission = function(userid){
	window.userid = userid;
 	$.get("../PermissionManage/getUserPermission",{"userid": userid},function(data){
		var html = "";
		for(var i=0;i<data.length;i++){
			html = html+"<tr><td>"+data[i].FNAME+"</td><td>"+data[i].FNUMBER+"</td><td><a href=javascript:deleteuserpermission('"+data[i].FID+"','"+data[i].FUSERID+"')>删除</a></td></tr>";
		}
		$("#table_userpermission tbody").html(html);
		$("modal_userpermission").modal("show");
	});
	$("#modal_userpermission").modal("show");
	$.get("../DepartmentManage/getParentDivisionTree",{"id":""},function(data){
		zTreeObj = $.fn.zTree.init($("#tree_parentdivision"), zTreesettings,data);
	});
}

var deleteuserpermission = function(id,userid){
	$.get("../PermissionManage/deleteUserPermission",{"id": id},function(data){
		getuserpermission(userid);
	});
}

var uuid=function() {
	return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
		var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
		return v.toString(16);
	});
}

</script>
</tempus:Content>

</tempus:ContentPage>