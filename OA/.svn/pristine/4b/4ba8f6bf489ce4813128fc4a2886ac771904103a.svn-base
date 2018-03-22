<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/master/masterPage.tld" prefix="tempus" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="i18n" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="org.springframework.web.servlet.support.RequestContextUtils" language="java"%>
<%
	String locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request).toString();
	String contextpath = request.getContextPath();
%>
<tempus:ContentPage materPageId="masterstep">

<tempus:Content contentPlaceHolderId="title">
    <title><i18n:message code="消息列表" /></title>
    <link href="../assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
    <link href="../assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
</tempus:Content>

<tempus:Content contentPlaceHolderId="main">
<div class="page-content" style="min-height: 1147px;">
    <!-- BEGIN PAGE HEAD-->
    <div class="page-head">
        <!-- BEGIN PAGE TITLE -->
        <div class="page-title">
            <h1><i18n:message code="消息列表" /></h1>
        </div>
        <!-- END PAGE TITLE -->
    </div>
    <!-- END PAGE HEAD-->
    <!-- BEGIN PAGE BASE CONTENT -->
    <div style="background-color: white;padding: 20px;">
   		<div class="pull-left" >
            <label class="control-label pull-left">消息状态:</label>
            <div class="mt-radio-inline pull-left" style="padding: 0px 10px;">
                <label class="mt-radio">
                    <input type="radio" name="isread" value="0" checked > 未读  <span></span>
                </label>
                <label class="mt-radio">
                    <input type="radio" name="isread" value="1" > 已读  <span></span>
                </label>
                <label class="mt-radio">
                    <input type="radio" name="isread" value="" > 全部   <span></span>
                </label>
            </div>
        </div>
   		<table id="datatable" class="table">
   			<thead class="hidden"><tr><th></th></tr></thead>
	        <tbody></tbody>
		</table>
    </div>
    <!-- END PAGE BASE CONTENT -->
</div>

</tempus:Content>

<tempus:Content contentPlaceHolderId="js">
<script src="../assets/global/scripts/datatable.js" type="text/javascript"></script>
<script src="../assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>
<script>
$(function(){
	initdatatable();
	
	$("#datatable tbody a").on("click",function(){
		if($(this).data(readtime)){
			readmessage($(this).data("id"));
		}
	});
	
	$('input:radio[name="isread"]').on("change",function(){
		$("#datatable").DataTable().draw();
	});
	
});

var initdatatable = function(){
	$("#datatable").DataTable({
		serverSide: true,
		ordering: false,
		searching: true,
		order: [[0, "desc"]],
		lengthMenu: [[10,20,50,100],[10,20,50,100]],
      	pageLength: 20,
      	pagingType: "bootstrap_full_number",
		//dom: '<<t>lp>',
		ajax: {
	        url: './getMessageList',
	        data: function(data){
	        	return $.extend(data,{"isread":$('input:radio[name="isread"]:checked').val()});
	        }
		},
	    columns: [
	    	{orderable: false,render:function(data, type, row){
	    		var html = '<div data-id="'+row.FID+'" data-readtime="'+(row.FREADTIME==null)+'" onclick="readmessage(this)">'+(row.FURL==null?'':'<a href="<%=contextpath %>'+row.FURL+'">');
	    		html = html+'<div style="float: left;margin-right: -100%;"><div class="label label-sm label-'+(row.FREADTIME==null?'success':'default')+'">'
				   				+'<i class="fa fa-bell-o"></i>'
			   				+'</div></div>'
	    					+'<div style="float: left;width: 100%;padding-right: 150px;"><div style="margin-left: 30px;"> '+row.FTITLE+' </div></div>'
	    					+'<div style="float: right;width: 150px;margin-left: -150px;">'+row.FSENDTIME+'</div>';
	    		html = html+(row.FURL==null?'':'</a>')+"</div>";
	    		return html;
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

var readmessage = function(row){
	if($(row).data("readtime")){
		$(row).find(".label").removeClass("label-success").addClass("label-default");
		$.get("./readMessage",{"id":$(row).data("id")});
	}
	
}


</script>
</tempus:Content>

</tempus:ContentPage>