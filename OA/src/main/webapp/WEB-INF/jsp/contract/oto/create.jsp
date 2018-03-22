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
    <title><i18n:message code="合同"  /></title>
    <link href="../assets/global/plugins/bootstrap-select/css/bootstrap-select.css" rel="stylesheet" type="text/css" />
</tempus:Content>

<tempus:Content contentPlaceHolderId="main">
<div class="page-content" style="min-height: 1147px;">
    <!-- BEGIN PAGE HEAD-->
    <div class="page-head">
        <!-- BEGIN PAGE TITLE -->
        <div class="page-title">
            <h1>合同</h1>
        </div>
        <!-- END PAGE TITLE -->
    </div>
    <!-- END PAGE HEAD-->
    <!-- BEGIN PAGE BREADCRUMB -->
    <!-- BEGIN PAGE BASE CONTENT -->
    <div class="page-base-content">
    	<div>
    		<a class="btn btn-default" href="../MyWorkFlow/Initiate">返回</a>
    		<button class="btn btn-success" onclick="$('#status').val(1);$('#form_bill').submit()" <c:if test="${returnObject.old==true}">style="display: none;"</c:if> >保存为草稿</button>
    		<button class="btn btn-success" onclick="$('#status').val(2);$('#form_bill').submit()">提交</button>
    	</div>
    	<hr/>
    	<form id="form_bill" class="form-horizontal" role="form">
    		<input type="hidden" id="status" name="status" />
    		<input type="hidden" id="id" name="id" value="<%=UUID.randomUUID().toString() %>"/>
    		<input type="hidden" id="type1" name="type1" value="${returnObject.type}"/>
			<div class="form-group">
				<div class="form-group-inline">
	        		<label class="col-md-2 control-label">标题<span class="required">*</span></label>
	        		<div class="col-md-10"><input class="form-control" name="name" value="${USER.name}的合同审批" required /></div>
        		</div>
			</div>
   			<div class="form-group">
           		<label class="col-md-2 control-label">合同编号</label>
           		<div class="col-md-4"><input class="form-control" name="number" readonly /></div>
   			</div>
   			<div class="form-group">
	            <label class="col-md-2 control-label">合同类型<span class="required">*</span></label>
	            <div class="col-md-10">
	                <div class="mt-radio-inline" id="type">
	                	<c:forEach items="${returnObject.typelist}" var="row" varStatus="S">
		                    <label class="mt-radio">
		                        <input type="radio" name="type2" value="${row.FVALUE}">${row.FNAME}<span></span>
		                    </label>
	                    </c:forEach>
	                </div>
	            </div>
	        </div>
   			<div class="form-group">
	            <label class="col-md-2 control-label">腾邦签署主体<span class="required">*</span></label>
	            <div class="col-md-4">
	                <select class="form-control" name="company" required >
	                	<c:forEach items="${returnObject.company}" var="row" varStatus="S">
	                        <option value="${row.FID}">${row.FNAME}</option>
	                    </c:forEach>
	                </select>
	            </div>
	        </div>
	        <div class="form-group">
	            <label class="col-md-2 control-label">是否采用我方合同模板<span class="required">*</span></label>
	            <div class="col-md-2">
	                <div class="mt-radio-inline" style="padding-bottom: 0px;">
	                    <label class="mt-radio">
	                        <input type="radio" name="template" value="1" onclick="usetemplate(this.value)" checked />是<span></span>
	                    </label>
	                    <label class="mt-radio">
	                        <input type="radio" name="template" value="0" onclick="usetemplate(this.value)" />否<span></span>
	                    </label>
	                </div>
	            </div>
	            <div class="col-md-10 col-md-offset-2">
	            	<textarea id="templatereason" style="display: none;" name="templatereason" class="form-control" placeholder="原因"></textarea>
	            </div>
	        </div>
   			<div class="form-group">
	            <label class="col-md-2 control-label">是否新合同<span class="required">*</span></label>
	            <div class="col-md-10">
	                <div class="mt-radio-inline" style="padding-bottom: 0px;">
	                    <label class="mt-radio">
	                        <input type="radio" name="newcontract" value="1" onclick="fnewcontract(this.value)" checked />是 <span></span>
	                    </label>
	                    <label class="mt-radio">
	                        <input type="radio" name="newcontract" value="0" onclick="fnewcontract(this.value)" />否，旧合同续期 <span></span>
	                    </label>
	                </div>
	            </div>
	            <div class="col-md-10 col-md-offset-2">
	            	<textarea id="oldcontractchange" style="display: none;" name="oldcontractchange" class="form-control" placeholder="合同更改内容"></textarea>
	            </div>
	        </div>
   			<div class="form-group">
	            <label class="col-md-2 control-label">合同简介<span class="required">*</span></label>
	            <div class="col-md-10">
	            	<div class="row">
		            	<div class="col-md-5">
			                <label class="control-label">对方名称<span class="required">*</span></label>
			                <input class="form-control" name="sidename" required />
		                </div>
	                </div>
	                <div class="row">
		                <div class="col-md-12">
			                <label class="control-label">内容（目的）<span class="required">*</span></label>
			                <textarea class="form-control" name="content" required></textarea>
		                </div>
	                </div>
	                <div class="row">
		                <div class="col-md-2">
			                <label class="control-label">金额<span class="required">*</span></label>
			                <input class="form-control" id="amount" name="amount" type="number" max="30000000" required />
		                </div>
		                <div class="col-md-2">
			                <label class="control-label">币种<span class="required">*</span></label>
			                <select class="form-control" id="currency" name="currency" required>
			                	<option value="">请选择</option>
			                	<option value="CNY">人民币</option>
			                	<option value="HKD">港币</option>
			                	<option value="USD">美元币</option>
			                </select>
		                </div>
	                </div>
	                <div class="row">
		                <div class="col-md-12">
			                <label class="control-label">期限（是否可续期）<span class="required">*</span></label>
			                <input class="form-control" name="term" required />
		                </div>
	                </div>
	            </div>
	        </div>
    	</form>
    	<%@ include file="../../layout/common-bill.jsp" %>
    </div>
    <!-- END PAGE BASE CONTENT -->
</div>

</tempus:Content>

<tempus:Content contentPlaceHolderId="js">
<script src="../assets/global/plugins/jquery-repeater/jquery.repeater.js" type="text/javascript"></script>
<script src="../assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/jquery-validation/js/additional-methods.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/jquery-validation/js/localization/messages_<%=locale %>.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/bootstrap-select/js/bootstrap-select.min.js" type="text/javascript"></script>
<script src="../js/jquery.qrcode.min.js" type="text/javascript"></script>
<script>

$(function(){
    $("#type label").on("click",function(){
    	$("#type label").css({"color":"black"});
    	$(this).css({"color":"red"});
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
        	if(data.type2==null){
        		alert("请选择合同类型");
        		return false;
        	}
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
	});
}

</script>
</tempus:Content>

</tempus:ContentPage>