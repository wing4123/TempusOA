<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
    <h4 class="modal-title">任务办理</h4>
</div>
<div class="modal-body">
	<div>
		<jsp:include page="<%=request.getContextPath()%>Reimbursement/WFShow">
			<jsp:param name="id" value="${formid}" /> 
		</jsp:include>
	</div>
    <hr>
    <label class="control-label">批注：</label>
    <textarea id="comment" class="form-control" style="margin-bottom: 12px;"></textarea>
    <!--
    <c:forEach items="${buttons}" var="row" varStatus="S">
    	<button class="btn btn-success" data-value="${row.value}" onclick="approval(this)">${row.name}</button>
    </c:forEach>
    -->
    <button class='btn btn-success <c:if test="${approval==2 || approval==3}">.hidden</c:if>' data-value="1" onclick="approval(this)" >同意</button>
    <button class="btn btn-danger <c:if test="${approval==2 || approval==3}">.hidden</c:if>" data-value="2" onclick="approval(this)">驳回</button>
    <button class="btn btn-success <c:if test="${approval==1}">.hidden</c:if>" data-value="3" onclick="approval(this)">提交</button>
    <button class="btn btn-success <c:if test="${approval==1}">.hidden</c:if>" data-value="4" onclick="approval(this)">放弃</button>
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
<div class="modal-footer">
    <button type="button" class="btn default" data-dismiss="modal">Close</button>
</div>

<script>
var approval = function(btn){
	console.log($(btn).data("value"));
	$.post("./Approval",{"choosevalue":$(btn).data("value"),"choosename":$(btn).text(),"comment":$("#comment").val(),"taskid":"${taskid}"},function(){
		$(".modal").modal("hide");
		$("#datatable").DataTable().draw();
	});
}
</script>