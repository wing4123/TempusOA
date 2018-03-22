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
    <link href="../assets/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css" />
    <style>
    	.table .btn {margin-right: 0px;}
    	.readonly{color: red;}
    </style>
	<div class="form-horizontal">
    		<input type="hidden" id="status" name="status" />
    		<input type="hidden" id="id" name="id" value="${returnObject.FID}"/>
			<div class="form-group">
				<div class="form-group-inline">
	        		<label class="col-md-2 control-label">标题<span class="readonly">*</span></label>
	        		<div class="col-md-10"><input class="form-control" name="name" value="${returnObject.FNAME}" readonly /></div>
        		</div>
			</div>
			<div class="form-group">
        		<div class="form-group-inline">
	        		<label class="col-md-2 control-label">申请人<span class="readonly">*</span></label>
	        		<div class="col-md-4"><input class="form-control" value="${returnObject.USER_NAME}" readonly /></div>
	        	</div>
				<div class="form-group-inline">
	        		<label class="col-md-2 control-label">部门<span class="readonly">*</span></label>
	        		<div class="col-md-4"><input class="form-control" value="${returnObject.DEPARTMENTNAME}" readonly /></div>
        		</div>
			</div>
     		<table class="table table-striped table-hover table-responsive">
    			<thead style="background-color: #e7ecf1;">
    				<tr>
    					<th style="width: 145px;">假期类型<span class="readonly">*</span></th>
    					<th style="width: 200px;">开始时间<span class="readonly">*</span></th>
    					<th style="width: 200px;">结束时间<span class="readonly">*</span></th>
    					<th style="width: 100px;">请假天数<span class="readonly">*</span></th>
    					<th>备注</th>
    				</tr>
    			</thead>
    			<tbody id="list">
    				<c:forEach items="${returnObject.entry}" var="row" varStatus="s">
	    				<tr>
	    					<td class="form-group-inline">
	    						<select class="form-control" name="type" disabled >
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
		                            <input type="text" size="16" class="form-control" name="begintime" value="${row.FBEGINTIME}" readonly >
		                            <span class="input-group-addon">
		                                <button class="btn default" type="button">
		                                    <i class="fa fa-calendar"></i>
		                                </button>
		                            </span>
		                        </div>
	    					</td>
	    					<td>
		                        <div class="input-group date form_datetime form_datetime bs-datetime form-group-inline">
		                            <input type="text" size="16" class="form-control" name="endtime" value="${row.FENDTIME}" readonly >
		                            <span class="input-group-addon">
		                                <button class="btn default date-set" type="button">
		                                    <i class="fa fa-calendar"></i>
		                                </button>
		                            </span>
		                        </div>
	    					</td>
	    					<td class="form-group-inline" ><input class="form-control" type="number" name="days" min="0" value="${row.FDAYS}" readonly /></td>
	    					<td><input class="form-control" name="remark" value="${row.FREMARK}" readonly /></td>
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
	        		<label class="col-md-2 control-label">请假事由<span class="readonly">*</span></label>
	        		<div class="col-md-10"><textarea class="form-control" name="cause" rows="3" readonly >${returnObject.FCAUSE}</textarea></div>
	        	</div>
			</div>
			<div class="form-group">
        		<div class="form-group-inline">
	        		<label class="col-md-2 control-label">工作安排<span class="readonly">*</span></label>
	        		<div class="col-md-10"><textarea class="form-control" name="workingarrangements" rows="3" readonly >${returnObject.FWORKINGARRANGEMENTS}</textarea></div>
	        	</div>
			</div>
    		<div class="form-group">
        		<div class="form-group-inline">
	        		<label class="col-md-2 control-label">交接人<span class="readonly">*</span></label>
	        		<div class="col-md-4">
	        			<div class="input-group" onclick="$('#modal_handoverperson').modal('show')" >
		                    <input id="handoverpersonname" class="form-control" value="${returnObject.FHANDOVERPERSONNAME}" readonly />
		                    <input type="hidden" id="handoverpersonid" name="handoverperson" value="${returnObject.FHANDOVERPERSONID}" class="form-control" />
		                    <span class="input-group-btn">
		                        <button class="btn default" type="button">
		                            <i class="fa fa-user"></i>
		                        </button>
		                    </span>
		                </div>
	        		</div>
        		</div>
			</div>
    	</div>    	
