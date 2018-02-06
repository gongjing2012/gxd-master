<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>登录-CINDATA-统一权限管理服务平台</title>
    <link href="${ctx}/static/css/login.css" rel="stylesheet">
</head>
<body>
    <div class="wrapper">
        <form method="post" action="${ctx}/login.action?redirect=${RequestParameters.redirect!}" id="loginForm">
            <input type="hidden" name="password"/>
            <div class="logo">
              <div class="icon">
                <#--<img src="${miscDomain}/static/admin/img/logo.png" alt="">-->
                <img src="${ctx}/static/img/logo-lg.png" alt="">
              </div>
              <div class="name">
                <h2>CINDATA统一权限管理服务平台</h2>
              </div>
            </div>
            <div class="loginForm">
                <div class="hd">
                    <h2 class="sprite">管理员登录</h2>
                </div>
                <div class="bd">
                    <div class="item">
                        <label>用户名：</label>
                        <input type="text" name="loginAccount" value="${loginAccount!''}" placeholder="请输入用户名" tabIndex=1>
                    </div>
                    <div class="item">
                        <label>密码：</label><a href="#" id="getPassword">忘记密码?</a>
                        <input type="password" name="password" value="" placeholder="请输入密码" tabIndex=2>
                    </div>
                    <div class="item">
                        <label>验证码：</label>
                        <span class="validIntro">输入下图中的字符，不区分大小写</span>
                        <p class="validInput">
                            <input type="text" name="validateCode" id="validCode" placeholder="请输入验证码" tabIndex=3>
                            <img src="${ctx}/validateCode.action" class="validImg" alt="">
                            <a href="#" id="changeCode">换一个</a>
                        </p>
                    </div>
                </div>
                <div class="ft">
                    <div class="item">
                        <a href="#" id="loginBtn">登 录</a>
                    </div>
                </div>
            </div>
        </form>
        <p class="copyright">&copy; 2018 CINDATA 版权所有</p>
    </div>
</body>
<script src="${ctx}/static/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="${ctx}/static/plugins/md5/jQuery.md5.js"></script>
<script src="${ctx}/static/js/login.js"></script>
<script type="text/javascript">
    var ctx = '${ctx}';
    var state = '${state}';
    var message = '${message}';
    if (top.location != self.location) {
        top.location = self.location;
    }
</script>
</html>
