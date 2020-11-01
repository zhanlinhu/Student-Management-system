<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${basePath}static/lib/layui-src/css/layui.css" media="all">
    <link rel="stylesheet" href="${basePath}static/lib/font-awesome-4.7.0/css/font-awesome.min.css" media="all">
    <link rel="stylesheet" href="${basePath}static/css/style.css" media="all">
<body>
<div class="layuimini-container layuimini-page-anim">
    <div class="layuimini-main width_60">
        <form class="layui-form">
            <div class="layui-form-item">
                <label class="layui-form-label">标题</label>
                <div class="layui-input-block">
                    <input type="text" name="title" lay-verify="required" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">原因</label>
                <div class="layui-input-block">
                    <input type="text" name="reason" lay-verify="required" class="layui-input">
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">类型</label>
                <div class="layui-input-block">
                    <select name="type" lay-filter="type" lay-verify="required">
                        <option value="">请选择</option>
                        <option value="休学">休学</option>
                        <option value="辍学">辍学</option>
                        <option value="复学">复学</option>
                        <option value="转学">转学</option>
                    </select>
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">附件</label>
                <div class="layui-input-block">
                    <input type="hidden" name="attach" id="attach">
                    <button type="button" class="layui-btn" id="test3"><i class="layui-icon"></i>上传文件</button>
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
</div>
<script src="${basePath}static/lib/layui-src/layui.js" charset="utf-8"></script>
<script src="${basePath}static/js/lay-config.js?v=2.0.0" charset="utf-8"></script>
<script>
    layui.use(['form','jquery','upload'], function () {
        var form = layui.form,upload=layui.upload,$ = layui.jquery;

        upload.render({
            elem: '#test3'
            ,url: '${basePath}request/upload' //改成您自己的上传接口
            ,accept: 'file' //普通文件
            ,done: function(res){
                $("#attach").val(res.data);
                layer.msg('上传成功');
                console.log(res);
            }
        });

        //获取窗口索引
        var index = parent.layer.getFrameIndex(window.name);
        //监听提交
        form.on('submit(save)', function (data) {
            var json = {};
            json.title = data.field.title;
            json.reason = data.field.reason;
            json.type = data.field.type;
            json.attach = data.field.attach;

            $.ajax({
                url:"${basePath}request/create",
                type:"POST",
                contentType:'application/json',
                dataType:'json',
                data:JSON.stringify(json),
                success:function(data){
                    layer.msg(data.msg,{time:500},
                        function(){
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
