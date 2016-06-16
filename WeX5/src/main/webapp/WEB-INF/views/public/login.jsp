<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://zlzkj.com/tags" prefix="z" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>WeX5管理平台</title>
	<link href="${__static__}/admin/css/login.css" rel="stylesheet" type="text/css"/>
</head>
<body class="login-bd">
<div id="mainBody">
	<div id="cloud1" class="cloud" style="background-position: 450px 100px;"></div>
	<div id="cloud2" class="cloud" style="background-position: 0 460px;"></div>
</div>
<div class="logintop">
	<span>欢迎登录后台管理界面平台</span>
</div>
<div class="loginbody">
	<span class="systemlogo"></span>
	<form class="loginbox" id="form" action="${__url__}" method="post">
		<ul>
			<li>
				<input name="loginName" type="text" class="loginuser" placeholder="账号" value="17826800420" />
			</li>
			<li>
				<input name="loginPass" type="password" class="loginpwd" placeholder="密码/如忘记密码这里填验证码后点忘记密码"  value="123456"/>
			</li>
<!-- 			<li> -->
<!-- 				<input name="checkCode" type="text" class="checkCode" placeholder="验证码" /> -->
<!-- 			</li> -->
			<li class="mb10">
				<input type="submit" class="loginbtn" value="登录" />
				&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" id="register" class="loginbtn" value="忘记密码" />
				<label>
				<img alt="code..." name="randImage" id="randImage" src='${z:u("/public/image")}'  width="60" height="20" border="1" align="absmiddle">
<!-- 					<input name="" type="checkbox" value="" checked="checked" />记住密码</label> -->
				<%--<label>--%>
				<%--<a href="#">忘记密码？</a>--%>
				<%--</label>--%>
			</li>
			<li>
				<span class="error" id="msg"></span>
			</li>
		</ul>
	</form>
</div>
<div class="loginbm">Copyright&nbsp;2015-
	<script>
		document.write(new Date().getFullYear());
	</script>
</div>
<script src="${__static__}/mod/jquery/jquery.min.js" type="text/javascript"></script>
<script src="${__static__}/admin/js/cloud.js" type="text/javascript"></script>
<script src="${__static__}/mod/jquery/jquery.form.js" type="text/javascript"></script>
<script type="text/javascript">
	$("#register").click(function(){
		var name = $(".loginuser").val();
		var pwd = $(".loginpwd").val();
		if (name == "") {
			$(".loginuser").focus();
			$("#msg").removeClass("right").addClass("error").html("用户名为空");
			return false;
		}else if (pwd == "") {
			$(".loginpwd").focus();
			$("#msg").removeClass("right").addClass("error").html("验证码为空！");
			return false;
		}
		else{
			$("#msg").removeClass("error").addClass("right").html("正在验证...");
			$.ajax({
				url : '${z:u("public/forgot_pass")}?user='+name+"&checkCode="+pwd,
				type : "POST",
				dataType : "json",
				beforeSubmit: function() {
					
				},
				success : function(data) {
	// 				alert("已经发送短信到你绑定的手机上！");
	// 				$.messager.alert("系统提示", result.info);
	// 				$("#msg").removeClass("error").addClass("right").html("密码发送成功");
					if(data.status == 1) {
						$("#msg").removeClass("error").addClass("right").html(data.info);
						alert(data.info);
					}
					else {
						document.getElementById("randImage").src = "${z:u("/public/image")}?"+Math.random();
						$("#msg").removeClass("right").addClass("error").html(data.info);
					}
				}
		   });
	   }
	});

	// 提交
	$("#form").ajaxForm({
		type: "post",
		dataType: "json",
		beforeSubmit: function() {
			var name = $(".loginuser").val();
			var pwd = $(".loginpwd").val();
			if (name == "") {
				$(".loginuser").focus();
				$("#msg").removeClass("right").addClass("error").html("用户名为空");
				return false;
			}
			if (pwd == "") {
				$(".loginpwd").focus();
				$("#msg").removeClass("right").addClass("error").html("密码为空");
				return false;
			}
			$("#msg").removeClass("error").addClass("right").html("正在登录...");
		},
		success: function(data) {
			if(data.status == 1) {
				$("#msg").removeClass("error").addClass("right").html(data.info);
				window.location.href = "${z:u('/')}";
			}
			else if(data.status == 0){
				$("#msg").removeClass("right").addClass("error").html(data.info);
			}
// 			else if(data.status == 2){
// 				alert("您尚未完善个人信息，请先完善个人信息！");
// 				window.location.href = "${z:u('/user/list')}";
// 			}
		}
	});
	
	$("#randImage").on("click", function() {
		document.getElementById("randImage").src = "${z:u("/public/image")}?"+Math.random();
		//alert(Math.random());
	});
</script>
</body>
</html>