<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://zlzkj.com/tags" prefix="z"%>

<div class="jq-layout rel" data-options="fit:true">
    <div data-options="region:'north',border:false">
        <div id="grid-toolbar" class="clearfix p5">
            <a id="add" class="btn btn-sm btn-success"><i class="icon icon-add"></i>新增</a>
            <a id="edit" class="btn btn-sm btn-info"><i class="icon icon-edit"></i>编辑</a>
            <a id="delete" class="btn btn-sm btn-danger"><i class="icon icon-delete"></i>删除</a>
        </div>
    </div>
    <div data-options="region:'center',border:false">
		<table fit="true" title="节点列表" class="jq-treegrid" data-options="{
			url:'${z:u('admin/action_node')}',
			idField:'id',
			treeField:'name',
			pagination: false,
			method:'post',
			columns: [[
				{field:'id',checkbox:true},
				{field:'name',title:'节点名称',width:200},
				{field:'url',title:'url',width:100},
				{field:'sortId',title:'排序值',width:50},
				{field:'isShow',title:'是否显示',width:60,formatter: App.statusFmt}
			]]
		}">
		</table>
	</div>
</div>
<script type="text/javascript">
	$("#add").click(function(){
		App.popup('${z:u("/admin/action_node_add")}',"新增节点");
	});
	$("#edit,#delete").click(function(){
		var row = $(".jq-treegrid").treegrid("getSelected");
		if(row==null){
			App.alert("请先选择一条记录","warning");
		}else{
			var eleId = $(this).attr("id");
			if(eleId=="edit"){
				App.popup('${z:u("/admin/action_node_edit")}?id='+row.id,"编辑节点");
			}else if(eleId=="delete"){
	            App.ajax('${z:u("/admin/action_node_delete")}?id='+row.id);
			}
		}
	});
</script>