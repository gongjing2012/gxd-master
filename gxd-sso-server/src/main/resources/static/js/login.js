/* 登录页
 ================================== */
$(function () {
    if (state != null && state != undefined) {
        if (state == -1 || state == -2) {
            addTip($('#validCode'), message);
        } else if (state = -11) {
            addTip($('input[name=loginAccount]'), message);
        } else if (state = -12) {
            addTip($('input[name=password]'), message);
        } else if (state = -13 || state == -14) {
            addTip($('.sprite'), message);
        } else {
            addTip($('#validCode'), message);
        }
    }
    // 设置需要验证的表单name值
    var valid = {
        loginAccount: {
            required: true,
            message: "请输入用户名"
        },
        password: {
            required: true,
            message: "请输入密码"
        },
        validateCode: {
            required: true,
            message: '请输入验证码',
            validType: validCode,
            validMessage: '验证码不正确'
        }
    };

    // 提交表单
    $('#loginBtn').bind('click', function () {
        if (checkForm()) {
            $('#loginForm').submit();
        }
        return false;
    });

    // 验证表单
    function checkForm() {
        var result = false;
        $.each(valid, function (i, n) {
            var input = $('input[name=' + i + ']');
            if (n.required && !$.trim(input.val())) {
                addTip(input, n.message);
                result = false;
            } else if (n.validType) {
                result = n.validType(input, n.validMessage);
            } else {
                result = true;
            }
        });
        return result;
    }

    // 获取焦点事件
    $('input').bind('focus', function () {
        $(this).removeClass('danger');
        $(this).siblings('.help-block').remove();
    });

    // 失去焦点事件
    $.each(valid, function (i, n) {
        var input = $('input[name=' + i + ']');
        input.bind('blur', function () {
            if (n.required && !$.trim(input.val())) {
                addTip(input, n.message);
            }
        });
    });

    // 校验验证码 暂时屏蔽
    function validCode(input, message) {
        return true;
    }

    // 加入提示消息
    function addTip(input, tip) {
        input.addClass('danger');
        input.parent().find('.help-block').remove();
        input.parent().append(helpBlock(tip));
    }

    // 提示消息
    function helpBlock(string) {
        return '<span class="help-block">' + string + '</span>';
    }

    // 更换验证码链接事件
    $('#changeCode').bind('click', function () {
        $('.validImg').attr('src', ctx + '/validateCode.action?' + Math.random());
        return false;
    });
    // 重新获取验证码
    $(".validImg").click(function () {
        $(this).attr("src", ctx + "/validateCode?" + Math.random());
    });
    // 绑定回车键事件
    $('#loginForm').bind('keyup', function (e) {
        if (e.keyCode == 13) {
            if (checkForm()) {
                $(this).submit();
            }
        }
    });

});