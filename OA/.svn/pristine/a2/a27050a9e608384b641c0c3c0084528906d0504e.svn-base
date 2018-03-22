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
    <title><i18n:message code="费用报销"  /></title>
    <link href="../assets/global/plugins/bootstrap-select/css/bootstrap-select.css" rel="stylesheet" type="text/css" />
</tempus:Content>

<tempus:Content contentPlaceHolderId="main">
<div class="page-content" style="min-height: 1147px;">
    <!-- BEGIN PAGE HEAD-->
    <div class="page-head">
        <!-- BEGIN PAGE TITLE -->
        <div class="page-title">
            <h1>费用报销</h1>
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
	            <label class="col-md-2 control-label">标题<span class="required">*</span></label>
	            <div class="col-md-8">
	                <input class="form-control" name="name" value="${USER.name}的费用报销申请" required />
	            </div>
	        </div>
   			<div class="form-group">
	            <label class="col-md-2 control-label">报销类型<span class="required">*</span></label>
	            <div class="col-md-10">
	                <div class="mt-radio-inline" style="padding-bottom: 0px;" id="type">
	                    <label class="mt-radio">
	                        <input type="radio" name="type" value="1" checked onchange="relation(this.value)" />礼品及业务招待费用/差旅费用审批/交通补助<span></span>
	                    </label>
	                    <label class="mt-radio">
	                        <input type="radio" name="type" value="2" onchange="relation(this.value)" />零星开支（办公用品、水电费、快递费等未经事前审批的报销事项）<span></span>
	                    </label>
	                </div>
	            </div>
	        </div>
<!--    			<div class="form-group" id="relation">
   				<label class="col-md-2 control-label">关联单据<span class="required">*</span></label>
        		<div class="col-md-2">
        			<input class="form-control" id="relationname" name="relationname" value="" readonly style="cursor: pointer;" onclick="$('#modal_relationbill').modal('show')" />
        			<input type="hidden" id="relationprocessid" name="relationprocessid" value="" required />
        			<span class="caret pull-right" style="margin-top: -18px;margin-right: 5px;"></span>
        		</div>
        		<label class="col-md-2 control-label">关联单据金额<span class="required">*</span></label>
        		<div class="col-md-2"><input class="form-control" id="relationamount" name="relationamount" value="" readonly /></div>
           		<label class="col-md-2 control-label">关联单据币种<span class="required">*</span></label>
           		<div class="col-md-2">
           			<select id="relationcurrency" name="relationcurrency" class="form-control" disabled  >
           				<option value=""></option>
						<option value="CNY">人民币 CNY</option>
						<option value="HKD">港币 HKD</option>
						<option value="USD">美元 USD</option>
           			</select>
           		</div>
   			</div> -->
   			<div class="form-group" id="relation">
   				<label class="col-md-2 control-label">关联单据<span class="required">*</span></label>
           		<div class="col-md-10">
                    <div>
                        <div id="list3">
                        	
                        </div>
                        <hr/>
                        <div class="row">
                        	<div class="col-md-6"><a href="javascript: addlist3();" data-repeater-create="" class="btn btn-info"><i class="fa fa-plus"></i>添加</a></div>
	            		</div>
                	</div>
                </div>
   			</div>   			
   			
   			<div class="form-group">
   				<label class="col-md-2 control-label">费用报销公司<span class="required">*</span></label>
   				<div class="col-md-3">
	           		<select name="company" class="form-control" onchange="changecompany(this.value)" required>
	        			<option value="">请选择</option>
						<c:forEach items="${returnObject.company}" var="ROW" varStatus="S">
							<option value="${ROW.FID}">${ROW.FNAME}</option>
						</c:forEach>
	           		</select>
           		</div>
           		<label class="col-md-2 control-label">申请人<span class="required">*</span></label>
           		<div class="col-md-3"><input id="applicant" class="form-control" readonly="readonly" value="${USER.name}"  required /></div>
   			</div>
   			<div class="form-group">
   				<label class="col-md-2 control-label">部门</label>
           		<div class="col-md-3"><input class="form-control" readonly="readonly" value="${USER.departmentname}"  required /></div>
           		<label class="col-md-2 control-label">币种<span class="required">*</span></label>
           		<div class="col-md-3">
           			<select name="currency" class="form-control" required>
           				<option value="">请选择</option>
						<option value="CNY">人民币 CNY</option>
						<option value="HKD">港币 HKD</option>
						<option value="USD">美元 USD</option>
           			</select>
           		</div>
   			</div>
