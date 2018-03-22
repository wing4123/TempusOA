<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/master/masterPage.tld" prefix="tempus" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="i18n" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String basepath = request.getContextPath();
%>
<tempus:ContentPage materPageId="masterstep">

<tempus:Content contentPlaceHolderId="title">
    <title>任务办理</title>
</tempus:Content>

<tempus:Content contentPlaceHolderId="main">
<div class="page-content" style="min-height: 1147px;">
    <!-- BEGIN PAGE BASE CONTENT -->
    <div class="page-head">
        <!-- BEGIN PAGE TITLE -->
        <div class="page-title">
            <h1><i18n:message code="任务办理"  /></h1>
        </div>
        <!-- END PAGE TITLE -->
    </div>
    <div class="page-base-content">
		<div>
			<jsp:include page="${formurl}?id=${formid}" />
		</div>
		<hr/>
	    <h3>审批历史</h3>
		<table id="datatable" class="table table-striped table-bordered table-hover table-checkable order-column">
			<thead>
				<tr>
					<th>序号</th>
	            	<th>流程节点</th>
	            	<th>办理人</th>
	            	<th>开始时间</th>
	            	<th>结束时间</th>
	            	<th>审批选项</th>
	            	<th>批注</th>
	            	<th>任务耗时</th>
	            </tr>
	        </thead>
	        <tbody>
	        <c:forEach items="${history}" var="row" varStatus="S">
		    	<tr><td>${S.index + 1}</td><td>${row.TASKNAME}</td><td>${row.ASSIGNEE}</td><td>${row.STARTTIME}</td><td>${row.ENDTIME}</td><td>${row.FOPTION}</td><td>${row.COMMENT}</td><td>${row.usetime}</td></tr>
		    </c:forEach>
	        </tbody>
		</table>
    </div>
    <!-- END PAGE BASE CONTENT -->
</div>
</tempus:Content>

<tempus:Content contentPlaceHolderId="js">
<script>
$(function(){
	
});
</script>
</tempus:Content>

</tempus:ContentPage>