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
        <meta charset="UTF-8" />
        <title>费用报销单</title>
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
    		<h2 style="text-align: center;">费用报销单</h2>
    		<hr style="border:0; border-top:3px double;margin: 0px;">
   			<div class="row" >流程ID：${returnObject.reimbursement.FPROCESSINSTANCEID}</div>
   			<div class="row">
   				<div style="float: left;width: 33%;">申请人：${returnObject.reimbursement.FCREATORNAME}</div>
   				<div style="float: left;width: 33%;">部门：${returnObject.reimbursement.FDEPARTMENTNAME}</div>
   				<div style="float: left;width: 33%;">申请日期：${fn:substring(returnObject.reimbursement.FCREATETIME, 0, 10)}</div>
   				<div style="clear: both;"></div>
   			</div class="row">
   			<hr>
   			<div class="row">报销类型：
   				<c:if test="${returnObject.reimbursement.FTYPE=='1'}">礼品及业务招待费用/差旅费用审批/交通补助</c:if>
 				<c:if test="${returnObject.reimbursement.FTYPE=='2'}">零星开支（办公用品、水电费、快递费等未经事前审批的报销事项）</c:if>
   			</div>
   			<div>关联单据：</div>
   			<table>
   				<thead>
   					<tr><th>标题</th><th>金额</th><th>币种</th></tr>
   				</thead>
				<tbody>
				<c:forEach items="${returnObject.relation}" var="row" varStatus="S">
					<tr>
    					<td>${row.FTITLE}</td>
    					<td>${row.FAMOUNT}</td>
    					<td>${row.FCURRENCY}</td>
    				</tr>
				</c:forEach>
				<c:if test="${returnObject.relation.size()==0}">
					<tr><td colspan="3">无数据...</tr>
				</c:if>
				</tbody>
   			</table>
   			<div class="row">费用报销公司：${returnObject.reimbursement.FCOMPANYNAME}</div>
   			<div>费用详情：</div>
   			<table>
   				<thead>
   					<tr><th>类别</th><th>内容</th><th>金额</th></tr>
   				</thead>
				<tbody>
				<c:forEach items="${returnObject.entry}" var="row" varStatus="S">
					<tr>
    					<td>${row.FNAME}</td>
    					<td>${row.FCONTENT}</td>
    					<td>${row.FAMOUNT}</td>
    				</tr>
				</c:forEach>
				<tr><td colspan="3">合计：${returnObject.reimbursement.FTOTALAMOUNT}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;币种：${returnObject.reimbursement.FCURRENCY}</td></tr>
				</tbody>
   			</table>
   			
   			
   			
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
        		console.log("${returnObject}");
        	});
        </script>
    </body>
</html>