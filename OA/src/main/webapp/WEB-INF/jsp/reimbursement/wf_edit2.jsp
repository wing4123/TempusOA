<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/master/masterPage.tld" prefix="tempus" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="i18n" %>
<%@ page import="org.springframework.web.servlet.support.RequestContextUtils" language="java"%>
<%@ page import="java.util.UUID" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request).toString();
%>
    <link href="../assets/global/plugins/bootstrap-select/css/bootstrap-select.css" rel="stylesheet" type="text/css" />
    <link href="../assets/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css" />
    	<div class="form-horizontal">
    		<div class="form-group">
	            <label class="col-md-2 control-label">标题<span class="required">*</span></label>
	            <div class="col-md-8">
	                <input class="form-control" name="name" value="${returnObject.reimbursement.FNAME}" readonly />
	            </div>
	        </div>   			
   			<div class="form-group">
	            <label class="col-md-2 control-label">报销类型<span class="required">*</span></label>
	            <div class="col-md-10">
	                <div class="mt-radio-inline" style="padding-bottom: 0px;" id="type">
	                    <label class="mt-radio">
	                        <input type="radio" name="type" value="1" <c:if test="${returnObject.reimbursement.FTYPE==1}">checked</c:if> disabled />礼品及业务招待费用/差旅费用审批/交通补助<span></span>
	                    </label>
	                    <label class="mt-radio">
	                        <input type="radio" name="type" value="2" <c:if test="${returnObject.reimbursement.FTYPE==2}">checked</c:if> disabled />零星开支（办公用品、水电费、快递费等未经事前审批的报销事项）<span></span>
	                    </label>
	                </div>
	            </div>
	        </div>
   			<div class="form-group" id="relation" <c:if test="${returnObject.reimbursement.FTYPE=='2'}">style="display: none;"</c:if> >
   				<label class="col-md-2 control-label">关联单据<span class="required">*</span></label>
           		<div class="col-md-10">
                    <div>
                        <div id="list3">
                        	<c:forEach items="${returnObject.list3}" var="row" varStatus="S">
							<div class="row">
								<div class="col-md-5" >
									<label class="control-label">标题<span class="required">*</span></label>
									<input class="form-control" name="relationname" value="${row.FTITLE}" readonly style="cursor: pointer;" onclick="relationbill_show(this)" required />
									<input type="hidden" name="relationprocessid" value="${row.FPROCESSINSTANCEID}" required />
									<span class="caret" style="position: absolute;right: 22px;top: 42px;"></span>
								</div>
								<div class="col-md-3">
									<label class="control-label">金额<span class="required">*</span></label>
									<div><input class="form-control" name="relationamount" value="${row.FAMOUNT}" readonly /></div>
								</div>
								<div class="col-md-3">
									<label class="control-label">币种<span class="required">*</span></label>
									<select name="relationcurrency" class="form-control" disabled  >
										<option value="CNY" <c:if test="${row.FCURRENCY=='CNY'}">selected</c:if> >人民币 CNY</option>
										<option value="HKD" <c:if test="${row.FCURRENCY=='HKD'}">selected</c:if> >港币 HKD</option>
										<option value="USD" <c:if test="${row.FCURRENCY=='USD'}">selected</c:if> >美元 USD</option>
									</select>
								</div>
								<div class="col-md-1" style="padding-top: 32px;">
								    <a href="../MyWorkFlow/ShowPage?processinstanceid=${row.FPROCESSINSTANCEID}" target="_blank" >查看</a>
								</div>
							</div>                        
                        	</c:forEach>
                        </div>
                        <hr/>
                	</div>
                </div>
   			</div>   
   			<div class="form-group">
   				<label class="col-md-2 control-label">费用报销公司<span class="required">*</span></label>
   				<div class="col-md-3">
	           		<select name="company" class="form-control" disabled="disabled">
	        			<option value="">请选择</option>
						<c:forEach items="${returnObject.company}" var="row" varStatus="S">
							<option value="${row.FID}" <c:if test="${returnObject.reimbursement.FCOMPANY==row.FID}">selected</c:if> >${row.FNAME}</option>
						</c:forEach>
	           		</select>
           		</div>
           		<label class="col-md-2 control-label">申请人<span class="required">*</span></label>
           		<div class="col-md-3"><input id="applicant" class="form-control" readonly="readonly" value="${returnObject.reimbursement.FCREATORNAME}"  required /></div>
   			</div>
   			<div class="form-group">
   				<label class="col-md-2 control-label">部门<span class="required">*</span></label>
           		<div class="col-md-3"><input class="form-control" readonly="readonly" value="${returnObject.reimbursement.FDEPARTMENTNAME}"  required /></div>
           		<label class="col-md-2 control-label">币种<span class="required">*</span></label>
           		<div class="col-md-3">
           			<select name="currency" class="form-control" disabled="disabled">
           				<option value="">请选择</option>
						<option value="CNY" <c:if test="${returnObject.reimbursement.FCURRENCY=='CNY'}">selected</c:if> >人民币 CNY</option>
						<option value="HKD" <c:if test="${returnObject.reimbursement.FCURRENCY=='HKD'}">selected</c:if> >港币 HKD</option>
						<option value="USD" <c:if test="${returnObject.reimbursement.FCURRENCY=='USD'}">selected</c:if> >美元 USD</option>
           			</select>
           		</div>
   			</div>
