<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>">
	<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

	<%--bootstrap时间插件--%>
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
	<%--bootstrap分页插件--%>
<link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">
<script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
<script type="text/javascript" src="jquery/bs_pagination/en.js"></script>

<script type="text/javascript">

	$(function(){

		$(".time").datetimepicker({
			minView: "month",
			language:  'zh-CN',
			format: 'yyyy-mm-dd',
			autoclose: true,
			todayBtn: true,
			pickerPosition: "bottom-left"
		});

		//	市场活动创建按钮单击事件
		$("#addBtn").click(function () {
			//	清空select中的选项
			$("#create-marketActivityOwner").find("option:selected").text("");
			$("#create-marketActivityOwner").empty();

			/*
			* 打开模态窗口操作：获取模态窗口的jquery对象，调用modal方法，为该方法传递参数:show/hide(打开/关闭)
			* */
			//	获取后台用户数据
			//	发起ajax请求
			$.ajax({
				url : "workbench/activity/getUserList.do",
				data : {},
				type : "get",
				dataType : "json",
				success : function (data) {
					//	遍历userList
					$.each(data, function (index, content) {
						$("#create-marketActivityOwner").append("<option value='" + content.id + "'>" + content.name + "</option>")
					})
					var id = "${user.id}";

					$("#create-marketActivityOwner").val(id);
				}
			})

			//	数据加载完毕，打开模态窗口
			$("#createActivityModal").modal("show");
		})

		//	市场活动新建保存按钮单击事件
		$("#saveBtn").click(function () {
			//	获取后端所需市场活动信息

			var data = {
				"owner" : $.trim($("#create-marketActivityOwner").val()),
				"name" : $.trim($("#create-marketActivityName").val()),
				"startDate" : $.trim($("#create-startTime").val()),
				"endDate" : $.trim($("#create-endTime").val()),
				"cost" : $.trim($("#create-cost").val()),
				"description" : $.trim($("#create-describe").val())
			}
			//	发起保存市场活动的ajax请求
			$.ajax({
				url : "workbench/activity/save.do",
				data : data,
				type : "post",
				dataType : "json",
				success : function (data) {
					/*
					{"success":true/false}
					 */
					if (data.success) {
						alert("活动创建成功");
						// 刷新市场活动列表
						pageList(1, $("activityPage").bs_pagination('getOption', 'rowsPerpage'));
						//	清空添加模态窗口	此方法用不了
						// $("#activityAddForm").reset();
						$("#activityAddForm")[0].reset();

						// 关闭添加操作的模态窗口
						$("#createActivityModal").modal("hide");

					} else {
						alert(data.msg);
					}
				}
			})

		})

		//	市场活动删除按钮单击事件
		$("#delBtn").click(function () {
			//	获取当前选中的事件的id
			var $checked = $("input[name=select]:checked");
			if ($checked.length === 0) {

				alert("请选择需要删除的记录");

			} else {
				//	确认删除
				if (confirm("确定删除选中的操作吗？")) {
					//	使用url + id=value1&id=value2的方式传递
					var param = "";
					for (var i=0; i < $checked.length; i++) {
						param += "id=" + $($checked[i]).val() + "&";
					}
					param = param.substring(0, param.length-1);

					//	发起删除活动的ajax请求
					$.ajax({
						url : "workbench/activity/delete.do",
						data : param,
						type : "post",
						dataType : "json",
						success : function (data) {
							/*
                            {"success":true/false}
                             */
							if (data.success) {
								alert("删除活动成功");
								// 刷新市场活动列表
								pageList(1, $("activityPage").bs_pagination('getOption', 'rowsPerpage'));
							} else {
								alert("删除活动失败");
							}
						}
					})
				}
			}
			//	刷新市场活动表格
		})

		//	市场活动修改按钮单击事件
		$("#editBtn").click(function () {

			var $select = $("input[name=select]:checked");

			if ($select.length === 0) {
				alert("请选择需要修改的活动记录");
			} else if ($select.length > 1) {
				alert("一次只能修改一条活动记录");
			} else {
				//	获取需要修改的活动id
				var updateId = $select.val();
				$("#update-id").val(updateId);

				//	ajax查询所所需信息
				$.ajax({
					url : "workbench/activity/updateQuery.do",
					data : {
						"id" : updateId
					},
					type : "post",
					dataType : "json",
					success : function (data) {
						/*
                        {"uList":[{u1}, {u2}, ..., {}], "activity":{id:?, name:?, ...}}
                         */
						var html = "<option></option>";
						$.each(data.uList, function (i, n) {
							html += "<option value='" + n.id + "'>"+ n.name +"</option>";
						})
						$("#edit-owner").html(html);

						$("#edit-id").val(data.activity.owner);

						$("#edit-name").val(data.activity.name);
						$("#edit-startDate").val(data.activity.startDate);
						$("#edit-endDate").val(data.activity.endDate);
						$("#edit-cost").val(data.activity.cost);
						$("#edit-description").val(data.activity.description);
						var ownerId = data.activity.owner;
						$("#edit-owner").val(ownerId);	//	将活动owner关联用户下拉列表
					}
				})

				//	显示模态窗口
				$("#editActivityModal").modal("show");
			}
		})

		//	市场活动修改模态窗口更新按钮单击事件
		$("#updateBtn").click(function () {
			if (confirm("确定更新当前的活动信息吗？")) {
				//	获取模态窗口更新后的信息
				var data = {
					"id" : $("input[name=select]:checked").val(),
					"editBy" : "${user.name}",
					"owner" : $.trim($("#edit-owner").val()),
					"name" : $.trim($("#edit-name").val()),
					"startDate" : $.trim($("#edit-startDate").val()),
					"endDate" : $.trim($("#edit-endDate").val()),
					"cost" : $.trim($("#edit-cost").val()),
					"description" : $.trim($("#edit-description").val())
				}
				$.ajax({
					url : "workbench/activity/update.do",
					data : data,
					type : "post",
					dataType : "json",
					success : function (data) {
						/*
                        {"success":true/false}
                         */
						if (data.success) {
							//	刷新市场活动表格
							pageList($("activityPage").bs_pagination('getOption', 'currentPage'),
									 $("activityPage").bs_pagination('getOption', 'rowsPerpage'));

						} else {
							alert("活动修改失败");
						}
					}
				})
				$("#editActivityModal").modal("hide");

			}
		})

		//	活动表格复选框 全选单击事件
		$("#selectAll").click(function () {
			$("input[name=select]").prop("checked", this.checked);
		})

		//	动态生成的元素不可以通过普通事件绑定方式进行操作
		//	动态生成的元素需要使用on方法触发事件
		//	格式为$(需要绑定元素的有效外层元素).on(绑定事件的方式，需要绑定的jquery对象， 回调函数)
		$("#activityTable").on("click", $("input[name=select]"), function () {
			$("#selectAll").prop("checked", $("input[name=select]").length === $("input[name=select]:checked").length)
		})

		//	为查询按钮绑定单击事件
		$("#searchBtn").click(function () {

			//	将之前的条件搜索信息保存到隐藏域中
			$("#hidden-name").val($.trim($("#search-name").val()));
			$("#hidden-owner").val($.trim($("#search-owner").val()));
			$("#hidden-startDate").val($.trim($("#search-startDate").val()));
			$("#hidden-endDate").val($.trim($("#search-endDate").val()));

			pageList($("activityPage").bs_pagination('getOption', 'currentPage'),
					$("activityPage").bs_pagination('getOption', 'rowsPerpage'));
		})

		//	页面加载完毕后刷新市场活动表格
		pageList(1, 2);
	});


	/**
	 * 刷新市场活动列表函数
	 * @param pageNo 页码
	 * @param pageSize 每页展现的记录条数
	 * 调用pageList方法的时机：
	 * 		(1) 点击市场活动超链接，页面加载完毕后
	 * 		(2) 市场活动在添加、修改、删除之后需要自动刷新
	 * 		(3) 点击查询按钮时
	 * 		(4) 点击分页组件时
	 */
	function pageList(pageNo, pageSize) {
		//	将全选的复选框重置
		$("#selectAll").prop("checked", false);

		$("#search-name").val($.trim($("#hidden-name").val()));
		$("#search-owner").val($.trim($("#hidden-owner").val()));
		$("#search-startDate").val($.trim($("#hidden-startDate").val()));
		$("#search-endDate").val($.trim($("#hidden-endDate").val()));

		$.ajax({
			url : "workbench/activity/getActivityList.do",
			data : {
				"pageNo" : pageNo,
				"pageSize" : pageSize,
				"name" : $.trim($("#search-name").val()),
				"owner" : $.trim($("#search-owner").val()),
				"startDate" : $.trim($("#search-startDate").val()),
				"endDate" : $.trim($("#search-endDate").val())
			},
			type : "get",
			dataType : "json",
			success : function (data) {
				/*
					data包含:
						(1) List<Activity> [{活动1},{活动2},...,{活动n}]
						(2) {"total":100}
					data:{"total":100, "activityList":[{活动1},{活动2},...,{活动n}]}
				* */

				var html = "";
				$.each(data.activityList, function (i, n) {
					html += '<tr class="active">';
					html += '<td><input type="checkbox" name="select" value="'+ n.id +'"/></td>';
					html += '<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'workbench/activity/detail.do?id='+ n.id + '\';">'+n.name+'</a></td>';
					html += '<td>'+n.owner+'</td>';
					html += '<td>'+n.startDate+'</td>';
					html += '<td>'+n.endDate+'</td>';
					html += '</tr>';
				})
				$("#activityTable").html(html);

				//	计算总页数
				var totalPages = data.total%pageSize === 0? data.total/pageSize : parseInt(data.total/pageSize) + 1;

				//	数据处理完毕后进行分页操作
				$("#activityPage").bs_pagination({
					currentPage: pageNo, // 页码
					rowsPerPage: pageSize, // 每页显示的记录条数
					maxRowsPerPage: 20, // 每页最多显示的记录条数
					totalPages: totalPages, // 总页数
					totalRows: data.total, // 总记录条数

					visiblePageLinks: 3, // 显示几个卡片

					showGoToPage: true,
					showRowsPerPage: true,
					showRowsInfo: true,
					showRowsDefaultInfo: true,
					/*点击分页组件时触发*/
					onChangePage : function(event, data){
						pageList(data.currentPage , data.rowsPerPage);
					}
				});

			}
		})
	}
	
