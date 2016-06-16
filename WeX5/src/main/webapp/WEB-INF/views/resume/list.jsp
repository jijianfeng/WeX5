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
 			url: '${z:u('resume/list')}',
			method:'post',
			columns: [[
				{field:'id',checkbox:true},
				{field:'workTime',title:'已工作年数',width:20,sortable:'true'},
				{field:'expectedSalary',title:'期待年薪（万/年）',width:20,sortable:'true'},
				{field:'workStatus',title:'目前工作状态',width:20,sortable:'true',formatter:statusFormatter},
				{field:'quality',title:'学历',width:20,sortable:'true',formatter:typeFormatter},
				{field:'userSchool',title:'毕业学校',width:20,sortable:'true'},
				{field:'userIntro',title:'自我简介',width:20,sortable:'true'},
				{field:'userTag',title:'个人标签',width:20,sortable:'true'},
				{field:'addUser',title:'添加用户',width:20,sortable:'true'},
				{field:'addTime',title:'添加时间',width:20,sortable:'true'},
				{field:'status',title:'状态',width:20,formatter:App.statusFmt,sortable:'true'}
			]]}">
		</table>
	</div>
</div>
<script type="text/javascript">
	
	function typeFormatter(v) {
	 	var val;
		if (v == 3) {
			val = '<font color="red">博士及以上</font>';
		} else if (v == 2) {
			val = '<font color="blue">硕士</font>';
		} else if (v == 1) {
			val = '<font color="green">本科</font>';
		}else if (v == 0){
	        val = '<font color="black">专科及以下</font>';
		}
		return val;
	}
	//1：在职；2：想跳槽；3：离职，正在找工作；4：在职，无跳槽打算
	function statusFormatter(v) {
	 	var val;
		if (v == 4) {
			val = '<font color="red">在职，无跳槽打算</font>';
		} else if (v == 3) {
			val = '<font color="blue">离职，正在找工作</font>';
		} else if (v == 2) {
			val = '<font color="green">想跳槽</font>';
		}else if (v == 1){
	        val = '<font color="black">在职</font>';
		}
		return val;
	}
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
 	
	$("#accept").click(function(){
		var row = $(".jq-datagrid").datagrid("getSelected");
		if(row==null){
			$.messager.alert("提示","请先选择一条记录","warning");
		}
		else if(row.status==1){
			App.alert("该记录已经审核通过了!","warning");
		}
		else{
			App.ajax('${z:u("/resume/list_accept")}?id='+row.id,{
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
			App.ajax('${z:u("/resume/list_refuse")}?id='+row.id,{
	            msg: "确定要禁用吗？",
	            type: "POST"
	        });
		}
 	});
	
</script>