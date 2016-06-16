<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://zlzkj.com/tags" prefix="z"%>
<form id="form" action="${__url__}" method="post" enctype="multipart/form-data" >
	<table align="center"  class="form-table" style="font-size:15px" bgcolor="white" ; >
			<tr>
				<td>模板备注:</td>
				<td><input type="text" name="remark" ></td>
			</tr>
			<tr>
				<td>模板上传：</td>
				<td><input type="file" name="file"></td>
			</tr>
			<tr>
				<td></td>
				<td><button type="submit" class="btn btn-small btn-success">确定添加</button></td>
			</tr>				
	</table>
</form>
<script type="text/javascript">
	$(document).ready(function(){  
	    $("#form").bind("submit", function(){  
	    	$('.jq-datagrid').datagrid('clearSelections');
	    	return true;
	    })
	})
</script>