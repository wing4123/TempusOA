<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="i18n" %>
<% 
	String basepath = request.getContextPath();
%>

<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
    <h4 class="modal-title"><i18n:message code="master.ChangePassword" /></h4>
</div>
<div class="modal-body">
	<form id="changepassword" class="form-horizontal" role="from">
      		<div class="from-body">
      			<div class="form-group">
      				<label class="col-md-2 control-label"><i18n:message code="master.OldPassword" /><span class="required">*</span></label>
      				<div class="col-md-10">
      					<div class="input-icon right">
	                        <i class="fa"></i>
	      					<input id="oldpassword" name="oldpassword" class="form-control" type="password" />
      					</div>
    				</div>
      			</div>
      			<div class="form-group">
      				<label class="col-md-2 control-label"><i18n:message code="master.NewPassword" /><span class="required">*</span></label>
      				<div class="col-md-10">
      					<div class="input-icon right">
	                        <i class="fa"></i>
	      					<input id="newpassword" name="newpassword" class="form-control" type="password" />
      					</div>
      				</div>
      			</div>
      			<div class="form-group">
      				<label class="col-md-2 control-label"><i18n:message code="master.ConfirmPassword" /><span class="required">*</span></label>
      				<div class="col-md-10">
      					<div class="input-icon right">
	                        <i class="fa"></i>
	      					<input id="confirmpassword" name="confirmpassword" class="form-control" type="password" />
      					</div>
      				</div>
      			</div>
      		</div>
      	</form>
</div>
<div class="modal-footer">
    <button type="button" data-dismiss="modal" class="btn dark btn-outline"><i18n:message code="common.cancel" /></button>
    <button type="button" class="btn green" onclick="changepassword()"><i18n:message code="common.confirm" /></button>
</div>

<script>
$(function(){
	initvalidation();
	
});

function initvalidation(){
	$("#oldpassword").on("keyup",function(){
		if($(this).val().length>0){
			$(this).closest('.form-group').removeClass("has-error").addClass('has-success');
			$(this).parent('.input-icon').children('i').removeClass("fa-warning").addClass("fa-check");
		}else{
			$(this).closest('.form-group').removeClass("has-success").addClass('has-error');
			$(this).parent('.input-icon').children('i').removeClass("fa-check").addClass("fa-warning");
		}
	});
	
	$("#newpassword").on("keyup",function(){
		if($(this).val().length>=6){
			$(this).closest('.form-group').removeClass("has-error").addClass('has-success');
			$(this).parent('.input-icon').children('i').removeClass("fa-warning").addClass("fa-check");
		}else{
			$(this).closest('.form-group').removeClass("has-success").addClass('has-error');
			$(this).parent('.input-icon').children('i').removeClass("fa-check").addClass("fa-warning");
		}
		
		if($(this).val()!=$("#confirmpassword").val() && $("#confirmpassword").val().length>0){
			$("#confirmpassword").closest('.form-group').removeClass("has-success").addClass('has-error');
			$("#confirmpassword").parent('.input-icon').children('i').removeClass("fa-check").addClass("fa-warning");
		}else if($(this).val()==$("#confirmpassword").val() && $("#confirmpassword").val().length>=6){
			$("#confirmpassword").closest('.form-group').removeClass("has-error").addClass('has-success');
			$("#confirmpassword").parent('.input-icon').children('i').removeClass("fa-warning").addClass("fa-check");
		}
	});
	
	$("#confirmpassword").on("keyup",function(){
		if($(this).val()==$("#newpassword").val() && $(this).val().length>=6){
			$(this).closest('.form-group').removeClass("has-error").addClass('has-success');
			$(this).parent('.input-icon').children('i').removeClass("fa-warning").addClass("fa-check");
		}else{
			$(this).closest('.form-group').removeClass("has-success").addClass('has-error');
			$(this).parent('.input-icon').children('i').removeClass("fa-check").addClass("fa-warning");
		}
	});
}

var changepassword = function(){
	if($("#changepassword .has-success").length==3){
		var data = $("#changepassword").serializeJSON();
		$.post("<%=basepath %>/ChangePassword",data,function(result){
			if(result==1){
				$("#modal_changepassword").modal("hide");
				swal("Success","","success");
			}else{
				swal("Error",'<i18n:message code="master.wrongoldpassword" />',"error");
				$("#oldpassword").closest('.form-group').removeClass("has-error").addClass('has-success');
				$("#oldpassword").parent('.input-icon').children('i').removeClass("fa-warning").addClass("fa-check");
			}
		});
	}
}

</script>