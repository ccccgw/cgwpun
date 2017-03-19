<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!-- DataTables CSS -->
<link href="${contextPath }/vendor/datatables/dataTables.bootstrap.css" rel="stylesheet">
<link href="${contextPath }/vendor/datatables/dataTables.responsive.css" rel="stylesheet">
<!-- DataTables JavaScript -->
<script src="${contextPath }/vendor/datatables/jquery.dataTables.min.js"></script>
<script src="${contextPath }/vendor/datatables/dataTables.bootstrap.min.js"></script>
<script src="${contextPath }/vendor/datatables/dataTables.responsive.js"></script>

<script src="${contextPath }/vendor/js/validate.js"></script>


<!-- page-wrapper  start -->

		<div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header text-primary"><c:if test="${operatType == '0' }">上传图片</c:if><c:if test="${operatType == '1' }">修改图片</c:if> </h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div>
                            <ol class="breadcrumb">
							  <li><a href="">上传管理</a></li>
							  <li><a href="#" onclick="toPage(this, '/upload/toImgUpload', '')">图片上传管理</a></li>
							  <li class="active"><c:if test="${operatType == '0' }">上传图片</c:if><c:if test="${operatType == '1' }">修改图片</c:if></li>
							</ol>
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-lg-6">
                                    <form role="form" id="imgUploadForm">
                                        <div class="form-group has-feedback">
                                            <label>用户编号</label>
                                            <input class="form-control" placeholder="请输入用户编号" name="userNo" id="userNo" valid="NotBlank" value="${baseSysUser.userNo}">
                                            <span class="glyphicon  form-control-feedback"></span>
                                            <span class="text-danger">（*必填项）</span>
                                        </div>
                                        <c:if test="${operatType == '0' }">
                                        <div class="form-group has-feedback">
                                            <label>密码</label>
                                            <input type="password"   class="form-control" placeholder="请输入密码" name="userPwd" id="userPwd"  valid="NotBlank" value="">
                                            <span class="glyphicon  form-control-feedback"></span>
                                            <p class="text-danger">（*必填项）</p>
                                        </div>
                                        <div class="form-group has-feedback">
                                            <label>重复密码</label>
                                            <input type="password"  class="form-control" placeholder="请重复输入密码" name="reUserPwd" id="reUserPwd" valid="NotBlank">
                                            <span class="glyphicon  form-control-feedback"></span>
                                            <p class="text-danger">（*必填项）</p>
                                        </div>
                                        </c:if>
                                        <div class="form-group has-feedback">
                                            <label>姓名</label>
                                            <input class="form-control" placeholder="请输入姓名" name="userName" id="userName"  valid="NotBlank" value="${baseSysUser.userName}">
                                            <span class="glyphicon  form-control-feedback"></span>
                                            <p class="text-danger">（*必填项）</p>
                                        </div>
                                        <div class="form-group" >
                                            <label>性别</label>
                                            <select class="form-control" name="gender" id="gender">
                                                <c:forEach items="${genderList}" var="item">
                                            		<option value="${item.subEntry}"  <c:if test="${item.subEntry == baseSysUser.gender }">selected</c:if> >${item.dictPrompt}</option>
                                            	</c:forEach>
                                            </select>
                                        </div>
                                        <div class="form-group has-feedback">
                                            <label>手机号码</label>
                                            <input class="form-control" placeholder="请输入手机号码" name="mobile" id="mobile"  valid="NotBlank" value="${baseSysUser.mobile}">
                                        </div>

                                        <div class="form-group has-feedback">
                                            <label>邮箱</label>
                                            <input class="form-control" placeholder="请输入邮箱" name="email" id="email"  valid="NotBlank" value="${baseSysUser.email}">
                                            <span class="glyphicon  form-control-feedback"></span>
                                            <p class="text-danger">（*必填项）</p>
                                        </div>
                                        <div class="form-group">
                                            <label>状态</label>
                                            <select class="form-control" name="status" id="status">
                                                <c:forEach items="${statusList}" var="item">
                                            		<option value="${item.subEntry}" <c:if test="${item.subEntry == baseSysUser.status }">selected</c:if> >${item.dictPrompt}</option>
                                            	</c:forEach>
                                            </select>
                                        </div>
                                        <div class="form-group has-feedback" name="roleIds">
                                            <div><label>请选择角色</label></div>
                                            <c:forEach items="${roleList}" var="item">
                                            	<label class="checkbox-inline">
                                                	<input type="checkbox" name="roleId" value="${item.roleId}" <c:if test="${fn:contains(baseSysUser.roleId, item.roleId)}">checked</c:if>>${item.roleName}
                                            	</label>
                                            </c:forEach>
                                        </div>

										<div hidden>
											<input name="operatType" value="${operatType}" >
										</div>
										
										<div hidden>
											<input name="userId" value="${baseSysUser.userId}" >
										</div>

                                        <button type="button" class="btn btn-primary" id="commit">提   交</button>
                                        <button type="button" class="btn btn-default" onclick="toPage(this, '/upload/toImgUpload', '')">取   消</button>
                                    </form>
                                </div>
                                <!-- /.col-lg-6 (nested) -->
                                
                                <!-- /.col-lg-6 (nested) -->
                            </div>
                            <!-- /.row (nested) -->
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>

<!-- page-wrapper   end -->

<script>
	$("#commit").click(function() {
		debugger;
		
		var email = $("#email").val();
		var userPwd = $("#userPwd").val();
		var reUserPwd = $("#reUserPwd").val();

		if (!checkNotBlank($("#userForm"))) {
			return;
		}

		if (userPwd != reUserPwd) {
			checkErrorHandle($("#reUserPwd"));
			showError("两次密码输入不一致！");
			return;
		}

		if (!(validateEmail(email) == "success")) {
			checkErrorHandle($("#email"));
			showError(validateEmail(email));
			return;
		}
		debugger;
		var url = "${contextPath }/ajaxUserEdit"
		var params = $("#imgUploadForm").serializeArray();

        $.ajax({
           type : "post" ,
           data : params,
           url : url,
           success: function(data) {
               if (data.error_no == "0") {
                   showError(data.error_info);
               } else {
                   showError(data.error_info);
               }
           }
        });

	});
</script>
