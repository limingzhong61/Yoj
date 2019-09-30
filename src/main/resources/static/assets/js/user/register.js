// Example starter JavaScript for disabling form submissions if there are invalid fields
$(function () {
    var emailExist = true;
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
    var csrfToken = $("meta[name='_csrf']").attr("content");
    var headers = {};
    headers[csrfHeader] = csrfToken;

    $("#registerBtn").click(function () {
        if (!validateWithMsg("#userName", $("#userName").val() != "","用户名不能为空")) {
            return false;
        }
        if (!validatePassword()) {
            return false;
        }
        if (!validateSecondPw()) {
            return false;
        }
        if (emailExist) {
            return false;
        }
        var formData = {};
        $("form").find("input").each(function () {
            formData[this.id] = this.value;
        });
        // console.log(formData);

        $.ajax({
            url: "/u/r/register",
            method: "POST",
            data: formData,
            headers: headers,
            success(res) {
                // console.log(res);
                if (res.success) {
                    // console.log(res);
                    window.location.href = "/user/login";
                } else {
                    for (var obj in res.extend) {
                        // console.log("form input[id=" + obj + "]");
                        var $input = $("form input[id=" + obj + "]");
                        validateWithMsg($input, false, res.extend[obj]);
                    }
                }
            },
            error(res) {
                // console.log(res);
            }
        })
    });
    //用户名验证
    $("#userName").change(function () {
        if(this.value === ""){
            validateWithMsg(this,false, "用户名不能为空");
            return;
        }
        $.ajax({
            url: "/u/r/validateUserName/"+this.value,
            method: "GET",
            headers: headers,
            success(res) {
                // console.log(res);
                if (res.success) {
                    // window.location.href = "/user/login";
                    validate($("#userName"),true);
                } else {
                    validateWithMsg($("#userName"),false,res.msg);
                }
            },
            error(res) {
                // console.log(res);
            }
        })

    });

    //邮箱验证
    function validateEmailFormat() {
        var emilReg = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/i;
        return validateWithMsg('#email', emilReg.test($("#email").val()),"请输入一个有效的电子邮件地址.");
    }

    $("#email").change(function () {
        validateEmailFormat();
        $.ajax({
            url: "/u/r/validateEmail/" + $("#email").val(),
            method: "GET",
            success(res) {
                // console.log(res);
                if (res.success) {
                    validate($("#email"),true);
                } else {
                    validateWithMsg($("#email"),false,res.msg);
                }
            },
            error(res) {
                // console.log(res);
            }
        })

    });
    $("#emailBtn").click(function () {
        if (!validateEmailFormat()) {
            return;
        }
        $('#myModal').modal('show');
        $.ajax({
            url: "/u/r/getEmailCheckCode/" + $("#email").val(),
            method: "GET",
            success(res) {
                $('#myModal').modal('hide');
                // console.log(res);
                if (res.success) {
                    emailExist = false;
                    countDown();
                } else {
                    emailExist = true;
                    validateWithMsg($("#email"), false, res.msg);
                }

            },
            error(res) {
                // console.log(res);
                $('#myModal').modal('hide');
            }
        })
    });
    var maxTime = 60;

    function countDown() {
        if (maxTime == 0) {
            checkCode = "";
            $("#emailBtn").removeAttr("disabled")
            $("#emailBtn").text("获取验证码");
            maxTime = 60;
        } else {
            $("#emailBtn").attr("disabled", "");
            $("#emailBtn").text(maxTime + "秒后重新获取");
            maxTime--;
            setTimeout(countDown, 1000);
        }
    }

    //密码校验
    function validateSecondPw() {
        if ($("#password").val() != "") {
            return validate("#secondPassword", $("#secondPassword").val() == $("#password").val());
        }
        return true;
    }

    function validatePassword() {
        var flag = validate("#password", $("#password").val() != "");
        if (!flag) return flag;
        return validateSecondPw();
    }

    $("#password").change(function () {
        validatePassword();
    });

    $("#secondPassword").change(function () {
        validateSecondPw();
    });

});