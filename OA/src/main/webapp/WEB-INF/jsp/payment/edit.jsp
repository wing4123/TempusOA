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
<tempus:ContentPage materPageId="masterstep">

<tempus:Content contentPlaceHolderId="title">
    <title><i18n:message code="合同付款申请"  /></title>
    <link href="../assets/global/plugins/bootstrap-select/css/bootstrap-select.css" rel="stylesheet" type="text/css" />
    <link href="../assets/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css" />
    <style>
    	.bill-group{display: inline-block;}
    	table.dataTable td.sorting_1{background: none !important;}
    </style>
</tempus:Content>

<tempus:Content contentPlaceHolderId="main">
<div class="page-content" style="min-height: 1147px;">
    <!-- BEGIN PAGE HEAD-->
    <div class="page-head">
        <!-- BEGIN PAGE TITLE -->
        <div class="page-title">
            <h1>合同付款申请</h1>
        </div>
        <!-- END PAGE TITLE -->
    </div>
    <!-- END PAGE HEAD-->
    <!-- BEGIN PAGE BREADCRUMB -->
    <!-- BEGIN PAGE BASE CONTENT -->
    <div class="page-base-content">
    	<div>
    		<a class="btn btn-default" href="../MyWorkFlow/Initiate">返回</a>
    		<button class="btn btn-success" onclick="$('#status').val(1);$('#form_bill').submit()">保存为草稿</button>
    		<button class="btn btn-success" onclick="$('#status').val(2);$('#form_bill').submit()">提交</button>
    	</div>
    	<hr/>
    	<form id="form_bill" class="form-horizontal" role="form">
    		<input type="hidden" id="status" name="status" />
    		<input type="hidden" id="id" name="id" value="${returnObject.FID}"/>
			<div class="form-group">
				<div class="form-group-inline">
	        		<label class="col-md-2 control-label">标题<span class="required">*</span></label>
	        		<div class="col-md-10"><input class="form-control" name="name" value="${returnObject.FNAME}" required /></div>
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
	        			<input class="form-control" id="contractnum" value="${returnObject.FCONTRACTNUM}" required readonly style="cursor: pointer;" onclick="$('#modal_contract').modal('show')" />
	        			<span class="caret pull-right" style="margin-top: -18px;margin-right: 5px;"></span>
	        			<input type="hidden" id="contractid" name="contractid" value="${returnObject.FCONTRACTID}" />
	        		</div>
        		</div>
				<div class="form-group-inline">
	        		<label class="col-md-2 control-label">签约单位<span class="required">*</span></label>
	        		<div class="col-md-4"><input class="form-control" id="signorg" name="signorg" value="${returnObject.FSIDENAME}" required readonly /></div>
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
	        		<div class="col-md-4"><input class="form-control" name="invoicenums" value="${returnObject.FINVOICENUMS}" /></div>
        		</div>
        	</div>				
			<div class="form-group">
				<div class="form-group-inline">
	        		<label class="col-md-2 control-label">付款方式<span class="required">*</span></label>
	        		<div class="col-md-4">
	        			<select class="form-control" name="paymentmethod">
			        		<option value="CHECK" <c:if test="${returnObject.FPAYMENTMETHOD=='CHECK'}">selected</c:if> >支票</option>
			        		<option value="WIRE" <c:if test="${returnObject.FPAYMENTMETHOD=='WIRE'}">selected</c:if> >电汇</option>
	        			</select>
	        		</div>
        		</div>
        		<div class="form-group-inline">
	        		<label class="col-md-2 control-label">要求付款时间<span class="required">*</span></label>
	        		<div class="col-md-4">
	        			<div class="input-group date date-picker" data-date-format="yyyy-mm-dd" >
		                    <input name="paymentdate" class="form-control" value="${fn:substring(returnObject.FPAYMENTDATE, 0, 10)}" required />
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
	        		<div class="col-md-4"><input class="form-control" id="smallamount" name="smallamount" type="text" value="${returnObject.FSMALLAMOUNT}" required /></div>
        		</div>
        		<div class="form-group-inline">
	        		<label class="col-md-2 control-label">付款金额（大写）<span class="required">*</span></label>
	        		<div class="col-md-4"><input class="form-control" id="bigamount" name="bigamount" value="${returnObject.FBIGAMOUNT}" readonly required /></div>
        		</div>
			</div>
			<div class="form-group">
				<div class="form-group-inline">
	        		<label class="col-md-2 control-label">币种<span class="required">*</span></label>
	        		<div class="col-md-4">
	        			<select name="currency" class="form-control" required >
		        			<option value="CNY" <c:if test="${returnObject.FCURRENCY=='CNY'}">selected</c:if> >人民币</option>
		        			<option value="HKD" <c:if test="${returnObject.FCURRENCY=='HKD'}">selected</c:if> >港币</option>
		        			<option value="USD" <c:if test="${returnObject.FCURRENCY=='USD'}">selected</c:if> >美元</option>
		           		</select>
	        		</div>
        		</div>
				<div class="form-group-inline">
	        		<label class="col-md-2 control-label">收款单位<span class="required">*</span></label>
	        		<div class="col-md-4">
	        			<input class="form-control" id="suppliername" value="${returnObject.VENDOR_NAME}" readonly required style="cursor: pointer;" onclick="$('#modal_supplier').modal('show')" />
	        			<input type="hidden" id="supplierid" name="supplierid" value="${returnObject.VENDOR_ID}" required />
	        			<span class="caret pull-right" style="margin-top: -18px;margin-right: 5px;"></span>
	        		</div>
        		</div>
			</div>
			<div class="form-group">
				<div class="form-group-inline">
	        		<label class="col-md-2 control-label">银行账号<span class="required">*</span></label>
	        		<div class="col-md-4"><input class="form-control" name="bankaccount" value="${returnObject.FBANKACCOUNT}" required /></div>
	        	</div>
	        	<div class="form-group-inline">
	        		<label class="col-md-2 control-label">开户行<span class="required">*</span></label>
	        		<div class="col-md-4"><input class="form-control" name="openbank" value="${returnObject.FOPENBANK}" required /></div>
	        	</div>
			</div>
			<div class="form-group">
				<div class="form-group-inline">
	        		<label class="col-md-2 control-label">经办说明<span class="required">*</span></label>
	        		<div class="col-md-10"><textarea class="form-control" name="remark" rows="5" required >${returnObject.FREMARK}</textarea></div>
	        	</div>
			</div>
    	</form>
    	<%@ include file="../layout/common-bill.jsp" %>
    </div>
    <!-- END PAGE BASE CONTENT -->
