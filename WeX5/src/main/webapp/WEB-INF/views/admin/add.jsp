<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://zlzkj.com/tags" prefix="z"%>

<form id="form" action="${__url__}" method="post">
    <table align="center" class="form-table">
		<tr>
			<td>登录名：</td>
			<td>
				<input name="loginName" class="jq-validatebox" value="" type="text" data-options="required:true" validType="loginName">
			</td>
		</tr>
		<tr>
			<td>密码：</td>
			<td>
				<input name="loginPass" class="jq-validatebox" value="" type="password" data-options="required:true" >
			</td>
		</tr>
		<tr>
			<td>昵称：</td>
			<td>
				<input name="nickname" type="text">
			</td>
		</tr>
		<tr>
			<td>邮箱：</td>
			<td>
				<input name="email"  type="text">
			</td>
		</tr>
		<tr>
			<td>手机号：</td>
			<td>
				<input name="mobile" type="text">
			</td>
		</tr>
		<tr>
		<td>备注：</td>
			<td>
				<input name="remark" type="text">
			</td>
		</tr>
		<tr>
			<td>是否启用：</td>
			<td>
				<label class="radio inline">
				  <input type="radio" name="isDisabled" value="1" checked="true">
				  是
				</label>
				<label class="radio inline">
				  <input type="radio" name="isDisabled" value="0">
				  否
				</label>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>
				<button type="submit" class="btn btn-small btn-success">确定</button>
			</td>
		</tr>
	</table>
</form>