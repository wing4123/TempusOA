<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/master/masterPage.tld" prefix="tempus" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="i18n" %>
<%@ page import="org.springframework.web.servlet.support.RequestContextUtils" language="java"%>
<%@ page import="java.util.UUID" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request).toString();
%>
    <link href="../assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
    <link href="../assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
    	<div class="form-horizontal">
    		<button class='btn btn-success pull-right' style="margin-top: -65px;" onclick="printform('${returnObject.reimbursement.FID}')">打印</button>
    		<div class="form-group">
	            <label class="col-md-2 control-label">标题<span class="required">*</span></label>
	            <div class="col-md-8">
	                <input class="form-control" name="name" value="${returnObject.reimbursement.FNAME}" readonly />
	            </div>
	        </div>   			
   			<div class="form-group">
	            <label class="col-md-2 control-label">报销类型<span class="required">*</span></label>
	            <div class="col-md-10">
	                <div class="mt-radio-inline" style="padding-bottom: 0px;" id="type">
	                    <label class="mt-radio">
	                        <input type="radio" name="type" value="1" <c:if test="${returnObject.reimbursement.FTYPE==1}">checked</c:if> disabled />礼品及业务招待费用/差旅费用审批/交通补助<span></span>
	                    </label>
	                    <label class="mt-radio">
	                        <input type="radio" name="type" value="2" <c:if test="${returnObject.reimbursement.FTYPE==2}">checked</c:if> disabled />零星开支（办公用品、水电费、快递费等未经事前审批的报销事项）<span></span>
	                    </label>
	                </div>
	            </div>
	        </div>
   			<div class="form-group" id="relation" <c:if test="${returnObject.reimbursement.FTYPE=='2'}">style="display: none;"</c:if> >
   				<label class="col-md-2 control-label">关联单据<span class="required">*</span></label>
           		<div class="col-md-10">
                    <div>
                        <div id="list3">
                        	<c:forEach items="${returnObject.list3}" var="row" varStatus="S">
							<div class="row">
								<div class="col-md-5" >
									<label class="control-label">标题<span class="required">*</span></label>
									<input class="form-control" name="relationname" value="${row.FTITLE}" readonly style="cursor: pointer;" onclick="relationbill_show(this)" required />
									<input type="hidden" name="relationprocessid" value="${row.FPROCESSINSTANCEID}" required />
									<span class="caret" style="position: absolute;right: 22px;top: 42px;"></span>
								</div>
								<div class="col-md-3">
									<label class="control-label">金额<span class="required">*</span></label>
									<div><input class="form-control" name="relationamount" value="${row.FAMOUNT}" readonly /></div>
								</div>
								<div class="col-md-3">
									<label class="control-label">币种<span class="required">*</span></label>
									<select name="relationcurrency" class="form-control" disabled  >
										<option value="CNY" <c:if test="${row.FCURRENCY=='CNY'}">selected</c:if> >人民币 CNY</option>
										<option value="HKD" <c:if test="${row.FCURRENCY=='HKD'}">selected</c:if> >港币 HKD</option>
										<option value="USD" <c:if test="${row.FCURRENCY=='USD'}">selected</c:if> >美元 USD</option>
									</select>
								</div>
								<div class="col-md-1" style="padding-top: 32px;">
								    <a href="../MyWorkFlow/ShowPage?processinstanceid=${row.FPROCESSINSTANCEID}" target="_blank" >查看</a>
								</div>
							</div>                        
                        	</c:forEach>
                        </div>
                        <hr/>
                	</div>
                </div>
   			</div>   
   			<div class="form-group">
   				<label class="col-md-2 control-label">费用报销公司<span class="required">*</span></label>
   				<div class="col-md-3">
	           		<select name="company" class="form-control" onchange="changecompany(this.value)" disabled="disabled">
	        			<option value="">请选择</option>
						<c:forEach items="${returnObject.company}" var="row" varStatus="S">
							<option value="${row.FID}" <c:if test="${returnObject.reimbursement.FCOMPANY==row.FID}">selected</c:if> >${row.FNAME}</option>
						</c:forEach>
	           		</select>
           		</div>
           		<label class="col-md-2 control-label">申请人<span class="required">*</span></label>
           		<div class="col-md-3"><input id="applicant" class="form-control" readonly="readonly" value="${returnObject.reimbursement.FCREATORNAME}"  required /></div>
   			</div>
   			<div class="form-group">
   				<label class="col-md-2 control-label">部门<span class="required">*</span></label>
           		<div class="col-md-3"><input class="form-control" readonly="readonly" value="${returnObject.reimbursement.FDEPARTMENTNAME}"  required /></div>
           		<label class="col-md-2 control-label">币种<span class="required">*</span></label>
           		<div class="col-md-3">
           			<select name="currency" class="form-control" disabled="disabled">
           				<option value="">请选择</option>
						<option value="CNY" <c:if test="${returnObject.reimbursement.FCURRENCY=='CNY'}">selected</c:if> >人民币 CNY</option>
						<option value="HKD" <c:if test="${returnObject.reimbursement.FCURRENCY=='HKD'}">selected</c:if> >港币 HKD</option>
						<option value="USD" <c:if test="${returnObject.reimbursement.FCURRENCY=='USD'}">selected</c:if> >美元 USD</option>
           			</select>
           		</div>
   			</div>
