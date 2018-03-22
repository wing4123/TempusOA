<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/master/masterPage.tld" prefix="tempus" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="i18n" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<tempus:ContentPage materPageId="masterstep">

<tempus:Content contentPlaceHolderId="title">
    <title><i18n:message code="腾邦控股"  /></title>
    <style>
    	.card-icon i {
		    font-size: 50px;
		    border: 2px solid #ecf0f4;
		    -webkit-border-radius: 50%;
		    -moz-border-radius: 50%;
		    border-radius: 50%;
		    padding: 47px 30px;
		    margin: 30px 0;
		}
		.card-icon,.card-title{text-align: center;}
		.card-title span {
		    font-size: 18px;
		    font-weight: 600;
		    color: #373d43;
		}
		.mt-overlay{background-color: rgba(0,0,0,0) !important;}
    </style>
</tempus:Content>

<tempus:Content contentPlaceHolderId="main">
<div class="page-content" style="min-height: 1147px;"><!-- assets/pages/media/bg/5.jpg -->
    <div class="row" style="background-image: url(./image/11.jpg);height: 500px;background-position: center;margin: -10px -20px 40px -20px;text-align: center;">
        <div class="col-md-12">
            <h1 style="color: #FFFFFF;font-size: 55px;fongt-weight: 600px;margin-top: 130px;text-shadow: 1px 1px 0 rgba(0,0,0,.2);">腾邦控股</h1>
            <h2 style="color: #FFFFFF;font-size: 22px;font-weight: 400;margin-top: 30px;margin-bottom: 40px;">业务申请与审批系统</h2>
        </div>
    </div>
    <div class="row margin-bottom-20" style="">
	    <!-- <div class="col-lg-3 col-md-6">
	    	<a href="./MyWorkFlow/Initiate">
		        <div class="portlet light">
		            <div class="card-icon">
		                <i class="icon-badge font-red-sunglo theme-font"></i>
		            </div>
		            <div class="card-title">
		                <span> 发起申请 </span>
		            </div>
		            <div class="card-desc">
		                <span></span>
		            </div>
		        </div>
	        </a>
	    </div>
	    <div class="col-lg-3 col-md-6">
	    	<a href="./MyWorkFlow/MyToBeDo">
		        <div class="portlet light">
		            <div class="card-icon">
		                <i class="icon-layers font-green-haze theme-font"></i>
		            </div>
		            <div class="card-title">
		                <span> 我的任务 </span>
		            </div>
		            <div class="card-desc">
		                <span></span>
		            </div>
		        </div>
	        </a>
	    </div>
	    <div class="col-lg-3 col-md-6">
	    	<a href="MyWorkFlow/MyProcess">
		        <div class="portlet light">
		            <div class="card-icon">
		                <i class="icon-basket font-purple-wisteria theme-font"></i>
		            </div>
		            <div class="card-title">
		                <span> 我的申请 </span>
		            </div>
		            <div class="card-desc">
		                <span></span>
		            </div>
		        </div>
	        </a>
	    </div>
	    <div class="col-lg-3 col-md-6">
	    	<a href="./Message/messageList">
	        <div class="portlet light">
	            <div class="card-icon">
	                <i class="icon-bell font-blue theme-font"></i>
	            </div>
	            <div class="card-title">
	                <span> 知会信息 </span>
	            </div>
	            <div class="card-desc">
	                <span></span>
	            </div>
	        </div>
	        </a>
	    </div> -->
	    
	    
	    <div class="portlet-body col-md-3">
	        <div class="mt-element-overlay">
	            <div class="row">
	                <div class="col-md-12">
	                    <div class="mt-overlay-3">
	                        <img src="./image/SZ.jpg" />
	                        <div class="mt-overlay">
	                            <h2>发起申请</h2>
	                            <a class="mt-info" href="./MyWorkFlow/Initiate">打开</a>
	                        </div>
	                    </div>
	                </div>
	            </div>
	        </div>
	    </div>
	    <div class="portlet-body col-md-3">
	        <div class="mt-element-overlay">
	            <div class="row">
	                <div class="col-md-12">
	                    <div class="mt-overlay-3">
	                        <img src="./image/MY.jpg" />
	                        <div class="mt-overlay">
	                            <h2>我的任务</h2>
	                            <a class="mt-info" href="./MyWorkFlow/MyToBeDo">打开</a>
	                        </div>
	                    </div>
	                </div>
	            </div>
	        </div>
	    </div>
	    <div class="portlet-body col-md-3">
	        <div class="mt-element-overlay">
	            <div class="row">
	                <div class="col-md-12">
	                    <div class="mt-overlay-3">
	                        <img src="./image/SG.jpg" />
	                        <div class="mt-overlay">
	                            <h2>我的申请</h2>
	                            <a class="mt-info" href="./MyWorkFlow/MyProcess">打开</a>
	                        </div>
	                    </div>
	                </div>
	            </div>
	        </div>
	    </div>
	    <div class="portlet-body col-md-3">
	        <div class="mt-element-overlay">
	            <div class="row">
	                <div class="col-md-12">
	                    <div class="mt-overlay-3">
	                        <img src="./image/SH.jpg" />
	                        <div class="mt-overlay">
	                            <h2>知会信息</h2>
	                            <a class="mt-info" href="./Message/messageList">打开</a>
	                        </div>
	                    </div>
	                </div>
	            </div>
	        </div>
	    </div>
	    
	</div>
    
</div>
</tempus:Content>

<tempus:Content contentPlaceHolderId="js">
<script>

</script>
</tempus:Content>

</tempus:ContentPage>