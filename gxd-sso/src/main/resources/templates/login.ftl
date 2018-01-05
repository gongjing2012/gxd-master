<!DOCTYPE html>
<html lang="en">
<head>
    <title>Authentication Service</title>
</head>
<body>
<form id="fm">
    <h2>Log in</h2>
    <input name="username" id="name" type="text" placeholder="Username"
           autofocus="true"/>
    <input name="redirect" id="redirect" type="hidden" value="http://127.0.0.1:3000"/>
    <input name="password" id="password" type="password" placeholder="Password"/>
    <div>(try username=hellokoding and password=hellokoding)</div>
    <div style="color: red">${error!}</div>
    <br/>
    <button type="button" id="test">Log In</button>
</form>
</body>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript" language="javascript">
    $("#test").click(function () {
        $.ajax({
            type: "POST",   //提交的方法
            url:"/login", //提交的地址
            data:$('#fm').serialize(),// 序列化表单值
            beforeSend: function(request) {
                request.setRequestHeader("token", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoZWxsb2tvZGluZyIsImlhdCI6MTUxNTE3MDkwN30._IrNM9gi_GRqXaU8J6bpneDbaARfeUFZo4lNOrAmG7M");
                request.setRequestHeader("uid", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoZWxsb2tvZGluZyIsImlhdCI6MTUxNTE3MDkwN30._IrNM9gi_GRqXaU8J6bpneDbaARfeUFZo4lNOrAmG7M");

            },
            async: false,
            error: function(request) {  //失败的话
                alert("Connection error");
            },
            success: function(data) {  //成功
                alert(data);  //就将返回的数据显示出来
                window.location.href="http://127.0.0.1:3000"
            }
        });
    });
</script>
</html>