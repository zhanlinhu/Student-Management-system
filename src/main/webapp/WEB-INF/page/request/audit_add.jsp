<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>用户管理</title>
    <link rel="stylesheet" href="${basePath}static/lib/layui-src/css/layui.css" media="all">
    <link rel="stylesheet" href="${basePath}static/lib/font-awesome-4.7.0/css/font-awesome.min.css" media="all">
    <link rel="stylesheet" href="${basePath}static/css/layuimini.css?v=2.0.1" media="all">
    <link rel="stylesheet" href="${basePath}static/css/themes/default.css" media="all">
    <link rel="stylesheet" href="${basePath}static/css/public.css" media="all">
</head>
<body>
<div class="layuimini-container layuimini-page-anim">
    <form class="layui-form">
        <div class="layui-form-item">
            <label class="layui-form-label">审批结果</label>
            <div class="layui-input-block">
                <select name="status" lay-verify="required">
                    <option value="3">通过</option>
                    <option value="4">拒绝</option>
                </select>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">备注</label>
            <div class="layui-input-block">
                <input type="text" name="type" value="${type}" class="layui-input">
                <input type="text" name="id" value="${id}" class="layui-input">
                <input type="text" name="result" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-primary layui-btn-sm data-add-btn">
                    <i class="fa fa-refresh"></i>
                    重置
                </button>
                <button class="layui-btn layui-btn-normal layui-btn-sm data-add-btn" lay-submit lay-filter="save">
                    <i class="fa fa-save"></i>
                    保存
                </button>
            </div>
        </div>
    </form>
</div>
<script src="${basePath}static/lib/layui-src/layui.js?<%=System.currentTimeMillis()%>" charset="utf-8"></script>
<script src="${basePath}static/js/lay-config.js?v=2.0.0" charset="utf-8"></script>
<script>
    layui.use(['form'], function () {
        var $ = layui.jquery,
            form = layui.form;

        var index = parent.layer.getFrameIndex(window.name);

            form.on('submit(save)', function (data) {
                $.ajax({
                    url:"${basePath}request/audit_add",
                    type:"POST",
                    dataType:'json',
                    data:data.field,
                    success:function(data){
                        layer.msg(data.msg,{time:500},function(){
                            parent.layer.close(index);
                        });
                    }
                });
                return false;

            });
    });
</script>

</body>
</html>
