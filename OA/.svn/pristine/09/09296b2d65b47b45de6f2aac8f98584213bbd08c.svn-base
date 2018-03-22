<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@ page import="org.springframework.context.ApplicationContext"%>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List" %>
<%@ page import="com.alibaba.fastjson.JSONObject"%>
<%@ page import="cn.tempus.system.root.HomeService" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="i18n" %>
<% 
	String basepath = request.getContextPath();
	String user = ((JSONObject) JSONObject.toJSON(session.getAttribute("USER"))).getString("userid");

	ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
	HomeService homeservice = (HomeService)ac.getBean("homeservice");
	List<HashMap<String,Object>> mytobedo = homeservice.mytobedo(user);
	List<HashMap<String,Object>> mymessage = homeservice.getMyMessageList(user);
	pageContext.setAttribute("mytobedo", mytobedo);
	pageContext.setAttribute("mymessage", mymessage);
%>
<div class="page-header navbar navbar-fixed-top">
    <!-- BEGIN HEADER INNER -->
    <div class="page-header-inner ">
        <!-- BEGIN LOGO -->
        <div class="page-logo">
            <a href="<%=basepath %>/index">
                <img src="<%=basepath %>/image/tempus logo.png" alt="logo" class="logo-default" style="height: auto;width: 150px;margin: 18px 10px;" />
            </a>
            <div class="menu-toggler sidebar-toggler">
                <!-- DOC: Remove the above "hide" to enable the sidebar toggler button on header -->
            </div>
        </div>
<%--         <div class="page-logo">
            <a href="<%=basepath %>/index">
                <img src="<%=basepath %>/assets/layouts/layout4/img/logo-light.png" alt="logo" class="logo-default" /> </a>
            <div class="menu-toggler sidebar-toggler">
                <!-- DOC: Remove the above "hide" to enable the sidebar toggler button on header -->
            </div>
        </div> --%>
        <!-- END LOGO -->
        <!-- BEGIN RESPONSIVE MENU TOGGLER -->
        <a href="javascript:;" class="menu-toggler responsive-toggler" data-toggle="collapse" data-target=".navbar-collapse"> </a>
        <!-- END RESPONSIVE MENU TOGGLER -->
        <!-- BEGIN PAGE ACTIONS -->
        <!-- DOC: Remove "hide" class to enable the page header actions -->
        
        <!-- BEGIN PAGE TOP -->
        <div class="page-top">
            <!-- BEGIN TOP NAVIGATION MENU -->
            <div class="top-menu">
                <ul class="nav navbar-nav pull-right">
                    <li class="separator hide"> </li>
                    <li class="dropdown dropdown-extended dropdown-notification dropdown-dark" id="header_notification_bar">
                        <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
                        	<i class="icon-bell"></i>
                            <c:if test="${mymessage.size() > 0}"><span class="badge badge-success"> ${mymessage.size()} </span></c:if>
                        </a>
                        <ul class="dropdown-menu">
                            <li class="external">
                                <h3>你有${mymessage.size()}条未读消息</h3>
                                <a href="<%=basepath %>/Message/messageList">view all</a>
                            </li>
                            <li>
                                <ul class="dropdown-menu-list scroller" style="height: 250px;font-size: 12px;" data-handle-color="#637283">
                                    <c:forEach var="row" items="${mymessage}" varStatus="s">
	                                    <li>
	                                        <a style="font-size: 12px;" onclick="readmsg('${row.FID}','${row.FURL}')" >
	                                            <span class="time">${row.FSENDTIME}</span>
	                                            <span class="details">
	                                                <span class="label label-sm label-icon label-success">
	                                                    <i class="fa fa-bell-o"></i>
	                                                </span>
	                                                ${row.FTITLE}
	                                            </span>
	                                        </a>
	                                    </li>
                                   </c:forEach>
                                </ul>
                            </li>
                        </ul>
                    </li>
                    <li class="dropdown dropdown-extended dropdown-tasks dropdown-dark" id="header_task_bar">
                        <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
                            <i class="icon-calendar"></i>
                            <c:if test="${mytobedo.size() > 0}"><span class="badge badge-success"> ${mytobedo.size()} </span></c:if>
                        </a>
                        <ul class="dropdown-menu extended tasks">
                            <li class="external">
                                <h3>你有${mytobedo.size()}个待办任务</h3>
                                <a href="<%=basepath %>/MyWorkFlow/MyToBeDo">查看所有</a>
                            </li>
                            <li>
                                <ul class="dropdown-menu-list scroller" style="height: 275px;" data-handle-color="#637283">
                                    <c:forEach var="row" items="${mytobedo}" varStatus="s">
                                    <li>
                                        <a href="<%=basepath %>/MyWorkFlow/ApprovalPage?taskid=${row.FTASKID}" style="font-size: 12px;">
                                            <div> ${row.FPROCESSNAME} </div>
                                            <div>
                                            	<span class=""> 发起人：${row.FSTARTUSER}</span>
                                            	<span class="pull-right">${row.FCREATETIME}</span>
                                            </div>
                                        </a>
                                    </li>
                                    </c:forEach>
                                </ul>
                            </li>
                        </ul>
                    </li>
                    <li class="separator hide"> </li>
                    <li class="dropdown dropdown-user dropdown-dark">
                        <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
                            <span class="username username-hide-on-mobile"> ${USER.name} </span>
                            <!-- DOC: Do not remove below empty space(&nbsp;) as its purposely used -->
                            <img alt="" class="img-circle" src="<%=basepath %>/assets/layouts/layout4/img/avatar9.jpg" /> </a>
                        <ul class="dropdown-menu dropdown-menu-default">
                            <li>
                                <a href="<%=basepath %>/logout"><i class="icon-key"></i> <i18n:message code="master.LogOut" /> </a>
                            </li>
                            <li>
                                <a href="<%=basepath %>/modal_ChangePassword" data-target="#modal_changepassword" data-toggle="modal"><i class="icon-key"></i> <i18n:message code="master.ChangePassword" /> </a>
                            </li>
                        </ul>
                    </li>
                    <!-- END USER LOGIN DROPDOWN -->
                </ul>
            </div>
            <!-- END TOP NAVIGATION MENU -->
        </div>
        <!-- END PAGE TOP -->
    </div>
    <!-- END HEADER INNER -->
</div>

<div id="modal_changepassword" class="modal fade" tabindex="-1" data-backdrop="static" >
	<div class="modal-dialog">
		<div class="modal-content">
		    
		</div>
	</div>
</div>

<script>
function readmsg(id,url){
	$.get("<%=basepath %>/Message/readMessage",{"id":id},function(data){
		if(data==1 && url!=""){
			location.href="<%=basepath %>"+url;
		}
	});
}
</script>
