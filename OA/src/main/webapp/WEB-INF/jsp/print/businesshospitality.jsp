<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.springframework.web.servlet.support.RequestContextUtils" language="java"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="i18n" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
	String locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request).toString();
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>业务招待费申请单</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta content="width=device-width, initial-scale=1" name="viewport" />
        <style>
        	/* .content{margin: 0 auto;width: 20.5cm;} */
        	table{border:solid 1px #000000;text-align: center;font-size: 10.5pt;border-width:1px 0px 0px 1px;width: 100%;border-spacing: 0;}
			td,th{border:solid 1px #000000; border-width:0px 1px 1px 0px; padding:5px 0px;text-align: center;}
			.row{margin: 5px 0px}
        </style>
    </head>
    <body>
    	<div class="content">
    		<h2 style="text-align: center;">业务招待费申请单</h2>
    		<hr style="border:0; border-top:3px double;margin: 0px;">
   			<div class="row" >
   				<div style="float: left;width: 66%;">标题：${returnObject.FNAME}</div>
   				<div style="float: left;width: 34%;">流程ID：${returnObject.FPROCESSINSTANCEID}</div>
   				<div style="clear: both;"></div>
   			</div>
   			<div class="row" >
   				<div style="float: left;width: 33%;">申请人：${returnObject.FCREATORNAME}</div>
   				<div style="float: left;width: 33%;">部门：${returnObject.FDEPARTMENTNAME}</div>
   				<div style="float: left;width: 33%;">申请日期：${fn:substring(returnObject.FCREATETIME, 0, 10)}</div>
   				<div style="clear: both;"></div>
   			</div>
   			<hr>
   			<div class="row" >
   				<div style="float: left;width: 50%;">金额：${returnObject.FAMOUNT}</div>
   				<div style="float: left;width: 50%;">币种：
   					<c:if test="${returnObject.FCURRENCY=='CNY'}">人民币</c:if>
	                <c:if test="${returnObject.FCURRENCY=='HKD'}">港币</c:if>
	                <c:if test="${returnObject.FCURRENCY=='USD'}">美元</c:if>
   				</div>
   				<div style="clear: both;"></div>
   			</div>
   			<div class="row" >客户名称：${returnObject.FCLIENT}</div>
   			<div class="row" >招待地点：${returnObject.FLOCATION}</div>
   			<div class="row" >
   				<div style="float: left;width: 5em;">出差事由：</div>
   				<div style="overflow: hidden;">${returnObject.FCAUSE}</div>
   			</div>
   			<div class="row" >
   				<div style="float: left;width: 50%;">招待日期：${fn:substring(returnObject.FDATE, 0, 10)}</div>
   				<div style="float: left;width: 50%;">参加人数：${returnObject.FPEOPLENUMBER}</div>
   				<div style="clear: both;"></div>
   			</div>
   			<div class="row" >公司陪同人员：${returnObject.FSTAFF}</div>
   			<div>审批历史：</div>
   			<table>
   				<thead>
   					<tr><th>序号</th><th>流程节点</th><th>办理人</th><th>开始时间</th><th>结束时间</th><th>审批选项</th><th>备注</th><th>任务耗时</th></tr>
   				</thead>
				<tbody>
				<c:forEach items="${returnObject.approvalhistory}" var="row" varStatus="S">
					<tr>
						<td>${S.count}</td>
						<td>${row.TASKNAME}</td>
						<td>${row.ASSIGNEE}</td>
						<td>${row.STARTTIME}</td>
						<td>${row.ENDTIME}</td>
						<td>${row.FOPTION}</td>
						<td>${row.FCOMMENT}</td>
						<td>${row.usetime}</td>
					</tr>
				</c:forEach>
				</tbody>
   			</table>
    	</div>
    	<OBJECT id="print" height=0 width=0 classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2 name=wb></OBJECT>
        <script src="../assets/global/plugins/jquery.min.js" type="text/javascript"></script>
        <script>
        	$(function(){
        		console.log("${returnObject}")
        	});
        </script>
    </body>
</html>