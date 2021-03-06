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
    <link href="../assets/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css" />
    	<div class="form-horizontal" >
    		<button class='btn btn-success pull-right' style="margin-top: -65px;" onclick="printform('${returnObject.FID}')">打印</button>
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
	        			<select class="form-control" name="paymentcompany" disabled >
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
		<h2>财务录入：</h2>
		<div class="form-horizontal">
			<div class="form-group">
        		<label class="col-md-2 control-label">发票摘要<span class="required">*</span></label>
        		<div class="col-md-10"><textarea class="form-control" name="invoicedescription" rows="5" readonly >${returnObject.FINVOICEDESCRIPTION}</textarea></div>
			</div>
		</div>
		<hr>
		<div class="form-horizontal">
			<input type="hidden" name="id" value="${returnObject.FID}" />
			<div class="form-group">
				<div class="form-group-inline">
					<label class="control-label col-md-2">付款银行账户<span class="required">*</span></label>
					<div class="col-md-4">
						<select class="form-control" name="paymentaccount" disabled >
							<option value="">请选择</option>
							<c:forEach items="${returnObject.paymentaccounts}" var="row" varStatus="s">
								<option value="${row.FNAME}" <c:if test="${row.FNAME==returnObject.FPAYMENTACCOUNT}">selected</c:if> >${row.FNAME}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="form-group-inline">
					<label class="control-label col-md-2">付款日期<span class="required">*</span></label>
					<div class="col-md-4">
						<div class="input-group date date-picker" data-date-format="yyyy-mm-dd" >
					       <input name="paymentdate" class="form-control" value="${fn:substring(returnObject.FAPPAYMENTDATE, 0, 10)}" readonly />
					       <span class="input-group-btn" style="vertical-align: top;">
					            <button class="btn default" type="button">
					                <i class="fa fa-calendar"></i>
					            </button>
					        </span>
					    </div>
					</div>
				</div>
			</div>
		</div>
		<hr>
		<div class="row form-horizontal">
       		<label class="col-md-2 control-label">AP发票编号<span class="required">*</span></label>
       		<div class="col-md-10"><input class="form-control" value="${returnObject.INVOICE_NUM}" readonly /></div>
		</div>
<script src="../assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/bootstrap-datepicker/locales/bootstrap-datepicker.<%=locale %>.min.js" type="text/javascript"></script>
<script>
	var printform = function(id){
	    $.get("../Print/Payment",{"id":id},function(jsp){
	    	var newwindow = window.open();
	    	if(newwindow){
	    		newwindow.document.write(jsp);
		    	newwindow.print();
		    	newwindow.document.getElementById("print").execWB(7,1);
	    	}else{
	    		alert("请启用浏览器弹出窗口，刷新后重试！");
	    	}
	    });
	}
</script>