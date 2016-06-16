<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://zlzkj.com/tags" prefix="z"%>

<form id="form" action="${__url__}" method="post" enctype="multipart/form-data">
    <table align="left" class="form-table" >
    	<tr>
    		<td>选项A：</td>
    		<td><input name="choice_namea"   type="text" class="jq-validatebox" validtype="length[0,40]" data-options="required:true" style="width:300px;">
    		选项B：
    		<input name="choice_nameb"   type="text" class="jq-validatebox" validtype="length[0,40]" data-options="required:true" style="width:300px;">
    		</td>
    	</tr>
    	<tr>
    		<td>选项C：</td>
    		<td><input name="choice_namec"   type="text" class="jq-validatebox" validtype="length[0,40]" data-options="required:true" style="width:300px;">
    		选项D：
    		<input name="choice_named"   type="text" class="jq-validatebox" validtype="length[0,40]" data-options="required:true" style="width:300px;">
    		</td>
    	</tr>
		<tr>
			<td>正确答案：</td>
			<td>
				<select class="jq-combobox" name="choiceValue" panelHeight="auto" style="width:200px;" id="type">
					<option value="0">A</option>
					<option value="1">B</option>
					<option value="2">C</option>
					<option value="3">D</option>
				</select>
				显示：
				<select class="jq-combobox" name="status" panelHeight="auto" style="width:200px;" id="type">
					<option value="1">显示</option>
					<option value="0">不显示</option>
				</select>
				等级：
				<select class="jq-combobox"  name="levelId" style="width:100px" data-options="{
						required:true,
						method:'post',
						editable:false,
						url: '${z:u('confirm/level_list')}'}">
				</select>
			</td>
		</tr>
		<tr>
			<td>内容：</td>
			<td>
<!-- 				<textarea style="width:500px;height:300px;" id="" name="content" class="jq-validatebox" data-options="required:true" ></textarea> -->
					<textarea class="jq-validatebox" id="editor" name="questionName" style="width: 600px;height: 350px;" ></textarea>
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
<script type="text/javascript">
	KindEditor.create("#editor",{
		uploadJson:'${z:u("upload")}'
	});
</script>