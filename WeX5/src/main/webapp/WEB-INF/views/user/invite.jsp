<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://zlzkj.com/tags" prefix="z"%>

<div class="jq-layout rel" data-options="fit:true">
	<div data-options="region:'north',border:false">
        <div id="grid-toolbar" class="clearfix p5">
        	<span>所属大学:</span>
            <input type="text" id="universityName" name="universityName" style="width:130px" />
<!--             <input type="hidden" id ="universitya" name="universitya" value=""> -->
<!-- 		    <button class="btn btn-xs btn-primary" id="look_university" type="button">查找</button> -->
		    <span>身份:</span>
        	<select class="jq-combobox" panelHeight="auto" style="width:110px" id="userType">
				<option value="0">全部</option>
				<option value="1">学校研究生部</option>
				<option value="2">学院</option>
				<option value="3">老师</option>
			</select>
		    <a id="search" class="btn btn-sm btn-success"><i class="icon icon-search"></i>搜索</a>
            <button class="btn btn-sm btn-info" id="empty">清空</button>
            <a id="invite" class="btn btn-sm btn-warning"><i class="icon icon-edit"></i>邀请开通</a>
        </div>
    </div>
	<div data-options="region:'center',border:false">
		<table class="jq-datagrid" fit="true" id="list" data-options="{
			url: '${z:u('user/invite')}',
			method:'post',
			pageList:[20,50,100,500,1000,3000],
			columns: [[
				{field:'id',checkbox:true},
				{field:'userName',title:'帐号',width:100,sortable:'true'},
				{field:'nickName',title:'用户姓名',width:100,sortable:'true'},
				{field:'roleId',title:'身份',width:100,sortable:'true'}, 
				{field:'userSex',title:'性别',width:100,sortable:'true'},
 				{field:'userPhone',title:'电话号码',width:100,sortable:'true'},
 				<c:if test="${sessionScope.userType==3}">
 				{field:'sendCode',title:'送审码',width:100,sortable:'true'},
 				</c:if>
 				{field:'universityName',title:'所属大学',width:100,sortable:'true'},
 				{field:'isValue',title:'有无激活',width:100,formatter:App.statusFmt,sortable:'true'}, 
			]]}">
		</table>
	</div>
</div>
<script type="text/javascript">
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
	

	$("#invite").click(function(){
		var row = $(".jq-datagrid").datagrid("getSelected");
		if(row==null){
			$.messager.alert("提示","请先选择一条记录","warning");
		}
		else if(row.isValue==1){
			$.messager.alert("提示","该用户已经激活","warning");
		}
		else{
// 			var eleId = $(this).attr("id");
			App.popup('${z:u("user/invite_people")}?id='+row.id, {
				title: "详情",
				width: 350,
				height: 180
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