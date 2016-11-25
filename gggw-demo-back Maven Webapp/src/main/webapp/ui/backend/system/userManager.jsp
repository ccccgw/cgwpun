<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!-- page-wrapper  start -->

		<div class="row">
            <div class="col-lg-12">
                <h3 class="page-header text-primary">用户管理
                	<button type="button" class="btn btn-outline btn-primary btn-sm pull-right"  onclick="toPage(this, '/toUserEdit', { operatType: '0' })" >添加用户</button>
                </h3>
            </div>
            <!-- /.col-lg-12 -->
        </div>

	    <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                            <ol class="breadcrumb">
                                <li><a href="">系统设置</a></li>
                                <li><a href="#" onclick="toPage(this, '/toUser', '')">用户管理</a></li>
                            </ol>
                        <!-- /.panel-heading -->
                        <div class="panel-body">

                            <div class="well">
                                <form class="form-inline" role="form" id="searchForm">
                                    <div class="form-group">
                                        <label >用户编号：</label>
                                        <input type="text" class="form-control" id="userNo" name="userNo" placeholder="请输入用户编号">
                                    </div>
                                    <div class="form-group">
                                        <label >姓名：</label>
                                        <input type="text" class="form-control" id="userName" name="userName" placeholder="请输入姓名">
                                    </div>
                                    <div class="form-group">
                                        <label >邮箱：</label>
                                        <input type="email" class="form-control" id="email" name="email" placeholder="请输入邮箱地址">
                                    </div>
                                    <div class="form-group">
                                        <label >性别：</label>
                                        <select class="form-control" id="gender" name="gender">
                                            <option selected value="">全部</option>
                                            <c:forEach items="${genderList}" var="item">
                                            	<option value="${item.subEntry}">${item.dictPrompt}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label >状态：</label>
                                        <select class="form-control" id="status" name="status">
                                            <option selected value="">全部</option>
                                            <c:forEach items="${statusList}" var="item">
                                            	<option value="${item.subEntry}">${item.dictPrompt}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label >创建日期（开始）：</label>
                                        <div class="input-group date form_date" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
						                    <input class="form-control" type="text" value="" id="createDateStart" name="createDateStart">
											<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
						                </div>
                                    </div>
                                    <div class="form-group">
                                        <label >创建日期（结束）：</label>
                                        <div class="input-group date form_date" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
						                    <input class="form-control" type="text" value="" id="createDateEnd" name="createDateEnd">
											<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
						                </div>
                                    </div>
                                    <button type="button" class="btn btn-primary btn-sm" id="searchBtn"><i class="fa fa-search"></i>  查询</button>
                                    <button type="reset" class="btn btn-default btn-sm"><i class="fa fa-search"></i>  重置</button>
                                </form>
                            </div>

                            <table width="100%" class="table table-striped table-bordered table-hover" id="userList">
                                <thead>
                                    <tr>
                                        <th>用户编号</th>
                                        <th>姓名</th>
                                        <th>手机号</th>
                                        <th>邮箱</th>
                                        <th>用户状态</th>
                                        <th>操作</th>
                                    </tr>
                                </thead>
                                <tbody>

                                </tbody>
                            </table>
                            <!-- /.table-responsive -->
                            
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
         

<!-- page-wrapper   end -->

<script>

    $(document).ready(function() {
    	initDatetimepicker($('.form_date'));
    	
        // API:     http://datatables.club/reference/option/
        // EXAMPLE: http://datatables.club/example/user_share/mir/

        var $table = $('#userList');
        var _table = $table.DataTable($.extend(true,{},CONSTANT.DATA_TABLES.DEFAULT_OPTION ,{
            stripeClasses   :       ["odd", "even"],        //为奇偶行加上样式，兼容不支持CSS伪类的场合
            serverSide      :       true,                  //启用服务器端分页
            renderer        :       "bootstrap",            //渲染样式：Bootstrap和jquery-ui
            searching       :       false,                 //禁用原生搜索
            processing		: 		true,  					//加载提示
            //order			: 		[],          			//取消默认排序查询,否则复选框一列会出现小箭头
            ordering		: 		false,					//这地方true的话css冲突了.sorting这个class冲突 
           	scrollY			: 		500,					//表格高度，去掉的话   会自适应。
            ajax            :        function(data, callback, settings) {
                    var searchForm =  $("#searchForm").serializeArray();
                    //组装分页参数
                    var PageForm = buildPageForm(data);
                    $.ajax({
                        url : "${contextPath }/ajaxUserList?rnd=" + new Date().getTime() + PageForm,
                        type: "POST",
                        data: searchForm,
                        success: function(result) {
                                //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                                //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                                callback(result);
                        },
                        error: function(XMLHttpRequest, textStatus, errorThrown) {
                            showError("查询失败");
                        }
                    });
            },
            columns         :       [
                { "data": "userNo" },
                { "data": "userName" },
                { "data": "mobile" },
                { "data": "email" },
                { "data": "status" , 
                	render : function(data,type, row, meta) {
                    return '<i class="fa fa-male"></i> ' + getDictCaption("1000", data);
                	} 
                },
                { "data":  null }
            ],
            createdRow      :       function ( row, data, index ) {
                //给当前行加样式
                /*
                if (data.userNo=="000003") {
                    $(row).addClass("success");
                }*/
                //给当前行某列加样式
                $('td', row).eq(4).addClass( (data.status == 8) ? "text-success":"text-danger");
                //不使用render，改用jquery文档操作呈现单元格
                var $btnEdit =   $('<button type="button" class="btn btn-sm btn-primary btn-edit " >修改</button>  ');
                var $btnDel  =   $('<button type="button" class="btn btn-sm btn-danger btn-del " >删除</button>');
                $('td', row).eq(5).text("");
                $('td', row).eq(5).append($btnEdit).append("  ").append($btnDel);
            }
        }));
        
         //行点击事件
	    $("tbody",$table).on("click","tr",function(event) {
	        $(this).addClass("info").siblings().removeClass("info");
	    });

        //点击删除按钮
        $table.on("click",".btn-del",function() {
            var item = _table.row($(this).closest('tr')).data();
            $(this).closest('tr').addClass("info").siblings().removeClass("info");
            Manage.del(_table,"${contextPath }/ajaxUserDel", item);
        });

        //点击编辑按钮
        $table.on("click",".btn-edit",function() {
            var item = _table.row($(this).closest('tr')).data();
            $(this).closest('tr').addClass("info").siblings().removeClass("info");
            Manage.edit(_table,"/toUserEdit", item);
        });

        //点击搜索按钮
        $("#searchBtn").click(function(){
            searchForm = $("#searchForm").serializeArray();
            _table.draw();
        });
    });
</script>