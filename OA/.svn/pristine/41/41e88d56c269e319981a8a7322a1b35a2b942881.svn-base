<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/master/masterPage.tld" prefix="tempus" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="i18n" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<tempus:ContentPage materPageId="masterstep">

<tempus:Content contentPlaceHolderId="title">
    <title><i18n:message code="menu.title"  /></title>
    <link href="../assets/global/plugins/jstree/dist/themes/default/style.min.css" rel="stylesheet" type="text/css" />
    <link href="../assets/global/plugins/select2/css/select2.min.css" rel="stylesheet" type="text/css" />
    <link href="../assets/global/plugins/select2/css/select2-bootstrap.min.css" rel="stylesheet" type="text/css" />
    <style>
    	.content{
    		
    	}
    </style>
</tempus:Content>

<tempus:Content contentPlaceHolderId="main">
<div class="page-content" style="min-height: 1147px;">
    <!-- BEGIN PAGE HEAD-->
    <div class="page-head">
        <!-- BEGIN PAGE TITLE -->
        <div class="page-title">
            <h1><i18n:message code="menu.title"  /></h1>
        </div>
        <!-- END PAGE TITLE -->
    </div>
    <!-- END PAGE HEAD-->
    <!-- BEGIN PAGE BASE CONTENT -->
    <div style="background-color: white;padding: 20px;">
    	<div>
    		<button class="btn green" onclick="add()"><i18n:message code="common.add" /></button>
    		<button class="btn green" onclick="edit()"><i18n:message code="common.edit" /></button>
    		<button class="btn green" onclick="deletex()"><i18n:message code="common.delete" /></button>
    	</div>
   		<div id="menutree" class="tree-demo" style="margin: 20px;"></div>
    </div>
    <!-- END PAGE BASE CONTENT -->
</div>

