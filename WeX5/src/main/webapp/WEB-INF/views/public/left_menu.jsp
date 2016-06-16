<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://zlzkj.com/tags" prefix="z"%>

<div class="jq-accordion" fit="true" data-options="animate:false,border:false,multiple:true">
	<c:forEach var="node" items="${leftMenuList}" varStatus="statusMain">
		<div class='${statusMain.first?"first":""} main-menu' title="${node.name }" data-options="iconCls:'icon-application-cascade'">
			<ul class="sub-menu" data-group="${node.name }">
				<c:forEach var="subNode" items="${node.children}" varStatus="statusSub">
<!-- 					<c:if test="${sessionScope.userType!=2||subNode.id!=13}"> -->
<!-- 						<c:if test="${sessionScope.userType!=2||subNode.id!=156}"> -->
 					 		 <li class='${statusSub.first?"first":""}' data-hash="/${subNode.url}"><a href='${z:u(subNode.url) }'>${subNode.name }</a></li> 
<!-- 						</c:if> -->
<!-- 					</c:if>				 -->
				</c:forEach>
			</ul>
		</div>
	</c:forEach>
</div>