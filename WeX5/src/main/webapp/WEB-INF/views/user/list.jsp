<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://zlzkj.com/tags" prefix="z"%>

<div class="jq-layout rel" data-options="fit:true">
	<div data-options="region:'north',border:false">
        <div id="grid-toolbar" class="clearfix p5">
        	<span>所属大学:</span>
            <input type="text"  id="universityName" name="universityName" style="width:130px" />
		    <span>身份:</span>
        	<select class="jq-combobox" panelHeight="auto" style="width:110px" id="userType">
				<option value="0">全部</option>
				<option value="1">学校研究生部</option>
				<option value="2">学院</option>
				<option value="3">老师</option>
			</select>
		    <a id="search" class="btn btn-sm btn-success"><i class="icon icon-search"></i>搜索</a>
            <a id="detail" class="btn btn-sm btn-warning"><i class="icon icon-search"></i>查看更多信息</a>
            <a id="delete" class="btn btn-sm btn-danger"><i class="icon icon-delete"></i>删除</a>
            <a id="accept" class="btn btn-sm btn-success"><i class="icon icon-accept"></i>启用用户</a>
            <a id="cancel" class="btn btn-sm btn-danger"><i class="icon icon-cancel"></i>禁用用户</a>
            <a id="restart" class="btn btn-sm btn-warning"><i class="icon icon-edit"></i>重置密码</a>
            <a id="ressetphont" class="btn btn-sm btn-danger"><i class="icon icon-edit"></i>重置绑定手机</a>
        </div>
    </div>
	<div data-options="region:'center',border:false">
		<table class="jq-datagrid" fit="true" id="list" data-options="{
			url: '${z:u('user/list')}',
			method:'post',
			pageList:[20,50,100,500,1000,3000],
			columns: [[
				{field:'id',checkbox:true},
				{field:'nickName',title:'用户名',width:100,sortable:'true'},
				{field:'roleId',title:'身份',width:100,sortable:'true'}, 
				{field:'userSex',title:'性别',width:100,sortable:'true'},
 				{field:'userPhone',title:'电话号码',width:100,sortable:'true'},
 				{field:'loginTime',title:'最后一次登录',width:100,sortable:'true'},
 				{field:'status',title:'状态',width:100,formatter:App.statusFmt,sortable:'true'}
			]]}">
		</table>
	</div>
</div>
<script type="text/javascript">
	$("#invite").click(function(){
		var row = $(".jq-datagrid").datagrid("getSelected");
		if(row==null){
			$.messager.alert("提示","请先选择一条记录","warning");
		}
		else if(row.isValue==1){
			$.messager.alert("提示","该用户已经激活","warning");
		}
		else{
	//			var eleId = $(this).attr("id");
			App.popup('${z:u("user/invite_people")}?id='+row.id, {
				title: "详情",
				width: 350,
				height: 180
			});
		}
	});
	$("#addCollege").click(function(){
		App.popup('${z:u('user/college_user_add')}', {
			title:"添加学院",
			width: 400,
	 		height: 320
		});
	});
	
	$("#addColleges").click(function(){
		App.popup('${z:u('user/college_user_adds')}', {
			title:"批量添加学院"
		});
	});
	
	$("#addUser").click(function(){
		App.popup('${z:u('user/teacher_user_add')}', {
			title:"添加老师",
			width: 400,
	 		height: 320
		});
	});
	
	$("#addUserAdmin").click(function(){
		App.popup('${z:u('user/teacher_user_add_admin')}', {
			title:"添加老师",
			width: 400,
	 		height: 320
		});
	});
	
	$("#addUsersAdmin").click(function(){
		App.popup('${z:u('user/teacher_user_adds_admin')}', {
			title:"批量添加"
		});
	});
	
	$("#search").on("click", function() {
		var userType= $("#userType").combobox("getValue");
	 	var universitya = $("#universityName").val();
		$("#list").datagrid({
			url:'${z:u("/user/list")}?userType='+userType+"&universityName="+universitya
		});
	});
	
	$("#empty").on("click", function() {
		document.getElementById('universitya').value='';
		document.getElementById('universityName').value='';
		$('#userType').combobox('setValues','0');
	});
	
	$("#ressetphont").on("click", function() {
		var row = $(".jq-datagrid").datagrid("getSelected");
		if(row==null){
			$.messager.alert("提示","请先选择一条记录","warning");
		}else{
			App.ajax('${z:u("/user/reset_phone")}?id='+row.id,{
	          	 type: "POST",
	          	 msg:"你确定要重置改用户的绑定手机吗？"
	        });
		}
	});

	$("#detail").click(function(){
		var row = $(".jq-datagrid").datagrid("getSelected");
		if(row==null){
			$.messager.alert("提示","请先选择一条记录","warning");
		}else{
			var eleId = $(this).attr("id");
			App.popup('${z:u("user/detail")}?id='+row.id, {
				title: "详情",
				width: 800,
				height: 400
			});
		}
	});
	
	$("#messEdit").click(function(){
		var row = $(".jq-datagrid").datagrid("getSelected");
		if(row==null){
			$.messager.alert("提示","请先选择一条记录","warning");
		}else{
			//var eleId = $(this).attr("id");
			App.popup('${z:u("user/messEdit")}?id='+row.id, {
				title: "用户编辑",
				width: 600,
				height: 550
			});
		}
	});
	
	$("#edit,#delete").click(function(){
		var row = $(".jq-datagrid").datagrid("getSelected");
		if(row == null){
			App.alert("请先选择一条记录","warning");
		}else{
			var eleId = $(this).attr("id");
			if(eleId=="edit"){
				App.popup('${z:u("/user/edit")}?id='+row.id,"编辑节点");
				
			}else if(eleId=="delete"){
	            App.ajax('${z:u("/user/deletes")}?id='+row.id,{
	           	 type: "POST",
	           	 msg:"你确定要删除所选的数据吗？"
	            });
			}
		}
	});
	
	$("#restart").click(function(){
		var row = $(".jq-datagrid").datagrid("getSelected");
		if(row == null){
			App.alert("请先选择一条记录","warning");
		}
		else{
			App.ajax('${z:u("user/restart")}?id='+ row.id, {
		        msg: "确定要重置该用户密码吗?",
		        type: "POST",
		        success : function(data) {
		        	alert(data.info);
 				}
	    	});
		}
	});
	
	$("#accept").click(function(){
		var row = $(".jq-datagrid").datagrid("getSelected");
		if(row == null){
			App.alert("请先选择一条记录","warning");
		}
		else if(row.isValue==1){
			App.alert("该用户已经被启用了","warning");
		}
		else{
			App.ajax('${z:u("user/accept")}?id='+ row.id, {
	        msg: "确定要启用该用户吗？",
	        type: "POST",
	    	});
		}
	});
	
	$("#cancel").click(function(){
		var row = $(".jq-datagrid").datagrid("getSelected");
		if(row == null){
			App.alert("请先选择一条记录","warning");
		}
		else if(row.isValue==0){
			App.alert("该用户已经被禁用了","warning");
		}
		else{
			App.ajax('${z:u("user/cancel")}?id='+ row.id, {
	        msg: "确定要禁用该用户吗？",
	        type: "POST",
// 	        success: function(data) {
// 				if(data.status == 1) {
// 					alert("dad");
// 				}else {
// 					alert("dad");
// 				}
// 			}
	    	});
		}
	});
	
	$("#look_university").on("click", function() {
		App.dialog({
			title: "选择学校",
			href: "${z:u('public/university_select?id=7')}",
			width: 800,
			height: 600
		});
	});
</script>