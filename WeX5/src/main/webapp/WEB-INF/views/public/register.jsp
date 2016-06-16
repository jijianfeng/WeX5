<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://zlzkj.com/tags" prefix="z" %>
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>注册</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width"> 
	<link href="${__static__}/admin/ui/jqui.min.css" rel="stylesheet" type="text/css"/>
	<link href="${__static__}/admin/css/admin.css" rel="stylesheet" type="text/css"/>
	<%--<link href="${__static__}/admin/css/style.css" rel="stylesheet" type="text/css"/>--%>
	<link href="${__static__}/mod/fancybox/jquery.fancybox.css" rel="stylesheet" />
    <style>
        .title{border-bottom:5px solid #3697DC;padding:10px 0}
        footer{position:absolute;bottom:0;width:100%;}
        .error{color: #FF0000}
        .right{color: #00AA00}
    </style>
</head>
<body>
<div class="container">
    <h1 class="text-center title"  style="
    height: 40px;"><img alt="" src="${__static__}/admin/img/logo22.png"></h1>
<form id="form" action="${__url__}" method="post">
    <table align="center" class="form-table" style="margin:40px 50px">
    	<tr>
			<td>用户名：</td>
			<td>
				<input id="userName" name="userName"   type="text"   class="jq-validatebox"  data-options="required:true" style="width:300px;" onblur="javascript:chkname(this);"><span></span>
			</td>
		</tr>
		<tr>
			<td>密码：</td>
			<td>
				<input name="userPassword" type="password"  class="jq-validatebox"  data-options="required:true" style="width:300px;">
			</td>
		</tr>
		<tr>
		<td>重复密码：</td>
		<td>
				<input name="userPasswordAgain" type="password"  class="jq-validatebox"  data-options="required:true" style="width:300px;">
		</td>
		</tr>
    	<tr>
			<td>性别：</td>
			<td colspan="4" >
				<input  name="userSex" type="radio" value="0">女&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input  name="userSex" type="radio" value="1">男
			</td>
		</tr>
		<tr>
			<td>联系电话：</td>
			<td>
				<input name="userPhone"   type="text"   class="jq-validatebox"  data-options="required:true" style="width:300px;">
			</td>
		</tr>
		<tr>
			<td>职称：</td>
			<td>
			    <select name="titlesId" class="jq-combobox" id="titlelist" style="width:315px" data-options="{
			    	required:'true',
					method:'post',
					editable:true,
					url: '${z:u('public/titlelist')}'}">
				</select>
			</td>
		</tr>
		<tr>
			<td>真实姓名：</td>
			<td>
				<input name="nickName"   type="text"   class="jq-validatebox"  data-options="required:true" style="width:300px;">
			</td>
		</tr>
		<tr>
			<td>身份证号：</td>
			<td>
				<input name="identifyNo" type="text"  class="jq-validatebox" style="width:300px;" data-options="required:true" >
		</td>
		</tr>
		<tr>
			<td>出生日期：</td>
			<td colspan="4" >
				<input class="jq-datebox" style="width:315px" name="userBirthday"  data-options="required:true"  ></input>
			</td>
		</tr>
		<tr>
			<td>银行卡号：</td>
			<td>
				<input name="visaCard" class="jq-validatebox" type="text" style="width:300px;"  data-options="required:true"  >
			</td>
			<td class="tr">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;开户银行：</td>
			<td>
				 <input name="visaBank" class="jq-validatebox" type="text" style="width:300px;"  data-options="required:true"  >
			</td>
		</tr>
		<tr>
			<td>所在省份：</td>
			<td> 
				<select name="areaId" class="jq-combobox" id="arealist" style="width:315px" data-options="{
						required:'true',
						method:'post',
						editable:true,
						url: '${z:u('public/arealist_nodefault')}'}">
				</select>
			</td>
			</tr>
			<tr>
			<td class="tr">所属大学：</td>
			<td>
				 <select name="universityId" class="jq-combobox" id="universitylist" style="width:315px" data-options="{
				    required:'true',
					method:'post',
					editable:true,
					}">
			</select>
			</td>
			<td class="tr">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;所属学院：</td>
			<td>
				<select name="collegeId" class="jq-combobox" id="collegelist" style="width:190px" data-options="{
					method:'post',
					editable:true,
					}">
			</select>
			</td>
			<td class="tr">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;所属专业：</td>
			<td>
				<select name="majorId" class="jq-combobox" id="majorlist" style="width:190px" data-options="{
					method:'post',
					editable:true,
					}">
			</select>
			</td>
		</tr>
			
	
		
		<tr>
			<td>角色身份：</td>
			<td>
				<select name="userType" class="jq-combobox" panelHeight="auto" style="width:315px" data-options="required:true" >
				<option value="0">学校研究部</option>
				<option value="1">学院</option>
				<option value="2">老师</option>
				<option value="3">超级管理员</option>
				<option value="4">普通管理员</option>
			</select>
			</td>
			<td class="tr">角色权限：</td>
			<td>
				 <select name="roleId" class="jq-combobox" name="rolelists" style="width:190px"  data-options="{
				 	required:'true',
					method:'post',
					editable:true,
					url: '${z:u('public/rolelists')}'}">
				</select>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>
				<button type="submit" class="btn btn-small btn-info">提交注册</button>
			    <span class="error" id="msg"></span>	
			</td>	
		</tr>
	</table>
