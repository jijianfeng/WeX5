<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://zlzkj.com/tags" prefix="z"%>

<form id="form" action="${__url__}" method="post">
    <table align="left" class="form-table" style="margin:40px 50px">
    	<tr>
			<td>账号：</td>
			<td colspan="4" >
				<input class="w200" name="name" class="jq-validatebox" readonly="true"   value='${user.userPhone}' type="text" data-options="required:true">
			</td>
		</tr>
		<tr>
			<td>原密码：</td>
			<td colspan="4" >
				<input class="w200" name="OPass" class="jq-validatebox" value="" type="password" data-options="required:true">
			</td>
		</tr>
		<tr>
			<td>新密码：</td>
			<td colspan="4" >
				<input class="w200" name="NPass" id="pass"  value="" type="password" data-options="required:true">
			</td>
		</tr>
		<tr>
			<td>新密码：</td>
			<td colspan="4" >
				<input class="w200" name="Again" id="again"  value="" type="password" data-options="required:true">
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>
				<button type="submit" class="btn btn-small btn-success">确定</button>
				<a class="btn btn-primary btn-small" href="">返回</a>
			</td>
		</tr>
	</table>
</form>
<script>
	$("#form").ajaxForm({
		type: "post",
		dataType: "json",
		beforeSubmit: function() {
			var pass = $("#pass").val();
			var again = $("#again").val();
			if (pass != again) {
				App.alert("两次密码不一致","success");
				return false;
			}
		},
		success: function(data) {
			if(data.status == 1) {
				App.alert("修改成功","success");
			}else {
				App.alert(data.info,"warnning");
			}
		}
	});
// 	App.ajaxForm("#form");
</script>