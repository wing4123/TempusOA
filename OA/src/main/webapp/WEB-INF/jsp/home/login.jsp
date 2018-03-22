<%@ page import="java.net.URLDecoder"%>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="org.springframework.web.servlet.support.RequestContextUtils" language="java"%>
<%@ page import="java.util.Enumeration" language="java"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="i18n" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<% 
	String path = request.getContextPath();
	String redirect0 = request.getParameter("redirect");
	String redirect = redirect0==null?"":URLDecoder.decode(redirect0, "UTF-8");
	String locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request).toString();
	request.setAttribute("locale", locale);
	Enumeration<String> names = session.getAttributeNames();
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Login</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta content="width=device-width, initial-scale=1" name="viewport" />
        <meta content="" name="author" />
        <!-- BEGIN GLOBAL MANDATORY STYLES -->
        <!-- <link href="https://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700&subset=all" rel="stylesheet" type="text/css" /> -->
        <link href="<%=path %>/assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
        <link href="<%=path %>/assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
        <link href="<%=path %>/assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="<%=path %>/assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css" />
        <!-- END GLOBAL MANDATORY STYLES -->
        <!-- BEGIN PAGE LEVEL PLUGINS -->
        <link href="<%=path %>/assets/global/plugins/select2/css/select2.min.css" rel="stylesheet" type="text/css" />
        <link href="<%=path %>/assets/global/plugins/select2/css/select2-bootstrap.min.css" rel="stylesheet" type="text/css" />
        <!-- END PAGE LEVEL PLUGINS -->
        <!-- BEGIN THEME GLOBAL STYLES -->
        <link href="<%=path %>/assets/global/css/components.min.css" rel="stylesheet" id="style_components" type="text/css" />
        <link href="<%=path %>/assets/global/css/plugins.min.css" rel="stylesheet" type="text/css" />
        <!-- END THEME GLOBAL STYLES -->
        <!-- BEGIN PAGE LEVEL STYLES -->
        <link href="<%=path %>/assets/pages/css/login.min.css" rel="stylesheet" type="text/css" />
        <!-- END PAGE LEVEL STYLES -->
        <!-- BEGIN THEME LAYOUT STYLES -->
        <!-- END THEME LAYOUT STYLES -->
        <link rel="shortcut icon" href="<%=path %>/favicon.ico" /> 
        <style>
        	.login {
			    background-color: #000000 !important;
			}
			.content{
				background-color: rgba(0,0,0,0) !important;
				box-shadow: 0 0 5px rgba(81, 203, 238, 1);
				z-index: 1;
			}
			.login .content .form-actions{
				border-bottom: none !important;
				padding-bottom: 0px !important;
			}
			.login .content .form-control{
				background-color: rgba(0,0,0,0) !important;
			}
        </style>
        <style>
			html, body { background: #000; margin: 0; padding:0;}
			canvas { width: 100%; height: 100%; position: absolute; }
			
			/* Demo Buttons Style */
			.codrops-demos {
				font-size: 0.8em;
				text-align:center;
				position:absolute;
				z-index:99;
				width:96%;
			}
			
			.codrops-demos a {
				display: inline-block;
				margin: 0.35em 0.1em;
				padding: 0.5em 1.2em;
				outline: none;
				text-decoration: none;
				text-transform: uppercase;
				letter-spacing: 1px;
				font-weight: 700;
				border-radius: 2px;
				font-size: 110%;
				border: 2px solid transparent;
				color:#fff;
			}
			
			.codrops-demos a:hover,
			.codrops-demos a.current-demo {
				border-color: #383a3c;
			}
		</style>
    </head>
    <!-- END HEAD -->

    <body class=" login">
        <!-- BEGIN LOGO -->
        <div class="logo">
        	<img src="<%=path %>/image/tempus logo.png" alt="" style="width: 250px;height: auto;" />
        </div>
        <!-- END LOGO -->
        <!-- BEGIN LOGIN -->
        <div class="content">
   	    	<div style="display: inline-block;position: fixed;top: 0px;right: 0px;color: white;">
	       		<i18n:message code="login.language" />:
	       		<select style="color: black;" onchange="changeLanguage(this)">
	       			<option <c:if test="${locale=='zh_CN'}">selected</c:if> value="zh_CN">简体中文</option>
	       			<option <c:if test="${locale=='en_US'}">selected</c:if> value="en_US">English</option>
	       		</select>
	       	</div>
            <!-- BEGIN LOGIN FORM -->
            <form class="login-form" action="" method="post" style="opacity: 0.8;">
                <h3 class="form-title font-purple-wisteria"><i18n:message code="login.login" /></h3>
                <div id="empty" class="alert alert-danger display-hide">
                    <button class="close" data-close="alert"></button>
                    <span><i18n:message code="login.empty" /></span>
                </div>
                <div id="loginfailed" class="alert alert-danger display-hide">
                    <button class="close" data-close="alert"></button>
                    <span><i18n:message code="login.loginfailed" /></span>
                </div>
                <div class="form-group">
                    <!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
                    <label class="control-label visible-ie8 visible-ie9"><i18n:message code="login.username" /></label>
                    <input id="username" class="form-control form-control-solid placeholder-no-fix" type="text" autocomplete="off" placeholder='<i18n:message code="login.username" />' name="username" value="${cookie.userName.value}" /> </div>
                <div class="form-group">
                    <label class="control-label visible-ie8 visible-ie9"><i18n:message code="login.password" /></label>
                    <input id="password" class="form-control form-control-solid placeholder-no-fix" type="password" autocomplete="off" placeholder='<i18n:message code="login.password" />' name="password" onkeydown="if(event.keyCode==13){handleLogin();}" /> </div>
                <div class="form-actions">
                    <button type="submit" class="btn purple-wisteria uppercase" style="width: 100%;" onclick="handleLogin()"><i18n:message code="login.login" /></button>
                    <%-- <label class="rememberme check mt-checkbox mt-checkbox-outline">
                        <input type="checkbox" name="remember" value="1" /><i18n:message code="login.remember" />
                        <span></span>
                    </label>
                    <a href="javascript:;" id="forget-password" class="forget-password"><i18n:message code="login.forgotpassword" /></a> --%>
                </div>
            </form>
            <!-- END LOGIN FORM -->
            <!-- BEGIN FORGOT PASSWORD FORM -->
            <form class="forget-form">
                <h3 class="font-green">Forget Password ?</h3>
                <p> Enter your e-mail address below to reset your password. </p>
                <div class="form-group">
                    <input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="Email" name="email" /> </div>
                <div class="form-actions">
                    <button type="button" id="back-btn" class="btn green btn-outline">Back</button>
                    <button type="submit" class="btn btn-success uppercase pull-right">Submit</button>
                </div>
            </form>
            <!-- END FORGOT PASSWORD FORM -->
        </div>
        <div class="copyright"> 2017 © Tempus </div>
        <canvas id="canvas" style="width: 100%;height: 100%;position: absolute;top: 0px;"></canvas>
        <!-- BEGIN CORE PLUGINS -->
        <script src="<%=path %>/assets/global/plugins/jquery.min.js" type="text/javascript"></script>
        <script src="<%=path %>/assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="<%=path %>/assets/global/plugins/js.cookie.min.js" type="text/javascript"></script>
        <script src="<%=path %>/assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
        <script src="<%=path %>/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
        <script src="<%=path %>/assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
        <!-- END CORE PLUGINS -->
        <!-- BEGIN PAGE LEVEL PLUGINS -->
        <script src="<%=path %>/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
        <script src="<%=path %>/assets/global/plugins/jquery-validation/js/additional-methods.min.js" type="text/javascript"></script>
        <!-- END PAGE LEVEL PLUGINS -->
        <!-- BEGIN THEME GLOBAL SCRIPTS -->
        <script src="<%=path %>/assets/global/scripts/app.min.js" type="text/javascript"></script>
        <!-- END THEME GLOBAL SCRIPTS -->
        <script>
            
            var handleLogin = function() {
                $('.login-form').validate({
                    errorElement: 'span', //default input error message container
                    errorClass: 'help-block', // default input error message class
                    focusInvalid: false, // do not focus the last invalid input
                    rules: {
                        username: {
                            required: true
                        },
                        password: {
                            required: true
                        },
                        remember: {
                            required: false
                        }
                    },

                    messages: {
                        username: {
                            required: "Username is required."
                        },
                        password: {
                            required: "Password is required."
                        }
                    },

                    invalidHandler: function(event, validator) { //display error alert on form submit   
                        $('#empty', $('.login-form')).show();
                    },

                    highlight: function(element) { // hightlight error inputs
                        $(element).closest('.form-group').addClass('has-error'); // set error class to the control group
                    },

                    success: function(label) {
                        label.closest('.form-group').removeClass('has-error');
                        label.remove();
                    },

                    errorPlacement: function(error, element) {
                        error.insertAfter(element.closest('.input-icon'));
                    },

                    submitHandler: function(form) {
                    	login();
                    }
                });

                $('.login-form input').keypress(function(e) {
                    if (e.which == 13) {
                        if ($('.login-form').validate().form()) {
                        	login();
                        }
                        return false;
                    }
                });
            }
            
            jQuery('#forget-password').click(function() {
                jQuery('.login-form').hide();
                jQuery('.forget-form').show();
            });

            jQuery('#back-btn').click(function() {
                jQuery('.login-form').show();
                jQuery('.forget-form').hide();
            });
            
            var login = function(){
            	$.post("./DoLogin",{"username":$("#username").val(),"password":$("#password").val()},function(data){
                	if(data==1){
                		location.href="<%=redirect==""?"./index":redirect %>";
                	}else{
                		$('#loginfailed', $('.login-form')).show();
                	}
            	});
            }
            
            var changeLanguage = function(select){
            	location.href="./login?locale="+$(select).val();
            }
            	
        </script>
        <script>
			$(function(){
			  var canvas = document.querySelector('canvas'),
			      ctx = canvas.getContext('2d')
			  canvas.width = window.innerWidth;
			  canvas.height = window.innerHeight;
			  ctx.lineWidth = .3;
			  ctx.strokeStyle = (new Color(150)).style;
			
			  var mousePosition = {
			    x: 30 * canvas.width / 100,
			    y: 30 * canvas.height / 100
			  };
			
			  var dots = {
			    nb: 750,
			    distance: 50,
			    d_radius: 100,
			    array: []
			  };
			
			  function colorValue(min) {
			    return Math.floor(Math.random() * 255 + min);
			  }
			  
			  function createColorStyle(r,g,b) {
			    return 'rgba(' + r + ',' + g + ',' + b + ', 0.8)';
			  }
			  
			  function mixComponents(comp1, weight1, comp2, weight2) {
			    return (comp1 * weight1 + comp2 * weight2) / (weight1 + weight2);
			  }
			  
			  function averageColorStyles(dot1, dot2) {
			    var color1 = dot1.color,
			        color2 = dot2.color;
			    
			    var r = mixComponents(color1.r, dot1.radius, color2.r, dot2.radius),
			        g = mixComponents(color1.g, dot1.radius, color2.g, dot2.radius),
			        b = mixComponents(color1.b, dot1.radius, color2.b, dot2.radius);
			    return createColorStyle(Math.floor(r), Math.floor(g), Math.floor(b));
			  }
			  
			  function Color(min) {
			    min = min || 0;
			    this.r = colorValue(min);
			    this.g = colorValue(min);
			    this.b = colorValue(min);
			    this.style = createColorStyle(this.r, this.g, this.b);
			  }
			
			  function Dot(){
			    this.x = Math.random() * canvas.width;
			    this.y = Math.random() * canvas.height;
			
			    this.vx = -.5 + Math.random();
			    this.vy = -.5 + Math.random();
			
			    this.radius = Math.random() * 2;
			
			    this.color = new Color();
			    console.log(this);
			  }
			
			  Dot.prototype = {
			    draw: function(){
			      ctx.beginPath();
			      ctx.fillStyle = this.color.style;
			      ctx.arc(this.x, this.y, this.radius, 0, Math.PI * 2, false);
			      ctx.fill();
			    }
			  };
			
			  function createDots(){
			    for(i = 0; i < dots.nb; i++){
			      dots.array.push(new Dot());
			    }
			  }
			
			  function moveDots() {
			    for(i = 0; i < dots.nb; i++){
			
			      var dot = dots.array[i];
			
			      if(dot.y < 0 || dot.y > canvas.height){
			        dot.vx = dot.vx;
			        dot.vy = - dot.vy;
			      }
			      else if(dot.x < 0 || dot.x > canvas.width){
			        dot.vx = - dot.vx;
			        dot.vy = dot.vy;
			      }
			      dot.x += dot.vx;
			      dot.y += dot.vy;
			    }
			  }
			
			  function connectDots() {
			    for(i = 0; i < dots.nb; i++){
			      for(j = 0; j < dots.nb; j++){
			        i_dot = dots.array[i];
			        j_dot = dots.array[j];
			
			        if((i_dot.x - j_dot.x) < dots.distance && (i_dot.y - j_dot.y) < dots.distance && (i_dot.x - j_dot.x) > - dots.distance && (i_dot.y - j_dot.y) > - dots.distance){
			          if((i_dot.x - mousePosition.x) < dots.d_radius && (i_dot.y - mousePosition.y) < dots.d_radius && (i_dot.x - mousePosition.x) > - dots.d_radius && (i_dot.y - mousePosition.y) > - dots.d_radius){
			            ctx.beginPath();
			            ctx.strokeStyle = averageColorStyles(i_dot, j_dot);
			            ctx.moveTo(i_dot.x, i_dot.y);
			            ctx.lineTo(j_dot.x, j_dot.y);
			            ctx.stroke();
			            ctx.closePath();
			          }
			        }
			      }
			    }
			  }
			
			  function drawDots() {
			    for(i = 0; i < dots.nb; i++){
			      var dot = dots.array[i];
			      dot.draw();
			    }
			  }
			
			  function animateDots() {
			    ctx.clearRect(0, 0, canvas.width, canvas.height);
			    moveDots();
			    connectDots();
			    drawDots();
			
			    requestAnimationFrame(animateDots);	
			  }
			
			  $('canvas').on('mousemove', function(e){
			    mousePosition.x = e.pageX;
			    mousePosition.y = e.pageY;
			  });
			
			  $('canvas').on('mouseleave', function(e){
			    mousePosition.x = canvas.width / 2;
			    mousePosition.y = canvas.height / 2;
			  });
			
			  createDots();
			  requestAnimationFrame(animateDots);	
			});
		</script>
    </body>
</html>