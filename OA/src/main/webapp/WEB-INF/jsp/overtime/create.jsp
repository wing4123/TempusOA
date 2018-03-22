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
    <title><i18n:message code="加班申请" /></title>
	<link href="../assets/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css" />
    <style>
    	.table .btn {margin-right: 0px;}
    	.required{color: red;}
    </style>
</tempus:Content>

<tempus:Content contentPlaceHolderId="main">
<div class="page-content" style="min-height: 1147px;">
    <!-- BEGIN PAGE HEAD-->
    <div class="page-head">
        <!-- BEGIN PAGE TITLE -->
        <div class="page-title">
            <h1>加班申请</h1>
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
			<div class="form-group">
				<div class="form-group-inline">
	        		<label class="col-md-2 control-label">标题<span class="required">*</span></label>
	        		<div class="col-md-10"><input class="form-control" name="name" value="${USER.name}的加班申请" required /></div>
        		</div>
			</div>
			<div class="form-group">
        		<div class="form-group-inline">
	        		<label class="col-md-2 control-label">申请人<span class="required">*</span></label>
	        		<div class="col-md-4"><input class="form-control" value="${USER.name}" readonly /></div>
	        	</div>
				<div class="form-group-inline">
	        		<label class="col-md-2 control-label">部门<span class="required">*</span></label>
	        		<div class="col-md-4"><input class="form-control" value="${USER.departmentname}" readonly /></div>
        		</div>
			</div>
    		<div class="form-group">
        		<div class="form-group-inline">
	        		<label class="col-md-2 control-label">加班事由<span class="required">*</span></label>
	        		<div class="col-md-10"><textarea class="form-control" name="cause" rows="3" required ></textarea></div>
	        	</div>
			</div>			
    		<div class="form-group" style="background-color: #e7ecf1;margin: 0px;padding: 8px 0px;font-weight: bold;text-align: center;">
    			<div class="col-md-3">开始时间<span class="required">*</span></div>
    			<div class="col-md-3">结束时间<span class="required">*</span></div>
    			<div class="col-md-2">加班时长（小时）<span class="required">*</span></div>
    			<div class="col-md-3">加班性质<span class="required">*</span></div>
    			<div class="col-md-1">操作<span class="required">*</span></div>
    		</div>
    		<div id="list" style="margin-top: 15px;text-align: center;">
				<div class="form-group">
					<div class="col-md-3">
						<div class="input-group date form_datetime form_datetime bs-datetime form-group-inline">
                            <input type="text" size="16" class="form-control" name="begintime" required >
                            <span class="input-group-addon">
                                <button class="btn default" type="button">
                                    <i class="fa fa-calendar"></i>
                                </button>
                            </span>
                        </div>
					</div>
					<div class="col-md-3">
						<div class="input-group date form_datetime form_datetime bs-datetime form-group-inline">
                            <input type="text" size="16" class="form-control" name="endtime" required >
                            <span class="input-group-addon">
                                <button class="btn default" type="button">
                                    <i class="fa fa-calendar"></i>
                                </button>
                            </span>
                        </div>						
					</div>
					<div class="col-md-2"><input class="form-control" name="hours" type="number" min=0 required /></div>
					<div class="col-md-3">
						<select class="form-control" name="type" required>
							<option value="1" >工作日延长加班</option>
							<option value="2" >休息日加班</option>
							<option value="3" >法定假日加班</option>
						</select>
					</div>
					<div class="col-md-1"><span class="btn green fa fa-plus" onclick="addlist()"></span></div>
				</div>
    		</div>
    	</form>
    	<%@ include file="../layout/common-bill.jsp" %>
    </div>
    <!-- END PAGE BASE CONTENT -->
</div>

<div class="form-group" id="template" style="display: none;">
	<div class="col-md-3">
		<div class="input-group date form_datetime form_datetime bs-datetime form-group-inline">
            <input type="text" size="16" class="form-control" name="begintime" required >
            <span class="input-group-addon">
                <button class="btn default" type="button">
                    <i class="fa fa-calendar"></i>
                </button>
            </span>
        </div>
	</div>
	<div class="col-md-3">
		<div class="input-group date form_datetime form_datetime bs-datetime form-group-inline">
            <input type="text" size="16" class="form-control" name="endtime" required >
            <span class="input-group-addon">
                <button class="btn default" type="button">
                    <i class="fa fa-calendar"></i>
                </button>
            </span>
        </div>						
	</div>
	<div class="col-md-2"><input class="form-control" name="hours" type="number" min=0 required /></div>
	<div class="col-md-3">
		<select class="form-control" name="type" required>
			<option value="1" >工作日延长加班</option>
			<option value="2" >休息日加班</option>
			<option value="3" >法定假日加班</option>
		</select>
	</div>
	<div class="col-md-1"><span class="btn red fa fa fa-minus" onclick="removelist(this)"></span></div>
</div>

</tempus:Content>

<tempus:Content contentPlaceHolderId="js">
<script src="../assets/global/plugins/jquery-validation/js/jquery.validate.js" type="text/javascript"></script>
<script src="../assets/global/plugins/jquery-validation/js/additional-methods.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/jquery-validation/js/localization/messages_<%=locale %>.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.<%=locale %>.js" type="text/javascript"></script>
<script>

$(function(){
	$('.form_datetime').datetimepicker({
		autoclose: true,
        format: "yyyy-mm-dd hh:ii",
        fontAwesome: true,
        pickerPosition: "bottom-left",
        language: "<%=locale %>"
	});
	
    initvalidate();
    
    $(document).on("keyup","#list input[name='hours']",function(){
    	if(this.value%0.5!=0){
    		alert("error,只能填写0.5的整数倍！");
    		this.value=1;
    	}
    });
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
        errorPlacement: function(error, element) {
            //error.insertAfter(element.parent());
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

var addlist = function () {
	var add = $("#template").clone();
	add.removeAttr("id");
	add.find(".form_datetime").datetimepicker({
		autoclose: true,
        format: "yyyy-mm-dd hh:ii",
        fontAwesome: true,
        pickerPosition: "bottom-left",
        language: "<%=locale %>"
    });
	$("#list").append(add);
	add.slideDown();
}

var removelist = function(row){
	$(row).closest('.form-group').slideUp(function(){$(this).remove()});
}
</script>
</tempus:Content>

</tempus:ContentPage>