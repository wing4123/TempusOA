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
    <style>
    	.required{
   		    color: #e02222;
		    font-size: 12px;
		    padding-left: 2px;
    	}
    </style>
    	<div class="form-horizontal">
    		<button class='btn btn-success pull-right' style="margin-top: -65px;" onclick="printform('${returnObject.travel.FID}')">打印</button>
    		<input type="hidden" id="status" name="status" />
    		<input type="hidden" id="id" name="id" value="${returnObject.travel.FID}"/>
   			<div class="form-group">
           		<label class="col-md-2 control-label">标题<span class="readonly">*</span></label>
           		<div class="col-md-10"><input class="form-control" name="name" value="${returnObject.travel.FNAME}" readonly /></div>
   			</div>
    		<div class="row">
    			<div class="col-md-4">
    				<div class="form-group">
		           		<label class="col-md-6 control-label">申请人<span class="readonly">*</span></label>
		           		<div class="col-md-6"><input class="form-control" value="${returnObject.travel.FCREATORNAME}" readonly /></div>
		   			</div>
    			</div>
    			<div class="col-md-4">
    				<div class="form-group">
		           		<label class="col-md-6 control-label">部门<span class="readonly">*</span></label>
		           		<div class="col-md-6"><input class="form-control" value="${returnObject.travel.FDEPARTMENTNAME}" readonly /></div>
		   			</div>
    			</div>
    			<div class="col-md-4">
    				<div class="form-group">
		           		<label class="col-md-6 control-label">申请日期<span class="readonly">*</span></label>
		           		<div class="col-md-6"><input class="form-control" value="${fn:substring(returnObject.travel.FLASTUPDATETIME, 0, 10)}" readonly /></div>
		   			</div>
    			</div>
    		</div>
    		<div class="row">
    			<div class="col-md-4">
    				<div class="form-group">
		           		<label class="col-md-6 control-label">开始日期<span class="readonly">*</span></label>
		           		<div class="col-md-6">
		           			<div class="input-group date date-picker" data-date-format="yyyy-mm-dd" >
			                    <input name="begindate" class="form-control" value="${fn:substring(returnObject.travel.FBEGINDATE, 0, 10)}" readonly />
			                    <span class="input-group-btn" style="vertical-align: top;">
			                        <button class="btn default" type="button">
			                            <i class="fa fa-calendar"></i>
			                        </button>
			                    </span>
			                </div>
		           		</div>
		   			</div>
    			</div>
    			<div class="col-md-4">
    				<div class="form-group">
		           		<label class="col-md-6 control-label">结束日期<span class="readonly">*</span></label>
		           		<div class="col-md-6">
		           			<div class="input-group date date-picker" data-date-format="yyyy-mm-dd" >
			                    <input name="enddate" class="form-control" value="${fn:substring(returnObject.travel.FENDDATE, 0, 10)}" readonly />
			                    <span class="input-group-btn" style="vertical-align: top;">
			                        <button class="btn default" type="button">
			                            <i class="fa fa-calendar"></i>
			                        </button>
			                    </span>
			                </div>
		           		</div>
		   			</div>
    			</div>
    			<div class="col-md-4">
    				<div class="form-group">
		           		<label class="col-md-6 control-label">出差天数<span class="readonly">*</span></label>
		           		<div class="col-md-6"><input name="days" class="form-control" type="number" min=0 value="${returnObject.travel.FDAYS}" readonly /></div>
		   			</div>
    			</div>
    		</div>
    		<div class="row">
    			<div class="col-md-4">
    				<div class="form-group">
		           		<label class="col-md-6 control-label">出差地点<span class="readonly">*</span></label>
		           		<div class="col-md-6"><input class="form-control" name="location" value="${returnObject.travel.FLOCATION}" readonly /></div>
		   			</div>
    			</div>
    			<div class="col-md-4">
    				<div class="form-group">
		           		<label class="col-md-6 control-label">地域<span class="readonly">*</span></label>
		           		<div class="col-md-6">
		           			<select class="form-control" name="area" disabled >
			                	<option value="1" <c:if test="${returnObject.travel.FAREA=='1'}">selected</c:if> >内地</option>
			                	<option value="2" <c:if test="${returnObject.travel.FAREA=='2'}">selected</c:if> >港澳台</option>
			                	<option value="3" <c:if test="${returnObject.travel.FAREA=='3'}">selected</c:if> >国外</option>
			                </select>
		           		</div>
		   			</div>
    			</div>
    			<div class="col-md-4">
    				<div class="form-group">
		           		<label class="col-md-6 control-label">项目类型<span class="readonly">*</span></label>
		           		<div class="col-md-6">
							<select class="form-control" name="projecttype" disabled >
			                	<option value="1" <c:if test="${returnObject.travel.FPROJECTTYPE=='1'}">selected</c:if> >一般项目</option>
			                	<option value="2" <c:if test="${returnObject.travel.FPROJECTTYPE=='2'}">selected</c:if> >投资整合项目</option>
			                	<option value="3" <c:if test="${returnObject.travel.FPROJECTTYPE=='3'}">selected</c:if> >IT项目</option>
			                </select>
						</div>
		   			</div>
    			</div>
    		</div>
    		<div class="row">
    			<div class="col-md-4">
    				<div class="form-group">
		           		<label class="col-md-6 control-label">项目负责人<span class="readonly">*</span></label>
		           		<div class="col-md-6">
	                        <div class="input-icon right">
		                        <i class="fa fa-user font-blue"></i>
		                        <input id="projectleadername" class="form-control" value="${returnObject.travel.FPROJECTMANAGERNAME}" readonly readonly="readonly" onclick="showmodal_projectleader()" />
		                        <input id="projectleaderid" name="projectleader" value="${returnObject.travel.FPROJECTMANAGERID}" type="hidden"/>
		                    </div>
	                    </div>
		   			</div>
    			</div>
    		</div>
    		<div class="row">
    			<div class="col-md-12">
    				<div class="form-group">
		           		<label class="col-md-2 control-label">出差事由<span class="readonly">*</span></label>
		           		<div class="col-md-10"><textarea class="form-control" name="cause" readonly >${returnObject.travel.FCAUSE}</textarea></div>
		   			</div>
    			</div>
    		</div>
    		<div style="padding-left: 10p;"><span style="display: inline-block;width: 5px;height:20px;background-color: green;vertical-align: middle;"></span>行程</div>
     		<table class="table table-striped table-hover table-responsive">
    			<thead style="background-color: #e7ecf1;">
    				<tr>
    					<th>交通工具<span class="required">*</span></th>
    					<th>日期<span class="required">*</span></th>
    					<th>出发城市<span class="required">*</span></th>
    					<th>抵达城市<span class="required">*</span></th>
    					<th>航班/车次</th>
    					<th>费用<span class="required">*</span></th>
    					<th>其他说明</th>
    				</tr>
    			</thead>
    			<tbody id="list">
    			<c:forEach items="${returnObject.travelrouts}" var="row" varStatus="S">
    				<tr>
    					<td>
    						<select class="form-control" name="transport" disabled style="min-width: 80px;" >
			                	<option value="1" <c:if test="${row.FTRANSPORT=='1'}">selected</c:if> >飞机</option>
			                	<option value="2" <c:if test="${row.FTRANSPORT=='2'}">selected</c:if> >火车</option>
			                	<option value="4" <c:if test="${row.FTRANSPORT=='3'}">selected</c:if> >市内交通</option>
			                	<option value="4" <c:if test="${row.FTRANSPORT=='4'}">selected</c:if> >私车公用</option>
			                </select>
    					</td>
    					<td>
    						<div class="input-group date date-picker" data-date-format="yyyy-mm-dd" >
			                    <input name="departuredate" class="form-control" value="${fn:substring(row.FDEPARTUREDATE, 0, 10)}"  readonly />
			                    <span class="input-group-btn" style="vertical-align: top;">
			                        <button class="btn default" type="button">
			                            <i class="fa fa-calendar"></i>
			                        </button>
			                    </span>
			                </div>
    					</td>
    					<td><input class="form-control" name="departurecity" value="${row.FDEPARTURECITY}" readonly /></td>
    					<td><input class="form-control" name="destinationcity" value="${row.FDESTINATIONCITY}" readonly /></td>
    					<td><input class="form-control" name="trips" value="${row.FTRIPS}" readonly /></td>
    					<td><input class="form-control" name="cost" value="${row.FCOST}" type="number" min=0 readonly /></td>
    					<td><input class="form-control" name="remark" value="${row.FREMARK}" readonly /></td>
    				</tr>
    			</c:forEach>
    			</tbody>
    		</table>
    		<div><span style="display: inline-block;width: 5px;height:20px;background-color: green;vertical-align: middle;"></span>业务招待</div>
    		<div class="form-group" style="background-color: #e7ecf1;margin: 0px;padding: 8px;font-weight: bold;position: relative;">
    			<div class="col-md-2">客户<span class="required">*</span></div>
    			<div class="col-md-2">地点<span class="required">*</span></div>
    			<div class="col-md-2">事由<span class="required">*</span></div>
    			<div class="col-md-2">金额<span class="required">*</span></div>
    			<div class="col-md-2">参加人数<span class="required">*</span></div>
    			<div class="col-md-2">公司陪同人员<span class="required">*</span></div>
    		</div>
    		<div id="list2">
	    		<c:forEach items="${returnObject.business}" var="row" varStatus="s">
	    		<div class="template2">
	    			<div class="form-group" style="margin: 10px 0px 10px 0px;padding: 0px 8px 0px 8px;position: relative;">
						<div class="col-md-2"><input class="form-control" name="businessclient" value="${row.FCLIENT}" readonly /></div>
						<div class="col-md-2"><input class="form-control" name="businesslocation" value="${row.FLOCATION}" readonly /></div>
						<div class="col-md-2"><input class="form-control" name="businesscause" value="${row.FCAUSE}" readonly /></div>
						<div class="col-md-2"><input class="form-control" name="businessamount" value="${row.FAMOUNT}" type="number" min=0 readonly /></div>
						<div class="col-md-2"><input class="form-control" name="businessjoinnumber" value="${row.FJOINNUMBER}" type="number" readonly /></div>
						<div class="col-md-2"><input class="form-control" name="businessstaff" value="${row.FSTAFF}" readonly /></div>
					</div>
					<hr style="margin: 0px;" />	
				</div>
	    		</c:forEach>
    		</div>
    		<div style="margin-top: 20px;"><span style="display: inline-block;width: 5px;height:20px;background-color: green;vertical-align: middle;"></span>住宿和餐饮</div>
    		<table class="table table-responsive" >
    			<thead style="background-color: #e7ecf1;" >
    				<tr>
    					<th>项目</th>
						<th>项目单价</th>
						<th>人天数</th>
						<th>金额</th>
						<th>备注</th>
    				</tr>
    			</thead>
    			<tbody>
    				<tr>
    					<td>酒店/住宿</td>
    					<td><input class="form-control" min=0 type="number" name="hotelprice" value="${returnObject.travel.FHOTELPRICE}" readonly /></td>
    					<td><input class="form-control" min=0 type="number" name="hoteldays" value="${returnObject.travel.FHOTELDAYS}" readonly /></td>
    					<td><input class="form-control" min=0 type="number" name="hotelamount" value="${returnObject.travel.FHOTELAMOUNT}" readonly /></td>
    					<td><input class="form-control" name="hotelremark" value="${returnObject.travel.FHOTELREMARK}" readonly /></td>
    				</tr>
    				<tr>
    					<td>餐饮</td>
    					<td><input class="form-control" min=0 type="number" name="foodprice" value="${returnObject.travel.FFOODPRICE}" readonly /></td>
    					<td><input class="form-control" min=0 type="number" name="fooddays" value="${returnObject.travel.FFOODDAYS}" readonly /></td>
    					<td><input class="form-control" min=0 type="number" name="foodamount" value="${returnObject.travel.FFOODAMOUNT}" readonly /></td>
    					<td><input class="form-control" name="foodremark" value="${returnObject.travel.FFOODREMARK}" readonly /></td>
    				</tr>
    				<tr>
    					<td>其他</td>
    					<td colspan="2" ></td>
    					<td><input class="form-control" min=0 type="number" name="otheramount" value="${returnObject.travel.FOTHERAMOUNT}" readonly /></td>
    					<td><input class="form-control" name="otherremark" value="${returnObject.travel.FOTHERREMARK}" readonly /></td>
    				</tr>
    			</tbody>
    		</table>
    		<hr/>
    		<div class="form-group" style="margin-bottom: 0px;">
    			<label class="col-md-2 control-label">总金额</label>
    			<div class="col-md-2"><input class="form-control" name="totalamount" value="${returnObject.travel.FTOTALAMOUNT}" type="number" min=0 readonly /></div>
    			<label class="col-md-2 control-label">币种</label>
    			<div class="col-md-2">
    				<select class="form-control" name="currency" disabled >
	                	<option value="CNY" <c:if test="${returnObject.travel.FCURRENCY=='CNY'}">selected</c:if> >人民币</option>
	                	<option value="HKD" <c:if test="${returnObject.travel.FCURRENCY=='HKD'}">selected</c:if> >港币</option>
	                	<option value="USD" <c:if test="${returnObject.travel.FCURRENCY=='USD'}">selected</c:if> >美元</option>
	                </select>
    			</div>
    		</div>
    	</div>
    	
<script>
	var printform = function(id){
	    $.get("../Print/Travel",{"id":id},function(jsp){
	    	var newwindow = window.open();
	    	if(newwindow){
	    		newwindow.document.write(jsp);
		    	newwindow.print();
		    	newwindow.document.getElementById("print").execWB(7,1);
	    	}else{
	    		alert("请启用浏览器弹出窗口，刷新后重试！");
	    	}
	    });
	}
</script>