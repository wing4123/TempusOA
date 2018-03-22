<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/master/masterPage.tld" prefix="tempus" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="i18n" %>
<%@ page import="org.springframework.web.servlet.support.RequestContextUtils" language="java"%>
<%@ page import="java.util.UUID" language="java"%>
<%@ page import="org.apache.commons.lang3.time.FastDateFormat" language="java"%>
<%@ page import="java.util.Date" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
	String locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request).toString();
	String today = FastDateFormat.getInstance("yyyy-MM-dd").format(new Date());
%>
    <link href="../assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
    <link href="../assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
    <link href="../assets/global/plugins/jquery-file-upload/css/jquery.fileupload.css" rel="stylesheet" type="text/css" />
    <link href="../assets/global/plugins/jquery-file-upload/css/jquery.fileupload-ui.css" rel="stylesheet" type="text/css" />
    <link href="../assets/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css" />
    <style>
    	.table .btn {margin-right: 0px;}
    	.required{color: red;}
    </style>
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
	        		<label class="col-md-2 control-label">申请人<span class="required">*</span></label>
	        		<div class="col-md-4"><input class="form-control" value="${returnObject.FCREATORNAME}" readonly /></div>
	        	</div>
				<div class="form-group-inline">
	        		<label class="col-md-2 control-label">部门<span class="required">*</span></label>
	        		<div class="col-md-4"><input class="form-control" value="${returnObject.FDEPARTMENTNAME}" readonly /></div>
        		</div>
			</div>
			
			<div class="form-group">
				<div class="form-group-inline">
	        		<label class="col-md-2 control-label">上班时间<span class="required">*</span></label>
	        		<div class="col-md-4">
						<div class="input-group date form_datetime form_datetime bs-datetime form-group-inline">
                            <input type="text" size="16" class="form-control" name="begintime" value="${fn:substring(returnObject.FBEGINTIME, 0, 16)}" required >
                            <span class="input-group-addon">
                                <button class="btn default" type="button">
                                    <i class="fa fa-calendar"></i>
                                </button>
                            </span>
                        </div>
					</div>
        		</div>
        		<div class="form-group-inline">
	        		<label class="col-md-2 control-label">下班时间<span class="required">*</span></label>
	        		<div class="col-md-4">
						<div class="input-group date form_datetime form_datetime bs-datetime form-group-inline">
                            <input type="text" size="16" class="form-control" name="endtime" value="${fn:substring(returnObject.FENDTIME, 0, 16)}" required >
                            <span class="input-group-addon">
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
	        		<label class="col-md-2 control-label">异常类型<span class="required">*</span></label>
	        		<div class="col-md-4">
	        			<select class="form-control" name="type" required>
	        				<option value="1" <c:if test="${returnObject.FTYPE=='1'}" >selected</c:if> >忘记打卡</option>
	        				<option value="2" <c:if test="${returnObject.FTYPE=='2'}" >selected</c:if> >打卡异常</option>
	        			</select>
	        		</div>
        		</div>
        		<div class="form-group-inline">
	        		<label class="col-md-2 control-label">异常说明<span class="required">*</span></label>
	        		<div class="col-md-4"><input class="form-control" name="description" value="${returnObject.FDESCRIPTION}" required /></div>
        		</div>
			</div>
    	</form>
		<%@ include file="../layout/common-bill.jsp" %>
<script src="../assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/jquery-validation/js/additional-methods.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/jquery-validation/js/localization/messages_<%=locale %>.min.js" type="text/javascript"></script>
<script src="../assets/global/scripts/datatable.js" type="text/javascript"></script>
<script src="../assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>
<script src="../js/jquery.qrcode.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.<%=locale %>.js" type="text/javascript"></script>
<script>
$(function(){
	$('.form_datetime').datetimepicker({
		autoclose: true,
        format: "yyyy-mm-dd hh:ii",
        fontAwesome: true,
        pickerPosition: "bottom-left",
        language: "<%=locale %>",
       	todayBtn: "linked",
        todayHighlight: true
	});
	
    initvalidate();
    
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
        	console.log(data);
         	$.post("../AttendanceAnomaly/Save",data,function(){
         		approval(btn);
        	});
        }
    });
}

</script>