<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/master/masterPage.tld" prefix="tempus" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="i18n" %>
<%@ page import="org.springframework.web.servlet.support.RequestContextUtils" language="java"%>
<%@ page import="java.util.UUID" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request).toString();
%>
    	<div class="form-horizontal">
   			<div class="form-group">
           		<label class="col-md-2 control-label">合同编号</label>
           		<div class="col-md-4"><input class="form-control" name="number" value="${returnObject.contract.FNUMBER}" readonly /></div>
   			</div>
   			<div class="form-group">
	            <label class="col-md-2 control-label">合同类型<span class="required">*</span></label>
	            <div class="col-md-10">
	                <div class="mt-radio-inline" id="type">
	                	<c:forEach items="${returnObject.typelist}" var="row" varStatus="S">
		                    <label class="mt-radio" <c:if test="${row.FVALUE==returnObject.contract.FTYPE2}">style='color: red;'</c:if>>
		                        <input type="radio" name="type2" value="${row.FVALUE}" <c:if test="${row.FVALUE==returnObject.contract.FTYPE2}">checked</c:if> disabled >${row.FNAME}<span></span>
		                    </label>
	                    </c:forEach>
	                </div>
	            </div>
	        </div>
   			<div class="form-group">
	            <label class="col-md-2 control-label">腾邦签署主体<span class="required">*</span></label>
	            <div class="col-md-4">
	                <select class="form-control" name="company" disabled >
	                	<c:forEach items="${returnObject.company}" var="row" varStatus="S">
	                        <option value="${row.FID}" <c:if test="${row.FID==returnObject.contract.FCOMPANY}">selected</c:if> >${row.FNAME}</option>
	                    </c:forEach>
	                </select>
	            </div>
	        </div>
	        <div class="form-group">
	            <label class="col-md-2 control-label">是否采用我方合同模板<span class="required">*</span></label>
	            <div class="col-md-2">
	                <div class="mt-radio-inline" style="padding-bottom: 0px;">
	                    <label class="mt-radio">
	                        <input type="radio" name="template" value="1" <c:if test="${returnObject.contract.FTEMPLATE==1}">checked</c:if> disabled />是<span></span>
	                    </label>
	                    <label class="mt-radio">
	                        <input type="radio" name="template" value="0" <c:if test="${returnObject.contract.FTEMPLATE==0}">checked</c:if> disabled />否<span></span>
	                    </label>
	                </div>
	            </div>
	            <div class="col-md-10 col-md-offset-2">
	            	<textarea id="templatereason" <c:if test="${returnObject.contract.FTEMPLATE=='1'}">style='display: none;'</c:if> name="templatereason" class="form-control" placeholder="原因" readonly >${returnObject.contract.FTEMPLATEREASON}</textarea>
	            </div>
	        </div>
   			<div class="form-group">
	            <label class="col-md-2 control-label">是否新合同<span class="required">*</span></label>
	            <div class="col-md-10">
	                <div class="mt-radio-inline" style="padding-bottom: 0px;">
	                    <label class="mt-radio">
	                        <input type="radio" name="newcontract" value="1" onclick="fnewcontract(this.value)" <c:if test="${returnObject.contract.FNEWCONTRACT==1}">checked</c:if> disabled />是 <span></span>
	                    </label>
	                    <label class="mt-radio">
	                        <input type="radio" name="newcontract" value="0" onclick="fnewcontract(this.value)" <c:if test="${returnObject.contract.FNEWCONTRACT==0}">checked</c:if> disabled />否，就合同续期 <span></span>
	                    </label>
	                </div>
	            </div>
	            <div class="col-md-10 col-md-offset-2">
	            	<textarea id="oldcontractchange" <c:if test="${returnObject.contract.FNEWCONTRACT==1}">style='display: none;'</c:if> name="oldcontractchange" class="form-control" readonly>${returnObject.contract.FOLDCONTRACTCHANGE}</textarea>
	            </div>
	        </div>
   			<div class="form-group">
	            <label class="col-md-2 control-label">合同简介<span class="required">*</span></label>
	            <div class="col-md-10">
	            	<div class="row">
		            	<div class="col-md-5">
			                <label class="control-label">对方名称<span class="required">*</span></label>
			                <input class="form-control" name="sidename" value="${returnObject.contract.FSIDENAME}" readonly />
		                </div>
	                </div>
	                <div class="row">
		                <div class="col-md-12">
			                <label class="control-label">内容（目的）<span class="required">*</span></label>
			                <textarea class="form-control" name="content" readonly>${returnObject.contract.FCONTENT}</textarea>
		                </div>
	                </div>
	                <div class="row">
		                <div class="col-md-2">
			                <label class="control-label">金额<span class="required">*</span></label>
			                <input class="form-control" id="amount" name="amount" value="${returnObject.contract.FAMOUNT}" readonly />
		                </div>
		                <div class="col-md-2">
			                <label class="control-label">币种<span class="required">*</span></label>
			                <select class="form-control" id="currency" name="currency" disabled>
			                	<option value="">请选择</option>
			                	<option value="CNY" <c:if test="${returnObject.contract.FCURRENCY=='CNY'}">selected</c:if> >人民币</option>
			                	<option value="HKD" <c:if test="${returnObject.contract.FCURRENCY=='HKD'}">selected</c:if> >港币</option>
			                	<option value="USD" <c:if test="${returnObject.contract.FCURRENCY=='USD'}">selected</c:if> >美元币</option>
			                </select>
		                </div>
	                </div>
	                <div class="row">
		                <div class="col-md-12">
			                <label class="control-label">期限（是否可续期）<span class="required">*</span></label>
			                <input class="form-control" name="term" VALUE="${returnObject.contract.FTERM}" readonly />
		                </div>
	                </div>
	            </div>
	        </div>
    	</div>
