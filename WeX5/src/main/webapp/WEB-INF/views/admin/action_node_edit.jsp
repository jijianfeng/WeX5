<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://zlzkj.com/tags" prefix="z"%>

<form id="form" action="${__url__}" method="post">
    <table align="center" class="form-table">
		<tr>
			<td>父节点：</td>
			<td>
				<input class="jq-combotree" type="text" name="pid" value="${actionNode.pid}" url="${z:u('admin/action_node?ui=combo')}" data-options="{
						required:true,
						method:'post',
						valueField:'id'
					}" />
			</td>
		</tr>
		<tr>
			<td>节点名称：</td>
			<td>
				<input name="name" class="jq-validatebox" value="${actionNode.name}" type="text" data-options="required:true">
			</td>
		</tr>
		<tr>
			<td>url：</td>
			<td>
				<input name="url"  value="${actionNode.url}" type="text" >
			</td>
		</tr>
		<tr>
			<td>排序值：</td>
			<td>
				<input name="sortId" class="jq-validatebox" value="${actionNode.sortId}" type="text" data-options="required:true" validType="number" >
			</td>
		</tr>
		<tr>
			<td>是否显示：</td>
			<td>
				<label class="radio inline">
				  <input type="radio" name="isShow" value="1" ${actionNode.isShow==1?"checked":""}>
				  是
				</label>
				<label class="radio inline">
				  <input type="radio" name="isShow" value="0" ${actionNode.isShow==0?"checked":""}>
				  否
				</label>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>
				<input type="hidden" name="id" value="${actionNode.id}">
				<button type="submit" class="btn btn-small btn-success">确定</button>
			</td>
		</tr>
	</table>
</form>