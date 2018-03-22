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
    <link href="../assets/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css" />
    
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
	        		<div class="col-md-4"><input class="form-control" value="${USER.name}" readonly /></div>
	        	</div>
				<div class="form-group-inline">
	        		<label class="col-md-2 control-label">部门<span class="required">*</span></label>
	        		<div class="col-md-4"><input class="form-control" value="${USER.departmentname}" readonly /></div>
        		</div>
			</div>
			<div class="form-group" style="text-align: center;background-color: #e7ecf1;margin: 0px 0px 15px 0px;height: 30px;line-height: 30px;">
				<div class="col-md-4">开始时间</div>
				<div class="col-md-4">结束时间</div>
				<div class="col-md-3">公出时长(小时)</div>
				<div class="col-md-1">操作</div>
			</div>
			<div id="list">
				<c:forEach items="${returnObject.entry}" var="row" varStatus="s">
					<div class="form-group">
						<div class="form-group-inline">
			        		<div class="col-md-4">
								<div class="input-group date form_datetime form_datetime bs-datetime form-group-inline">
		                            <input type="text" size="16" class="form-control" name="begintime" value="${fn:substring(row.FBEGINTIME, 0, 16)}" required >
		                            <span class="input-group-addon">
		                                <button class="btn default" type="button">
		                                    <i class="fa fa-calendar"></i>
		                                </button>
		                            </span>
		                        </div>
							</div>
		        		</div>
		        		<div class="form-group-inline">
			        		<div class="col-md-4">
								<div class="input-group date form_datetime form_datetime bs-datetime form-group-inline">
		                            <input type="text" size="16" class="form-control" name="endtime" value="${fn:substring(row.FENDTIME, 0, 16)}" required >
		                            <span class="input-group-addon">
		                                <button class="btn default" type="button">
		                                    <i class="fa fa-calendar"></i>
		                                </button>
		                            </span>
		                        </div>
							</div>
		        		</div>
		        		<div class="form-group-inline">
			        		<div class="col-md-3"><input class="form-control" name="hours" value="${row.FHOURS}" type="number" required ></div>
		        		</div>
		        		<div class="col-md-1" style="text-align: center;">
		        			<c:if test="${s.first}"><span class="btn green fa fa-plus" onclick="addlist()"></span></c:if>
    						<c:if test="${!s.first}"><span class="btn red fa fa fa-minus" onclick="removelist(this)"></span></c:if>
		        		</div>
		        		<div class="form-group-inline" style="margin-top: 50px;">
			        		<label class="col-md-2 control-label">公出事由<span class="required">*</span></label>
		        			<div class="col-md-9"><textarea name="cause" class="form-control" required >${row.FCAUSE}</textarea></div>
		        		</div>
					</div>
				</c:forEach>
			</div>
			<div class="form-group">
				<label class="col-md-2 col-md-offset-7 control-label">合计<span class="required">*</span></label>
        		<div class="col-md-2"><input id="totalhours" name="totalhours" class="form-control" value="${returnObject.FTOTALHOURS}" readonly /></div>
			</div>
		</form>
		<%@ include file="../layout/common-bill.jsp" %>
		
		<div id="template" class="form-group" style="display: none;">
			<div class="form-group-inline">
        		<div class="col-md-4">
					<div class="input-group date form_datetime form_datetime bs-datetime form-group-inline">
                           <input type="text" size="16" class="form-control" name="begintime" required >
                           <span class="input-group-addon">
                               <button class="btn default" type="button">
                                   <i class="fa fa-calendar"></i>
                               </button>
                           </span>
                       </div>
				</div>
       		</div>
       		<div class="form-group-inline">
        		<div class="col-md-4">
					<div class="input-group date form_datetime form_datetime bs-datetime form-group-inline">
                           <input type="text" size="16" class="form-control" name="endtime" required >
                           <span class="input-group-addon">
                               <button class="btn default" type="button">
                                   <i class="fa fa-calendar"></i>
                               </button>
                           </span>
                       </div>
				</div>
       		</div>
       		<div class="form-group-inline">
        		<div class="col-md-3"><input class="form-control" name="hours" type="number" required ></div>
       		</div>
       		<div class="col-md-1" style="text-align: center;"><span class="btn red fa fa fa-minus" onclick="removelist(this)"></span></div>
	    	<div class="form-group-inline" style="margin-top: 50px;">
     			<label class="col-md-2 control-label">公出事由<span class="required">*</span></label>
    			<div class="col-md-9"><textarea name="cause" class="form-control" required >${row.FCAUSE}</textarea></div>
    		</div>
		</div>
		
<script src="../assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/jquery-validation/js/additional-methods.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/jquery-validation/js/localization/messages_<%=locale %>.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.<%=locale %>.js" type="text/javascript"></script>
<script>

$(function(){
	$('#list .form_datetime').datetimepicker({
		autoclose: true,
        format: "yyyy-mm-dd hh:ii",
        fontAwesome: true,
        pickerPosition: "bottom-left",
        language: "<%=locale %>",
       	todayBtn: "linked",
        todayHighlight: true
	});
	
	$(document).on("keyup","#list input[name='hours']",function(){
		sum();
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
         	$.post("../BusinessOut/Save",data,function(){
         		approval(btn);
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
	$(row).closest('.form-group').slideUp(function(){
		$(this).remove();
		sum();
	});
}

var sum = function(){
	var totalhours=0;
	$("#list input[name='hours']").each(function(){
		totalhours = totalhours+Number(this.value);
	});
	$("#totalhours").val(totalhours);
}
</script>