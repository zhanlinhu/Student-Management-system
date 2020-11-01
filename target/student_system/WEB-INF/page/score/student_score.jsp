<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>学生成绩</title>
</head>
<body>
<div class="layuimini-container layuimini-page-anim">
    <div class="layuimini-main">
        <div class="layui-row">
            <table class="layui-hide" id="currentTableId" lay-filter="currentTableFilter"></table>
        </div>

    </div>
</div>
<script>
    layui.use(['form', 'table'], function () {
        var $ = layui.jquery,
            form = layui.form,
            table = layui.table;
            table.render({
                elem: '#currentTableId',
                url: '${basePath}score/student_score',
                contentType:'application/json',
                method:"post",
                toolbar: '#toolbar',
                defaultToolbar: ['filter', 'exports', 'print'],
                page: false,
                cols: [[
                    {field: 'stu_name',  title: '姓名'},
                    {field: 'course_name', title: 'Java基础'},
                    {field: 'year', title: '年份'},
                    {field: 'type', title: '类型'},
                    {field: 'score', title: '分数'}
                ]],
                done: function(res, page, count){
                    for(var index in res.data){
                        if(res.data[index].selected >= 1){
                            res.data[index]["LAY_CHECKED"]='true';
                            var index= res.data[index]['LAY_TABLE_INDEX'];
                            $('tr[data-index=' + index + '] input[type="checkbox"]').prop('checked', true);
                            $('tr[data-index=' + index + '] input[type="checkbox"]').next().addClass('layui-form-checked');
                        }
                    }
                },
                skin: 'line'
            });

        /**
         * toolbar事件监听
         */
        table.on('toolbar(currentTableFilter)', function (obj) {
            if (obj.event === 'save') {   // 监听添加操作
                var checkStatus = table.checkStatus('currentTableId');
                var data = checkStatus.data;
                if(data.length ==0){
                    layer.msg("请选择选课信息",{time:1000});
                    return;
                }
                var sectionIdArr = [];
                var courseIdArr = [];
                $.each(data,function (i,item) {
                    sectionIdArr.push(item.id);
                    courseIdArr.push(item.course.id);
                })
                $.ajax({
                    url:"${basePath}score/create",
                    type:"POST",
                    dataType:'json',
                    data:{
                        sectionIds:sectionIdArr.join(","),
                        courseIds:courseIdArr.join(",")
                    },
                    success:function(data){
                        layer.msg(data.msg,{time:500},
                            function(){
                                table.reload("currentTableId");
                            });
                    }
                });
            }
        });
    });
</script>
</body>
</html>
