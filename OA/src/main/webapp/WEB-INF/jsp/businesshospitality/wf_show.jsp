<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/master/masterPage.tld" prefix="tempus" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="i18n" %>
<%@ page import="org.springframework.web.servlet.support.RequestContextUtils" language="java"%>
<%@ page import="java.util.UUID" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
	String locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request).toString();
%>
    	<div class="form-horizontal" >
    		<button class='btn btn-success pull-right' style="margin-top: -65px;" onclick="printform('${returnObject.FID}')">打印</button>
    		<input type="hidden" id="status" name="status" />
    		<input type="hidden" id="id" name="id" value="${returnObject.FID}"/>
    		<div class="row">
    			<div class="col-md-10 col-md-offset-1">
    				<div class="form-group" style="padding: 0px 15px;">
		           		<label class="col-md-2 control-label">标题<span class="readonly">*</span></label>
		           		<div class="col-md-10"><input class="form-control" name="name" value="${returnObject.FNAME}" readonly /></div>
		   			</div>
    				<div class="col-md-6">
		   				<div class="form-group">
			           		<label class="col-md-4 control-label">申请人</label>
			           		<div class="col-md-8"><input class="form-control" value="${returnObject.FCREATORNAME}" readonly /></div>
			   			</div>
			   			<div class="form-group">
			           		<label class="col-md-4 control-label">金额<span class="readonly">*</span></label>
			           		<div class="col-md-8"><input class="form-control" id="amount" name="amount" value="${returnObject.FAMOUNT}" readonly /></div>
			   			</div>	
	    			</div>
	    			<div class="col-md-6">
			   			<div class="form-group">
			           		<label class="col-md-4 control-label">部门</label>
			           		<div class="col-md-8"><input class="form-control" value="${returnObject.FDEPARTMENTNAME}" readonly /></div>
			   			</div>
 			   			<div class="form-group">
			                <label class="col-md-4 control-label">币种<span class="readonly">*</span></label>
			                <div class="col-md-8">
				                <select class="form-control" name="currency" disabled >
				                	<option value="">请选择</option>
				                	<option value="CNY" <c:if test="${returnObject.FCURRENCY=='CNY'}" >selected</c:if> >人民币 CNY</option>
				                	<option value="HKD" <c:if test="${returnObject.FCURRENCY=='HKD'}" >selected</c:if> >港币 HKD</option>
				                	<option value="USD" <c:if test="${returnObject.FCURRENCY=='USD'}" >selected</c:if> >美元币 USD</option>
				                </select>
			                </div>
		                </div>
	    			</div>
	    			<div class="form-group" style="padding: 0px 15px;">
		           		<label class="col-md-2 control-label">客户名称<span class="readonly">*</span></label>
		           		<div class="col-md-10"><input class="form-control" name="client" value="${returnObject.FCLIENT}" readonly /></div>
		   			</div>
		   			<div class="form-group" style="padding: 0px 15px;">
		           		<label class="col-md-2 control-label">招待地点<span class="readonly">*</span></label>
		           		<div class="col-md-10"><input class="form-control" name="location" value="${returnObject.FLOCATION}" readonly /></div>
		   			</div>   			
		   			<div class="form-group" style="padding: 0px 15px;">
		           		<label class="col-md-2 control-label">事由<span class="readonly">*</span></label>
		           		<div class="col-md-10"><textarea class="form-control" name="cause" readonly >${returnObject.FCAUSE}</textarea></div>
		   			</div>
		   			<div class="col-md-6">
			   			<div class="form-group">
			           		<label class="col-md-4 control-label">申请日期<span class="readonly">*</span></label>
			           		<div class="col-md-8">
			           			<div class="input-group date date-picker" data-date-format="yyyy-mm-dd" >
				                    <input name="date" class="form-control" value="${fn:substring(returnObject.FDATE, 0, 10)}" readonly />
				                    <span class="input-group-btn" style="vertical-align: top;">
				                        <button class="btn default" type="button">
				                            <i class="fa fa-calendar"></i>
				                        </button>
				                    </span>
				                </div>
			           		</div>
			   			</div>
		   			</div>
		   			<div class="col-md-6">
		   				<div class="form-group">
			           		<label class="col-md-4 control-label">参加人数<span class="readonly">*</span></label>
			           		<div class="col-md-8"><input class="form-control" name="peoplenumber" value="${returnObject.FPEOPLENUMBER}" readonly onkeyup="clearNoNum(this)" /></div>
			   			</div>
		   			</div>
		   			<div class="form-group" style="padding: 0px 15px;">
		           		<label class="col-md-2 control-label">公司陪同人员<span class="readonly">*</span></label>
		           		<div class="col-md-10"><input class="form-control" name="staff" value="${returnObject.FSTAFF}" readonly /></div>
		   			</div>
    			
    			</div>
    		</div>
    	</div>
    	
<script>
var printform = function(id){
    $.get("../Print/BusinessHospitality",{"id":id},function(jsp){
    	var newwindow = window.open();
    	if(newwindow){
    		newwindow.document.write(jsp);
	    	newwindow.print();
	    	newwindow.document.getElementById("print").execWB(7,1)
    	}else{
    		alert("请启用浏览器弹出窗口，刷新后重试！");
    	}
    });
}

</script>  