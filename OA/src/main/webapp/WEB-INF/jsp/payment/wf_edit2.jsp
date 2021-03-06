<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/master/masterPage.tld" prefix="tempus" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="i18n" %>
<%@ page import="org.springframework.web.servlet.support.RequestContextUtils" language="java"%>
<%@ page import="java.util.UUID" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
	String locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request).toString();
%>
    <link href="../assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
    <link href="../assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
    	<div class="form-horizontal" >
    		<input type="hidden" id="status" name="status" />
    		<input type="hidden" id="id" name="id" value="${returnObject.FID}"/>
			<div class="form-group">
				<div class="form-group-inline">
	        		<label class="col-md-2 control-label">标题<span class="required">*</span></label>
	        		<div class="col-md-10"><input class="form-control" name="name" value="${returnObject.FNAME}" readonly /></div>
        		</div>
			</div>
			<div class="form-group">
				<div class="form-group-inline">
	        		<label class="col-md-2 control-label">申请部门<span class="required">*</span></label>
	        		<div class="col-md-4"><input class="form-control" value="${returnObject.FDEPARTMENTNAME}" readonly /></div>
        		</div>
        		<div class="form-group-inline">
	        		<label class="col-md-2 control-label">申请人<span class="required">*</span></label>
	        		<div class="col-md-4"><input class="form-control" value="${returnObject.FCREATORNAME}" readonly /></div>
	        	</div>
			</div>
			<div class="form-group" id="contract" <c:if test="${returnObject.FCONTRACTPAYMENT=='0'}">style="display: none;"</c:if>>
				<div class="form-group-inline">
	        		<label class="col-md-2 control-label">合同编号<span class="required">*</span></label>
	        		<div class="col-md-4">
	        			<%-- <input class="form-control" id="contractnum" value="${returnObject.FCONTRACTNUM}" required readonly style="cursor: pointer;" readonly />
	        			<span class="caret pull-right" style="margin-top: -18px;margin-right: 5px;"></span>
	        			<input type="hidden" id="contractid" name="contractid" value="${returnObject.FCONTRACTID}" /> --%>
	        			<div class="input-group">
	                       <input type="text" class="form-control" value="${returnObject.FCONTRACTNUM}" readonly>
	                       <input type="hidden" name="contractid" value="${returnObject.FCONTRACTID}" />
	                       <span class="input-group-btn">
	                           <a class="btn green" href="./ShowPage?processinstanceid=${returnObject.FCONTRACTPROCESSINSTANCEID}" target="_blank" >查看</a>
	                       </span>
	                   	</div>
	        		</div>
        		</div>
				<div class="form-group-inline">
	        		<label class="col-md-2 control-label">签约单位<span class="required">*</span></label>
	        		<div class="col-md-4"><input class="form-control" id="signorg" name="signorg" value="${returnObject.FSIDENAME}" readonly /></div>
        		</div>
			</div>
			<div class="form-group">
				<div class="form-group-inline">
	        		<label class="col-md-2 control-label">付款公司</label>
	        		<div class="col-md-4">
	        			<select class="form-control" name="paymentcompany">
			        		<c:forEach items="${company}" var="row" >
			        			<option value="${row.FID}" <c:if test="${row.FID==returnObject.FPAYMENTCOMPANY}" >selected</c:if> >${row.FNAME}</option>
			        		</c:forEach>
	        			</select>	        			
	        		</div>
        		</div>
        		<div class="form-group-inline">
	        		<label class="col-md-2 control-label">发票编号</label>
	        		<div class="col-md-4"><input class="form-control" name="invoicenums" value="${returnObject.FINVOICENUMS}" readonly /></div>
        		</div>
        	</div>				
			<div class="form-group">
				<div class="form-group-inline">
	        		<label class="col-md-2 control-label">付款方式<span class="required">*</span></label>
	        		<div class="col-md-4">
	        			<select class="form-control" name="paymentmethod" disabled >
			        		<option value="CHECK" <c:if test="${returnObject.FPAYMENTMETHOD=='CHECK'}">selected</c:if> >支票</option>
			        		<option value="WIRE" <c:if test="${returnObject.FPAYMENTMETHOD=='WIRE'}">selected</c:if> >电汇</option>
	        			</select>
	        		</div>
        		</div>
        		<div class="form-group-inline">
	        		<label class="col-md-2 control-label">要求付款时间<span class="required">*</span></label>
	        		<div class="col-md-4">
	        			<div class="input-group date date-picker" data-date-format="yyyy-mm-dd" >
		                    <input name="paymentdate" class="form-control" value="${fn:substring(returnObject.FPAYMENTDATE, 0, 10)}" readonly />
		                    <span class="input-group-btn" style="vertical-align: top;">
		                        <button class="btn default" type="button">
		                            <i class="fa fa-calendar"></i>
		                        </button>
		                    </span>
		                </div>
	        		</div>
        		</div>
			</div>
			<div class="form-group">
				<div class="form-group-inline">
	        		<label class="col-md-2 control-label">付款金额（小写）<span class="required">*</span></label>
	        		<div class="col-md-4"><input class="form-control" id="smallamount" name="smallamount" type="text" value="${returnObject.FSMALLAMOUNT}" readonly /></div>
        		</div>
        		<div class="form-group-inline">
	        		<label class="col-md-2 control-label">付款金额（大写）<span class="required">*</span></label>
	        		<div class="col-md-4"><input class="form-control" id="bigamount" name="bigamount" value="${returnObject.FBIGAMOUNT}" readonly /></div>
        		</div>
			</div>
			<div class="form-group">
				<div class="form-group-inline">
	        		<label class="col-md-2 control-label">币种<span class="required">*</span></label>
	        		<div class="col-md-4">
	        			<select name="currency" class="form-control" disabled >
		        			<option value="CNY" <c:if test="${returnObject.FCURRENCY=='CNY'}">selected</c:if> >人民币</option>
		        			<option value="HKD" <c:if test="${returnObject.FCURRENCY=='HKD'}">selected</c:if> >港币</option>
		        			<option value="USD" <c:if test="${returnObject.FCURRENCY=='USD'}">selected</c:if> >美元</option>
		           		</select>
	        		</div>
        		</div>
				<div class="form-group-inline">
	        		<label class="col-md-2 control-label">收款单位<span class="required">*</span></label>
	        		<div class="col-md-4">
	        			<input class="form-control" id="suppliername" value="${returnObject.VENDOR_NAME}" readonly />
	        			<input type="hidden" id="supplierid" name="supplierid" value="${returnObject.VENDOR_ID}" />
	        		</div>
        		</div>
			</div>
			<div class="form-group">
				<div class="form-group-inline">
	        		<label class="col-md-2 control-label">银行账号<span class="required">*</span></label>
	        		<div class="col-md-4"><input class="form-control" name="bankaccount" value="${returnObject.FBANKACCOUNT}" readonly /></div>
	        	</div>
	        	<div class="form-group-inline">
	        		<label class="col-md-2 control-label">开户行<span class="required">*</span></label>
	        		<div class="col-md-4"><input class="form-control" name="openbank" value="${returnObject.FOPENBANK}" readonly /></div>
	        	</div>
			</div>
			<div class="form-group">
				<div class="form-group-inline">
	        		<label class="col-md-2 control-label">经办说明<span class="required">*</span></label>
	        		<div class="col-md-10"><textarea class="form-control" name="remark" rows="5" readonly >${returnObject.FREMARK}</textarea></div>
	        	</div>
			</div>
    	</div>
    	<%@ include file="../layout/common-bill.jsp" %>
		<h3>财务录入：</h3>
		<form id="form2" class="form-horizontal" role="form">
			<div class="form-group">
        		<label class="col-md-2 control-label">发票摘要<span class="required">*</span></label>
        		<div class="col-md-10"><textarea class="form-control" name="invoicedescription" rows="5" required ></textarea></div>
        		<input name="id" type="hidden" value="${returnObject.FID}" />
			</div>
		</form>
<script src="../assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/jquery-validation/js/additional-methods.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/jquery-validation/js/localization/messages_<%=locale %>.min.js" type="text/javascript"></script>
<script>
	$(function(){
		$('#form2').validate({
	        errorElement: 'span',
	        errorClass: 'help-block',
	        focusInvalid: false,
	        errorPlacement: function (error, element) { // render error placement for each input type
	            var cont = $(element).parent('.input-group').after(error);
	        },
	        highlight: function (element) { // hightlight error inputs
	            $(element).closest('.form-group').addClass('has-error'); // set error class to the control group
	        },
	        unhighlight: function (element) { // revert the change done by hightlight
	            $(element).closest('.form-group').removeClass('has-error'); // set error class to the control group
	        },
	        submitHandler: function (form) { //表单校验通过并提交表单
	        	App.blockUI({animate: true});
        		var data = $(form).serializeJSON();
        		$.post("../Payment/Accounting",data,function(rs){
        			if(rs.code=="S"){
        				approval(btn);
        			}else{
        				swal("Error!", "系统出错，请联系管理员", "error");
        			}
            	});
	        }
	    });
	});
	
</script>