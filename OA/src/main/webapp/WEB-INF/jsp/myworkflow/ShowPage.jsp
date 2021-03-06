<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/master/masterPage.tld" prefix="tempus" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="i18n" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<tempus:ContentPage materPageId="masterstep">

<tempus:Content contentPlaceHolderId="title">
    <title>查看流程表单</title>
</tempus:Content>

<tempus:Content contentPlaceHolderId="main">
<div class="page-content" style="min-height: 1147px;">
    <!-- BEGIN PAGE BASE CONTENT -->
    <div class="page-head">
        <!-- BEGIN PAGE TITLE -->
        <div class="page-title">
            <h1><i18n:message code="查看流程表单" />(流程ID:${processinstanceid})</h1>
        </div>
        <!-- END PAGE TITLE -->
    </div>
    <div class="page-base-content">
		<div>
			<jsp:include page="${formurl}?id=${formid}" />
<%-- 			<jsp:include page="./attachment.jsp">
				<jsp:param name="name" value="value"/>
			</jsp:include> --%>
			<%-- <%@ include file="../layout/bill-attachment.jsp" %> --%>
			<%@ include file="../layout/bill-attachment.jsp" %>
		</div>
		<hr/>
		<div style="height: 40px;line-height: 40px;position: relative;">
			<span style="font-size: 22px;font-weight: normal;" >审批历史</span>
			<c:if test="${sys_starter==USER.userid && (choose==0 || choose==3)}">
				<button class="btn btn-danger btn-sm" style="position: absolute;top: 5px;left: 100px;" onclick="undo()">撤回</button>
			</c:if>
		</div>
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
		    	<tr><td>${S.index + 1}</td><td>${row.TASKNAME}</td><td>${row.ASSIGNEE}</td><td>${row.STARTTIME}</td><td>${row.ENDTIME}</td><td>${row.FOPTION}</td><td>${row.FCOMMENT}</td><td>${row.usetime}</td></tr>
		    </c:forEach>
	        </tbody>
		</table>
    </div>
    <!-- END PAGE BASE CONTENT -->
</div>

<div id="modal_viewimage" class="modal fade" tabindex="-1" style="overflow: auto;" >
    <div class="modal-body" style="text-align: center;">
    	<img id="viewimage" src="" />
    </div>
    <div style="position: fixed;bottom: 100px;background-color: #2b2b2b;width: 280px;left: 50%;margin-left: -90px;opacity: 0.5;">
    	<div style="cursor: pointer;color: red;font-size: 30px;float: left;margin: 0px 10px;" onclick="switchimg(-1)"><span class="fa fa-arrow-left"></span></div>
    	<div style="cursor: pointer;color: red;font-size: 30px;float: left;margin: 0px 10px;" onclick="rotate(-90)"><span class="fa fa-rotate-left"></span></div>
   		<div style="cursor: pointer;color: red;font-size: 30px;float: left;margin: 0px 10px;" onclick="rotate(90)"><span class="fa fa-rotate-right"></span></div>
	    <div style="cursor: pointer;color: red;font-size: 30px;float: left;margin: 0px 10px;" onclick="scale(-0.2)"><span class="fa fa-search-minus"></span></div>
	    <div style="cursor: pointer;color: red;font-size: 30px;float: left;margin: 0px 10px;" onclick="scale(0.2)"><span class="fa fa-search-plus"></span></div>
        <div style="cursor: pointer;color: red;font-size: 30px;float: left;margin: 0px 10px;" onclick="switchimg(1)"><span class="fa fa-arrow-right"></span></div>
	    <div class="clearfix"></div>
    </div>
</div>
</tempus:Content>

<tempus:Content contentPlaceHolderId="js">
<script>
var tablei=0;
var viewimage = function(rn){
	tablei=rn-1;
	var tabledata=$("#filelist").DataTable().data();
	$("#viewimage").prop("src","../file/ViewImage?id="+tabledata[tablei].FID);
	$("#viewimage").css({"transform":"rotate(0deg)"})
	$("#modal_viewimage").modal("show");
}

var Rotate = function(direction){
	$("#viewimage").css({"transform":"rotate(90deg)"})
}

var r = 0;
var s = 1;
// 旋转图片
var rotate = function (a) {
	r=r+a;
	if(r==360 || r==-360){
		r=0;
	}
	$("#viewimage").css({"transform":"rotate("+r+"deg)"});
	var width=$("#viewimage").width();
	var height=$("#viewimage").height();
}

var switchimg = function(pn){
	console.log(tablei);
	tablei=tablei+pn;
	var tabledata=$("#filelist").DataTable().data();
	
	console.log(tablei);
	while(tablei<tabledata.length && tablei>0 && tabledata[tablei].FTYPE.toUpperCase()!="JPG" && tabledata[tablei].FTYPE.toUpperCase()!="PNG" && tabledata[tablei].FTYPE.toUpperCase()!="GIF"){
		tablei=tablei+pn;
		console.log(tablei);
	}
	
	if(tablei>=tabledata.length || tablei<0){
		tablei=tablei-pn;
		return false;
	}
	
	console.log(tablei);
	
	$("#viewimage").css({"transform":"rotate(0deg)"});
	$("#viewimage").css({"width":"auto","height":"auto"});
	$("#viewimage").prop("src","../file/ViewImage?id="+tabledata[tablei].FID);
	
}

//缩放图片
function scale(b) {
	s=s+b;
	if(s>2){
		s=2;
	}else if(s<0){
		s=0;
	}
	var width=$("#viewimage").width();
	var height=$("#viewimage").height();
	//$("#viewimage").css({"transform":"scale("+s+","+s+")"});
	$("#viewimage").css({"width":width*(1+b)+"px","height":height*(1+b)+"px"});
}

var undo = function(){
	App.blockUI({animate: true});
	$.get("./undo/${processinstanceid}",function(editurl){
		location.href=editurl;
	});
}

</script>
</tempus:Content>

</tempus:ContentPage>