<%--    			<div class="form-group">
   				<label class="col-md-2 control-label">领款人<span class="required">*</span></label>
           		<div class="col-md-3">
					<input id="payee" value='${returnObject.reimbursement.USER_NAME}' class="form-control" readonly="readonly" style="cursor: pointer;" />
				</div>
   			</div> --%>
   			<div class="form-group">
   				<label class="col-md-2 control-label">详情<span class="required">*</span></label>
           		<div class="col-md-10">
                    <div>
                        <div id="list">
                        <c:forEach items="${returnObject.entry}" var="row1" varStatus="S">
                        	<div class="row">
								<div class="col-md-3">
									<label class="control-label">费用类别<span class="required">*</span></label>
									<select class="form-control" disabled>
							     		<option value="">请选择</option>
							     		<c:forEach items="${returnObject.costcategory}" var="row2" varStatus="S">
											<option value="${row2.COSTVALUE}" <c:if test="${row2.COSTVALUE==row1.FCOSTCATEGORY}">selected</c:if> >${row2.COSTNAME}</option>
										</c:forEach>
									</select>
								</div>
								<div class="col-md-6">
									<label class="control-label">内容<span class="required">*</span></label>
									<input name="content" class="form-control" value="${row1.FCONTENT}" disabled/>
								</div>
								<div class="col-md-2">
									<label class="control-label">金额<span class="required">*</span></label>
									<input name="amount" class="form-control currency" value="${row1.FAMOUNT}" disabled />
								</div>
							</div>
						</c:forEach>
                        </div>
                        <hr/>
                        <div class="row">
	            			<div class="col-md-6  col-md-offset-6" style="padding: 0px;">
		            			<label class="control-label col-md-6">合计</label>
		            			<div class="col-md-4"><input id="total" name="total" value="${returnObject.reimbursement.FTOTALAMOUNT}" class="form-control required" readonly="readonly"/></div>
	            			</div>
	            		</div>
                	</div>
                </div>
   			</div>
    	</div>
		<hr/>
		<h3>财务人员填写：</h3>
		<form id="form2" class="form-horizontal" role="form">
			<input type="hidden" name="id" value="${returnObject.reimbursement.FID}" />
			<div class="form-group">
   				<label class="col-md-2 control-label">费用报销公司<span class="required">*</span></label>
   				<div class="col-md-4">
	           		<select id="company2" name="costcompany" class="form-control" onchange="changecompany(this.value)" required >
	        			<option value="">请选择</option>
						<c:forEach items="${returnObject.company2}" var="row" varStatus="S">
							<option value="${row.COMPANYNUM}" >${row.COMPANYNAME}</option>
						</c:forEach>
	           		</select>
           		</div>
           		<label class="col-md-2 control-label">发票日期<span class="required">*</span></label>
				<div class="col-md-4">
					<div class="input-group date date-picker" data-date-format="yyyy-mm-dd" >
				       <input name="invoicedate" class="form-control" value="" required />
				       <span class="input-group-btn" style="vertical-align: top;">
				            <button class="btn default" type="button">
				                <i class="fa fa-calendar"></i>
				            </button>
				        </span>
				    </div>
				</div>
   			</div>
   			
   			<div class="form-group">
   				<label class="col-md-2 control-label">详情<span class="required">*</span></label>
           		<div class="col-md-10">
                    <div>
                        <div id="list2"></div>
                        <div class="row">
                        	<div class="col-md-6"><a href="javascript: addlist();" data-repeater-create="" class="btn btn-info"><i class="fa fa-plus"></i>添加</a></div>
	            			<div class="col-md-6" style="padding: 0px;">
		            			<label class="control-label col-md-6">合计</label>
		            			<div class="col-md-4"><input id="total2" name="total" class="form-control required" readonly="readonly"/></div>
	            			</div>
	            		</div>
                	</div>
                </div>
   			</div>
		</form>
		