<div id="modal_add" class="modal fade in" tabindex="-1" data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title"><i18n:message code="menu.modal_add_head" /></h4>
            </div>
            <div class="modal-body" style="padding: 20px;">
            	<form id="form_addmenu" class="form-horizontal" role="from">
            		<input id="menu.id" name="id" type="hidden"/>
            		<div class="from-body">
            			<div class="form-group">
            				<label class="col-md-3 control-label"><i18n:message code="common.name" /><span class="required">*</span></label>
            				<div class="col-md-9">
            					<div class="col-md-6" style="padding: 0px;">
									<div class="input-group">
		                                <span class="input-group-addon"><i18n:message code="chinese" /></span>
		                                <input id="menu.zh_CN" name="zh_CN" class="form-control" placeholder="">
	                                </div>
                                </div>
       							<div class="col-md-6" style="padding: 0px;">
									<div class="input-group">
		                                <span class="input-group-addon"><i18n:message code="english" /></span>
		                                <input id="menu.en_US" name="en_US" class="form-control" placeholder="">
	                                </div>
                                </div>
							</div>
            			</div>
            			<div class="form-group">
            				<label class="col-md-3 control-label"><i18n:message code="common.icon" /></label>
            				<div class="col-md-9">
            					<select id="menu.icon" name="icon" class="form-control select2">
           							<option value="icon-user">icon-user</option>
									<option value="icon-user-female">icon-user-female</option>
									<option value="icon-users">icon-users</option>
									<option value="icon-user-follow">icon-user-follow</option>
									<option value="icon-user-following">icon-user-following</option>
									<option value="icon-user-unfollow">icon-user-unfollow</option>
									<option value="icon-trophy">icon-trophy</option>
									<option value="icon-speedometer">icon-speedometer</option>
									<option value="icon-social-youtube">icon-social-youtube</option>
									<option value="icon-social-twitter">icon-social-twitter</option>
									<option value="icon-social-tumblr">icon-social-tumblr</option>
									<option value="icon-social-facebook">icon-social-facebook</option>
									<option value="icon-social-dropbox">icon-social-dropbox</option>
									<option value="icon-social-dribbble">icon-social-dribbble</option>
									<option value="icon-shield">icon-shield</option>
									<option value="icon-screen-tablet">icon-screen-tablet</option>
									<option value="icon-screen-smartphone">icon-screen-smartphone</option>
									<option value="icon-screen-desktop">icon-screen-desktop</option>
									<option value="icon-plane">icon-plane</option>
									<option value="icon-notebook">icon-notebook</option>
									<option value="icon-moustache">icon-moustache</option>
									<option value="icon-mouse">icon-mouse</option>
									<option value="icon-magnet">icon-magnet</option>
									<option value="icon-magic-wand">icon-magic-wand</option>
									<option value="icon-hourglass">icon-hourglass</option>
									<option value="icon-graduation">icon-graduation</option>
									<option value="icon-ghost">icon-ghost</option>
									<option value="icon-game-controller">icon-game-controller</option>
									<option value="icon-fire">icon-fire</option>
									<option value="icon-eyeglasses">icon-eyeglasses</option>
									<option value="icon-envelope-open">icon-envelope-open</option>
									<option value="icon-envelope-letter">icon-envelope-letter</option>
									<option value="icon-energy">icon-energy</option>
									<option value="icon-emot">icon-emot</option>
									<option value="icon-smile">icon-smile</option>
									<option value="icon-disc">icon-disc</option>
									<option value="icon-cursor-move">icon-cursor-move</option>
									<option value="icon-crop">icon-crop</option>
									<option value="icon-credit-card">icon-credit-card</option>
									<option value="icon-chemistry">icon-chemistry</option>
									<option value="icon-bell">icon-bell</option>
									<option value="icon-badge">icon-badge</option>
									<option value="icon-anchor">icon-anchor</option>
									<option value="icon-action-redo">icon-action-redo</option>
									<option value="icon-action-undo">icon-action-undo</option>
									<option value="icon-bag">icon-bag</option>
									<option value="icon-basket">icon-basket</option>
									<option value="icon-basket-loaded">icon-basket-loaded</option>
									<option value="icon-book-open">icon-book-open</option>
									<option value="icon-briefcase">icon-briefcase</option>
									<option value="icon-bubbles">icon-bubbles</option>
									<option value="icon-calculator">icon-calculator</option>
									<option value="icon-call-end">icon-call-end</option>
									<option value="icon-call-in">icon-call-in</option>
									<option value="icon-call-out">icon-call-out</option>
									<option value="icon-compass">icon-compass</option>
									<option value="icon-cup">icon-cup</option>
									<option value="icon-diamond">icon-diamond</option>
									<option value="icon-direction">icon-direction</option>
									<option value="icon-directions">icon-directions</option>
									<option value="icon-docs">icon-docs</option>
									<option value="icon-drawer">icon-drawer</option>
									<option value="icon-drop">icon-drop</option>
									<option value="icon-earphones">icon-earphones</option>
									<option value="icon-earphones-alt">icon-earphones-alt</option>
									<option value="icon-feed">icon-feed</option>
									<option value="icon-film">icon-film</option>
									<option value="icon-folder-alt">icon-folder-alt</option>
									<option value="icon-frame">icon-frame</option>
									<option value="icon-globe">icon-globe</option>
									<option value="icon-globe-alt">icon-globe-alt</option>
									<option value="icon-handbag">icon-handbag</option>
									<option value="icon-layers">icon-layers</option>
									<option value="icon-map">icon-map</option>
									<option value="icon-picture">icon-picture</option>
									<option value="icon-pin">icon-pin</option>
									<option value="icon-playlist">icon-playlist</option>
									<option value="icon-present">icon-present</option>
									<option value="icon-printer">icon-printer</option>
									<option value="icon-puzzle">icon-puzzle</option>
									<option value="icon-speech">icon-speech</option>
									<option value="icon-vector">icon-vector</option>
									<option value="icon-wallet">icon-wallet</option>
									<option value="icon-arrow-down">icon-arrow-down</option>
									<option value="icon-arrow-left">icon-arrow-left</option>
									<option value="icon-arrow-right">icon-arrow-right</option>
									<option value="icon-arrow-up">icon-arrow-up</option>
									<option value="icon-bar-chart">icon-bar-chart</option>
									<option value="icon-bulb">icon-bulb</option>
									<option value="icon-calendar">icon-calendar</option>
									<option value="icon-control-end">icon-control-end</option>
									<option value="icon-control-forward">icon-control-forward</option>
									<option value="icon-control-pause">icon-control-pause</option>
									<option value="icon-control-play">icon-control-play</option>
									<option value="icon-control-rewind">icon-control-rewind</option>
									<option value="icon-control-start">icon-control-start</option>
									<option value="icon-cursor">icon-cursor</option>
									<option value="icon-dislike">icon-dislike</option>
									<option value="icon-equalizer">icon-equalizer</option>
									<option value="icon-graph">icon-graph</option>
									<option value="icon-grid">icon-grid</option>
									<option value="icon-home">icon-home</option>
									<option value="icon-like">icon-like</option>
									<option value="icon-list">icon-list</option>
									<option value="icon-login">icon-login</option>
									<option value="icon-logout">icon-logout</option>
									<option value="icon-loop">icon-loop</option>
									<option value="icon-microphone">icon-microphone</option>
									<option value="icon-music-tone">icon-music-tone</option>
									<option value="icon-music-tone-alt">icon-music-tone-alt</option>
									<option value="icon-note">icon-note</option>
									<option value="icon-pencil">icon-pencil</option>
									<option value="icon-pie-chart">icon-pie-chart</option>
									<option value="icon-question">icon-question</option>
									<option value="icon-rocket">icon-rocket</option>
									<option value="icon-share">icon-share</option>
									<option value="icon-share-alt">icon-share-alt</option>
									<option value="icon-shuffle">icon-shuffle</option>
									<option value="icon-size-actual">icon-size-actual</option>
									<option value="icon-size-fullscreen">icon-size-fullscreen</option>
									<option value="icon-support">icon-support</option>
									<option value="icon-tag">icon-tag</option>
									<option value="icon-trash">icon-trash</option>
									<option value="icon-umbrella">icon-umbrella</option>
									<option value="icon-wrench">icon-wrench</option>
									<option value="icon-ban">icon-ban</option>
									<option value="icon-bubble">icon-bubble</option>
									<option value="icon-camcorder">icon-camcorder</option>
									<option value="icon-camera">icon-camera</option>
									<option value="icon-check">icon-check</option>
									<option value="icon-clock">icon-clock</option>
									<option value="icon-close">icon-close</option>
									<option value="icon-cloud-download">icon-cloud-download</option>
									<option value="icon-cloud-upload">icon-cloud-upload</option>
									<option value="icon-doc">icon-doc</option>
									<option value="icon-envelope">icon-envelope</option>
									<option value="icon-eye">icon-eye</option>
									<option value="icon-flag">icon-flag</option>
									<option value="icon-folder">icon-folder</option>
									<option value="icon-heart">icon-heart</option>
									<option value="icon-info">icon-info</option>
									<option value="icon-key">icon-key</option>
									<option value="icon-link">icon-link</option>
									<option value="icon-lock">icon-lock</option>
									<option value="icon-lock-open">icon-lock-open</option>
									<option value="icon-magnifier">icon-magnifier</option>
									<option value="icon-magnifier-add">icon-magnifier-add</option>
									<option value="icon-magnifier-remove">icon-magnifier-remove</option>
									<option value="icon-paper-clip">icon-paper-clip</option>
									<option value="icon-paper-plane">icon-paper-plane</option>
									<option value="icon-plus">icon-plus</option>
									<option value="icon-pointer">icon-pointer</option>
									<option value="icon-power">icon-power</option>
									<option value="icon-refresh">icon-refresh</option>
									<option value="icon-reload">icon-reload</option>
									<option value="icon-settings">icon-settings</option>
									<option value="icon-star">icon-star</option>
									<option value="icon-symbol-female">icon-symbol-female</option>
									<option value="icon-symbol-male">icon-symbol-male</option>
									<option value="icon-target">icon-target</option>
									<option value="icon-volume-1">icon-volume-1</option>
									<option value="icon-volume-2">icon-volume-2</option>
									<option value="icon-volume-off">icon-volume-off</option>
            					</select>
            				</div>
            			</div>
            			<div class="form-group">
            				<label class="col-md-3 control-label"><i18n:message code="common.url" /></label>
            				<div class="col-md-9"><input id="menu.url" name="url" class="form-control" placeholder=""/></div>
            			</div>
            			<div class="form-group">
            				<label class="col-md-3 control-label"><i18n:message code="menu.modal_add_parentmenu" /></label>
            				<div class="col-md-9">
            					<input id=menu.parentid name="parentmenu" type="hidden" />
            					<input id="menu.parenttext" class="form-control" readonly="readonly" onclick="showparentmenu()" />
            				</div>
            			</div>
            		</div>
            	</form>
            </div>
            <div class="modal-footer">
                <button type="button" data-dismiss="modal" class="btn dark btn-outline"><i18n:message code="common.cancel" /></button>
                <button type="submit" class="btn green" onclick="savemenu()"><i18n:message code="common.save" /></button>
            </div>
        </div>
    </div>
