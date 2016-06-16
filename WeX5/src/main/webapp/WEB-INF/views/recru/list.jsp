<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://zlzkj.com/tags" prefix="z"%>

<div class="jq-layout rel" data-options="fit:true">
	<div data-options="region:'north',border:false">
        <div id="grid-toolbar" class="clearfix p5">
        	<span class="ml10">资讯搜索：</span>
			<input class="mr10" id="title" type="text"  placeholder="输入资讯关键词搜索" />
			<span>状态:</span>
        	<select class="jq-combobox" panelHeight="auto" style="width:100px" id="status">
				<option value="0">不限</option>
				<option value="1">未解决</option>
				<option value="2">已解决</option>
			</select>
			<a id="search" class="btn btn-sm btn-info"><i class="icon icon-search"></i>搜索</a>
        	<a id="detail" class="btn btn-sm btn-warning"><i class="icon icon-edit"></i>详情</a>
        	<a id="accept" class="btn btn-sm btn-success"><i class="icon icon-accept"></i>审核通过</a>
        	<a id="refuse" class="btn btn-sm btn-danger"><i class="icon icon-cancel"></i>禁用记录</a>
        	<a id="delete" class="btn btn-sm btn-danger"><i class="icon icon-delete"></i>删除</a>
        </div>
    </div>
	<div data-options="region:'center',border:false">
		<table class="jq-datagrid" fit="true" id="list" data-options="{
 			url: '${z:u('recru/list')}',
			method:'post',
			columns: [[
				{field:'id',checkbox:true},
				{field:'job',title:'职位',width:20,sortable:'true'},
				{field:'description',title:'描述',width:20,sortable:'true'},
				{field:'yearSalary',title:'年薪（万/年）',width:20,sortable:'true'},
				{field:'jobTag',title:'职位标签',width:20,sortable:'true'},
				{field:'requireTag',title:'职位要求',width:20,sortable:'true'},
				{field:'addUser',title:'添加人',width:20,sortable:'true'},
				{field:'addTime',title:'添加时间',width:20,sortable:'true'},
				{field:'endTime',title:'结束时间',width:20,sortable:'true'},
				{field:'status',title:'状态',width:20,formatter:App.statusFmt,sortable:'true'}
			]]}">
		</table>
	</div>
</div>
<script type="text/javascript">
	
	$("#search").on("click", function() {
		var status = $("#status").combobox("getValue");
	 	var title = $("#title").val();
		$("#list").datagrid({
			url:'${z:u("recru/list")}?title='+title+"&status="+status
		});
	});

	$("#detail").click(function(){
		var row = $(".jq-datagrid").datagrid("getSelected");
		if(row==null){
			$.messager.alert("提示","请先选择一条记录","warning");
		}else{
 			App.popup('${z:u('recru/list_detail')}?id='+row.id, {
 				title:"查看详情"
			});
		}
 	});
	
	$("#accept").click(function(){
		var row = $(".jq-datagrid").datagrid("getSelected");
		if(row==null){
			$.messager.alert("提示","请先选择一条记录","warning");
		}
		else if(row.status==1){
			App.alert("该记录已经审核通过了!","warning");
		}
		else{
			App.ajax('${z:u("/recru/list_accept")}?id='+row.id,{
	            msg: "确定要审核通过吗？",
	            type: "POST"
	        });
		}
 	});
	
	$("#refuse").click(function(){
		var row = $(".jq-datagrid").datagrid("getSelected");
		if(row==null){
			$.messager.alert("提示","请先选择一条记录","warning");
		}
		else if(row.status==0){
			App.alert("该记录已经禁用了!","warning");
		}
		else{
			App.ajax('${z:u("/recru/list_refuse")}?id='+row.id,{
	            msg: "确定要禁用吗？",
	            type: "POST"
	        });
		}
 	});
	
	$("#delete").click(function(){
		var row = $(".jq-datagrid").datagrid("getSelected");
		if(row == null){
			App.alert("请先选择一条记录","info");
		}else{
			App.ajax('${z:u("/link/list_delete")}?id='+row.id,{
	            msg: "确定要删除此条回复吗？",
	            type: "POST"
	            });
		}
	});
 	

	
</script>