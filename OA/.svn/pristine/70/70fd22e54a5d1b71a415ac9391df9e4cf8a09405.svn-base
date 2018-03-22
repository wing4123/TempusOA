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
	 	<div id="form_bill" class="form-horizontal">
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
	        		<div class="col-md-4"><input class="form-control" value="${returnObject.FCREATORNAME}" readonly /></div>
	        	</div>
				<div class="form-group-inline">
	        		<label class="col-md-2 control-label">部门<span class="readonly">*</span></label>
	        		<div class="col-md-4"><input class="form-control" value="${returnObject.FDEPARTMENTNAME}" readonly /></div>
        		</div>
			</div>
			
			<div class="form-group">
				<div class="form-group-inline">
	        		<label class="col-md-2 control-label">上班时间<span class="readonly">*</span></label>
	        		<div class="col-md-4">
						<div class="input-group date form_datetime form_datetime bs-datetime form-group-inline">
                            <input type="text" size="16" class="form-control" name="begintime" value="${fn:substring(returnObject.FBEGINTIME, 0, 16)}" readonly >
                            <span class="input-group-addon">
                                <button class="btn default" type="button">
                                    <i class="fa fa-calendar"></i>
                                </button>
                            </span>
                        </div>
					</div>
        		</div>
        		<div class="form-group-inline">
	        		<label class="col-md-2 control-label">下班时间<span class="readonly">*</span></label>
	        		<div class="col-md-4">
						<div class="input-group date form_datetime form_datetime bs-datetime form-group-inline">
                            <input type="text" size="16" class="form-control" name="begintime" value="${fn:substring(returnObject.FENDTIME, 0, 16)}" readonly >
                            <span class="input-group-addon">
                                <button class="btn default" type="button">
                                    <i class="fa fa-calendar"></i>
                                </button>
                            </span>
                        </div>
					</div>
        		</div>
			</div>
			
    		<div class="form-group">
				<div class="form-group-inline">
	        		<label class="col-md-2 control-label">异常类型<span class="readonly">*</span></label>
	        		<div class="col-md-4">
	        			<select class="form-control" name="type" disabled>
	        				<option value="1" <c:if test="${returnObject.FTYPE=='1'}" >selected</c:if> >忘记打卡</option>
	        				<option value="2" <c:if test="${returnObject.FTYPE=='2'}" >selected</c:if> >打卡异常</option>
	        			</select>
	        		</div>
        		</div>
        		<div class="form-group-inline">
	        		<label class="col-md-2 control-label">异常说明<span class="readonly">*</span></label>
	        		<div class="col-md-4"><input class="form-control" name="description" value="${returnObject.FDESCRIPTION}" readonly /></div>
        		</div>
			</div>
    	</div>
		