</div>

<div id="modal_parentmenu" class="modal fade in" tabindex="-1" data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title"><i18n:message code="menu.modal_add_head" /></h4>
            </div>
            <div class="modal-body" style="padding: 20px;">
            	<div id="parentmenutree" class="tree-demo"></div>
            </div>
            <div class="modal-footer">
                <button type="button" data-dismiss="modal" class="btn dark btn-outline"><i18n:message code="common.cancel" /></button>
                <button type="button" data-dismiss="modal" class="btn green" onclick="selectparentmenu()"><i18n:message code="common.save" /></button>
            </div>
        </div>
    </div>
</div>
</tempus:Content>

<tempus:Content contentPlaceHolderId="js">
<script src="../assets/global/plugins/jstree/dist/jstree.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/select2/js/select2.full.min.js" type="text/javascript"></script>
<script src="../assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script>
$(function(){
	initjstree();
	initselect2();
});

$.fn.modal.Constructor.prototype.enforceFocus = function () { }

var initjstree = function(){
	$(".tree-demo").jstree({
        "core" : {
            "themes" : {
                "responsive": false
            }, 
            "check_callback" : true,
            'data' : {
                'url' : function (node) {
                  return '../MenuManage/getMenuTree';
                },
                'data' : function (node) {
                  return { 'parent' : node.id };
                }
            }
        },
        "types" : {
            "default" : {
                "icon" : "fa fa-folder icon-state-warning icon-lg"
            },
            "file" : {
                "icon" : "fa fa-file icon-state-warning icon-lg"
            }
        },
        "state" : { "key" : "demo3" },
        "plugins" : [ "dnd", "state", "types" ]
    });
}
	

