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
    <title><i18n:message code="我发起的流程" /></title>
    <link href="../assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
    <link href="../assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
</tempus:Content>

<tempus:Content contentPlaceHolderId="main">
<div class="page-content" style="min-height: 1147px;">
    <!-- BEGIN PAGE HEAD-->
    <div class="page-head">
        <!-- BEGIN PAGE TITLE -->
        <div class="page-title">
            <h1><i18n:message code="我发起的流程"  /></h1>
        </div>
        <!-- END PAGE TITLE -->
    </div>
    <!-- END PAGE HEAD-->
    <!-- BEGIN PAGE BASE CONTENT -->
    <div style="background-color: white;padding: 20px;">
    	<div class="row">
	   		<div class="form-group">
		   		<div class="col-md-6">
		            <label class="control-label pull-left">流程是否结束:</label>
		            <div class="mt-radio-inline pull-left" style="padding: 0px 10px;">
		                <label class="mt-radio">
		                    <input type="radio" name="isend" value="" checked onclick='datatablerefresh()'> 全部  <span></span>
		                </label>
		                <label class="mt-radio">
		                    <input type="radio" name="isend" value="0" onclick='datatablerefresh()'> 未结束  <span></span>
		                </label>
		                <label class="mt-radio">
		                    <input type="radio" name="isend" value="1" onclick='datatablerefresh()'> 已结束   <span></span>
		                </label>
		            </div>
		        </div>
		        <div class="col-md-6">
		        	<label class="control-label pull-left">流程类别:</label>
		        	<select id="processType" class="pull-left" style="margin-left: 10px;" onchange='datatablerefresh()'>
		        		<option value="">全部</option>
			        	<c:forEach items="${allProcess}" var="row" varStatus="s">
			        		<option value="${row.PROC_KEY}">${row.PROC_NAME}</option>
			        	</c:forEach>
		        	</select>
		        </div>
	        </div>
        </div>
   		<table id="datatable" class="table table-striped table-bordered table-hover table-checkable order-column">
	        <thead>
	            <tr>
	            	<th>流程ID</th>
	            	<th>单据标题</th>
	            	<th>流程发起时间</th>
	            	<th>流程结束时间</th>
	                <th>Action</th>
	            </tr>
	        </thead>
	        <tbody></tbody>
		</table>
    </div>
    <!-- END PAGE BASE CONTENT -->
</div>

<div id="modal_flowchart" class="modal fade bs-example-modal-lg text-center" tabindex="-1" role="dialog">
	<div class="modal-dialog modal-lg" style="display: inline-block; width: auto;">
		<div class="modal-content">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
			<img  id="flowchart" src="" >
		</div>
	</div>
</div>

<div id="modal_approve" class="modal fade bs-modal-lg" tabindex="-1" data-backdrop="static" >
	<div class="modal-dialog" style="width: 80%;">
		<div class="modal-content">
		</div>
	</div>
</div>
</tempus:Content>

<tempus:Content contentPlaceHolderId="js">
<script src="../assets/global/scripts/datatable.js" type="text/javascript"></script>
<script src="../assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>
<script>
$(function(){
	initdatatable();
});

var initdatatable = function(){
	$("#datatable").DataTable({
		serverSide: true,
		ordering: true,
		searching: true,
		order: [[2, "desc"]],
		lengthMenu: [[10,20,50,100],[10,20,50,100]],
      	pageLength: 20,
      	pagingType: "bootstrap_full_number",
		//dom: '<<t>lp>',
        ajax: function ( data, callback, settings ) {
        	data.isend = $('input:radio[name="isend"]:checked').val();
        	data.processType = $('#processType').val();
            $.get("./getMyProcess",data,function(result){
            	App.blockUI({
                    target: '.page-content',
                    animate: true
                });
            	callback(result);
            	App.unblockUI('.page-content');
            });
        },
	    columns: [
	    	{data:"FPROCESSINSTANCEID",orderable: false},
	    	{data:"PROC_INST_NAME",orderable: true},
	    	{data:"FSTARTTIME",orderable: true},
	    	{data:"FENDTIME",orderable: true},
	    	{orderable: false,render:function(data, type, row){
	    		return '<div class="btn-group">'
				    		+'<button class="btn btn-xs green dropdown-toggle" type="button" data-toggle="dropdown" aria-expanded="false">'
				    			+'<i class="fa fa-angle-down"></i> Actions'
			    			+'</button>'
			    			+'<ul class="dropdown-menu" role="menu">'
		    					+'<li><a href="../MyWorkFlow/ShowPage?processinstanceid='+row.FPROCESSINSTANCEID+'"><span class="icon-docs"></span>查看表单</a></li>'
		    					+'<li><a href="../modeler/diagram-viewer/index.html?processDefinitionId='+row.FPROC_DEF_ID+'&processInstanceId='+row.FPROCESSINSTANCEID+'" target="_blank"><span class="icon-docs"></span>查看流程图</a></li>'
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

var viewflowchart = function(taskid){
	$("#flowchart").attr("src","./getRuntimeFlowChart?taskid="+taskid);
	$("#modal_flowchart").modal("show");
}

var showapprove = function(taskid){
	$.get("./getApprovalpage",{"taskid":taskid},function(data){
		$("#modal_approve .modal-content").html(data);
		$("#modal_approve").modal("show");
	});
}

var datatablerefresh = function(){
	$("#datatable").DataTable().draw();
}

</script>
</tempus:Content>

</tempus:ContentPage>