</div>

<div id="modal_qrcode" class="modal fade bs-modal-lg" tabindex="-1">
	<div class="modal-dialog" style="width: 300px;height: 300px;">
		<div id="qrcode"></div>
	</div>
</div>

<div id="modal_supplier" class="modal fade" tabindex="-1" data-backdrop="static" >
	<div class="modal-dialog">
		<div class="modal-content" style="width: 700px;">
		    <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
		        <h4 class="modal-title">选择收款单位</h4>
		    </div>
		    <div class="modal-body">
		    	<table id="datatable_supplier" class="table table-bordered">
		            <thead>
		                <tr>
		                    <th>收款单位名称</th>
		                </tr>
		            </thead>
		            <tbody></tbody>
	            </table>
		    </div>
		    <div class="modal-footer">
		        <button type="button" data-dismiss="modal" class="btn dark btn-outline"><i18n:message code="common.cancel" /></button>
		        <button type="button" class="btn green" onclick="selectsupplier()"><i18n:message code="确定" /></button>
		    </div>
		</div>
	</div>
</div>

<div id="modal_contract" class="modal fade" tabindex="-1" data-backdrop="static" >
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
		    <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
		        <h4 class="modal-title">选择合同</h4>
		    </div>
		    <div class="modal-body">
		    	<table id="datatable_contract" class="table table-bordered">
		            <thead>
		                <tr>
		                    <th>合同编号</th>
		                    <th>合同名称</th>
		                </tr>
		            </thead>
		            <tbody></tbody>
	            </table>
		    </div>
		    <div class="modal-footer">
		        <button type="button" data-dismiss="modal" class="btn dark btn-outline"><i18n:message code="common.cancel" /></button>
		        <button type="button" class="btn green" onclick="selectcontract()"><i18n:message code="确定" /></button>
		    </div>
		</div>
	</div>
</div>

</tempus:Content>

<tempus:Content contentPlaceHolderId="js">
<script src="../assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/jquery-validation/js/additional-methods.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/jquery-validation/js/localization/messages_<%=locale %>.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/bootstrap-datepicker/locales/bootstrap-datepicker.<%=locale %>.min.js" type="text/javascript"></script>
<script src="../js/smalltoBIG.js" type="text/javascript"></script>
<script>

$(function(){
	$('.date-picker').datepicker({
        autoclose: true,
        language: "<%=locale.replace("_", "-") %>",
		weekStart: 0
    });
	
    initamoutinput();
    initvalidate();
    initialdatatable_supplier();
    initialdatatable_contract();
});

var initvalidate = function() {
	$('#form_bill').validate({
        errorElement: 'span',
        errorClass: 'help-block',
        focusInvalid: false,
        highlight: function (element) {
        	$(element).closest('.form-group-inline').addClass('has-error');
        },
        unhighlight: function (element) {
        	$(element).closest('.form-group-inline').removeClass('has-error');
        },
        submitHandler: function (form) { //表单校验通过并提交表单
        	var data = $(form).serializeJSON();
         	$.post("./Save",data,function(){
        		swal({
				  title: "Success",
				  text: "保存成功！",
				  type: "success"
				},
				function(){
					location.href="../MyWorkFlow/Initiate";
				});
        	});
        }
    });
}

function clearNoNum(obj){
	obj.value = obj.value.replace(/[^\d.]/g,""); //清除"数字"和"."以外的字符
	obj.value = obj.value.replace(/^\./g,""); //验证第一个字符是数字而不是
	obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的
	obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
	obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'); //只能输入两个小数
}