<%--    			<div class="form-group">
   				<label class="col-md-2 control-label">领款人<span class="required">*</span></label>
           		<div class="col-md-3">
					<input id="payee" value='${returnObject.reimbursement.USER_NAME}' class="form-control" readonly="readonly" style="cursor: pointer;" />
				</div>
   			</div> --%>
   			<div class="form-group">
   				<label class="col-md-2 control-label">详情<span class="required">*</span></label>
           		<div class="col-md-10">
                    <div>
                        <div id="list">
                        <c:forEach items="${returnObject.entry}" var="row1" varStatus="S">
                        	<div class="row">
								<div class="col-md-3">
									<label class="control-label">费用类别<span class="required">*</span></label>
									<select name="costcategory" class="form-control" disabled>
							     		<option value="">请选择</option>
							     		<c:forEach items="${returnObject.costcategory}" var="row2" varStatus="S">
											<option value="${row2.COSTVALUE}" <c:if test="${row2.COSTVALUE==row1.FCOSTCATEGORY}">selected</c:if> >${row2.COSTNAME}</option>
										</c:forEach>
									</select>
								</div>
								<div class="col-md-6">
									<label class="control-label">内容<span class="required">*</span></label>
									<input name="content" class="form-control" value="${row1.FCONTENT}" disabled/>
								</div>
								<div class="col-md-2">
									<label class="control-label">金额<span class="required">*</span></label>
									<input name="amount" class="form-control currency" value="${row1.FAMOUNT}" disabled />
								</div>
							</div>
						</c:forEach>
                        </div>
                        <hr/>
                        <div class="row">
	            			<div class="col-md-6  col-md-offset-6" style="padding: 0px;">
		            			<label class="control-label col-md-6">合计</label>
		            			<div class="col-md-4"><input id="total" name="total" value="${returnObject.reimbursement.FTOTALAMOUNT}" class="form-control required" readonly="readonly"/></div>
	            			</div>
	            		</div>
                	</div>
                </div>
   			</div>
    	</div>
    	
<script>
var printform = function(id){
    $.get("../Print/Reimbursement",{"id":id},function(jsp){
    	var newwindow = window.open();
    	if(newwindow){
    		newwindow.document.write(jsp);
	    	newwindow.print();
	    	newwindow.document.getElementById("print").execWB(7,1)
    	}else{
    		alert("请启用浏览器弹出窗口，刷新后重试！");
    	}
    });
}

</script>    	