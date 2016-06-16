<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://zlzkj.com/tags" prefix="z"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>500-系统异常</title>
</head>
<body>
	<div style="padding:10px;">
		<h3>500，系统发生异常！</h3>
		<br/>
		错误信息为：
		<br/>
		${errorInfo }
	</div>
</body>
</html>