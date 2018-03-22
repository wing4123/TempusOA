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
        <title>出差申请</title>
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
    		<h2 style="text-align: center;">出差申请单</h2>
    		<hr style="border:0; border-top:3px double;margin: 0px;">
   			<div class="row" >
   				<div style="float: left;width: 66%;">标题：${returnObject.travel.FNAME}</div>
   				<div style="float: left;width: 34%;">流程ID：${returnObject.travel.FPROCESSINSTANCEID}</div>
   				<div style="clear: both;"></div>
   			</div>
   			<div class="row" >
   				<div style="float: left;width: 33%;">申请人：${returnObject.travel.FCREATORNAME}</div>
   				<div style="float: left;width: 33%;">部门：${returnObject.travel.FDEPARTMENTNAME}</div>
   				<div style="float: left;width: 33%;">申请日期：${fn:substring(returnObject.travel.FLASTUPDATETIME, 0, 10)}</div>
   				<div style="clear: both;"></div>
   			</div>
   			<hr>
   			<div class="row" >
   				<div style="float: left;width: 33%;">开始日期：${fn:substring(returnObject.travel.FBEGINDATE, 0, 10)}</div>
   				<div style="float: left;width: 33%;">结束日期：${fn:substring(returnObject.travel.FENDDATE, 0, 10)}</div>
   				<div style="float: left;width: 33%;">出差天数：${returnObject.travel.FDAYS}天</div>
   				<div style="clear: both;"></div>
   			</div>
   			<div class="row" >
   				<div style="float: left;width: 33%;">地点：${returnObject.travel.FLOCATION}</div>
   				<div style="float: left;width: 33%;">地域：
   					<c:if test="${returnObject.travel.FAREA=='1'}">内地</c:if>
   					<c:if test="${returnObject.travel.FAREA=='2'}">港澳台</c:if>
   					<c:if test="${returnObject.travel.FAREA=='3'}">国外</c:if>
   				</div>
   				<div style="float: left;width: 33%;">项目类型：
   					<c:if test="${returnObject.travel.FPROJECTTYPE=='1'}">一般项目</c:if>
			        <c:if test="${returnObject.travel.FPROJECTTYPE=='2'}">投资整合项目</c:if>
			        <c:if test="${returnObject.travel.FPROJECTTYPE=='3'}">IT项目</c:if>
   				</div>
   				<div style="clear: both;"></div>
   			</div>   	
   			<div class="row" >
   				<div style="float: left;width: 5em;">出差事由：</div>
   				<div style="overflow: hidden;">${returnObject.travel.FCAUSE}</div>
   			</div>
   			<div>行程：</div>
   			<table>
   				<thead>
   					<tr><th>交通工具</th><th>日期</th><th>出发城市</th><th>抵达城市</th><th>航班车次</th><th>费用</th><th>其他说明</th></tr>
   				</thead>
				<tbody>
				<c:forEach items="${returnObject.travelrouts}" var="row" varStatus="S">
					<tr>
    					<td>
    						<c:if test="${row.FTRANSPORT=='1'}">飞机</c:if>
    						<c:if test="${row.FTRANSPORT=='2'}">火车</c:if>
    						<c:if test="${row.FTRANSPORT=='3'}">市内交通</c:if>
    						<c:if test="${row.FTRANSPORT=='4'}">私车公用</c:if>
    					</td>
    					<td>${fn:substring(row.FDEPARTUREDATE, 0, 10)}</td>
    					<td>${row.FDEPARTURECITY}</td>
    					<td>${row.FDESTINATIONCITY}</td>
    					<td>${row.FTRIPS}</td>
    					<td>${row.FCOST}</td>
    					<td>${row.FREMARK}</td>
    				</tr>
				</c:forEach>
				<c:if test="${returnObject.travelrouts.size()==0}">
					<tr><td colspan="7">无数据...</tr>
				</c:if>
				</tbody>   			
   			</table>
   			<div>业务招待：</div>
   			<table>
   				<thead>
   					<tr><th>客户</th><th>地点</th><th>事由</th><th>金额</th><th>参加人数</th><th>公司陪同人员</th></tr>
   				</thead>
				<tbody>
				<c:forEach items="${returnObject.business}" var="row" varStatus="S">
					<tr>
    					<td>${row.FCLIENT}</td>
    					<td>${row.FLOCATION}</td>
    					<td>${row.FCAUSE}</td>
    					<td>${row.FAMOUNT}</td>
    					<td>${row.FJOINNUMBER}</td>
    					<td>${row.FSTAFF}</td>
    				</tr>
				</c:forEach>
				<c:if test="${returnObject.business.size()==0}">
					<tr><td colspan="6">无数据...</tr>
				</c:if>
				</tbody>
   			</table>
   			<div>住宿/餐饮</div>
   			<table>
   				<thead>
   					<tr><th>项目</th><th>单价</th><th>人天数</th><th>金额</th><th>备注</th></tr>
   				</thead>
				<tbody>
					<tr>
						<td>酒店/住宿</td>
						<td>${returnObject.travel.FHOTELPRICE}</td>
						<td>${returnObject.travel.FHOTELDAYS}</td>
						<td>${returnObject.travel.FHOTELAMOUNT}</td>
						<td>${returnObject.travel.FHOTELREMARK}</td>
					</tr>
					<tr>
						<td>餐饮</td>
						<td>${returnObject.travel.FFOODPRICE}</td>
						<td>${returnObject.travel.FFOODDAYS}</td>
						<td>${returnObject.travel.FFOODAMOUNT}</td>
						<td>${returnObject.travel.FFOODREMARK}</td>
					</tr>
					<tr>
						<td>其他</td>
						<td colspan=2></td>
						<td>${returnObject.travel.FOTHERAMOUNT}</td>
						<td>${returnObject.travel.FOTHERREMARK}</td>
					</tr>
				</tbody>
   			</table>
   			<div>
   				<div style="float: left;width: 50%;">总金额：${returnObject.travel.FTOTALAMOUNT}</div>
   				<div style="float: left;width: 50%;">币种：
   					<c:if test="${returnObject.travel.FCURRENCY=='CNY'}">人民币</c:if>
	                <c:if test="${returnObject.travel.FCURRENCY=='HKD'}">港币</c:if>
	                <c:if test="${returnObject.travel.FCURRENCY=='USD'}">美元</c:if>
   				</div>
   			</div>
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
        		
        	});
        </script>
    </body>
</html>