</form>
        </div>
    </div>


<!-- FOOTER -->
<!-- <footer >Copyright&nbsp;2013-
	<script>
		document.write(new Date().getFullYear());
	</script>
</footer> -->
<script src="${__static__}/mod/jquery/jquery.min.js" type="text/javascript"></script>
<script src="${__static__}/admin/ui/jqui.min.js" type="text/javascript"></script>
<script src="${__static__}/mod/jquery/jquery.hashchange.min.js" type="text/javascript"></script>
<script src="${__static__}/admin/js/admin.min.js" type="text/javascript"></script>
<script src="${__static__}/mod/fancybox/jquery.fancybox.min.js" type="text/javascript"></script>
<script src="${__static__}/mod/jquery/jquery.form.js" type="text/javascript"></script>
<script type="text/javascript">

   


	// 提交
	$("#form").ajaxForm({
		type: "post",
		dataType: "json",
		beforeSubmit: function() {
			$("#msg").removeClass("error").addClass("right").html("正在注册...");
		},
		success: function(data) {
			if(data.status == 1) {
				$("#msg").removeClass("error").addClass("right").html(data.info);
				window.location.href = "${z:u('/')}";
			}else {
				$("#msg").removeClass("right").addClass("error").html(data.info);
			}
		}
	});
	
	
	//四级联动
	$("#arealist").combobox({
		editable:false,
		onChange:function(){
			var v = $("#arealist").combobox("getValue");
			$('#universitylist').combobox('reload', '${z:u('public/universitylist_nodefault')}'+"?areaId="+v);
			$('#universitylist').combobox('setValues','');
			$('#collegelist').combobox('setValues','0');
			$('#majorlist').combobox('setValues','0');
		}
	});
	
	$("#universitylist").combobox({
		editable:false,
		onChange:function(){
			var b = $("#universitylist").combobox("getValue");
			$('#collegelist').combobox('reload', '${z:u('public/collegelist')}'+"?universityId="+b);
			$('#collegelist').combobox('setValues','0');
			$('#majorlist').combobox('setValues','0');
		}
	});
	
	$("#collegelist").combobox({
		editable:false,
		onChange:function(){
			var n = $("#collegelist").combobox("getValue");
			$('#majorlist').combobox('reload', '${z:u('public/majorlist')}'+"?collegeId="+n);
			$('#majorlist').combobox('setValues','0');
		}
	});
	
	// 提交
	$("#form").ajaxForm({
		type: "POST",
		dataType: "json",
		beforeSubmit: function() {
			$("#msg").removeClass("error").addClass("right").html("正在注册...");
		},
		success: function(data) {
			if(data.status == 1) {
				$("#msg").removeClass("error").addClass("right").html(data.info);
				window.location.href = "${z:u('/')}";
			}else {
				$("#msg").removeClass("right").addClass("error").html(data.info);
			}
		}
	});
</script>
</body>
</html>
