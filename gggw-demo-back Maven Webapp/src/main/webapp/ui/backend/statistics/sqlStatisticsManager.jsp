<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />


<script src="${contextPath }/vendor/echarts/echarts.min.js"></script>

<!-- page-wrapper  start -->

		<div class="row">
            <div class="col-lg-12">
                <h3 class="page-header text-primary">sql统计实时统计（使用最多的10条）
                	
                </h3>
            </div>
            <!-- /.col-lg-12 -->
        </div>

	    <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                            <ol class="breadcrumb">
                                <li><a href="">统计视图</a></li>
                                <li><a href="#" onclick="toPage(this, '/toSQLStatistics', '')">sql统计</a></li>
                            </ol>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
							<div class="row">
                                <div class="col-lg-6">
                                	<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    								<div id="sqlViewBar" style="width: 100%;height:70%;"></div>
                                </div>      
                                <div class="col-lg-6">
                                	<!-- sql语句在这里显示 -->
    								<div id="sqlBar" ></div>
                                </div>       
                            </div>   
                            
                            <div class="row">
                                  <div class="col-lg-6">
                                	<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    								<div id="sqlViewLine" style="width: 100%;height:70%;"></div>
                                </div>      
                                <div class="col-lg-6">
                                	<!-- sql语句在这里显示  -->
    								<div id="sqlLine" ></div>
                                </div>           
                            </div>                         
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
         

<!-- page-wrapper   end -->

<script>
		var sqlList = [];
		var allData = [];
		
        // 基于准备好的dom，初始化echarts实例
        var sqlViewBar = echarts.init(document.getElementById('sqlViewBar'));

		// 显示标题，图例和空的坐标轴
		sqlViewBar.setOption({
		    title: {
		        text: 'sql统计实时统计'
		    },
		    tooltip: {},
		    toolbox: {
		    		show:true,
		    		feature: {
						    magicType: {
						        type: ['line', 'bar']
						    },
						    dataView: {
						    	show: true
						    }
						}
		    },
		    legend: {
		        data:['sql语句']
		    },
		    xAxis: {
		        data: []
		    },
		    yAxis: {},
		    series: [{
		        name: 'sql语句',
		        type: 'line',
		        data: []
		    }]
		});

     	
		
		function renderView() {
			// 异步加载数据
			$.get('${contextPath }/druid/sql.json?orderBy=ExecuteCount&orderType=asc&page=1&perPageCount=10&').done(function (data) {
				data = jQuery.parseJSON(data);
				allData = data;
				var nameList = [];
				var dataList = [];
				$.each(data.Content, function(index, item) {
					nameList[index] = "NO" + (index+1);
					sqlList[index] 	= item.SQL;
					dataList[index] = item.ExecuteCount;
				});
			    // 填入数据
			    sqlViewBar.setOption({
			        xAxis: {
			            data: nameList
			        },
			        series: [{
			            // 根据名字对应到相应的系列
			            name: 'sql语句',
			            data: dataList
			        }]
			    });
			});
		}
		
		//定时任务每秒刷新
		setInterval("renderView()", 5 * 1000);
		
		sqlViewBar.on('click', function (params) {
				 var index = parseInt(params.name.substr(2));
   				 $('#sqlBar').html(sqlList[index]);
		});
    
</script>