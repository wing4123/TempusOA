<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/master/masterPage.tld" prefix="tempus" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="i18n" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
			<jsp:include page="${formurl}?id=${formid}&taskid=${taskid}" />
			<%-- <c:if test="${choose!=2 && choose!=0}"><%@ include file="../layout/common-bill.jsp" %></c:if> --%>
			<%-- <c:if test="${choose==2}"><%@ include file="../layout/common-bill.jsp" %></c:if> --%>
			<c:if test="${choose!=5 && choose != 2}"><%@ include file="../layout/bill-attachment.jsp" %></c:if>
			
			<%-- ${formurl}?id=${formid} --%>
		</div>
		<hr/>
		<label class="control-label">批注：</label>
	    <textarea id="comment" class="form-control" style="margin-bottom: 12px;">同意</textarea>
	    <button class='btn btn-success <c:if test="${choose==2}">hidden</c:if>' data-value="1" onclick="formsubmit(1)" >同意</button>
	    <button class='btn btn-danger <c:if test="${choose==2}">hidden</c:if>' data-value="2" onclick="formsubmit(2)">驳回</button>
	    <button class='btn btn-success <c:if test="${choose==0 || choose==1 || choose==3}">hidden</c:if>' data-value="3" onclick="formsubmit(3)">提交</button>
	    <button class='btn btn-danger <c:if test="${choose==0 || choose==1 || choose==3}">hidden</c:if>' data-value="4" onclick="formsubmit(4)">放弃</button>
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
		    	<tr><td>${S.index + 1}</td><td>${row.TASKNAME}</td><td>${row.ASSIGNEE}</td><td>${row.STARTTIME}</td><td>${row.ENDTIME}</td><td>${row.FOPTION}</td><td>${row.FCOMMENT}</td><td>${row.usetime}</td></tr>
		    </c:forEach>
	        </tbody>
		</table>
    </div>
    <!-- END PAGE BASE CONTENT -->
</div>

<div id="modal_viewimage" class="modal fade" tabindex="-1" style="overflow: auto;" >
    <div class="modal-body" style="width: 100%;height: 100%;display: flex;align-items: center;">
    	<div style="width: 100%;text-align: center;">
    		<img id="viewimage" src="" />
    	</div>
    </div>
    <div style="position: fixed;bottom: 100px;background-color: #2b2b2b;width: 325px;left: 50%;margin-left: -90px;opacity: 0.5;">
    	<div style="cursor: pointer;color: red;font-size: 30px;float: left;margin: 0px 10px;" onclick="switchimg(-1)"><span class="fa fa-arrow-left"></span></div>
    	<div style="cursor: pointer;color: red;font-size: 30px;float: left;margin: 0px 10px;" onclick="rotate(-90)"><span class="fa fa-rotate-left"></span></div>
   		<div style="cursor: pointer;color: red;font-size: 30px;float: left;margin: 0px 10px;" onclick="rotate(90)"><span class="fa fa-rotate-right"></span></div>
	    <div style="cursor: pointer;color: red;font-size: 30px;float: left;margin: 0px 10px;" onclick="$('#modal_viewimage').modal('hide')"><span class="fa fa-times"></span></div>
	    <div style="cursor: pointer;color: red;font-size: 30px;float: left;margin: 0px 10px;" onclick="scale(-0.2)"><span class="fa fa-search-minus"></span></div>
	    <div style="cursor: pointer;color: red;font-size: 30px;float: left;margin: 0px 10px;" onclick="scale(0.2)"><span class="fa fa-search-plus"></span></div>
        <div style="cursor: pointer;color: red;font-size: 30px;float: left;margin: 0px 10px;" onclick="switchimg(1)"><span class="fa fa-arrow-right"></span></div>
	    <div class="clearfix"></div>
    </div>
</div>

<div id="modal_qrcode" class="modal fade bs-modal-lg" tabindex="-1">
	<div class="modal-dialog" style="width: 300px;height: 300px;">
		<div id="qrcode"></div>
	</div>
</div>
</tempus:Content>

<tempus:Content contentPlaceHolderId="js">
<script src="../js/jquery.qrcode.min.js" type="text/javascript"></script>
<script>
var btn;
$(function(){
	
});

var approval = function(btn){
	$.post("./Approval",{"choose":btn,"comment":$("#comment").val(),"taskid":"${taskid}"},function(){
		location.href="./MyToBeDo"
	});
}

var formsubmit = function(b){
	btn=b;
	if($("form").length>0 && btn!=4){
		$("form").submit();
	}else{
		App.blockUI({animate: true});
		approval(btn);
	}
}

var tablei=0;
var viewimage = function(rn){
	console.log(rn);
	tablei=rn-1;
	var tabledata=$("#filelist").DataTable().data();
	$("#viewimage").prop("src","../file/ViewImage?id="+tabledata[tablei].FID);
	$("#viewimage").css({"transform":"rotate(0deg)","width":"auto","height":"auto"});
	$("#modal_viewimage").modal("show");
}

var Rotate = function(direction){
	$("#viewimage").css({"transform":"rotate(90deg)"})
}

/*图片预览*/
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
	tablei=tablei+pn;
	var tabledata=$("#filelist").DataTable().data();
	
	while(tablei<tabledata.length && tablei>0 && tabledata[tablei].FTYPE.toUpperCase()!="JPG" && tabledata[tablei].FTYPE.toUpperCase()!="PNG" && tabledata[tablei].FTYPE.toUpperCase()!="GIF"){
		tablei=tablei+pn;
	}
	
	if(tablei>=tabledata.length || tablei<0){
		tablei=tablei-pn;
		return false;
	}
	
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

</script>
</tempus:Content>

</tempus:ContentPage>