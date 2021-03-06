<%@ page language="java" contentType="text/html; charset=utf-8"    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<% 
	String path = request.getContextPath();     
%>
<div class="page-sidebar navbar-collapse collapse">
    <!-- BEGIN SIDEBAR MENU -->
    <!-- DOC: Apply "page-sidebar-menu-light" class right after "page-sidebar-menu" to enable light sidebar menu style(without borders) -->
    <!-- DOC: Apply "page-sidebar-menu-hover-submenu" class right after "page-sidebar-menu" to enable hoverable(hover vs accordion) sub menu mode -->
    <!-- DOC: Apply "page-sidebar-menu-closed" class right after "page-sidebar-menu" to collapse("page-sidebar-closed" class must be applied to the body element) the sidebar sub menu mode -->
    <!-- DOC: Set data-auto-scroll="false" to disable the sidebar from auto scrolling/focusing -->
    <!-- DOC: Set data-keep-expand="true" to keep the submenues expanded -->
    <!-- DOC: Set data-auto-speed="200" to adjust the sub menu slide up/down speed -->
    <ul class="page-sidebar-menu   " data-keep-expanded="false" data-auto-scroll="true" data-slide-speed="200">
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
    <!-- END SIDEBAR MENU -->
</div>