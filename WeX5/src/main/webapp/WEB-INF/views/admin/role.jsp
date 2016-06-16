<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://zlzkj.com/tags" prefix="z"%>
<div class="jq-layout rel" data-options="fit:true">
	<div data-options="region:'north',border:false">
        <div id="grid-toolbar" class="clearfix p5">
           <a id="add" class="btn btn-sm btn-success"><i class="icon icon-add"></i>新增</a>
			<a id="edit" class="btn btn-sm btn-info"><i class="icon icon-edit"></i>编辑</a>
			<a id="delete" class="btn btn-sm btn-danger"><i class="icon icon-delete"></i>删除</a>
			<a id="access" class="btn btn-sm btn-warning">设置权限</a>
        </div>
    </div>
    <div data-options="region:'center',border:false">
		<table title="角色列表" fit="true" class="jq-datagrid" data-options="{
			url:'${z:u('admin/role')}',
			method:'post',
			columns: [[
				{field:'id',checkbox:true},
				{field:'name',title:'角色名称',width:100},
				{field:'remark',title:'备注',width:100},
				{field:'status',title:'是否启用',width:60,formatter:App.statusFmt}
			]]}
			">
		</table>
	</div>
</div>
<script type="text/javascript">
	$("#add").on("click", function(){
		App.popup('${z:u("admin/role_add")}',"新增角色");
	});
	$("#edit,#delete,#access").on("click", function(){
		var data = $(".jq-datagrid").datagrid("getSelected");
		if(data == null){
			App.alert("请先选择一条记录","warning");
		}else{
			var id = data.id;
			var eleId = $(this).attr("id");
			if(eleId == "edit"){
				App.popup('${z:u("admin/role_edit")}?id='+id,"编辑角色");
			}else if(eleId == "delete"){
				App.ajax("${z:u('admin/role_delete')}?id="+id);
			}else if(eleId == "access"){
				App.popup('${z:u("admin/role_access")}?id='+id,{
					title: "设置权限",
					width: 600,
					height: 600
				});
			}
		}
	});
</script>