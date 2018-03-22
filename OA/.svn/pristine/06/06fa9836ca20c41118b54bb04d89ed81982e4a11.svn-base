<%@ page language="java" contentType="text/html; charset=utf-8"    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<% 
	String path = request.getContextPath();     
%>
<ul class="sub-menu" <c:if test="${ROW.selected==true}">style="display: block;"</c:if>>
	<c:forEach items="${menu}" var="ROW" varStatus="S">
		<li class='nav-item<c:if test="${ROW.selected==true}"> active open</c:if>'>
		    <a href="<c:if test="${fn:length(ROW.children)==0}"><%=path %>${ROW.url}</c:if> <c:if test="${fn:length(ROW.children)>0}">javascript:;</c:if>" class="nav-link nav-toggle">
		        <i class="${ROW.icon}"></i>
		        <span class="title">${ROW.text}</span>
		        <c:if test="${ROW.selected==true}">
	           		<span class="selected"></span>
		           	<c:if test="${fn:length(ROW.children)>0}">
		           		<span class='arrow open'></span>
		           	</c:if>
	            </c:if>
	            <c:if test="${ROW.selected!=true}">
	            	<c:if test="${fn:length(ROW.children)>0}">
	         			<span class='arrow'></span>
	         		</c:if>
	            </c:if>
		    </a>
		    <c:if test="${fn:length(ROW.children)>0}">
		       	<c:set var="menu" value="${ROW.children}" scope="request"/>
		       	<jsp:include page="menu.jsp"/>  
		    </c:if>
		</li>
	</c:forEach>
</ul>

