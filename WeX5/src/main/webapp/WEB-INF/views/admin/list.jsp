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
		<table title="管理员列表" fit="true" class="jq-datagrid" data-options="{
			url:'${z:u('admin/list')}',
			method:'post',
			columns: [[
				{field:'id',checkbox:true},
				{field:'loginName',title:'登录名',width:100},
				{field:'nickname',title:'昵称',width:100},
				{field:'email',title:'邮箱',width:100},
				{field:'mobile',title:'手机',width:100},
				{field:'remark',title:'备注',width:100},
				{field:'isDisabled',title:'是否启用',width:60,formatter:App.statusFmt}
			]]}
			">
		</table>
	</div>
</div>
<script type="text/javascript">
	$("#add").on("click", function(){
		App.popup('${z:u("admin/add")}',"新增管理员");
	});
	$("#edit,#delete,#detail").on("click", function(){
		var data = $(".jq-datagrid").datagrid("getSelected");
		if(data == null){
			App.alert("请先选择一条记录","warning");
		}else{
			var id = data.id;
			var eleId = $(this).attr("id");
			if(eleId == "edit"){
				App.popup('${z:u("admin/edit")}?id='+id,"编辑管理员");
			}else if(eleId == "delete"){
				App.ajax("${z:u('admin/delete')}?id="+id);
			}
		}
	});
</script>