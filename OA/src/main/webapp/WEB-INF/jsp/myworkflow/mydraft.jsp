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
    <title><i18n:message code="我的草稿" /></title>
    <link href="../assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
    <link href="../assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
</tempus:Content>

<tempus:Content contentPlaceHolderId="main">
<div class="page-content" style="min-height: 1147px;">
    <!-- BEGIN PAGE HEAD-->
    <div class="page-head">
        <!-- BEGIN PAGE TITLE -->
        <div class="page-title">
            <h1><i18n:message code="我的草稿"  /></h1>
        </div>
        <!-- END PAGE TITLE -->
    </div>
    <!-- END PAGE HEAD-->
    <!-- BEGIN PAGE BASE CONTENT -->
    <div style="background-color: white;padding: 20px;">
   		<table id="datatable" class="table table-striped table-bordered table-hover table-checkable order-column">
	        <thead>
	            <tr>
	            	<th>序号</th>
	            	<th>单据名称</th>
	            	<th>创建时间</th>
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
		ajax: {
	        url: './getMyDraftList'
		},
	    columns: [
	    	{data:"RN",orderable: false},
	    	{data:"FNAME",orderable: true},
	    	{data:"FCREATETIME",orderable: true},
	    	{orderable: false,render:function(data, type, row){
	    		return '<div class="btn-group">'
				    		+'<button class="btn btn-xs green dropdown-toggle" type="button" data-toggle="dropdown" aria-expanded="false">'
				    			+'<i class="fa fa-angle-down"></i> Actions'
			    			+'</button>'
			    			+'<ul class="dropdown-menu" role="menu">'
		    					+'<li><a href="javascript: fedit(\''+row.FEDITURL+'\')"><span class="icon-docs"></span>编辑</a></li>'
		    					+'<li><a href="javascript: fdelete(\''+row.FDELETEURL+'\')"><span class="icon-docs"></span>删除</a></li>'
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

var fedit = function(editurl){
	location.href=editurl;
}

var fdelete = function(deleteurl){
	$.get(deleteurl,function(data){
		swal("Success","删除成功！","success");
		$("#datatable").DataTable().draw();
	});
}

</script>
</tempus:Content>

</tempus:ContentPage>