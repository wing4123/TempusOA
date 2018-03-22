<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/master/masterPage.tld" prefix="tempus" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="i18n" %>
<%@ page import="org.springframework.web.servlet.support.RequestContextUtils" language="java"%>
<%@ page import="java.util.UUID" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request).toString();
%>
<tempus:ContentPage materPageId="masterstep">

<tempus:Content contentPlaceHolderId="title">
    <title><i18n:message code="礼品及业务招待费申请"  /></title>
    <link href="../assets/global/plugins/bootstrap-select/css/bootstrap-select.css" rel="stylesheet" type="text/css" />
    <link href="../assets/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css" />
</tempus:Content>

<tempus:Content contentPlaceHolderId="main">
<div class="page-content" style="min-height: 1147px;">
    <!-- BEGIN PAGE HEAD-->
    <div class="page-head">
        <!-- BEGIN PAGE TITLE -->
        <div class="page-title">
            <h1>礼品及业务招待费申请</h1>
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
    		<input type="hidden" id="id" name="id" value="<%=UUID.randomUUID().toString() %>"/>
    		<div class="row">
    			<div class="col-md-10 col-md-offset-1">
    				<div class="form-group" style="padding: 0px 15px;">
		           		<label class="col-md-2 control-label">标题<span class="required">*</span></label>
		           		<div class="col-md-10"><input class="form-control" name="name" value="${USER.name}的业务招待费申请" required /></div>
		   			</div>
    				<div class="col-md-6">
		   				<div class="form-group">
			           		<label class="col-md-4 control-label">申请人</label>
			           		<div class="col-md-8"><input class="form-control" value="${USER.name}" readonly /></div>
			   			</div>
			   			<div class="form-group">
			           		<label class="col-md-4 control-label">金额<span class="required">*</span></label>
			           		<div class="col-md-8"><input class="form-control" id="amount" name="amount" required /></div>
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
				                	<option value="CNY">人民币</option>
				                	<option value="HKD">港币</option>
				                	<option value="USD">美元币</option>
				                </select>
			                </div>
		                </div>
	    			</div>
	    			<div class="form-group" style="padding: 0px 15px;">
		           		<label class="col-md-2 control-label">客户名称<span class="required">*</span></label>
		           		<div class="col-md-10"><input class="form-control" name="client" required /></div>
		   			</div>
		   			<div class="form-group" style="padding: 0px 15px;">
		           		<label class="col-md-2 control-label">招待地点<span class="required">*</span></label>
		           		<div class="col-md-10"><input class="form-control" name="location" required /></div>
		   			</div>   			
		   			<div class="form-group" style="padding: 0px 15px;">
		           		<label class="col-md-2 control-label">事由<span class="required">*</span></label>
		           		<div class="col-md-10"><textarea class="form-control" name="cause" required ></textarea></div>
		   			</div>
		   			<div class="col-md-6">
			   			<div class="form-group">
			           		<label class="col-md-4 control-label">申请日期<span class="required">*</span></label>
			           		<div class="col-md-8">
			           			<div class="input-group date date-picker" data-date-format="yyyy-mm-dd" >
				                    <input name="date" class="form-control" required />
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
			           		<div class="col-md-8"><input class="form-control" name="peoplenumber" required onkeyup="clearNoNum(this)" /></div>
			   			</div>
		   			</div>
		   			<div class="form-group" style="padding: 0px 15px;">
		           		<label class="col-md-2 control-label">公司陪同人员<span class="required">*</span></label>
		           		<div class="col-md-10"><input class="form-control" name="staff" required /></div>
		   			</div>
    			
    			</div>
    		</div>
    	</form>
    	<%@ include file="../layout/common-bill.jsp" %>
    </div>
    <!-- END PAGE BASE CONTENT -->
</div>
</tempus:Content>

<tempus:Content contentPlaceHolderId="js">
<script src="../assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/jquery-validation/js/additional-methods.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/jquery-validation/js/localization/messages_<%=locale %>.min.js" type="text/javascript"></script>
<script src="../assets/global/scripts/datatable.js" type="text/javascript"></script>
<script src="../assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>
<script src="../js/jquery.qrcode.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/bootstrap-datepicker/locales/bootstrap-datepicker.<%=locale %>.min.js" type="text/javascript"></script>
<script>
$(function(){
	$('.date-picker').datepicker({
        autoclose: true,
        language: "<%=locale.replace("_", "-") %>",
		weekStart: 1
    });
    
    initamoutinput();
    initvalidate();
});

var initvalidate = function() {
	$('#form_bill').validate({
        errorElement: 'span',
        errorClass: 'help-block',
        focusInvalid: false,
        highlight: function (element) {
        	$(element).closest('.form-group').addClass('has-error');
        },
        unhighlight: function (element) {
        	$(element).closest('.form-group').removeClass('has-error');
        },
        submitHandler: function (form) { //表单校验通过并提交表单
        	var data = $(form).serializeJSON();
         	$.post("./Save",data,function(){
        		//swal("Success!", "", "success");
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

var usetemplate = function(is){
	if(is==0){
		$("#templatereason").show();
	}else if(is==1){
		$("#templatereason").hide();
	}
}

var fnewcontract = function(is){
	if(is==0){
		$("#oldcontractchange").show();
	}else if(is==1){
		$("#oldcontractchange").hide();
	}
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
	    //this.value=Number(this.value).toFixed(2);
	});
}

</script>
</tempus:Content>

</tempus:ContentPage>