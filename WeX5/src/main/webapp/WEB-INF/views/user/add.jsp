<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://zlzkj.com/tags" prefix="z" %>
<%@ page session="false" %>

<form id="form" method='post' action="${__url__}" enctype="multipart/form-data">
    <table align="center" class="form-table">
    	<tr id="parent_module">
            <td align="right">账号：</td>
            <td>
               <input value="${user.name }" name="name" type="text" class="jq-validatebox" data-options="required:true" />
            </td>
        </tr>
        <tr>
        	<td align="right">密码：</td>
        	<td>
        		<input class="jq-validatebox" type="text" name="password" value="${user.password}" />            
        	</td>
        </tr>
        <tr>
            <td align="right">用户角色：</td>
            <td>
           			<select class="jq-combobox" name="roleId" data-options="{
					method:'post',
					editable:true,
					selected:true,
					url: '${z:u('public/rolelists')}'}">
<%-- 					<c:if test="${roleId!=null }"><option value="${ roleId}" selected="selected"></option></c:if>
 --%>				</select>
            </td>
        </tr>
        <tr>
        	<td align="right">类型：</td>
        	<td>
        	<select class="jq-combobox" id="type" name ="types">
  					<option value ="1">企业</option>
  					<option value ="2">地区</option>
  					<option value="3">部门</option>
			</select>
        	</td>
        </tr>
        <tr>
        <td align="right">详细类型：</td>
			<td>
				<input type="text" disabled="true" name="name" id="name" />
				<input type="hidden" name="ida" id="ida" />
				<button class="btn btn-xs btn-primary" id="look" type="button">查找</button>
			</td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td>
                <button type="submit"  class="btn btn-sm btn-success">确定</button>
            </td>
        </tr>
    </table>
</form>
<script>
	$("#look").on("click", function() {
	var wd = $("#type").combobox("getValue");
	
		App.dialog({
			title: "选择",
			href: '${z:u("user/select")}?types='+wd,
			width: 800,
			height: 600
		});
	});
</script>