<!--    			<div class="form-group">
   				<label class="col-md-2 control-label">领款人<span class="required">*</span></label>
           		<div class="col-md-3">
					<input id="payee" class="form-control" readonly="readonly" style="cursor: pointer;" onclick="showusermodal(this)" required />
					<input name="payee" type="hidden">
				</div>
   			</div> -->
   			<div class="form-group">
   				<label class="col-md-2 control-label">详情<span class="required">*</span></label>
           		<div class="col-md-10">
                    <div>
                        <div id="list">
                        	
                        </div>
                        <hr/>
                        <div class="row">
                        	<div class="col-md-6"><a href="javascript: addlist();" data-repeater-create="" class="btn btn-info"><i class="fa fa-plus"></i>添加</a></div>
	            			<div class="col-md-6" style="padding: 0px;">
		            			<label class="control-label col-md-6">合计</label>
		            			<div class="col-md-4"><input id="total" name="total" class="form-control required" readonly="readonly"/></div>
	            			</div>
	            		</div>
                	</div>
                </div>
   			</div>
    	</form>
    	<%@ include file="../layout/common-bill.jsp" %>
    </div>
    <!-- END PAGE BASE CONTENT -->
</div>

<div id="modal_user" class="modal fade bs-modal-lg" tabindex="-1" data-backdrop="static" >
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
		    <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
		        <h4 class="modal-title">选择领款人</h4>
		    </div>
		    <div class="modal-body" style="padding: 20px;max-height: 700px;overflow-y: auto;">
		    	<table id="payeelist" class="table table-striped table-bordered table-hover table-checkable order-column" >
		            <thead>
		                <tr>
		                    <th>工号</th>
		                    <th>姓名</th>
		                    <th>部门</th>
		                </tr>
		            </thead>
		            <tbody></tbody>
	            </table>
		    </div>
		    <div class="modal-footer">
		        <button type="button" data-dismiss="modal" class="btn dark btn-outline"><i18n:message code="common.cancel" /></button>
		        <button type="button" class="btn green" onclick="selectuser()"><i18n:message code="确定" /></button>
		    </div>
		</div>
	</div>
</div>

<div id="template" class="row" style="display: none;">
	<div class="col-md-3">
		<label class="control-label">费用类别<span class="required">*</span></label>
		<select name="costcategory" class="bs-select form-control" data-live-search="true" data-size="8" required >
     		<option value="">请选择</option>
			<c:forEach items="${returnObject.costcategory}" var="row" varStatus="s">
				<option value="${row.COSTVALUE}">${row.COSTNAME}</option>
			</c:forEach>
		</select>
	</div>
	<div class="col-md-6">
		<label class="control-label">内容<span class="required">*</span></label>
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
</div>

<div id="template2" class="row" style="display: none;">
	<div class="col-md-5" >
		<label class="control-label">标题<span class="required">*</span></label>
		<input class="form-control" name="relationname" value="" readonly style="cursor: pointer;" onclick="relationbill_show(this)" required />
		<input type="hidden" name="relationprocessid" value="" required />
		<span class="caret" style="position: absolute;right: 22px;top: 42px;"></span>
	</div>
	<div class="col-md-3">
		<label class="control-label">金额<span class="required">*</span></label>
		<div><input class="form-control" name="relationamount" value="" readonly /></div>
	</div>
	<div class="col-md-3">
		<label class="control-label">币种<span class="required">*</span></label>
		<select name="relationcurrency" class="form-control" disabled  >
			<option value=""></option>
			<option value="CNY">人民币 CNY</option>
			<option value="HKD">港币 HKD</option>
			<option value="USD">美元 USD</option>
		</select>
	</div>
	<div class="col-md-1">
	    <label class="control-label">&nbsp;</label>
	    <a onclick="$(this).closest('.row').slideUp(function(){$(this).remove();});" class="btn btn-danger" style="display: block;width: 37px;"><i class="fa fa-close"></i></a>
	</div>
</div>

<div id="modal_relationbill" class="modal fade bs-modal-lg" tabindex="-1" data-backdrop="static" >
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
		    <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
		        <h4 class="modal-title">选择关联单据</h4>
		    </div>
		    <div class="modal-body" style="padding: 20px;max-height: 700px;overflow-y: auto;">
		    	<table id="datatable_relationbill" class="table table-striped table-bordered table-checkable order-column" >
		            <thead>
		                <tr>
		                    <th>流程ID</th>
		                    <th>单据类型</th>
		                    <th>单据名称</th>
		                    <th>金额</th>
		                    <th>币种</th>
		                </tr>
		            </thead>
		            <tbody></tbody>
	            </table>
		    </div>
		    <div class="modal-footer">
		        <button type="button" data-dismiss="modal" class="btn dark btn-outline"><i18n:message code="common.cancel" /></button>
		        <button type="button" class="btn green" onclick="selectrelationbill()"><i18n:message code="common.confirm" /></button>
		    </div>
		</div>
	</div>
</div>

</tempus:Content>

<tempus:Content contentPlaceHolderId="js">
<script src="../assets/global/plugins/jquery-repeater/jquery.repeater.js" type="text/javascript"></script>
<script src="../assets/global/plugins/jquery-validation/js/jquery.validate.js" type="text/javascript"></script>
<script src="../assets/global/plugins/jquery-validation/js/additional-methods.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/jquery-validation/js/localization/messages_<%=locale %>.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/bootstrap-select/js/bootstrap-select.min.js" type="text/javascript"></script>
<script>

