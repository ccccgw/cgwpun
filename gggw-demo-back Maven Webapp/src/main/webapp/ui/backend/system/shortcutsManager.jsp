<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!-- page-wrapper  start -->

		<div class="row">
            <div class="col-lg-12">
                <h3 class="page-header text-primary">快捷操作
                </h3>
            </div>
            <!-- /.col-lg-12 -->
        </div>

	        <!-- /.row -->
            <div class="row">
                <div class="col-lg-6">
                    <!-- /.panel -->
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            	请点击以下按钮进行快捷操作
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <button type="button" id="shutdown" class="btn btn-default btn-circle btn-xl"><i class="fa fa-power-off"></i>
                            </button>
                            <button type="button" id="reboot" class="btn btn-primary btn-circle btn-xl"><i class="fa fa-rotate-right"></i>
                            </button>
                            <button type="button" id="lock" class="btn btn-success btn-circle btn-xl"><i class="fa fa-lock"></i>
                            </button>
                            <button type="button" id="unlock" class="btn btn-info btn-circle btn-xl"><i class="fa fa-unlock"></i>
                            </button>
                            <button type="button" id="bomb" class="btn btn-danger btn-circle btn-xl"><i class="fa fa-bomb"></i>
                            </button>
                            <button type="button" id="piper" class="btn btn-warning btn-circle btn-xl"><i class="fa fa-pied-piper-alt"></i>
                            </button>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
            </div>
            <!-- /.row -->
         

<!-- page-wrapper   end -->

<script>

    $(document).ready(function() {
    
    	$("#shutdown").click(function(){
	    	showConfirm("确定要关机吗？", function(){
	    		BootstrapDialog.show({
            		title: '关闭计算机',
            		message: '正在关机中......请稍后'
        		});
        		$.post("${contextPath }/ajaxShutdown?rnd=" + new Date().getTime());
	    	})
    	});
    	
    	$("#reboot").click(function(){
	    	showConfirm("确定要重启吗？", function(){
	    		BootstrapDialog.show({
            		title: '重启计算机',
            		message: '正在重启中......请稍后'
        		});
        		$.post("${contextPath }/ajaxReboot?rnd=" + new Date().getTime());
	    	})
    	});
    	
    	$("#lock").click(function(){
	    	showConfirm("确定要锁定吗？", function(){
	    		BootstrapDialog.show({
            		title: '锁定计算机',
            		message: '正在锁定中......请稍后'
        		});
        		$.post("${contextPath }/ajaxLock?rnd=" + new Date().getTime());
	    	})
    	});
    	
    	$("#unlock").click(function(){
	    	showConfirm("确定要解锁吗？", function(){
	    		BootstrapDialog.show({
            		title: '解锁计算机',
            		message: '正在解锁中......请稍后'
        		});
        		$.post("${contextPath }/ajaxUnlock?rnd=" + new Date().getTime());
	    	})
    	});
    	
    	$("#bomb").click(function(){
	    	showConfirm("确定要爆炸吗？", function(){
	    		BootstrapDialog.show({
            		title: '爆炸计算机',
            		message: '正在爆炸中......请稍后'
        		});
        		$.post("${contextPath }/ajaxBomb?rnd=" + new Date().getTime());
	    	})
    	});
    	
    	$("#piper").click(function(){
	    	showConfirm("确定要吹箫吗？", function(){
	    		BootstrapDialog.show({
            		title: '吹箫...',
            		message: '正在吹箫中......请稍后'
        		});
        		$.post("${contextPath }/ajaxPiper?rnd=" + new Date().getTime());
	    	})
    	});
    	 
    });
</script>