<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/master/masterPage.tld" prefix="tempus" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="i18n" %>
<%@ page import="org.springframework.web.servlet.support.RequestContextUtils" language="java"%>
<%@ page import="java.util.UUID" language="java"%>
<%@ page import="org.apache.commons.lang3.time.FastDateFormat" language="java"%>
<%@ page import="java.util.Date" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request).toString();
	String today = FastDateFormat.getInstance("yyyy-MM-dd").format(new Date());
%>
<tempus:ContentPage materPageId="masterstep">

<tempus:Content contentPlaceHolderId="title">
    <title><i18n:message code="出差申请"  /></title>
    <link href="../assets/global/plugins/bootstrap-select/css/bootstrap-select.css" rel="stylesheet" type="text/css" />
    <link href="../assets/global/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css" />
    <style>
    	.required{
   		    color: #e02222;
		    font-size: 12px;
		    padding-left: 2px;
    	}
    </style>
</tempus:Content>

<tempus:Content contentPlaceHolderId="main">
<div class="page-content" style="min-height: 1147px;">
    <!-- BEGIN PAGE HEAD-->
    <div class="page-head">
        <!-- BEGIN PAGE TITLE -->
        <div class="page-title">
            <h1>出差申请</h1>
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
           		<div class="col-md-10"><input class="form-control" name="name" value="${USER.name}的出差申请申请" required /></div>
   			</div>
    		<div class="row">
    			<div class="col-md-4">
    				<div class="form-group">
		           		<label class="col-md-6 control-label">申请人<span class="required">*</span></label>
		           		<div class="col-md-6"><input class="form-control" value="${USER.name}" readonly /></div>
		   			</div>
    			</div>
    			<div class="col-md-4">
    				<div class="form-group">
		           		<label class="col-md-6 control-label">部门<span class="required">*</span></label>
		           		<div class="col-md-6"><input class="form-control" value="${USER.departmentname}" readonly /></div>
		   			</div>
    			</div>
    			<div class="col-md-4">
    				<div class="form-group">
		           		<label class="col-md-6 control-label">申请日期<span class="required">*</span></label>
		           		<div class="col-md-6"><input class="form-control" value="<%=today %>" readonly /></div>
		   			</div>
    			</div>
    		</div>
    		<div class="row">
    			<div class="col-md-4">
    				<div class="form-group">
		           		<label class="col-md-6 control-label">开始日期<span class="required">*</span></label>
		           		<div class="col-md-6">
		           			<div class="input-group date date-picker" data-date-format="yyyy-mm-dd" >
			                    <input name="begindate" class="form-control" required />
			                    <span class="input-group-btn" style="vertical-align: top;">
			                        <button class="btn default" type="button">
			                            <i class="fa fa-calendar"></i>
			                        </button>
			                    </span>
			                </div>
		           		</div>
		   			</div>
    			</div>
    			<div class="col-md-4">
    				<div class="form-group">
		           		<label class="col-md-6 control-label">结束日期<span class="required">*</span></label>
		           		<div class="col-md-6">
		           			<div class="input-group date date-picker" data-date-format="yyyy-mm-dd" >
			                    <input name="enddate" class="form-control" required />
			                    <span class="input-group-btn" style="vertical-align: top;">
			                        <button class="btn default" type="button">
			                            <i class="fa fa-calendar"></i>
			                        </button>
			                    </span>
			                </div>
		           		</div>
		   			</div>
    			</div>
    			<div class="col-md-4">
    				<div class="form-group">
		           		<label class="col-md-6 control-label">出差天数<span class="required">*</span></label>
		           		<div class="col-md-6"><input name="days" class="form-control" type="number" min=0 required /></div>
		   			</div>
    			</div>
    		</div>
    		<div class="row">
    			<div class="col-md-4">
    				<div class="form-group">
		           		<label class="col-md-6 control-label">出差地点<span class="required">*</span></label>
		           		<div class="col-md-6"><input class="form-control" name="location" value="" required /></div>
		   			</div>
    			</div>
    			<div class="col-md-4">
    				<div class="form-group">
		           		<label class="col-md-6 control-label">地域<span class="required">*</span></label>
		           		<div class="col-md-6">
		           			<select class="form-control" name="area" required >
			                	<option value="1">内地</option>
			                	<option value="2">港澳台</option>
			                	<option value="3">国外</option>
			                </select>
		           		</div>
		   			</div>
    			</div>
    			<div class="col-md-4">
    				<div class="form-group">
		           		<label class="col-md-6 control-label">项目类型<span class="required">*</span></label>
		           		<div class="col-md-6">
							<select class="form-control" name="projecttype" required >
			                	<option value="1">一般项目</option>
			                	<option value="2">投资整合项目</option>
			                	<option value="3">IT项目</option>
			                </select>
						</div>
		   			</div>
    			</div>
    		</div>
    		<div class="row">
    			<div class="col-md-4">
    				<div class="form-group">
		           		<label class="col-md-6 control-label">项目负责人</label>
		           		<div class="col-md-6">
	                        <div class="input-icon right">
		                        <i class="fa fa-user font-blue"></i>
		                        <input id="projectleadername" class="form-control" value="" readonly="readonly" onclick="showmodal_projectleader()" />
		                        <input id="projectleaderid" name="projectleader" type="hidden"/>
		                    </div>
	                    </div>
		   			</div>
    			</div>
    		</div>
    		<div class="row">
    			<div class="col-md-12">
    				<div class="form-group">
		           		<label class="col-md-2 control-label">出差事由<span class="required">*</span></label>
		           		<div class="col-md-10"><textarea class="form-control" name="cause" required ></textarea></div>
		   			</div>
    			</div>
    		</div>
    		<div style="padding-left: 10p;"><span style="display: inline-block;width: 5px;height:20px;background-color: green;vertical-align: middle;"></span>交通</div>
     		<table class="table table-striped table-hover table-responsive">
    			<thead style="background-color: #e7ecf1;">
    				<tr>
    					<th>交通工具<span class="required">*</span></th>
    					<th>日期<span class="required">*</span></th>
    					<th>出发城市<span class="required">*</span></th>
    					<th>抵达城市<span class="required">*</span></th>
    					<th>航班/车次/公里数</th>
    					<th>费用<span class="required">*</span></th>
    					<th>其他说明</th>
    					<th style="width: 50px;"><span class="fa fa-plus-circle" style="font-size: 20px;color: green;cursor: pointer;" onclick="addlist()"></span></th>
    				</tr>
    			</thead>
    			<tbody id="list">
    			
    			</tbody>
    		</table>
    		<div><span style="display: inline-block;width: 5px;height:20px;background-color: green;vertical-align: middle;"></span>业务招待</div>
    		<div class="form-group" style="background-color: #e7ecf1;margin: 0px;padding: 8px;font-weight: bold;position: relative;">
    			<div class="col-md-2">客户<span class="required">*</span></div>
    			<div class="col-md-2">地点<span class="required">*</span></div>
    			<div class="col-md-2">事由<span class="required">*</span></div>
    			<div class="col-md-2">金额<span class="required">*</span></div>
    			<div class="col-md-2">参加人数<span class="required">*</span></div>
    			<div class="col-md-2">公司陪同人员<span class="required">*</span></div>
    			<span class="fa fa-plus-circle" style="font-size: 20px;color: green;cursor: pointer;position: absolute;top: 10px;right: 0px;" onclick="addlist2()"></span>
    		</div>
    		<div id="list2">
	    		
    		</div>
    		<div style="margin-top: 20px;"><span style="display: inline-block;width: 5px;height:20px;background-color: green;vertical-align: middle;"></span>住宿和餐饮</div>
    		<table class="table table-responsive" >
    			<thead style="background-color: #e7ecf1;" >
    				<tr>
    					<th>项目</th>
    					<th>项目单价</th>
    					<th>人天数</th>
    					<th>金额</th>
    					<th>备注</th>
    				</tr>
    			</thead>
    			<tbody>
    				<tr>
    					<td>酒店/住宿</td>
    					<td><input class="form-control" min=0 type="number" name="hotelprice" /></td>
    					<td><input class="form-control" min=0 type="number" name="hoteldays" /></td>
    					<td><input class="form-control" min=0 type="number" name="hotelamount" readonly /></td>
    					<td><input class="form-control" name="hotelremark" /></td>
    				</tr>
    				<tr>
    					<td>餐饮</td>
    					<td><input class="form-control" min=0 type="number" name="foodprice" /></td>
    					<td><input class="form-control" min=0 type="number" name="fooddays" /></td>
    					<td><input class="form-control" min=0 type="number" name="foodamount" readonly /></td>
    					<td><input class="form-control" name="foodremark" /></td>
    				</tr>
    				<tr>
    					<td>其他</td>
    					<td colspan="2" ></td>
    					<td><input class="form-control" min=0 type="number" name="otheramount" /></td>
    					<td><input class="form-control" name="otherremark" /></td>
    				</tr>
    			</tbody>
    		</table>
    		<hr/>
    		<div class="form-group" style="margin-bottom: 0px;">
    			<label class="col-md-2 control-label">总金额</label>
    			<div class="col-md-2"><input class="form-control" name="totalamount" type="number" min=0 readonly /></div>
    			<label class="col-md-2 control-label">币种</label>
    			<div class="col-md-2">
    				<select class="form-control" name="currency" required >
	                	<option value="CNY">人民币</option>
	                	<option value="HKD">港币</option>
	                	<option value="USD">美元</option>
	                </select>
    			</div>
    		</div>
    	</form>
    	<%@ include file="../layout/common-bill.jsp" %>
    </div>
    <!-- END PAGE BASE CONTENT -->
