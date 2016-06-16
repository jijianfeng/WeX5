<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://zlzkj.com/tags" prefix="z"%>
<table id="pg" class="jq-propertygrid" url='${z:u("user/detail")}?id=${id}' data-options="{
			method:'post',
			rownumbers: false,
			pagination: false,
			columns:[[
        		{field:'name',title:'名称',width:200},
        		{field:'value',title:'值',width:600}
    		]],
    		autoRowHeight:false,
    		rowStyler: function(index,row){
				return 'height:33px';
		},
		onLoadSuccess: gridLoad
	}">
</table>
<div id="msg" style="width: 100%; display: none;"></div>
<script type="text/javascript">
function gridLoad() {
	$(".J_imgShow").each(function() {
		$(this).fancybox();
	});
}
</script>