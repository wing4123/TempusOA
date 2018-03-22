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
    <style>
    	.table .btn {margin-right: 0px;}
    	.required{color: red;}
    </style>
		<form id="form_bill" class="form-horizontal" role="form">
    		<input type="hidden" id="status" name="status" value="" />
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
	        		<div class="col-md-4"><input class="form-control" value="${returnObject.USER_NAME}" readonly /></div>
	        	</div>
				<div class="form-group-inline">
	        		<label class="col-md-2 control-label">部门<span class="required">*</span></label>
	        		<div class="col-md-4"><input class="form-control" value="${returnObject.DEPARTMENTNAME}" readonly /></div>
        		</div>
			</div>
     		<table class="table table-striped table-hover table-responsive">
    			<thead style="background-color: #e7ecf1;">
    				<tr>
    					<th style="width: 145px;">假期类型<span class="required">*</span></th>
    					<th style="width: 200px;">开始时间<span class="required">*</span></th>
    					<th style="width: 200px;">结束时间<span class="required">*</span></th>
    					<th style="width: 100px;">请假天数<span class="required">*</span></th>
    					<th>备注</th>
    					<th style="width: 50px;">操作</th>
    				</tr>
    			</thead>
    			<tbody id="list">
    				<c:forEach items="${returnObject.entry}" var="row" varStatus="s">
	    				<tr>
	    					<td class="form-group-inline">
	    						<select class="form-control" name="type" required >
	    							<option value="1" <c:if test="${row.FTYPE==1}">selected</c:if> >年假</option>
	    							<option value="2" <c:if test="${row.FTYPE==2}">selected</c:if> >调休</option>
	    							<option value="3" <c:if test="${row.FTYPE==3}">selected</c:if> >婚假</option>
	    							<option value="4" <c:if test="${row.FTYPE==4}">selected</c:if> >产假</option>
	    							<option value="5" <c:if test="${row.FTYPE==5}">selected</c:if> >陪产假</option>
	    							<option value="6" <c:if test="${row.FTYPE==6}">selected</c:if> >产检假</option>
	    							<option value="7" <c:if test="${row.FTYPE==7}">selected</c:if> >丧假</option>
	    							<option value="-1" <c:if test="${row.FTYPE==-1}">selected</c:if> >事假（扣薪）</option>
	    							<option value="-2" <c:if test="${row.FTYPE==-2}">selected</c:if> >病假（扣薪）</option>
	    						</select>
	    					</td>
	    					<td>
		                        <div class="input-group date form_datetime form_datetime bs-datetime form-group-inline">
		                            <input type="text" size="16" class="form-control" name="begintime" value="${row.FBEGINTIME}" required >
		                            <span class="input-group-addon">
		                                <button class="btn default" type="button">
		                                    <i class="fa fa-calendar"></i>
		                                </button>
		                            </span>
		                        </div>
	    					</td>
	    					<td>
		                        <div class="input-group date form_datetime form_datetime bs-datetime form-group-inline">
		                            <input type="text" size="16" class="form-control" name="endtime" value="${row.FENDTIME}" required >
		                            <span class="input-group-addon">
		                                <button class="btn default date-set" type="button">
		                                    <i class="fa fa-calendar"></i>
		                                </button>
		                            </span>
		                        </div>
	    					</td>
	    					<td class="form-group-inline" ><input class="form-control" type="number" name="days" min="0" value="${row.FDAYS}" required /></td>
	    					<td><input class="form-control" name="remark" value="${row.FREMARK}" /></td>
	    					<td>
	    						<c:if test="${s.first}"><span class="btn green fa fa-plus" onclick="addlist()"></span></c:if>
	    						<c:if test="${!s.first}"><span class="btn red fa fa fa-minus" onclick="removelist(this)"></span></c:if>
	    					</td>
	    				</tr>    					
    				</c:forEach>
    			</tbody>
    		</table>
    		<div class="form-group">
        		<div class="col-md-4">全薪假：<input id="quanxin" name="quanxin" value="${returnObject.FQUANXIN}" class="form-control" style="width: 150px;display: inline-block;" readonly />天</div>
        		<div class="col-md-4">扣薪假：<input id="kouxin" name="kouxin" value="${returnObject.FKOUXIN}" class="form-control" style="width: 150px;display: inline-block;" readonly />天</div>
        		<div class="col-md-4">合计，共请假：<input id="heji" name="heji" value="${returnObject.FHEJI}" class="form-control" style="width: 150px;display: inline-block;" readonly />天</div>
			</div>
    		<div class="form-group">
        		<div class="form-group-inline">
	        		<label class="col-md-2 control-label">请假事由<span class="required">*</span></label>
	        		<div class="col-md-10"><textarea class="form-control" name="cause" rows="3" required >${returnObject.FCAUSE}</textarea></div>
	        	</div>
			</div>
			<div class="form-group">
        		<div class="form-group-inline">
	        		<label class="col-md-2 control-label">工作安排<span class="required">*</span></label>
	        		<div class="col-md-10"><textarea class="form-control" name="workingarrangements" rows="3" required >${returnObject.FWORKINGARRANGEMENTS}</textarea></div>
	        	</div>
			</div>
    		<div class="form-group">
        		<div class="form-group-inline">
	        		<label class="col-md-2 control-label">交接人<span class="required">*</span></label>
	        		<div class="col-md-4">
	        			<div class="input-group" onclick="$('#modal_handoverperson').modal('show')" >
		                    <input id="handoverpersonname" class="form-control" value="${returnObject.FHANDOVERPERSONNAME}" readonly />
		                    <input type="hidden" id="handoverpersonid" name="handoverperson" value="${returnObject.FHANDOVERPERSONID}" class="form-control" required />
		                    <span class="input-group-btn">
		                        <button class="btn default" type="button">
		                            <i class="fa fa-user"></i>
		                        </button>
		                    </span>
		                </div>
	        		</div>
        		</div>
			</div>
    	</form>
		<%@ include file="../layout/common-bill.jsp" %>
		
		<table class="table" id="template" style="display: none;">
			<tr>
				<td class="form-group-inline">
					<select class="form-control" name="type" required >
						<option value="" >请选择</option>
						<option value="1" >年假</option>
						<option value="2" >调休</option>
						<option value="3" >婚假</option>
						<option value="4" >产假</option>
						<option value="5" >陪产假</option>
						<option value="6" >产检假</option>
						<option value="7" >丧假</option>
						<option value="-1" >事假（扣薪）</option>
						<option value="-2" >病假（扣薪）</option>
					</select>
				</td>
				<td>
				    <div class="input-group date form_datetime form_datetime bs-datetime form-group-inline">
				        <input type="text" size="16" class="form-control" name="begintime" required >
				        <span class="input-group-addon">
				            <button class="btn default" type="button">
				                <i class="fa fa-calendar"></i>
				            </button>
				        </span>
				    </div>
				</td>
				<td>
				    <div class="input-group date form_datetime form_datetime bs-datetime form-group-inline">
				        <input type="text" size="16" class="form-control" name="endtime" required >
				        <span class="input-group-addon">
				            <button class="btn default date-set" type="button">
				                <i class="fa fa-calendar"></i>
				            </button>
				        </span>
				    </div>
				</td>
				<td class="form-group-inline" ><input class="form-control" type="number" name="days" min="0" required /></td>
				<td><input class="form-control" name="remark" /></td>
				<td><span class="btn red fa fa fa-minus" onclick="removelist(this)"></span></td>
				</tr>
			</table>
			
			<div id="modal_handoverperson" class="modal fade bs-modal-lg" tabindex="-1" data-backdrop="static" >
				<div class="modal-dialog modal-lg">
					<div class="modal-content">
					    <div class="modal-header">
					        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
					        <h4 class="modal-title">工作交接人</h4>
					    </div>
					    <div class="modal-body" style="padding: 20px;max-height: 700px;overflow-y: auto;">
					    	<table id="datatable_handoverperson" class="table table-striped table-bordered table-checkable order-column" >
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
        language: "<%=locale %>"
	});
	
    initvalidate();
    initdatatable_handoverperson();
    
    $(document).on("keyup","#list input[name='days']",function(){
    	if(this.value%0.5!=0){
    		alert("error,只能填写0.5的整数倍！");
    		this.value=1;
    	}
    	sum();
    });
    $(document).on("change","#list input[name='days']",function(){
    	if(this.value%0.5!=0){
    		alert("error,只能填写0.5的整数倍！");
    		this.value=1;
    	}
    	sum();
    });
    $(document).on("change","#list select",function(){
    	sum();
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
         	$.post("../Leave/Save",data,function(){
         		approval(btn);
        	});
        }
    });
}

var addlist = function () {
	var add = $("#template tbody").children().clone();
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
	$(row).closest('tr').remove();
	sum();
}

var sum = function(){
	var quanxin = 0;
	var kouxin = 0;
	$("#list tr").each(function(){
		var days = Number($(this).find("input[name='days']").val());
		if($(this).find("select").val()>0){
			quanxin = quanxin+days;
		}else if($(this).find("select").val()<0){
			kouxin = kouxin+days
		}
	});
	$("#quanxin").val(quanxin);
	$("#kouxin").val(kouxin);
	$("#heji").val(quanxin+kouxin);
}

var initdatatable_handoverperson = function(){
	var datatable = $("#datatable_handoverperson");

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

var selectuser = function(){
	var data = $("#datatable_handoverperson").DataTable().rows('.selected').data();
	if(data.length==0){
		swal("Error!", "没有选择人员", "error");
	}else{
		$("#handoverpersonname").val(data[0].USER_NAME);
		$("#handoverpersonid").val(data[0].USER_ID);
		$("#modal_handoverperson").modal("hide");
	}
}
 
</script>