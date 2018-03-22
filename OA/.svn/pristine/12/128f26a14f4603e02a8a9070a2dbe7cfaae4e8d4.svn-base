<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/master/masterPage.tld" prefix="tempus" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="i18n" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="org.springframework.web.servlet.support.RequestContextUtils" language="java"%>
<%
	String locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request).toString();
%>
<tempus:ContentPage materPageId="masterstep">

<tempus:Content contentPlaceHolderId="title">
    <title>权限管理</title>
    <link href="../assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
    <link href="../assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
    <link href="../assets/plugins/zTree_v3/css/metroStyle/metroStyle.css" rel="stylesheet" type="text/css" />
    <link href="../assets/plugins/multi-select/css/multi-select.css" rel="stylesheet" type="text/css" />
    <style>
    	.ms-container{width: 100% !important;}
    	.ms-container>div{height: 500px !important;width: 250px !important;}
    	.ms-list{height: 100% !important;}
    	.custom-header{text-align: center;background-color: #32c5d2;color: white;}
    	.search-input{width: 200px;vertical-align: middle;}
    	.selectbtn{width: 50px;height: 25px;padding: 0px;}
    </style>
</tempus:Content>

<tempus:Content contentPlaceHolderId="main">
<div class="page-content" style="min-height: 1147px;">
    <!-- BEGIN PAGE HEAD-->
    <div class="page-head">
        <!-- BEGIN PAGE TITLE -->
        <div class="page-title">
            <h1><i18n:message code="权限管理" /></h1>
        </div>
        <!-- END PAGE TITLE -->
    </div>
    <!-- END PAGE HEAD-->
    <!-- BEGIN PAGE BASE CONTENT -->
    <div style="background-color: white;padding:15px;">
		<div>
			<button class="btn green" onclick="addrole()"><i class="fa fa-plus"></i><i18n:message code="common.add" /></button>
		</div>
		<div style="margin-top: 20px;">
			<table id="datatable" class="table table-striped table-bordered table-hover table-checkable order-column">
	            <thead>
	                <tr>
	                	<th>角色名称</th>
	                    <th>菜单</th>
	                    <!-- <th>人员</th> -->
	                    <th>Action</th>
	                </tr>
	            </thead>
	            <tbody></tbody>
	    	</table>
    	</div>
    </div>
</div>
    <!-- END PAGE BASE CONTENT -->
<div id="modal_role" class="modal fade in" tabindex="-1" data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title"><i18n:message code="common.add" /></h4>
            </div>
            <div class="modal-body" style="padding: 20px;">
            	<form id="form_role" class="form-horizontal" role="from">
            		<div class="from-body">
            			<div class="form-group">
            				<label class="col-md-3 control-label"><i18n:message code="common.name" /><span class="required">*</span></label>
            				<div class="col-md-9"><input id="role_name" name="name" class="form-control" placeholder=""/></div>
            				<input id="role_id" name="id" type="hidden"/>
            			</div>
            			<div class="form-group">
            				<label class="col-md-3 control-label"><i18n:message code="common.remark" /></label>
            				<div class="col-md-9"><textarea id="role_remark" name="remark" class="form-control"></textarea></div>
            			</div>
            		</div>
            	</form>
            </div>
            <div class="modal-footer">
                <button type="button" data-dismiss="modal" class="btn dark btn-outline"><i18n:message code="common.cancel" /></button>
                <button type="submit" class="btn green" onclick="$('#form_role').submit()"><i18n:message code="common.save" /></button>
            </div>
        </div>
    </div>
</div>

<div id="modal_menu" class="modal fade in" tabindex="-1" data-backdrop="static" data-keyboard="true" >
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">添加菜单权限</h4>
            </div>
            <div class="modal-body" style="padding: 20px;">
            	<div id="rolemenu" class="ztree"></div>
            </div>
            <div class="modal-footer">
                <button type="button" data-dismiss="modal" class="btn dark btn-outline"><i18n:message code="common.cancel" /></button>
                <button type="button" class="btn green" onclick="saverolemenu()"><i18n:message code="common.save" /></button>
            </div>
        </div>
    </div>
</div>

<div id="modal_user" class="modal fade modal-scroll in" tabindex="-1" data-backdrop="static" data-keyboard="true" >
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">添加人员</h4>
            </div>
            <div class="modal-body" style="padding: 20px 20px 60px 20px;">
            	<select id="selectuser" multiple="multiple" name="selectuser[]">
            		<option value="1112">704894-董金鹏</option><option value="1437">802826-叶惜云</option>
				</select>
            </div>
            <div class="modal-footer">
                <button type="button" data-dismiss="modal" class="btn dark btn-outline"><i18n:message code="common.cancel" /></button>
                <button type="button" class="btn green" onclick="saveroleuser()"><i18n:message code="common.save" /></button>
            </div>
        </div>
    </div>
</div>
</tempus:Content>

<tempus:Content contentPlaceHolderId="js">
<script src="../assets/global/scripts/datatable.js" type="text/javascript"></script>
<script src="../assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>
<script src="../assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/jquery-validation/js/additional-methods.min.js" type="text/javascript"></script>
<script src="../assets/plugins/zTree_v3/js/jquery.ztree.core.min.js" type="text/javascript"></script>
<script src="../assets/plugins/zTree_v3/js/jquery.ztree.excheck.min.js" type="text/javascript"></script>
<script src="../assets/plugins/multi-select/js/jquery.multi-select.js" type="text/javascript"></script>
<script src="../assets/plugins/multi-select/js/jquery.quicksearch.js" type="text/javascript"></script>
<script>
var roleid;
$(function(){
	initdatatable();
	initvalidate_role();
	//initztree();
	initmultiselect();
});

var initdatatable = function(){
	$("#datatable").DataTable({
		serverSide: true,
		ordering: false,
		searching: false,
		order: [[0, "asc"]],
		lengthMenu: [[10,20,50,100],[10,20,50,100]],
      	pageLength: 20,
      	pagingType: "bootstrap_full_number",
		dom: '<<t>lp>',
		ajax: {
	        url: './getRoleList'
		},
	    columns: [
	    	{data:"FNAME",orderable: false},
	    	{data:"FMENUS",orderable: false},
	    	/* {data:"FUSERS",orderable: false}, */
	    	{orderable: false,render:function(data, type, row){
	    		return '<div class="btn-group">'
				    		+'<button class="btn btn-xs green dropdown-toggle" type="button" data-toggle="dropdown" aria-expanded="false">'
				    			+'<i class="fa fa-angle-down"></i> Actions'
			    			+'</button>'
			    			+'<ul class="dropdown-menu" role="menu">'
		    					+'<li><a href="javascript:editrole(\''+row.FID+'\');"><span class="icon-docs"></span>Edit</a></li>'
		    					+'<li><a href="javascript:deleterole(\''+row.FID+'\');"><span class="icon-docs"></span>Delete</a></li>'
		    					+'<li><a href="javascript:addmenu(\''+row.FID+'\');"><span class="icon-docs"></span>Add menu</a></li>'
		    					+'<li><a href="javascript:adduser(\''+row.FID+'\');"><span class="icon-docs"></span>Add user</a></li>'
	    					+'</ul>'
    					+' </div>';
	    	}}
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

}

var addrole = function(){
	$("#form_role")[0].reset();
	$("#modal_role").modal("show");
}


var deleterole = function(id){
	swal({
		title: '<i18n:message code="common.deletecomfirmtitle" />',
		text: '<i18n:message code="common.deletecomfirmtext" />',
		type: "warning",
		showCancelButton: true,
		confirmButtonColor: "#DD6B55",
		confirmButtonText: '<i18n:message code="common.confirm" />',
		cancelButtonText: '<i18n:message code="common.cancel" />',
		closeOnConfirm: false,
	},
	function(isConfirm){
		if (isConfirm) {
			$.get("./DeleteRole",{"id": id},function(){
				swal("Deleted!", "", "success");
				$("#datatable").DataTable().draw();
			});
		}
	});
}

var editrole = function(id){
	$.get("./getRoleInfoById",{"id":id},function(data){
		$("#role_id").val(data.FID);
		$("#role_name").val(data.FNAME);
		$("#role_remark").val(data.FREMARK);
		$("#modal_role").modal("show");
	});
}

var initvalidate_role = function() {
	$('#form_role').validate({
        errorElement: 'span',
        errorClass: 'help-block',
        focusInvalid: true,
        rules: {
            name: {
                required: true
            },
            remark: {
            	required: true,
            	maxlength: 250
            }
        },
        messages: {
            name: {
                required: "Department name is required."
            }
        },
        invalidHandler: function (event, validator) { //表单校验失败
			console.log("error");
            //App.scrollTo(error, -200);
        },
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
        	$.post("./SaveRole",data,function(){
        		$("#modal_role").modal("hide");
        		swal("Success!", "", "success");
        		$("#datatable").DataTable().draw();
        	});
        }
    });
}

var zTreeObj;
var zTreesettings={
	check: {
		enable: true,   //true / false 分别表示 显示 / 不显示 复选框或单选框
		autoCheckTrigger: false,   //true / false 分别表示 触发 / 不触发 事件回调函数
		chkStyle: "checkbox",   //勾选框类型(checkbox 或 radio）
		chkboxType: { "Y": "ps", "N": "ps" }   //勾选 checkbox 对于父子节点的关联关系
	},
	view:{
		nameIsHTML: false,
		showIcon: true
	}
}

var initztree = function(){
	$.get("./getRoleMenuState",{},function(data){
		zTreeObj = $.fn.zTree.init($("#rolemenu"), zTreesettings,data);
	});
}

var addmenu = function(id){
	roleid=id;
	$.get("./getRoleMenuState",{"roleid":id},function(data){
		zTreeObj = $.fn.zTree.init($("#rolemenu"), zTreesettings,data);
	});
	$("#modal_menu").modal("show");
}

 var saverolemenu = function(){
	var nodes = zTreeObj.getCheckedNodes(true);
	if(nodes.length==0){
		swal("Error!","没有菜单被选中","error");
	}else{
		var menus = [];
		$.each(nodes,function(i,n){
			//if(n.children.length==0){
				menus.push(n.id);
			//}
		});
		
		$.post("./SaveRoleMenu",{roleid:roleid,menus: menus},function(data){
			$("#modal_menu").modal("hide");
			swal("Success!","","success");
		});
	}
	
}

var adduser = function(id){
	roleid=id;
	App.blockUI({
        target: '.page-content',
        animate: true
    });
	$.get("./getRoleUserList",{"roleid":id},function(data){
		var html = "";
		for(var i=0;i<data.length;i++){
			html=html+'<option value="'+data[i].FID+'" '+(data[i].FSELECTED==1?"selected":"")+'>'+data[i].FCODE+'-'+data[i].FNAME+'</option>';
		}
		$('#selectuser').html(html);
		$('#selectuser').multiSelect('refresh');
		App.unblockUI('.page-content');
		$("#modal_user").modal("show");
	});
}

var searchuser = function(){
	var search = $("#search_user").val();
	$.getJSON("./getUserList",{"search": search,roleid: roleid},function(data){
		var html = "";
		$.each(data,function(i,row){
			html+="<tr><td><label class='mt-checkbox'><input type='checkbox' value='"+row.USER_ID+"' name='users' "+row.CHECKED+" /><span></span></label></td><td>"+row.USER_CODE+"</td><td>"+row.USER_NAME+"</td></tr>";
		});
		$("#datatable_user tbody").html(html);
	});
}

var checkedall = function(chec){
	$("#datatable_user input").prop("checked",$(chec).is(':checked'));
}

var saveroleuser = function(){
	$.post("./SaveRoleUser",{roleid: roleid,users: $("#selectuser").val()},function(data){
		$("#modal_user").modal("hide");
		swal("Success!","","success");
		$("#datatable").DataTable().draw();
	});
}

var initmultiselect = function(){
	$('#selectuser').multiSelect({
		keepOrder: true,
		selectableHeader: "<div><input type='text' class='search-input' autocomplete='off' placeholder='搜索'><button class='btn blue selectbtn' onclick='$(\"#selectuser\").multiSelect(\"select_all\")'>全选</button></div><div class='custom-header'>未选择用户</div>",
  		selectionHeader: "<div><input type='text' class='search-input' autocomplete='off' placeholder='搜索'><button class='btn selectbtn' onclick='$(\"#selectuser\").multiSelect(\"deselect_all\")'>清空</button></div><div class='custom-header'>已选择用户</div>",
  		afterInit: function(ms){
  		    var that = this,
  		        $selectableSearch = that.$selectableUl.parent().find(".search-input"),
  		        $selectionSearch = that.$selectionUl.parent().find(".search-input"),
  		        selectableSearchString = '#'+that.$container.attr('id')+' .ms-elem-selectable:not(.ms-selected)',
  		        selectionSearchString = '#'+that.$container.attr('id')+' .ms-elem-selection.ms-selected';
  		      console.log($selectableSearch);
  		    that.qs1 = $selectableSearch.quicksearch(selectableSearchString)
  		    .on('keydown', function(e){
  		      if (e.which === 40){
  		        that.$selectableUl.focus();
  		        return false;
  		      }
  		    });

  		    that.qs2 = $selectionSearch.quicksearch(selectionSearchString)
  		    .on('keydown', function(e){
  		      if (e.which == 40){
  		        that.$selectionUl.focus();
  		        return false;
  		      }
  		    });
  		  },
  		  afterSelect: function(value){
  		    this.qs1.cache();
  		    this.qs2.cache();
  		  },
  		  afterDeselect: function(value){
  		    this.qs1.cache();
  		    this.qs2.cache();
  		  }
	});
}

</script>
</tempus:Content>

</tempus:ContentPage>