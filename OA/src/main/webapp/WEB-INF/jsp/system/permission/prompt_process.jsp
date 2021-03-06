<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="i18n" %>

	<div class="modal-dialog modal-lg">
		<div class="modal-content">
		    <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
		        <h4 class="modal-title">选择用户</h4>
		    </div>
		    <div class="modal-body" style="padding: 20px;max-height: 700px;overflow-y: auto;">
		    	<table id="datatable_process_modal" class="table table-striped table-bordered table-condensed table-hover" style="width: 100%;">
		            <thead>
		                <tr>
		                	<th>
                                <label class="mt-checkbox mt-checkbox-single mt-checkbox-outline">
                                    <input type="checkbox" class="group-checkable" />
                                    <span></span>
                                </label>
                            </th>
		                    <th>流程编号</th>
		                    <th>流程名称</th>
		                </tr>
		            </thead>
		            <tbody></tbody>
	            </table>
		    </div>
		    <div class="modal-footer">
		        <button type="button" data-dismiss="modal" class="btn dark btn-outline"><i18n:message code="common.cancel" /></button>
		        <button type="button" class="btn green" onclick="addProcess()"><i18n:message code="确定" /></button>
		    </div>
		</div>
	</div>

<script>

var initdatatable_process_modal = function(){
	var table = $("#datatable_process_modal");
	table.DataTable({
		serverSide: true,
		ordering: true,
		searching: true,
		order: [[1, "asc"]],
		lengthMenu: [[10,20,50,100],[10,20,50,100]],
      	pageLength: 20,
      	pagingType: "bootstrap_full_number",
      	bStateSave: true,
		dom: 'lftipr',
        ajax: {
        	url: "./prcessModal",
        	data: function(d){
        		d.roleid = selectRoleId;
        	}
        },
        columns: [
        	{render:function(data, type, row){
        		return '<label class="mt-checkbox mt-checkbox-single mt-checkbox-outline">'
		                	+'<input type="checkbox" class="checkboxes" value="'+ row.FID +'" />'
		                    +'<span></span>'
		                +'</label>';
        	}, orderable: false},
        	{data:"FKEY", orderable: false},
        	{data:"FNAME", orderable: false}
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
	
	table.find('.group-checkable').change(function () {
        var checked = jQuery(this).is(":checked");
        table.find("tbody .checkboxes").each(function () {
            if (checked) {
                $(this).prop("checked", true);
                $(this).parents('tr').addClass("active");
            } else {
                $(this).prop("checked", false);
                $(this).parents('tr').removeClass("active");
            }
        });
    });

    table.on('change', 'tbody tr .checkboxes', function () {
        $(this).parents('tr').toggleClass("active");
    });
}

initdatatable_process_modal();

</script>