<div id="template" class="row" style="display: none;">
	<div class="col-md-11">
		<div class="row">
			<div class="col-md-4">
				<label class="control-label">成本中心<span class="required">*</span></label>
		   		<select id="costcenter" name="costcenter" class="bs-select form-control" data-live-search="true" data-size="8" required></select>
		  	</div>
			<div class="col-md-4">
				<label class="control-label">费用类别<span class="required">*</span></label>
				<select name="costcategory" class="bs-select form-control" data-live-search="true" data-size="8" required>
		     		<option value="">请选择</option>
					<c:forEach items="${returnObject.costcategory2}" var="row" varStatus="s">
						<option value="${row.COSTVALUE}">${row.COSTNAME}</option>
					</c:forEach>
				</select>
			</div>
			<div class="col-md-4">
				<label class="control-label">项目<span class="required">*</span></label>
				<select id="project" name="project" class="bs-select form-control" data-live-search="true" data-size="8" required>
				</select>
			</div>
		</div>
		<div class="row">
			<div class="col-md-10">
				<label class="control-label">内容</label>
				<input name="content" class="form-control" required/>
			</div>
			<div class="col-md-2">
				<label class="control-label">金额<span class="required">*</span></label>
				<input name="amount" class="form-control currency" required />
			</div>
		</div>
	</div>
	<div class="col-md-1" style="padding-top: 58px;">
	    <a onclick="$(this).closest('.row').slideUp(function(){$(this).next().remove();$(this).remove();sum();});" class="btn btn-danger" style="display: block;width: 37px;"><i class="fa fa-close"></i></a>
	</div>
</div>		
		
<%-- <div id="template" class="row" style="display: none;">
	<div class="row">
		<label class="col-md-2 control-label">成本中心<span class="required">*</span></label>
   		<div class="col-md-4">
    		<select id="costcenter" name="costdepartment" class="form-control" required>
 				<option value="">请选择</option>
    		</select>
   		</div>
	</div>
	
	<div class="row">
	
	</div>
	<div class="col-md-3">
		<label class="control-label">费用类别<span class="required">*</span></label>
		<select name="costcategory" class="bs-select form-control" data-live-search="true" data-size="8" required>
     		<option value="">请选择</option>
			<c:forEach items="${returnObject.costcategory}" var="row" varStatus="s">
				<option value="${row.COSTVALUE}">${row.COSTNAME}</option>
			</c:forEach>
		</select>
	</div>
	<div class="col-md-6">
		<label class="control-label">内容</label>
		<input name="content" class="form-control" required/>
	</div>
	<div class="col-md-2">
		<label class="control-label">金额<span class="required">*</span></label>
		<input name="amount" class="form-control currency" required />
	</div>
	<div class="col-md-1">
	    <label class="control-label">&nbsp;</label>
	    <a onclick="$(this).closest('.row').slideUp(function(){$(this).remove();sum();});" class="btn btn-danger" style="display: block;width: 37px;"><i class="fa fa-close"></i></a>
	</div>