</div>

<table class="table" id="template" style="display: none;">
	<tr>
		<td>
			<select class="form-control" name="transport" required  style="min-width: 80px;">
	          	<option value="1">飞机</option>
	          	<option value="2">火车</option>
	          	<option value="3">市内交通</option>
	          	<option value="4">私车公用</option>
          	</select>
		</td>
		<td>
			<div class="input-group date date-picker" data-date-format="yyyy-mm-dd" >
		        <input name="departuredate" class="form-control" required />
		        <span class="input-group-btn" style="vertical-align: top;">
		            <button class="btn default" type="button">
		                <i class="fa fa-calendar"></i>
		            </button>
		        </span>
		    </div>
		</td>
		<td><input class="form-control" name="departurecity" required /></td>
		<td><input class="form-control" name="destinationcity" required /></td>
		<td><input class="form-control" name="trips" /></td>
		<td><input class="form-control" name="cost" type="number" min=0 required /></td>
		<td><input class="form-control" name="remark" /></td>
		<td style="width: 50px;"><span class="fa fa-minus-circle" style="font-size: 20px;color: red;cursor: pointer;" onclick="removelist(this)"></span></td>
	</tr>
</table>

<div id="template2" class="template2" style="display: none;">
	<div class="form-group" style="margin: 10px 0px 10px 0px;padding: 0px 8px 0px 8px;position: relative;">
		<div class="col-md-2"><input class="form-control" name="businessclient" required /></div>
		<div class="col-md-2"><input class="form-control" name="businesslocation" required /></div>
		<div class="col-md-2"><input class="form-control" name="businesscause" required /></div>
		<div class="col-md-2"><input class="form-control" name="businessamount" type="number" min=0 required /></div>
		<div class="col-md-2"><input class="form-control" name="businessjoinnumber" type="number" required /></div>
		<div class="col-md-2"><input class="form-control" name="businessstaff" required /></div>
		<span class="fa fa-minus-circle" style="font-size: 20px;color: red;cursor: pointer;position: absolute;top: 10px;right: 0px;" onclick="removelist2(this)"></span>
	</div>
	<hr style="margin: 0px;" />
