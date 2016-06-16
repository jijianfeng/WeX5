<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://zlzkj.com/tags" prefix="z"%>
<form id="form" action="${__url__}" method="post" enctype="multipart/form-data" >
	<table align="center"  class="form-table" style="font-size:15px" bgcolor="white" ; >
			<tr>
				<input type="hidden" value="${universitytId}" name="universityId">
				<td><input type="file" name="file" accept=".xls"></td>
				<td><button type="submit" class="btn btn-small btn-success">确定</button></td>
			</tr>				
	</table>
</form>
<script type="text/javascript">
	$(document).ready(function(){  
	    $("#form").bind("submit", function(){  
	    	$('.jq-datagrid').datagrid('clearSelections');
	    	return true;
	    });
	});
</script>