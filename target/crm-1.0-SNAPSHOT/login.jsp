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
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
</head>
<body>

	<script type="text/javascript">

		$(function () {

			if (window.top !== window) {
				window.top.location = window.location;
			}

			//	页面加载完毕后，用户名文本框自动获得焦点
			$("#loginAct").focus();

			//	为登录按钮绑定点击事件，执行验证登录操作
			$("#submitBtn").click(function () {
				login();
			})

			//	为当前登录页面绑定敲键盘事件
			//
			$(window).keydown(function (event) {
				if (event.keyCode === 13) {
					login();
				}
			})
		})

		//	登录函数
		function login() {
			//	获取用户名及密码
			var loginAct = $.trim($("#loginAct").val());
			var loginPwd = $.trim($("#loginPwd").val());

			//	验证账号密码是否为空
			if (loginAct === "" || loginPwd === "") {
				$("#msg").html("用户名及密码不能为空");
				return false;
			}

			//	发起ajax请求
			$.ajax({
				url : "settings/user/login.do",
				data : {
					"loginAct" : loginAct,
					"loginPwd" : loginPwd
				},
				type : "post",
				dataType : "json",
				success : function (data) {
					/*
						data
							{"success" : true/false, "msg" : "detail error info"}
					* */

					//	登录成功，跳转到工作台初始页面

					if (data.success) {
						window.location.href = "workbench/index.jsp"
					//	登录失败，显示登录失败详细新信息
					} else {
						$("#msg").html(data.msg);
					}
				}
			})

			//	根据请求返回值执行不同操作
		}
	</script>

	<div style="position: absolute; top: 0px; left: 0px; width: 60%;">
		<img src="image/IMG_7114.JPG" style="width: 100%; height: 90%; position: relative; top: 50px;">
	</div>
	<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
		<div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">CRM &nbsp;<span style="font-size: 12px;">&copy;2017&nbsp;动力节点</span></div>
	</div>

	<div style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5">
		<div style="position: absolute; top: 0px; right: 60px;">
			<div class="page-header">
				<h1>登录</h1>
			</div>
			<form action="workbench/index.jsp" class="form-horizontal" role="form">
				<div class="form-group form-group-lg">
					<div style="width: 350px;">
						<input class="form-control" type="text" placeholder="用户名" id="loginAct">
					</div>
					<div style="width: 350px; position: relative;top: 20px;">
						<input class="form-control" type="password" placeholder="密码" id="loginPwd">
					</div>
					<div class="checkbox"  style="position: relative;top: 30px; left: 10px; color: red">
						
							<span id="msg"></span>
						
					</div>
					<button type="button" id="submitBtn" class="btn btn-primary btn-lg btn-block"  style="width: 350px; position: relative;top: 45px;">登录</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>