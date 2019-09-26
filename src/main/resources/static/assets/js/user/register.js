// Example starter JavaScript for disabling form submissions if there are invalid fields
$(function () {
    var emailExist = false;
    $("#registerBtn").click(function () {
        if (!validate("#userName", $("#userName").val() != "")) {
            return false;
        }
        if (!validatePassword()) {
            return false;
        }
        if (!validateSecondPw()) {
            return false;
        }
        if (!emailExist || !validateEmailFormat()) {
            return false;
        }
        var formData = {};
        $("form").find("input").each(function () {
            formData[this.id] = this.value;
        });
        // console.log(formData);


        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var headers = {};
        headers[csrfHeader] = csrfToken;
        $.ajax({
            url: "/u/register",
            method: "POST",
            data: formData,
            headers: headers,
            success(res) {
                console.log(res);
                if (res.success) {
                    console.log(res);
                    window.location.href = "/user/login";
                } else {
                    for (var obj in res.extend) {
                        console.log("form input[id=" + obj + "]");
                        var $input = $("form input[id=" + obj + "]");
                        validateWithMsg($input, false, res.extend[obj]);
                    }
                }
            },
            error(res) {
                console.log(res);
            }
        })
    });

    $("#userName").change(function () {
        validate(this, this.value != "");
    });

    //邮箱验证
    function validateEmailFormat() {
        var emilReg = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/i;
        return validate('#email', emilReg.test($("#email").val()));
    }

    $("#email").change(function () {
        validateEmailFormat();
    });
    $("#emailBtn").click(function () {
        // countDown();
        // return;
        if (!validateEmailFormat()) {
            return;
        }

        $('#myModal').modal('show');
        $.ajax({
            url: "/u/getEmailCheckCode/" + $("#email").val(),
            method: "GET",
            success(res) {
                $('#myModal').modal('hide');
                console.log(res);
                if (res.success) {
                    countDown();
                } else {
                    emailExist = true;
                    validateWithMsg($("#email"), false, res.msg);
                }

            },
            error(res) {
                console.log(res);
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