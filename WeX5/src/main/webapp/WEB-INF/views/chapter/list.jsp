<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://zlzkj.com/tags" prefix="z"%>

<div class="jq-layout rel" data-options="fit:true">
	<div data-options="region:'north',border:false">
        <div id="grid-toolbar" class="clearfix p5">
        	<span class="ml10">章节标题搜索：</span>
			<input class="mr10" id="title" type="text"  placeholder="输入章节关键词搜索" />
			<span>状态:</span>
        	<select class="jq-combobox" panelHeight="auto" style="width:100px" id="status">
				<option value="0">不限</option>
				<option value="1">入门</option>
				<option value="2">初级</option>
				<option value="3">中级</option>
				<option value="3">高级</option>
			</select>
			<a id="search" class="btn btn-sm btn-info"><i class="icon icon-search"></i>搜索</a>
			<a id="add" class="btn btn-sm btn-success"><i class="icon icon-add"></i>新增</a>
        	<a id="edit" class="btn btn-sm btn-warning"><i class="icon icon-edit"></i>编辑</a>
            <a id="delete" class="btn btn-sm btn-danger"><i class="icon icon-delete"></i>删除</a>
        </div>
    </div>   
	<div data-options="region:'center',border:false">
		<table class="jq-datagrid" fit="true" id="list" data-options="{
 			url: '${z:u('chapter/list')}',
			method:'post',
			columns: [[
				{field:'id',checkbox:true},
				{field:'title',title:'标题',width:20,sortable:'true'},
				{field:'remark',title:'备注',width:20,sortable:'true'},
				{field:'userId',title:'添加人',width:20,sortable:'true'},
				{field:'addTime',title:'添加时间',width:20,sortable:'true'},
				{field:'status',title:'状态',width:20,formatter:App.statusFmt,sortable:'true'},
			]]}">
		</table>
	</div>
</div>
<script type="text/javascript">
	
	$("#search").on("click", function() {
		var status = $("#status").combobox("getValue");
	 	var title = $("#title").val();
		$("#list").datagrid({
			url:'${z:u("/chapter/list")}?title='+title+"&status="+status
		});
	});
	
	$("#add").click(function(){
		App.popup('${z:u('chapter/list_add')}',{
			title:"添加章节"
		});
	});
	
	$("#edit").click(function(){
		var row = $(".jq-datagrid").datagrid("getSelected");
	if(row==null){
		$.messager.alert("提示","请先选择一条记录","warning");
	}else{
 			App.popup('${z:u('chapter/list_edit')}?id='+row.id, {
 				title:"章节编辑"
			});
		}
 	});
	
	$("#delete").click(function(){
		var row = $(".jq-datagrid").datagrid("getSelected");
		if(row == null){
			App.alert("请先选择一条记录","info");
		}else{
			App.ajax('${z:u("chapter/list_delete")}?id='+row.id,{
	            msg: "确定要删除此条记录吗？",
	            type: "POST"
	            });
		}
	});
	
</script>