</script>
</head>
<body>
	<input type="hidden" id="hidden-name"/>
	<input type="hidden" id="hidden-owner"/>
	<input type="hidden" id="hidden-startDate"/>
	<input type="hidden" id="hidden-endDate"/>

	<input type="hidden" id="update-id">
	<input type="hidden" id="edit-id">
	<!-- 创建市场活动的模态窗口 -->
	<div class="modal fade" id="createActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form" id="activityAddForm">
					
						<div class="form-group">
							<label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-marketActivityOwner">
								</select>
							</div>
                            <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-marketActivityName">
                            </div>
						</div>
						
						<div class="form-group">
							<label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-startTime" readonly>
							</div>
							<label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-endTime" readonly>
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-describe"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal" id="saveBtn">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改市场活动的模态窗口 -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form" id="activityUpdateForm">
					
						<div class="form-group">
							<label for="edit-owner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-owner">
								</select>
							</div>
                            <label for="edit-Name" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-name">
                            </div>
						</div>

						<div class="form-group">
							<label for="edit-startDate" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="edit-startDate">
							</div>
							<label for="edit-endDate" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="edit-endDate">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-cost" class="col-sm-2 control-label">成本</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-cost">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-description" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<%--
									关于文本域<textarea>
										(1) 需要以标签对的形式呈现，正常情况下标签对需要紧挨着
										(2) textarea虽然是以标签对的方式呈现，但也属于表单元素，所有的
											textarea取值与赋值，都应该统一使用val()方法，而不是html()方法
								--%>
								<textarea class="form-control" rows="3" id="edit-description"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="updateBtn">更新</button>
				</div>
			</div>
		</div>
	</div>
	

	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>市场活动列表</h3>
			</div>
		</div>
	</div>
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" type="text" id="search-name">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="search-owner">
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">开始日期</div>
					  <input class="form-control time" type="text" id="search-startDate" />
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">结束日期</div>
					  <input class="form-control time" type="text" id="search-endDate">
				    </div>
				  </div>
				  
				  <button type="button" id="searchBtn" class="btn btn-default">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" id="addBtn"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" id="editBtn"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" id="delBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="selectAll"/></td>
							<td>名称</td>
                            <td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
						</tr>
					</thead>
					<tbody id="activityTable">

					</tbody>
				</table>
			</div>
			<div style="height: 50px; position: relative;top: 30px;">
				<div id="activityPage"></div>
			</div>
		</div>
		
	</div>
</body>
</html>