var initamoutinput = function(){
	$("#smallamount").on('keyup', function (event) {
	    var $amountInput = $(this);
	    //响应鼠标事件，允许左右方向键移动 
	    event = window.event || event;
	    if (event.keyCode == 37 | event.keyCode == 39) {
	        return;
	    }
	    //先把非数字的都替换掉，除了数字和. 
	    $amountInput.val($amountInput.val().replace(/[^\d.]/g, "").
	   		//只允许一个小数点              
	        replace(/^\./g, "").replace(/\.{2,}/g, ".").
	        //只能输入小数点后两位
	        replace(".", "$#$").replace(/\./g, "").replace("$#$", ".").replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'));
	    
	    var big = $(this).small2BIG();
		$("#bigamount").val(big);
	});
	$("#smallamount").on('blur', function () {
	    var $amountInput = $(this);
	    //最后一位是小数点的话，移除
	    $amountInput.val(($amountInput.val().replace(/\.$/g, "")));
	    //this.value=Number(this.value).toFixed(2);
	});
}

var initialdatatable_supplier = function(){
	var datatable = $("#datatable_supplier");

	datatable.DataTable({
		serverSide: true,
		ordering: true,
		searching: true,
		order: [[0, "asc"]],
		lengthMenu: [[10,20,50,100],[10,20,50,100]],
      	pageLength: 10,
      	pagingType: "bootstrap_full_number",
		//dom: '<<t>lp>',
		ajax: {
	        url: './getSupplierList'
		},
	    columns: [
	    	{data:"VENDOR_NAME"}
	    ],
        language: {
        	processin: "<i18n:message code="datatable.processin" />",
        	loadingRecords: "<i18n:message code="datatable.loadingRecords" />",
            zeroRecord: "<i18n:message code="datatable.zeroRecord" />",
            emptyTable: "<i18n:message code="datatable.emptyTable" />",
            info: "<i18n:message code="datatable.info" />",
            infoFiltered: "<i18n:message code="datatable.infoFiltered" />",
           	lengthMenu: "<i18n:message code="datatable.lengthMenu" />",
           	search: "<i18n:message code="datatable.search" />",
            paginate:{
            	first: "<i18n:message code="datatable.first" />",
            	last: "<i18n:message code="datatable.last" />",
            	next: "<i18n:message code="datatable.next" />",
            	previous: "<i18n:message code="datatable.previous" />"
            }
        }
	});
	
	datatable.find('tbody').on('click', 'tr', function () {
	    if ($(this).hasClass('selected')) {
	       	$(this).removeClass('selected');
	    }else{
	    	datatable.find('tr.selected').removeClass('selected');
			$(this).addClass('selected');
	    }
	});
}

var selectsupplier = function(){
	var data = $("#datatable_supplier").DataTable().rows('.selected').data();
	if(data.length==0){
		swal("Error!", "没有选择收款单位", "error");
	}else{
		$("#suppliername").val(data[0].VENDOR_NAME);
		$("#supplierid").val(data[0].VENDOR_ID);
		$("#modal_supplier").modal("hide");
	}
}

var initialdatatable_contract = function(){
	var datatable = $("#datatable_contract");

	datatable.DataTable({
		serverSide: true,
		ordering: true,
		searching: true,
		order: [[0, "asc"]],
		lengthMenu: [[10,20,50,100],[10,20,50,100]],
      	pageLength: 10,
      	pagingType: "bootstrap_full_number",
		//dom: '<<t>lp>',
		ajax: {
	        url: './getContractList'
		},
	    columns: [
	    	{data:"FNUMBER"},
	    	{data:"FNAME"}
	    ],
        language: {
        	processin: "<i18n:message code="datatable.processin" />",
        	loadingRecords: "<i18n:message code="datatable.loadingRecords" />",
            zeroRecord: "<i18n:message code="datatable.zeroRecord" />",
            emptyTable: "<i18n:message code="datatable.emptyTable" />",
            info: "<i18n:message code="datatable.info" />",
            infoFiltered: "<i18n:message code="datatable.infoFiltered" />",
           	lengthMenu: "<i18n:message code="datatable.lengthMenu" />",
           	search: "<i18n:message code="datatable.search" />",
            paginate:{
            	first: "<i18n:message code="datatable.first" />",
            	last: "<i18n:message code="datatable.last" />",
            	next: "<i18n:message code="datatable.next" />",
            	previous: "<i18n:message code="datatable.previous" />"
            }
        }
	});
	
	datatable.find('tbody').on('click', 'tr', function () {
	    if ($(this).hasClass('selected')) {
	       	$(this).removeClass('selected');
	    }else{
	    	datatable.find('tr.selected').removeClass('selected');
			$(this).addClass('selected');
	    }
	});
}

var selectcontract = function(){
	var data = $("#datatable_contract").DataTable().rows('.selected').data();
	if(data.length==0){
		swal("Error!", "没有选择合同", "error");
	}else{
		$("#contractid").val(data[0].FID);
		$("#contractnum").val(data[0].FNUMBER);
		$("#signorg").val(data[0].FSIDENAME);
		$("#modal_contract").modal("hide");
	}
}

</script>
</tempus:Content>

</tempus:ContentPage>