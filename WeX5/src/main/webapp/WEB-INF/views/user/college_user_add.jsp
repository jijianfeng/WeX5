<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://zlzkj.com/tags" prefix="z"%>

<form id="form" action="${__url__}" method="post">
    <table align="center" class="form-table" >
    	<tr>
			<td><span>所属大学:</span></td>
			<td>
			<input type="text" 	disabled="disabled" value="${university.universityName}">
		    <input type="hidden" id ="universityaa"  name="universityId" value="${university.id}">
		    </td>
		</tr>
		<tr>
			<td class="tr">学院名称：</td>
			<td>
				<input name="collegeName" class="jq-validatebox"  type="text" data-options="required:true"  >
			</td>
		</tr>
		<tr>			
			<td class="tr">联系人：</td>
			<td>
				<input name="linkMan"  class="jq-validatebox"  type="text" data-options="required:true" >
			</td>
		</tr>
		<tr>		
			<td>联系电话：</td>
			<td>
				<input name="linkTel" class="jq-validatebox"  type="text" data-options="required:true"  >
			</td>
		</tr>
		<tr>
			<td class="tr">联系地址：</td>
			<td>
				<input name="linkAddress" class="jq-validatebox"  type="text" data-options="required:true"  >
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
	<input name="id"  type="hidden"  >
</form>
<script>
$("#arealist1").combobox({
	editable:false,
	onChange:function(){
		var v = $("#arealist1").combobox("getValue");
		$('#universitylist1').combobox('reload', '${z:u('public/universitylist_nodefault')}'+"?areaId="+v);
		$('#universitylist1').combobox('setValues','');
	}
});

$("#look_universitya").on("click", function() {
	App.dialog({
		title: "选择学校",
		href: "${z:u('public/university_select?id=8')}",
		width: 800,
		height: 600
	});
});
</script>