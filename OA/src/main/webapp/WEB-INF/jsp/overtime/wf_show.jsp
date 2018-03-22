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
	        		<div class="col-md-4"><input class="form-control" value="${returnObject.USER_NAME}" readonly /></div>
	        	</div>
				<div class="form-group-inline">
	        		<label class="col-md-2 control-label">部门<span class="readonly">*</span></label>
	        		<div class="col-md-4"><input class="form-control" value="${returnObject.DEPARTMENTNAME}" readonly /></div>
        		</div>
			</div>
    		<div class="form-group">
        		<div class="form-group-inline">
	        		<label class="col-md-2 control-label">加班事由<span class="readonly">*</span></label>
	        		<div class="col-md-10"><textarea class="form-control" name="cause" rows="3" readonly >${returnObject.FCAUSE}</textarea></div>
	        	</div>
			</div>			
    		<div class="form-group" style="background-color: #e7ecf1;margin: 0px;padding: 8px 0px;font-weight: bold;text-align: center;">
    			<div class="col-md-3">开始时间<span class="readonly">*</span></div>
    			<div class="col-md-3">结束时间<span class="readonly">*</span></div>
    			<div class="col-md-2">加班时长（小时）<span class="readonly">*</span></div>
    			<div class="col-md-3">加班性质<span class="readonly">*</span></div>
    		</div>
    		<div id="list" style="margin-top: 15px;text-align: center;">
    		<c:forEach items="${returnObject.entry}" var="row" varStatus="s">
				<div class="form-group">
					<div class="col-md-3">
						<div class="input-group date form_datetime form_datetime bs-datetime form-group-inline">
                            <input type="text" size="16" class="form-control" name="begintime" value="${row.FBEGINTIME}" readonly >
                            <span class="input-group-addon">
                                <button class="btn default" type="button">
                                    <i class="fa fa-calendar"></i>
                                </button>
                            </span>
                        </div>
					</div>
					<div class="col-md-3">
						<div class="input-group date form_datetime form_datetime bs-datetime form-group-inline">
                            <input type="text" size="16" class="form-control" name="begintime" value="${row.FENDTIME}" readonly >
                            <span class="input-group-addon">
                                <button class="btn default" type="button">
                                    <i class="fa fa-calendar"></i>
                                </button>
                            </span>
                        </div>						
					</div>
					<div class="col-md-2"><input class="form-control" name="hours" type="number" min=0 value="${row.FHOURS}" readonly /></div>
					<div class="col-md-3">
						<select class="form-control" disabled >
							<option value="1" <c:if test="${row.FTYPE=='1'}">selected</c:if> >工作日延长加班</option>
							<option value="2" <c:if test="${row.FTYPE=='2'}">selected</c:if> >休息日加班</option>
							<option value="3" <c:if test="${row.FTYPE=='3'}">selected</c:if> >法定假日加班</option>
						</select>
					</div>
				</div>
				</c:forEach>
    		</div>
    	</div>