var initselect2 = function(){
	$(".select2").select2({
		placeholder: '',
		width: "100%",
	    allowClear: true,
	    templateResult: function(opt){
	    	return $("<span class='"+opt.text+"'></span><span style='margin-left: 20px;'>"+opt.text+"</span>");
	    },
	    templateSelection: function(opt){
	    	return $("<span class='"+opt.text+"'></span><span style='margin-left: 20px;'>"+opt.text+"</span>");
	    }
	});
}

var add = function(){
	$("#form_addmenu")[0].reset();
	$("#modal_add").modal("show");
}

var showparentmenu = function(){
	$("#modal_parentmenu").modal("show");
}

var selectparentmenu = function(){
	var parentmenu = $("#parentmenutree").jstree().get_selected(true)[0];
	$("#menu\\.parenttext").val(parentmenu.text);
	$("#menu\\.parentid").val(parentmenu.id);
}

var savemenu = function(){
	var data = $('#form_addmenu').serializeJSON();
	if($('#form_addmenu').validate()){
		$.post("./SaveMenu",data,function(){
			$("#modal_add").modal("hide");
			swal("Success!", "操作成功", "success");
			$(".tree-demo").each(function(){
				$(this).jstree(true).refresh();
			});
		});
	}
}

$.fn.validate = function(){
	var result=true;
	this.find(".required").each(function(){
		var inputs = $(this).closest('.form-group').find(".form-control");
		inputs.each(function(){
			var input = $(this);
			input.unbind('click').bind('click',function(){
				input.closest('.form-group').removeClass('has-error');
			});
			if(input.val()==""){
				input.closest('.form-group').addClass("has-error");
				if(result){
					result=false;
				}
			}
		});
	});
	return result;
}

var edit = function(){
	var menu = $("#menutree").jstree().get_selected(true)[0];
	$.get("./getMenuInfoById",{"id":menu.id},function(data){
		$("#menu\\.id").val(data.FID);
		$("#menu\\.zh_CN").val(data.FZH_CN);
		$("#menu\\.en_US").val(data.FEN_US);
		$("#menu\\.icon").val(data.FICON);
		$("#menu\\.url").val(data.FURL);
		$("#menu\\.parentid").val(data.FPARENTID);
		$("#menu\\.parenttext").val(data.FPARENTTEXT);
		$("#modal_add").modal("show");
	});
}

var deletex = function(){
	var menu = $("#menutree").jstree().get_selected(true)[0];
	if(menu.children.length>0){
		swal("Error!", "该菜单下有子菜单，无法删除", "error");
	}else{
		swal({
			title: '<i18n:message code="common.deletecomfirmtitle" />',
			text: '<i18n:message code="common.deletecomfirmtext" />',
			type: "warning",
			showCancelButton: true,
			confirmButtonColor: "#DD6B55",
			confirmButtonText: '<i18n:message code="common.confirm" />',
			cancelButtonText: '<i18n:message code="common.cancel" />',
			closeOnConfirm: false,
		},
		function(isConfirm){
			if (isConfirm) {
				$.get("./DeleteMenu",{"id":menu.id},function(){
					swal("Deleted!", "删除成功", "success");
					$(".tree-demo").each(function(){
						$(this).jstree(true).refresh();
					});
				});
			}
		});
	}
}

</script>
</tempus:Content>

</tempus:ContentPage>