</div> --%>
		
<script src="../assets/global/plugins/bootstrap-select/js/bootstrap-select.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/jquery-validation/js/additional-methods.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/jquery-validation/js/localization/messages_<%=locale %>.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/bootstrap-datepicker/locales/bootstrap-datepicker.<%=locale %>.min.js" type="text/javascript"></script>
<script>
$(function(){
	
    $("#form2").on("keyup",".currency",function(e){
    	if(e.which<37 || e.which>40){
    		clearNoNum(this);
        	sum();
    	}
    });
    
    initvalidate();
    
	$('.date-picker').datepicker({
        autoclose: true,
        language: "<%=locale %>",
        todayBtn: "linked",
        todayHighlight: true
    });
	$('.date-picker').datepicker("setDate",new Date());
    
});

var changecompany = function(companynum){
	$("#list2").empty();
	$("#total2").val("")
	$.get("../Reimbursement/getDepartmentList",{"companyid": companynum},function(data){
		var options = "<option value=''>请选择</option><option value='0'>无</option>";
		$.each(data, function(i, item){
			options=options + "<option value='"+item.FID+"'>"+item.FNAME+"</option>";
		});
		$("#costcenter").html(options);
	});
	
	$.get("../Reimbursement/getProjectList",{"companyid": companynum},function(data){
		var options = "<option value='0'>无</option>";
		$.each(data, function(i, item){
			options=options + "<option value='"+item.FID+"'>"+item.FNAME+"</option>";
		});
		$("#project").html(options);
	});
}

var addlist = function () {
	if($("#company2").val()==""){
		swal("Warning!", "请选择公司", "warning");
		return false;
	}
	var add = $("#template").clone();
	add.find('.bs-select').selectpicker();
	add.removeAttr("id");
	$("#list2").append(add);
	$("#list2").append("<hr/>");
	add.slideDown();
}

function clearNoNum(obj){
	obj.value = obj.value.replace(/[^\d.]/g,""); //清除"数字"和"."以外的字符
	obj.value = obj.value.replace(/^\./g,""); //验证第一个字符是数字而不是
	obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的
	obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
	obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'); //只能输入两个小数
}

var sum = function(){
	var total=0;
	$("#form2 .currency").each(function(){
		total=total+Number(this.value);
	});
	$("#total2").val(total.toFixed(2));
}

var initvalidate = function() {
	$('#form2').validate({
        errorElement: 'span',
        errorClass: 'help-block',
        focusInvalid: false,
        errorPlacement: function (error, element) { // render error placement for each input type
            var cont = $(element).parent('.input-group');
            if (cont.size() > 0) {
                cont.after(error);
            } else {
                element.after(error);
            }
        },
        highlight: function (element) { // hightlight error inputs
            $(element).closest('.form-group').addClass('has-error'); // set error class to the control group
        },
        unhighlight: function (element) { // revert the change done by hightlight
            $(element).closest('.form-group').removeClass('has-error'); // set error class to the control group
        },
        submitHandler: function (form) { //表单校验通过并提交表单
        	var data = $(form).serializeJSON();
        	if(Number(data.total)==Number($("#total").val())){
        		App.blockUI({
        	        target: '.page-container',
        	        animate: true
        	    });
        		
        		$.post("../Reimbursement/Save2",data,function(rs){
        			App.unblockUI(".page-container");
        			if(rs==0){
        				swal("Error!", "系统出错，请联系管理员", "error");
        				return false;
        			}
        			approval(btn);
            	});
        	}else{
        		swal("Error!", "总金额必须相等", "error");
        	}
        }
    });
}

</script>