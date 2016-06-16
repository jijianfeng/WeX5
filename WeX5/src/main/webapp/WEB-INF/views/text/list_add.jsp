<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://zlzkj.com/tags" prefix="z"%>

<form id="form" action="${__url__}" method="post" enctype="multipart/form-data">
    <table align="left" class="form-table" >
		<tr>
			<td>标题：</td>
			<td>
				<input name="title"   type="text" class="jq-validatebox" validtype="length[0,40]" data-options="required:true" style="width:140px;">
				类型：
				<select class="jq-combobox"  name="chapterId" style="width:100px" data-options="{
						required:true,
						method:'post',
						editable:false,
						url: '${z:u('chapter/chapter_list')}'}">
				</select>
				显示：
				<select class="jq-combobox" name="status" panelHeight="auto" style="width:60px" id="type">
					<option value="1">显示</option>
					<option value="0">不显示</option>
				</select>
				排序值：
				<input name="sortNumber" class="jq-validatebox w50" validtype="number" value="" type="text" data-options="required:true"  />
				<a style="color: red;font-size:16">排序值约大，资讯约靠前</a>
			</td>
		</tr>
		<tr>
			<td>内容：</td>
			<td>
<!-- 				<textarea style="width:500px;height:300px;" id="" name="content" class="jq-validatebox" data-options="required:true" ></textarea> -->
					<textarea class="jq-validatebox" id="editor" name="content" style="width: 600px;height: 350px;" ></textarea>
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