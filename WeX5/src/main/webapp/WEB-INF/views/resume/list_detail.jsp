<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://zlzkj.com/tags" prefix="z" %>
<%@ page session="false" %>

<form id="form" method='post' action="${__url__}" enctype="multipart/form-data">
    <table align="center" class="form-table">
    	<tr id="parent_module">
            <td align="right">账号：</td>
            <td>
               <input value="${Recru.job }" name="job" type="text" class="jq-validatebox" disabled="disabled" data-options="required:true" />
            </td>
        </tr>
        <tr>
        	<td align="right">年薪：</td>
        	<td>
        		<input class="jq-validatebox" type="text" name="password" disabled="disabled" value="${Recru.yearSalary}" />            
        	</td>
        </tr>
        <tr>
        	<td align="right">职业标签：</td>
        	<td>
        		<input class="jq-validatebox" type="text" name="password" disabled="disabled" value="${Recru.jobTag}" />            
        	</td>
        </tr>
        <tr>
        	<td align="right">职业要求：</td>
        	<td>
        		<input class="jq-validatebox" type="text" name="password" disabled="disabled" value="${Recru.requireTag}" />            
        	</td>
        </tr>
        <tr>
        	<td align="right">添加人：</td>
        	<td>
        		<input class="jq-validatebox" type="text" name="password" disabled="disabled" value="${nickName}" />            
        	</td>
        </tr>
        <tr>
        	<td align="right">类型：</td>
        	<td>
        	<select class="jq-combobox" id="type" disabled="disabled" name ="types">
  					<option value ="0">不启用</option>
  					<option value ="1">启用</option>
  					<c:if test="${Recru.status!=null }"><option value="${Recru.status}" selected="selected"></option></c:if>
			</select>
        	</td>
        </tr>
        <tr>
        	<td align="right">详细信息：</td>
        	<td>
        		<textarea disabled="disabled" style="width:200px;height:180px;">${Recru.description}</textarea>
        	</td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td>
                <button class="btn btn-primary btn-small J_close" type="button">确定</button>
            </td>
        </tr>
    </table>
</form>