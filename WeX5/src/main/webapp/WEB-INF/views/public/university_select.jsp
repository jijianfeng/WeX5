<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://zlzkj.com/tags" prefix="z" %>

<div class="jq-layout rel" data-options="fit:true">
    <div data-options="region:'north',border:false">
        <div class="tools fix" style="padding-bottom: 5px;">
            <ul class="fl">
                <li>
                    <span>学校名称：</span>
                    <input type="text" id="wd2"/>
                    <span>学校代码：</span>
                    <input type="text" id="wd3"/>
                    <button class="btn btn-primary btn-sm" id="search2">搜索</button>
                </li>
            </ul>
            <ul class="toolbar fr">
                <li id="add_to_form">
                    <button class="btn btn-sm btn-success">将选择的学校带回表单</button>
                </li>
            </ul>
        </div>
    </div>
    <div data-options="region:'center',border:false">
        <table id="grtid2" title="企业列表" fit="true" class="jq-datagrid" data-options="{
			url:'${z:u('public/university')}',
			method:'post',
			columns: [[
				{field:'id',checkbox:true},
				{field:'name',title:'学校名称',width: 200},
				{field:'code',title:'学校代码',width: 200},
			]]
			}">
        </table>
    </div>
</div>
<script type="text/javascript">
    // 搜索
    $("#search2").on("click", function () {
        var val = $("#wd2").val();
        $("#grtid2").datagrid({
            url: '${z:u("/project/findgrid")}?wd=' + val
        });
    });
    // 将对应的内容带回表单
    $("#add_to_form").on("click", function () {
        var row = $("#grtid2").datagrid("getSelected");
        if (row == null) {
            App.alert("请先选择一个企业", "warning");
            return false;
        }
        $("#enterpriseId").val(row.id);
        $("#enterprise_name").val(row.name);
        // 关闭弹出层
        $(".panel-tool-close:eq(1)").click();
    });
</script>