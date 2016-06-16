<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://zlzkj.com/tags" prefix="z"%>

<form id="form" action="${__url__}" method="post">
    <table align="left" class="form-table" style="margin:40px 50px">
    	<tr>
			<td>姓名：</td>
			<td colspan="4" >
				<input class="w200" name="name" disabled="disabled" class="jq-validatebox"  value='${user.nickName}' type="text" data-options="required:true">
			</td>
		</tr>
		<tr>
			<td>电话号码：</td>
			<td colspan="4" >
				<input class="w200" name="userPhone" id="userPhone" class="jq-validatebox"  value='${user.userPhone}' type="text" data-options="required:true">
			</td>
		</tr>
		<tr>
			<td>邮箱：</td>
			<td colspan="4" >
				<input  style="width:150px" name="userMail" id="userMail" type="text" value="${user.userMail}"  data-options="required:true"  ></input>
			</td>
		</tr>
		<tr>
			<td>地址邮编：</td>
			<td colspan="4" >
				<input  style="width:150px" name="userAddress" id="userAddress" value="${user.userAddress}" type="text"  data-options="required:true"  ></input>
			</td>
		</tr>
		<c:if test="${user.userType==0||user.userType==1}">
		<tr>
			<td>送审码：</td>
			<td colspan="4" >
				<input  style="width:150px" name="sendCode" id="sendCode" type="text" value="${user.sendCode}"  data-options="required:true"  ></input>
			</td>
		</tr>
		<tr>
			<td>职务：</td>
			<td colspan="4" >
				<input  style="width:150px" name="workName" id="workName" value="${user.workName}" type="text"  data-options="required:true"  ></input>
			</td>
		</tr>
		<tr>
			<td>座机：</td>
			<td colspan="4" >
				<input  style="width:150px" name="localTel" id="localTel" value="${user.localTel}" type="text"  data-options="required:true"  ></input>
			</td>
		</tr>
		</c:if>
		<tr>
			<td>身份证号：</td>
			<td colspan="4" >
				<input  style="width:150px" name="identifyNo" id="identifyNo" type="text" value="${user.identifyNo}"  data-options="required:true"  ></input>
			</td>
		</tr>
		<tr>
			<td>性别：</td>
			<td colspan="4" >
				<input  name="userSex" type="radio" value="0"
				<c:if test="${user.userSex==0}">
	           	checked="checked"
			    </c:if>
				>女&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input  name="userSex" type="radio" value="1"
				<c:if test="${user.userSex==1}">
	           	checked="checked"
			    </c:if>
				>男
			</td>
		</tr>
		<c:if test="${sessionScope.userType==2}">
		<tr>
			<td>职称：</td>
			<td>
				<select name="titlesId" class="jq-combobox" id="titlelist"  value="${user.titlesId}" style="width:150px" data-options="{
			    	required:'true',
					method:'post',
					editable:true,
					url: '${z:u('public/titlelist')}'}">
					<option value="${user.titlesId}" selected="selected">
				</select>
			</td>
		</tr>
		<tr>
			<td>教授职称：</td>
			<td>
				<select name="userTitle" class="jq-combobox" id="titlelist" style="width:150px">
					<option value="0">博导</option>
					<option value="1">硕导</option>
					<option value="${user.userTitle}" selected="selected">
				</select>
			</td>
		</tr>
		<tr>
			<td>擅长一级专业：</td>
			<td colspan="4" >
				<select class="jq-combobox" id="majorlista"  name="majora" data-options="{
    	        	url: '${z:u('public/majorOneList')}',
    	        	required:true,
					method:'post',
					editable:true,
					}">
				</select>
			</td>
		</tr>
		<tr>
			<td>擅长二级专业：</td>
			<td colspan="4" >
				<select class="jq-combobox" id="majorlistb"  name="majorb" data-options="{
    	        	url: '${z:u('public/major_two_list')}'+'?id=0',
    	        	required:true,
					method:'post',
					editable:true,
					}">
				</select>
			</td>
		</tr>
		</c:if>
		<tr>
			<td>所在银行：</td>
			<td colspan="4" >
				<input class="w200" name="visaBank" id="visaBank" class="jq-validatebox" value="${user.visaBank}" type="text" data-options="required:true">
			</td>
		</tr>
		<tr>
			<td>银行卡号：</td>
			<td colspan="4" >
				<input class="w200" name="visaCard" id="visaCard" class="jq-validatebox" value="${user.visaCard}" type="text" data-options="required:true">
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>
				<button type="submit" class="btn btn-small btn-success">确定</button>
				<a class="btn btn-primary btn-small" href="">返回</a>
			</td>
		</tr>
	</table>
</form>
<script>
// 	$("#form").ajaxForm({
// 		type: "post",
// 		dataType: "json",
// 		beforeSubmit: function() {
// 			var pass = $("#pass").val();
// 			var again = $("#again").val();
// 			if (pass != again) {
// 				App.alert("两次密码不一致","warnning");
// 				return false;
// 			}
// 		},
// 		success: function(data) {
// 			if(data.status == 1) {
// 				App.alert("修改成功","success");
// 				location.reload();
// 			}else {
// 				App.alert(data.info,"warnning");
// 			}
// 		}
// 	});

// 	document.getElementById("sendCode").onclick=function(){
// 		var userPhone = $("#userPhone").val();
// 		time(this);
// 		$.ajax({
// 			url: '${z:u("/user/send_code")}?id='+${user.id}+"&userPhone="+userPhone,
//             msg: "这里会发送验证码到您的绑定手机，如果暂无绑定会发给您输入的手机号码！",
//             type: "POST",
//             dataType : "json",
//             success : function(result) {
//             	time(this);
//             	alert(result.status);
//             	if(result.status == 1){
//             		var btn = document.getElementById("sendCode");
//             		time(btn);
// 				}
//             	else{
//             		alert(result.info);
//             	}
// 			}
//         });
// 	}; 
	
// 	var wait=60; 
// 	function time(o) { 
//         if (wait == 0) { 
//             o.removeAttribute("disabled");           
//             o.value="免费获取验证码"; 
//             wait = 60; 
//         } else { 
//             o.setAttribute("disabled", true); 
//             o.value=wait+"秒后可以重新发送"; 
//             wait--; 
//             setTimeout(function() { 
//                 time(o); 
//             }, 
//             1000); 
//         } 
//     }
	
	$("#majorlista").combobox({
		editable:false,
		onChange:function(){
			//alert("开始");
			var n = $("#majorlista").combobox("getValue");
			$('#majorlistb').combobox('reload', '${z:u('public/major_two_list')}'+"?id="+n);
			$('#majorlistb').combobox('setValues','0');
		}
	});
</script>