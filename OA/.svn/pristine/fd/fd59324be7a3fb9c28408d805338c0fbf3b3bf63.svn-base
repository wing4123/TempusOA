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
			<div class="form-group" style="text-align: center;background-color: #e7ecf1;margin: 0px 0px 15px 0px;height: 30px;line-height: 30px;">
				<div class="col-md-4">开始时间</div>
				<div class="col-md-4">结束时间</div>
				<div class="col-md-3">公出时长(小时)</div>
			</div>
			<div id="list">
				<c:forEach items="${returnObject.entry}" var="row" varStatus="s">
					<div class="form-group">
						<div class="form-group-inline">
			        		<div class="col-md-4">
								<div class="input-group date form_datetime form_datetime bs-datetime form-group-inline">
		                            <input type="text" size="16" class="form-control" name="begintime" value="${fn:substring(row.FBEGINTIME, 0, 16)}" readonly >
		                            <span class="input-group-addon">
		                                <button class="btn default" type="button">
		                                    <i class="fa fa-calendar"></i>
		                                </button>
		                            </span>
		                        </div>
							</div>
		        		</div>
		        		<div class="form-group-inline">
			        		<div class="col-md-4">
								<div class="input-group date form_datetime form_datetime bs-datetime form-group-inline">
		                            <input type="text" size="16" class="form-control" name="endtime" value="${fn:substring(row.FENDTIME, 0, 16)}" readonly >
		                            <span class="input-group-addon">
		                                <button class="btn default" type="button">
		                                    <i class="fa fa-calendar"></i>
		                                </button>
		                            </span>
		                        </div>
							</div>
		        		</div>
		        		<div class="form-group-inline">
			        		<div class="col-md-4"><input type="text" class="form-control" name="hours" value="${row.FHOURS}" readonly ></div>
		        		</div>
		        		<div class="form-group-inline" style="margin-top: 50px;">
			        		<label class="col-md-2 control-label">公出事由<span class="required">*</span></label>
		        			<div class="col-md-9"><textarea name="cause" class="form-control" readonly >${row.FCAUSE}</textarea></div>
		        		</div>
					</div>
				</c:forEach>
			</div>
			<div class="form-group">
				<label class="col-md-2 col-md-offset-7 control-label">合计<span class="readonly">*</span></label>
        		<div class="col-md-2"><input id="totalhours" name="totalhours" class="form-control" value="${returnObject.FTOTALHOURS}" readonly /></div>
			</div>
		</div>
