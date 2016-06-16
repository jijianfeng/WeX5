<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://zlzkj.com/tags" prefix="z" %>
<form id="form" action="${__url__}" method="post">
 <c:forEach items ="${node}" var="v0">
    <div class="access-box">
        <div class="hd">
            <label>
                <strong>${v0.name}</strong>
            </label>
        </div>
        <div class="bd">
         <c:forEach items ="${v0.children}" var="v1">
		     <div class="m-box J_checkBox mb10">
		        <div class="hd pl10">
		            <label>
		                <input type="checkbox" class="module_cbo" name="nodeId" value="${v1.id}" ${v1.isCheck==true?'checked':''}>
		                &nbsp;<strong>${v1.name}</strong>
		            </label>
		        </div>
		        <div class="bd pl10 pt10">
		         <c:forEach items ="${v1.children}" var="v2">
		            <div class="rule_check mb10">
		                <div style="margin-bottom: 5px;">
		                    <label class="checkbox">
		                        <input class="rules_row" type="checkbox" name="nodeId" value="${v2.id}" ${v2.isCheck==true?'checked':''}>&nbsp;${v2.name}
		                    </label>
		                </div>
		                <span class="child_row">
		                 	<c:forEach items ="${v2.children}" var="v3">
		                    <label class="checkbox ml20"><input class="auth_rules" type="checkbox" name="nodeId" value="${v3.id}" ${v3.isCheck==true?'checked':''}>&nbsp;${v3.name}</label>
		                    </c:forEach>
		                </span>
		            </div>
		            </c:forEach>
		        </div>
		    </div>
            </c:forEach>
        </div>
    </div>
    </c:forEach>
   <!--  <div class="m-box J_checkBox mb10">
        <div class="hd pl10">
            <label>
                <input type="checkbox" class="module_cbo" name="node_id[]" value="190" checked="checked">
                &nbsp;<strong>全局设置</strong>
            </label>
        </div>
        <div class="bd pl10 pt10">
            <div class="rule_check mb10">
                <div style="margin-bottom: 5px;">
                    <label class="checkbox">
                        <input class="rules_row" type="checkbox" name="node_id[]" value="191" checked="checked">&nbsp;单位管理
                    </label>
                </div>
                <span class="child_row"> 
                    <label class="checkbox ml20"><input class="auth_rules" type="checkbox" name="node_id[]" value="214">&nbsp;新增</label><label class="checkbox ml20"><input class="auth_rules" type="checkbox" name="node_id[]" value="215">&nbsp;编辑</label><label class="checkbox ml20"><input class="auth_rules" type="checkbox" name="node_id[]" value="215">&nbsp;删除</label>
                </span>
            </div>
        </div>
    </div>
    <div class="m-box J_checkBox mb10">
        <div class="hd pl10">
            <label>
                <input type="checkbox" class="module_cbo" name="node_id[]" value="190" checked="checked">
                &nbsp;<strong>单位设置</strong>
            </label>
        </div>
        <div class="bd pl10 pt10">
            <div class="rule_check mb10">
                <div style="margin-bottom: 5px;">
                    <label class="checkbox">
                        <input class="rules_row" type="checkbox" name="node_id[]" value="191" checked="checked">&nbsp;单位管理
                    </label>
                </div>
                <span class="child_row"></span>
            </div>
            <div class="rule_check mb10">
                <div style="margin-bottom: 5px;">
                    <label class="checkbox">
                        <input class="rules_row" type="checkbox" name="node_id[]" value="191" checked="checked">&nbsp;大棚管理
                    </label>
                </div>
                <span class="child_row"> 
                    <label class="checkbox ml20"><input class="auth_rules" type="checkbox" name="node_id[]" value="214">&nbsp;新增</label><label class="checkbox ml20"><input class="auth_rules" type="checkbox" name="node_id[]" value="215">&nbsp;编辑</label><label class="checkbox ml20"><input class="auth_rules" type="checkbox" name="node_id[]" value="215">&nbsp;删除</label>
                </span>
            </div>
        </div> 
    </div>-->
    <div class="form-actions">
        <button type="submit"  class="btn btn-success">确定授权</button>
    </div>
</form>
<script>
    // 全选
    $(".J_checkBox").each(function() {
        var ckAll = $(this).find('.module_cbo');
        var rulesRow = $(this).find('.rules_row');
        var ckItem = $(this).find("input[name='node_id[]']");
        var ckNode = $(this).find(".auth_rules");
        var rulesRowLen = rulesRow.length;
        ckAll.on('click',function() {
            ckItem.prop("checked",this.checked);
        });
        rulesRow.each(function() {
            var ckItem2 = $(this).parents(".rule_check").find(".auth_rules");
            var len = ckItem2.length;
            $(this).on('click',function() {
                // 给b绑定判断事件
                var b=rulesRow.filter(":checked").length==rulesRowLen;
                // 通过三元运算判断
                var flag=ckAll.prop("checked",b?true:false);
                ckItem2.prop("checked",this.checked);
            });
            // ckItem2.on('click',function() {
            //  var b=ckNode.filter(":checked").length==ckNode.length;
            //  var b2=ckItem2.filter(":checked").length==len;
            //  var flag=ckAll.prop("checked",b?true:false);
            //  var flag2=$(this).parents(".rule_check").find(".rules_row").prop("checked",b2?true:false);
            // });
        });
    });
</script>
