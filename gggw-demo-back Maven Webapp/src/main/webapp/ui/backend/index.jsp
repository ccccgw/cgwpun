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

        <!-- DataTables CSS -->
        <link href="${contextPath }/vendor/datatables/dataTables.bootstrap.css" rel="stylesheet">
        <link href="${contextPath }/vendor/datatables/dataTables.responsive.css" rel="stylesheet">
        <!-- DataTables JavaScript -->
        <script src="${contextPath }/vendor/datatables/jquery.dataTables.min.js"></script>
        <script src="${contextPath }/vendor/datatables/dataTables.bootstrap.min.js"></script>
        <script src="${contextPath }/vendor/datatables/dataTables.responsive.js"></script>

        <!-- bootstrap-dialog  -->
        <link href="${contextPath }/vendor/dialog/bootstrap-dialog.css" rel="stylesheet">
        <script src="${contextPath }/vendor/dialog/bootstrap-dialog.js"></script>
        
        <!-- datetimepicker -->
		<script src="${contextPath }/vendor/datetimepicker/bootstrap-datetimepicker.js" charset="UTF-8"></script>
        <script src="${contextPath }/vendor/datetimepicker/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
        <link href="${contextPath }/vendor/datetimepicker/bootstrap-datetimepicker.min.css" rel="stylesheet">
        
        <!-- Custom Theme JavaScript -->
        <script src="${contextPath }/vendor/js/sb-admin-2.js"></script>
        <script src="${contextPath }/vendor/js/constant.js"></script>
        <script src="${contextPath }/vendor/js/common.js"></script>
        
        <!-- zTree -->
        <link href="${contextPath }/vendor/zTree/zTreeStyle.css" rel="stylesheet">
        <script src="${contextPath }/vendor/zTree/jquery.ztree.all.min.js"></script>
</head>
  
  <body>
		<div id="wrapper">

        <!-- Navigation -->
        <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
			<div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">A wanderer isn't always lost</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="${contextPath }/toLogin">A wanderer isn't always lost</a>
            </div>
            <!-- /.navbar-header -->
            
            <%@ include file="include/top.jsp"%>
            <!-- /.navbar-top-links -->
            
            <%@ include file="include/left.jsp"%>
            <!-- /.navbar-static-side -->
            
        </nav>

        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Dashboard</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-3 col-md-6">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-comments fa-5x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div class="huge">26</div>
                                    <div>New Comments!</div>
                                </div>
                            </div>
                        </div>
                        <a href="#">
                            <div class="panel-footer">
                                <span class="pull-left">View Details</span>
                                <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                <div class="clearfix"></div>
                            </div>
                        </a>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6">
                    <div class="panel panel-green">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-tasks fa-5x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div class="huge">12</div>
                                    <div>New Tasks!</div>
                                </div>
                            </div>
                        </div>
                        <a href="#">
                            <div class="panel-footer">
                                <span class="pull-left">View Details</span>
                                <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                <div class="clearfix"></div>
                            </div>
                        </a>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6">
                    <div class="panel panel-yellow">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-shopping-cart fa-5x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div class="huge">124</div>
                                    <div>New Orders!</div>
                                </div>
                            </div>
                        </div>
                        <a href="#">
                            <div class="panel-footer">
                                <span class="pull-left">View Details</span>
                                <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                <div class="clearfix"></div>
                            </div>
                        </a>
                    </div>
                </div>
                <div class="col-lg-3 col-md-6">
                    <div class="panel panel-red">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-3">
                                    <i class="fa fa-support fa-5x"></i>
                                </div>
                                <div class="col-xs-9 text-right">
                                    <div class="huge">13</div>
                                    <div>Support Tickets!</div>
                                </div>
                            </div>
                        </div>
                        <a href="#">
                            <div class="panel-footer">
                                <span class="pull-left">View Details</span>
                                <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                                <div class="clearfix"></div>
                            </div>
                        </a>
                    </div>
                </div>
            </div>
            <!-- /.row -->

                <!-- /.col-lg-8 -->

                    <!-- /.panel .chat-panel -->

                <!-- /.col-lg-4 -->

            <!-- /.row -->
        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->
  </body>
  <script>
  		$(function(){
  			getAllDict();
  			
  			$(window).bind('hashchange', function () {
	    		hashChange();
			}); 
			
			$('#side-menu a').click(function(){
				$('#side-menu a').removeClass("active");
    			$(this).addClass("active"); 
			});	
  		});
  
  		//使用ajax的方式跳转  避免重复刷新菜单和顶部栏
  		function toPage(obj, url, params) {
  			//如果指向地址为空
  		    if (!url) {
  		    	return;
  		    }
  			completeUrl = "${contextPath }" + url;
  			var title = obj.innerText;
  			if (!title) {
  				title = obj;
  			}
  			$("#page-wrapper").html('<h1>页面加载中...</h1>');
  			$.ajax({
  				type : "GET",
  				url : completeUrl+"?rnd=" + new Date().getTime(),
  				dataType : "text",
  				data : params,
         		success : function(data) {
         			//修改title
         			$(document).attr("title", title);
         			//修改地址
         			history.pushState(null, title ,"#!" + url + ";@" + title);
         			$("#page-wrapper").html(data);
         		}
         	});
  		}
  </script>
</html>