$(function(){
	addlist();
    initvalidate();
    //initpayee();
    initalrelationbill();
    
    $("#form_bill").on("keyup",".currency",function(e){
    	if(e.which<37 || e.which>40){
    		clearNoNum(this);
        	sum();
    	}
    });
    
    template = $($("#list").html()).clone();
    
});

var addlist = function () {
	var add = $("#template").clone();
	add.find('.bs-select').selectpicker();
	add.removeAttr("id");
	$("#list").append(add);
	add.slideDown();
}

var addlist3 = function () {
	var add = $("#template2").clone();
	add.removeAttr("id");
	$("#list3").append(add);
	add.slideDown();
}

var initvalidate = function() {
	$('#form_bill').validate({
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
        invalidHandler: function(event, validator) {
			alert("有必填项未填写，请检查！");
        },
        submitHandler: function (form) { //表单校验通过并提交表单
        	if($("#list").children().length==0){
        		swal("Error!", "请添加费用类别", "error");
        	}else if($('input[name="type"]:checked').val()=="1" && $("#list3").children().length==0){
        		swal("Error!", "请添加关联单据", "error");
        	}
        	else{
            	App.blockUI({
                    target: '.page-content',
                    animate: true
                });
            	$("#list3 select[name='relationcurrency']").removeAttr("disabled");
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

var sum = function(){
	var total=0;
	$(".currency").each(function(){
		total=total+Number(this.value);
	});
	$("#total").val(total.toFixed(2));
}

var showusermodal = function(ip){
	$("#modal_user").modal("show");
}

var selectuser = function(){
	var data = $("#payeelist").DataTable().rows('.selected').data();
	if(data.length==0){
		swal("Error!", "没有选择人员", "error");
	}else{
		$("#payee").val(data[0].USER_NAME);
		$("#payee").next().val(data[0].USER_ID);
		$("#modal_user").modal("hide");
	}
}

var changecompany = function(companyid){
	if(companyid!=""){
		$.get("./getDepartmentList",{"companyid": companyid},function(data){
			var html="<option value=''>请选择</option>";
			for(var i=0;i<data.length;i++){
				html+="<option value='"+data[i].FID+"'>"+data[i].FNAME+"</option>";
			}
			$("#costcenter").html(html);
		});
	}else{
		$("#costcenter").html("<option value=''>请选择</option>");
	}
}

var initpayee = function(){
	var datatable = $("#payeelist");

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
	        url: '../UserManage/getAllUserList'
		},
	    columns: [
	    	{data:"USER_CODE"},
	    	{data:"USER_NAME"},
	    	{data:"DEPARTMENTNAME"}
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
	    if ($(this).hasClass('selected') ) {
	       	$(this).removeClass('selected');
	    } else {
	    	datatable.find('tr.selected').removeClass('selected');
			$(this).addClass('selected');
	    }
	});
}

var relation = function(v){
	$("#relation").slideToggle();
	if(v=="1"){
		$("#list3 input[name='relationname']").each(function(){
			$(this).attr("required","required");
		});
	}else if(v=="2"){
		$("#list3 input[name='relationname']").each(function(){
			$(this).removeAttr("required");
		});
	}
}

var initalrelationbill = function(){
	var datatable = $("#datatable_relationbill");

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
	        url: './getRelationBill'
		},
	    columns: [
	    	{data:"FPROCESSINSTANCEID"},
	    	{data:"BILLTYPE"},
	    	{data:"FNAME"},
	    	{data:"FAMOUNT"},
	    	{data:"FCURRENCY"}
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
	    if ($(this).hasClass('selected') ) {
	       	$(this).removeClass('selected');
	    } else {
	    	datatable.find('tr.selected').removeClass('selected');
			$(this).addClass('selected');
	    }
	});
}

var selectrelationbill = function(){
	var data = $("#datatable_relationbill").DataTable().rows('.selected').data();
	var s=true;
	if(data.length==0){
		swal("Error!", "没有选择选择关联单据", "error");
	}else{
		console.log($("#list3 input[name='relationprocessid']").length);
		$("#list3 input[name='relationprocessid']").each(function(){
			if(this.value==data[0].FPROCESSINSTANCEID){
				swal("Error!", "不能重复关联单据", "error");
				s=false;
			}
		});
		if(s){
			relationitem.find("[name='relationname']").val(data[0].FPROCESSINSTANCEID+"-"+data[0].FNAME);
			relationitem.find("[name='relationprocessid']").val(data[0].FPROCESSINSTANCEID);
			relationitem.find("[name='relationamount']").val(data[0].FAMOUNT);
			relationitem.find("[name='relationcurrency']").val(data[0].FCURRENCY);
			$("#modal_relationbill").modal("hide");
		}
	}
}

var relationitem;

var relationbill_show = function(item){
	relationitem=$(item).parent().parent();
	$('#modal_relationbill').modal('show');
}

</script>
</tempus:Content>

</tempus:ContentPage>