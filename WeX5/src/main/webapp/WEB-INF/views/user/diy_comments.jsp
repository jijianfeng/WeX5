<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://zlzkj.com/tags" prefix="z"%>
<div class="jq-layout rel" data-options="fit:true">
	<div data-options="region:'north',border:false">
        <div id="grid-toolbar" class="clearfix p5">
           <a id="add" class="btn btn-sm btn-success"><i class="icon icon-add"></i>新增</a>
			<a id="edit" class="btn btn-sm btn-info"><i class="icon icon-edit"></i>编辑</a>
			<a id="delete" class="btn btn-sm btn-danger"><i class="icon icon-delete"></i>删除</a>
			<a id="download" class="btn btn-sm btn-warning">下载模块</a>
        </div>
    </div>
    <div data-options="region:'center',border:false">
		<table title="评阅书模块管理" fit="true" class="jq-datagrid" data-options="{
			url:'${z:u('user/diy_comments')}',
			method:'post',
			columns: [[
				{field:'id',checkbox:true},
				{field:'remark',title:'模板备注',width:100},
				{field:'uploadTime',title:'上传时间',width:100}
			]]}
			">
		</table>
	</div>
</div>
<script type="text/javascript">
	$("#add").on("click", function(){
		App.popup('${z:u("user/diy_add")}',"新增模板");
	});
	$("#edit,#delete").on("click", function(){
		var data = $(".jq-datagrid").datagrid("getSelected");
		if(data == null){
			App.alert("请先选择一条记录","warning");
		}else{
			var id = data.id;
			var eleId = $(this).attr("id");
			if(eleId == "edit"){
				App.popup('${z:u("user/diy_edit")}?id='+id,"编辑角色");
			}else if(eleId == "delete"){
				App.ajax("${z:u('user/diy_delete')}?id="+id);
			}
		}
	});
	
	$("#download").click(function(){
		var row = $(".jq-datagrid").datagrid("getSelected");
		if(row==null){
			App.alert("请选择一条记录","warning");
		}else{
			window.location.href = '${z:u("/user/download_diy")}?id='+row.id;
		}
	});
</script>