</div>

<div id="modal_projectleader" class="modal fade bs-modal-lg" tabindex="-1" data-backdrop="static" >
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
		    <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
		        <h4 class="modal-title">项目负责人</h4>
		    </div>
		    <div class="modal-body" style="padding: 20px;max-height: 700px;overflow-y: auto;">
		    	<table id="datatable_projectleader" class="table table-striped table-bordered table-checkable order-column" >
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
</tempus:Content>
 
<tempus:Content contentPlaceHolderId="js">
<script src="../assets/global/plugins/jquery-validation/js/jquery.validate.js" type="text/javascript"></script>
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
	initdatepicker();
    initvalidate();
    
    initdatatable_projectleader();
    
    $(document).on("keyup","input[type='number']",function(){
    	this.value = this.value.replace("^[0-9]+(.[0-9]{1,2})?$");
    });
    
    $(document).on("keyup","#list input[name='cost'],input[name='hotelprice'],input[name='hoteldays'],input[name='foodprice'],input[name='fooddays'],input[name='businessamount'],input[name='otheramount']",function(){
    	$("input[name='hotelamount']").val(Number($("input[name='hotelprice']").val())*Number($("input[name='hoteldays']").val()));
    	$("input[name='foodamount']").val(Number($("input[name='foodprice']").val())*Number($("input[name='fooddays']").val()));
    	sum();
    });
    $(document).on("change","#list input[name='cost'],input[name='hotelprice'],input[name='hoteldays'],input[name='foodprice'],input[name='fooddays'],input[name='businessamount'],input[name='otheramount']",function(){
    	$("input[name='hotelamount']").val(Number($("input[name='hotelprice']").val())*Number($("input[name='hoteldays']").val()));
    	$("input[name='foodamount']").val(Number($("input[name='foodprice']").val())*Number($("input[name='fooddays']").val()));
    	sum();
    });
    
});

