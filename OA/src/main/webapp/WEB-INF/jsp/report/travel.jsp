<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="utf-8"%>
<%@ page import="org.springframework.web.servlet.support.RequestContextUtils" language="java"%>
<%@ taglib uri="/WEB-INF/master/masterPage.tld" prefix="tempus" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="i18n" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request).toString();
%>
<tempus:ContentPage materPageId="masterstep">

<tempus:Content contentPlaceHolderId="title">
    <title><i18n:message code="出差报表"  /></title>
    <link href="../assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
    <link href="../assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
    <link href="../assets/plugins/zTree_v3/css/metroStyle/metroStyle.css" rel="stylesheet" type="text/css" />
    <link href="../assets/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css" />
    <link href="../assets/global/plugins/bootstrap-daterangepicker/daterangepicker.min.css" rel="stylesheet" type="text/css" />
    <style>
    	th{white-space: nowrap;}
    	.dataTables_scrollHead th,.dataTables_scrollBody td{padding: 5px 8px !important;font-size: 13px !important;}
    	.dataTables_scrollBody th{padding: 0px 8px !important;}
    	/* .dataTables_scrollHead th{font-size: 13px !important;} */
    </style>
</tempus:Content>

<tempus:Content contentPlaceHolderId="main">
<div class="page-content" style="padding-top: 0px;">
	<div style="background-color: white;min-height: 763px;padding: 10px;">
		<div class="portlet box green" style="margin-bottom: 0px;">
	        <div class="portlet-title">
	            <div class="caption">
	                <i class="fa fa-globe"></i>出差申请报表</div>
	            <div class="pull-right" style="padding: 5px;">
	            	<a class="btn btn-sm red btn-outline" href=javascript:datatablereload() >查询</a>
	            	<a class="btn btn-sm purple btn-outline" href="./ExportTravel" target="_blank">导出</a>
	            </div>
	        </div>
	        <div class="portlet-body">
        		<form id="form_search" class="form-horizontal" role="form">
        			<div class="form-group">
		        		<label class="col-md-2 control-label">组织<span class="required">*</span></label>
		        		<div class="col-md-4">
			        		<div class="input-icon input-icon-sm right">
		                       <i class="fa fa-close" style="cursor: pointer;" onclick="$(this).next().val('');$(this).next().next().val('')"></i>
		                       <input type="text" class="form-control input-sm" readonly id="divisionname" onclick="$('#modal_division').modal('show');" />
		                       <input type="hidden" id="divisionnum" name="divisionnum" />
		                    </div>
	                    </div>
	                    <label class="col-md-2 control-label">申请日期<span class="required">*</span></label>
                        <div class="col-md-4">
                        	<div style="height: 3px;"></div>
                            <div class="input-group date-picker input-daterange" data-date-format="yyyy-mm-dd">
                                <input type="text" class="form-control input-sm" style="margin-top: 0px;" name="createdate_from">
                                <span class="input-group-addon"> to </span>
                                <input type="text" class="form-control input-sm" style="margin-top: 0px;" name="createdate_to">
                        	</div>
                        </div>
					</div>
        		</form>
	            <table class="table table-striped table-bordered table-hover order-column table-condensed" id="datatable">
	                <thead>
	                    <tr class="info">
	                        <th>序号</th>
	                        <th>流程ID</th>
	                        <th>标题</th>
	                        <th>申请人</th>
	                        <th>部门</th>
	                        <th>审批状态</th>
	                        <th>申请日期</th>
	                        <th>开始日期</th>
	                        <th>结束日期</th>
	                        <th>出差天数</th>
	                        <th>出差地点</th>
	                        <th>地域</th>
	                        <th>出差事由</th>
	                        <th>酒店/住宿单价</th>
	                        <th>酒店/住宿人天</th>
	                        <th>酒店/住宿金额</th>
	                        <th>酒店/住宿备注</th>
	                        <th>餐饮单价</th>
	                        <th>餐饮人天</th>
	                        <th>餐饮金额</th>
	                        <th>餐饮备注</th>
	                        <th>其他金额</th>
	                        <th>其他备注</th>
	                        <th>交通工具</th>
	                        <th>日期</th>
	                        <th>出发城市</th>
	                        <th>抵达城市</th>
	                        <th>航班/车次/公里数</th>
	                        <th>费用</th>
	                        <th>其他说明</th>
	                        <th>客户</th>
	                        <th>地点</th>
	                        <th>事由</th>
	                        <th>金额</th>
	                        <th>参加人数</th>
	                        <th>公司陪同人员</th>
	                    </tr>
	                </thead>
	            </table>
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
<script src="../assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>
<script src="../assets/plugins/zTree_v3/js/jquery.ztree.core.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/bootstrap-datepicker/locales/bootstrap-datepicker.<%=locale %>.min.js" type="text/javascript"></script>
<script>
$(function(){
	initdatatable();
	initztree_division();
	$('.date-picker').datepicker({
        autoclose: true,
        language: "<%=locale %>"
    });
});

var initdatatable = function(){
	$("#datatable").DataTable({
	    serverSide: true,
        ordering: false,
        searching: false,
        ajax: function ( data, callback, settings ) {
        	var formdata = $("#form_search").serializeJSON();
            $.post("./getTravelData",$.extend(data,formdata),function(result){callback(result)});
        },
        scrollY: 580,
        deferRender:    true,
        scrollX:        true,
        scroller: {
            loadingIndicator: true
        },
        columns: [
        	{data:"RN"},
	    	{data:"F1",render:function(data, type, row){
	    		return "<a href='../MyWorkFlow/ShowPage?processinstanceid="+row.F1+"' target='_blank'>"+row.F1+"</a>"
	    	}},
	    	{data:"F2"},
	    	{data:"F3"},
	    	{data:"F4"},
	    	{data:"F5"},
	    	{data:"F6"},
	    	{data:"F7"},
	    	{data:"F8"},
	    	{data:"F9"},
	    	{data:"F10"},
	    	{data:"F11"},
	    	{data:"F12"},
	    	{data:"F13"},
	    	{data:"F14"},
	    	{data:"F15"},
	    	{data:"F16"},
	    	{data:"F17"},
	    	{data:"F18"},
	    	{data:"F19"},
	    	{data:"F20"},
	    	{data:"F34"},
	    	{data:"F35"},
	    	{data:"F21"},
	    	{data:"F22"},
	    	{data:"F23"},
	    	{data:"F24"},
	    	{data:"F25"},
	    	{data:"F26"},
	    	{data:"F27"},
	    	{data:"F28"},
	    	{data:"F29"},
	    	{data:"F30"},
	    	{data:"F31"},
	    	{data:"F32"},
	    	{data:"F33"}
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

var initztree_division = function(){
	zTreeObj = $.fn.zTree.init($("#divisiontree"), {},${permissiontree});
}

var search = function(){
	$("#modal_division").modal("show");
}

var selectdivision = function(){
	var division = $.fn.zTree.getZTreeObj("divisiontree").getSelectedNodes();
	$("#divisionname").val(division[0].name);
	$("#divisionnum").val(division[0].FNUMBER);
	datatablereload();
	$("#modal_division").modal("hide");
}

var datatablereload = function(){
	$("#datatable").DataTable().draw();
}

</script>
</tempus:Content>

</tempus:ContentPage>