<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html>

<head>
    	<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<title>Yeah~~~~</title>
		<!-- Bootstrap Core CSS -->
		<link href="${contextPath }/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">		
		<!-- MetisMenu CSS -->
        <link href="${contextPath }/vendor/metisMenu/metisMenu.min.css" rel="stylesheet">
		<!-- Custom CSS -->
		<link href="${contextPath }/vendor/css/sb-admin-2.css" rel="stylesheet">		
		<!-- Morris Charts CSS 
		<link href="${contextPath }/vendor/morrisjs/morris.css" rel="stylesheet">-->	
		<!-- Custom Fonts -->
		<link href="${contextPath }/vendor/font-awesome/css/font-awesome.min.css"
					rel="stylesheet" type="text/css">
		
		<!-- jQuery -->
		<script src="${contextPath }/vendor/jquery/jquery.min.js"></script>		
		<!-- Bootstrap Core JavaScript -->
		<script src="${contextPath }/vendor/bootstrap/js/bootstrap.min.js"></script>		
		<!-- Metis Menu Plugin JavaScript -->
        <script src="${contextPath }/vendor/metisMenu/metisMenu.min.js"></script>
		<!-- Morris Charts JavaScript 
		    <script src="../vendor/raphael/raphael.min.js"></script>
		    <script src="../vendor/morrisjs/morris.min.js"></script>
		    <script src="../data/morris-data.js"></script>-->		
		<!-- Custom Theme JavaScript -->
		<script src="${contextPath }/vendor/js/sb-admin-2.js"></script>
		<script src="${contextPath }/vendor/js/constant.js"></script>
        <script src="${contextPath }/vendor/js/common.js"></script>
        
        <!-- bootstrap-dialog  -->
        <link href="${contextPath }/vendor/dialog/bootstrap-dialog.css" rel="stylesheet">
        <script src="${contextPath }/vendor/dialog/bootstrap-dialog.js"></script>

</head>

<body>

       <div class="container">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">

                <!-- login start-->
                <div class="login-panel panel panel-primary"  id="login-panel">
                    <div class="panel-heading text-center">
                        <h3 class="panel-title">登 录</h3>
                    </div>
                    <div class="panel-body">

                        <h3 class="page-header text-center text-info" style="margin-top: 0px">请输入用户名密码</h3>

                        <form role="loginForm" id="loginForm">
                            <fieldset>
                                <div class="form-group has-feedback">
                                    <input class="form-control" placeholder="请输入用户名" name="userNo" type="text" valid="NotBlank" autofocus>
                                    <span class="glyphicon  form-control-feedback"></span>
                                </div>
                                <div class="form-group has-feedback">
                                    <input class="form-control" placeholder="请输入密码" name="userPwd" type="password" valid="NotBlank">
                                    <span class="glyphicon  form-control-feedback"></span>
                                </div>
                                <div class="input-group has-feedback">
                                	<input class="form-control" placeholder="请输入验证码" name="verifyCode" type="text" valid="NotBlank">
                                	<div class="input-group-btn">
    									<img id="imageCode" src="${contextPath }/static/image/code.png"> 
  									</div>                               	                           	
                                </div>                                
                                <div class="checkbox">
                                    <label>
                                        <input name="remember" type="checkbox" value="Remember Me">记住我
                                    </label>
                                    <!-- Change this to a button or input when using this as a form -->
                                    <a href="#" class="btn btn-primary pull-right" id="loginButton">登录</a>
                                </div>

                            </fieldset>
                        </form>
                    </div>
                    <div class="panel-footer">
                        <a href="#" class="toForgotPWD" ><i class="fa fa-arrow-left fa-fw"></i> 忘记密码</a>
                        <a href="#" class="pull-right toRegist">现在注册 <i class="fa fa-arrow-right fa-fw"></i></a>
                    </div>
                </div>
                <!-- login end-->

                <!-- regist start-->
                <div class="login-panel panel panel-green" hidden id="regist-panel">
                    <div class="panel-heading text-center">
                        <h3 class="panel-title">注 册</h3>
                    </div>
                    <div class="panel-body">

                        <h3 class="page-header text-center text-success" style="margin-top: 0px">新用户注册</h3>

                        <form role="registForm">
                            <fieldset>
                                <div class="form-group">
                                    <input class="form-control" placeholder="请输入用户名" name="email" type="email" autofocus>
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="请输入用密码" name="password" type="password" value="">
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="" name="email" type="email" autofocus>
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="" name="password" type="password" value="">
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="" name="email" type="email" autofocus>
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="" name="password" type="password" value="">
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="" name="email" type="email" autofocus>
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="" name="password" type="password" value="">
                                </div>
                                <div class="form-group">
                                    <!-- Change this to a button or input when using this as a form -->
                                    <button type="reset" class="btn btn-default" id="return">重置</button>
                                    <a href="#" class="btn btn-success pull-right" id="registButton">注册</a>
                                </div>

                            </fieldset>
                        </form>
                    </div>
                    <div class="panel-footer text-center">
                        <a href="#" class="text-success toLogin"><i class="fa fa-arrow-left fa-fw"></i> 返回登录</a>
                    </div>
                </div>
                <!-- regist end-->

                <!-- forgotPWD start-->
                <div class="login-panel panel panel-red" hidden id="forgotPWD-panel">
                    <div class="panel-heading text-center">
                        <h3 class="panel-title">忘记密码</h3>
                    </div>
                    <div class="panel-body">

                        <h3 class="page-header text-center text-danger" style="margin-top: 0px">请输入您的邮箱</h3>

                        <form role="forgotPWDForm">
                            <fieldset>
                                <div class="form-group">
                                    <input class="form-control" placeholder="请输入用户名" name="email" type="email" autofocus>
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="请输入用密码" name="password" type="password" value="">
                                </div>
                                <div class="form-group">
                                    <!-- Change this to a button or input when using this as a form -->
                                    <a href="#" class="btn btn-danger pull-right" id="forgotPWDButton">发送</a>
                                </div>

                            </fieldset>
                        </form>
                    </div>
                    <div class="panel-footer text-center">
                        <a href="#" class="text-danger toLogin">返回登录 <i class="fa fa-arrow-right fa-fw"></i></a>
                    </div>
                </div>
                <!-- forgotPWD end-->

            </div>
        </div>
    </div>

</body>
<script>
    $(function() {
    	debugger;
        $(".toLogin").click(function(){
            $(".panel").hide();
            $("#login-panel").show();
        });
        $(".toRegist").click(function(){
            $(".panel").hide();
            $("#regist-panel").show();
        });
        $(".toForgotPWD").click(function(){
            $(".panel").hide();
            $("#forgotPWD-panel").show();
        });

		//加载完成获取验证码
		$("#imageCode").attr("src", "imageCode.img?d=" + new Date().getTime());
    });
    
	//点击获取验证码
	$("#imageCode").click(function() {
		$("#imageCode").attr("src", "imageCode.img?d=" + new Date().getTime());
	});
	
	$("#loginButton").click(function() {
		if (!checkNotBlank($("#loginForm"))) {
			return;
		}
		var url = "${contextPath }/ajaxSubmitLogin"
		var params = $("#loginForm").serializeArray();
		$.ajax({
           type : "post" ,
           data : params,
           url : url,
           success: function(data) {
               if (data.error_no == "0") {
                   window.location.href="${contextPath }/toHomeA"; 
               } else {
                   showError(data.error_info);
               }
           }
        });
	});
</script>

</html>
