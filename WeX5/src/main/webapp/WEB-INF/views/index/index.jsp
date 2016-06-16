<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://zlzkj.com/tags" prefix="z" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>WeX5后台管理系统</title>
	<link href="${__static__}/admin/ui/jqui.min.css" rel="stylesheet" type="text/css"/>
	<link href="${__static__}/admin/css/admin.css" rel="stylesheet" type="text/css"/>
	<%--<link href="${__static__}/admin/css/style.css" rel="stylesheet" type="text/css"/>--%>
	<link href="${__static__}/mod/fancybox/jquery.fancybox.css" rel="stylesheet" media="screen" />
	<script>
		var ZLZ = window.ZLZ = {
			"ROOT"   : "${__root__}",
			"URL"    : "${__url__}",
			"STATIC" : "${__static__}"
		};
	</script>
</head>
<body class="jq-layout" style="position:static;">


<div id="header" data-options="region:'north',border:false">
	<div id="logo">
		<img src="${__static__}/admin/img/logo.png" />
	</div>
	<div id="nav-bar" class="fix">
		<div class="fl nav-content">
			<ul id="nav-menu" class="fix">
				<c:forEach var="node" items="${topMenuList}" varStatus="status">
					<li class='${status.first?"first":""}${status.last?"last":""} fl'>
						<a href='${z:u("/left_menu") }/${node.id }'>${node.name }</a>
					</li>
				</c:forEach>
			</ul>
		</div>
 	</div> 
</div>

<div id="left" data-options="region:'west',headerCls:'left-header',iconCls:'icon-chart-organisation',title:'子功能菜单'">
</div>
<div id="center" data-options="region:'center'">
	<div class="jq-layout rel" data-options="fit:true">
		<div  data-options="region:'north',border:false">
			<div id="crumb" class="fix">
				<div class="fl">
					<em class="c1 icon icon-resultset-next"></em>
					<em class="c2"></em>
					<em class="c3"></em>
					<em class="c4"></em>
				</div>
				<div class="fr">
					<span class="mr10">欢迎您，${sessionScope.loginName}</span>
					<c:if test="${sessionScope.userType==0}">
						<span class="mr10">，送审码：${sessionScope.code}</span>
					</c:if>
					<a class="icon icon-door-out" href='${z:u("/public/logout")}'>安全退出</a>
				</div>
			</div>
		</div>
		<div id="content" data-options="region:'center',border:false" ></div>
		<div>
<!-- 		<input type="hidden" value="${isAll}" id="isAll"> -->
		</div>
	</div>
</div>


<!-- js section -->
<script src="${__static__}/mod/jquery/jquery.min.js" type="text/javascript"></script>
<script src="${__static__}/admin/ui/jqui.min.js" type="text/javascript"></script>
<script src="${__static__}/mod/jquery/jquery.hashchange.min.js" type="text/javascript"></script>
<script src="${__static__}/admin/js/admin.min.js" type="text/javascript"></script>
<script src="${__static__}/mod/kindEditor/kindeditor.min.js" type="text/javascript"></script>
<script src="${__static__}/mod/fancybox/jquery.fancybox.min.js" type="text/javascript"></script>
<script src="${__static__}/mod/jquery/jquery.form.js" type="text/javascript"></script>
<!-- <script src="${__static__}/mod/webupload/webuploader.min.js" type="text/javascript"></script> -->
<script src="${__static__}/mod/jquery/jquery.hashchange.min.js" type="text/javascript"></script>

<!-- fancyplayer -->
<!-- <script type="text/javascript" src="${__static__}/mod/fancyplayer/js/flowplayer-3.1.1.min.js"></script> -->
<!-- <script type="text/javascript" src="${__static__}/mod/fancyplayer/js/jquery.fancybox-1.2.1.pack.js"></script> -->
<!-- <script type="text/javascript" src="${__static__}/mod/fancyplayer/js/jquery.easing.1.3.js"></script> -->
<!-- <script type="text/javascript" src="${__static__}/mod/fancyplayer/js/fancyplayer.js"></script> -->
	
<script type="text/javascript">
	//首次进入加载第一个左侧菜单
	if(window.location.hash==""){
		$("#nav-menu li.first").click();
	}
	function timeout(){
	    alert("界面超时，请重新登录");
	    window.location.href="${z:u("/public/logout")}";
    }
    window.setTimeout("timeout()", 60*60*1000);
</script>
<!--end of js section -->
</body>
</html>