var initdatepicker = function(){
	$('.date-picker').not('#template').datepicker({
        autoclose: true,
        language: "<%=locale %>",
		weekStart: 1
    });
}

var initvalidate = function() {
	$('#form_bill').validate({
        errorElement: 'span',
        errorClass: 'help-block',
        focusInvalid: false,
        highlight: function (element) {
        	$(element).closest('.form-group').addClass('has-error');
        	$(element).closest('td').addClass('has-error');
        },
        unhighlight: function (element) {
        	$(element).closest('.form-group').removeClass('has-error');
        	$(element).closest('td').removeClass('has-error');
        },
        invalidHandler: function(event, validator) {
			alert("有必填项未填写，请检查！");
        },
        submitHandler: function (form) { //表单校验通过并提交表单
            App.blockUI({animate: true});
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
	var add = $("#template tbody").children().clone();
	add.removeAttr("id");
	add.find(".date-picker").datepicker({
        autoclose: true,
        language: "<%=locale %>",
		weekStart: 1
    });
	$("#list").append(add);
	add.slideDown();
}

var removelist = function(row){
	$(row).closest('tr').remove();
	sum();
}

var addlist2 = function () {
	var add = $("#template2").clone();
	add.removeAttr("id");
	$("#list2").append(add);
	add.slideDown();
}

var removelist2 = function(row){
	console.log(row);
	$(row).closest('.template2').slideUp(function(){$(this).remove();sum();});
}


var showmodal_projectleader = function(){
	$("#modal_projectleader").modal("show");
}

var selectuser = function(){
	var data = $("#datatable_projectleader").DataTable().rows('.selected').data();
	if(data.length==0){
		swal("Error!", "没有选择人员", "error");
	}else{
		$("#projectleadername").val(data[0].USER_NAME);
		$("#projectleaderid").val(data[0].USER_ID);
		$("#modal_projectleader").modal("hide");
	}
}

var initdatatable_projectleader = function(){
	var datatable = $("#datatable_projectleader");

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

var sum = function(){
	var sum = 0;
	$("input[name='hotelamount'],input[name='foodamount'],input[name='cost'],input[name='businessamount'],input[name='otheramount']").each(function(index,row){
		sum = sum+Number(row.value);
	});
	$("input[name='totalamount']").val(sum.toFixed(2));
}
 
</script>
</tempus:Content>

</tempus:ContentPage>