<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://zlzkj.com/tags" prefix="z" %>
<%@ page session="false" %>

<form id="form" method='post' action="${__url__}" enctype="multipart/form-data">
    <table align="center" class="form-table">
    	<tr id="parent_module">
            <td align="right">对方手机号码：</td>
            <td>
               <input  name="userTel" type="text" class="jq-validatebox" data-options="required:true" />
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