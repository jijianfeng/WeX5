<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://zlzkj.com/tags" prefix="z"%>

<form id="form" action="${__url__}" method="post">
    <table align="center" class="form-table" >
    	<tr>
			<td><span>所属大学:</span></td>
			<td>
			   <input type="text" disabled="true" id="universityNamea" name="universityName" style="width:160px" />
			   <button class="btn btn-xs btn-primary" id="look_universityaa" type="button">查找</button>
			   <input type="hidden" id ="universityaa" name="universityaaa" value="">
		    </td>
		</tr>
		<tr>
			<td class="tr">学院：</td>
			<td>
				<select class="jq-combobox" id="collegelista" name="collegeaaa" style="width:160px" data-options="{
    	        	required:true,
					method:'post',
					editable:true,
<!-- 					url: '${z:u('public/collegelistwhere_nodefault')}' -->
					}">			
    	        </select>
			</td>
		</tr>
		<tr>
			<td>是否只属于管理员：</td>
			<td>
				<input type="radio" value="1"  id="i1" name="isAdmin" checked="check"><label>：是</label>
				<input type="radio" value="0" id="i2" name="isAdmin"><label>：否</label>
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

$("#look_universityaa").on("click", function() {
	App.dialog({
		title: "选择学校",
		href: "${z:u('public/university_select?id=6')}",
		width: 800,
		height: 600
	});
});
</script>