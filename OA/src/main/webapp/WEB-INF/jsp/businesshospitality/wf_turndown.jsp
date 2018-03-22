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
    	<form id="form_bill" class="form-horizontal" role="form">
    		<input type="hidden" id="status" name="status" />
    		<input type="hidden" id="id" name="id" value="${returnObject.FID}"/>
    		<div class="row">
    			<div class="col-md-10 col-md-offset-1">
    				<div class="form-group" style="padding: 0px 15px;">
		           		<label class="col-md-2 control-label">标题<span class="required">*</span></label>
		           		<div class="col-md-10"><input class="form-control" name="name" value="${returnObject.FNAME}" required /></div>
		   			</div>
    				<div class="col-md-6">
		   				<div class="form-group">
			           		<label class="col-md-4 control-label">申请人</label>
			           		<div class="col-md-8"><input class="form-control" value="${USER.name}" readonly /></div>
			   			</div>
			   			<div class="form-group">
			           		<label class="col-md-4 control-label">金额<span class="required">*</span></label>
			           		<div class="col-md-8"><input class="form-control" id="amount" name="amount" value="${returnObject.FAMOUNT}" required /></div>
			   			</div>	
	    			</div>
	    			<div class="col-md-6">
			   			<div class="form-group">
			           		<label class="col-md-4 control-label">部门</label>
			           		<div class="col-md-8"><input class="form-control" value="${USER.departmentname}" readonly /></div>
			   			</div>
 			   			<div class="form-group">
			                <label class="col-md-4 control-label">币种<span class="required">*</span></label>
			                <div class="col-md-8">
				                <select class="form-control" name="currency" required >
				                	<option value="">请选择</option>
				                	<option value="CNY" <c:if test="${returnObject.FCURRENCY=='CNY'}" >selected</c:if> >人民币 CNY</option>
				                	<option value="HKD" <c:if test="${returnObject.FCURRENCY=='HKD'}" >selected</c:if> >港币 HKD</option>
				                	<option value="USD" <c:if test="${returnObject.FCURRENCY=='USD'}" >selected</c:if> >美元币 USD</option>
				                </select>
			                </div>
		                </div>
	    			</div>
	    			<div class="form-group" style="padding: 0px 15px;">
		           		<label class="col-md-2 control-label">客户名称<span class="required">*</span></label>
		           		<div class="col-md-10"><input class="form-control" name="client" value="${returnObject.FCLIENT}" required /></div>
		   			</div>
		   			<div class="form-group" style="padding: 0px 15px;">
		           		<label class="col-md-2 control-label">招待地点<span class="required">*</span></label>
		           		<div class="col-md-10"><input class="form-control" name="location" value="${returnObject.FLOCATION}" required /></div>
		   			</div>
		   			<div class="form-group" style="padding: 0px 15px;">
		           		<label class="col-md-2 control-label">事由<span class="required">*</span></label>
		           		<div class="col-md-10"><textarea class="form-control" name="cause" required >${returnObject.FCAUSE}</textarea></div>
		   			</div>
		   			<div class="col-md-6">
			   			<div class="form-group">
			           		<label class="col-md-4 control-label">申请日期<span class="required">*</span></label>
			           		<div class="col-md-8">
			           			<div class="input-group date date-picker" data-date-format="yyyy-mm-dd" >
				                    <input name="date" class="form-control" value="${fn:substring(returnObject.FDATE, 0, 10)}" required />
				                    <span class="input-group-btn" style="vertical-align: top;">
				                        <button class="btn default" type="button">
				                            <i class="fa fa-calendar"></i>
				                        </button>
				                    </span>
				                </div>
			           		</div>
			   			</div>
		   			</div>
		   			<div class="col-md-6">
		   				<div class="form-group">
			           		<label class="col-md-4 control-label">参加人数<span class="required">*</span></label>
			           		<div class="col-md-8"><input class="form-control" name="peoplenumber" value="${returnObject.FPEOPLENUMBER}" required onkeyup="clearNoNum(this)" /></div>
			   			</div>
		   			</div>
		   			<div class="form-group" style="padding: 0px 15px;">
		           		<label class="col-md-2 control-label">公司陪同人员<span class="required">*</span></label>
		           		<div class="col-md-10"><input class="form-control" name="staff" value="${returnObject.FSTAFF}" required /></div>
		   			</div>
    			
    			</div>
    		</div>
    	</form>
    	<%@ include file="../layout/common-bill.jsp" %>
<script src="../assets/global/scripts/datatable.js" type="text/javascript"></script>
<script src="../assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>
<script src="../assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/jquery-validation/js/additional-methods.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/jquery-validation/js/localization/messages_<%=locale %>.min.js" type="text/javascript"></script>
<script src="../js/jquery.qrcode.min.js" type="text/javascript"></script>
<script>

$(function(){
    initamoutinput();
    initvalidate();
});

var initvalidate = function() {
	$('#form_bill').validate({
        errorElement: 'span',
        errorClass: 'help-block',
        focusInvalid: false,
        highlight: function (element) {
        	if($(element).parent().parent().attr("class").indexOf(".form-group")>-1){
        		$(element).closest('.form-group').addClass('has-error');
        	}else{
        		$(element).parent().addClass('has-error');
        	}
        },
        unhighlight: function (element) {
        	if($(element).parent().parent().attr("class").indexOf(".form-group")>-1){
        		$(element).closest('.form-group').removeClass('has-error');
        	}else{
        		$(element).parent().removeClass('has-error');
        	}
        },
        submitHandler: function (form) { //表单校验通过并提交表单
        	var data = $(form).serializeJSON();
        	$.post("../BusinessHospitality/Save",data,function(){
        		//swal("Success!", "", "success");
        		approval(btn);
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
	$("#amount").on('keyup', function (event) {
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
	            });
	$("#amount").on('blur', function () {
	    var $amountInput = $(this);
	    //最后一位是小数点的话，移除
	    $amountInput.val(($amountInput.val().replace(/\.$/g, "")));
	});
}
</script>