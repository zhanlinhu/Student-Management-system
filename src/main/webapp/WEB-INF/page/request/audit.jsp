<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>用户管理</title>
</head>
<body>
<div class="layuimini-container layuimini-page-anim">
    <div class="layuimini-main">
        <div style="margin: 10px">
            <form class="layui-form layui-form-pane">
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">标题</label>
                        <div class="layui-input-inline">
                            <input type="text" name="title" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <button class="layui-btn layui-btn-primary"  lay-submit lay-filter="search-btn"><i class="layui-icon"></i> 搜 索</button>
                    </div>
                </div>
            </form>
        </div>
        <script type="text/html" id="toolbar">
            <div class="layui-btn-container">
                <button class="layui-btn layui-btn-normal layui-btn-sm data-add-btn" lay-event="add">
                    <i class="fa fa-plus"></i>
                    审批
                </button>
            </div>
        </script>
        <table class="layui-hide" id="currentTableId" lay-filter="currentTableFilter"></table>
    </div>
</div>
<script>
    layui.use(['form', 'table'], function () {
        var $ = layui.jquery,
            form = layui.form,
            table = layui.table;
            table.render({
                elem: '#currentTableId',
                url: '${basePath}/request/audit',
                contentType:'application/json',
                method:"post",
                toolbar: '#toolbar',
                defaultToolbar: ['filter', 'exports', 'print'],
                page: true,
                cols: [[
                    {type: "checkbox", width: 50},
                    {field: 'id', width: 80, title: 'ID'},
                    {field: 'title',  title: '标题'},
                    {field: 'type',  title: '类型'},
                    {field: 'createDate',  title: '创建日期'},
                    {field: 'statusTxt',  title: '状态'}
                ]],
                skin: 'line'
            });

        // 监听搜索操作
        form.on('submit(search-btn)', function (data) {
            //执行搜索重载
            table.reload('currentTableId', {
                contentType:'application/json',
                where: data.field
            }, 'data');
            return false;
        });
        form.on('submit(audit-btn)', function (data) {
            console.log(data);
        });

        /**
         * toolbar事件监听
         */
        table.on('toolbar(currentTableFilter)', function (obj) {

            if (obj.event === 'add') {   // 监听添加操作
                var checkStatus = table.checkStatus('currentTableId');
                var data = checkStatus.data;
                if(data.length !=1){
                    layer.msg("请选择一行数据审批",{time:1000});
                    return;
                }


                var index = layer.open({
                    title: '添加用户',
                    type: 2,
                    shade: 0.2,
                    shadeClose: false,
                    area: ['50%', '50%'],
                    content: "${basePath}request/audit_add?id="+data[0].id,
                    end:function(){
                        table.reload('currentTableId');
                    }
                });
            }
        });
    });
</script>
<%--<form class="layui-form" id="auditForm">
    <div class="layui-form-item">
        <div class="layui-input-block">
            <label class="layui-form-label">审批</label>
            <select name="status" lay-filter="status" lay-verify="required">
                <option value="">请选择</option>
                <option value="1">通过</option>
                <option value="0">拒绝</option>
            </select>
        </div>
        <div class="layui-input-block">
            <label class="layui-form-label">结果描述</label>
            <div class="layui-input-inline">
                <input type="text" name="result" class="layui-input">
                <input type="hidden" name="type" value="${type}" class="layui-input">
            </div>
        </div>
        <div class="layui-inline">
            <button class="layui-btn layui-btn-primary"  lay-submit lay-filter="audit-btn"><i class="layui-icon"></i>保存</button>
        </div>
    </div>
</form>--%>
</body>
</html>
