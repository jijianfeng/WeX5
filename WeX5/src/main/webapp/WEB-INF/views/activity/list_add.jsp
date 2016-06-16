<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://zlzkj.com/tags" prefix="z"%>

<form id="form" action="${__url__}" method="post" enctype="multipart/form-data">
    <table align="left" class="form-table" >
		<tr>
			<td>标题：</td>
			<td>
				<input name="title"   type="text" class="jq-validatebox" validtype="length[0,40]" data-options="required:true" style="width:300px;">
				显示：
				<select class="jq-combobox" name="status" panelHeight="auto" style="width:100px" id="type">
					<option value="1">显示</option>
					<option value="0">不显示</option>
				</select>
				排序值：
				<input name="sortNumber" class="jq-validatebox w50" validtype="number" value="" type="text" data-options="required:true"  />
				<a style="color: red;font-size:16">排序值约大，资讯约靠前</a>
			</td>
		</tr>
		<tr>
			<td>状态：</td>
			<td>
				人员限制：
				<input name="allNumber"   type="text" class="jq-validatebox" validtype="number" data-options="required:true" style="width:100px;">
				结束时间：
				<input class="jq-datebox" style="width:100px" name="end" id="endTime"></input>
				封面：<input class="jq-validatebox w200" type="file" validtype="image" accept=".png,.gif,.jpg,.jpeg" name="imgFile" data-options="required:true"/>
				<span class="g9 ml5">*尺寸：800px*300px</span>
			</td>
		</tr>
		<tr>
			<td>内容：</td>
			<td>
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